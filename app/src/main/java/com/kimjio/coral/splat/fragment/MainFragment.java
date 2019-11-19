package com.kimjio.coral.splat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kimjio.coral.data.splat.NicknameIcon;
import com.kimjio.coral.data.splat.Records;
import com.kimjio.coral.databinding.SplatMainFragmentBinding;

public class MainFragment extends SplatBaseFragment<SplatMainFragmentBinding> {

    private Records records;
    private NicknameIcon nicknameIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding.statsCard.setRecords(records);
        binding.statsCard.setNicknameIcon(nicknameIcon);
        return view;
    }

    public void setRecords(Records records) {
        this.records = records;
        if (binding != null) {
            binding.statsCard.setRecords(this.records);
            binding.equipmentCard.setPlayer(this.records.getPlayer());
        }
    }

    public void setNicknameIcon(NicknameIcon nicknameIcon) {
        this.nicknameIcon = nicknameIcon;
        if (binding != null)
            binding.statsCard.setNicknameIcon(this.nicknameIcon);
    }
}
