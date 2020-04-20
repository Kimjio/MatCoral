package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

public class LeagueStats {
    public static final String LEAGUE_TYPE_PAIR = "pair";
    public static final String LEAGUE_TYPE_TEAM = "team";

    @SerializedName("gold_count")
    private int goldCount;
    @SerializedName("silver_count")
    private int silverCount;
    @SerializedName("bronze_count")
    private int bronzeCount;
    @SerializedName("no_medal_count")
    private int noMedalCount;

    public int getGoldCount() {
        return goldCount;
    }

    public int getSilverCount() {
        return silverCount;
    }

    public int getBronzeCount() {
        return bronzeCount;
    }

    public int getNoMedalCount() {
        return noMedalCount;
    }
}
