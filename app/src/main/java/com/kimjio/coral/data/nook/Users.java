package com.kimjio.coral.data.nook;

import com.kimjio.coral.data.Wrapper;

import java.util.List;

public class Users implements Wrapper<List<User>> {
    private List<User> users;

    @Override
    public List<User> getData() {
        return users;
    }
}
