package com.kimjio.coral.nook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Annotation;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kimjio.coral.R;
import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.data.nook.MyDesignQR;
import com.kimjio.coral.data.nook.User;
import com.kimjio.coral.databinding.NookActivityBinding;
import com.kimjio.coral.databinding.NookUserItemBinding;
import com.kimjio.coral.nook.util.MyDesignParser;
import com.kimjio.coral.nook.util.MyDesignRenderer;
import com.kimjio.coral.nook.viewmodel.NookViewModel;
import com.kimjio.coral.nook.widget.NintendoCharactersView;
import com.kimjio.coral.util.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.kimjio.coral.api.NintendoApi.getAuthorization;

public class NookActivity extends BaseActivity<NookActivityBinding> {
    private NookViewModel viewModel;

    private static final String TAG = "NookActivity";

    // 1001 offline
    // 3002 empty
    // 4002 expired

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(NookViewModel.class);
        observeData();

        WebServiceToken webServiceToken = getIntent().getParcelableExtra("web_service_token");
        getSessionCookie(Objects.requireNonNull(webServiceToken).getAccessToken());

        /*binding.button.setOnClickListener(v -> {
            NintendoCharactersView.showPopup(this, v, binding.editText);
        });

        binding.buttonSend.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.editText.getText())) {
                binding.textInputLayout.setError(getText(R.string.error_empty));
            } else {
                binding.textInputLayout.setError(null);
                viewModel.sendMessage(getAuthorization(viewModel.getToken().getValue().getToken()), binding.editText.getText().toString());
            }
        });

        binding.button2.setOnClickListener(v -> {
            MyDesignCaptureActivity.start(this);
        });*/
    }

    @Override
    protected void observeData() {
        viewModel.getThrowable().observe(this, throwable -> {
            if (throwable instanceof HttpException) {
                handleHTTPException((HttpException) throwable);
            }
        });
        viewModel.getCookieResponseData().observe(this, response -> {
            if (viewModel.getUser().getValue() == null) {
                viewModel.loadUsers();
            }
        });
        viewModel.getUser().observe(this, user -> {
            binding.userProfile.setUser(user);
            viewModel.loadToken(user.getId());
        });
        viewModel.getUsers().observe(this, users -> {
            if (viewModel.getUser().getValue() == null) {
                if (users.size() > 1) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.select_user)
                            .setCancelable(false)
                            .setAdapter(new UserListAdapter(this, users), (dialog, which) -> {
                                viewModel.setUser(users.get(which));
                            })
                            .create()
                            .show();
                } else {
                    viewModel.setUser(users.get(0));

                }
            }
        });
    }

    private void getSessionCookie(@NonNull String accessToken) {
        if (viewModel.getCookieResponseData().getValue() == null)
            viewModel.loadCookieResponseData(accessToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                boolean multiple = data.getBooleanExtra(MyDesignCaptureActivity.RESULT_MULTIPLE, false);
                byte[] rawBytes2 = data.getByteArrayExtra(MyDesignCaptureActivity.RESULT_COLOR_DATA_BACK);
                byte[] rawBytes3 = data.getByteArrayExtra(MyDesignCaptureActivity.RESULT_COLOR_DATA_LEFT);
                byte[] rawBytes4 = data.getByteArrayExtra(MyDesignCaptureActivity.RESULT_COLOR_DATA_RIGHT);
                if (result.getContents() == null) {
                    Log.d(TAG, "onActivityResult: Canceled");
                } else {
                    if (result.getRawBytes() != null) {
                        MyDesignQR qr = MyDesignParser.analyzeQrBinary(result.getRawBytes());
                        Bitmap bitmap = null;
                        if (qr.getDesignType() == MyDesignQR.Type.NORMAL) {
                            bitmap = MyDesignRenderer.renderNormalToCanvas(qr.getPaletteColors(), qr.getColorData());
                        } else if (qr.getDesignType() == MyDesignQR.Type.PRO && multiple) {
                            bitmap = MyDesignRenderer.renderProToCanvas(qr.getPaletteColors(), new byte[][]{qr.getColorData(), rawBytes2, rawBytes3, rawBytes4}, qr.getUsage());
                        }
                        binding.imageView2.setImageBitmap(bitmap);
                    } else
                        Log.w(TAG, "onActivityResult: Not correctly read");
                }
            }
        }

    }

    private void handleHTTPException(HttpException e) {
        Response<?> response = e.response();
        if (response != null) {
            ResponseBody body = response.errorBody();
            if (body != null)
                try {
                    JSONObject object = new JSONObject(body.string());
                    CharSequence error = object.getString("code");
                    if (error.equals("1001"))
                        error = getText(R.string.error_offline);

                    if (error instanceof SpannedString) {
                        SpannedString spannedString = (SpannedString) error;
                        Annotation[] annotations = spannedString.getSpans(0, spannedString.length(), Annotation.class);
                        SpannableString spannableString = new SpannableString(spannedString);
                        for (Annotation annotation : annotations) {
                            if (annotation.getKey().equals("name")) {
                                String fontName = annotation.getValue();
                                if (fontName.equals("color")) {
                                    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSecondaryDarkNook)),
                                            spannedString.getSpanStart(annotation),
                                            spannedString.getSpanEnd(annotation),
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                            }
                        }
                        error = spannableString;
                    }

                    new MaterialAlertDialogBuilder(this)
                            .setIcon(R.drawable.ic_error_outline)
                            .setTitle(R.string.error_title)
                            .setMessage(error)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, (dialogInterface, which) -> {
                            })
                            .show();
                } catch (JSONException | IOException ignore) {
                }
        }
    }

    private static class UserListAdapter extends ArrayAdapter<User> {

        UserListAdapter(@NonNull Context context, @NonNull List<User> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nook_user_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.binding.setUser(getItem(position));

            return convertView;
        }

        private static class ViewHolder {
            NookUserItemBinding binding;

            ViewHolder(@NonNull View itemView) {
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }
}