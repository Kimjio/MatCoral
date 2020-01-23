package com.kimjio.coral.data.splat;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Challenge {
    private String key;
    private String name;
    private String image;
    @SerializedName("paint_points")
    private int paintPoints;
    private String url;
    @SerializedName("url_message")
    private String urlMessage;
    @SerializedName("is_last")
    private boolean isLast;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return Uri.parse(image);
    }

    public String getImage() {
        return image;
    }

    public int getPaintPoints() {
        return paintPoints;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlMessage() {
        return urlMessage;
    }

    public boolean isLast() {
        return isLast;
    }
}
