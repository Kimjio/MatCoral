package com.kimjio.coral.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.kimjio.coral.R;
import com.kimjio.coral.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {
    protected BaseViewModel viewModel;
    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        binding.setLifecycleOwner(this);
    }

    protected void observeData() {
        throw new RuntimeException("Stub!");
    }

    @NonNull
    public ActionBar requireSupportActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            throw new IllegalArgumentException("ActionBar does not set for this Activity");
        return actionBar;
    }

    @LayoutRes
    private int getLayoutRes() {
        String[] split = ((Class<?>) ((ParameterizedType) Objects.requireNonNull(getClass().getGenericSuperclass())).getActualTypeArguments()[0]).getSimpleName().replace("Binding", "").split("(?<=.)(?=\\p{Upper})");
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < split.length; i++) {
            name.append(split[i].toLowerCase());
            if (i != split.length - 1)
                name.append("_");
        }

        try {
            return R.layout.class.getField(name.toString()).getInt(R.layout.class);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = false;
        if (item.getItemId() == android.R.id.home) {
            finish();
            result = true;
        }
        return result || super.onOptionsItemSelected(item);
    }
}