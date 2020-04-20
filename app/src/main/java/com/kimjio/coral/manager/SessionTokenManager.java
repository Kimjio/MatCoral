package com.kimjio.coral.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.kimjio.coral.data.auth.SessionToken;

public final class SessionTokenManager {
    private static final String PREF_SESSION_TOKEN = "pref_session_token";
    private static final String CODE = "code";
    private static final String SESSION_TOKEN = "session_token";
    private static SessionTokenManager INSTANCE;
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
        editor.putString(CODE, Base64.encodeToString(sessionToken.getCode().getBytes(), Base64.DEFAULT));
        editor.putString(SESSION_TOKEN, Base64.encodeToString(sessionToken.getSessionToken().getBytes(), Base64.DEFAULT));
        editor.apply();
    }

    public SessionToken loadSessionToken() {
        if (!(preferences.contains(CODE) && preferences.contains(SESSION_TOKEN)))
            return null;
        String code, sessionToken;
        code = new String(Base64.decode(preferences.getString(CODE, ""), Base64.DEFAULT));
        sessionToken = new String(Base64.decode(preferences.getString(SESSION_TOKEN, ""), Base64.DEFAULT));
        return new SessionToken(code, sessionToken);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
