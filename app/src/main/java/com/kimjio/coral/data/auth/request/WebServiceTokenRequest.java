package com.kimjio.coral.data.auth.request;

public class WebServiceTokenRequest {
    private long id;
    private String registrationToken;
    private String requestId;
    private long timestamp;
    private String f;

    public WebServiceTokenRequest(long id, String registrationToken, String requestId, long timestamp, String f) {
        this.id = id;
        this.registrationToken = registrationToken;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.f = f;
    }
}
