package com.kimjio.coral.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
    private int bottomMargin = 0;

    public ScrollingViewBehavior() {
    }

    public ScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency) || dependency instanceof BottomNavigationView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        boolean result = super.onDependentViewChanged(parent, child, dependency);

        if (dependency instanceof BottomNavigationView && dependency.getHeight() != bottomMargin) {
            bottomMargin = dependency.getHeight();
            ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin = bottomMargin;
            child.requestLayout();
            return true;
        }

        return result;
    }
}
