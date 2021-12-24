package com.example.BackRadar;

import android.util.Log;

import com.example.BackRadar.Entity.FileInfoInPath;
import com.example.BackRadar.Entity.ParamsOfSetting;

import java.util.List;

public class SettingActivityPresenter implements ISettingActivityPresenter {
    private static final String TAG = "SettingActivityPresente";
    SettingActivity mSettingActivity;
    SettingAcitivityModel mSettingActivityModel;
    List<FileInfoInPath> lists_path;
    SettingActivityPresenter(SettingActivity settingActivity) {
        mSettingActivity = settingActivity;
        mSettingActivityModel = new SettingAcitivityModel();
    }


    @Override
    public void getParamsOfSetting() {
        lists_path = mSettingActivityModel.getFilePaths();
        mSettingActivity.showPathFiles(lists_path);
    }

    @Override
    public void saveParamsOfSetting(ParamsOfSetting paramsOfSetting, ICallBackToDo callBackToDo) {
        String filename = paramsOfSetting.getParamsName();
        filename = filename.split("\\.")[0]+"Params";
        String path = android.os.Environment.getExternalStorageDirectory()+"/radar/params";
        boolean modelWriteObj = mSettingActivityModel.WriteToFile(paramsOfSetting, path,filename);
        if ((modelWriteObj)) {
            callBackToDo.dosuccess();
        } else {
            callBackToDo.dofailed();
        }
    }

    @Override
    public void showSaveDataDialog() {

    }

    @Override
    public void destroySaveDataDialog() {

    }

    @Override
    public ParamsOfSetting loadParamsFromFile(String url,ICallBackToDo backtoDo) {
        String path = android.os.Environment.getExternalStorageDirectory()+"/radar/params/";
        ParamsOfSetting paramsOfSetting = mSettingActivityModel.readFromFile(path+url);
        if (paramsOfSetting==null){
            backtoDo.dofailed();
        }else {
            backtoDo.dosuccess();
        }
        return paramsOfSetting;
    }

    @Override
    public void deleteParams(String url) {
        String urlPath = android.os.Environment.getExternalStorageDirectory()+"/radar/params/"+url;
        mSettingActivityModel.deleteParams(urlPath);
    }
}
