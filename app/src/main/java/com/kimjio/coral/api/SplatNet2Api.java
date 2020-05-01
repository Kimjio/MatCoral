package com.kimjio.coral.api;

import com.kimjio.coral.data.splat.Festivals;
import com.kimjio.coral.data.splat.FullRecords;
import com.kimjio.coral.data.splat.NicknameIcons;
import com.kimjio.coral.data.splat.Stages;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SplatNet2Api {
    String SPLAT_NET_2 = "https://app.splatoon2.nintendo.net/";
    // https://app.splatoon2.nintendo.net/introduction

    @GET(".")
    @Headers({
            "Accept: text/html, application/xhtml+xml, application/xml"
    })
    Observable<Response<Void>> getCookie(@Header("X-GameWebToken") String webServiceToken);

    @GET("api/records")
    Observable<FullRecords> getFullRecords();

    @GET("api/data/stages")
    Observable<Stages> getStages();

    @GET("api/festivals/active")
    Observable<Festivals> getActiveFestivals();

    @GET("api/nickname_and_icon")
    Observable<NicknameIcons> getNicknameIcon(@Query("id") String id);
}
