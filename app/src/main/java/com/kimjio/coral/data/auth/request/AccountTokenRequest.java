package com.kimjio.coral.data.auth.request;

import com.google.gson.annotations.SerializedName;

/**
 * access_token & id_token Request
 */
public class AccountTokenRequest {
    @SerializedName("client_id")
    private String clientId;
    @SerializedName("grant_type")
    private String grantType;
    @SerializedName("session_token")
    private String sessionToken;

    public AccountTokenRequest(String clientId, String grantType, String sessionToken) {
        this.clientId = clientId;
        this.grantType = grantType;
        this.sessionToken = sessionToken;
    }
}
