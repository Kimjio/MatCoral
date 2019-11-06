package com.kimjio.coral.data;

public class User extends BaseData {
    private String name;
    private String imageUrl;
    private String supportId; //지원 코드
    private Membership membership; //NSO 구독 여부
    private boolean isFriend;
    private boolean isInvited;
    private boolean isJoinedVoip; //음성 사용 중?
    private boolean isOwner; //방장
    private boolean isPlaying;
    private boolean isServiceUser; //NSO 구독 사용자?
    private long lastSeenAt;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
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
