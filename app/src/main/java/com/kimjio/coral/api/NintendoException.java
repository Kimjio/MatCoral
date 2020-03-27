package com.kimjio.coral.api;

import androidx.annotation.Nullable;

public class NintendoException extends RuntimeException {
    public static final int ERROR_UPGRADE = 9427;
    public static final int ERROR_INVALID_TOKEN = 9403;

    private int status;

    public NintendoException(int status, String errorMessage) {
        super(errorMessage);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
