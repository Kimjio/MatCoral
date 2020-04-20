package com.kimjio.coral.data.me;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectableData implements Parcelable {
    private boolean optedIn;
    private long updatedAt;

    protected SelectableData(Parcel in) {
        optedIn = in.readByte() != 0;
        updatedAt = in.readLong();
    }

    public static final Creator<SelectableData> CREATOR = new Creator<SelectableData>() {
        @Override
        public SelectableData createFromParcel(Parcel in) {
            return new SelectableData(in);
        }

        @Override
        public SelectableData[] newArray(int size) {
            return new SelectableData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (optedIn ? 1 : 0));
        dest.writeLong(updatedAt);
    }
}
