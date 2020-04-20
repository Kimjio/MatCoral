package com.kimjio.coral.data.auth;

import com.google.gson.annotations.SerializedName;

public class SessionToken {
    private String code;
    @SerializedName("session_token")
    private String sessionToken;

    public SessionToken() {
    }

    public SessionToken(String code, String sessionToken) {
        this.code = code;
        this.sessionToken = sessionToken;
    }

    public String getCode() {
        return code;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
