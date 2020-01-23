package com.kimjio.coral.data.splat;

import com.google.gson.annotations.SerializedName;

public class Brand {
    private String id;
    private String name;
    private String image;
    @SerializedName("frequent_skill")
    private Skill frequentSkill;
}
