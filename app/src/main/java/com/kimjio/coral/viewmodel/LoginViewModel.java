package com.kimjio.coral.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.Constants;
import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.util.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.kimjio.coral.api.NintendoAccountApi.NINTENDO_ACCOUNTS;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";

    private MutableLiveData<SessionToken> sessionTokenLiveData = new MutableLiveData<>();

    public LiveData<SessionToken> getSessionToken(String clientId, String sessionTokenCode, String sessionTokenCodeVerifier) {
        NintendoAccountApi accountApi = RetrofitUtil.getInstance(NINTENDO_ACCOUNTS).create(NintendoAccountApi.class);
        disposable.add(accountApi.getSessionToken(clientId, sessionTokenCode, sessionTokenCodeVerifier).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).retryWhen(throwableObservable -> throwableObservable.flatMap(Observable::error))
                .subscribeWith(new DisposableObserver<SessionToken>() {
                    @Override
                    public void onNext(SessionToken sessionToken) {
                        sessionTokenLiveData.postValue(sessionToken);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        return sessionTokenLiveData;
    }
}
