package com.example.upload.fragment;

import android.app.Activity;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.example.interfaces.GetCallBack;
import com.example.upload.GetRequestInterface;
import com.example.upload.LoadOnSubscribe;
import com.example.upload.UpLoadFileListAdapter;
import com.example.upload.convertor.FileConverterFactory;
import com.example.upload.entity.FileMd5;
import com.example.upload.entity.FileMdRes;
import com.example.upload.entity.UpFilePath;
import com.example.upload.entity.UpLoadFileInfo;
import com.example.upload.up.LoadCallBack;
import com.example.upload.utils.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadThread extends Thread {
    private static final String TAG = "UploadThread";
    private int position;
    private boolean isShut;
    private UpLoadFileListAdapter adapter;
    private List<UpLoadFileInfo> mfileInfos;
    private String user_token;
    private GetRequestInterface mgetRequestInterface;
    private String pro_file;
    private DecimalFormat decimalFormat;
    private Activity mAcitivty;

    UploadThread(int position, boolean isShut, UpLoadFileListAdapter adapter, List<UpLoadFileInfo> mfileInfos, String user_token, GetRequestInterface mgetRequestInterface, String pro_file,Activity mAcitivty) {
        this.position = position;
        this.isShut = isShut;
        this.adapter = adapter;
        this.mfileInfos = mfileInfos;
        this.user_token = user_token;
        this.mgetRequestInterface = mgetRequestInterface;
        this.pro_file = pro_file;
        decimalFormat = new DecimalFormat("0.00");
        this.mAcitivty = mAcitivty;
    }

    public void setShut(boolean shut) {
        isShut = shut;
    }

    @Override
    public void run() {
        searchFile(mgetRequestInterface, mfileInfos.get(position).getFilePath(), user_token);
    }

    private void searchToUpFile(int start, int stop, List<String> files) {
        if (start > stop) {
            return;
        }
//        searchFile(mgetRequestInterface, files.get(start), user_token, new GetCallBack() {
//            @Override
//            public void doThing() {
        if (isShut) {
            isShut = false;
            mfileInfos.get(position).setFilePath(null);
            mfileInfos.get(position).setUploadpercent("0.0%");
            mfileInfos.get(position).setIsfileup(false);
            adapter.notifyDataSetChanged();
            interrupt();
            return;
        }
        float p = (float) start / (float) stop;
        p = p * 100;
        String format = decimalFormat.format(p);
        mfileInfos.get(position).setUploadpercent(format + "%");
        adapter.notifyDataSetChanged();

        uploadFileBySplit(mgetRequestInterface, new File(files.get(start)), user_token, new GetCallBack() {
            @Override
            public void doThing() {
                searchToUpFile(start + 1, stop, files);
            }
        });
//            }
//        });
    }

    private void searchFile(GetRequestInterface mgetRequestInterface, String filename, String user_token) {
        FileMd5 md5 = new FileMd5();
        File file = new File(filename);
        md5.setFilename(pro_file + "-" + file.getName());
//        md5.setFilename(mTv_project_name.getText().toString() + "-" + file.getName());
        try {
            md5.setMd5("");//FileUtils.getMd5(FileUtils.getByte(file)
        } catch (Exception e) {
            e.printStackTrace();
        }
        md5.setLen(file.length());
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gson.toJson(md5));
        Call<FileMdRes> call = mgetRequestInterface.verifyFile(body, user_token);
        call.enqueue(new Callback<FileMdRes>() {
            @Override
            public void onResponse(Call<FileMdRes> call, Response<FileMdRes> response) {
                if (response.body() != null) {
                    if (response.body().getExits().equals("file")) {
                        String[] str = file.getName().split("\\.");
                        Toast.makeText(mAcitivty, "服务器已有该文件", Toast.LENGTH_SHORT).show();
                        mfileInfos.get(position).setUploadpercent("100.0%");
                        mfileInfos.get(position).setFileRemotePath(pro_file + "-" + str[0] + "." + str[1]);
                        mfileInfos.get(position).setIsfileup(true);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mAcitivty, "正在上传", Toast.LENGTH_SHORT).show();
                        if (file.length() > 70 * 1024 * 1024) {
                            File filepath = new File(file.getParent() + "/" + file.getName() + "_");
                            List<String> files = new ArrayList<>();
                            if (filepath.exists() && filepath.isDirectory()) {
                                File[] files1 = filepath.listFiles();
                                for (File f : files1) {
                                    files.add(f.getAbsolutePath());
                                }
                            } else {
                                filepath.mkdir();
                                FileUtils utils = new FileUtils();
                                try {
                                    files = utils.splitBySize(file, 1 * 512 * 1024, filepath.getAbsolutePath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            searchToUpFile(Integer.parseInt(response.body().getCount()), files.size() - 1, files);
                        } else {
                            uploadOneFile(file, user_token);
                        }
//                        if (file.length() > 70 * 1024 * 1024){
//                            uploadFileBySplit(getRequestInterface,file,user_token);
//                        }else {
//                            uploadOneFile(file,user_token);
//                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<FileMdRes> call, Throwable t) {
                Toast.makeText(mAcitivty, "校验文件错误！:" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadFileBySplit(GetRequestInterface getRequestInterface, File file, String user_token, GetCallBack callBack) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60000, TimeUnit.SECONDS)
                .readTimeout(60000, TimeUnit.SECONDS)
                .writeTimeout(60000, TimeUnit.SECONDS)
//                .addInterceptor(getHeader())
                .addNetworkInterceptor(getResponseIntercept()).build();
        Retrofit mretrofit = new Retrofit.Builder()
                .baseUrl(FileUtils.IP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getRequestInterface = mretrofit.create(GetRequestInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", pro_file + "-" + file.getName(), body);
        Call<UpFilePath> upFilePathCall = getRequestInterface.uploadOneFileBySplit(part, user_token);
        upFilePathCall.enqueue(new Callback<UpFilePath>() {
            @Override
            public void onResponse(Call<UpFilePath> call, Response<UpFilePath> response) {
                if (response.body() == null) {
                    try {
                        Toast.makeText(mAcitivty, "" + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Exception " + e);
                    }
                } else {
                    String[] sts = response.body().getAvatar().split("\\.");
                    if (sts[sts.length - 1].equals("true")) {

                        String pathfile = file.getParent();
                        FileUtils.deleteFile(new File(pathfile));
                        String filepath = response.body().getAvatar();
                        String[] paths = filepath.split("\\.");
                        mfileInfos.get(position).setUploadpercent("100.0%");
                        mfileInfos.get(position).setFileRemotePath(paths[0] + paths[1]);
                        mfileInfos.get(position).setIsfileup(true);
                        adapter.notifyDataSetChanged();
                    } else {
                        callBack.doThing();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpFilePath> call, Throwable t) {
                Toast.makeText(mAcitivty, "上传失败", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onResponse: " + t);
            }
        });
    }

    public void uploadOneFile(File file, String user_token) {
        LoadOnSubscribe loadOnSubscribe = new LoadOnSubscribe();
        ArrayMap<String, Object> params = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            params = new ArrayMap<>();
        }
        List<String> files1 = new ArrayList<>();
        files1.add(file.getAbsolutePath());
        params.put("files", files1);
        params.put("LoadOnSubscribe", loadOnSubscribe);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .writeTimeout(60000, TimeUnit.MILLISECONDS)
//                .addInterceptor(getHeader())
                .addNetworkInterceptor(getResponseIntercept()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new FileConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(FileUtils.IP)
                .build();
        GetRequestInterface loadService = retrofit.create(GetRequestInterface.class);
        Observable.merge(Observable.create(loadOnSubscribe), loadService.upLoadOneFile(params, user_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadCallBack<Object>() {
                    @Override
                    protected void onProgress(String percent) {
//                        mtv_up_percent.setText(percent + "%");
                        mfileInfos.get(position).setUploadpercent(percent + "%");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onSuccess(Object t) {
                        Toast.makeText(mAcitivty, "上传成功", Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        String str = gson.toJson(t);
                        UpFilePath path = gson.fromJson(str, UpFilePath.class);
                        String urlrawpath = path.getAvatar();
                        mfileInfos.get(position).setFileRemotePath(urlrawpath);
                        mfileInfos.get(position).setIsfileup(true);
                        adapter.notifyDataSetChanged();
//                        ib_upload_file.setImageResource(R.drawable.upload_file_03);
                    }
                });

//        Log.d(TAG, "uploadOneFile:  --> path :"+file.getAbsolutePath());
//        RequestBody body = RequestBody
//                .create(MediaType.parse("application/octet-stream"),file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),body);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://39.105.125.51:8001/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
//        Call<UpFilePath> upFilePathCall = getRequestInterface.upLoadOneFile(part,user_token);
//        upFilePathCall.enqueue(new Callback<UpFilePath>() {
//            @Override
//            public void onResponse(Call<UpFilePath> call, Response<UpFilePath> response) {
//                if (response.body() == null){
//                    try {
//                        Toast.makeText(mActivity,""+response.errorBody().string(),Toast.LENGTH_LONG).show();
//                    }catch (Exception e){
//                        Log.e(TAG, "Exception "+e);
//                    }
//                }else {
//                    Toast.makeText(mActivity,"上传雷达数据成功",Toast.LENGTH_LONG).show();
//                    urlrawpath = response.body().getAvatar();
//                    ib_upload_file.setImageResource(R.drawable.upload_file_03);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UpFilePath> call, Throwable t) {
//                Toast.makeText(mActivity,"上传失败",Toast.LENGTH_LONG).show();
//                Log.d(TAG, "onResponse: "+t);
//            }
//        });
    }

    private HttpLoggingInterceptor getResponseIntercept() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
