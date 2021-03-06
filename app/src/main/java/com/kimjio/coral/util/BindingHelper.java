package com.kimjio.coral.util;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kimjio.coral.R;
import com.kimjio.coral.data.nook.Fruit;
import com.kimjio.coral.data.nook.UserProfile;
import com.kimjio.coral.data.nook.ZodiacSign;
import com.kimjio.coral.nook.widget.PassportPatternLayout;
import com.kimjio.coral.nook.widget.PassportWaveView;
import com.kimjio.coral.widget.DrawableSizeTextView;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public final class BindingHelper {

    private BindingHelper() {
    }

    @BindingAdapter("srcUri")
    public static void setDrawable(ImageView view, Uri uri) {
        Glide.with(view)
                .load(uri)
                .transform(new RoundedCorners(ViewUtils.dpToPx(view.getContext(), 2)))
                .into(view);
    }

    @BindingAdapter("srcProfileUri")
    public static void setProfileDrawable(ImageView view, Uri uri) {
        Glide.with(view)
                .load(uri)
                .placeholder(R.drawable.ic_account)
                .error(Glide.with(view).load(R.drawable.ic_user_red).transform(new CropCircleWithBorderTransformation(ViewUtils.dpToPx(view.getContext(), 3), ContextCompat.getColor(view.getContext(), R.color.stroke_color))))
                .transform(new CropCircleWithBorderTransformation(ViewUtils.dpToPx(view.getContext(), 3), ContextCompat.getColor(view.getContext(), R.color.stroke_color)))
                .into(view);
    }

    @BindingAdapter({"srcCircleUri", "srcReported", "reported"})
    public static void setCircleDrawable(ImageView view, Uri srcCircleUri, Drawable srcReported, boolean reported) {
        if (srcCircleUri == null) {
            if (reported && srcReported != null) {
                view.setImageDrawable(srcReported);
            }
            return;
        }
        TypedValue value = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.colorSecondary, value, true);
        CircularProgressDrawable drawable = new CircularProgressDrawable(view.getContext());
        drawable.setColorSchemeColors(value.data);
        drawable.setStyle(CircularProgressDrawable.DEFAULT);
        drawable.start();
        Glide.with(view)
                .load(srcCircleUri)
                .transform(new CircleCrop())
                .placeholder(drawable)
                .into(view);
    }

    @BindingAdapter("drawableTopUri")
    public static void setDrawableTopUri(DrawableSizeTextView view, Uri uri) {
        Glide.with(view)
                .load(uri)
                .transform(new RoundedCorners(ViewUtils.dpToPx(view.getContext(), 2)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        view.post(() -> view.setCompoundDrawablesRelative(null, resource, null, null));
                        return true;
                    }
                })
                .submit();
    }

    @BindingAdapter("drawableStartUri")
    public static void setDrawableStartUri(DrawableSizeTextView view, Uri uri) {
        Glide.with(view)
                .load(uri)
                .placeholder(R.drawable.ic_account)
                .error(R.drawable.ic_account)
                .transform(new CircleCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        view.post(() -> view.setCompoundDrawablesRelative(resource, null, null, null));
                        return true;
                    }
                })
                .submit();
    }

    @BindingAdapter("drawableStartUriBorder")
    public static void setDrawableStartUriBorder(DrawableSizeTextView view, Uri uri) {
        Glide.with(view)
                .load(uri)
                .placeholder(R.drawable.ic_account)
                .error(R.drawable.ic_account)
                .transform(new CropCircleWithBorderTransformation(ViewUtils.dpToPx(view.getContext(), 1), view.getTextColors().getDefaultColor()))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        view.post(() -> view.setCompoundDrawablesRelative(resource, null, null, null));
                        return true;
                    }
                })
                .submit();
    }

    @BindingAdapter("lottie_rawResId")
    public static void setRawId(LottieAnimationView view, @RawRes int rawRes) {
        if (rawRes == 0) {
            Log.w("LottieBinding", "rawId is 0x0. Ignoring... ");
            return;
        }
        view.setAnimation(rawRes);
    }

    @BindingConversion
    public static int setVisibleByString(String text) {
        return TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE;
    }

    @BindingConversion
    public static int setVisibleByBoolean(boolean bool) {
        return bool ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("character")
    public static void setCharacter(TextView view, char character) {
        view.setText(Character.valueOf(character).toString());
    }

    @BindingAdapter({"fruit", "zodiacSign"})
    public static void setFruit(PassportWaveView view, Fruit fruit, ZodiacSign zodiacSign) {
        view.setFruit(fruit);
        view.setZodiacSign(zodiacSign);
        view.background();
    }

    @BindingAdapter("birth")
    public static void setBirthdayText(TextView view, UserProfile.Birth birth) {
        if (birth != null)
            view.setText(view.getContext().getString(R.string.birthday, birth.getMonth(), birth.getDay()));
    }

    @BindingAdapter("timeStamp")
    public static void setTimeStampText(TextView view, UserProfile.TimeStamp timeStamp) {
        if (timeStamp != null)
            view.setText(view.getContext().getString(R.string.registered_at, timeStamp.getYear(), timeStamp.getMonth(), timeStamp.getDay()));
    }

    @BindingAdapter("titleTheme")
    public static void setTitleThemeByZodiacSign(ImageView view, ZodiacSign zodiacSign) {
        if (zodiacSign != null)
            view.setImageDrawable(new ContextThemeWrapper(view.getContext(), zodiacSign.getTitleTheme()).getDrawable(R.drawable.passport_title));
    }

    @BindingAdapter("patternTheme")
    public static void setPatternThemeByZodiacSign(PassportPatternLayout view, ZodiacSign zodiacSign) {
        if (zodiacSign != null)
            view.background(zodiacSign.getPatternTheme());
    }
}
