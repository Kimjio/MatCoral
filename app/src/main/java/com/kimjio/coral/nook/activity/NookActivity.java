package com.kimjio.coral.nook.activity;

import android.content.Context;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;

import com.kimjio.coral.R;
import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.data.auth.WebServiceToken;
import com.kimjio.coral.data.nook.User;
import com.kimjio.coral.databinding.NookActivityBinding;
import com.kimjio.coral.databinding.NookUserItemBinding;
import com.kimjio.coral.nook.viewmodel.NookViewModel;
import com.kimjio.coral.nook.widget.NintendoCharactersView;
import com.kimjio.coral.util.ViewUtils;

import java.util.List;
import java.util.Objects;

import static com.kimjio.coral.api.NintendoApi.getAuthorization;

public class NookActivity extends BaseActivity<NookActivityBinding> {
    private NookViewModel viewModel;
    private String token;

    private static final String TAG = "NookActivity";

    // 1001 offline
    // 3002 empty

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appBar);
        requireSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requireSupportActionBar().setDisplayShowTitleEnabled(false);
        requireSupportActionBar().setLogo(R.drawable.logo_nooklink);

        viewModel = ViewModelProviders.of(this).get(NookViewModel.class);
        observeData();

        WebServiceToken webServiceToken = getIntent().getParcelableExtra("web_service_token");
        getSessionCookie(Objects.requireNonNull(webServiceToken).getAccessToken());

        binding.button.setOnClickListener(v -> {
            NintendoCharactersView nintendoCharactersView = new NintendoCharactersView(this);
            PopupWindow popupWindow = new PopupWindow(nintendoCharactersView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewUtils.dpToPx(this, 240));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setElevation(8);
            popupWindow.showAsDropDown(v);
            nintendoCharactersView.setOnItemClickListener((charSeq, position) -> {
                binding.editText.append(charSeq);
            });
            nintendoCharactersView.setOnCloseClickListener(view -> {
                popupWindow.dismiss();
            });
        });

        binding.buttonSend.setOnClickListener(v -> {
            viewModel.sendMessage(getAuthorization(token), binding.editText.getText().toString());
        });
    }

    @Override
    protected void observeData() {
        viewModel.getThrowable().observe(this, Throwable::printStackTrace);
        viewModel.getCookieResponseData().observe(this, response -> viewModel.loadUsers());
        viewModel.getUsers().observe(this, users -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.select_user)
                    .setCancelable(false)
                    .setAdapter(new UserListAdapter(this, users), (dialog, which) -> {
                        binding.userProfile.setUser(users.get(which));
                        viewModel.loadToken(users.get(which).getId());
                    })
                    .create()
                    .show();
            /*if (users.size() > 1) {
                //TODO Select User
            } else {
                binding.userProfile.setUser(users.get(0));
                viewModel.loadToken(users.get(0).getId());
            }*/
        });
        viewModel.getToken().observe(this, token -> {
            //TODO NookTokenManager
            Log.d(TAG, "observeData: " + token.getToken());
            this.token = token.getToken();
        });
    }

    private void getSessionCookie(@NonNull String accessToken) {
        if (viewModel.getCookieResponseData().getValue() == null)
            viewModel.loadCookieResponseData(accessToken);
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