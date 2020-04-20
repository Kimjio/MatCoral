package com.kimjio.coral.data.me;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Me implements Parcelable {
    public static final Creator<Me> CREATOR = new Creator<Me>() {
        @Override
        public Me createFromParcel(Parcel in) {
            return new Me(in);
        }

        @Override
        public Me[] newArray(int size) {
            return new Me[size];
        }
    };
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

    protected Me(Parcel in) {
        id = in.readString();
        eachEmailOptedIn = in.readParcelable(EachEmailOptedIn.class.getClassLoader());
        mii = in.readParcelable(Mii.class.getClassLoader());
        screenName = in.readString();
        analyticsOptedInUpdatedAt = in.readLong();
        isChild = in.readByte() != 0;
        clientFriendsOptedInUpdatedAt = in.readString();
        timeZone = in.readParcelable(TimeZone.class.getClassLoader());
        updatedAt = in.readLong();
        candidateMiis = in.createTypedArray(Mii.CREATOR);
        emailOptedIn = in.readByte() != 0;
        gender = in.readString();
        analyticsPermissions = in.readParcelable(AnalyticsPermissions.class.getClassLoader());
        emailVerified = in.readByte() != 0;
        country = in.readString();
        clientFriendsOptedIn = in.readByte() != 0;
        createdAt = in.readLong();
        region = in.readString();
        birthday = in.readString();
        nickname = in.readString();
        language = in.readString();
        emailOptedInUpdatedAt = in.readLong();
        analyticsOptedIn = in.readByte() != 0;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(eachEmailOptedIn, flags);
        dest.writeParcelable(mii, flags);
        dest.writeString(screenName);
        dest.writeLong(analyticsOptedInUpdatedAt);
        dest.writeByte((byte) (isChild ? 1 : 0));
        dest.writeString(clientFriendsOptedInUpdatedAt);
        dest.writeParcelable(timeZone, flags);
        dest.writeLong(updatedAt);
        dest.writeTypedArray(candidateMiis, flags);
        dest.writeByte((byte) (emailOptedIn ? 1 : 0));
        dest.writeString(gender);
        dest.writeParcelable(analyticsPermissions, flags);
        dest.writeByte((byte) (emailVerified ? 1 : 0));
        dest.writeString(country);
        dest.writeByte((byte) (clientFriendsOptedIn ? 1 : 0));
        dest.writeLong(createdAt);
        dest.writeString(region);
        dest.writeString(birthday);
        dest.writeString(nickname);
        dest.writeString(language);
        dest.writeLong(emailOptedInUpdatedAt);
        dest.writeByte((byte) (analyticsOptedIn ? 1 : 0));
    }
}
