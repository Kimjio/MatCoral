package com.kimjio.coral.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kimjio.coral.R;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.databinding.LoginActivityBinding;
import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.manager.UIManager;
import com.kimjio.coral.manager.UserManager;
import com.kimjio.coral.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    private static LoginViewModel viewModel;
    private SessionTokenManager sessionTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        sessionTokenManager = SessionTokenManager.getInstance(getApplication());
        SessionToken token = sessionTokenManager.loadSessionToken();
        if (token != null) {
            hideLogin();
            login(token);
        } else {
            binding.animation.playAnimation();
            binding.buttonSignIn.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, viewModel.getLoginUri())));
        }
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
    }

    private void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getData() != null) {
            if (viewModel.verifyToken(intent.getData())) {
                viewModel.getSessionToken().observe(this, sessionToken -> {
                    sessionTokenManager.saveSessionToken(sessionToken);
                    login(sessionToken);
                });
            }
        }
    }


    private void login(SessionToken sessionToken) {
        hideLogin();
        showProgress();
        viewModel.getThrowable().observe(this, throwable -> {
            if (throwable instanceof HttpException) {
                Response response = ((HttpException) throwable).response();
                if (response != null) {
                    ResponseBody body = response.errorBody();
                    if (body != null)
                        try {
                            JSONObject object = new JSONObject(body.string());
                            String error = object.getString("error");
                            if (error.equals("invalid_grant"))
                                error = getString(R.string.error_re_auth);

                            new MaterialAlertDialogBuilder(this)
                                    .setIcon(R.drawable.ic_error)
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
                }
            }
        });
        viewModel.getToken(sessionToken.getSessionToken()).observe(this, token ->
                viewModel.getMe(token.getAccessToken()).observe(this, me ->
                        viewModel.getFTokens(token.getIdToken()).observe(this, fTokenMap -> {
                            FToken nso = fTokenMap.get(FToken.NSO);
                            FToken webApp = fTokenMap.get(FToken.WEB_APP);
                            viewModel.login(me, Objects.requireNonNull(nso)).observe(this, response -> {
                                TokenManager.getInstance()
                                        .setNsoToken(nso)
                                        .setWebAppToken(webApp)
                                        .setWebApiServerCredential(response.getWebApiServerCredential());
                                UserManager.getInstance()
                                        .setUser(response.getUser())
                                        .setAccountUser(me);
                                Intent intent = new Intent(this, MainActivity.class);
                                if (!UIManager.getInstance(this).welcomeDisplayed()) {
                                    intent.setComponent(new ComponentName(this, WelcomeActivity.class));
                                }
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                            });
                        })));
    }
}