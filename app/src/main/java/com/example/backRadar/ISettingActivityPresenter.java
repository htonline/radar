package com.example.backRadar;

import com.example.backRadar.Entity.ParamsOfSetting;

public interface ISettingActivityPresenter {
    void getParamsOfSetting();
    void saveParamsOfSetting(ParamsOfSetting paramsOfSetting,ICallBackToDo callBackToDo);
    void showSaveDataDialog();
    void destroySaveDataDialog();
    ParamsOfSetting loadParamsFromFile(String url,ICallBackToDo callBackToDo);
    void deleteParams(String url);

}
