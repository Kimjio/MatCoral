package com.kimjio.coral.splat.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.kimjio.coral.R;
import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.databinding.SplatActivityBinding;
import com.kimjio.coral.splat.viewmodel.SplatViewModel;
import com.kimjio.coral.splat.viewpager2.SplatFragmentAdapter;
import com.kimjio.coral.util.ViewUtils;

import java.util.Objects;

public class SplatActivity extends BaseActivity<SplatActivityBinding> implements SwipeRefreshLayout.OnRefreshListener {
    protected SplatViewModel viewModel;
    private SplatFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(SplatViewModel.class);
        observeData();

        WebServiceToken webServiceToken = getIntent().getParcelableExtra("web_service_token");
        getSessionCookie(Objects.requireNonNull(webServiceToken).getAccessToken());

        binding.viewPager.setAdapter(adapter = new SplatFragmentAdapter(this));
        binding.viewPager.setUserInputEnabled(false);

        binding.bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, insets) -> {
            int systemTop = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            binding.swipeRefreshLayout.setProgressViewOffset(true, systemTop, systemTop + ViewUtils.dpToPx(this, 56) + 40);
            return insets;
        });

        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.swipeRefreshLayout.setRefreshing(true);

        MaterialShapeDrawable drawable = new MaterialShapeDrawable();
        drawable.setFillColor(new ColorStateList(new int[][]{new int[]{R.attr.state_lifted},new int[]{-R.attr.state_lifted}}, new int[]{SurfaceColors.SURFACE_2.getColor(this), Color.TRANSPARENT}));

        binding.appBarLayout.setBackground(drawable);
    }

    @Override
    protected void observeData() {
        viewModel.getThrowable().observe(this, Throwable::printStackTrace);
        viewModel.getCookieResponseData().observe(this, response -> {
            if (!response.raw().request().url().encodedPath().contains("introduction")) {
                getData(false);
            } else {
                // TODO Not registered
            }
        });
        viewModel.getFullRecordsData().observe(this, fullRecords -> {
            binding.bottomNavigationView.getMenu().getItem(0).setIcon(fullRecords.getRecords().getPlayer().getType().getSpecies().equals("inklings") ? R.drawable.ic_splat_squid : R.drawable.ic_splat_octo);
            if (viewModel.getNicknameIconData().getValue() == null)
                viewModel.loadNicknameIconData(fullRecords.getRecords().getPlayer().getNsUserId());
            else
                binding.swipeRefreshLayout.setRefreshing(false);
        });
        viewModel.getNicknameIconData().observe(this, nicknameIcons -> binding.swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int index = -1;
        int scrollId = View.NO_ID;
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.menu_home:
                index = 0;
                result = true;
                scrollId = R.id.scroll_home;
                break;
            case R.id.menu_stages:
                index = 1;
                result = true;
                scrollId = R.id.scroll_stages;
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
        binding.appBarLayout.setLiftOnScrollTargetViewId(scrollId);
        binding.appBarLayout.setLifted(true);
        return result || super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    private void getSessionCookie(@NonNull String accessToken) {
        if (viewModel.getCookieResponseData().getValue() == null)
            viewModel.loadCookieResponseData(accessToken);
    }

    private void getData(boolean refresh) {
        if (viewModel.getFullRecordsData().getValue() == null || refresh)
            viewModel.loadFullRecordsData();
    }
}
