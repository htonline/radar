package com.example.interfaces;

public interface GetCallBack<T> {
    void success(T t);
    void failed(T t);
}
