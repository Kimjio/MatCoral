package com.kimjio.coral.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.databinding.SplashActivityBinding;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            Class<? extends Activity> activityClass = IntroActivity.class;
            if (SessionTokenManager.getInstance(getApplication()).loadSessionToken() != null)
                activityClass = LoginActivity.class;
            startActivity(new Intent(this, activityClass));
            finish();
        }, 2000);
    }
}