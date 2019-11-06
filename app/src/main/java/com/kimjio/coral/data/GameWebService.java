package com.kimjio.coral.data;

import java.util.List;

public class GameWebService extends BaseData {
    private String name; //Splatoon 2
    private String imageUrl; //384 x 384
    private String uri; //SplatNet URL
    private List<String> whiteList; //redirect 허용 목록 또는 허용 주소

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUri() {
        return uri;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }
}
