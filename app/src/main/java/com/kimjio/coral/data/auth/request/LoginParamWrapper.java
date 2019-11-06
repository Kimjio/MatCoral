package com.kimjio.coral.data.auth.request;

import com.kimjio.coral.data.Wrapper;

public class LoginParamWrapper implements Wrapper<LoginParam> {
    private LoginParam parameter;

    public LoginParamWrapper(LoginParam parameter) {
        this.parameter = parameter;
    }

    @Override
    public LoginParam getData() {
        return parameter;
    }
}
