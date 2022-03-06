package com.kimjio.coral.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.TwoStatePreference;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kimjio.coral.BuildConfig;
import com.kimjio.coral.R;
import com.kimjio.coral.databinding.SettingsActivityBinding;
import com.kimjio.coral.manager.SessionTokenManager;
import com.kimjio.coral.manager.TokenManager;
import com.kimjio.coral.manager.UIManager;
import com.kimjio.coral.manager.UserManager;
import com.kimjio.coral.util.ViewUtils;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class SettingsActivity extends BaseActivity<SettingsActivityBinding> {

    public static final String KEY_USER_INFO = "user_info";
    public static final String KEY_USER_ACCOUNT = "user_account";
    public static final String KEY_SUPPORT_CODE = "support_code";
    public static final String KEY_MONOCHROME_ICON = "monochrome_icon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);

        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressWarnings("unused")
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            Preference userInfo = findPreference(KEY_USER_INFO);
            Preference userAccount = findPreference(KEY_USER_ACCOUNT);
            Preference supportCode = findPreference(KEY_SUPPORT_CODE);
            Preference monochromeIcon = findPreference(KEY_MONOCHROME_ICON);
            if (userInfo != null) {
                Glide.with(userInfo.getContext())
                        .load(UserManager.getInstance().getUser().getImageUri())
                        .error(Glide.with(userInfo.getContext()).load(R.drawable.ic_user_red).transform(new CropCircleWithBorderTransformation(ViewUtils.dpToPx(userInfo.getContext(), 1), ContextCompat.getColor(userInfo.getContext(), R.color.stroke_color))))
                        .transform(new CropCircleWithBorderTransformation(ViewUtils.dpToPx(userInfo.getContext(), 1), ContextCompat.getColor(userInfo.getContext(), R.color.stroke_color)))
                        .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            userInfo.setIcon(resource);
                        });
                        return true;
                    }
                }).submit(ViewUtils.dpToPx(userInfo.getContext(), 32), ViewUtils.dpToPx(userInfo.getContext(), 32));
                userInfo.setTitle(UserManager.getInstance().getUser().getName());
            }
            if (userAccount != null) {
                userAccount.setSummary(UserManager.getInstance().getAccountUser().getScreenName());
            }
            if (supportCode != null) {
                supportCode.setSummary(UserManager.getInstance().getUser().getSupportId());
            }
            if (monochromeIcon != null) {
                monochromeIcon.setEnabled(Build.VERSION.SDK_INT == Build.VERSION_CODES.S);
            }
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            if (preference.hasKey()) {
                switch (preference.getKey()) {
                    case KEY_USER_INFO:
                        new AlertDialog.Builder(preference.getContext())
                                .setMessage(R.string.confirm_sign_out)
                                .setPositiveButton(R.string.btn_sign_out, (dialog, which) -> {
                                    SessionTokenManager.getInstance(preference.getContext()).clear();
                                    UIManager.getInstance(preference.getContext()).clear();
                                    TokenManager.getInstance().clear();
                                    startActivity(new Intent(preference.getContext(), IntroActivity.class));
                                    requireActivity().finishAffinity();
                                })
                                .setNegativeButton(android.R.string.no, (dialog, which) -> {})
                                .create()
                                .show();
                        break;
                    case KEY_USER_ACCOUNT:
                        break;
                    case KEY_SUPPORT_CODE:
                        break;
                    case KEY_MONOCHROME_ICON:
                        setUseMonochromeIcon(((TwoStatePreference) preference).isChecked());
                        break;
                }
            }

            return super.onPreferenceTreeClick(preference);
        }

        private void setUseMonochromeIcon(boolean useMonochrome) {
            if (useMonochrome) {

                requireContext().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(BuildConfig.APPLICATION_ID, String.format("%s.activity.SplashActivity", BuildConfig.APPLICATION_ID)),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
                );
                requireContext().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(BuildConfig.APPLICATION_ID, String.format("%s.Monochrome", BuildConfig.APPLICATION_ID)),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                );
            } else {
                requireContext().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(BuildConfig.APPLICATION_ID, String.format("%s.activity.SplashActivity", BuildConfig.APPLICATION_ID)),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                );
                requireContext().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(BuildConfig.APPLICATION_ID, String.format("%s.Monochrome", BuildConfig.APPLICATION_ID)),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
                );
            }
        }
    }
}