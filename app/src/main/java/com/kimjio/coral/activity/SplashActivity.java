package com.kimjio.coral.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.databinding.SplashActivityBinding;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: " + System.getProperty("http.agent"));

        new Handler().postDelayed(() -> {
            Class<? extends Activity> activityClass = IntroActivity.class;
            if (SessionTokenManager.getInstance(getApplication()).loadSessionToken() != null)
                activityClass = LoginActivity.class;
            startActivity(new Intent(this, activityClass));
            finish();
        }, 2000);
    }
}