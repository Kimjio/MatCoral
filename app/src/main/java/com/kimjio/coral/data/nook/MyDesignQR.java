package com.kimjio.coral.data.nook;

import androidx.annotation.IntRange;

import java.util.Arrays;

public class MyDesignQR {
    private String title;
    private long playerId;
    private String creator;
    // 0: boy 1: girl
    private int gender;
    private long villageId;
    private String village;
    private int language;
    private int country;
    private int region;
    private byte[] paletteColors;
    private int color;
    private int looks;
    private int usage;
    private byte[] colorData;
    private Type designType;
    // qrText
    private String raw;
    // 0 ~ 3; 4
    @IntRange(from = 0, to = 3)
    private int sheetIndex;
    private long consecutiveId;

    public MyDesignQR(String title, long playerId, String creator, int gender,
                      long villageId, String village, int language, int country,
                      int region, byte[] paletteColors, int color, int looks,
                      int usage, byte[] colorData, String raw, int sheetIndex, long consecutiveId) {
        this.title = title;
        this.playerId = playerId;
        this.creator = creator;
        this.gender = gender;
        this.villageId = villageId;
        this.village = village;
        this.language = language;
        this.country = country;
        this.region = region;
        this.paletteColors = paletteColors;
        this.color = color;
        this.looks = looks;
        this.usage = usage;
        this.colorData = colorData;
        this.raw = raw;
        this.sheetIndex = sheetIndex;
        this.consecutiveId = consecutiveId;
        this.designType = getDesignTypeByUsage();
    }

    public String getTitle() {
        return title;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getCreator() {
        return creator;
    }

    public int getGender() {
        return gender;
    }

    public long getVillageId() {
        return villageId;
    }

    public String getVillage() {
        return village;
    }

    public int getLanguage() {
        return language;
    }

    public int getCountry() {
        return country;
    }

    public int getRegion() {
        return region;
    }

    public byte[] getPaletteColors() {
        return paletteColors;
    }

    public int getColor() {
        return color;
    }

    public int getLooks() {
        return looks;
    }

    public int getUsage() {
        return usage;
    }

    public byte[] getColorData() {
        return colorData;
    }

    public Type getDesignType() {
        return designType;
    }

    public String getRaw() {
        return raw;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public long getConsecutiveId() {
        return consecutiveId;
    }

    private Type getDesignTypeByUsage() {
        switch (usage) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return Type.PRO;
            case 9:
            case 10:
            case 11:
            default:
                return Type.NORMAL;
        }
    }

    public enum Type {
        NORMAL,
        PRO
    }

    @Override
    public String toString() {
        return "MyDesignQR{" +
                "title='" + title + '\'' +
                ", playerId=" + playerId +
                ", creator='" + creator + '\'' +
                ", gender=" + gender +
                ", villageId=" + villageId +
                ", village='" + village + '\'' +
                ", language=" + language +
                ", country=" + country +
                ", region=" + region +
                ", paletteColors=" + Arrays.toString(paletteColors) +
                ", color=" + color +
                ", looks=" + looks +
                ", usage=" + usage +
                ", colorData=" + Arrays.toString(colorData) +
                ", designType=" + designType +
                ", sheetIndex=" + sheetIndex +
                ", consecutiveId=" + consecutiveId +
                '}';
    }
}
