package com.kimjio.coral.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ACNewHorizonsApi {
    String AC_NEW_HORIZONS = "https://web.sd.lp1.acbaa.srv.nintendo.net/";

    @GET(".")
    @Headers({
            "Accept: text/html, application/xhtml+xml, application/xml"
    })
    Observable<Response<Void>> getCookie(@Header("X-GameWebToken") String webServiceToken);

}
