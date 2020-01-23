package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

public class Weapon {
    private String id;
    private String name;
    private String image;
    private String thumbnail;
    private Ability sub;
    private Ability special;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Ability getSub() {
        return sub;
    }

    public Ability getSpecial() {
        return special;
    }

    public static class Ability {
        private String id;
        private String name;
        @SerializedName("image_a")
        private String imageA;
        @SerializedName("image_b")
        private String imageB;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getImageA() {
            return imageA;
        }

        public String getImageB() {
            return imageB;
        }
    }

    public static class Stats {
        @SerializedName("win_count")
        private int winCount;
        @SerializedName("lose_count")
        private int loseCount;
        @SerializedName("win_meter")
        private double winMeter;
        @SerializedName("max_win_meter")
        private double maxWinMeter;
        @SerializedName("total_paint_point")
        private int totalPaintPoint;
        @SerializedName("last_use_time")
        private long lastUseTime;
        private Weapon weapon;

        public int getWinCount() {
            return winCount;
        }

        public int getLoseCount() {
            return loseCount;
        }

        public double getWinMeter() {
            return winMeter;
        }

        public double getMaxWinMeter() {
            return maxWinMeter;
        }

        public int getTotalPaintPoint() {
            return totalPaintPoint;
        }

        public long getLastUseTime() {
            return lastUseTime;
        }

        public Weapon getWeapon() {
            return weapon;
        }
    }
}
