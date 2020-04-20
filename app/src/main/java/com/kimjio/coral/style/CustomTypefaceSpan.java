package com.kimjio.coral.style;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class CustomTypefaceSpan extends MetricAffectingSpan {

    private Typeface typeface;

    public CustomTypefaceSpan(@NonNull Context context, @FontRes int res) {
        this.typeface = ResourcesCompat.getFont(context, res);
    }

    public CustomTypefaceSpan(@NonNull Typeface typeface) {
        this.typeface = typeface;
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint textPaint) {
        textPaint.setTypeface(typeface);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(typeface);
    }
}
