package com.kimjio.coral.util;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.Dimension;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static int dpToPx(Context context, @Dimension(unit = Dimension.DP) float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
}
