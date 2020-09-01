package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.kimjio.coral.R;
import com.kimjio.coral.data.nook.Fruit;
import com.kimjio.coral.data.nook.ZodiacSign;
import com.kimjio.coral.util.ViewUtils;

public abstract class PassportWaveView extends FrameLayout {
    private Paint maskPaint;
    private Paint gradientPaint;

    private int startColor = Color.TRANSPARENT;
    private int endColor = Color.TRANSPARENT;

    private float cornerRadius;

    private ZodiacSign zodiacSign;
    private Fruit fruit;

    private final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = () -> {
        background();
        getViewTreeObserver().removeOnGlobalLayoutListener(this.globalLayoutListener);
    };

    public PassportWaveView(Context context) {
        this(context, null);
    }

    public PassportWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PassportWaveView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PassportWaveView, defStyle, 0);

        cornerRadius = a.getDimension(R.styleable.PassportWaveView_cornerRadius, 0);

        a.recycle();

        Shader maskShader = new BitmapShader(getBitmap(getResources().getDrawable(getDrawableResource(), null)), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        maskPaint.setShader(maskShader);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        setLayerType(LAYER_TYPE_HARDWARE, maskPaint);
        setLayerType(LAYER_TYPE_HARDWARE, gradientPaint);
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setZodiacSign(ZodiacSign zodiacSign) {
        this.zodiacSign = zodiacSign;
        if (getType() == Type.TOP)
            endColor = getEndColor();
        else
            startColor = getStartColor();
        background();
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
        if (getType() == Type.TOP)
            startColor = getStartColor();
        else
            endColor = getEndColor();
        background();
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        background();
    }

    protected int getStartColor() {
        String color = "#00000000";
        if (getType() == Type.TOP) {
            if (fruit == null) return Color.TRANSPARENT;
            switch (fruit.getType()) {
                case APPLE:
                    color = "#F9F1D8";
                    break;
                case ORANGE:
                    color = "#F9F4D4";
                    break;
                case PEAR:
                    color = "#F5F6D9";
                    break;
                case PEACH:
                    color = "#F9F0DD";
                    break;
                case CHERRY:
                    color = "#F9EED6";
                    break;
            }
        } else {
            if (zodiacSign == null) return Color.TRANSPARENT;
            switch (zodiacSign) {
                case ARIES:
                    color = "#F3F1DD";
                    break;
                case TAURUS:
                    color = "#EBF4D8";
                    break;
                case GEMINI:
                    color = "#ECEEE9";
                    break;
                case CANCER:
                    color = "#FCEEDB";
                    break;
                case LEO:
                    color = "#F3F6D4";
                    break;
                case VIRGO:
                    color = "#F2F0E9";
                    break;
                case LIBRA:
                    color = "#EAF6DF";
                    break;
                case SCORPIO:
                    color = "#FAF0D2";
                    break;
                case SAGITTARIUS:
                    color = "#E8F3E6";
                    break;
                case CAPRICORN:
                    color = "#FCECD9";
                    break;
                case AQUARIUS:
                    color = "#F8ECE8";
                    break;
                case PISCES:
                    color = "#EAF2E6";
                    break;
            }
        }
        return Color.parseColor(color);
    }

    protected int getEndColor() {
        String color = "#00000000";
        if (getType() == Type.BOTTOM) {
            if (fruit == null) return Color.TRANSPARENT;
            switch (fruit.getType()) {
                case APPLE:
                    color = "#FAE0CE";
                    break;
                case ORANGE:
                    color = "#FCEECE";
                    break;
                case PEAR:
                    color = "#E4F4CC";
                    break;
                case PEACH:
                case CHERRY:
                    color = "#FCE7DF";
                    break;
            }
        } else {
            if (zodiacSign == null) return Color.TRANSPARENT;
            switch (zodiacSign) {
                case ARIES:
                    color = "#F5F4DE";
                    break;
                case TAURUS:
                    color = "#EBF4D8";
                    break;
                case GEMINI:
                    color = "#F4F5DF";
                    break;
                case CANCER:
                    color = "#F8F4DF";
                    break;
                case LEO:
                    color = "#F5F5DE";
                    break;
                case VIRGO:
                    color = "#F5F4E2";
                    break;
                case LIBRA:
                    color = "#F3F5DF";
                    break;
                case SCORPIO:
                    color = "#F7F5DC";
                    break;
                case SAGITTARIUS:
                    color = "#F4F5E1";
                    break;
                case CAPRICORN:
                    color = "#F8F4E0";
                    break;
                case AQUARIUS:
                    color = "#F7F3E2";
                    break;
                case PISCES:
                    color = "#F3F4E1";
                    break;
            }
        }
        return Color.parseColor(color);
    }

    public void background() {
        if (getWidth() == 0 || getHeight() == 0) return;
        LinearGradient shader = new LinearGradient(0, 0, getMeasuredWidth(), 0, startColor, endColor, Shader.TileMode.CLAMP);
        gradientPaint.setShader(shader);
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float[] radii = new float[]{
                getType() == Type.TOP ? cornerRadius : 0, getType() == Type.TOP ? cornerRadius : 0,
                getType() == Type.TOP ? cornerRadius : 0, getType() == Type.TOP ? cornerRadius : 0,
                getType() == Type.BOTTOM ? cornerRadius : 0, getType() == Type.BOTTOM ? cornerRadius : 0,
                getType() == Type.BOTTOM ? cornerRadius : 0, getType() == Type.BOTTOM ? cornerRadius : 0
        };

        Path clipPath = new Path();
        clipPath.addRoundRect(new RectF(canvas.getClipBounds()), radii, Path.Direction.CW);
        canvas.clipPath(clipPath);

        canvas.drawPaint(gradientPaint);
        canvas.drawPaint(maskPaint);

        setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    @DrawableRes
    protected abstract int getDrawableResource();

    protected abstract Type getType();

    protected enum Type {
        TOP,
        BOTTOM
    }
}
