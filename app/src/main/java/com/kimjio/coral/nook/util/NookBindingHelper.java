package com.kimjio.coral.nook.util;

import android.net.Uri;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.kimjio.coral.R;
import com.kimjio.coral.util.ViewUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public final class NookBindingHelper {

    private NookBindingHelper() {
    }

    @BindingAdapter("srcNookProfileUri")
    public static void setNookProfileDrawable(ImageView view, Uri uri) {
        TypedValue value = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.colorSecondary, value, true);
        CircularProgressDrawable drawable = new CircularProgressDrawable(view.getContext());
        drawable.setColorSchemeColors(value.data);
        drawable.setStyle(CircularProgressDrawable.DEFAULT);
        drawable.start();
        Glide.with(view)
                .load(uri)
                .transform(new RoundedCornersTransformation(ViewUtils.dpToPx(view.getContext(), 12), 0))
                .placeholder(drawable)
                .into(view);
    }
}
