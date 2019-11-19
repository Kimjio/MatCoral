package com.kimjio.coral.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.kimjio.coral.R;
import com.kimjio.coral.databinding.IntroItemBinding;
import com.kimjio.coral.data.view.IntroItem;

public class IntroFragment extends BaseFragment<IntroItemBinding> {

    private static final String KEY_ITEM = "intro_item";

    private IntroItem item;

    public IntroFragment() {
    }

    public IntroFragment(@NonNull IntroItem item) {
        this.item = item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            item = savedInstanceState.getParcelable(KEY_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding.setItem(item);
        return view;
    }

    public void playAnimation() {
        if (binding != null)
            binding.animation.playAnimation();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_ITEM, item);
    }
}
