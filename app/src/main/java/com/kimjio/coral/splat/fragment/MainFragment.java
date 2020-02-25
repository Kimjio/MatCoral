package com.kimjio.coral.splat.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.BR;
import com.kimjio.coral.R;
import com.kimjio.coral.data.splat.NicknameIcon;
import com.kimjio.coral.data.splat.Records;
import com.kimjio.coral.databinding.SplatMainFragmentBinding;
import com.kimjio.coral.splat.viewmodel.SplatViewModel;

public class MainFragment extends SplatBaseFragment<SplatMainFragmentBinding> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding.setViewModel(viewModel);
        return view;
    }
}
