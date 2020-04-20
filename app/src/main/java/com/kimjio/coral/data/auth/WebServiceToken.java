package com.kimjio.coral.data.auth;

import android.os.Parcel;
import android.os.Parcelable;

public class WebServiceToken extends WebApiServerCredential {
    protected WebServiceToken(Parcel in) {
        super(in);
    }

    public static final Creator<WebServiceToken> CREATOR = new Creator<WebServiceToken>() {
        @Override
        public WebServiceToken createFromParcel(Parcel in) {
            return new WebServiceToken(in);
        }

        @Override
        public WebServiceToken[] newArray(int size) {
            return new WebServiceToken[size];
        }
    };
}
