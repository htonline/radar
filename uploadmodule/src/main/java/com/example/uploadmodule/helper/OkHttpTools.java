package com.example.uploadmodule.helper;


import com.example.uploadmodule.upload.entity.DetectionInformation;
import com.example.uploadmodule.upload.entity.TkyDetectionInformation;
import com.example.uploadmodule.upload.utils.FileUtils;
import com.google.gson.Gson;

import org.json.JSONException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpTools {
    private static final String TAG = "OkHttpTools";
    private int isok=0;
    //创建OkHttpClient对象
    private final OkHttpClient client = new OkHttpClient();

    public String UploadWallData(DetectionInformation information, String token) throws JSONException {


        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        Gson gson = new Gson();
        String s = gson.toJson(information);
        RequestBody formBody = RequestBody.create(JSON,s);
        Request request = new Request.Builder()
                .url(FileUtils.IP+"api/detectionInformation")
                .addHeader("Authorization",token)
                .post(formBody)
                .build();
        Response respon = null;
        try (Response response = client.newCall(request).execute()) {
            //响应成功并返回响应内容
            if (response.isSuccessful()) {
                isok=1;
                return String.valueOf(isok);
                //此时代码执行在子线程，修改UI的操作使用handler跳转到UI线程
            }else{
                return response.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //响应失败返回" "
        return "";
    }

    public String UploadTkyData(TkyDetectionInformation information, String token) throws JSONException {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        Gson gson = new Gson();
        String s = gson.toJson(information);
        RequestBody formBody = RequestBody.create(JSON,s);
        Request request = new Request.Builder()
                .url(FileUtils.IP+"api/tkyDetectionInformation")
                .addHeader("Authorization",token)
                .post(formBody)
                .build();
        Response respon = null;
        try (Response response = client.newCall(request).execute()) {
            //响应成功并返回响应内容
            if (response.isSuccessful()) {
                isok=1;
                return String.valueOf(isok);
                //此时代码执行在子线程，修改UI的操作使用handler跳转到UI线程
            }else{
                return response.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //响应失败返回" "
        return "";
    }
}