package com.kimjio.coral.data.auth.flapg;

import com.google.gson.annotations.SerializedName;

public class FToken {
    public static final String APP = "app";
    public static final String NSO = "nso";

    @SerializedName("p1")
    private String token;
    @SerializedName("p2")
    private String timestamp;
    @SerializedName("p3")
    private String uuid;
    private String f;

    public String getToken() {
        return token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUUID() {
        return uuid;
    }

    public String getF() {
        return f;
    }
}
