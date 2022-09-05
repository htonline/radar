package com.example.uploadmodule.upload;

import android.util.ArrayMap;


import com.example.uploadmodule.upload.convertor.UpLoadFileType;
import com.example.uploadmodule.upload.entity.DeviceRes;
import com.example.uploadmodule.upload.entity.FileExists;
import com.example.uploadmodule.upload.entity.FileMdRes;
import com.example.uploadmodule.upload.entity.UpFilePath;
import com.example.uploadmodule.upload.entity.UserInfoLogin;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("/api/hotfix/gethotfixpag")
    Call<ResponseBody> gethotfix();
}
