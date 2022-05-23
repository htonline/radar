package com.example.upload;

import android.util.ArrayMap;

import com.example.upload.convertor.UpLoadFileType;
import com.example.upload.entity.DeviceRes;
import com.example.upload.entity.FileExists;
import com.example.upload.entity.FileMdRes;
import com.example.upload.entity.UpFilePath;
import com.example.upload.entity.UserInfoLogin;
import com.example.upload.entity.Userinfo;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetRequestInterface {
    @POST("auth/loginByNoCode")
    Call<UserInfoLogin> getUserInfo(@Body RequestBody userinfo);

    @UpLoadFileType
    @POST("api/upload/updateFile")
    Observable<Object> upLoadOneFile(@Body ArrayMap<String, Object> params, @Header("Authorization") String token);

    @Multipart
    @POST("api/upload/updateFileBySplit")
    Call<UpFilePath> uploadOneFileBySplit(@Part MultipartBody.Part file, @Header("Authorization") String token);

    @POST("api/phoneOperate/searchFile")
    Call<FileExists> getFileExists(@Body RequestBody filename, @Header("Authorization") String token);

    @POST("api/upload/verifyFile")
    Call<FileMdRes> verifyFile(@Body RequestBody filemd5, @Header("Authorization") String token);

    @POST("api/deviceInformation/queryDeviceInformation")
    Call<DeviceRes> queryDeviceInformation(@Body RequestBody id, @Header("Authorization") String token);

}
