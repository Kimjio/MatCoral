package com.kimjio.coral.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GameWebService extends BaseData implements Parcelable {
    public static final long ID_SMASH = 0x13E3EF9E7E0000L;
    public static final long ID_SPLAT2 = 0x14657000000000L;
    public static final long ID_AC_NEW_HORIZON = 0x119990320F0000L;

    private String name; //Splatoon 2
    private String imageUri; //384 x 384
    private String uri; //SplatNet URL
    private List<String> whiteList; //redirect 허용 목록 또는 허용 주소

    public GameWebService() {
    }

    protected GameWebService(Parcel in) {
        super(in);
        name = in.readString();
        imageUri = in.readString();
        uri = in.readString();
        whiteList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(imageUri);
        dest.writeString(uri);
        dest.writeStringList(whiteList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameWebService> CREATOR = new Creator<GameWebService>() {
        @Override
        public GameWebService createFromParcel(Parcel in) {
            return new GameWebService(in);
        }

        @Override
        public GameWebService[] newArray(int size) {
            return new GameWebService[size];
        }
    };

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return Uri.parse(imageUri);
    }

    public Uri getUri() {
        return Uri.parse(uri);
    }

    public List<String> getWhiteList() {
        return whiteList;
    }
}
