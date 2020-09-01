package com.kimjio.coral.data;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.InvocationTargetException;

public class Attribute implements Parcelable {
    public static final String KEY_STATUS_BAR_COLOR = "appStatusBarBgColor";
    public static final String KEY_NAVIGATION_BAR_COLOR = "appNavigationBgColor";
    public static final String KEY_VERIFY_MEMBERSHIP = "verifyMembership";

    public static final Class<Boolean> TYPE_BOOLEAN = Boolean.class;
    public static final Class<String> TYPE_STRING = String.class;
    public static final Class<Color> TYPE_COLOR = Color.class;

    private String attrKey;
    private String attrValue;

    public Attribute() {
    }

    protected Attribute(Parcel in) {
        attrKey = in.readString();
        attrValue = in.readString();
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };

    public String getKey() {
        return attrKey;
    }

    public void setKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public <T> T getValue(Class<T> type) {
        if (type == TYPE_STRING) return type.cast(attrValue);
        try {
            if (type == TYPE_COLOR) {
                return type.cast(type.getDeclaredMethod("valueOf", int.class).invoke(type, (int) type.getDeclaredMethod("parseColor", String.class).invoke(Color.class, "#" + attrValue)));
            }
            return type.cast(type.getDeclaredMethod("valueOf", String.class).invoke(type, attrValue));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public static Class<?> getTypeByKey(String key) {
        switch (key) {
            case KEY_VERIFY_MEMBERSHIP:
                return TYPE_BOOLEAN;
            case KEY_STATUS_BAR_COLOR:
            case KEY_NAVIGATION_BAR_COLOR:
                return TYPE_COLOR;
        }
        return TYPE_STRING;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attrKey);
        dest.writeString(attrValue);
    }
}
