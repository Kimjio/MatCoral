package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

public class Player {
    private Gear head;
    @SerializedName("head_skills")
    private Skills headSkills;

    private Gear clothes;
    @SerializedName("clothes_skills")
    private Skills clothesSkills;

    private Gear shoes;
    @SerializedName("shoes_skills")
    private Skills shoesSkills;

    @SerializedName("max_league_point_pair")
    private double maxLeaguePointPair;
    @SerializedName("max_league_point_team")
    private double maxLeaguePointTeam;

    private String nickname;
    @SerializedName("player_rank")
    private String level;
    @SerializedName("player_type")
    private Type type;
    @SerializedName("principal_id")
    private String nsUserId;
    @SerializedName("star_rank")
    private int starLevel;
    @SerializedName("udemae_clam")
    private RankBattle rankClamBlitz;
    @SerializedName("udemae_rainmaker")
    private RankBattle rankClamRainmaker;
    @SerializedName("udemae_zones")
    private RankBattle rankClamSplatZones;
    @SerializedName("udemae_tower")
    private RankBattle rankClamTowerControl;
    private Weapon weapon;

    public Gear getHead() {
        return head;
    }

    public Skills getHeadSkills() {
        return headSkills;
    }

    public Gear getClothes() {
        return clothes;
    }

    public Skills getClothesSkills() {
        return clothesSkills;
    }

    public Gear getShoes() {
        return shoes;
    }

    public Skills getShoesSkills() {
        return shoesSkills;
    }

    public double getMaxLeaguePointPair() {
        return maxLeaguePointPair;
    }

    public double getMaxLeaguePointTeam() {
        return maxLeaguePointTeam;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLevel() {
        return level;
    }

    public Type getType() {
        return type;
    }

    public String getNsUserId() {
        return nsUserId;
    }

    public int getStarLevel() {
        return starLevel;
    }

    public RankBattle getRankClamBlitz() {
        return rankClamBlitz;
    }

    public RankBattle getRankClamRainmaker() {
        return rankClamRainmaker;
    }

    public RankBattle getRankClamSplatZones() {
        return rankClamSplatZones;
    }

    public RankBattle getRankClamTowerControl() {
        return rankClamTowerControl;
    }

    public String getMaxRank() {
        int rankClam = rankClamBlitz.getNumber();
        int rankRain = rankClamRainmaker.getNumber();
        int rankZones = rankClamSplatZones.getNumber();
        int rankTower = rankClamTowerControl.getNumber();

        int maxRank = Math.max(rankClam, Math.max(rankRain, Math.max(rankZones, rankTower)));

        return RankBattle.Rank.toString(maxRank);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public class Type {
        private String species; //inklings octolings
        private String style; //boy girl

        public String getSpecies() {
            return species;
        }

        public String getStyle() {
            return style;
        }
    }
}
