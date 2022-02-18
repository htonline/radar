package com.example.UpLoad;


import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.UpLoad.Fragment.UploadFragment;
import com.example.UpLoad.Fragment.UploadNoQrCodeFragment;
import com.example.ladarmonitor.R;

//import org.json.JSONException;
//import org.json.JSONObject;


public class MyDialogActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydialogactivity);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type",-1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (type == 0){
            UploadFragment fragment = new UploadFragment();
            transaction.add(R.id.activity_mydialog,fragment);
        }else if (type == 1){
            UploadNoQrCodeFragment fragment = new UploadNoQrCodeFragment();
            transaction.add(R.id.activity_mydialog,fragment);
        }else {
            Toast.makeText(this,"出错了！",Toast.LENGTH_LONG).show();
        }
        transaction.commit();



    }


}
