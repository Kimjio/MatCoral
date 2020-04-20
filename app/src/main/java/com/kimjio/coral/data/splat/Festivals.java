package com.kimjio.coral.data.splat;

import com.kimjio.coral.data.Wrapper;

import java.util.List;

public class Festivals implements Wrapper<List<Festival>> {
    private List<Festival> festivals;

    @Override
    public List<Festival> getData() {
        return festivals;
    }
}
