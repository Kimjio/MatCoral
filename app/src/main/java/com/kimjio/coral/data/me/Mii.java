package com.kimjio.coral.data.me;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Mii implements Parcelable {
    private String id;
    private String clientId;
    @SerializedName("etag")
    private String eTag;
    private String type;
    private long updatedAt;
    private String imageOrigin;
    private String imageUriTemplate;
    private String favoriteColor;
    private Map<String, String> storeData;
    private Map<String, String> coreData;


    protected Mii(Parcel in) {
        id = in.readString();
        clientId = in.readString();
        eTag = in.readString();
        type = in.readString();
        updatedAt = in.readLong();
        imageOrigin = in.readString();
        imageUriTemplate = in.readString();
        favoriteColor = in.readString();
        in.readMap(storeData = new HashMap<>(), String.class.getClassLoader());
        in.readMap(coreData = new HashMap<>(), String.class.getClassLoader());
    }

    public static final Creator<Mii> CREATOR = new Creator<Mii>() {
        @Override
        public Mii createFromParcel(Parcel in) {
            return new Mii(in);
        }

        @Override
        public Mii[] newArray(int size) {
            return new Mii[size];
        }
    };

    public Uri getMiiUri() {
        return Uri.parse(String.format("https://cdn-mii.accounts.nintendo.com/2.0.0/mii_images/%s/%s.png?type=face_only&width=96&bgColor=00000000", id, eTag));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(clientId);
        dest.writeString(eTag);
        dest.writeString(type);
        dest.writeLong(updatedAt);
        dest.writeString(imageOrigin);
        dest.writeString(imageUriTemplate);
        dest.writeString(favoriteColor);
        dest.writeMap(storeData);
        dest.writeMap(coreData);
    }
    /*class StoreData {
        @SerializedName("3")
        String data;
    }

    class CoreData {
        @SerializedName("4")
        String data;
    }*/
}
