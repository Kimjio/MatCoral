package com.kimjio.coral.data.nook;

import android.net.Uri;

public class Reaction {
    private String label;
    private String name;
    private String url;

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Uri getUri() {
        return Uri.parse(url);
    }
}
