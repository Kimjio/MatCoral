package com.kimjio.coral.splat.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kimjio.coral.data.splat.NicknameIcon;
import com.kimjio.coral.data.splat.Records;
import com.kimjio.coral.splat.fragment.BattlesFragment;
import com.kimjio.coral.splat.fragment.MainFragment;
import com.kimjio.coral.splat.fragment.SalmonFragment;
import com.kimjio.coral.splat.fragment.StagesFragment;
import com.kimjio.coral.splat.fragment.StatsFragment;
import com.kimjio.coral.splat.viewmodel.SplatViewModel;

public class SplatFragmentAdapter extends FragmentStateAdapter {

    private MainFragment mainFragment = new MainFragment();
    private StagesFragment stagesFragment = new StagesFragment();
    private StatsFragment statsFragment = new StatsFragment();
    private BattlesFragment battlesFragment = new BattlesFragment();
    private SalmonFragment salmonFragment = new SalmonFragment();

    public SplatFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SplatFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public SplatFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = mainFragment;
                break;
            case 1:
                fragment = stagesFragment;
                break;
            case 2:
                fragment = statsFragment;
                break;
            case 3:
                fragment = battlesFragment;
                break;
            case 4:
                fragment = salmonFragment;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
