package com.kimjio.coral.data.me;

import android.os.Parcel;
import android.os.Parcelable;

public class PermissionData implements Parcelable {
    private boolean permitted;
    private long updatedAt;

    protected PermissionData(Parcel in) {
        permitted = in.readByte() != 0;
        updatedAt = in.readLong();
    }

    public static final Creator<PermissionData> CREATOR = new Creator<PermissionData>() {
        @Override
        public PermissionData createFromParcel(Parcel in) {
            return new PermissionData(in);
        }

        @Override
        public PermissionData[] newArray(int size) {
            return new PermissionData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (permitted ? 1 : 0));
        dest.writeLong(updatedAt);
    }
}
