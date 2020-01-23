package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

public class RankBattle {
    private String name;
    private int number;
    @SerializedName("s_plus_number")
    private Integer sPlusNumber;
    @SerializedName("is_x")
    private boolean isX;
    @SerializedName("is_number_reached")
    private boolean isNumberReached;

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getSPlusNumber() {
        if (sPlusNumber == null) return -1;
        return sPlusNumber;
    }

    public boolean isX() {
        return isX;
    }

    public boolean isNumberReached() {
        return isNumberReached;
    }

    public static final class Rank {
        public static final int RANK_C_MIN = 0;
        public static final int RANK_C = 1;
        public static final int RANK_C_PLUS = 2;
        public static final int RANK_B_MIN = 3;
        public static final int RANK_B = 4;
        public static final int RANK_B_PLUS = 5;
        public static final int RANK_A_MIN = 6;
        public static final int RANK_A = 7;
        public static final int RANK_A_PLUS = 8;
        public static final int RANK_S = 9;
        public static final int RANK_S_PLUS = 10;
        public static final int RANK_X = 11;

        public static String toString(int maxRank) {
            String rank;
            switch (maxRank) {
                case RANK_X:
                    rank = "X";
                    break;
                case RANK_S_PLUS:
                    rank = "S+";
                    break;
                case RANK_S:
                    rank = "S";
                    break;
                case RANK_A_PLUS:
                    rank = "A+";
                    break;
                case RANK_A:
                    rank = "A";
                    break;
                case RANK_A_MIN:
                    rank = "A-";
                    break;
                case RANK_B_PLUS:
                    rank = "B+";
                    break;
                case RANK_B:
                    rank = "B";
                    break;
                case RANK_B_MIN:
                    rank = "B-";
                    break;
                case RANK_C_PLUS:
                    rank = "C+";
                    break;
                case RANK_C:
                    rank = "C";
                    break;
                default:
                case RANK_C_MIN:
                    rank = "C-";
            }
            return rank;
        }
    }
}
