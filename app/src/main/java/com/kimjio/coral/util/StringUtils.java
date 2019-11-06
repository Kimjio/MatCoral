package com.kimjio.coral.util;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;

public final class StringUtils {

    public static final String UTF_8 = Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 ? "UTF8" : StandardCharsets.UTF_8.toString();

    private StringUtils() {
    }

    public static String getEncodedString(String s) {
        try {
            return URLEncoder.encode(s, UTF_8);
        } catch (UnsupportedEncodingException | IllegalCharsetNameException ignore) {
        }
        return s;
    }

    public static String getAppendedString(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for (String s : arr) {
            builder.append(s);
        }
        return builder.toString();
    }
}
