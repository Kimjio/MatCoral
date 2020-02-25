package com.kimjio.coral.data.auth;

import android.os.Parcel;
import android.os.Parcelable;

import com.kimjio.coral.data.BaseData;

public class WebApiServerCredential extends BaseData implements Parcelable {
    private String accessToken;
    private int expiresIn;

    protected WebApiServerCredential(Parcel in) {
        super(in);
        accessToken = in.readString();
        expiresIn = in.readInt();
    }

    public static final Creator<WebApiServerCredential> CREATOR = new Creator<WebApiServerCredential>() {
        @Override
        public WebApiServerCredential createFromParcel(Parcel in) {
            return new WebApiServerCredential(in);
        }

        @Override
        public WebApiServerCredential[] newArray(int size) {
            return new WebApiServerCredential[size];
        }
    };

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(accessToken);
        dest.writeInt(expiresIn);
    }
}
