package com.kimjio.coral.data.nook;

import android.net.Uri;

public class Npc {
    private String name;
    private String image;
    private int birthMonth;
    private int birthDay;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public Uri getImageUri() {
        return Uri.parse(image);
    }
}
