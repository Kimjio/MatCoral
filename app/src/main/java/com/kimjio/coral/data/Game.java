package com.kimjio.coral.data;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class Game extends BaseData {
    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    private String name;
    private String imageUrl;
    private String instructionUrl;
    private int maxMember;
    private String officialUrl;
    private GameMode selectedGameMode;
    private String voiceChatBackgroundUrl;

    public Game() {
    }

    protected Game(Parcel in) {
        super(in);
        name = in.readString();
        imageUrl = in.readString();
        instructionUrl = in.readString();
        maxMember = in.readInt();
        officialUrl = in.readString();
        selectedGameMode = in.readParcelable(GameMode.class.getClassLoader());
        voiceChatBackgroundUrl = in.readString();
    }

    @BindingAdapter("drawableTop")
    public static void getImageFromURL(TextView textView, String url) {
        //Get Image
        Drawable drawable = null;
        textView.setCompoundDrawablesRelative(null, drawable, null, null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(instructionUrl);
        dest.writeInt(maxMember);
        dest.writeString(officialUrl);
        dest.writeParcelable(selectedGameMode, flags);
        dest.writeString(voiceChatBackgroundUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return Uri.parse(imageUrl);
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
}
