package com.kimjio.coral.splat.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kimjio.coral.R;
import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.databinding.SplatActivityBinding;
import com.kimjio.coral.splat.viewmodel.SplatViewModel;
import com.kimjio.coral.splat.viewpager2.SplatFragmentAdapter;

import java.util.Objects;

public class SplatActivity extends BaseActivity<SplatActivityBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private SplatViewModel viewModel;
    private SplatFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(SplatViewModel.class);
        observeData();

        WebServiceToken webServiceToken = getIntent().getParcelableExtra("web_service_token");
        getSessionCookie(Objects.requireNonNull(webServiceToken).getAccessToken());

        binding.viewPager.setAdapter(adapter = new SplatFragmentAdapter(this));
        binding.viewPager.setUserInputEnabled(false);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);

        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.post(this::onRefresh);
    }

    @Override
    protected void observeData() {
        viewModel.getThrowable().observe(this, Throwable::printStackTrace);
        viewModel.getCookieResponseData().observe(this, response -> getData());
        viewModel.getFullRecordsData().observe(this, fullRecords -> {
            viewModel.loadNicknameIconData(fullRecords.getRecords().getPlayer().getNsUserId());
            adapter.setRecords(fullRecords.getRecords());
            binding.bottomNavigationView.getMenu().getItem(0).setIcon(fullRecords.getRecords().getPlayer().getType().getSpecies().equals("inklings") ? R.drawable.ic_splat_squid : R.drawable.ic_splat_octo);
        });
        viewModel.getNicknameIconData().observe(this, nicknameIcons -> {
            adapter.setNicknameIcon(nicknameIcons.get(0));
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int index = -1;
        boolean result = false;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                result = true;
                break;
            case R.id.menu_home:
                index = 0;
                result = true;
                break;
            case R.id.menu_stages:
                index = 1;
                result = true;
                break;
            case R.id.menu_stats:
                index = 2;
                result = true;
                break;
            case R.id.menu_battles:
                index = 3;
                result = true;
                break;
            case R.id.menu_salmon:
                index = 4;
                result = true;
                break;
        }
        if (index >= 0)
            binding.viewPager.setCurrentItem(index, false);
        return result || super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getSessionCookie(@NonNull String accessToken) {
        viewModel.loadCookieResponseData(accessToken);
    }

    private void getData() {
        viewModel.loadFullRecordsData();
    }
}
