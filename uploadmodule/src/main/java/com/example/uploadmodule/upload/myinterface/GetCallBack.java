package com.example.uploadmodule.upload.myinterface;

public interface GetCallBack<T> {
    default void success(T t){

    }
    default void doThing(){

    }
    default void failed(T t){

    }
}
