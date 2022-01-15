package com.kimjio.coral.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.kimjio.coral.R;
import com.kimjio.coral.api.FlapgApi;
import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.api.NintendoApi;
import com.kimjio.coral.data.Wrapper;
import com.kimjio.coral.data.auth.TokenResponse;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.Token;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.auth.request.TokenRequest;
import com.kimjio.coral.data.auth.request.TokenRequestWrapper;
import com.kimjio.coral.data.auth.request.AccountTokenRequest;
import com.kimjio.coral.data.me.Me;
import com.kimjio.coral.util.RetrofitUtil;
import com.kimjio.hash.HashTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import static com.kimjio.coral.api.FlapgApi.FLAPG;
import static com.kimjio.coral.api.NintendoAccountApi.AUTH_SCHEME;
import static com.kimjio.coral.api.NintendoAccountApi.CLIENT_ID;
import static com.kimjio.coral.api.NintendoAccountApi.NINTENDO_ACCOUNTS;
import static com.kimjio.coral.api.NintendoAccountApi.RESPONSE_TYPE;
import static com.kimjio.coral.api.NintendoAccountApi.SCOPE_STR;
import static com.kimjio.coral.api.NintendoAccountApi.TOKEN_GRANT_TYPE;
import static com.kimjio.coral.api.NintendoApi.getAuthorization;
import static com.kimjio.coral.api.NintendoAccountApi.getUserAgent;
import static com.kimjio.coral.api.NintendoApi.NINTENDO;
import static com.kimjio.coral.api.NintendoApi.NSO_VERSION;
import static com.kimjio.coral.util.StringUtils.UTF_8;
import static com.kimjio.coral.util.StringUtils.getEncodedString;

import io.reactivex.rxjava3.core.Observable;

public class LoginViewModel extends BaseViewModel {
    protected final NintendoApi nintendoApi = RetrofitUtil.getInstance(NINTENDO).create(NintendoApi.class);
    protected final NintendoAccountApi accountApi = RetrofitUtil.getInstance(NINTENDO_ACCOUNTS).create(NintendoAccountApi.class);
    protected final FlapgApi flapgApi = RetrofitUtil.getInstance(FLAPG).create(FlapgApi.class);

    private String state; //state
    private String sessionTokenCode;
    private String sessionTokenCodeChallenge; //stc:c

    private Me me;
    private FToken f;

    private OnProgressChangedListener progressChangedListener;
    private int step = 1;

    private MutableLiveData<SessionToken> sessionTokenLiveData = new MutableLiveData<>();
    protected MutableLiveData<Token> tokenLiveData = new MutableLiveData<>();
    protected MutableLiveData<Me> meLiveData = new MutableLiveData<>();
    protected MutableLiveData<FToken> fTokenNSOLiveData = new MutableLiveData<>();
    protected MutableLiveData<FToken> fTokenAPPLiveData = new MutableLiveData<>();
    protected MutableLiveData<TokenResponse> tokenResponseLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    @Override
    protected <T> void onError(Observable<T> observable, Throwable e) {

    }

    @Override
    protected <T, W extends Wrapper<T>> void onErrorWrapper(Observable<W> observable, Throwable e) {

    }

    public Uri getLoginUri() {
        String rawUrl = NINTENDO_ACCOUNTS + "connect/1.0.0/authorize?state=%s&redirect_uri=%s&client_id=%s&scope=%s&response_type=%s&session_token_code_challenge=%s&session_token_code_challenge_method=S256&theme=login_form";
        state = getEncodedString(getRandom());
        sessionTokenCodeChallenge = getCrypto(state);
        String url = String.format(rawUrl, state, AUTH_SCHEME, CLIENT_ID, SCOPE_STR, RESPONSE_TYPE, sessionTokenCodeChallenge);
        //state 로 확인, challenge 로 추가 검증 (state 가 다르면 무시, challenge 가 다르면 로딩 후 통신 오류 발생 (Communication Error))
        return Uri.parse(url);
    }

    public LiveData<SessionToken> getSessionToken() {
        return sessionTokenLiveData;
    }

    public void loadSessionToken() {
        if (progressChangedListener != null) progressChangedListener.onProgressChanged(R.string.progress_issue_token, step++);
        disposable.add(getDisposable(accountApi.getSessionToken(CLIENT_ID, sessionTokenCode, state), sessionTokenLiveData));
    }

    public LiveData<Token> getAccountToken() {
        return tokenLiveData;
    }

    public void loadAccountToken(String sessionToken) {
        if (progressChangedListener != null) progressChangedListener.onProgressChanged(R.string.progress_issue_token, step++);
        disposable.add(getDisposable(accountApi.getAccountToken(getUserAgent(), new AccountTokenRequest(CLIENT_ID, TOKEN_GRANT_TYPE, sessionToken)), tokenLiveData));
    }

    public LiveData<Me> getMe() {
        return meLiveData;
    }

    public void loadMe(String accessToken) {
        if (progressChangedListener != null) progressChangedListener.onProgressChanged(R.string.progress_query_user, step);
        disposable.add(getDisposable(accountApi.getMe(getUserAgent(), getAuthorization(accessToken)), meLiveData));
    }

    public LiveData<FToken> getFTokenNSO() {
        return fTokenNSOLiveData;
    }


    public LiveData<FToken> getFTokenAPP() {
        return fTokenAPPLiveData;
    }

    public void loadFTokenNSO(String idToken) {
        if (progressChangedListener != null) progressChangedListener.onProgressChanged(R.string.progress_issue_token, step++);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        disposable.add(
                getWrapperDisposable(
                        flapgApi.getFToken(idToken, timestamp,
                                UUID.randomUUID().toString(),
                                HashTool.getHash(idToken, timestamp),
                                FToken.NSO),
                        fTokenNSOLiveData));
    }

    public void loadFTokenAPP(String idToken) {
        if (progressChangedListener != null) progressChangedListener.onProgressChanged(R.string.progress_issue_token, step++);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        disposable.add(
                getWrapperDisposable(
                        flapgApi.getFToken(idToken, timestamp,
                                UUID.randomUUID().toString(),
                                HashTool.getHash(idToken, timestamp),
                                FToken.APP),
                        fTokenAPPLiveData));
    }

    public LiveData<TokenResponse> getTokenResponse() {
        return tokenResponseLiveData;
    }

    public void setProgressChangedListener(OnProgressChangedListener progressChangedListener) {
        this.progressChangedListener = progressChangedListener;
    }

    public void login(Me me, FToken f) {
        if (me != null) this.me = me;
        if (f != null) this.f = f;
        synchronized (LoginViewModel.class) {
            if (this.me != null && this.f != null) {
                if (progressChangedListener != null) progressChangedListener.onProgressChanged(R.string.progress_sign_in, -1);
                disposable.add(getWrapperDisposable(nintendoApi.login(getUserAgent(), NSO_VERSION,
                        new TokenRequestWrapper(
                                new TokenRequest(this.me.getBirthday(), this.me.getCountry(), this.f.getToken(), this.f.getUUID(), Long.parseLong(this.f.getTimestamp()), this.f.getF())
                        )),
                        tokenResponseLiveData));
            }
        }
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

    public boolean verifyToken(Uri response) {
        response = Uri.parse(response.toString().replace("auth#","auth?"));
        sessionTokenCode = response.getQueryParameter("session_token_code");
        final String sessionTokenCodeVerifier = response.getQueryParameter("state");
        final boolean isStateEquals = sessionTokenCodeVerifier != null && sessionTokenCodeVerifier.equals(state);
        try {
            JSONObject jwtPayload = new JSONObject(new String(Base64.decode(sessionTokenCode.split("\\.")[1], Base64.DEFAULT)));
            boolean isSTCCEquals = jwtPayload.getString("stc:c").equals(sessionTokenCodeChallenge);
            return isStateEquals && isSTCCEquals;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(@StringRes int text, int step);
    }
}
