package com.kimjio.coral.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import androidx.appcompat.widget.TooltipCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.kimjio.coral.R;

public class IconButtonPreference extends Preference {

    private Drawable mIcon;
    private CharSequence mContentDescription;

    public IconButtonPreference(Context context) {
        this(context, null);
    }

    public IconButtonPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconButtonPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.Preference_IconButton);
    }

    public IconButtonPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setSelectable(false);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconButtonPreference, defStyleAttr, defStyleRes);

        mIcon = a.getDrawable(R.styleable.IconButtonPreference_android_src);
        mContentDescription = a.getText(R.styleable.IconButtonPreference_contentDescription);

        a.recycle();
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        ImageButton button = holder.itemView.findViewById(R.id.iconButtonWidget);
        button.setImageDrawable(mIcon);
        button.setContentDescription(mContentDescription);
        button.setOnClickListener(v -> {
            PreferenceManager preferenceManager = getPreferenceManager();
            if (preferenceManager != null) {
                PreferenceManager.OnPreferenceTreeClickListener listener = preferenceManager
                        .getOnPreferenceTreeClickListener();
                if (listener != null) {
                    listener.onPreferenceTreeClick(this);
                }
            }
        });
        TooltipCompat.setTooltipText(button, mContentDescription);
    }
}
