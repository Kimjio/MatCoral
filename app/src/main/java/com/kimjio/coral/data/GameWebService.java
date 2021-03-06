package com.kimjio.coral.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWebService extends BaseData implements Parcelable {
    public static final long ID_SMASH = 0x13E3EF9E7E0000L;
    public static final long ID_SPLAT2 = 0x14657000000000L;
    public static final long ID_AC_NEW_HORIZON = 0x119990320F0000L;

    private String name; //ex. Splatoon 2
    private String imageUri; //size. 384 x 384
    private String uri; //ex. SplatNet URL
    private List<String> whiteList; //WebView redirect 허용 주소들(와일드카드 포함); 사용 안함
    private List<Attribute> customAttributes;

    protected GameWebService(Parcel in) {
        super(in);
        name = in.readString();
        imageUri = in.readString();
        uri = in.readString();
        whiteList = in.createStringArrayList();
        customAttributes = in.createTypedArrayList(Attribute.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(imageUri);
        dest.writeString(uri);
        dest.writeStringList(whiteList);
        dest.writeTypedList(customAttributes);
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

    public List<Attribute> getCustomAttributeList() {
        return customAttributes;
    }

    public Map<String, Object> getCustomAttributes() {
        Map<String, Object> map = new HashMap<>();
        for (Attribute attribute : customAttributes) {
            map.put(attribute.getKey(), attribute.getValue(Attribute.getTypeByKey(attribute.getKey())));
        }
        return map;
    }
}
