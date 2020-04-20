package com.kimjio.coral.data.splat;

import com.kimjio.coral.data.Wrapper;

import java.util.List;

public class Stages implements Wrapper<List<Stage>> {
    private List<Stage> stages;

    @Override
    public List<Stage> getData() {
        return stages;
    }
}
