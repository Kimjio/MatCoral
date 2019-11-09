package com.kimjio.coral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.R;
import com.kimjio.coral.data.GameWebService;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.databinding.MainActivityBinding;
import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.manager.UserManager;
import com.kimjio.coral.recycler.GameWebServiceAdapter;
import com.kimjio.coral.splat.activity.SplatActivity;
import com.kimjio.coral.viewmodel.MainViewModel;

import java.util.Objects;

public class MainActivity extends BaseActivity<MainActivityBinding> {

    private static final String TAG = "MainActivity";

    private static MainViewModel viewModel;

    private GameWebServiceAdapter adapter = new GameWebServiceAdapter((item, position) -> {
        if (item.getId() == GameWebService.ID_SPLAT2) {
            startActivity(new Intent(this, SplatActivity.class));
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        binding.list.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getGameWebServicesData().observe(this, gameWebServices -> {
            adapter.setGameWebServices(gameWebServices);
        });
        if (TokenManager.getInstance().expired()) {
            getToken();
        }
    }

    private void getToken() {
        viewModel.getToken(SessionTokenManager.getInstance(this).loadSessionToken().getSessionToken()).observe(this, token -> {
            viewModel.getMe(token.getAccessToken()).observe(this, me -> {
                viewModel.getFTokens(token.getIdToken()).observe(this, fTokenMap -> {
                    FToken nso = fTokenMap.get(FToken.NSO);
                    FToken webApp = fTokenMap.get(FToken.WEB_APP);
                    viewModel.getToken(Objects.requireNonNull(nso)).observe(this, response -> {
                        TokenManager.getInstance()
                                .setNsoToken(nso)
                                .setWebAppToken(webApp)
                                .setWebApiServerCredential(response.getWebApiServerCredential());
                        UserManager.getInstance().setAccountUser(me);
                        UserManager.getInstance().setUser(response.getUser());
                    });
                });
            });
        });
    }
}