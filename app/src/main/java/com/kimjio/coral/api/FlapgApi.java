package com.kimjio.coral.api;

import com.kimjio.coral.data.auth.flapg.FToken;
import com.kimjio.coral.data.auth.flapg.FTokenWrapper;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface FlapgApi {

    String FLAPG = "https://flapg.com/ika2/";

    @GET("api/login?public")
    @Headers({"X-Ver: 3"})
    Observable<FTokenWrapper> getFToken(@Header("X-Token") String idToken, @Header("X-Time") String timestamp, @Header("X-Guid") String guid, @Header("X-Hash") String hash, @Header("X-IID") String iid);
}
