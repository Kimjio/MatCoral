package com.kimjio.coral.api;

import android.os.Build;

import com.kimjio.coral.data.GameWebServices;
import com.kimjio.coral.data.auth.TokenResponseWrapper;
import com.kimjio.coral.data.auth.WebServiceTokenWrapper;
import com.kimjio.coral.data.auth.request.TokenRequestWrapper;
import com.kimjio.coral.data.auth.request.WebServiceTokenRequestWrapper;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NintendoApi {
    String NINTENDO = "https://api-lp1.znc.srv.nintendo.net/";
    String NSO_PACKAGE = "com.nintendo.znca";
    String NSO_VERSION = "1.8.0";

    static String getUserAgent() {
        return NSO_PACKAGE + "/" + NSO_VERSION + " (Android/" + Build.VERSION.RELEASE + ")";
    }

    static String getAuthorization(String accessToken) {
        return String.format("Bearer %s",accessToken);
    }

    @POST("v1/Account/Login")
    @Headers({
            "Content-Type: application/json",
            "X-Platform: Android",
    })
    Observable<TokenResponseWrapper> login(@Header("User-Agent") String userAgent, @Header("X-ProductVersion") String version, @Body TokenRequestWrapper tokenRequestWrapper);

    @POST("v1/Account/GetToken")
    @Headers({
            "Content-Type: application/json",
            "X-Platform: Android"
    })
    Observable<TokenResponseWrapper> getToken(@Header("User-Agent") String userAgent, @Header("X-ProductVersion") String version, @Header("Authorization") String authorization, @Body TokenRequestWrapper tokenRequestWrapper);

    @POST("v1/Game/ListWebServices")
    @Headers({
            "Content-Type: application/json",
            "X-Platform: Android"
    })
    Observable<GameWebServices> getGameWebServices(@Header("User-Agent") String userAgent, @Header("X-ProductVersion") String version, @Header("Authorization") String authorization);

    @POST("v2/Game/GetWebServiceToken")
    @Headers({
            "Content-Type: application/json",
            "X-Platform: Android"
    })
    Observable<WebServiceTokenWrapper> getWebServiceToken(@Header("User-Agent") String userAgent, @Header("X-ProductVersion") String version, @Header("Authorization") String authorization, @Body WebServiceTokenRequestWrapper webServiceTokenRequestWrapper);
}
