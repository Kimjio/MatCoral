package com.kimjio.coral.manager;

import com.kimjio.coral.data.auth.WebApiServerCredential;
import com.kimjio.coral.data.auth.flapg.FToken;

public final class TokenManager {
    private static TokenManager INSTANCE;

    private FToken nsoToken;
    private FToken webAppToken;
    private WebApiServerCredential webApiServerCredential;
    private long webApiTimestamp;
    private long webApiExpiresIn;

    private TokenManager() {
    }

    public static TokenManager getInstance() {
        if (INSTANCE == null) {
            synchronized (TokenManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new TokenManager();
            }
        }
        return INSTANCE;
    }

    public FToken getNsoToken() {
        return nsoToken;
    }

    public TokenManager setNsoToken(FToken nsoToken) {
        this.nsoToken = nsoToken;
        return this;
    }

    public FToken getWebAppToken() {
        return webAppToken;
    }

    public TokenManager setWebAppToken(FToken webAppToken) {
        this.webAppToken = webAppToken;
        return this;
    }

    public WebApiServerCredential getWebApiServerCredential() {
        return webApiServerCredential;
    }

    public TokenManager setWebApiServerCredential(WebApiServerCredential webApiServerCredential) {
        this.webApiServerCredential = webApiServerCredential;
        webApiTimestamp = System.currentTimeMillis() / 1000;
        webApiExpiresIn = webApiServerCredential.getExpiresIn();
        return this;
    }

    public boolean expired() {
        return System.currentTimeMillis() / 1000 > webApiTimestamp + webApiExpiresIn || webApiServerCredential == null;
    }
}
