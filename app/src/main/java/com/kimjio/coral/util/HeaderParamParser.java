package com.kimjio.coral.util;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.ArrayList;
import java.util.Map;

public final class HeaderParamParser {
    private String param;
    private ParamList params = new ParamList();

    public HeaderParamParser(@NonNull String param) {
        if (param == null) throw new IllegalArgumentException("param == null");
        this.param = param;
        parse();
    }

    private void parse() {
        String[] paramArr = param.split(";");
        for (String param : paramArr) {
            String[] keyValue = param.split("=");
            if (keyValue.length < 2)
                params.add(new Param(keyValue[0]));
            else
                params.add(new Param(keyValue[0], keyValue[1]));
        }
    }

    public ParamList getParams() {
        return params;
    }

    public static class Param {
        private String key;
        private String value;

        private Param(String key) {
            this.key = key;
        }

        private Param(String key, String value) {
            this(key);
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ParamList extends ArrayList<Param> {
        public int indexOfContainsKey(@NonNull String key) {
            int index = -1;
            for (int i = 0; i < size(); i++) {
                if (get(i).key.contains(key)) {
                    index = i;
                    break;
                }
            }
            return index;
        }

        public int indexOfKeyLowerCase(@NonNull String key) {
            int index = -1;
            for (int i = 0; i < size(); i++) {
                if (get(i).key.toLowerCase().equals(key)) {
                    index = i;
                    break;
                }
            }
            return index;
        }

        public boolean containsKey(@NonNull String key) {
            boolean equals = false;
            for (Param p : this) {
                equals = p.key.contains(key);
                if (equals) break;
            }
            return equals;
        }
    }
}
