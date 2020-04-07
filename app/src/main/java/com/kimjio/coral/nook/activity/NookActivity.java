package com.kimjio.coral.nook.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.R;
import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.api.NintendoApi;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.databinding.NookActivityBinding;
import com.kimjio.coral.nook.viewmodel.NookViewModel;
import com.kimjio.coral.nook.widget.NintendoCharactersView;
import com.kimjio.coral.util.ViewUtils;

import java.util.Objects;

import static com.kimjio.coral.api.NintendoApi.getAuthorization;

public class NookActivity extends BaseActivity<NookActivityBinding> {
    private NookViewModel viewModel;
    private String token;

    private static final String TAG = "NookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requireSupportActionBar().setLogo(R.drawable.logo_nooklink);

        viewModel = ViewModelProviders.of(this).get(NookViewModel.class);
        observeData();

        WebServiceToken webServiceToken = getIntent().getParcelableExtra("web_service_token");
        getSessionCookie(Objects.requireNonNull(webServiceToken).getAccessToken());

        binding.button.setOnClickListener(v -> {
            NintendoCharactersView nintendoCharactersView = new NintendoCharactersView(this);
            PopupWindow popupWindow = new PopupWindow(nintendoCharactersView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewUtils.dpToPx(this, 240));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setElevation(8);
            popupWindow.showAsDropDown(v);
            nintendoCharactersView.setOnItemClickListener((charSeq, position) -> {
                binding.editText.append(charSeq);
                popupWindow.dismiss();
            });
        });

        binding.buttonSend.setOnClickListener(v -> {
            viewModel.sendMessage(getAuthorization(token), binding.editText.getText().toString());
        });
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
            //TODO NookTokenManager
            Log.d(TAG, "observeData: " + token.getToken());
            this.token = token.getToken();
        });
    }

    private void getSessionCookie(@NonNull String accessToken) {
        if (viewModel.getCookieResponseData().getValue() == null)
            viewModel.loadCookieResponseData(accessToken);
    }
}