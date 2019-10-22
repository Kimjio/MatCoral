package com.kimjio.coral.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kimjio.coral.R;
import com.kimjio.coral.fragment.IntroFragment;
import com.kimjio.coral.data.view.IntroItem;

public class IntroActivity extends AppIntro3 {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pager = findViewById(R.id.view_pager);
        addSlide(new IntroFragment(new IntroItem("Nintendo Switch Online", "Enhance your online gameplay\nexperience on Nintendo Switch!", R.raw.tutorial_image_01)));
        addSlide(new IntroFragment(new IntroItem("Enjoy voice chat while gaming!", "", R.raw.tutorial_image_02)));
        addSlide(new IntroFragment(new IntroItem("Check info related\n to your game!", "", R.raw.tutorial_image_03)));
    }

    @Override
    protected void onSlideChanged(Fragment oldFragment, Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        if (newFragment instanceof IntroFragment)
            //if (!((IntroFragment) newFragment).isAnimating())
                ((IntroFragment) newFragment).playAnimation();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        pager.setCurrentItem(pager.getAdapter().getCount() - 1, true);
    }
}
