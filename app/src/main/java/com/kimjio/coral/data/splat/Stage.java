package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

public class Stage {
    private int id;
    private String name;
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public static class Stats {
        @SerializedName("area_win")
        private int zonesWin;
        @SerializedName("area_lose")
        private int zonesLose;
        @SerializedName("asari_win")
        private int clamWin;
        @SerializedName("asari_lose")
        private int clamLose;
        @SerializedName("hoko_win")
        private int rainWin;
        @SerializedName("hoko_lose")
        private int rainLose;
        @SerializedName("yagura_win")
        private int towerWin;
        @SerializedName("yagura_lose")
        private int towerLose;
        @SerializedName("last_play_time")
        private long lastPlayTime;
        private Stage stage;

        public int getZonesWin() {
            return zonesWin;
        }

        public int getZonesLose() {
            return zonesLose;
        }

        public int getClamWin() {
            return clamWin;
        }

        public int getClamLose() {
            return clamLose;
        }

        public int getRainWin() {
            return rainWin;
        }

        public int getRainLose() {
            return rainLose;
        }

        public int getTowerWin() {
            return towerWin;
        }

        public int getTowerLose() {
            return towerLose;
        }

        public long getLastPlayTime() {
            return lastPlayTime;
        }

        public Stage getStage() {
            return stage;
        }
    }
}
