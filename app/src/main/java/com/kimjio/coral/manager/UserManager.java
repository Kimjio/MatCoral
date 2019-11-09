package com.kimjio.coral.manager;

import com.kimjio.coral.data.User;
import com.kimjio.coral.data.me.Me;

public final class UserManager {
    private static UserManager INSTANCE;

    private User user;
    private Me accountUser;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (INSTANCE == null)
            synchronized (UserManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserManager();
                }
            }
        return INSTANCE;
    }

    public User getUser() {
        return user;
    }

    public UserManager setUser(User user) {
        this.user = user;
        return this;
    }

    public Me getAccountUser() {
        return accountUser;
    }

    public UserManager setAccountUser(Me accountUser) {
        this.accountUser = accountUser;
        return this;
    }
}
