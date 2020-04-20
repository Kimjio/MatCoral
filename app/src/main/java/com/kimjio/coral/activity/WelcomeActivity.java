package com.kimjio.coral.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kimjio.coral.data.User;
import com.kimjio.coral.data.me.Me;
import com.kimjio.coral.databinding.WelcomeActivityBinding;
import com.kimjio.coral.manager.UIManager;
import com.kimjio.coral.manager.UserManager;

public class WelcomeActivity extends BaseActivity<WelcomeActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Me me = UserManager.getInstance().getAccountUser();
        User user = UserManager.getInstance().getUser();

        binding.setMe(me);
        binding.setUser(user);
        binding.buttonStart.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        UIManager.getInstance(this).setWelcomeDisplayed();
    }
}