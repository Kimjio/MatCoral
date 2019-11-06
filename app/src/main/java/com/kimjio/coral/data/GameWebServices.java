package com.kimjio.coral.data;

import java.util.List;

public class GameWebServices {
    private int status;
    private String correlationId;
    private List<GameWebService> result;

    public int getStatus() {
        return status;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public List<GameWebService> getResult() {
        return result;
    }
}
