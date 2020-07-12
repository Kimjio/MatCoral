package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.StyleRes;
import androidx.appcompat.view.ContextThemeWrapper;

import com.kimjio.coral.R;
import com.kimjio.coral.nook.drawable.TileDrawable;

public class PassportPatternLayout extends FrameLayout {

    public PassportPatternLayout(Context context) {
        this(context, null);
    }

    public PassportPatternLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PassportPatternLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void background(@StyleRes int theme) {
        Drawable drawable = getResources().getDrawable(R.drawable.passport_pattern, new ContextThemeWrapper(getContext(), theme).getTheme());
        setBackground(new TileDrawable(drawable, Shader.TileMode.REPEAT));
    }
}