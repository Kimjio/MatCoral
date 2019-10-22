package com.kimjio.coral.api;


import com.kimjio.coral.data.auth.SessionToken;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NintendoAccountApi {

    String NINTENDO_ACCOUNTS = "https://accounts.nintendo.com/";
    String NINTENDO_SWITCH_ONLINE_VERSION = "1.5.2";

    @POST("connect/1.0.0/api/session_token")
    @FormUrlEncoded
    Observable<SessionToken> getSessionToken(@Field("client_id") String clientId,
                                             @Field("session_token_code") String sessionTokenCode,
                                             @Field("session_token_code_verifier") String sessionTokenCodeVerifier);


    default String getUserAgent() {
        return "OnlineLounge/" + NINTENDO_SWITCH_ONLINE_VERSION + " NASDKAPI Android";
    }

}
