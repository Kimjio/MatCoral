package com.kimjio.coral.nook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.NookLinkApi;
import com.kimjio.coral.data.nook.Message;
import com.kimjio.coral.data.nook.ResponseStatus;
import com.kimjio.coral.data.nook.Token;
import com.kimjio.coral.data.nook.TokenRequest;
import com.kimjio.coral.data.nook.User;
import com.kimjio.coral.util.RetrofitUtil;
import com.kimjio.coral.viewmodel.BaseViewModel;

import java.util.List;

import retrofit2.Response;

public class NookViewModel extends BaseViewModel {
    private final NookLinkApi nookLinkApi;

    private MutableLiveData<Response<Void>> cookieResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<Token> tokenLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseStatus> messageResponseStatusLiveData = new MutableLiveData<>();

    public NookViewModel(@NonNull Application application) {
        super(application);
        nookLinkApi = RetrofitUtil.getInstance(application.getApplicationContext(), NookLinkApi.NOOK_LINK).create(NookLinkApi.class);
    }

    public LiveData<Response<Void>> getCookieResponseData() {
        return cookieResponseLiveData;
    }

    public void loadCookieResponseData(String webServiceToken) {
        disposable.add(getDisposable(nookLinkApi.getCookie(webServiceToken), cookieResponseLiveData));
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void loadUsers() {
        disposable.add(getWrapperDisposable(nookLinkApi.getUsers(), usersLiveData));
    }

    public LiveData<Token> getToken() {
        return tokenLiveData;
    }

    public void loadToken(String userId) {
        disposable.add(getDisposable(nookLinkApi.getAuthToken(new TokenRequest(userId)), tokenLiveData));
    }

    public LiveData<ResponseStatus> getMessageResponseStatus() {
        return messageResponseStatusLiveData;
    }

    public void sendMessage(String authorization, String message) {
        disposable.add(getDisposable(nookLinkApi.sendMessage(authorization, new Message(message)), messageResponseStatusLiveData));
    }
}
