package com.example.uploadmodule.upload.convertor;

import android.util.ArrayMap;

import com.example.uploadmodule.upload.LoadOnSubscribe;
import com.example.uploadmodule.upload.up.FileProgressRequestBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Converter;


public class FileRequestBodyConverter implements Converter<ArrayMap<String, Object>, RequestBody> {

    //进度发射器
    LoadOnSubscribe uploadOnSubscribe;

    public FileRequestBodyConverter() {}

    @Override
    public RequestBody convert(ArrayMap<String, Object> params) throws IOException {

        uploadOnSubscribe = (LoadOnSubscribe) params.get("LoadOnSubscribe");

        if (params.containsKey("filePathList")){
            return filesToMultipartBody((List<String>)params.get("filePathList"));
        } else if (params.containsKey("files")){
            return filesToMultipartBody((List<File>) params.get("files"));
        } else {
            return null;
        }
    }


    /**
     * 用于把 File集合 或者 File路径集合 转化成 MultipartBody
     * @param files File列表或者 File 路径列表
     * @param <T> 泛型（File 或者 String）
     * @return MultipartBody（retrofit 多文件文件上传）
     */
    public synchronized <T> MultipartBody filesToMultipartBody(List<T> files) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        long sumLeng = 0L;
        File file;
        for (T t : files) {
            if (t instanceof File) file = (File) t;
            else if (t instanceof String) file = new File((String) t);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
            else break;

            sumLeng += file.length();
            // TODO 为了简单起见，没有判断file的类型
            FileProgressRequestBody requestBody = new FileProgressRequestBody(file, "multipart/form-data", uploadOnSubscribe);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        uploadOnSubscribe.setmSumLength(sumLeng);

        return builder.build();
    }
}
