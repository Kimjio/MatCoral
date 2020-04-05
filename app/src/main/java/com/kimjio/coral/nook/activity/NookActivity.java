package com.kimjio.coral.nook.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.databinding.NookActivityBinding;
import com.kimjio.coral.nook.viewmodel.NookViewModel;

import java.util.Objects;

public class NookActivity extends BaseActivity<NookActivityBinding> {
    private NookViewModel viewModel;

    private static final String TAG = "NookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(NookViewModel.class);
        observeData();

        WebServiceToken webServiceToken = getIntent().getParcelableExtra("web_service_token");
        getSessionCookie(Objects.requireNonNull(webServiceToken).getAccessToken());
    }

    @Override
    protected void observeData() {
        viewModel.getThrowable().observe(this, Throwable::printStackTrace);
        viewModel.getCookieResponseData().observe(this, response -> viewModel.loadUsers());
        viewModel.getUsers().observe(this, users -> {
            if (users.size() > 1) {
                //TODO Select User
            } else {
                viewModel.loadToken(users.get(0).getId());
            }
        });
        viewModel.getToken().observe(this, token -> {
            Log.d(TAG, "observeData: " + token.getToken());
        });
    }

    private void getSessionCookie(@NonNull String accessToken) {
        if (viewModel.getCookieResponseData().getValue() == null)
            viewModel.loadCookieResponseData(accessToken);
    }
}