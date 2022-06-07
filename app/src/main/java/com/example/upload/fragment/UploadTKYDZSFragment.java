package com.example.upload.fragment;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.backRadar.SettingActivity.FILE_RESULT_CODE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.helper.OkHttpTools;
import com.example.helper.WifiTool;
import com.example.helper.zbar.CaptureActivity;
import com.example.interfaces.CallBackToDo;
import com.example.ladarmonitor.R;
import com.example.upload.FileListMyDialogManager;
import com.example.upload.GetRequestInterface;
import com.example.upload.SelectPicActivity;
import com.example.upload.UpLoadFileListAdapter;
import com.example.upload.adapter.ScaninfoListViewAdapter;
import com.example.upload.annotation.scaninfoanno;
import com.example.upload.entity.DetectionInformation;
import com.example.upload.entity.DeviceRes;
import com.example.upload.entity.TestInformation;
import com.example.upload.entity.TkyDetectionInformation;
import com.example.upload.entity.TkyTestInformation;
import com.example.upload.entity.UpLoadFileInfo;
import com.example.upload.entity.UserInfoLogin;
import com.example.upload.utils.FileUtils;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadTKYDZSFragment extends Fragment {
    public static final int TO_SELECT_PHOTO = 3;
    private static final String TAG = "MyDialogActivity";
    SharedPreferences sharePath;
    private Activity mActivity;
    private OkHttpTools tools;
    //    private TextView textView;
    private String path = null;
    private String urlString = null;
    private String filepath = null;
    private String uploadpath;
    private Button abtn_my_dialog_cancel;
    private Button abtn_my_dialog_upload;
    private ImageButton ib_touch_show;
    private String user_token = null;
    private String tkybydbh = null;
    private ThreadPoolExecutor threadPool;

    private String temptoken = null;
    //    private ImageButton btn_chosePath;
    private ImageButton ib_scan;
    //    private ImageButton ib_upload_file;
//    private ImageButton ib_upload_img_01;
//    private ImageButton ib_upload_img_02;
//    private ImageButton ib_upload_img_03;
    private ImageView ib_login;
    //    private TextView mtv_up_percent;
    private AsyncTask asyncTask2;

    private TextView mTv_project_name;
    //    private TextView tv_dqid;
//    private TextView tv_dqbh;
//    private TextView tv_dqtype;
//    private TextView tv_dqheight;
//    private TextView tv_dqwidth;
//    private TextView tv_dqthk;
    //    private EditText tv_cxbh;
//    private EditText tv_cxorient;
//    private TextView tv_cxstart;
//    private TextView tv_cxstop;
//    private TextView tv_dqloc;
    private TextView mtv_login_username;
    WifiTool wifiAdmin;
    private String urlrawpath;
    private String urlpicpath1;
    private String urlpicpath2;
    private String urlpicpath3;
    private Retrofit mretrofit;
    private GetRequestInterface mgetRequestInterface;
    private int choseImgUp = 0;
    private int tag_is_uploadimg01 = 0;
    private int tag_is_uploadimg02 = 1;
    private int tag_is_uploadimg03 = 1;

    private UserInfoLogin userInfoLogin;

    private EditText et_detect_username;//采集人员姓名
    private EditText et_mainServerNum;//主机序号
    private EditText et_remark;

    private TkyTestInformation tkyTestInformation;

    private boolean isuploadcancel = false;
    private ListView lv_uploadfile;
    private int listPosition;
    private UpLoadFileListAdapter adapter;
    private List<UpLoadFileInfo> mfileInfos;
    private String[] fileinfoarray = {"L1", "L2", "L3", "L4"
            , "L5", "L6", "L7", "L8", "L9"};
    private List<AbstractMap.SimpleEntry<String, String>> scaninfolists;
    private CallBackToDo backToDo;
    protected static final int TO_SCAN_RESULT = 2;
    public static final String defaultPath = "/storage/emulated/0/datas";

    private DetectionInformation detectionInformation;
    private FrameLayout scaninfo;
    private SharedPreferences mainPreferences;
    private SharedPreferences.Editor mainPreferenceEditor;
    private Message msg;
    private HashMap<Integer, UploadThread> map;

    private ListView scaninfolistview;
    private ScaninfoListViewAdapter scaninfoListViewAdapter;
//    class UiHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    mfileInfos.get(msg.arg2).setUploadpercent(msg.arg1 + "%");
//                    adapter.notifyDataSetChanged();
//                    break;
//                case 2:
//                    int listposition = msg.arg2;
//                    String url = (String) msg.obj;
//                    mfileInfos.get(listposition).setUploadpercent("100.0%");
//                    mfileInfos.get(listposition).setFileRemotePath(url);
//                    mfileInfos.get(listposition).setIsfileup(true);
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(mActivity, "上传雷达数据成功", Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
//    }
//
//    private Handler uiHandler = new UiHandler();

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
        Bundle bundle = getArguments();
        userInfoLogin = (UserInfoLogin) bundle.getSerializable("userinfologin");
        tools = new OkHttpTools();
        mainPreferences = mActivity.getSharedPreferences("tkyuploadfrgInfo", 0);
        mainPreferenceEditor = mainPreferences.edit();
        initFileInfosData();
        mretrofit = new Retrofit.Builder()
                .baseUrl(FileUtils.IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mgetRequestInterface = mretrofit.create(GetRequestInterface.class);
        map = new HashMap<>();
        scaninfolists = new ArrayList<>();

    }

    private void initFileInfosData() {
        mfileInfos = new ArrayList<>();
        if (TextUtils.isEmpty(mainPreferences.getString("cxbh0", ""))) {
            for (int i = 0; i < 9; i++) {
                UpLoadFileInfo info = new UpLoadFileInfo();
                info.setCxbh(fileinfoarray[i]);
                mfileInfos.add(info);
            }
        } else {
            for (int i = 0; i < 9; i++) {
                UpLoadFileInfo info = new UpLoadFileInfo();

                info.setCxbh(mainPreferences.getString("cxbh" + i, ""));
                info.setStartKM(mainPreferences.getString("startKM" + i, ""));
                info.setStopKM(mainPreferences.getString("stopKM" + i, ""));
                info.setFilePath(mainPreferences.getString("filePath" + i, ""));
                info.setLinelength(mainPreferences.getString("linelength" + i, ""));

                info.setUploadpercent(mainPreferences.getString("uploadpercent" + i, "0.0%"));
                info.setIsfileup(mainPreferences.getBoolean("isfileup" + i, false));
                info.setFileRemotePath(mainPreferences.getString("fileRemotePath" + i, ""));
                info.setPhotoPath(mainPreferences.getString("photoPath" + i, ""));

                info.setPhotoRemotePath(mainPreferences.getString("photoRemotePath" + i, ""));
                info.setIsphotoup(mainPreferences.getBoolean("isphotoup" + i, false));

                mfileInfos.add(info);
            }
        }

        backToDo = new CallBackToDo() {
            @Override
            public void callBackdoSearchFile(int position) {
                listPosition = position;
                EditText et_start = lv_uploadfile.getChildAt(position).findViewById(R.id.detectionStartingDistance);
                EditText et_stop = lv_uploadfile.getChildAt(position).findViewById(R.id.detectionEndingDistance);
                EditText et_length = lv_uploadfile.getChildAt(position).findViewById(R.id.detectionLength);
                listPosition = position;

                if (!TextUtils.isEmpty(et_start.getText().toString())) {
                    mfileInfos.get(listPosition).setStartKM(et_start.getText().toString());
                }
                if (!TextUtils.isEmpty(et_stop.getText().toString())) {
                    mfileInfos.get(listPosition).setStopKM(et_stop.getText().toString());
                }
                if (!TextUtils.isEmpty(et_length.getText().toString())) {
                    mfileInfos.get(listPosition).setLinelength(et_length.getText().toString());
                }
                adapter.notifyDataSetChanged();
                Intent intent = FileListMyDialogManager.actionStart(mActivity, filepath);
                startActivityForResult(intent, FILE_RESULT_CODE);
            }

            @Override
            public void callBackdoUploadFile(int position) {
                listPosition = position;
                if (TextUtils.isEmpty(mfileInfos.get(position).getFilePath())) {
                    Toast.makeText(mActivity, "请选择雷达数据", Toast.LENGTH_SHORT).show();
                } else {
                    urlString = uploadpath;
                    if (urlString == null){
                        return;
                    }
                    urlString = urlString.replace("sdcard", "storage/emulated/0");
                    if (user_token == "") {
                        Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else {
//                        searchFile(mgetRequestInterface, urlString, user_token);
                        UploadThread thread = new UploadThread(position, false, adapter, mfileInfos, user_token
                                , mgetRequestInterface, mTv_project_name.getText().toString(), mActivity);
                        thread.start();
                        map.put(position, thread);
                    }
                }
            }

            @Override
            public void callBackdoUploadPhoto(int position) {
                listPosition = position;
                Intent intent = new Intent(mActivity, SelectPicActivity.class);
                intent.putExtra("token", user_token);
                startActivityForResult(intent, TO_SELECT_PHOTO);
                choseImgUp = 1;
            }

            @Override
            public void callBackCancelUpload(int position) {
//                listPosition = position;
//                isuploadcancel = true;
//                Log.d(TAG, "callBackCancelUpload:  --- "+isuploadcancel);
                UploadThread uploadThread = map.get(position);
                if (uploadThread != null){
                    uploadThread.setShut(true);
                }
                mfileInfos.get(position).setFilePath(null);
                mfileInfos.get(position).setUploadpercent("0.0%");
                mfileInfos.get(position).setIsfileup(false);
                adapter.notifyDataSetChanged();
            }
        };
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dialog_tky, null);
        wifiAdmin = new WifiTool(mActivity);
        sharePath = mActivity.getSharedPreferences("mainPeremeterOrders", 0);
        filepath = sharePath.getString("path", "/storage/emulated/0");
        mtv_login_username = view.findViewById(R.id.tv_dialog_username);
//        Toast.makeText(MyDialogActivity.this,filepath+"",Toast.LENGTH_SHORT).show();
//        textView = (TextView) view.findViewById(R.id.tv_pathoffile);
//        btn_chosePath = view.findViewById(R.id.my_dialog_addFile);
        ib_scan = view.findViewById(R.id.iv_scanicon);
//        ib_upload_file = view.findViewById(R.id.ib_uploadFile);
//        tv_cxbh = view.findViewById(R.id.tv_cxbh);
//        tv_cxorient = view.findViewById(R.id.tv_cxorient);
//        tv_cxstart = view.findViewById(R.id.tv_cxstart);
//        tv_cxstop = view.findViewById(R.id.tv_cxstop);
//        tv_dqid = findViewById(R.id.tv_dqid);
        mTv_project_name = view.findViewById(R.id.project_name);
//        tv_dqbh = view.findViewById(R.id.tv_dqbh);
//        tv_dqheight = view.findViewById(R.id.tv_dqheight);
//        tv_dqthk = view.findViewById(R.id.tv_dqthk);
//        tv_dqtype = view.findViewById(R.id.tv_dqtype);
//        tv_dqwidth = view.findViewById(R.id.tv_dqwidth);
//        tv_dqloc = view.findViewById(R.id.tv_dqloc);
        et_detect_username = view.findViewById(R.id.et_detect_username);
        et_mainServerNum = view.findViewById(R.id.et_mainServerNum);
        et_remark = view.findViewById(R.id.et_remark);
        ib_login = view.findViewById(R.id.ib_login);
        lv_uploadfile = view.findViewById(R.id.lv_uploadCX);
        scaninfo = view.findViewById(R.id.scannerInfo);
        ib_touch_show = view.findViewById(R.id.ib_touchShow);
        scaninfolistview = view.findViewById(R.id.scaninfolistview);
        ib_touch_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scaninfo.getVisibility() == View.VISIBLE) {
                    scaninfo.setVisibility(View.GONE);
                } else {
                    scaninfo.setVisibility(View.VISIBLE);
                }
            }
        });
        scaninfo.setVisibility(View.GONE);
        ((Button) scaninfo.findViewById(R.id.scan_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaninfo.setVisibility(View.GONE);
            }
        });
//        mtv_up_percent = view.findViewById(R.id.upload_percent);
//        ib_upload_img_01 = view.findViewById(R.id.ib_upload_pic_01);
//        ib_upload_img_02 = view.findViewById(R.id.ib_upload_pic_02);
//        ib_upload_img_03 = view.findViewById(R.id.ib_upload_pic_03);

//        ib_upload_img_01.setImageResource(R.drawable.uploadim16);
//        ib_upload_img_02.setImageResource(R.drawable.uploadim16);
//        ib_upload_img_03.setImageResource(R.drawable.uploadim16);

        ib_login.setImageResource(R.drawable.loginok16);
        mtv_login_username.setText(userInfoLogin.getUser().getUserIn().getNickName());
        user_token = userInfoLogin.getToken();
        adapter = new UpLoadFileListAdapter(mActivity, backToDo, mfileInfos);
        lv_uploadfile.setAdapter(adapter);
//        ib_upload_img_01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mActivity, SelectPicActivity.class);
//                if (user_token != null) {
//                    intent.putExtra("token", user_token);
//                    startActivityForResult(intent, TO_SELECT_PHOTO);
//                    choseImgUp = 1;
//                } else {
//                    Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        ib_upload_img_02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mActivity, SelectPicActivity.class);
//                if (user_token != null) {
//                    intent.putExtra("token", user_token);
//                    startActivityForResult(intent, TO_SELECT_PHOTO);
//                    choseImgUp = 2;
//                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
//            }
//        });
//        ib_upload_img_03.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mActivity, SelectPicActivity.class);
//                if (user_token != null) {
//                    intent.putExtra("token", user_token);
//                    startActivityForResult(intent, TO_SELECT_PHOTO);
//                    choseImgUp = 3;
//                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
//            }
//        });

//        ib_upload_file.setImageResource(R.drawable.uploadfiled);
        uploadpath = null;
        abtn_my_dialog_cancel = (Button) view.findViewById(R.id.btn_my_dialog_cancel);
        abtn_my_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                wifiAdmin.openWifi();
//                Toast.makeText(MyDialogActivity.this,"wifi已打开",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 9; i++) {
                    EditText et_start = lv_uploadfile.getChildAt(i).findViewById(R.id.detectionStartingDistance);
                    EditText et_stop = lv_uploadfile.getChildAt(i).findViewById(R.id.detectionEndingDistance);
                    EditText et_length = lv_uploadfile.getChildAt(i).findViewById(R.id.detectionLength);

                    if (!TextUtils.isEmpty(et_start.getText().toString())) {
                        mfileInfos.get(i).setStartKM(et_start.getText().toString());
                    }
                    if (!TextUtils.isEmpty(et_stop.getText().toString())) {
                        mfileInfos.get(i).setStopKM(et_stop.getText().toString());
                    }
                    if (!TextUtils.isEmpty(et_length.getText().toString())) {
                        mfileInfos.get(i).setLinelength(et_length.getText().toString());
                    }

                    mainPreferenceEditor.putString("cxbh" + i, mfileInfos.get(i).getCxbh());
                    mainPreferenceEditor.putString("startKM" + i, mfileInfos.get(i).getStartKM());
                    mainPreferenceEditor.putString("stopKM" + i, mfileInfos.get(i).getStopKM());
                    mainPreferenceEditor.putString("filePath" + i, mfileInfos.get(i).getFilePath());
                    mainPreferenceEditor.putString("linelength" + i, mfileInfos.get(i).getLinelength());

                    mainPreferenceEditor.putString("uploadpercent" + i, mfileInfos.get(i).getUploadpercent());
                    mainPreferenceEditor.putBoolean("isfileup" + i, mfileInfos.get(i).getIsfileup());
                    mainPreferenceEditor.putString("fileRemotePath" + i, mfileInfos.get(i).getFileRemotePath());
                    mainPreferenceEditor.putString("photoPath" + i, mfileInfos.get(i).getPhotoPath());

                    mainPreferenceEditor.putString("photoRemotePath" + i, mfileInfos.get(i).getPhotoRemotePath());
                    mainPreferenceEditor.putBoolean("isphotoup" + i, mfileInfos.get(i).getIsphotoup());

                }

                mainPreferenceEditor.apply();

                mActivity.finish();
            }
        });

        abtn_my_dialog_upload = (Button) view.findViewById(R.id.btn_my_dialog_upload);
        abtn_my_dialog_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runFirst();
                initHttpDQTask();
                asyncTask2.execute((Object) null);

            }
        });
//        ib_upload_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.d(TAG,textView.getText().toString().length()+"-----------");
//                if (user_token != null) {
//                    if (textView.getText().toString().length() < 3) {
//                        Toast.makeText(mActivity, "请选择雷达数据", Toast.LENGTH_SHORT).show();
//                    } else {
//                        urlString = uploadpath;
//                        urlString = urlString.replace("sdcard", "storage/emulated/0");
//                        if (user_token == "") {
//                            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//                        }else {
//                            uploadOneFile(new File(urlString), user_token);
//                        }
//                    }
//                } else Toast.makeText(mActivity, "请先登录", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        btn_chosePath.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = FileListMyDialogManager.actionStart(mActivity, filepath);
//                startActivityForResult(intent, FILE_RESULT_CODE);
//            }
//        });
//        textView.setText(uploadpath);
        ib_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                startActivityForResult(intent, TO_SCAN_RESULT);
            }
        });
        initText();
        scaninfoListViewAdapter = new ScaninfoListViewAdapter(this.getActivity(), scaninfolists);
        scaninfolistview.setAdapter(scaninfoListViewAdapter);

        return view;
    }

    private void initText() {
        scaninfolists.clear();
        String resscan = mainPreferences.getString("tkyTestInformation", "");
        try {
            if (!TextUtils.isEmpty(resscan)) {
                Gson gson = new Gson();
                tkyTestInformation = gson.fromJson(resscan, TkyTestInformation.class);
                Field[] fields = TkyTestInformation.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.get(tkyTestInformation) == null) {
                        continue;
                    }
                    AbstractMap.SimpleEntry<String, String> simpleEntry = new AbstractMap.SimpleEntry<>(field.getAnnotation(scaninfoanno.class).value(), String.valueOf(field.get(tkyTestInformation)));
                    scaninfolists.add(simpleEntry);
                }
                tkybydbh = tkyTestInformation.getBydbh();
                mTv_project_name.setText(tkyTestInformation.getXmname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        mfileInfos.get(listPosition).setFilePath(path);
                        adapter.notifyDataSetChanged();
//                        textView.setText(str[str.length - 1]);
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
                                mfileInfos.get(listPosition).setIsphotoup(true);
                                mfileInfos.get(listPosition).setPhotoRemotePath(strpicpathd);
                                adapter.notifyDataSetChanged();
//                                ib_upload_img_01.setImageResource(R.drawable.ok16);
                                urlpicpath1 = strpicpathd;
                                tag_is_uploadimg01 = 1;
                                break;
                            case 2:
//                                ib_upload_img_02.setImageResource(R.drawable.ok16);
                                urlpicpath2 = strpicpathd;
                                tag_is_uploadimg02 = 1;
                                break;
                            case 3:
//                                ib_upload_img_03.setImageResource(R.drawable.ok16);
                                urlpicpath3 = strpicpathd;
                                tag_is_uploadimg03 = 1;
                                break;

                        }
                    }
                }
                break;
            case TO_SCAN_RESULT:
                if (resultCode == mActivity.RESULT_OK) {
                    String result = data.getStringExtra(CaptureActivity.EXTRA_STRING);
                    Gson gson = new Gson();
                    try {
                        scaninfolists.clear();
                        tkyTestInformation = gson.fromJson(result, TkyTestInformation.class);
                        scaninfo.setVisibility(View.VISIBLE);
                        Field[] fields = TkyTestInformation.class.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (field.get(tkyTestInformation) == null) {
                                continue;
                            }
                            AbstractMap.SimpleEntry<String, String> simpleEntry = new AbstractMap.SimpleEntry<>(field.getAnnotation(scaninfoanno.class).value(), String.valueOf(field.get(tkyTestInformation)));
                            scaninfolists.add(simpleEntry);
                        }
                        scaninfoListViewAdapter.notifyDataSetChanged();
                        mTv_project_name.setText(tkyTestInformation.getXmname());
                        mfileInfos = new ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            UpLoadFileInfo info = new UpLoadFileInfo();
                            info.setCxbh(fileinfoarray[i]);
                            mfileInfos.add(info);
                        }
                        tkybydbh = tkyTestInformation.getBydbh();
                        adapter = new UpLoadFileListAdapter(mActivity, backToDo, mfileInfos);
                        lv_uploadfile.setAdapter(adapter);
                        mainPreferenceEditor.putString("tkyTestInformation", result);
                        mainPreferenceEditor.apply();
                    } catch (Exception e) {
                        Log.d(TAG, "error " + e);
                        Toast.makeText(mActivity, "请扫描正确二维码", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                break;
            default:
                break;

        }


    }

    private void initHttpDQTask() {
        asyncTask2 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                int i = 0;
                List<String> results = new ArrayList<>();
                for (UpLoadFileInfo info : mfileInfos) {
                    EditText et_start = lv_uploadfile.getChildAt(i).findViewById(R.id.detectionStartingDistance);
                    EditText et_stop = lv_uploadfile.getChildAt(i).findViewById(R.id.detectionEndingDistance);
                    EditText et_length = lv_uploadfile.getChildAt(i).findViewById(R.id.detectionLength);
                    i++;
                    if (TextUtils.isEmpty(et_start.getText())
                            && TextUtils.isEmpty(et_stop.getText())
                            && TextUtils.isEmpty(et_length.getText())
                            && TextUtils.isEmpty(info.getFilePath())
                            && TextUtils.isEmpty(info.getPhotoPath())) {
                        continue;
                    }
                    if ((TextUtils.isEmpty(et_start.getText())
                            || TextUtils.isEmpty(et_stop.getText())
                            || TextUtils.isEmpty(info.getFileRemotePath())
                            || TextUtils.isEmpty(info.getPhotoRemotePath()))) {
                        results.add("-1");
                        return results;
                    }
                    info.setStartKM(et_start.getText().toString());
                    info.setStopKM(et_stop.getText().toString());
                    info.setLinelength(et_length.getText().toString());


                    TkyDetectionInformation deteInfo = new TkyDetectionInformation();
//                    deteInfo.setTunnelName(testInformation.getTunnelName());
//                    deteInfo.setSectionName(testInformation.getSectionName());
//                    deteInfo.setProjectName(testInformation.getProjectName());
//                    deteInfo.setTestId(testInformation.getTestId());
//                    deteInfo.setWorksiteName(testInformation.getWorksiteName());

                    deteInfo.setSjstartMile(info.getStartKM());
                    deteInfo.setSjstopMile(info.getStopKM());
                    deteInfo.setBydbh(tkybydbh);
                    deteInfo.setAccount(userInfoLogin.getUser().getUserIn().getNickName());
                    deteInfo.setAppFileTypePhoto(info.getPhotoRemotePath());
                    deteInfo.setAppFileTypeRadar(info.getFileRemotePath());
                    deteInfo.setTestType("1");
                    deteInfo.setBeizhu1(info.getCxbh());
                    String result = null;
                    try {
                        result = tools.UploadTkyData(deteInfo, user_token);
                        results.add(result);
                    } catch (JSONException | org.json.JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "doInBackground:  --> " + result);
                }

//                wall.setBeizhu(et_remark.getText().toString() + "备注");
//                wall.setJiancerenyuanName(et_detect_username.getText().toString());
//                wall.setShujuwenjianName(urlrawpath);
//                wall.setZhujiXuhao(et_mainServerNum.getText().toString());
//                wall.setZhaopianOne(urlpicpath1);
////                wall.setZhaopianTwo(urlpicpath2);
////                wall.setZhaopianThree(urlpicpath3);
//                wall.setToken(user_token);

//                runFirst();
//                OkHttpTools tools = new OkHttpTools();
////                Log.d(TAG, wall+"");
//                String result = null;
//                try {
//                    result = tools.UploadWallData(wall);
//                } catch (JSONException | org.json.JSONException e) {
//                    e.printStackTrace();
//                }
//                        handler.sendEmptyMessage(1);
//                        Log.d(TAG, "============="+result);
//                OkHttpTools tools =  new OkHttpTools();
//                String result = null;
//                try {
//                    result = tools.Login();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                return results;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
//                if (o.toString() == String.valueOf(1)) {
//                    Toast.makeText(mActivity, "上传成功!", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(mActivity, o.toString(), Toast.LENGTH_SHORT).show();
//                }
                List<String> stringList = (List<String>) o;
                for (String s : stringList) {
                    if (s.equals("-1")) {
                        Toast.makeText(mActivity, "请将单道信息填写完整！", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!s.equals("1")) {
                        Toast.makeText(mActivity, "上传失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (stringList.isEmpty()) {
                    Toast.makeText(mActivity, "无信息", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "上传成功", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
