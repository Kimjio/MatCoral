package com.kimjio.coral.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.kimjio.coral.api.NintendoException;
import com.kimjio.coral.data.Wrapper;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class BaseViewModel extends AndroidViewModel {
    protected SavedStateHandle savedStateHandle;
    protected MutableLiveData<Throwable> throwableLiveData = new MutableLiveData<>();
    protected CompositeDisposable disposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        this.savedStateHandle = savedStateHandle;
    }

    public MutableLiveData<Throwable> getThrowable() {
        return throwableLiveData;
    }

    protected <T, D extends MutableLiveData<T>> Disposable getDisposable(Observable<T> observable, D liveData) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable.take(3).flatMap(throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                    return Observable.error(throwable);
                }))
                .subscribeWith(getDisposableObserver(observable, liveData));
    }

    protected <T, D extends MutableLiveData<T>> DisposableObserver<T> getDisposableObserver(Observable<T> observable, D liveData) {
        return new DisposableObserver<T>() {
            @Override
            public void onNext(@NonNull T result) {
                liveData.postValue(result);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                BaseViewModel.this.onError(observable, e);
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
                .retryWhen(throwableObservable -> throwableObservable.take(3).flatMap(throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                    return Observable.error(throwable);
                }))
                .subscribeWith(getWrapperDisposableObserver(observable, liveData));
    }

    protected  <T, W extends Wrapper<T>, D extends MutableLiveData<T>> DisposableObserver<W> getWrapperDisposableObserver(Observable<W> observable, D liveData) {
        return new DisposableObserver<W>() {
            @Override
            public void onNext(@NonNull W result) {
                if (result.getData() == null) {
                    onError(new NintendoException(result.getStatus(), result.getErrorMessage()));
                } else {
                    liveData.postValue(result.getData());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                BaseViewModel.this.onErrorWrapper(observable, e);
                throwableLiveData.setValue(e);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    protected abstract <T> void onError(Observable<T> observable, Throwable e);

    protected abstract <T, W extends Wrapper<T>> void onErrorWrapper(Observable<W> observable, Throwable e);

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
