package com.kimjio.coral.data.auth;

import com.kimjio.coral.data.Wrapper;

public class WebServiceTokenWrapper implements Wrapper<WebServiceToken> {
    private int status;
    private WebServiceToken result;

    @Override
    public WebServiceToken getData() {
        return result;
    }
}
