package com.kimjio.coral.data;

import android.os.Parcel;

public class GameMode extends BaseData {
    public static final Creator<GameMode> CREATOR = new Creator<GameMode>() {
        @Override
        public GameMode createFromParcel(Parcel in) {
            return new GameMode(in);
        }

        @Override
        public GameMode[] newArray(int size) {
            return new GameMode[size];
        }
    };
    private String name; //Salmon Run
    private String instructionUrl; //How to Join

    public GameMode() {
    }

    protected GameMode(Parcel in) {
        super(in);
        name = in.readString();
        instructionUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(instructionUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getInstructionUrl() {
        return instructionUrl;
    }
}
