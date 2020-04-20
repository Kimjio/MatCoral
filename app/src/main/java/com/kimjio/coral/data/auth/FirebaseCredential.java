package com.kimjio.coral.data.auth;

public class FirebaseCredential {
    private String accessToken;
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
