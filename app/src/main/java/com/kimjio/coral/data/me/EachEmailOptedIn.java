package com.kimjio.coral.data.me;

import android.os.Parcel;
import android.os.Parcelable;

public class EachEmailOptedIn implements Parcelable {
    private SelectableData deals;
    private SelectableData survey;

    protected EachEmailOptedIn(Parcel in) {
        deals = in.readParcelable(SelectableData.class.getClassLoader());
        survey = in.readParcelable(SelectableData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(deals, flags);
        dest.writeParcelable(survey, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EachEmailOptedIn> CREATOR = new Creator<EachEmailOptedIn>() {
        @Override
        public EachEmailOptedIn createFromParcel(Parcel in) {
            return new EachEmailOptedIn(in);
        }

        @Override
        public EachEmailOptedIn[] newArray(int size) {
            return new EachEmailOptedIn[size];
        }
    };
}
