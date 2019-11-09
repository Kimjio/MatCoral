package com.kimjio.coral.data.me;

import android.os.Parcel;
import android.os.Parcelable;

public class AnalyticsPermissions implements Parcelable {
    private PermissionData targetMarketing;
    private PermissionData internalAnalysis;

    protected AnalyticsPermissions(Parcel in) {
        targetMarketing = in.readParcelable(PermissionData.class.getClassLoader());
        internalAnalysis = in.readParcelable(PermissionData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(targetMarketing, flags);
        dest.writeParcelable(internalAnalysis, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnalyticsPermissions> CREATOR = new Creator<AnalyticsPermissions>() {
        @Override
        public AnalyticsPermissions createFromParcel(Parcel in) {
            return new AnalyticsPermissions(in);
        }

        @Override
        public AnalyticsPermissions[] newArray(int size) {
            return new AnalyticsPermissions[size];
        }
    };
}
