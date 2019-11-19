package com.kimjio.coral.data.view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RawRes;

public class IntroItem implements Parcelable {
    public final String title;
    public String desc;
    @RawRes
    public final int rawRes;

    public IntroItem(String title, String desc, int rawRes) {
        this(title, rawRes);
        this.desc = desc;
    }

    public IntroItem(String title, int rawRes) {
        this.title = title;
        this.rawRes = rawRes;
    }

    private IntroItem(Parcel in) {
        title = in.readString();
        desc = in.readString();
        rawRes = in.readInt();
    }

    public static final Creator<IntroItem> CREATOR = new Creator<IntroItem>() {
        @Override
        public IntroItem createFromParcel(Parcel in) {
            return new IntroItem(in);
        }

        @Override
        public IntroItem[] newArray(int size) {
            return new IntroItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeInt(rawRes);
    }
}
