package com.kimjio.coral.data.splat;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reward {
    private int id;
    private List<Image> images;
    @SerializedName("paint_points")
    private int paintPoints;

    public int getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public int getPaintPoints() {
        return paintPoints;
    }

    public static class Image {
        private String thumbnail;
        private String url;

        public String getThumbnail() {
            return thumbnail;
        }

        public String getUrl() {
            return url;
        }

        public Uri getThumbnailUri() {
            return Uri.parse(thumbnail);
        }

        public Uri getUri() {
            return Uri.parse(url);
        }
    }
}
