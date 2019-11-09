package com.kimjio.coral.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.collection.ArraySet;

import com.kimjio.coral.data.auth.SessionCookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class SessionCookieManager {
    private static final String PREF_SESSION_COOKIE = "pref_session_cookie";
    private static final String PREF_COOKIES = "pref_cookies";

    private static SessionCookieManager INSTANCE;

    private SharedPreferences preferences;

    private SessionCookieManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREF_SESSION_COOKIE, Context.MODE_PRIVATE);
    }

    public static SessionCookieManager getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (SessionCookieManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new SessionCookieManager(context);
            }
        return INSTANCE;
    }

    public void saveCookie(SessionCookie cookie) {
        Set<String> cookieSet = preferences.getStringSet(PREF_COOKIES, new ArraySet<>());
        cookieSet.add(TextUtils.concat(cookie.toString()).toString());
        preferences.edit().putStringSet(PREF_COOKIES, cookieSet).apply();
    }

    public List<SessionCookie> getCookies() {
        Set<String> cookieSet = preferences.getStringSet(PREF_COOKIES, new ArraySet<>());
        List<SessionCookie> cookieList = new ArrayList<>();
        for (String cookie : cookieSet) {
            cookieList.add(new SessionCookie(cookie));
        }
        return cookieList;
    }
}
