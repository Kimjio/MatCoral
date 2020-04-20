package com.kimjio.coral.data;

public interface Wrapper<T> {
    default int getStatus() {
        return 0;
    }

    default String getErrorMessage() {
        return null;
    }

    T getData();
}
