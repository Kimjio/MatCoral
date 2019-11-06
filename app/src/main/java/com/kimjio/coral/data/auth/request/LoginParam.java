package com.kimjio.coral.data.auth.request;

import java.util.Locale;

public class LoginParam {
    private String language = Locale.getDefault().toLanguageTag();
    private String naBirthday;
    private String naCountry;
    private String naIdToken;
    private String requestId;
    private long timestamp;
    private String f;

    public LoginParam(String naBirthday, String naCountry, String naIdToken, String requestId, long timestamp, String f) {
        this.naBirthday = naBirthday;
        this.naCountry = naCountry;
        this.naIdToken = naIdToken;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.f = f;
    }
}
