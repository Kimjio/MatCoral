package com.kimjio.coral.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kimjio.coral.data.Wrapper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel extends AndroidViewModel {
    private MutableLiveData<Throwable> throwableLiveData = new MutableLiveData<>();
    CompositeDisposable disposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Throwable> getThrowable() {
        return throwableLiveData;
    }

    protected <T, D extends MutableLiveData<T>> Disposable getDisposable(Observable<T> observable, D liveData) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable.flatMap(Observable::error))
                .subscribeWith(getDisposableObserver(liveData));
    }

    protected <T, D extends MutableLiveData<T>> DisposableObserver<T> getDisposableObserver(D liveData) {
        return new DisposableObserver<T>() {
            @Override
            public void onNext(T result) {
                liveData.postValue(result);
            }

            @Override
            public void onError(Throwable e) {
                throwableLiveData.postValue(e);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    protected <T, W extends Wrapper<T>, D extends MutableLiveData<T>> Disposable getWrapperDisposable(Observable<W> observable, D liveData) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable.flatMap(Observable::error))
                .subscribeWith(getWrapperDisposableObserver(liveData));
    }

    protected  <T, W extends Wrapper<T>, D extends MutableLiveData<T>> DisposableObserver<W> getWrapperDisposableObserver(D liveData) {
        return new DisposableObserver<W>() {
            @Override
            public void onNext(W result) {
                liveData.postValue(result.getData());
            }

            @Override
            public void onError(Throwable e) {
                throwableLiveData.setValue(e);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
