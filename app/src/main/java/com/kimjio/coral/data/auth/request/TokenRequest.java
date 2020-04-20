package com.kimjio.coral.data.auth.request;

import java.util.Locale;

public class TokenRequest {
    private String language;
    private String naBirthday;
    private String naCountry;
    private String naIdToken;
    private String requestId;
    private long timestamp;
    private String f;

    public TokenRequest(String naBirthday, String naCountry, String naIdToken, String requestId, long timestamp, String f) {
        this.language = Locale.getDefault().toLanguageTag();
        this.naBirthday = naBirthday;
        this.naCountry = naCountry;
        this.naIdToken = naIdToken;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.f = f;
    }

    public TokenRequest(String naIdToken, String requestId, long timestamp, String f) {
        this.naIdToken = naIdToken;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.f = f;
    }
}
