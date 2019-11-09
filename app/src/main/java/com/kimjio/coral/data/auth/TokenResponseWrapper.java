package com.kimjio.coral.data.auth;

import com.kimjio.coral.data.Wrapper;

public class TokenResponseWrapper implements Wrapper<TokenResponse> {
    private TokenResponse result;

    @Override
    public TokenResponse getData() {
        return result;
    }
}
