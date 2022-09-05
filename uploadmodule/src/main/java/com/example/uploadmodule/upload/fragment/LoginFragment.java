package com.example.uploadmodule.upload.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uploadmodule.R;
import com.example.uploadmodule.upload.GetRequestInterface;
import com.example.uploadmodule.upload.entity.UserInfoLogin;
import com.example.uploadmodule.upload.entity.Userinfo;
import com.example.uploadmodule.upload.myinterface.GetCallBack;
import com.example.uploadmodule.upload.utils.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.internal.operators.flowable.FlowableTake;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private GetCallBack<UserInfoLogin> getCallBack;
    private Button mbtn_login;
    private Button mbtn_login_cancel;
    private EditText met_username;
    private EditText met_password;
    private Activity mActivity;
    private Context mcontext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int type =0;
    public void setGetCallBack(GetCallBack getCallBack) {
        this.getCallBack = getCallBack;
    }

    public LoginFragment(Context context, int type) {
        mcontext = context;
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        preferences = mcontext.getSharedPreferences("userlogininfo", 0);
        editor = preferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mbtn_login = view.findViewById(R.id.btn_login);
        mbtn_login_cancel = view.findViewById(R.id.btn_login_cancel);
        met_username = view.findViewById(R.id.et_login_user);
        met_password = view.findViewById(R.id.et_login_pwd);
        String susername = preferences.getString("username", null);
        String spassword = preferences.getString("password", null);
        met_username.setText(susername);
        met_password.setText(spassword);
        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(met_username.getText()) || TextUtils.isEmpty(met_password.getText())) {
                    Toast.makeText(mActivity, "请输入正确的用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();

            }
        });

        mbtn_login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        return view;
    }
    public void login(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FileUtils.IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
        File file = new File(Environment.getExternalStorageDirectory().getPath(),"patch_signed_7zip.apk");
        if (!file.exists()){
            Call<ResponseBody> gethotfix = getRequestInterface.gethotfix();
            gethotfix.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d(TAG, "onResponse:  "+response.code());
                    if (response.body() != null){
                        File file = new File(Environment.getExternalStorageDirectory().getPath(),"patch_signed_7zip.apk");
                        InputStream inputStream = response.body().byteStream();
                        OutputStream outputStream = null;
                        try {
                            byte[] fileReader = new byte[4096];

                            long fileSize = response.body().contentLength();
                            Log.d(TAG, "onResponse: ---"+fileSize);
                            long fileSizeDownloaded = 0;

//                            inputStream = body.byteStream();
                            outputStream = new FileOutputStream(file);

                            while (true) {
                                int read = inputStream.read(fileReader);

                                if (read == -1) {
                                    break;
                                }

                                outputStream.write(fileReader, 0, read);

                                fileSizeDownloaded += read;

                                Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                            }

                            outputStream.flush();
                        } catch (Exception e) {
                            Log.d(TAG, "onError"+e);
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "onFailure: --"+t);
                }
            });
        }

        Gson gson = new Gson();
        Userinfo userinfo = new Userinfo(met_username.getText().toString(), met_password.getText().toString());
        if(type == 2 && !met_username.getText().toString().equals("wgwangy")){
            Toast.makeText(mcontext,"请输入铁科院账户",Toast.LENGTH_SHORT).show();
            return;
        }
        String str = gson.toJson(userinfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str);
        Call<UserInfoLogin> call = getRequestInterface.getUserInfo(body);
        call.enqueue(new Callback<UserInfoLogin>() {
            @Override
            public void onResponse(Call<UserInfoLogin> call, Response<UserInfoLogin> response) {
                if (response.body() == null) {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                editor.putString("username", met_username.getText().toString());
                editor.putString("password", met_password.getText().toString());
                editor.commit();
                Toast.makeText(mActivity, "登录成功！", Toast.LENGTH_SHORT).show();
                getCallBack.success(response.body());
            }

            @Override
            public void onFailure(Call<UserInfoLogin> call, Throwable t) {
                Toast.makeText(mActivity, "登录失败" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
