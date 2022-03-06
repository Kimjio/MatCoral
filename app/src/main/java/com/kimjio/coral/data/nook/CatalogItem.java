package com.kimjio.coral.data.nook;

import androidx.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatalogItem {
    // 0, 1
    @SerializedName("can_sell")
    private int canSell;

    // NotForSale, NotSee,
    @SerializedName("catalog_type")
    private String catalogType;

    @SerializedName("fashion_theme")
    private List<String> fashionTheme;

    private String color1;
    private String color2;
    private String from;

    @SerializedName("hha_theme")
    private int hhaTheme;

    @SerializedName("icon")
    private String icon;

    @SerializedName("is_tool_category")
    private boolean isToolCategory;

    @SerializedName("item_fossil_set_id")
    private int itemFossilSetId;

    @SerializedName("item_size_id")
    private String itemSizeId;

    @SerializedName("kind_id")
    private String kindId;

    private String label;
    private int price;
    private boolean remakable;

    @SerializedName("shop_remakable")
    private boolean shopRemakable;

    @SerializedName("small_genre")
    private String smallGenre;

    @SerializedName("ui_category")
    private String uiCategory;

    @SerializedName("unique_id")
    private int uniqueId;
}

class RegionEvent {
    private List<Country> countries;
    private String name;
    private List<Period> period;
}

class Country {

}

class Period {

}
