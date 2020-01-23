package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Records {
    @SerializedName("unique_id")
    private String uniqueId;
    @SerializedName("fes_results")
    private Map<String, FestivalResult> festivalResults;
    private Map<String, LeagueStats> leagueStats;
    @SerializedName("win_count")
    private int winCount;
    @SerializedName("lose_count")
    private int loseCount;
    private Player player;
    @SerializedName("recent_win_count")
    private int recentWinCount;
    @SerializedName("recent_lose_count")
    private int recentLoseCount;
    @SerializedName("recent_disconnect_count")
    private int recentDisconnectCount;
    @SerializedName("stage_stats")
    private Map<String, Stage.Stats> stageStats;
    @SerializedName("start_time")
    private long startTime;
    @SerializedName("update_time")
    private long updateTime;
    @SerializedName("weapon_stats")
    private Map<String, Weapon.Stats> weaponStats;


    public String getUniqueId() {
        return uniqueId;
    }

    public Map<String, FestivalResult> getFestivalResults() {
        return festivalResults;
    }

    public Map<String, LeagueStats> getLeagueStats() {
        return leagueStats;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRecentWinCount() {
        return recentWinCount;
    }

    public int getRecentLoseCount() {
        return recentLoseCount;
    }

    public int getRecentDisconnectCount() {
        return recentDisconnectCount;
    }

    public Map<String, Stage.Stats> getStageStats() {
        return stageStats;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public Map<String, Weapon.Stats> getWeaponStats() {
        return weaponStats;
    }
}
