package com.kimjio.coral.data.nook;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    private String userId;
    @SerializedName("mPNm")
    private String playerName;
    @SerializedName("mBirth")
    private Birth birth;
    @SerializedName("mHandleName")
    private String handleName;
    @SerializedName("mComment")
    private String comment;
    @SerializedName("mIsLandMaster")
    private boolean isLandMaster;
    @SerializedName("mTimeStamp")
    private TimeStamp registeredTimeStamp;
    @SerializedName("mMyDesignAuthorId")
    private String myDesignAuthorId;
    @SerializedName("mJpeg")
    private String image;
    private String contentId;
    private String digest;
    private long createdAt;
    private String landName;
    @SerializedName("mLanguage")
    private String language;
    @SerializedName("mVer")
    private int version;

    public String getPlayerName() {
        return playerName;
    }

    public Birth getBirth() {
        return birth;
    }

    public String getHandleName() {
        return handleName;
    }

    public String getComment() {
        return comment;
    }

    public boolean isLandMaster() {
        return isLandMaster;
    }

    public TimeStamp getRegisteredTimeStamp() {
        return registeredTimeStamp;
    }

    public String getMyDesignAuthorId() {
        return myDesignAuthorId;
    }

    public String getImage() {
        return image;
    }

    public Uri getImageUri() {
        return Uri.parse(image);
    }

    public String getContentId() {
        return contentId;
    }

    public String getDigest() {
        return digest;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getLandName() {
        return landName;
    }

    public String getLanguage() {
        return language;
    }

    public int getVersion() {
        return version;
    }

    public static class Birth {
        int month;
        int day;

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }

        public ZodiacSign getZodiacSign() {
            switch (month) {
                case 1:
                    return day <= 19 ? ZodiacSign.CAPRICORN : ZodiacSign.AQUARIUS;
                case 2:
                    return day <= 18 ? ZodiacSign.AQUARIUS : ZodiacSign.PISCES;
                case 3:
                    return day <= 20 ? ZodiacSign.PISCES : ZodiacSign.ARIES;
                case 4:
                    return day <= 19 ? ZodiacSign.ARIES : ZodiacSign.TAURUS;
                case 5:
                    return day <= 20 ? ZodiacSign.TAURUS : ZodiacSign.GEMINI;
                case 6:
                    return day <= 21 ? ZodiacSign.GEMINI : ZodiacSign.CANCER;
                case 7:
                    return day <= 22 ? ZodiacSign.CANCER : ZodiacSign.LEO;
                case 8:
                    return day <= 22 ? ZodiacSign.LEO : ZodiacSign.VIRGO;
                case 9:
                    return day <= 22 ? ZodiacSign.VIRGO : ZodiacSign.LIBRA;
                case 10:
                    return day <= 23 ? ZodiacSign.LIBRA : ZodiacSign.SCORPIO;
                case 11:
                    return day <= 22 ? ZodiacSign.SCORPIO : ZodiacSign.SAGITTARIUS;
                case 12:
                    return day <= 21 ? ZodiacSign.SAGITTARIUS : ZodiacSign.CAPRICORN;
                default:
                    throw new IllegalArgumentException(month + " is invalid month");
            }
        }
    }

    public static class TimeStamp {
        int year;
        int month;
        int day;

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }
}
