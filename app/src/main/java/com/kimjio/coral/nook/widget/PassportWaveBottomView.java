package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.kimjio.coral.R;

public class PassportWaveBottomView extends PassportWaveView {
    public PassportWaveBottomView(Context context) {
        super(context);
    }

    public PassportWaveBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PassportWaveBottomView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getDrawableResource() {
        return R.drawable.passport_wave_bottom;
    }

    @Override
    protected Type getType() {
        return Type.BOTTOM;
    }
}
