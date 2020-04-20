package com.kimjio.coral.manager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;

import com.kimjio.coral.data.auth.SessionCookie;

import java.util.Map;
import java.util.Set;

@Deprecated
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
        for (String s : cookieSet) {
            if (s.contains(cookie.getKey()))
                cookieSet.remove(s);
        }
        cookieSet.add(cookie.toString());
        preferences.edit().putStringSet(PREF_COOKIES, cookieSet).apply();
    }

    public Map<String, SessionCookie> getCookies() {
        Set<String> cookieSet = preferences.getStringSet(PREF_COOKIES, new ArraySet<>());
        ArrayMap<String, SessionCookie> cookieMap = new ArrayMap<>();
        for (String cookie : cookieSet) {
            SessionCookie sessionCookie = new SessionCookie(cookie);
            cookieMap.put(sessionCookie.getKey(), sessionCookie);
        }
        return cookieMap;
    }
}
