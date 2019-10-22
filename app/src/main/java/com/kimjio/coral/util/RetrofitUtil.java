package com.kimjio.coral.util;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static final Map<String, Retrofit> INSTANCES = new HashMap<>();

    public static Retrofit getInstance(String url) {
        Retrofit retrofit = INSTANCES.get(url);
        if (retrofit == null)
            synchronized (RetrofitUtil.class) {
                if (INSTANCES.get(url) == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.level(HttpLoggingInterceptor.Level.BODY);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .client(new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                            .build();
                    INSTANCES.put(url, retrofit);
                }
            }
        return retrofit;
    }
}
