package com.kimjio.coral.data.splat;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Objects;

public class Festival {
    public static final String TEAM_ALPHA = "alpha";
    public static final String TEAM_BRAVO = "bravo";
    public static final String TEAM_NONE = "middle";

    public static final String IMAGE_PANEL = "panel";

    public static final String TIME_ANNOUNCE = "announce";
    public static final String TIME_START = "start";
    public static final String TIME_END = "end";
    public static final String TIME_RESULT = "result";

    @SerializedName("festival_id")
    private int id;
    private Map<String, Color> colors;
    private Map<String, String> images;
    private Map<String, String> names;
    @SerializedName("special_stage")
    private Stage specialStage;
    private Map<String, Long> times;

    public int getId() {
        return id;
    }

    public Stage getSpecialStage() {
        return specialStage;
    }

    @NonNull
    public Color getTeamColor(@Team String team) {
        return Objects.requireNonNull(colors.get(team));
    }

    @NonNull
    public String getImage(@Image String image) {
        return Objects.requireNonNull(images.get(image));
    }

    public String getTeamNameLong(@Team String team) {
        return names.get(team + "_long");
    }

    public String getTeamNameShort(@Team String team) {
        return names.get(team + "_short");
    }

    public long getTime(@Time String time) {
        Long timestamp = times.get(time);
        if (timestamp == null) return 0;
        return timestamp;
    }

    @StringDef({TEAM_ALPHA, TEAM_BRAVO, TEAM_NONE})
    public @interface Team {
    }

    @StringDef({TEAM_ALPHA, TEAM_BRAVO, IMAGE_PANEL})
    public @interface Image {
    }

    @StringDef({TIME_ANNOUNCE, TIME_START, TIME_END, TIME_RESULT})
    public @interface Time {
    }


}
