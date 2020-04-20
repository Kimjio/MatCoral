package com.kimjio.coral.splat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimjio.coral.api.SplatNet2Api;
import com.kimjio.coral.data.splat.FullRecords;
import com.kimjio.coral.data.splat.NicknameIcon;
import com.kimjio.coral.util.RetrofitUtil;
import com.kimjio.coral.viewmodel.BaseViewModel;

import java.util.List;

import retrofit2.Response;

public class SplatViewModel extends BaseViewModel {
    private final SplatNet2Api splatNet2Api;

    private MutableLiveData<Response<Void>> cookieResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<FullRecords> fullRecordsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<NicknameIcon>> nicknameIconsLiveData = new MutableLiveData<>();

    public SplatViewModel(@NonNull Application application) {
        super(application);
        splatNet2Api = RetrofitUtil.getInstance(application.getApplicationContext(), SplatNet2Api.SPLAT_NET_2).create(SplatNet2Api.class);
    }

    public LiveData<Response<Void>> getCookieResponseData() {
        return cookieResponseLiveData;
    }

    public void loadCookieResponseData(String webServiceToken) {
        disposable.add(getDisposable(splatNet2Api.getCookie(webServiceToken), cookieResponseLiveData));
    }

    public LiveData<FullRecords> getFullRecordsData() {
        return fullRecordsLiveData;
    }

    public void loadFullRecordsData() {
        disposable.add(getDisposable(splatNet2Api.getFullRecords(), fullRecordsLiveData));
    }

    public LiveData<List<NicknameIcon>> getNicknameIconData() {
        return nicknameIconsLiveData;
    }

    public void loadNicknameIconData(String id) {
        disposable.add(getWrapperDisposable(splatNet2Api.getNicknameIcon(id), nicknameIconsLiveData));
    }
}
