package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.FrameLayout;

import com.kimjio.coral.R;
import com.kimjio.coral.nook.drawable.TileDrawable;

public class PassportPatternLayout extends FrameLayout {

    public PassportPatternLayout(Context context) {
        this(context, null);
        init(null, 0);
    }

    public PassportPatternLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs, 0);
    }

    public PassportPatternLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PassportPatternLayout, defStyle, 0);

        Drawable drawable = getResources().getDrawable(R.drawable.passport_pattern, new ContextThemeWrapper(getContext(),
                a.getResourceId(R.styleable.PassportPatternLayout_passportPattern, R.style.Pattern)).getTheme());
        setBackground(new TileDrawable(drawable, Shader.TileMode.REPEAT));

        a.recycle();
    }
}