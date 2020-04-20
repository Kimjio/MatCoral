package com.kimjio.coral.data.auth;

import com.kimjio.coral.data.Wrapper;

public class TokenResponseWrapper implements Wrapper<TokenResponse> {
    private int status;
    private String errorMessage;
    private TokenResponse result;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public TokenResponse getData() {
        return result;
    }
}
