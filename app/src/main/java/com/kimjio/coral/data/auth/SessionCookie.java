package com.kimjio.coral.data.auth;

import androidx.annotation.NonNull;

public class SessionCookie {
    private String key;
    private String value;
    private long expiresIn;

    public SessionCookie(String cookie) {
        String[] split = cookie.split("/");
        this.key = split[0];
        this.value = split[1];
        this.expiresIn = Long.parseLong(split[2]);
    }

    public SessionCookie(String key, String value, long expiresIn) {
        this.key = key;
        this.value = value;
        this.expiresIn = expiresIn;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public boolean expires() {
        return System.currentTimeMillis() / 1000 > expiresIn;
    }

    @NonNull
    @Override
    public String toString() {
        return getKey() + "/" + getValue() + "/" + getExpiresIn();
    }

    @NonNull
    public String toRawString() {
        return getKey() + "=" + getValue() + ";";
    }
}
