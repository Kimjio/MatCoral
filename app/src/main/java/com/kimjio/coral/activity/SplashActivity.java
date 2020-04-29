package com.kimjio.coral.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.databinding.SplashActivityBinding;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {
    Handler handler = new Handler();
    Runnable runnable = () -> {
        Class<? extends Activity> activityClass = IntroActivity.class;
        if (SessionTokenManager.getInstance(getApplication()).loadSessionToken() != null)
            activityClass = LoginActivity.class;
        startActivity(new Intent(this, activityClass));
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void finish() {
        handler.removeCallbacks(runnable);
        super.finish();
    }
}