package com.kimjio.coral.data.nook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandProfile {
    @SerializedName("mVNm")
    private String name;
    @SerializedName("mVRuby")
    private int ruby; // ?
    @SerializedName("mFruit")
    private Fruit fruit;
    @SerializedName("mNormalNpc")
    private List<Npc> normalNpc;
    @SerializedName("mVillager")
    private List<UserProfile> villagers;

    public String getName() {
        return name;
    }

    public int getRuby() {
        return ruby;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public List<Npc> getNormalNpc() {
        return normalNpc;
    }

    public List<UserProfile> getVillagers() {
        return villagers;
    }
}
