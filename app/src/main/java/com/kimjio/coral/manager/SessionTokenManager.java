package com.kimjio.coral.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.kimjio.coral.data.auth.SessionToken;

public final class SessionTokenManager {
    private static SessionTokenManager INSTANCE;
    private static final String PREF_SESSION_TOKEN = "pref_session_token";
    private SharedPreferences preferences;

    private SessionTokenManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREF_SESSION_TOKEN, Context.MODE_PRIVATE);
    }

    public static SessionTokenManager getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (SessionTokenManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new SessionTokenManager(context);
            }
        return INSTANCE;
    }

    public void saveSessionToken(SessionToken sessionToken) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("code", Base64.encodeToString(sessionToken.getCode().getBytes(), Base64.DEFAULT));
        editor.putString("session_token", Base64.encodeToString(sessionToken.getSessionToken().getBytes(), Base64.DEFAULT));
        editor.apply();
    }

    public SessionToken loadSessionToken() {
        if (!(preferences.contains("code") && preferences.contains("session_token")))
            return null;
        String code, sessionToken;
        code = new String(Base64.decode(preferences.getString("code", ""), Base64.DEFAULT));
        sessionToken = new String(Base64.decode(preferences.getString("session_token", ""), Base64.DEFAULT));
        return new SessionToken(code, sessionToken);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
