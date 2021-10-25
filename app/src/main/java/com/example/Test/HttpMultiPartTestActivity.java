//package com.example.Test;
//
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//
//import com.example.helper.UploadImage;
//import com.example.ladarmonitor.R;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//
///**
// * Created by yanru.zhang on 16/7/7.
// * Email:yanru.zhang@renren-inc.com
// */
//public class HttpMultiPartTestActivity extends Activity {
//    private EditText editText;
//    private Button button;
//    private String urlString = null;
//    private AsyncTask asyncTask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_http_multi_part_test);
//        editText =(EditText) findViewById(R.id.edit_tv);
//        editText.setSelection(editText.getText().toString().length());
//        button = (Button) findViewById(R.id.btn);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //上传图片
//                initTask();
//                asyncTask.execute((Object) null);
//
//            }
//        });
//    }
//
//    private void initTask(){
//        asyncTask = new AsyncTask() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            protected Object doInBackground(Object[] params) {
//                String response = null;
//                //                    UploadImage.uploadFile();
//                UploadImage.uploadLogFile("/storage/emulated/0/DCIM/Camera/IMG_20210718_185611.jpg", "http://192.168.3.158:8090/api/image-upload");
//
//                return response;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                urlString = editText.getText().toString();
//                Toast.makeText(HttpMultiPartTestActivity.this, "开始上传", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//                Toast.makeText(HttpMultiPartTestActivity.this, (o == null ? "" : o.toString()), Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
//}
