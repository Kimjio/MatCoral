package com.kimjio.coral.data.auth.request;

import com.google.gson.annotations.SerializedName;

public class TokenRequest {
    @SerializedName("client_id")
    private String clientId;
    @SerializedName("grant_type")
    private String grantType;
    @SerializedName("session_token")
    private String sessionToken;

    public TokenRequest(String clientId, String grantType, String sessionToken) {
        this.clientId = clientId;
        this.grantType = grantType;
        this.sessionToken = sessionToken;
    }
}
