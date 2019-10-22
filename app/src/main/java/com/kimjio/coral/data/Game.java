package com.kimjio.coral.data;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class Game extends BaseData {
    public String name;
    public String imageUrl;
    public String instructionUrl;
    public int maxMember;
    public String officialUrl;
    public GameMode selectedGameMode;
    public String voiceChatBackgroundUrl;

    @BindingAdapter("drawableTop")
    public static void getImageFromURL(TextView textView, String url) {
        //Get Image
        Drawable drawable = null;
        textView.setCompoundDrawablesRelative(null, drawable, null, null);
    }
}
