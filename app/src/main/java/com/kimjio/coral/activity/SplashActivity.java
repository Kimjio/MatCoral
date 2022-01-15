package com.kimjio.coral.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.splashscreen.SplashScreen;

import com.kimjio.coral.R;
import com.kimjio.coral.databinding.SplashActivityBinding;
import com.kimjio.coral.manager.SessionTokenManager;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {
    private String shortcut;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shortcut = getIntent().getStringExtra("shortcut");
        if (shortcut == null) shortcut = "";
        // TODO: Custom Splash
        /*switch (shortcut) {
            case "nook":
                setTheme(R.style.Theme_Splash_Nook);
                break;
            case "splat":
                setTheme(R.style.Theme_Splash_Splat);
                break;
            default:
                setTheme(R.style.Theme_Splash);
        }*/
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepVisibleCondition(() -> true);
        super.onCreate(savedInstanceState);
        handler.postDelayed(this::openActivity, 2000);
    }

    public void openActivity() {
        Class<? extends Activity> activityClass = IntroActivity.class;
        if (SessionTokenManager.getInstance(getApplication()).loadSessionToken() != null)
            activityClass = LoginActivity.class;
        startActivity(new Intent(this, activityClass).putExtra("shortcut", shortcut));
        finish();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(this::openActivity);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        handler.removeCallbacks(this::openActivity);
        super.finish();
    }
}