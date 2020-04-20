package com.kimjio.coral.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kimjio.coral.api.NintendoException;
import com.kimjio.coral.data.Wrapper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel extends AndroidViewModel {
    protected MutableLiveData<Throwable> throwableLiveData = new MutableLiveData<>();
    protected CompositeDisposable disposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Throwable> getThrowable() {
        return throwableLiveData;
    }

    protected <T, D extends MutableLiveData<T>> Disposable getDisposable(Observable<T> observable, D liveData) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable.take(2).flatMap(throwable -> {
                    if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectException) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                    return Observable.error(throwable);
                }))
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
                .retryWhen(throwableObservable -> throwableObservable.take(2).flatMap(throwable -> {
                    if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectException) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                    return Observable.error(throwable);
                }))
                .subscribeWith(getWrapperDisposableObserver(liveData));
    }

    protected  <T, W extends Wrapper<T>, D extends MutableLiveData<T>> DisposableObserver<W> getWrapperDisposableObserver(D liveData) {
        return new DisposableObserver<W>() {
            @Override
            public void onNext(W result) {
                if (result.getData() == null) {
                    onError(new NintendoException(result.getStatus(), result.getErrorMessage()));
                } else {
                    liveData.postValue(result.getData());
                }
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
