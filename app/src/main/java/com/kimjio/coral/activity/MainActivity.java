package com.kimjio.coral.activity;

import android.os.Bundle;
import android.util.Log;

import com.kimjio.coral.R;
import com.kimjio.coral.databinding.MainActivityBinding;
import com.kimjio.coral.data.view.IntroItem;

public class MainActivity extends BaseActivity<MainActivityBinding> {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getData() != null) {
            Log.d(TAG, "onCreate: " + getIntent().getData().toString());
        }

        binding.intro.setItem(new IntroItem("Nintendo Switch Online", "Enhance your online gameplay\nexperience on Nintendo Switch!", R.raw.loader));
    }
}
