package com.kimjio.coral.data.auth.request;

import com.kimjio.coral.data.Wrapper;

public class WebServiceTokenRequestWrapper implements Wrapper<WebServiceTokenRequest> {
    private WebServiceTokenRequest parameter;

    public WebServiceTokenRequestWrapper(WebServiceTokenRequest parameter) {
        this.parameter = parameter;
    }

    @Override
    public WebServiceTokenRequest getData() {
        return parameter;
    }
}
