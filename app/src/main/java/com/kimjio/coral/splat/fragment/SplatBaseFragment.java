package com.kimjio.coral.splat.fragment;

import android.content.res.Configuration;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.kimjio.coral.fragment.BaseFragment;
import com.kimjio.coral.splat.viewmodel.SplatViewModel;

public abstract class SplatBaseFragment<VB extends ViewDataBinding> extends BaseFragment<VB> {
    private static final float[] NEGATIVE = {
            -1f,   0,   0,  0, 255, // red
              0, -1f,   0,  0, 255, // green
              0,   0, -1f,  0, 255, // blue
              0,   0,   0, 1f,   0  // alpha
    };

    protected SplatViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), this, getArguments())).get(SplatViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            super.onCreateView(inflater, container, savedInstanceState);
            if (binding.getRoot().getBackground() != null) {
                int nightFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (nightFlags == Configuration.UI_MODE_NIGHT_NO) {
                    binding.getRoot().getBackground().setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
                }
            }
        }
        return binding.getRoot();
    }
}
