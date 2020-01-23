package com.kimjio.coral.splat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kimjio.coral.R;

import java.lang.reflect.Field;
import java.util.Objects;

public class SplatSwipeRefreshLayout extends SwipeRefreshLayout {
    public SplatSwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SplatSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setColorSchemeResources(R.color.colorPrimarySplat, R.color.colorSecondarySplat);
    }

    private void init() {
        try {
            Field circleViewField = Objects.requireNonNull(getClass().getSuperclass()).getDeclaredField("mCircleView");
            circleViewField.setAccessible(true);
            Field progressField = getClass().getSuperclass().getDeclaredField("mProgress");
            progressField.setAccessible(true);
            SplatCircularProgressDrawable progressDrawable = new SplatCircularProgressDrawable(getContext());
            progressField.set(this, progressDrawable);
            ImageView imageView = (ImageView) circleViewField.get(this);
            Objects.requireNonNull(imageView).setImageDrawable((Drawable) progressField.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
