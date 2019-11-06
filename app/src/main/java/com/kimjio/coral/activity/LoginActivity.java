package com.kimjio.coral.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.R;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.request.LoginParamWrapper;
import com.kimjio.coral.data.view.IntroItem;
import com.kimjio.coral.databinding.LoginActivityBinding;
import com.kimjio.coral.util.StringUtils;
import com.kimjio.coral.viewmodel.LoginViewModel;
import com.kimjio.hash.HashTool;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    private static final String TAG = "LoginActivity";

    private static LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setItem(new IntroItem("Please sign in to\nyour Nintendo Account.", "Your Nintendo Account must be for ages 13 and up and must be linked to a Nintendo Switch console.", R.raw.tutorial_image_login));

        SessionToken token = loadSessionToken();
        if (token != null) {
            binding.loginLayout.setVisibility(View.GONE);
            login(token);
        } else {
            binding.animation.playAnimation();
            binding.buttonSignIn.setOnClickListener(v -> {
                /*viewModel.getAuth().observe(this, auth -> {
                    viewModel.getLoginUrl().observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String url) {
                            String query = url.substring(url.indexOf('?'));
                            String path = url.substring(0, url.indexOf('?'));
                            String[] queries = query.split("&");
                            for (int i = 0; i < queries.length; i++) {
                                String[] q = queries[i].split("=");
                                q[1] = StringUtils.getEncodedString(q[1]);
                                queries[i] = TextUtils.join("=", q);
                            }
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(path + TextUtils.join("&", queries))));
                            viewModel.getLoginParam().observe(LoginActivity.this, new Observer<LoginParamWrapper>() {
                                @Override
                                public void onChanged(LoginParamWrapper loginParamWrapper) {
                                    viewModel.login(loginParamWrapper).observe(LoginActivity.this, loginResult ->
                                            Log.d(TAG, "onChanged: " + loginResult.getUser().getName()));
                                    viewModel.getLoginParam().removeObserver(this);
                                }
                            });
                            viewModel.getLoginUrl().removeObserver(this);
                        }
                    });
                });*/
                startActivity(new Intent(Intent.ACTION_VIEW, viewModel.getLoginUri()));
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getDataString() != null) {
            Log.d(TAG, "onNewIntent: " + intent.getDataString());
            //viewModel.postData(intent.getDataString());
            if (viewModel.verifyToken(intent.getData())) {
                viewModel.getSessionToken().observe(this, sessionToken -> {
                    saveSessionToken(sessionToken);
                    login(sessionToken);
                });
            }
        }
    }

    private void saveSessionToken(SessionToken sessionToken) {
        SharedPreferences preferences = getSharedPreferences("session_token", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("code", Base64.encodeToString(sessionToken.getCode().getBytes(), Base64.DEFAULT));
        editor.putString("session_token", Base64.encodeToString(sessionToken.getSessionToken().getBytes(), Base64.DEFAULT));
        editor.apply();
    }

    private SessionToken loadSessionToken() {
        SharedPreferences preferences = getSharedPreferences("session_token", MODE_PRIVATE);
        if (!(preferences.contains("code") && preferences.contains("session_token")))
            return null;
        String code, sessionToken;
        code = new String(Base64.decode(preferences.getString("code", ""), Base64.DEFAULT));
        sessionToken = new String(Base64.decode(preferences.getString("session_token", ""), Base64.DEFAULT));
        return new SessionToken(code, sessionToken);
    }

    private void login(SessionToken sessionToken) {
        viewModel.getToken(sessionToken.getSessionToken()).observe(this, token -> {
            viewModel.getMe(token.getAccessToken()).observe(this, me -> {
                long timestamp = System.currentTimeMillis() / 1000;
                String timestampStr = Long.toString(timestamp);
                Log.d(TAG, "login: " + token.toString());
                viewModel.getFTokens(token.getIdToken(), timestampStr, HashTool.getHash(token.getIdToken(), timestampStr)).observe(this, fTokens -> {
                    viewModel.login(me.getBirthday(), me.getCountry(), fTokens.getLoginNSO().getToken(), fTokens.getLoginNSO().getUUID(), Long.parseLong(fTokens.getLoginNSO().getTimestamp()), fTokens.getLoginNSO().getF()).observe(this, loginResult -> {
                        binding.title.setText(loginResult.getUser().getName());
                        Log.d(TAG, "onNewIntent: " + loginResult.getUser().getName());
                    });
                    Log.d(TAG, "onNewIntent: " + fTokens.getLoginNSO().getF());
                });
            });
        });
    }
}