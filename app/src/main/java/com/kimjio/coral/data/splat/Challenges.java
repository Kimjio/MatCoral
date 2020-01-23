package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Challenges {
    @SerializedName("archived_challenges")
    private List<Challenge> archivedChallengesInkling;
    @SerializedName("archived_challenges_octa")
    private List<Challenge> archivedChallengesOctoling;

    @SerializedName("next_challenge")
    private Challenge nextChallengeInkling;
    @SerializedName("next_challenge_octa")
    private Challenge nextChallengeOctoling;

    @SerializedName("rewards")
    private List<Reward> rewardsInkling;
    @SerializedName("rewards_octa")
    private List<Reward> rewardsOctoling;

    @SerializedName("total_paint_point")
    private int totalPaintPointInkling;
    @SerializedName("total_paint_point_octa")
    private int totalPaintPointOctoling;

    public List<Challenge> getArchivedChallengesInkling() {
        return archivedChallengesInkling;
    }

    public List<Challenge> getArchivedChallengesOctoling() {
        return archivedChallengesOctoling;
    }

    public Challenge getNextChallengeInkling() {
        return nextChallengeInkling;
    }

    public Challenge getNextChallengeOctoling() {
        return nextChallengeOctoling;
    }

    public List<Reward> getRewardsInkling() {
        return rewardsInkling;
    }

    public List<Reward> getRewardsOctoling() {
        return rewardsOctoling;
    }

    public int getTotalPaintPointInkling() {
        return totalPaintPointInkling;
    }

    public int getTotalPaintPointOctoling() {
        return totalPaintPointOctoling;
    }
}
