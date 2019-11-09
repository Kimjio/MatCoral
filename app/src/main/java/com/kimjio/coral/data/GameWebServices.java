package com.kimjio.coral.data;

import java.util.List;

public class GameWebServices implements Wrapper<List<GameWebService>> {
    private int status;
    private String correlationId;
    private List<GameWebService> result;

    public int getStatus() {
        return status;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public List<GameWebService> getData() {
        return result;
    }
}
