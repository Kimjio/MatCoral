package com.kimjio.coral.data.nook;

import android.net.Uri;

public class User extends NookBaseItem {
    private String image;
    private Land land;
    private String screening;

    public String getImage() {
        return image;
    }

    public Uri getImageUri() {
        return Uri.parse(image);
    }

    public Land getLand() {
        return land;
    }

    public String getScreening() {
        return screening;
    }

    public boolean isReported() {
        if (screening == null) return false;
        return screening.equals("ng");
    }
}
