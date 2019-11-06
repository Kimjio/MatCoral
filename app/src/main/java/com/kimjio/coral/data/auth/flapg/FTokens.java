package com.kimjio.coral.data.auth.flapg;

import com.google.gson.annotations.SerializedName;

public class FTokens {
    @SerializedName("login_app")
    private FToken loginApp;
    @SerializedName("login_nso")
    private FToken loginNSO;

    public FToken getLoginApp() {
        return loginApp;
    }

    public FToken getLoginNSO() {
        return loginNSO;
    }
}
