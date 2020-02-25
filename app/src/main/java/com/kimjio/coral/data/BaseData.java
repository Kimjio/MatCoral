package com.kimjio.coral.data;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseData implements Parcelable {
    private long id;

    public BaseData() {
    }

    protected BaseData(Parcel in) {
        id = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
