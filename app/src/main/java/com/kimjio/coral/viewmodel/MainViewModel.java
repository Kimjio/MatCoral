package com.kimjio.coral.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.data.GameWebService;
import com.kimjio.coral.data.auth.TokenResponse;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.data.auth.WebServiceTokenWrapper;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.auth.request.TokenRequest;
import com.kimjio.coral.data.auth.request.TokenRequestWrapper;
import com.kimjio.coral.data.auth.request.WebServiceTokenRequest;
import com.kimjio.coral.data.auth.request.WebServiceTokenRequestWrapper;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.recycler.OnItemClickListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.kimjio.coral.api.NintendoAccountApi.getAuthorization;
import static com.kimjio.coral.api.NintendoApi.NSO_VERSION;
import static com.kimjio.coral.api.NintendoApi.getUserAgent;

public class MainViewModel extends LoginViewModel {
    private MutableLiveData<List<GameWebService>> gameWebServicesLiveData = new MutableLiveData<>();
    private OnItemClickListener<WebServiceToken> tokenListener;

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

    public void setTokenListener(OnItemClickListener<WebServiceToken> tokenListener) {
        this.tokenListener = tokenListener;
    }

    public void loadWebServiceToken(long id, int position) {
        disposable.add(
                nintendoApi.getWebServiceToken(getUserAgent(), NSO_VERSION,
                        getAuthorization(TokenManager.getInstance().getWebApiServerCredential().getAccessToken()),
                        new WebServiceTokenRequestWrapper(
                                new WebServiceTokenRequest(id,
                                        TokenManager.getInstance().getWebAppToken().getToken(),
                                        TokenManager.getInstance().getWebAppToken().getUUID(),
                                        Long.parseLong(TokenManager.getInstance().getWebAppToken().getTimestamp()),
                                        TokenManager.getInstance().getWebAppToken().getF()))).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(throwableObservable -> throwableObservable.flatMap(Observable::error))
                        .subscribeWith(new DisposableObserver<WebServiceTokenWrapper>() {
                            @Override
                            public void onNext(WebServiceTokenWrapper result) {
                                WebServiceToken token = result.getData();
                                token.setId(id);
                                tokenListener.onItemClick(token, position);
                            }

                            @Override
                            public void onError(Throwable e) {
                                throwableLiveData.setValue(e);
                            }

                            @Override
                            public void onComplete() {
                            }
                        })
        );
    }
}
