package com.kimjio.coral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.R;
import com.kimjio.coral.api.NintendoException;
import com.kimjio.coral.data.GameWebService;
import com.kimjio.coral.databinding.MainActivityBinding;
import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.manager.UserManager;
import com.kimjio.coral.nook.activity.NookActivity;
import com.kimjio.coral.recycler.GameWebServiceAdapter;
import com.kimjio.coral.splat.activity.SplatActivity;
import com.kimjio.coral.viewmodel.MainViewModel;

import java.util.Objects;

public class MainActivity extends BaseActivity<MainActivityBinding> {
    private static MainViewModel viewModel;
    private CauseBy causeBy;
    private LastClicked lastClicked;

    private GameWebServiceAdapter adapter = new GameWebServiceAdapter((item, position) -> {
        if (item.getId() == GameWebService.ID_SPLAT2) {
            lastClicked = new LastClicked(item.getId(), position);
            viewModel.loadWebServiceToken(item.getId(), position);
        } else if (item.getId() == GameWebService.ID_SMASH) {
        } else if (item.getId() == GameWebService.ID_AC_NEW_HORIZON) {
            lastClicked = new LastClicked(item.getId(), position);
            viewModel.loadWebServiceToken(item.getId(), position);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        binding.list.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        observeData();
    }

    @Override
    protected void observeData() {
        viewModel.getThrowable().observe(this, throwable -> {
            if (throwable instanceof NintendoException) {
                handleNintendoException((NintendoException) throwable);
            } else {
                throwable.printStackTrace();
            }
        });
        viewModel.getMe().observe(this, me ->
                UserManager.getInstance().setAccountUser(me)
        );
        viewModel.getFTokenNSO().observe(this, fToken -> {
            TokenManager.getInstance().setNsoToken(fToken);
            viewModel.loadToken(Objects.requireNonNull(fToken));
        });
        viewModel.getFTokenAPP().observe(this, fToken -> {
            TokenManager.getInstance().setWebAppToken(fToken);
            if (causeBy != null && (causeBy == CauseBy.INVALIDATE || causeBy == CauseBy.EXPIRED)) {
                if (lastClicked != null) {
                    viewModel.loadWebServiceToken(lastClicked.id, lastClicked.position);
                }
                causeBy = null;
                lastClicked = null;
            }
        });
        viewModel.getToken().observe(this, response -> {
            TokenManager.getInstance()
                    .setWebApiServerCredential(response.getWebApiServerCredential());
            UserManager.getInstance().setUser(response.getUser());
            viewModel.loadFTokenAPP(TokenManager.getInstance().getWebApiServerCredential().getAccessToken());
            viewModel.loadGameWebServices();
        });
        viewModel.getAccountToken().observe(this, token -> {
            viewModel.loadMe(token.getAccessToken());
            viewModel.loadFTokenNSO(token.getIdToken());
        });
        viewModel.getGameWebServices().observe(this, gameWebServices ->
                adapter.setGameWebServices(gameWebServices));
        viewModel.setTokenListener((webServiceToken, position) -> {
            if (webServiceToken.getId() == GameWebService.ID_SPLAT2) {
                startActivity(new Intent(MainActivity.this, SplatActivity.class).putExtra("web_service_token", webServiceToken));
            } else if (webServiceToken.getId() == GameWebService.ID_SMASH) {

            } else if (webServiceToken.getId() == GameWebService.ID_AC_NEW_HORIZON) {
                startActivity(new Intent(MainActivity.this, NookActivity.class).putExtra("web_service_token", webServiceToken));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TokenManager.getInstance().expired()) {
            if (TokenManager.getInstance().getWebApiServerCredential() == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }
            getToken(CauseBy.EXPIRED);
        } else {
            viewModel.loadGameWebServices();
        }
    }

    private void getToken(CauseBy causeBy) {
        this.causeBy = causeBy;
        viewModel.loadAccountToken(SessionTokenManager.getInstance(this).loadSessionToken().getSessionToken());
    }

    private void handleNintendoException(NintendoException e) {
        int status = e.getStatus();
        switch (status) {
            case NintendoException.ERROR_INVALID_TOKEN:
                getToken(CauseBy.INVALIDATE);
                break;
            case NintendoException.ERROR_EXPIRED:
                getToken(CauseBy.EXPIRED);
                break;
        }
    }

    private enum CauseBy {
        EXPIRED,
        INVALIDATE
    }

    private static class LastClicked{
        long id;
        int position;

        public LastClicked(long id, int position) {
            this.id = id;
            this.position = position;
        }

        public long getId() {
            return id;
        }

        public int getPosition() {
            return position;
        }
    }
}