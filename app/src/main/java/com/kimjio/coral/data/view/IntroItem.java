package com.kimjio.coral.data.view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RawRes;
import androidx.annotation.StringRes;

import com.kimjio.coral.R;

public class IntroItem implements Parcelable {
    @StringRes
    public final int titleRes;
    @StringRes
    public final int descRes;
    @RawRes
    public final int rawRes;

    public IntroItem(int titleRes, int descRes, int rawRes) {
        this.titleRes = titleRes;
        this.rawRes = rawRes;
        this.descRes = descRes;
    }

    public IntroItem(int titleRes, int rawRes) {
        this.titleRes = titleRes;
        this.rawRes = rawRes;
        this.descRes = R.string.gone;
    }

    private IntroItem(Parcel in) {
        titleRes = in.readInt();
        descRes = in.readInt();
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
        dest.writeInt(titleRes);
        dest.writeInt(descRes);
        dest.writeInt(rawRes);
    }
}
