package com.kimjio.coral.data.auth.request;

import com.kimjio.coral.data.Wrapper;

public class TokenRequestWrapper implements Wrapper<TokenRequest> {
    private TokenRequest parameter;

    public TokenRequestWrapper(TokenRequest parameter) {
        this.parameter = parameter;
    }

    @Override
    public TokenRequest getData() {
        return parameter;
    }
}
