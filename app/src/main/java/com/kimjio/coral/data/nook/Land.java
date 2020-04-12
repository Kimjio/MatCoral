package com.kimjio.coral.data.nook;

import android.content.Context;

import com.kimjio.coral.R;

public class Land extends NookBaseItem {
    private int displayId; // 0 ~ 3; 4개

    public int getDisplayId() {
        return displayId;
    }

    public String getNameWithSuffix(Context context) {
        String suffix = context.getResources().getStringArray(R.array.land_suffix)[displayId];
        return name + suffix;
    }
}
