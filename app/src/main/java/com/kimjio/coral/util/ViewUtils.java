package com.kimjio.coral.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static int dpToPx(Context context, @Dimension(unit = Dimension.DP) float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    public static BitmapDrawable getBitmapDrawableFromVectorDrawable(Context context, @DrawableRes int drawableId) {
        return new BitmapDrawable(context.getResources(), getBitmapFromVectorDrawable(context, drawableId));
    }

    public static BitmapDrawable getBitmapDrawableFromVectorDrawable(Context context, Drawable drawable) {
        return new BitmapDrawable(context.getResources(), getBitmapFromVectorDrawable(drawable));
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);

        Objects.requireNonNull(drawable);

        return getBitmapFromVectorDrawable(drawable);
    }

    public static Bitmap getBitmapFromVectorDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
