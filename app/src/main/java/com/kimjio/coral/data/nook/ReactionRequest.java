package com.kimjio.coral.data.nook;

public class ReactionRequest {
    private String type = "emoticon";
    private String body;

    public ReactionRequest(String body) {
        this.body = body;
    }
}
