package com.example.upload;


import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.interfaces.GetCallBack;
import com.example.upload.entity.UserInfoLogin;
import com.example.upload.fragment.LoginFragment;
import com.example.upload.fragment.UploadFragment;
import com.example.upload.fragment.UploadNoQrCodeFragment;
import com.example.ladarmonitor.R;

//import org.json.JSONException;
//import org.json.JSONObject;


public class MyDialogActivity extends FragmentActivity {
    private static UserInfoLogin staticuserInfoLogin;
    private FragmentManager fragmentManager;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydialogactivity);
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        fragmentManager = getSupportFragmentManager();
        if (staticuserInfoLogin == null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            LoginFragment mFragment = new LoginFragment(this);
            mFragment.setGetCallBack(new GetCallBack<UserInfoLogin>() {
                @Override
                public void success(UserInfoLogin userInfoLogin) {
                    staticuserInfoLogin = userInfoLogin;
                    upToFragment();
                }

                @Override
                public void failed(UserInfoLogin userInfoLogin) {
                    Toast.makeText(MyDialogActivity.this,"失败了！",Toast.LENGTH_SHORT).show();
                }
            });
            transaction.add(R.id.activity_mydialog,mFragment);
            transaction.commit();
        }else {
            upToFragment();
        }
    }
    public void upToFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        if (type == 0){
            fragment = new UploadFragment();
        }else if (type == 1){
            fragment = new UploadNoQrCodeFragment();
        }else {
            Toast.makeText(MyDialogActivity.this,"出错了！",Toast.LENGTH_LONG).show();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("userinfologin",staticuserInfoLogin);
        fragment.setArguments(bundle);
        transaction.replace(R.id.activity_mydialog,fragment);
        transaction.commit();
    }


}
