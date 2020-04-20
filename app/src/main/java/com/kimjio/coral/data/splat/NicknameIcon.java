package com.kimjio.coral.data.splat;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class NicknameIcon {
    private String nickname;
    @SerializedName("nsa_id")
    private String nsaId;
    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    public String getNickname() {
        return nickname;
    }

    public String getNsaId() {
        return nsaId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Uri getThumbnailUri() {
        return Uri.parse(thumbnailUrl);
    }
}
