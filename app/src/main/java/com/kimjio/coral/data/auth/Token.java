package com.kimjio.coral.data.auth;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Token {
    @SerializedName("id_token")
    private String idToken;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private long expiresIn;
    private String[] scope;

    public String getIdToken() {
        return idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String[] getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "Token{" +
                "idToken='" + idToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope=" + Arrays.toString(scope) +
                '}';
    }
}
