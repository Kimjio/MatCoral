package com.kimjio.coral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kimjio.coral.R;
import com.kimjio.coral.databinding.SplashActivityBinding;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        }, 2000);
    }
}
