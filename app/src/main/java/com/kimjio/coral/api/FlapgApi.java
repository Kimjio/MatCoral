package com.kimjio.coral.api;

import com.kimjio.coral.data.auth.flapg.FToken;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface FlapgApi {

    String FLAPG = "https://flapg.com/ika2/";

    @GET("api/login")
    @Headers({"X-Ver: 2"})
    Observable<Map<String, FToken>> getFTokens(@Header("X-Token") String idToken, @Header("X-Time") String timestamp, @Header("X-Guid") String guid, @Header("X-Hash") String hash, @Header("X-IID") String iid);
}
