package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.kimjio.coral.R;

public class PassportWaveTopView extends PassportWaveView {
    public PassportWaveTopView(Context context) {
        super(context);
    }

    public PassportWaveTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PassportWaveTopView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getDrawableResource() {
        return R.drawable.passport_wave_top;
    }

    @Override
    protected Type getType() {
        return Type.TOP;
    }
}
