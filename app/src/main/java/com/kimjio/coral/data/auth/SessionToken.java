package com.kimjio.coral.data.auth;

import com.google.gson.annotations.SerializedName;

public class SessionToken {
    private String code;
    @SerializedName("session_token")
    private String sessionToken;

    public String getCode() {
        return code;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public String toString() {
        return "SessionToken{" +
                "code='" + code + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
