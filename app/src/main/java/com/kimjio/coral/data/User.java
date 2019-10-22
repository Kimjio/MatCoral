package com.kimjio.coral.data;

public class User extends BaseData {
    public String name;
    public String imageUrl;
    public String supportId; //지원 코드
    public Membership membership; //NSO 구독 여부
    public boolean isFriend;
    public boolean isInvited;
    public boolean isJoinedVoip; //음성 사용 중?
    public boolean isOwner; //방장
    public boolean isPlaying;
    public boolean isServiceUser; //NSO 구독 사용자?
    public long lastSeenAt;
}
