package com.kimjio.coral.data.auth;

public class WebApiServerCredential {
    private String accessToken;
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
