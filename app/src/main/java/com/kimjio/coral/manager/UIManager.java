package com.kimjio.coral.manager;

import android.content.Context;
import android.content.SharedPreferences;

public final class UIManager {
    private static final String PREF_UI = "pref_ui";
    private static final String WELCOME_DISPLAYED = "welcome_displayed";

    private static UIManager INSTANCE;

    private SharedPreferences preferences;

    private UIManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREF_UI, Context.MODE_PRIVATE);
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
        preferences.edit().putBoolean(WELCOME_DISPLAYED, true).apply();
    }

    public boolean welcomeDisplayed() {
        return preferences.getBoolean(WELCOME_DISPLAYED, false);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
