package com.kimjio.coral.data.auth;

import com.kimjio.coral.data.User;

public class TokenResponse {
    private User user;
    private FirebaseCredential firebaseCredential;
    private WebApiServerCredential webApiServerCredential;
    private int status;
    private String correlationId;

    public User getUser() {
        return user;
    }

    public FirebaseCredential getFirebaseCredential() {
        return firebaseCredential;
    }

    public WebApiServerCredential getWebApiServerCredential() {
        return webApiServerCredential;
    }

    public int getStatus() {
        return status;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
