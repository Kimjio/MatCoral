package com.kimjio.coral.data;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class Game extends BaseData {
    private String name;
    private String imageUrl;
    private String instructionUrl;
    private int maxMember;
    private String officialUrl;
    private GameMode selectedGameMode;
    private String voiceChatBackgroundUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInstructionUrl() {
        return instructionUrl;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public String getOfficialUrl() {
        return officialUrl;
    }

    public GameMode getSelectedGameMode() {
        return selectedGameMode;
    }

    public String getVoiceChatBackgroundUrl() {
        return voiceChatBackgroundUrl;
    }

    @BindingAdapter("drawableTop")
    public static void getImageFromURL(TextView textView, String url) {
        //Get Image
        Drawable drawable = null;
        textView.setCompoundDrawablesRelative(null, drawable, null, null);
    }
}
