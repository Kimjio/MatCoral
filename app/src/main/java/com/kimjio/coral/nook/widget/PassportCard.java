package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.kimjio.coral.R;
import com.kimjio.coral.data.nook.LandProfile;
import com.kimjio.coral.data.nook.UserProfile;
import com.kimjio.coral.util.ViewUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PassportCard extends MaterialCardView {
    private PassportWaveTopView waveTopView;
    private PassportWaveBottomView waveBottomView;
    private ImageView titleImage;
    private PassportPatternLayout patternLayout;
    private ImageView profileView;
    private TextView nameView;
    private TextView handleView;
    private ImageView zodiacView;
    private TextView birthView;
    private TextView landView;
    private ImageView fruitIconView;
    private TextView fruitView;
    private TextView registeredAtView;

    public PassportCard(Context context) {
        this(context, null);
    }

    public PassportCard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PassportCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context = new ContextThemeWrapper(context, R.style.Theme_App_Nook), attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        @ColorInt final int backgroundColor = Color.parseColor("#FAF7DA");
        @ColorInt final int textColor1 = Color.parseColor("#4D3906");
        @ColorInt final int textColor2 = Color.parseColor("#938666");
        final float cardRadius = ViewUtils.dpToPx(context, 24);
        setCardBackgroundColor(backgroundColor);
        setRadius(cardRadius);
        MaterialCardView innerCard = new MaterialCardView(context);
        innerCard.setCardBackgroundColor(backgroundColor);
        innerCard.setRadius(cardRadius);
        innerCard.setCardElevation(0);

        {
            waveTopView = new PassportWaveTopView(context);
            waveTopView.setCornerRadius(ViewUtils.dpToPx(context, 24));

            titleImage = new AppCompatImageView(context);
            titleImage.setImageResource(R.drawable.passport_title);
            LayoutParams titleParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleParam.gravity = Gravity.CENTER;
            titleImage.setLayoutParams(titleParam);

            waveTopView.addView(titleImage);
            LayoutParams topParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dpToPx(context, 24));
            topParams.gravity = Gravity.TOP;
            waveTopView.setLayoutParams(topParams);
            innerCard.addView(waveTopView);
        }

        {
            patternLayout = new PassportPatternLayout(context);
            ConstraintLayout layout = new ConstraintLayout(context);

            CardView imageProfileWrapper = new CardView(context);
            imageProfileWrapper.setId(generateViewId());
            imageProfileWrapper.setCardBackgroundColor(backgroundColor);
            imageProfileWrapper.setRadius(ViewUtils.dpToPx(context, 16));
            imageProfileWrapper.setElevation(0);
            {
                profileView = new AppCompatImageView(context);
                final int profilePadding = ViewUtils.dpToPx(context, 5);
                profileView.setPadding(profilePadding, profilePadding, profilePadding, profilePadding);
                profileView.setContentDescription(context.getString(R.string.residents_thumbnail));
                final int profileSize = ViewUtils.dpToPx(context, 86);
                TypedValue value = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.colorSecondary, value, true);
                CircularProgressDrawable drawable = new CircularProgressDrawable(context);
                drawable.setColorSchemeColors(value.data);
                drawable.setStyle(CircularProgressDrawable.DEFAULT);
                drawable.start();
                profileView.setImageDrawable(drawable);
                LayoutParams profileParams = new LayoutParams(profileSize, profileSize);
                profileView.setLayoutParams(profileParams);
                imageProfileWrapper.addView(profileView);
            }

            ConstraintLayout.LayoutParams cardParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cardParams.setMarginStart(ViewUtils.dpToPx(context, 16));
            cardParams.topMargin = ViewUtils.dpToPx(context, 12);
            cardParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            cardParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            imageProfileWrapper.setLayoutParams(cardParams);
            layout.addView(imageProfileWrapper);

            nameView = new AppCompatTextView(context);
            nameView.setId(generateViewId());
            nameView.setPadding(0, ViewUtils.dpToPx(context, 4), 0, ViewUtils.dpToPx(context, 6));
            nameView.setTextColor(textColor1);
            nameView.setTextSize(20);
            ConstraintLayout.LayoutParams nameParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            nameParams.startToStart = imageProfileWrapper.getId();
            nameParams.topToBottom = imageProfileWrapper.getId();
            nameView.setLayoutParams(nameParams);
            layout.addView(nameView);

            AppCompatImageView palmView = new AppCompatImageView(context);
            palmView.setId(generateViewId());
            palmView.setImageResource(R.drawable.passport_palmtree);
            final int palmSize = ViewUtils.dpToPx(context, 88);
            ConstraintLayout.LayoutParams palmParams = new ConstraintLayout.LayoutParams(palmSize, palmSize);
            palmParams.setMarginEnd(ViewUtils.dpToPx(context, 6));
            palmParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            palmParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            palmView.setLayoutParams(palmParams);
            layout.addView(palmView);

            View divider1 = new View(context);
            divider1.setId(generateViewId());
            divider1.setBackgroundColor(backgroundColor);
            ConstraintLayout.LayoutParams divider1Params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ViewUtils.dpToPx(context, 2));
            divider1Params.startToStart = nameView.getId();
            divider1Params.topToBottom = nameView.getId();
            divider1Params.endToStart = palmView.getId();
            divider1.setLayoutParams(divider1Params);
            layout.addView(divider1);

            LinearLayout birthLayout = new LinearLayout(context);
            birthLayout.setId(generateViewId());
            birthLayout.setOrientation(LinearLayout.HORIZONTAL);
            {
                zodiacView = new AppCompatImageView(context);
                zodiacView.setId(generateViewId());
                zodiacView.setLayoutParams(new LinearLayout.LayoutParams(ViewUtils.dpToPx(context, 16), ViewUtils.dpToPx(context, 16)));
                birthLayout.addView(zodiacView);
                birthView = new AppCompatTextView(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    birthView.setTextAppearance(R.style.TextAppearance_MaterialComponents_Caption);
                } else {
                    birthView.setTextAppearance(context, R.style.TextAppearance_MaterialComponents_Caption);
                }
                birthView.setTextColor(textColor2);
                LinearLayout.LayoutParams birthParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                birthParams.setMarginStart(ViewUtils.dpToPx(context, 4));
                birthView.setLayoutParams(birthParams);
                birthLayout.addView(birthView);
            }
            ConstraintLayout.LayoutParams birthParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            birthParams.topMargin = ViewUtils.dpToPx(context, 4);
            birthParams.startToStart = nameView.getId();
            birthParams.topToBottom = divider1.getId();
            birthLayout.setLayoutParams(birthParams);
            layout.addView(birthLayout);

            LinearLayout landLayout = new LinearLayout(context);
            landLayout.setId(generateViewId());
            landLayout.setOrientation(LinearLayout.HORIZONTAL);
            {
                ImageView islandView = new AppCompatImageView(context);
                islandView.setId(generateViewId());
                islandView.setImageResource(R.drawable.ic_nook_island);
                islandView.setLayoutParams(new LinearLayout.LayoutParams(ViewUtils.dpToPx(context, 16), ViewUtils.dpToPx(context, 16)));
                landLayout.addView(islandView);
                landView = new AppCompatTextView(context);
                landView.setTextColor(textColor1);
                landView.setTextSize(10);
                LinearLayout.LayoutParams landParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                landParams.setMarginStart(ViewUtils.dpToPx(context, 4));
                landView.setLayoutParams(landParams);
                landLayout.addView(landView);
            }
            ConstraintLayout.LayoutParams landParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            landParams.topMargin = ViewUtils.dpToPx(context, 20);
            landParams.setMarginStart(ViewUtils.dpToPx(context, 8));
            landParams.startToEnd = imageProfileWrapper.getId();
            landParams.topToTop = imageProfileWrapper.getId();
            landLayout.setLayoutParams(landParams);
            layout.addView(landLayout);

            View divider2 = new View(context);
            handleView = new AppCompatTextView(context);
            divider2.setId(generateViewId());
            handleView.setId(generateViewId());

            divider2.setBackgroundColor(backgroundColor);
            ConstraintLayout.LayoutParams divider2Params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ViewUtils.dpToPx(context, 2));
            divider2Params.startToStart = landLayout.getId();
            divider2Params.topToBottom = landLayout.getId();
            divider2Params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            divider2Params.topMargin = ViewUtils.dpToPx(context, 4);
            divider2Params.setMarginEnd(ViewUtils.dpToPx(context, 16));
            divider2.setLayoutParams(divider2Params);
            layout.addView(divider2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                handleView.setTextAppearance(R.style.TextAppearance_MaterialComponents_Caption);
            } else {
                handleView.setTextAppearance(context, R.style.TextAppearance_MaterialComponents_Caption);
            }
            handleView.setTextColor(textColor1);
            ConstraintLayout.LayoutParams handleParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            handleParams.topMargin = ViewUtils.dpToPx(context, 2);
            handleParams.startToStart = landLayout.getId();
            handleParams.topToBottom = divider2.getId();
            handleView.setLayoutParams(handleParams);
            layout.addView(handleView);

            LinearLayout fruitLayout = new LinearLayout(context);
            fruitLayout.setId(generateViewId());
            fruitLayout.setOrientation(LinearLayout.HORIZONTAL);
            {
                fruitIconView = new AppCompatImageView(context);
                fruitIconView.setId(generateViewId());
                fruitIconView.setLayoutParams(new LinearLayout.LayoutParams(ViewUtils.dpToPx(context, 16), ViewUtils.dpToPx(context, 16)));
                fruitLayout.addView(fruitIconView);
                fruitView = new AppCompatTextView(context);
                fruitView.setTextColor(textColor2);
                fruitView.setTextSize(10);
                LinearLayout.LayoutParams fruitParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                fruitParams.setMarginStart(ViewUtils.dpToPx(context, 4));
                fruitView.setLayoutParams(fruitParams);
                fruitLayout.addView(fruitView);
            }
            ConstraintLayout.LayoutParams fruitParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fruitParams.startToEnd = landLayout.getId();
            fruitParams.topToTop = landLayout.getId();
            fruitParams.endToEnd = divider2.getId();
            fruitLayout.setLayoutParams(fruitParams);
            layout.addView(fruitLayout);

            View divider3 = new View(context);
            divider3.setId(generateViewId());
            divider3.setBackgroundColor(backgroundColor);
            ConstraintLayout.LayoutParams divider3Params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ViewUtils.dpToPx(context, 2));
            divider3Params.startToStart = landLayout.getId();
            divider3Params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            divider3Params.topToBottom = handleView.getId();
            divider3Params.topMargin = ViewUtils.dpToPx(context, 4);
            divider3Params.setMarginEnd(ViewUtils.dpToPx(context, 16));
            divider3.setLayoutParams(divider3Params);
            layout.addView(divider3);

            View divider4 = new View(context);
            divider4.setId(generateViewId());
            divider4.setBackgroundColor(backgroundColor);
            ConstraintLayout.LayoutParams divider4Params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ViewUtils.dpToPx(context, 2));
            divider4Params.topMargin = ViewUtils.dpToPx(context, 4);
            divider4Params.startToStart = nameView.getId();
            divider4Params.endToStart = palmView.getId();
            divider4Params.topToBottom = birthLayout.getId();
            divider4.setLayoutParams(divider4Params);
            layout.addView(divider4);

            layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            patternLayout.addView(layout);
            LayoutParams patternParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            patternParams.topMargin = ViewUtils.dpToPx(context, 24);
            patternParams.bottomMargin = ViewUtils.dpToPx(context, 40);
            patternLayout.setLayoutParams(patternParams);
            innerCard.addView(patternLayout);
        }

        {
            waveBottomView = new PassportWaveBottomView(context);
            waveBottomView.setCornerRadius(ViewUtils.dpToPx(context, 24));

            ConstraintLayout layout = new ConstraintLayout(context);

            registeredAtView = new AppCompatTextView(context);
            registeredAtView.setTextColor(textColor2);
            registeredAtView.setTextSize(10);
            ConstraintLayout.LayoutParams registeredAtParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int margin = ViewUtils.dpToPx(context, 8);
            registeredAtParams.topMargin = margin;
            registeredAtParams.bottomMargin = margin;
            registeredAtParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            registeredAtParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            registeredAtParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            registeredAtView.setLayoutParams(registeredAtParams);
            layout.addView(registeredAtView);

            ImageView caretsView = new AppCompatImageView(context);
            caretsView.setImageResource(R.drawable.passport_carets);
            ConstraintLayout.LayoutParams caretsParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            caretsParams.topMargin = margin;
            caretsParams.bottomMargin = margin;
            caretsParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            caretsParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            caretsParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            caretsView.setLayoutParams(caretsParams);
            layout.addView(caretsView);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            final int layoutMargin = ViewUtils.dpToPx(context, 16);
            layoutParams.setMarginStart(layoutMargin);
            layoutParams.setMarginEnd(layoutMargin);
            layout.setLayoutParams(layoutParams);
            waveBottomView.addView(layout);

            LayoutParams bottomParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dpToPx(context, 40));
            bottomParams.gravity = Gravity.BOTTOM;
            waveBottomView.setLayoutParams(bottomParams);
            innerCard.addView(waveBottomView);
        }

        LayoutParams innerCardParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        innerCardParam.setMargins(4, 4, 4, 4);
        innerCard.setLayoutParams(innerCardParam);
        addView(innerCard);
    }

    public void setProfile(UserProfile profile) {
        if (profile != null) {
            waveTopView.setZodiacSign(profile.getBirth().getZodiacSign());
            waveBottomView.setZodiacSign(profile.getBirth().getZodiacSign());
            titleImage.setImageDrawable(new ContextThemeWrapper(titleImage.getContext(), profile.getBirth().getZodiacSign().getTitleTheme()).getDrawable(R.drawable.passport_title));
            patternLayout.background(profile.getBirth().getZodiacSign().getPatternTheme());
            TypedValue value = new TypedValue();
            profileView.getContext().getTheme().resolveAttribute(R.attr.colorSecondary, value, true);
            CircularProgressDrawable drawable = new CircularProgressDrawable(profileView.getContext());
            drawable.setColorSchemeColors(value.data);
            drawable.setStyle(CircularProgressDrawable.DEFAULT);
            drawable.start();
            Glide.with(profileView)
                    .load(profile.getImageUri())
                    .transform(new RoundedCornersTransformation(ViewUtils.dpToPx(profileView.getContext(), 12), 0))
                    .placeholder(drawable)
                    .into(profileView);
            nameView.setText(profile.getPlayerName());
            handleView.setText(profile.getHandleName());
            zodiacView.setImageResource(profile.getBirth().getZodiacSign().getDrawableRes());
            birthView.setText(birthView.getContext().getString(R.string.birthday, profile.getBirth().getMonth(), profile.getBirth().getDay()));
            registeredAtView.setText(registeredAtView.getContext().getString(R.string.registered_at, profile.getRegisteredTimeStamp().getYear(), profile.getRegisteredTimeStamp().getMonth(), profile.getRegisteredTimeStamp().getDay()));
        }
    }

    public void setLand(LandProfile land) {
        if (land != null) {
            waveTopView.setFruit(land.getFruit());
            waveBottomView.setFruit(land.getFruit());
            landView.setText(land.getName());
            fruitIconView.setImageResource(land.getFruit().getType().getDrawableRes());
            fruitView.setText(land.getFruit().getName());
        }
    }
}
