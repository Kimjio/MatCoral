package com.kimjio.coral.data.me;

import com.google.gson.annotations.SerializedName;

public class Me {
    private String id;
    private EachEmailOptedIn eachEmailOptedIn;
    private Mii mii;
    private String screenName;
    private long analyticsOptedInUpdatedAt;
    private boolean isChild;
    private String clientFriendsOptedInUpdatedAt;
    @SerializedName("timezone")
    private TimeZone timeZone;
    private long updatedAt;
    private Mii[] candidateMiis;
    private boolean emailOptedIn;
    private String gender;
    private AnalyticsPermissions analyticsPermissions;
    private boolean emailVerified;
    private String country;
    private boolean clientFriendsOptedIn;
    private long createdAt;
    private String region;
    private String birthday;
    private String nickname;
    private String language;
    private long emailOptedInUpdatedAt;
    private boolean analyticsOptedIn;

    public String getId() {
        return id;
    }

    public EachEmailOptedIn getEachEmailOptedIn() {
        return eachEmailOptedIn;
    }

    public Mii getMii() {
        return mii;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getAnalyticsOptedInUpdatedAt() {
        return analyticsOptedInUpdatedAt;
    }

    public boolean isChild() {
        return isChild;
    }

    public String getClientFriendsOptedInUpdatedAt() {
        return clientFriendsOptedInUpdatedAt;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public Mii[] getCandidateMiis() {
        return candidateMiis;
    }

    public boolean isEmailOptedIn() {
        return emailOptedIn;
    }

    public String getGender() {
        return gender;
    }

    public AnalyticsPermissions getAnalyticsPermissions() {
        return analyticsPermissions;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getCountry() {
        return country;
    }

    public boolean isClientFriendsOptedIn() {
        return clientFriendsOptedIn;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getRegion() {
        return region;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLanguage() {
        return language;
    }

    public long getEmailOptedInUpdatedAt() {
        return emailOptedInUpdatedAt;
    }

    public boolean isAnalyticsOptedIn() {
        return analyticsOptedIn;
    }
}
