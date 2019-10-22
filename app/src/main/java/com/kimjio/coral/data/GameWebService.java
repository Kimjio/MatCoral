package com.kimjio.coral.data;

import java.util.List;

/**
 * ex. SplatNet
 */
public class GameWebService extends BaseData {
    public String name;
    public String imageUrl; //384 x 384
    public String url;
    public List<String> whiteList; //redirect 허용 목록 또는 허용 주소
}
