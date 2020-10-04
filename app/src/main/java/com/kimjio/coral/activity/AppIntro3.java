package com.kimjio.coral.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.github.appintro.AppIntro2;
import com.kimjio.coral.R;

public abstract class AppIntro3 extends AppIntro2 {

    @Override
    protected int getLayoutId() {
        return R.layout.appintro_intro_layout3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(true);
        setNavBarColor(((ColorDrawable)findViewById(R.id.bottom).getBackground()).getColor());
    }
}