package com.kimjio.coral.nook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.kimjio.coral.api.NookLinkApi;
import com.kimjio.coral.data.Wrapper;
import com.kimjio.coral.data.nook.LandProfile;
import com.kimjio.coral.data.nook.Message;
import com.kimjio.coral.data.nook.Reaction;
import com.kimjio.coral.data.nook.Reactions;
import com.kimjio.coral.data.nook.ResponseStatus;
import com.kimjio.coral.data.nook.Token;
import com.kimjio.coral.data.nook.TokenRequest;
import com.kimjio.coral.data.nook.User;
import com.kimjio.coral.data.nook.UserProfile;
import com.kimjio.coral.util.RetrofitUtil;
import com.kimjio.coral.viewmodel.BaseViewModel;

import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public class NookViewModel extends BaseViewModel {
    private final NookLinkApi nookLinkApi;

    private final MutableLiveData<Response<Void>> cookieResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<Token> tokenLiveData = new MutableLiveData<>();
    private final MutableLiveData<ResponseStatus> messageResponseStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserProfile> userProfileLiveData = new MutableLiveData<>();
    private final MutableLiveData<LandProfile> landProfileLiveData = new MutableLiveData<>();
    private final MutableLiveData<Reactions> reactionsLiveData = new MutableLiveData<>();

    public NookViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
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

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void setUser(User user) {
        this.userLiveData.postValue(user);
    }

    public LiveData<Token> getToken() {
        return tokenLiveData;
    }

    public void loadToken(String webServiceToken, String userId) {
        disposable.add(getDisposable(nookLinkApi.getAuthToken(webServiceToken, new TokenRequest(userId)), tokenLiveData));
    }

    public LiveData<ResponseStatus> getMessageResponseStatus() {
        return messageResponseStatusLiveData;
    }

    public void sendMessage(String authorization, String message) {
        disposable.add(getDisposable(nookLinkApi.sendMessage(authorization, new Message("keyboard", message, null)), messageResponseStatusLiveData));
    }

    public void sendMessageFriend(String authorization, String message, String userId) {
        disposable.add(getDisposable(nookLinkApi.sendMessage(authorization, new Message("friend", message, userId)), messageResponseStatusLiveData));
    }

    public void sendMessageAll(String authorization, String message) {
        disposable.add(getDisposable(nookLinkApi.sendMessage(authorization, new Message("all_friend", message, null)), messageResponseStatusLiveData));
    }

    public void sendMessageReaction(String authorization, String message) {
        disposable.add(getDisposable(nookLinkApi.sendMessage(authorization, new Message("emoticon", message, null)), messageResponseStatusLiveData));

    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfileLiveData;
    }

    public void loadUserProfile(String authorization, String id) {
        disposable.add(getDisposable(nookLinkApi.getUserProfile(authorization, id, Locale.getDefault().toLanguageTag()), userProfileLiveData));
    }

    public LiveData<LandProfile> getLandProfile() {
        return landProfileLiveData;
    }

    public void loadLandProfile(String authorization, String id) {
        disposable.add(getDisposable(nookLinkApi.getLandProfile(authorization, id, Locale.getDefault().toLanguageTag()), landProfileLiveData));
    }

    public LiveData<Reactions> getReactions() {
        return reactionsLiveData;
    }

    public void loadReactions(String authorization) {
        disposable.add(getDisposable(nookLinkApi.getReactions(authorization, Locale.getDefault().toLanguageTag()), reactionsLiveData));
    }

    @Override
    protected <T> void onError(Observable<T> observable, Throwable e) {

    }

    @Override
    protected <T, W extends Wrapper<T>> void onErrorWrapper(Observable<W> observable, Throwable e) {

    }
}
