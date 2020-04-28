package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.kimjio.coral.R;

public abstract class PassportWaveView extends View {
    private Paint maskPaint;
    private Paint gradientPaint;

    private int startColor;
    private int endColor;

    private final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = () -> {
        LinearGradient shader = new LinearGradient(0, 0, getMeasuredWidth(), 0, startColor, endColor, Shader.TileMode.CLAMP);
        gradientPaint.setShader(shader);
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

        startColor = a.getColor(R.styleable.PassportWaveView_startColor, Color.TRANSPARENT);
        endColor = a.getColor(R.styleable.PassportWaveView_endColor, Color.TRANSPARENT);

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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(gradientPaint);
        canvas.drawPaint(maskPaint);
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

    @DrawableRes
    protected abstract int getDrawableResource();
}
