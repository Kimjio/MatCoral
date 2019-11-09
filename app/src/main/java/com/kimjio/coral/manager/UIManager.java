package com.kimjio.coral.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public final class UIManager {
    private static final String PREF_WELCOME_DISPLAYED = "pref_welcome_displayed";

    private static UIManager INSTANCE;

    private SharedPreferences preferences;

    private UIManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("pref_ui", Context.MODE_PRIVATE);
    }

    public static UIManager getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (UIManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new UIManager(context);
            }
        return INSTANCE;
    }

    public void setWelcomeDisplayed() {
        preferences.edit().putBoolean(PREF_WELCOME_DISPLAYED, true).apply();
    }

    public boolean welcomeDisplayed() {
        return preferences.getBoolean(PREF_WELCOME_DISPLAYED, false);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
