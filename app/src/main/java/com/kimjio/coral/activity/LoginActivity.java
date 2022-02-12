package com.kimjio.coral.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kimjio.coral.R;
import com.kimjio.coral.api.FlapgApi;
import com.kimjio.coral.api.NintendoException;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.me.Me;
import com.kimjio.coral.databinding.LoginActivityBinding;
import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.manager.UIManager;
import com.kimjio.coral.manager.UserManager;
import com.kimjio.coral.viewmodel.LoginViewModel;
import com.kimjio.customtabs.CustomTabActivityHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * TODO use previous issued token
 */
public class LoginActivity extends BaseActivity<LoginActivityBinding> {
    private static LoginViewModel viewModel;
    private SessionTokenManager sessionTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(LoginViewModel.class);

        sessionTokenManager = SessionTokenManager.getInstance(getApplication());
        SessionToken sessionToken = sessionTokenManager.loadSessionToken();
        if (sessionToken != null) {
            hideLogin();
            login(sessionToken);
        } else {
            binding.animation.playAnimation();
        }
        binding.buttonSignIn.setOnClickListener(v -> {
            CustomTabsIntent intent = new CustomTabsIntent.Builder()
                    .setDefaultColorSchemeParams(new CustomTabColorSchemeParams.Builder().setToolbarColor(getResources().getColor(R.color.colorPrimary)).build())
                    .setShowTitle(true)
                    .build();

            CustomTabActivityHelper.openCustomTab(
                    this,
                    intent,
                    viewModel.getLoginUri());
        });

        observeData();
    }

    @Override
    protected void observeData() {
        viewModel.getSessionToken().observe(this, token -> {
            sessionTokenManager.saveSessionToken(token);
            login(token);
        });
        viewModel.getThrowable().observe(this, throwable -> {
            if (throwable instanceof HttpException) {
                handleHTTPException((HttpException) throwable);
            } else if (throwable instanceof NintendoException) {
                handleNintendoException((NintendoException) throwable);
            } else {
                new MaterialAlertDialogBuilder(this)
                        .setIcon(R.drawable.ic_error_outline)
                        .setTitle(R.string.error_title)
                        .setMessage(R.string.error_net_unstable)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, (dialogInterface, which) -> {
                            finish();
                        })
                        .show();
            }
        });
        viewModel.getAccountToken().observe(this, token -> {
            viewModel.loadMe(token.getAccessToken());
            viewModel.loadFTokenNSO(token.getIdToken());
        });
        LiveData<List<Object>> meToken = Transformations.switchMap(viewModel.getMe(),
                (me) -> {
                    UserManager.getInstance().setAccountUser(me);
                    return Transformations.map(viewModel.getFTokenNSO(),
                            (tokenNso) -> {
                                TokenManager.getInstance().setNsoToken(tokenNso);
                                return Arrays.asList(me, tokenNso);
                            });
                });
        meToken.observe(this, (list) -> viewModel.login((Me) list.get(0), (FToken) list.get(1)));
        viewModel.getFTokenAPP().observe(this, fToken -> {
            TokenManager.getInstance().setWebAppToken(fToken);
            Intent intent = new Intent(this, MainActivity.class);
            if (!UIManager.getInstance(this).welcomeDisplayed()) {
                intent.setComponent(new ComponentName(this, WelcomeActivity.class));
            }
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
        viewModel.getTokenResponse().observe(this, response -> {
            TokenManager.getInstance()
                    .setWebApiServerCredential(response.getWebApiServerCredential());
            UserManager.getInstance()
                    .setUser(response.getUser());
            viewModel.loadFTokenAPP(TokenManager.getInstance().getWebApiServerCredential().getAccessToken());
        });
        viewModel.setProgressChangedListener((text, step) -> {
            if (binding.progressText.getTag() != null && binding.progressText.getTag().equals(step))
                binding.progressText.append("\n" + getText(text));
            else
                binding.progressText.setText(text);
            if (text == R.string.progress_issue_token && step > 0)
                binding.progressText.append(" (" + step + ")");
            binding.progressText.setTag(step);
        });
    }

    private void showLogin() {
        binding.loginLayout.setVisibility(View.VISIBLE);
        binding.animation.playAnimation();
    }

    private void hideLogin() {
        binding.loginLayout.setVisibility(View.GONE);
    }

    private void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.progressText.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
        binding.progressText.setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getData() != null) {
            if (viewModel.verifyToken(intent.getData())) {
                viewModel.loadSessionToken();
            }
        }
    }

    private void handleHTTPException(HttpException e) {
        Response<?> response = e.response();
        if (response != null) {
            ResponseBody body = response.errorBody();
            if (body != null)
                try {
                    JSONObject object = new JSONObject(body.string());
                    String error = object.getString("error");
                    if (error.equals("invalid_grant"))
                        error = getString(R.string.error_re_auth);
                    if (error.equals("invalid_request"))
                        error = getString(R.string.error_token);

                    new MaterialAlertDialogBuilder(this)
                            .setIcon(R.drawable.ic_error_outline)
                            .setTitle(R.string.error_title)
                            .setMessage(error)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, (dialogInterface, which) -> {
                                sessionTokenManager.clear();
                                hideProgress();
                                showLogin();
                            })
                            .show();
                } catch (JSONException | IOException ignore) {
                }

            if (response.raw().request().url().toString().contains(FlapgApi.FLAPG) && e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                new MaterialAlertDialogBuilder(this)
                        .setIcon(R.drawable.ic_error_outline)
                        .setTitle(R.string.error_title)
                        .setMessage(R.string.error_unknown)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, (dialogInterface, which) -> {
                            finish();
                        })
                        .show();
        }
    }

    private void handleNintendoException(NintendoException e) {
        int status = e.getStatus();
        String error = e.getMessage();
        if (error != null) {
            if (error.equals("Upgrade required."))
                error = getString(R.string.error_upgrade);
            if (error.equals("Invalid token."))
                error = getString(R.string.error_token);
            if (error.equals("Nintendo Switch Onlineアプリの更新によりログインできません")) {
                error = getString(R.string.error_upgrade);
                status = NintendoException.ERROR_UPGRADE;
            }
        }
        if (status == NintendoException.ERROR_UPGRADE || status == NintendoException.ERROR_INVALID_TOKEN) {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_error_outline)
                    .setTitle(R.string.error_title)
                    .setMessage(error)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, (dialogInterface, which) -> {
                        if (e.getStatus() == NintendoException.ERROR_INVALID_TOKEN) {
                            sessionTokenManager.clear();
                            hideProgress();
                            showLogin();
                        } else {
                            finish();
                        }
                    })
                    .show();
        } else {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_error_outline)
                    .setTitle(R.string.error_title)
                    .setMessage(String.format(Locale.getDefault(), "%d: %s", e.getStatus(), error))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, (dialogInterface, which) -> {
                        finish();
                    })
                    .show();
        }
    }

    private void login(SessionToken sessionToken) {
        hideLogin();
        showProgress();
        viewModel.loadAccountToken(sessionToken.getSessionToken());
    }
}