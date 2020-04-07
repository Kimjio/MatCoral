package com.kimjio.coral.data.nook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

public class Message {
    @StringDef({"keyboard", "all_friend", "friend"})
    @interface Type {}

    @Type
    private String type;
    private String body;
    private String userId;

    public Message(@Type String type, @NonNull String body, @Nullable String userId) {
        //noinspection ConstantConditions
        if (body == null) {
            throw new NullPointerException("body == null");
        }
        if (type.equals("keyboard") || type.equals("all_friend") || type.equals("friend")) {
            if (type.equals("friend") && userId == null) {
                throw new IllegalArgumentException("userId cannot be null when type is 'friend'");
            }
            this.type = type;
            this.body = body;
            this.userId = userId;
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
