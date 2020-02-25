package com.kimjio.coral.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.kimjio.coral.R;

public class DrawableSizeTextView extends AppCompatTextView {
    private static final int LEFT = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;
    private static final int START = LEFT;
    private static final int END = RIGHT;

    private int drawableWidth, drawableHeight;
    private int drawableTopWidth, drawableTopHeight;
    private int drawableBottomWidth, drawableBottomHeight;
    private int drawableStartWidth, drawableStartHeight;
    private int drawableEndWidth, drawableEndHeight;
    private int drawableLeftWidth, drawableLeftHeight;
    private int drawableRightWidth, drawableRightHeight;

    public DrawableSizeTextView(Context context) {
        this(context, null);
    }

    public DrawableSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public DrawableSizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DrawableSizeTextView, defStyle, 0);

        drawableWidth = a.getDimensionPixelSize(
                R.styleable.DrawableSizeTextView_drawableTopWidth,
                0);
        drawableHeight = a.getDimensionPixelSize(
                R.styleable.DrawableSizeTextView_drawableTopHeight,
                0);
        drawableTopWidth = a.getDimensionPixelSize(
                R.styleable.DrawableSizeTextView_drawableTopWidth,
                drawableWidth);
        drawableTopHeight = a.getDimensionPixelSize(
                R.styleable.DrawableSizeTextView_drawableTopHeight,
                drawableHeight);
        drawableBottomWidth = a.getDimensionPixelSize(
                R.styleable.DrawableSizeTextView_drawableBottomWidth,
                drawableWidth);
        drawableBottomHeight = a.getDimensionPixelSize(
                R.styleable.DrawableSizeTextView_drawableBottomHeight,
                drawableHeight);

        if (a.hasValue(R.styleable.DrawableSizeTextView_drawableStartWidth)) {
            drawableStartWidth = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableStartWidth,
                    drawableWidth);

            switch (getResources().getConfiguration().getLayoutDirection()) {
                case LAYOUT_DIRECTION_LTR:
                    drawableLeftWidth = drawableStartWidth;
                    break;
                case LAYOUT_DIRECTION_RTL:
                    drawableRightWidth = drawableStartWidth;
                    break;
            }
        }
        if (a.hasValue(R.styleable.DrawableSizeTextView_drawableStartHeight)) {
            drawableStartHeight = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableStartHeight,
                    drawableHeight);

            switch (getResources().getConfiguration().getLayoutDirection()) {
                case LAYOUT_DIRECTION_LTR:
                    drawableLeftHeight = drawableStartHeight;
                    break;
                case LAYOUT_DIRECTION_RTL:
                    drawableRightHeight = drawableStartHeight;
                    break;
            }
        }
        if (a.hasValue(R.styleable.DrawableSizeTextView_drawableEndWidth)) {
            drawableEndWidth = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableEndWidth,
                    drawableWidth);

            switch (getResources().getConfiguration().getLayoutDirection()) {
                case LAYOUT_DIRECTION_LTR:
                    drawableRightWidth = drawableEndWidth;
                    break;
                case LAYOUT_DIRECTION_RTL:
                    drawableLeftWidth = drawableEndWidth;
                    break;
            }
        }
        if (a.hasValue(R.styleable.DrawableSizeTextView_drawableEndHeight)) {
            drawableEndHeight = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableEndHeight,
                    drawableHeight);

            switch (getResources().getConfiguration().getLayoutDirection()) {
                case LAYOUT_DIRECTION_LTR:
                    drawableRightHeight = drawableEndHeight;
                    break;
                case LAYOUT_DIRECTION_RTL:
                    drawableLeftHeight = drawableEndHeight;
                    break;
            }
        }

        if (drawableLeftWidth == 0)
            drawableLeftWidth = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableLeftWidth,
                    drawableWidth);
        if (drawableLeftHeight == 0)
            drawableLeftHeight = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableLeftHeight,
                    drawableHeight);
        if (drawableRightWidth == 0)
            drawableRightWidth = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableRightWidth,
                    drawableWidth);
        if (drawableRightHeight == 0)
            drawableRightHeight = a.getDimensionPixelSize(
                    R.styleable.DrawableSizeTextView_drawableRightHeight,
                    drawableHeight);

        a.recycle();

        applyDrawablesSize();
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        return null;
    }

    private void applyDrawablesSize() {
        Drawable[] drawables = getCompoundDrawables();
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        if (drawablesRelative[START] != null) {
            switch (getResources().getConfiguration().getLayoutDirection()) {
                case LAYOUT_DIRECTION_LTR:
                    drawables[LEFT] = drawablesRelative[START];
                    break;
                case LAYOUT_DIRECTION_RTL:
                    drawables[RIGHT] = drawablesRelative[START];
                    break;
            }
        }
        if (drawablesRelative[END] != null) {
            switch (getResources().getConfiguration().getLayoutDirection()) {
                case LAYOUT_DIRECTION_LTR:
                    drawables[RIGHT] = drawablesRelative[END];
                    break;
                case LAYOUT_DIRECTION_RTL:
                    drawables[LEFT] = drawablesRelative[END];
                    break;
            }
        }
        setDrawableSize(drawables[TOP], drawableTopWidth, drawableTopHeight);
        setDrawableSize(drawables[BOTTOM], drawableBottomWidth, drawableBottomHeight);
        setDrawableSize(drawables[LEFT], drawableStartWidth, drawableStartHeight);
        setDrawableSize(drawables[RIGHT], drawableEndWidth, drawableEndHeight);
        super.setCompoundDrawables(drawables[LEFT], drawables[TOP], drawables[RIGHT], drawables[BOTTOM]);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        setDrawableSize(top, drawableTopWidth, drawableTopHeight);
        setDrawableSize(bottom, drawableBottomWidth, drawableBottomHeight);
        setDrawableSize(left, drawableStartWidth, drawableStartHeight);
        setDrawableSize(right, drawableEndWidth, drawableEndHeight);
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        setDrawableSize(top, drawableTopWidth, drawableTopHeight);
        setDrawableSize(bottom, drawableBottomWidth, drawableBottomHeight);
        setDrawableSize(start, drawableStartWidth, drawableStartHeight);
        setDrawableSize(end, drawableEndWidth, drawableEndHeight);
        super.setCompoundDrawablesRelative(start, top, end, bottom);
    }

    private void setDrawableSize(Drawable drawable, int width, int height) {
        if (drawable == null) return;
        if (width > 0 || height > 0) {
            Rect bounds = drawable.getBounds();

            bounds.right = bounds.left + width;
            bounds.bottom = bounds.top + height;

            drawable.setBounds(bounds);
        }
    }

    public float getDrawableWidth() {
        return drawableWidth;
    }

    public void setDrawableWidth(int drawableWidth) {
        this.drawableWidth = drawableWidth;
        invalidate();
    }

    public float getDrawableHeight() {
        return drawableHeight;
    }

    public void setDrawableHeight(int drawableHeight) {
        this.drawableHeight = drawableHeight;
        invalidate();
    }

    public float getDrawableTopWidth() {
        return drawableTopWidth;
    }

    public void setDrawableTopWidth(int drawableTopWidth) {
        this.drawableTopWidth = drawableTopWidth;
        invalidate();
    }

    public float getDrawableTopHeight() {
        return drawableTopHeight;
    }

    public void setDrawableTopHeight(int drawableTopHeight) {
        this.drawableTopHeight = drawableTopHeight;
        invalidate();
    }

    public float getDrawableBottomWidth() {
        return drawableBottomWidth;
    }

    public void setDrawableBottomWidth(int drawableBottomWidth) {
        this.drawableBottomWidth = drawableBottomWidth;
        invalidate();
    }

    public float getDrawableBottomHeight() {
        return drawableBottomHeight;
    }

    public void setDrawableBottomHeight(int drawableBottomHeight) {
        this.drawableBottomHeight = drawableBottomHeight;
        invalidate();
    }

    public float getDrawableStartWidth() {
        return drawableStartWidth;
    }

    public void setDrawableStartWidth(int drawableStartWidth) {
        this.drawableStartWidth = drawableStartWidth;
        invalidate();
    }

    public float getDrawableStartHeight() {
        return drawableStartHeight;
    }

    public void setDrawableStartHeight(int drawableStartHeight) {
        this.drawableStartHeight = drawableStartHeight;
        invalidate();
    }

    public float getDrawableEndWidth() {
        return drawableEndWidth;
    }

    public void setDrawableEndWidth(int drawableEndWidth) {
        this.drawableEndWidth = drawableEndWidth;
        invalidate();
    }

    public float getDrawableEndHeight() {
        return drawableEndHeight;
    }

    public void setDrawableEndHeight(int drawableEndHeight) {
        this.drawableEndHeight = drawableEndHeight;
        invalidate();
    }

    public float getDrawableLeftWidth() {
        return drawableLeftWidth;
    }

    public void setDrawableLeftWidth(int drawableLeftWidth) {
        this.drawableLeftWidth = drawableLeftWidth;
        invalidate();
    }

    public float getDrawableLeftHeight() {
        return drawableLeftHeight;
    }

    public void setDrawableLeftHeight(int drawableLeftHeight) {
        this.drawableLeftHeight = drawableLeftHeight;
        invalidate();
    }

    public float getDrawableRightWidth() {
        return drawableRightWidth;
    }

    public void setDrawableRightWidth(int drawableRightWidth) {
        this.drawableRightWidth = drawableRightWidth;
        invalidate();
    }

    public float getDrawableRightHeight() {
        return drawableRightHeight;
    }

    public void setDrawableRightHeight(int drawableRightHeight) {
        this.drawableRightHeight = drawableRightHeight;
        invalidate();
    }
}
