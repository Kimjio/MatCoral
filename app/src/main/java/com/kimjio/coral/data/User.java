package com.kimjio.coral.data;

import android.net.Uri;
import android.os.Parcel;

public class User extends BaseData {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String name;
    private String imageUri;
    private String supportId; //지원 코드
    private Membership membership; //NSO 구독 여부
    private boolean isFriend;
    private boolean isInvited;
    private boolean isJoinedVoip; //음성 사용 중?
    private boolean isOwner; //방장
    private boolean isPlaying;
    private boolean isServiceUser; //NSO 구독 사용자?
    private long lastSeenAt;

    public User() {
    }

    protected User(Parcel in) {
        super(in);
        name = in.readString();
        imageUri = in.readString();
        supportId = in.readString();
        membership = in.readParcelable(Membership.class.getClassLoader());
        isFriend = in.readByte() != 0;
        isInvited = in.readByte() != 0;
        isJoinedVoip = in.readByte() != 0;
        isOwner = in.readByte() != 0;
        isPlaying = in.readByte() != 0;
        isServiceUser = in.readByte() != 0;
        lastSeenAt = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(imageUri);
        dest.writeString(supportId);
        dest.writeParcelable(membership, flags);
        dest.writeByte((byte) (isFriend ? 1 : 0));
        dest.writeByte((byte) (isInvited ? 1 : 0));
        dest.writeByte((byte) (isJoinedVoip ? 1 : 0));
        dest.writeByte((byte) (isOwner ? 1 : 0));
        dest.writeByte((byte) (isPlaying ? 1 : 0));
        dest.writeByte((byte) (isServiceUser ? 1 : 0));
        dest.writeLong(lastSeenAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return Uri.parse(imageUri);
    }

    public String getSupportId() {
        return supportId;
    }

    public Membership getMembership() {
        return membership;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public boolean isJoinedVoip() {
        return isJoinedVoip;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isServiceUser() {
        return isServiceUser;
    }

    public long getLastSeenAt() {
        return lastSeenAt;
    }
}
