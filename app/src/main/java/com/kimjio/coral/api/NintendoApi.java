package com.kimjio.coral.api;

import com.kimjio.coral.data.auth.LoginResultWrapper;
import com.kimjio.coral.data.auth.request.LoginParamWrapper;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NintendoApi {
    String NINTENDO = "https://api-lp1.znc.srv.nintendo.net/";
    String NSO_PACKAGE = "com.nintendo.znca";
    String NSO_VERSION = "1.5.2";


    @POST("v1/Account/Login")
    @Headers({
            "Content-Type: application/json",
            "X-Platform: Android"
    })
    Observable<LoginResultWrapper> login(@Header("User-Agent") String userAgent, @Header("X-ProductVersion") String version, @Body LoginParamWrapper loginParamWrapper);

    static String getUserAgent() {
        return NSO_PACKAGE + "/" + NSO_VERSION;
    }
}
