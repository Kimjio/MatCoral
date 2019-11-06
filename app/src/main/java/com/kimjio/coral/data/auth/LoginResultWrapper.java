package com.kimjio.coral.data.auth;

import com.kimjio.coral.data.Wrapper;

public class LoginResultWrapper implements Wrapper<LoginResult> {
    private LoginResult result;

    @Override
    public LoginResult getData() {
        return result;
    }
}
