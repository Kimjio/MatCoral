package com.kimjio.coral.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.data.GameWebService;
import com.kimjio.coral.data.auth.TokenResponse;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.auth.request.TokenRequest;
import com.kimjio.coral.data.auth.request.TokenRequestWrapper;
import com.kimjio.coral.data.auth.request.WebServiceTokenRequest;
import com.kimjio.coral.data.auth.request.WebServiceTokenRequestWrapper;
import com.kimjio.coral.manager.TokenManager;

import java.util.List;

import static com.kimjio.coral.api.NintendoAccountApi.getAuthorization;
import static com.kimjio.coral.api.NintendoApi.NSO_VERSION;
import static com.kimjio.coral.api.NintendoApi.getUserAgent;

public class MainViewModel extends LoginViewModel {
    private MutableLiveData<List<GameWebService>> gameWebServicesLiveData = new MutableLiveData<>();
    private MutableLiveData<WebServiceToken> webServiceTokenLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TokenResponse> getToken() {
        return tokenResponseLiveData;
    }

    public void loadToken(FToken f) {
        disposable.add(
                getWrapperDisposable(
                        nintendoApi.getToken(getUserAgent(), NSO_VERSION,
                                NintendoAccountApi.getAuthorization(TokenManager.getInstance().getWebApiServerCredential().getAccessToken()),
                                new TokenRequestWrapper(
                                        new TokenRequest(f.getToken(), f.getUUID(), Long.parseLong(f.getTimestamp()), f.getF()))),
                        tokenResponseLiveData));
    }

    public LiveData<List<GameWebService>> getGameWebServices() {
        return gameWebServicesLiveData;
    }

    public void loadGameWebServices() {
        disposable.add(
                getWrapperDisposable(
                        nintendoApi.getGameWebServices(getUserAgent(), NSO_VERSION,
                                getAuthorization(TokenManager.getInstance().getWebApiServerCredential().getAccessToken())),
                        gameWebServicesLiveData));
    }

    public LiveData<WebServiceToken> getWebServiceToken() {
        return webServiceTokenLiveData;
    }

    public void loadWebServiceToken(long id) {
        disposable.add(
                getWrapperDisposable(
                        nintendoApi.getWebServiceToken(getUserAgent(), NSO_VERSION,
                                getAuthorization(TokenManager.getInstance().getWebApiServerCredential().getAccessToken()),
                                new WebServiceTokenRequestWrapper(
                                        new WebServiceTokenRequest(id,
                                                TokenManager.getInstance().getWebAppToken().getToken(),
                                                TokenManager.getInstance().getWebAppToken().getUUID(),
                                                Long.parseLong(TokenManager.getInstance().getWebAppToken().getTimestamp()),
                                                TokenManager.getInstance().getWebAppToken().getF()))),
                        webServiceTokenLiveData));
    }
}
