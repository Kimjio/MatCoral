package com.kimjio.coral.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;


import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitUtil {

    private static final Map<String, Retrofit> INSTANCES = new WeakHashMap<>();

    private RetrofitUtil() {
    }

    public static Retrofit getInstance(String url) {
        Retrofit retrofit = INSTANCES.get(url);
        if (retrofit == null)
            synchronized (RetrofitUtil.class) {
                if (INSTANCES.get(url) == null) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .client(new OkHttpClient.Builder()
                                    .addInterceptor(new DNTInterceptor())
                                    .addInterceptor(loggingInterceptor)
                                    .build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .build();
                    INSTANCES.put(url, retrofit);
                }
            }
        return retrofit;
    }

    public static Retrofit getInstance(Context context, String url) {
        Retrofit retrofit = INSTANCES.get(url);
        if (retrofit == null)
            synchronized (RetrofitUtil.class) {
                if (INSTANCES.get(url) == null) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .client(new OkHttpClient.Builder()
                                    .addInterceptor(new DNTInterceptor())
                                    .addInterceptor(loggingInterceptor)
                                    .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))
                                    .build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .build();
                    INSTANCES.put(url, retrofit);
                }
            }
        return retrofit;
    }

    private static class DNTInterceptor implements Interceptor {

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            return chain.proceed(chain.request().newBuilder().addHeader("DNT", "1").build());
        }
    }
}
