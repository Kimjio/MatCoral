package com.kimjio.coral.api;


import android.text.TextUtils;

import com.kimjio.coral.data.auth.SessionToken;
import com.kimjio.coral.data.auth.Token;
import com.kimjio.coral.data.auth.request.AITokenRequest;
import com.kimjio.coral.data.me.Me;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.kimjio.coral.api.NintendoApi.NSO_VERSION;
import static com.kimjio.coral.util.StringUtils.getEncodedString;

public interface NintendoAccountApi {

    String NINTENDO_ACCOUNTS = "https://accounts.nintendo.com/";
    String CLIENT_ID = "71b963c1b7b6d119";
    String[] SCOPE = new String[] {"openid", "user", "user.birthday", "user.mii", "user.screenName"};
    String AUTH_SCHEME = getEncodedString("npf71b963c1b7b6d119") + "://auth";
    String SCOPE_STR = getEncodedString(TextUtils.join(" ", SCOPE));
    String RESPONSE_TYPE = getEncodedString("session_token_code");
    String TOKEN_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer-session-token";

    @POST("connect/1.0.0/api/session_token")
    @FormUrlEncoded
    Observable<SessionToken> getSessionToken(@Field("client_id") String clientId,
                                             @Field("session_token_code") String sessionTokenCode,
                                             @Field("session_token_code_verifier") String sessionTokenCodeVerifier);

    @POST("connect/1.0.0/api/token")
    @Headers({"Content-Type: application/json"})
    Observable<Token> getToken(@Header("User-Agent") String userAgent, @Body AITokenRequest request);

    @GET("https://api.accounts.nintendo.com/2.0.0/users/me")
    Observable<Me> getMe(@Header("User-Agent") String userAgent, @Header("Authorization") String authorization);

    static String getAuthorization(String accessToken) {
        return String.format("Bearer %s",accessToken);
    }

    static String getUserAgent() {
        return "OnlineLounge/" + NSO_VERSION + " NASDKAPI Android";
    }
}
