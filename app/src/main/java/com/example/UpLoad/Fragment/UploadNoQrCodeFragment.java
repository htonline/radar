package com.example.UpLoad.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.UpLoad.FileListMyDialogManager;
import com.example.UpLoad.SelectPicActivity;
import com.example.entity.Wall;
import com.example.helper.OkHttpTools;
import com.example.helper.UploadImage;
import com.example.helper.WifiTool;
import com.example.ladarmonitor.R;

import java.io.File;
import java.lang.reflect.Method;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.BackRadar.SettingActivity.FILE_RESULT_CODE;

public class UploadNoQrCodeFragment extends Fragment {
    public static final int TO_SELECT_PHOTO = 3;
    private static final String TAG = "MyDialogActivity2";
    SharedPreferences sharePath;
    private Activity mActivity;
    private TextView textView;
    private String path = null;
    private String urlString = null;
    private String filepath = null;
    private String uploadpath;
    private Button abtn_my_dialog_cancel;
    private Button abtn_my_dialog_upload;

    private String user_token = null;

    private String temptoken = null;
    private ImageButton btn_chosePath;
    private ImageButton ib_upload_file;
    private ImageButton ib_upload_img_01;
    private ImageButton ib_upload_img_02;
    private ImageButton ib_upload_img_03;
    private ImageButton ib_login;

    private AsyncTask asyncTask;
    private AsyncTask asyncTask2;
    private AsyncTask asyncTask3;
    private Wall wall;
    //    private TextView tv_dqid;
    private EditText et_dqbh2;
    private EditText et_dqtype2;
    private EditText et_dqheight2;
    private EditText et_dqwidth2;
    private EditText et_dqthk2;
    private EditText et_cxbh2;
    private EditText et_cxorient2;
    private EditText et_cxstart2;
    private EditText et_cxstop2;
    private EditText et_dqloc2;
    WifiTool wifiAdmin;
    private String urlrawpath2;
    private String urlpicpath12;
    private String urlpicpath22;
    private String urlpicpath32;

    private int choseImgUp = 0;
    private int tag_is_uploadraw = 0;
    private int tag_is_uploadimg01 = 0;
    private int tag_is_uploadimg02 = 0;
    private int tag_is_uploadimg03 = 0;


    private EditText et_detect_username2;//采集人员姓名
    private EditText et_mainServerNum2;//主机序号
    private EditText et_remark2;

    public static final String defaultPath = "/storage/emulated/0/datas";


    public void runFirst() {
        if (SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
            Object sVmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
        } catch (Throwable e) {
            Log.e("[error]", "reflect bootstrap failed:", e);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_dialog2,null);
        wifiAdmin = new WifiTool(mActivity);
        sharePath = mActivity.getSharedPreferences("mainPeremeterOrders", 0);
        filepath = sharePath.getString("path", "/storage/emulated/0");

//        Toast.makeText(MyDialogActivity2.this,filepath+"",Toast.LENGTH_SHORT).show();
        textView = (TextView) view.findViewById(R.id.tv_pathoffile2);
        btn_chosePath = view.findViewById(R.id.my_dialog_addFile2);
        ib_upload_file = view.findViewById(R.id.ib_uploadFile2);
        et_cxbh2 = view.findViewById(R.id.et_cxbh2);
        et_cxorient2 = view.findViewById(R.id.et_cxorient2);
        et_cxstart2 = view.findViewById(R.id.et_cxstart2);
        et_cxstop2 = view.findViewById(R.id.et_cxstop2);
//        tv_dqid = findViewById(R.id.tv_dqid);
        et_dqbh2 = view.findViewById(R.id.et_dqbh2);
        et_dqheight2 = view.findViewById(R.id.et_dqheight2);
        et_dqthk2 = view.findViewById(R.id.et_dqthk2);
        et_dqtype2 = view.findViewById(R.id.et_dqtype2);
        et_dqwidth2 = view.findViewById(R.id.et_dqwidth2);
        et_dqloc2 = view.findViewById(R.id.et_dqloc2);
        et_detect_username2 = view.findViewById(R.id.et_detect_username2);
        et_mainServerNum2 = view.findViewById(R.id.et_mainServerNum2);
        et_remark2 = view.findViewById(R.id.et_remark2);
        ib_login = view.findViewById(R.id.ib_login);

        ib_upload_img_01 = view.findViewById(R.id.ib_upload_pic_012);
        ib_upload_img_02 = view.findViewById(R.id.ib_upload_pic_022);
        ib_upload_img_03 = view.findViewById(R.id.ib_upload_pic_032);

        ib_upload_img_01.setImageResource(R.drawable.uploadim16);
        ib_upload_img_02.setImageResource(R.drawable.uploadim16);
        ib_upload_img_03.setImageResource(R.drawable.uploadim16);

        ib_login.setImageResource(R.drawable.login_16);

        ib_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                wifiAdmin.closeWifi();
                Toast.makeText(mActivity,"请确认连接正常功能的wifi",Toast.LENGTH_SHORT).show();


                runFirst();
                initHttpDQTaskLogin();
                asyncTask3.execute((Object) null);
            }
        });
        ib_upload_img_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SelectPicActivity.class);
                if (user_token != null) {
                    intent.putExtra("token", user_token);
                    startActivityForResult(intent, TO_SELECT_PHOTO);
                    choseImgUp = 1;
                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();


            }
        });
        ib_upload_img_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SelectPicActivity.class);
                if (user_token != null) {
                    intent.putExtra("token", user_token);
                    startActivityForResult(intent, TO_SELECT_PHOTO);
                    choseImgUp = 2;
                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        ib_upload_img_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SelectPicActivity.class);
                if (user_token != null) {
                    intent.putExtra("token", user_token);
                    startActivityForResult(intent, TO_SELECT_PHOTO);
                    choseImgUp = 3;
                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        wall = new Wall();
        ib_upload_file.setImageResource(R.drawable.uploadfiled);
        uploadpath = "";
        abtn_my_dialog_cancel = (Button) view.findViewById(R.id.btn_my_dialog_cancel);
        abtn_my_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                wifiAdmin.openWifi();
//                Toast.makeText(MyDialogActivity2.this,"wifi已打开",Toast.LENGTH_SHORT).show();
                mActivity.finish();
            }
        });

        abtn_my_dialog_upload = (Button) view.findViewById(R.id.btn_my_dialog_upload);
        abtn_my_dialog_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_token == null) {
                    Toast.makeText(mActivity, "请先点击左上角登录", Toast.LENGTH_SHORT).show();

                } else if (et_cxstart2.getText().toString() == "") {
                    Toast.makeText(mActivity, "请填写完整", Toast.LENGTH_SHORT).show();
                } else if (tag_is_uploadraw == 0) {
                    Toast.makeText(mActivity, "请上传雷达数据", Toast.LENGTH_SHORT).show();
                } else if (tag_is_uploadimg01 == 0 || tag_is_uploadimg02 == 0 || tag_is_uploadimg03 == 0) {
                    Toast.makeText(mActivity, "请上传完整3张现场图片", Toast.LENGTH_SHORT).show();
                } else {
                    runFirst();
                    initHttpDQTask();
                    asyncTask2.execute((Object) null);


                }


            }
        });
        ib_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG,textView.getText().toString().length()+"-----------");
                if (user_token != null) {
                    if (textView.getText().toString().length() < 3) {
                        Toast.makeText(mActivity, "请选择雷达数据", Toast.LENGTH_SHORT).show();
                    } else {
                        initTask();
                        asyncTask.execute((Object) null);
                    }
                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();

            }
        });
        btn_chosePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = FileListMyDialogManager.actionStart(mActivity, filepath);
                startActivityForResult(intent, FILE_RESULT_CODE);
            }
        });
        textView.setText(uploadpath);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_RESULT_CODE:
                if (true) {
                    Bundle bundle = null;
                    if (data != null && (bundle = data.getExtras()) != null) {
                        path = bundle.getString("file");
                        uploadpath = path;
                        String str[] = path.split("/");
                        textView.setText(str[str.length - 1]);
//                path=path+"/"+tv_storeFile.getText().toString();
                    }
                }
                break;
            case TO_SELECT_PHOTO:
                if (resultCode == mActivity.RESULT_OK) {
                    int rescode = data.getIntExtra("UPLOADIMG_RES", 0);
                    String strpicpath = data.getStringExtra("UPPICRES");
                    String strpicpathd = null;
                    if (strpicpath != null) {
                        JSONObject jsonObj = JSON.parseObject(strpicpath);
                        strpicpathd = jsonObj.getString("avatar");
                    }
                    if (rescode == 1) {
                        switch (choseImgUp) {
                            case 1:
                                ib_upload_img_01.setImageResource(R.drawable.ok16);
                                urlpicpath12 = strpicpathd;
                                tag_is_uploadimg01 = 1;
                                break;
                            case 2:
                                ib_upload_img_02.setImageResource(R.drawable.ok16);
                                urlpicpath22 = strpicpathd;
                                tag_is_uploadimg02 = 1;
                                break;
                            case 3:
                                ib_upload_img_03.setImageResource(R.drawable.ok16);
                                urlpicpath32 = strpicpathd;
                                tag_is_uploadimg03 = 1;
                                break;

                        }
                    }
                }
                break;
            default:
                break;

        }


    }


    private void initHttpDQTaskLogin() {
        asyncTask3 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpTools tools = new OkHttpTools();
                String result = null;
                try {
                    result = tools.Login();
                } catch (JSONException | org.json.JSONException e) {
                    e.printStackTrace();
                }
//                        handler.sendEmptyMessage(1);
//                        Log.d(TAG, "============="+result);
//                OkHttpTools tools =  new OkHttpTools();
//                String result = null;
//                try {
//                    result = tools.Login();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                return result;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                user_token = o.toString();
                if (o.toString().indexOf("Bearer") != -1) {
                    Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
                    ib_login.setImageResource(R.drawable.loginok16);
                } else
                    Toast.makeText(mActivity, o.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }


    private void initHttpDQTask() {
        asyncTask2 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {



                wall.setDangqiangWeizhi(et_dqloc2.getText().toString());
                wall.setDangqiangBianhao(et_dqbh2.getText().toString());
                wall.setDangqiangLeixing(et_dqtype2.getText().toString());
                wall.setDangqiangGao(et_dqheight2.getText().toString());
                wall.setDangqiangKuan(et_dqwidth2.getText().toString());
                wall.setDangqiangHoudu(et_dqthk2.getText().toString());
                wall.setCexianBianhao(et_cxbh2.getText().toString());
                wall.setCexianFangxiang(et_cxorient2.getText().toString());
                wall.setCexianQidian(et_cxstart2.getText().toString());
                wall.setCexianZhongdian(et_cxstop2.getText().toString());


                wall.setBeizhu(et_remark2.getText().toString() + "备注");
                wall.setJiancerenyuanName(et_detect_username2.getText().toString());
                wall.setShujuwenjianName(urlrawpath2);
                wall.setZhujiXuhao(et_mainServerNum2.getText().toString());
                wall.setZhaopianOne(urlpicpath12);
                wall.setZhaopianTwo(urlpicpath22);
                wall.setZhaopianThree(urlpicpath32);
                wall.setToken(user_token);

//                runFirst();
                OkHttpTools tools = new OkHttpTools();
//                Log.d(TAG, wall+"");
                String result = null;
                try {
                    result = tools.UploadWallData(wall);
                } catch (JSONException | org.json.JSONException e) {
                    e.printStackTrace();
                }
//                        handler.sendEmptyMessage(1);
//                        Log.d(TAG, "============="+result);
//                OkHttpTools tools =  new OkHttpTools();
//                String result = null;
//                try {
//                    result = tools.Login();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                return result;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o.toString() == String.valueOf(1)) {
                    Toast.makeText(mActivity, "上传成功!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mActivity, o.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initTask() {
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                //                    UploadImage.uploadFile();
//                Log.d(TAG, urlString);

                urlString = urlString.replace("sdcard", "storage/emulated/0");
//                Log.d(TAG, urlString);
                if (user_token == "") {
                    Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
                }
                String response = UploadImage.uploadFile(new File(urlString), "http://39.105.125.51:8001/api/phoneOperate/updateWenjian", user_token);
                return response;

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                urlString = uploadpath;
                Toast.makeText(mActivity, "开始上传", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o.toString().indexOf("avatar") != 0) {
                    Toast.makeText(mActivity, "上传成功！", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObj = JSON.parseObject(o.toString());
                    urlrawpath2 = jsonObj.getString("avatar");
                    ib_upload_file.setImageResource(R.drawable.ok16);
                    tag_is_uploadraw = 1;
                } else
                    Toast.makeText(mActivity, (o == null ? "" : o.toString()), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
