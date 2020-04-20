package com.kimjio.coral.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * NSO 구독 여부
 */
public class Membership extends BaseData {
    private boolean isActive; //구독 여부

    public Membership() {
    }

    protected Membership(Parcel in) {
        super(in);
        isActive = in.readByte() != 0;
    }

    public static final Creator<Membership> CREATOR = new Creator<Membership>() {
        @Override
        public Membership createFromParcel(Parcel in) {
            return new Membership(in);
        }

        @Override
        public Membership[] newArray(int size) {
            return new Membership[size];
        }
    };

    public boolean isActive() {
        return isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (isActive ? 1 : 0));
    }
}
