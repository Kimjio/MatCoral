package com.kimjio.coral.data.nook;

import android.net.Uri;

public class User extends NookBaseItem {
    private String image;
    private Land land;

    public String getImage() {
        return image;
    }

    public Uri getImageUri() {
        return Uri.parse(image);
    }

    public Land getLand() {
        return land;
    }
}
