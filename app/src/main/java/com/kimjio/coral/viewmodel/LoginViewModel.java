package com.kimjio.coral.viewmodel;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.FlapgApi;
import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.api.NintendoApi;
import com.kimjio.coral.data.Wrapper;
import com.kimjio.coral.data.auth.LoginResult;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.Token;
import com.kimjio.coral.data.auth.flapg.FTokens;
import com.kimjio.coral.data.auth.request.LoginParam;
import com.kimjio.coral.data.auth.request.LoginParamWrapper;
import com.kimjio.coral.data.auth.request.TokenRequest;
import com.kimjio.coral.data.me.Me;
import com.kimjio.coral.util.RetrofitUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.kimjio.coral.api.FlapgApi.FLAPG;
import static com.kimjio.coral.api.NintendoAccountApi.AUTH_SCHEME;
import static com.kimjio.coral.api.NintendoAccountApi.CLIENT_ID;
import static com.kimjio.coral.api.NintendoAccountApi.NINTENDO_ACCOUNTS;
import static com.kimjio.coral.api.NintendoAccountApi.RESPONSE_TYPE;
import static com.kimjio.coral.api.NintendoAccountApi.SCOPE_STR;
import static com.kimjio.coral.api.NintendoAccountApi.TOKEN_GRANT_TYPE;
import static com.kimjio.coral.api.NintendoAccountApi.getAuthorization;
import static com.kimjio.coral.api.NintendoAccountApi.getUserAgent;
import static com.kimjio.coral.api.NintendoApi.NINTENDO;
import static com.kimjio.coral.api.NintendoApi.NSO_VERSION;
import static com.kimjio.coral.util.StringUtils.UTF_8;
import static com.kimjio.coral.util.StringUtils.getEncodedString;

public class LoginViewModel extends BaseViewModel {

    static {
        System.loadLibrary("hash");
    }

    private static final String TAG = "LoginViewModel";

    private NintendoApi nintendoApi = RetrofitUtil.getInstance(NINTENDO).create(NintendoApi.class);
    private NintendoAccountApi accountApi = RetrofitUtil.getInstance(NINTENDO_ACCOUNTS).create(NintendoAccountApi.class);
    private FlapgApi flapgApi = RetrofitUtil.getInstance(FLAPG).create(FlapgApi.class);

    private String state; //state
    private String sessionTokenCode;
    private String sessionTokenCodeChallenge; //stc:c

    private MutableLiveData<SessionToken> sessionTokenLiveData = new MutableLiveData<>();
    private MutableLiveData<Token> tokenLiveData = new MutableLiveData<>();
    private MutableLiveData<Me> meLiveData = new MutableLiveData<>();
    private MutableLiveData<FTokens> fTokensLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResultLiveData = new MutableLiveData<>();

    public Uri getLoginUri() {
        String rawUrl = NINTENDO_ACCOUNTS + "connect/1.0.0/authorize?state=%s&redirect_uri=%s&client_id=%s&scope=%s&response_type=%s&session_token_code_challenge=%s&session_token_code_challenge_method=S256&theme=login_form";
        state = getEncodedString(getRandom());
        sessionTokenCodeChallenge = getCrypto(state);
        String url = String.format(rawUrl, state, AUTH_SCHEME, CLIENT_ID, SCOPE_STR, RESPONSE_TYPE, sessionTokenCodeChallenge);
        //state 로 확인, challenge 로 추가 검증 (state 가 다르면 무시, challenge 가 다르면 로딩 후 통신 오류 발생 (Communication Error))
        return Uri.parse(url);
    }

    public LiveData<SessionToken> getSessionToken() {
        disposable.add(getDisposable(accountApi.getSessionToken(CLIENT_ID, sessionTokenCode, state), sessionTokenLiveData));
        return sessionTokenLiveData;
    }

    public LiveData<Token> getToken(String sessionToken) {
        disposable.add(getDisposable(accountApi.getToken(getUserAgent(), new TokenRequest(CLIENT_ID, TOKEN_GRANT_TYPE, sessionToken)), tokenLiveData));
        return tokenLiveData;
    }

    public LiveData<Me> getMe(String accessToken) {
        disposable.add(getDisposable(accountApi.getMe(getUserAgent(), getAuthorization(accessToken)), meLiveData));
        return meLiveData;
    }

    public LiveData<FTokens> getFTokens(String idToken, String timestamp, String hash) {
        disposable.add(getDisposable(flapgApi.getFTokens(idToken, timestamp, UUID.randomUUID().toString(), hash, getRandom()), fTokensLiveData));
        return fTokensLiveData;
    }

    public LiveData<LoginResult> login(LoginParamWrapper loginParamWrapper) {
        disposable.add(getWrapperDisposable(nintendoApi.login(getUserAgent(), NSO_VERSION, loginParamWrapper), loginResultLiveData));
        return loginResultLiveData;
    }

    public LiveData<LoginResult> login(String naBirthDay, String naCountry, String naIdToken, String uuid, long timestamp, String f) {
        disposable.add(getWrapperDisposable(nintendoApi.login(getUserAgent(), NSO_VERSION, new LoginParamWrapper(new LoginParam(naBirthDay, naCountry, naIdToken, uuid, timestamp, f))), loginResultLiveData));
        return loginResultLiveData;
    }

    private <T, D extends MutableLiveData<T>> Disposable getDisposable(Observable<T> observable, D liveData) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable.flatMap(Observable::error))
                .subscribeWith(getDisposableObserver(liveData));
    }

    private <T, D extends MutableLiveData<T>> DisposableObserver<T> getDisposableObserver(D liveData) {
        return new DisposableObserver<T>() {
            @Override
            public void onNext(T result) {
                liveData.postValue(result);
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private <T, W extends Wrapper<T>, D extends MutableLiveData<T>> Disposable getWrapperDisposable(Observable<W> observable, D liveData) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable.flatMap(Observable::error))
                .subscribeWith(getWrapperDisposableObserver(liveData));
    }

    private <T, W extends Wrapper<T>, D extends MutableLiveData<T>> DisposableObserver<W> getWrapperDisposableObserver(D liveData) {
        return new DisposableObserver<W>() {
            @Override
            public void onNext(W result) {
                liveData.postValue(result.getData());
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private String getCrypto(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return new String(Base64.encode(digest.digest(str.getBytes()), Base64.URL_SAFE | Base64.NO_PADDING), UTF_8).replace("\n", "");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    private String getRandom() {
        final int size = 50;
        Random random = new Random();
        final String text = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = text.charAt(random.nextInt(text.length()));
        }
        return new String(chars);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public boolean verifyToken(Uri responseRaw) {
        String response = responseRaw.toString();
        Log.d(TAG, "verifyToken: " + response);
        final String[] args = response.substring(AUTH_SCHEME.length()).replace('#', '\0').split("&");
        sessionTokenCode = args[1].split("=")[1];// response.getQueryParameter("");
        final String sessionTokenCodeVerifier = args[2].split("=")[1];
        final boolean isStateEquals = sessionTokenCodeVerifier.equals(state);
        try {
            JSONObject jwtPayload = new JSONObject(new String(Base64.decode(sessionTokenCode.split("\\.")[1], Base64.DEFAULT)));
            boolean isSTCCEquals = jwtPayload.getString("stc:c").equals(sessionTokenCodeChallenge);
            return isStateEquals && isSTCCEquals;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
