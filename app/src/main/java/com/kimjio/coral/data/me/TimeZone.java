package com.kimjio.coral.data.me;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeZone implements Parcelable {
    private String id;
    private String name;
    private String utcOffset;
    private String utcOffsetSeconds;

    protected TimeZone(Parcel in) {
        id = in.readString();
        name = in.readString();
        utcOffset = in.readString();
        utcOffsetSeconds = in.readString();
    }

    public static final Creator<TimeZone> CREATOR = new Creator<TimeZone>() {
        @Override
        public TimeZone createFromParcel(Parcel in) {
            return new TimeZone(in);
        }

        @Override
        public TimeZone[] newArray(int size) {
            return new TimeZone[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(utcOffset);
        dest.writeString(utcOffsetSeconds);
    }
}
