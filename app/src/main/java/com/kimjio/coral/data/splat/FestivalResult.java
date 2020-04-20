package com.kimjio.coral.data.splat;

import androidx.annotation.IntRange;

import com.google.gson.annotations.SerializedName;

public class FestivalResult {
    public static final int FEST_POINT_FIEND = 100;
    public static final int FEST_POINT_DEFENDER = 250;
    public static final int FEST_POINT_CHAMPION = 500;
    public static final int FEST_POINT_MASTER = 999;
    public static final int FEST_POINT_MAX = FEST_POINT_FIEND + FEST_POINT_DEFENDER + FEST_POINT_CHAMPION + FEST_POINT_MASTER;

    @SerializedName("fes_id")
    private String id;
    @SerializedName("fes_point")
    private int fesPoint;
    @SerializedName("fes_power")
    private int fesPower;
    @SerializedName("fes_start_time")
    private long fesStartTime;
    @SerializedName("fes_grade")
    private Grade fesGrade;
    @SerializedName("fes_contribution_challenge")
    private Contribution fesContributionChallenge;
    @SerializedName("fes_contribution_regular")
    private Contribution fesContributionRegular;

    public String getId() {
        return id;
    }

    public int getPoint() {
        if (fesPoint == 0 && fesGrade.rank == Grade.GRADE_MASTER)
            return FEST_POINT_MAX;
        return fesPoint;
    }

    public int getLegacyPower() {
        return fesPower;
    }

    public long getStartTime() {
        return fesStartTime;
    }

    public Grade getGrade() {
        return fesGrade;
    }

    public Contribution getContributionChallenge() {
        return fesContributionChallenge;
    }

    public Contribution getContributionRegular() {
        return fesContributionRegular;
    }

    public boolean isCompat() {
        return fesPower > 0 && !isLegacy();
    }

    public boolean isLegacy() {
        return fesContributionRegular == null && fesContributionChallenge == null;
    }

    public static class Grade {
        public static final int GRADE_FAN = 0;
        public static final int GRADE_FIEND = 1;
        public static final int GRADE_DEFENDER = 2;
        public static final int GRADE_CHAMPION = 3;
        public static final int GRADE_MASTER = 4;

        private String name;
        @IntRange(from = 0, to = 4)
        private int rank;

        public String getName() {
            return name;
        }

        public int getRank() {
            return rank;
        }
    }

    public static class Contribution {
        private double average;
        private int total;

        public double getAverage() {
            return average;
        }

        public int getTotal() {
            return total;
        }
    }
}
