package com.kimjio.coral.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.FlapgApi;
import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.api.NintendoApi;
import com.kimjio.coral.data.GameWebService;
import com.kimjio.coral.data.GameWebServices;
import com.kimjio.coral.data.auth.TokenResponse;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.auth.request.TokenRequest;
import com.kimjio.coral.data.auth.request.TokenRequestWrapper;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.util.RetrofitUtil;

import java.util.List;

import static com.kimjio.coral.api.FlapgApi.FLAPG;
import static com.kimjio.coral.api.NintendoAccountApi.NINTENDO_ACCOUNTS;
import static com.kimjio.coral.api.NintendoAccountApi.getAuthorization;
import static com.kimjio.coral.api.NintendoAccountApi.getUserAgent;
import static com.kimjio.coral.api.NintendoApi.NINTENDO;
import static com.kimjio.coral.api.NintendoApi.NSO_VERSION;

public class MainViewModel extends LoginViewModel {
    private MutableLiveData<List<GameWebService>> gameWebServicesLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TokenResponse> getToken(FToken f) {
        disposable.add(getWrapperDisposable(nintendoApi.getToken(getUserAgent(), NSO_VERSION, NintendoAccountApi.getAuthorization(TokenManager.getInstance().getWebApiServerCredential().getAccessToken()), new TokenRequestWrapper(new TokenRequest(f.getToken(), f.getUUID(), Long.parseLong(f.getTimestamp()), f.getF()))), tokenResponseLiveData));
        return tokenResponseLiveData;
    }

    public LiveData<List<GameWebService>> getGameWebServicesData() {
        disposable.add(getWrapperDisposable(nintendoApi.getGameWebServices(getUserAgent(), NSO_VERSION, getAuthorization(TokenManager.getInstance().getWebApiServerCredential().getAccessToken())), gameWebServicesLiveData));
        return gameWebServicesLiveData;
    }
}
