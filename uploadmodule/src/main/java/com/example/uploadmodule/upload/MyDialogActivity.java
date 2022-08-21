package com.example.uploadmodule.upload;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uploadmodule.R;
import com.example.uploadmodule.upload.entity.UserInfoLogin;
import com.example.uploadmodule.upload.fragment.LoginFragment;
import com.example.uploadmodule.upload.fragment.UploadFragment;
//import com.example.uploadmodule.upload.fragment.UploadNoQrCodeFragment;
import com.example.uploadmodule.upload.fragment.UploadTKYDZSFragment;
import com.example.uploadmodule.upload.myinterface.GetCallBack;


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
        initConnected();
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        fragmentManager = getSupportFragmentManager();
//        if (staticuserInfoLogin == null){
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
//        }else {
//            upToFragment();
//        }
    }
    public void upToFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        if (type == 0){
            fragment = new UploadFragment();
        }else if (type == 1){
//            fragment = new UploadNoQrCodeFragment();
        }else if (type ==2 ){
            fragment = new UploadTKYDZSFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("userinfologin",staticuserInfoLogin);
        fragment.setArguments(bundle);
        transaction.replace(R.id.activity_mydialog,fragment);
        transaction.commit();
    }
    private void initConnected() {
        if(Build.VERSION.SDK_INT >= 21){
            final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
            NetworkRequest request = builder.build();
            ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        connectivityManager.bindProcessToNetwork(network);
                    } else {
                        ConnectivityManager.setProcessDefaultNetwork(network);
                    }
                }
            };
            connectivityManager.requestNetwork(request, callback);
        }
    }

}
