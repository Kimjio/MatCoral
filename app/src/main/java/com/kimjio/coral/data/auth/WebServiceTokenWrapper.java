package com.kimjio.coral.data.auth;

import com.kimjio.coral.data.Wrapper;

public class WebServiceTokenWrapper implements Wrapper<WebServiceToken> {
    private int status;
    private String errorMessage;
    private WebServiceToken result;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public WebServiceToken getData() {
        return result;
    }
}
