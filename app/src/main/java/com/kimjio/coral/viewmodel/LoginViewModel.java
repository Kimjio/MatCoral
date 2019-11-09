package com.kimjio.coral.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.FlapgApi;
import com.kimjio.coral.api.NintendoAccountApi;
import com.kimjio.coral.api.NintendoApi;
import com.kimjio.coral.data.auth.TokenResponse;
import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.Token;
import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.auth.request.TokenRequest;
import com.kimjio.coral.data.auth.request.TokenRequestWrapper;
import com.kimjio.coral.data.auth.request.AITokenRequest;
import com.kimjio.coral.data.me.Me;
import com.kimjio.coral.util.RetrofitUtil;
import com.kimjio.hash.HashTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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
    protected NintendoApi nintendoApi = RetrofitUtil.getInstance(NINTENDO).create(NintendoApi.class);
    protected NintendoAccountApi accountApi = RetrofitUtil.getInstance(NINTENDO_ACCOUNTS).create(NintendoAccountApi.class);
    protected FlapgApi flapgApi = RetrofitUtil.getInstance(FLAPG).create(FlapgApi.class);

    private String state; //state
    private String sessionTokenCode;
    private String sessionTokenCodeChallenge; //stc:c

    private MutableLiveData<SessionToken> sessionTokenLiveData = new MutableLiveData<>();
    protected MutableLiveData<Token> tokenLiveData = new MutableLiveData<>();
    protected MutableLiveData<Me> meLiveData = new MutableLiveData<>();
    protected MutableLiveData<Map<String, FToken>> fTokensLiveData = new MutableLiveData<>();
    protected MutableLiveData<TokenResponse> tokenResponseLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
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
        disposable.add(getDisposable(accountApi.getSessionToken(CLIENT_ID, sessionTokenCode, state), sessionTokenLiveData));
        return sessionTokenLiveData;
    }

    public LiveData<Token> getToken(String sessionToken) {
        disposable.add(getDisposable(accountApi.getToken(getUserAgent(), new AITokenRequest(CLIENT_ID, TOKEN_GRANT_TYPE, sessionToken)), tokenLiveData));
        return tokenLiveData;
    }

    public LiveData<Me> getMe(String accessToken) {
        disposable.add(getDisposable(accountApi.getMe(getUserAgent(), getAuthorization(accessToken)), meLiveData));
        return meLiveData;
    }

    public LiveData<Map<String, FToken>> getFTokens(String idToken) {
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        disposable.add(getDisposable(flapgApi.getFTokens(idToken, timestamp, UUID.randomUUID().toString(), HashTool.getHash(idToken, timestamp), getRandom()), fTokensLiveData));
        return fTokensLiveData;
    }

    public LiveData<TokenResponse> login(Me me, FToken f) {
        disposable.add(getWrapperDisposable(nintendoApi.login(getUserAgent(), NSO_VERSION, new TokenRequestWrapper(new TokenRequest(me.getBirthday(), me.getCountry(), f.getToken(), f.getUUID(), Long.parseLong(f.getTimestamp()), f.getF()))), tokenResponseLiveData));
        return tokenResponseLiveData;
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
}
