package com.example.upload.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.interfaces.GetCallBack;
import com.example.ladarmonitor.R;
import com.example.upload.GetRequestInterface;
import com.example.upload.entity.UserInfoLogin;
import com.example.upload.entity.Userinfo;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    private GetCallBack<UserInfoLogin> getCallBack;
    private Button mbtn_login;
    private Button mbtn_login_cancel;
    private EditText met_username;
    private EditText met_password;
    private Activity mActivity;
    private Context mcontext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public void setGetCallBack(GetCallBack getCallBack) {
        this.getCallBack = getCallBack;
    }

    public LoginFragment(Context context) {
        mcontext = context;
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
                .baseUrl("http://39.105.125.51:8001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
        Gson gson = new Gson();
        Userinfo userinfo = new Userinfo(met_username.getText().toString(), met_password.getText().toString());
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
                Toast.makeText(getActivity(), "登录失败" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
