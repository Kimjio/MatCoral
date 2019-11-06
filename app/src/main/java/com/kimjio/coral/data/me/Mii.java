package com.kimjio.coral.data.me;

import com.google.gson.annotations.SerializedName;

public class Mii {
    private String id;
    private String clientId;
    @SerializedName("etag")
    private String eTag;
    private String type;
    private long updatedAt;
    private String imageOrigin;
    private String imageUriTemplate;
    private String favoriteColor;
    private StoreData storeData;
    private CoreData coreData;

    class StoreData {
        @SerializedName("3")
        String data;
    }

    class CoreData {
        @SerializedName("4")
        String data;
    }
}
