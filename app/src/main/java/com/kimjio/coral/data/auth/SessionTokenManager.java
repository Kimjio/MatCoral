package com.kimjio.coral.data.auth;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public final class SessionTokenManager {

    public static final String PREF_SESSION_TOKEN = "pref_session_token";

    private static SessionTokenManager INSTANCE;

    private SharedPreferences preferences;

    public static SessionTokenManager getInstance(Application application) {
        if (INSTANCE == null)
            synchronized (SessionTokenManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new SessionTokenManager(application);
            }
        return INSTANCE;
    }

    private SessionTokenManager(Application application) {
        preferences = application.getSharedPreferences(PREF_SESSION_TOKEN , Context.MODE_PRIVATE);
    }


}
