package com.kimjio.coral.data.splat;

import android.net.Uri;

public abstract class SplatBaseData {
    private static final String SPLAT_BASE = "https://app.splatoon2.nintendo.net";

    protected String id;
    protected String name;
    protected String image;
    protected String thumbnail;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Uri getImageUri() {
        return Uri.parse(SPLAT_BASE + image);
    }

    public Uri getThumbnailUri() {
        return Uri.parse(SPLAT_BASE + thumbnail);
    }
}
