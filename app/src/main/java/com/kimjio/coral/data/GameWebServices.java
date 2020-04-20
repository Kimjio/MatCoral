package com.kimjio.coral.data;

import java.util.List;

public class GameWebServices implements Wrapper<List<GameWebService>> {
    private int status;
    private String errorMessage;
    private String correlationId;
    private List<GameWebService> result;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public List<GameWebService> getData() {
        return result;
    }
}
