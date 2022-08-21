package com.example.uploadmodule.upload.myinterface;

public interface CallBackToDo {
    void callBackdoSearchFile(int positon);
    void callBackdoUploadFile(int position);
    void callBackdoUploadPhoto(int position);
    void callBackCancelUpload(int position);
}
