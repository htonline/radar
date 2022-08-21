package com.example.uploadmodule.upload;


import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class RequestBaseObserver<V> implements Observer<V> {

    private Disposable disposed;

    public RequestBaseObserver() {}

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposed = d;
    }

    @Override
    public void onNext(V t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }


    /**
     * 上传、下载 需重写此方法，更新进度
     * @param percent 进度百分比 数
     */
    protected void onProgress(String percent){

    }

    /**
     * 请求成功 回调
     *
     * @param t 请求返回的数据
     */
    protected abstract void onSuccess(V t);

}
