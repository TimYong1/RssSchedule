package com.sx.base.net;

import com.sx.base.R;
import com.util.toast.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class HttpBaseObserver<T> implements Observer<T> {

    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {
        succeed(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            ToastUtils.INSTANCE.show(R.string.time_out_exception);
            e = new ServerException(e);
        }
        LogUtils.e(e);
        failed(e);
    }

    @Override
    public void onComplete() {
        mDisposable.dispose();
    }

    public abstract void succeed(T t);
    public abstract void failed(Throwable e);
}
