package com.kimjio.coral.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.R;
import com.kimjio.coral.data.view.IntroItem;
import com.kimjio.coral.databinding.LoginActivityBinding;
import com.kimjio.coral.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.kimjio.coral.api.NintendoAccountApi.NINTENDO_ACCOUNTS;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    private static final String TAG = "LoginActivity";

    public static final String KEY_STATE = "state";
    public static final String KEY_VERIFIER = "verifier";

    public static final String AUTH_SCHEME = getEncodedString("npf71b963c1b7b6d119") + "://auth";
    public static final String CLIENT_ID = "71b963c1b7b6d119";
    public static final String SCOPE = getEncodedString("openid user user.birthday user.mii user.screenName");
    public static final String RESPONSE_TYPE = getEncodedString("session_token_code");

    private static LoginViewModel viewModel;

    private String state; //state
    private String sessionTokenCodeChallenge; //stc:c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding.setItem(new IntroItem("Please sign in to\nyour Nintendo Account.", "Your Nintendo Account must be for ages 13 and up and must be linked to a Nintendo Switch console.", R.raw.tutorial_image_login));
        binding.animation.playAnimation();
        binding.buttonSignIn.setOnClickListener(v -> {
            String rawUrl = NINTENDO_ACCOUNTS + "connect/1.0.0/authorize?state=%s&redirect_uri=%s&client_id=%s&scope=%s&response_type=%s&session_token_code_challenge=%s&session_token_code_challenge_method=S256&theme=login_form";
            state = getEncodedString(getRandom());
            sessionTokenCodeChallenge = getCrypto(state);
            String url = String.format(rawUrl, state, AUTH_SCHEME, CLIENT_ID, SCOPE, RESPONSE_TYPE, sessionTokenCodeChallenge);
            //state 로 확인, challenge 로 추가 검증 (state 가 다르면 무시, challenge 가 다르면 로딩 후 통신 오류 발생 (Communication Error))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getDataString() != null) {
            Log.d(TAG, "onNewIntent: " + intent.getDataString());

            final String responseRaw = intent.getDataString();
            final String[] args = responseRaw.substring(AUTH_SCHEME.length()).replace('#', '\0').split("&");
            final String sessionCodeToken = args[1].split("=")[1];
            final String sessionTokenCodeVerifier = args[2].split("=")[1];
            final boolean isStateEquals = sessionTokenCodeVerifier.equals(state);
            Log.d(TAG, "onNewIntent: " + isStateEquals);
            try {
                JSONObject jwtPayload = new JSONObject(new String(Base64.decode(args[1].split("=")[1].split("\\.")[1], Base64.DEFAULT)));
                boolean isSTCCEquals = jwtPayload.getString("stc:c").equals(sessionTokenCodeChallenge);
                Log.d(TAG, "onNewIntent: " + isSTCCEquals);

                viewModel.getSessionToken(CLIENT_ID, sessionCodeToken, sessionTokenCodeVerifier).observe(this, sessionToken -> {
                    sessionToken.getCode();
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_STATE, state);
        outState.putString(KEY_VERIFIER, sessionTokenCodeChallenge);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        state = savedInstanceState.getString(KEY_STATE);
        sessionTokenCodeChallenge = savedInstanceState.getString(KEY_VERIFIER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static String getEncodedString(String s) {
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ignore) {
        }
        return s;
    }

    private static String getDecodedString(String s) {
        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ignore) {
        }
        return s;
    }

    private String getCryptoRandom() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String s = Base64.encodeToString(digest.digest(getRandom().getBytes()), Base64.URL_SAFE | Base64.NO_PADDING);
            Log.d(TAG, "getCryptoRandom: " + s);
            return s;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCrypto(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String s = new String(Base64.encode(digest.digest(str.getBytes(StandardCharsets.UTF_8.toString())), Base64.URL_SAFE | Base64.NO_PADDING), StandardCharsets.UTF_8.toString()).replace("+", "-").replace("/", "_").replace("=", "");
            Log.d(TAG, "getCrypto: " + str);
            return s;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRandom() {
        final int size = 50;
        Random random = new Random();
        String text = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = text.charAt(random.nextInt(text.length()));
        }
        String s = new String(chars);
        Log.d(TAG, "getRandom: " + s);
        return s;
    }
}
