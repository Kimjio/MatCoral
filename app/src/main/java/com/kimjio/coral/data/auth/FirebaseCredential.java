package com.kimjio.coral.data.auth;

public class FirebaseCredential {
    private String accessToken;
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
