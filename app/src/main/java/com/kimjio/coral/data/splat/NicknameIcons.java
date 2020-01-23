package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;
import com.kimjio.coral.data.Wrapper;

import java.util.List;

public class NicknameIcons implements Wrapper<List<NicknameIcon>> {
    @SerializedName("nickname_and_icons")
    private List<NicknameIcon> nicknameIcons;

    @Override
    public List<NicknameIcon> getData() {
        return nicknameIcons;
    }
}
