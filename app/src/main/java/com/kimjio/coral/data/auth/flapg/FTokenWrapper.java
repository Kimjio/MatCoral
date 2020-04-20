package com.kimjio.coral.data.auth.flapg;

import com.kimjio.coral.data.Wrapper;

public class FTokenWrapper implements Wrapper<FToken> {
    private String error;
    private FToken result;

    @Override
    public String getErrorMessage() {
        return error;
    }

    @Override
    public FToken getData() {
        return result;
    }
}
