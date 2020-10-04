package com.kimjio.coral.data.nook;

import com.kimjio.coral.data.Wrapper;

import java.util.List;

public class Reactions implements Wrapper<List<Reaction>> {
    private String language;
    private List<Reaction> emoticons;

    @Override
    public List<Reaction> getData() {
        return emoticons;
    }
}
