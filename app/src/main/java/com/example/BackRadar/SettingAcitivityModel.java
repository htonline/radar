package com.example.BackRadar;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.example.BackRadar.Entity.FileInfoInPath;
import com.example.BackRadar.Entity.ParamsOfSetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SettingAcitivityModel {
    private static final String TAG = "SettingAcitivityModel";

    public boolean WriteToFile(Object obj, String path,String url) {
//        String filesDir = getApplicationContext().getFilesDir().getPath();
        try {
            File filepath = new File(path);
            if (!filepath.exists()){
                filepath.mkdirs();
            }
            File file = new File(path,url);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream ot = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(ot);
            oos.writeObject(obj);
            oos.close();
            ot.close();
        } catch (IOException e) {
            Log.e(TAG, "WriteToFile: " + e.toString());
            return false;
        }
        return true;
    }

    public List<FileInfoInPath> getFilePaths() {
        List<FileInfoInPath> fileInfoInPaths = new ArrayList<>();
        File file = new File(android.os.Environment.getExternalStorageDirectory() + "/radar/params");
        if (!file.exists()){
            file.mkdirs();
        }
        File[] files = file.listFiles();
        for (File file1 : files) {
            FileInfoInPath fileInfoInPath = new FileInfoInPath();
            fileInfoInPath.setFilename(file1.getName());
            long time = file1.lastModified();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updatetime = formatter.format(time);
            fileInfoInPath.setUpdateTime(updatetime);
            fileInfoInPaths.add(fileInfoInPath);
        }
        return fileInfoInPaths;
    }

    public ParamsOfSetting readFromFile(String url){
        ParamsOfSetting paramsOfSetting=null;
        try {
            InputStream in = new FileInputStream(url);
            ObjectInputStream inn = new ObjectInputStream(in);
            paramsOfSetting = (ParamsOfSetting) inn.readObject();
            inn.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramsOfSetting;
    }
    public void deleteParams(String url){
        File file = new File(url);
        if (file.exists()){
            file.delete();
        }
    }
}
