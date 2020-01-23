package com.kimjio.coral.data.splat;

import androidx.annotation.ColorInt;

public class Color {
    private float a;
    private float r;
    private float g;
    private float b;

    @ColorInt
    public int getColorInt() {
        return android.graphics.Color.argb(Math.round(a), Math.round(r), Math.round(g), Math.round(b));
    }
}
