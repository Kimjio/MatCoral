package com.kimjio.coral.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
                                    .addInterceptor(loggingInterceptor)
                                    .build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                            .build();
                    INSTANCES.put(url, retrofit);
                }
            }
        return retrofit;
    }

    private abstract class CookiesInterceptor implements Interceptor {

        protected static final String PREF_COOKIES = "pref_cookies";
        private static final String PREF_COOKIE = "pref_cookie";
        protected SharedPreferences preferences;

        public CookiesInterceptor(Context context) {
            preferences = context.getApplicationContext().getSharedPreferences(PREF_COOKIE, Context.MODE_PRIVATE);
        }

        @NotNull
        @Override
        public abstract Response intercept(@NotNull Chain chain) throws IOException;
    }

    private class AddCookiesInterceptor extends CookiesInterceptor {
        public AddCookiesInterceptor(Context context) {
            super(context);
        }

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            Set<String> cookieSet = preferences.getStringSet(PREF_COOKIES, new HashSet<>());

            String cookies = TextUtils.join("; ", cookieSet.toArray());

            builder.addHeader("Cookie", cookies);

            return chain.proceed(builder.build());
        }
    }
}
