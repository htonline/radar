package com.example.upload.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.IpSecManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.TokenWatcher;
import android.text.TextUtils;
import android.util.ArrayMap;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.interfaces.CallBackToDo;
import com.example.interfaces.GetCallBack;
import com.example.thread.ShowProgressThread;
import com.example.upload.FileListMyDialogManager;
import com.example.upload.GetRequestInterface;
import com.example.upload.LoadOnSubscribe;
import com.example.upload.SelectPicActivity;
import com.example.entity.Wall;
import com.example.helper.OkHttpTools;
import com.example.helper.WifiTool;
import com.example.helper.zbar.CaptureActivity;
import com.example.ladarmonitor.R;
import com.example.upload.UpLoadFileListAdapter;
import com.example.upload.convertor.FileConverterFactory;
import com.example.upload.entity.DetectionInformation;
import com.example.upload.entity.FileExists;
import com.example.upload.entity.FileInfo;
import com.example.upload.entity.FileMd5;
import com.example.upload.entity.FileMdRes;
import com.example.upload.entity.TestInformation;
import com.example.upload.entity.UpFilePath;
import com.example.upload.entity.UpLoadFileInfo;
import com.example.upload.entity.UserInfoLogin;
import com.example.upload.entity.Userinfo;
import com.example.upload.up.LoadCallBack;
import com.example.upload.utils.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
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

import static android.os.Build.VERSION.SDK_INT;
import static com.example.backRadar.SettingActivity.FILE_RESULT_CODE;

public class UploadFragment extends Fragment {
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
    private int tag_is_uploadraw = 0;
    private int tag_is_uploadimg01 = 0;
    private int tag_is_uploadimg02 = 1;
    private int tag_is_uploadimg03 = 1;

    private UserInfoLogin userInfoLogin;

    private EditText et_detect_username;//采集人员姓名
    private EditText et_mainServerNum;//主机序号
    private EditText et_remark;

    private ListView lv_uploadfile;
    private int listPosition;
    private UpLoadFileListAdapter adapter;
    private List<UpLoadFileInfo> mfileInfos;
    private String[] fileinfoarray = {"H0", "H1", "H-1", "H2"
            , "H-2", "H3", "H-3", "H4", "H-4", "JD1"
            , "JD-1", "JD2", "JD-2"};
    private CallBackToDo backToDo;
    protected static final int TO_SCAN_RESULT = 2;
    public static final String defaultPath = "/storage/emulated/0/datas";
    private TestInformation testInformation;
    private DetectionInformation detectionInformation;
    private FrameLayout scaninfo;
    private SharedPreferences mainPreferences;
    private SharedPreferences.Editor mainPreferenceEditor;
    private ShowProgressThread t;
    private Message msg;

    class UiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mfileInfos.get(msg.arg2).setUploadpercent(msg.arg1 + "%");
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    int listposition = msg.arg2;
                    String url = (String) msg.obj;
                    mfileInfos.get(listposition).setUploadpercent("100.0%");
                    mfileInfos.get(listposition).setFileRemotePath(url);
                    mfileInfos.get(listposition).setIsfileup(true);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(mActivity, "上传雷达数据成功", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private Handler uiHandler = new UiHandler();

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
        initFileInfosData();
        tools = new OkHttpTools();
        mainPreferences = mActivity.getSharedPreferences("uploadfrgInfo", 0);
        mainPreferenceEditor = mainPreferences.edit();
        mretrofit = new Retrofit.Builder()
                .baseUrl(FileUtils.IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mgetRequestInterface = mretrofit.create(GetRequestInterface.class);
    }

    private void initFileInfosData() {
        mfileInfos = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            UpLoadFileInfo info = new UpLoadFileInfo();
            info.setCxbh(fileinfoarray[i]);
            mfileInfos.add(info);
        }
        backToDo = new CallBackToDo() {
            @Override
            public void callBackdoSearchFile(int position) {
                listPosition = position;
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
                    urlString = urlString.replace("sdcard", "storage/emulated/0");
                    if (user_token == "") {
                        Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else {
                        searchFile(mgetRequestInterface, urlString, user_token);
//                        if (file.length() > 70 * 1024 * 1024) {
//                            File filepath = new File(file.getName()+"_");
//                            List<String> files = new ArrayList<>();
//                            if (filepath.exists() && filepath.isDirectory()){
//                                files = Arrays.asList(filepath.list());
//                            }else {
//                                FileUtils utils = new FileUtils();
//                                try {
//                                    files = utils.splitBySize(file, 20 * 1024 * 1024,filepath.getAbsolutePath());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            searchToUpFile(0, files.size() - 1, files);
//                        } else {
//                            searchFile(mgetRequestInterface, file.getName(), user_token, new GetCallBack() {
//                                @Override
//                                public void doThing() {
//                                    uploadOneFile(file, user_token);
//                                }
//                            });
//                        }
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
        };
    }

    private void searchToUpFile(int start, int stop, List<String> files) {
        if (start > stop) {
            return;
        }
//        searchFile(mgetRequestInterface, files.get(start), user_token, new GetCallBack() {
//            @Override
//            public void doThing() {
        t = new ShowProgressThread(listPosition, start, stop,
                false, 100, adapter, mfileInfos, uiHandler);
        t.start();
        uploadFileBySplit(mgetRequestInterface, new File(files.get(start)), user_token, new GetCallBack() {
            @Override
            public void doThing() {
                searchToUpFile(start + 1, stop, files);
            }
        });
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dialog, null);
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
        return view;
    }

    private void initText() {
//        testInformation = mainPreferences.getS;
        Gson gson = new Gson();
        testInformation = gson.fromJson(mainPreferences.getString("testinformation", ""), TestInformation.class);
        if (testInformation==null){
            testInformation = new TestInformation();
        }
        mTv_project_name.setText(testInformation.getProjectName());
        initScanInfo(testInformation);
    }
    private void initScanInfo(TestInformation testInformation){
        ((TextView) scaninfo.findViewById(R.id.test_infor_id)).setText(""+testInformation.getTestInforId());
        ((TextView) scaninfo.findViewById(R.id.test_id)).setText(""+testInformation.getTestId());
        ((TextView) scaninfo.findViewById(R.id.test_time)).setText(""+testInformation.getTestTime());
        ((TextView) scaninfo.findViewById(R.id.test_starting_distance)).setText(""+testInformation.getTestStartingDistance());
        ((TextView) scaninfo.findViewById(R.id.test_ending_distance)).setText(""+testInformation.getTestEndingDistance());
        ((TextView) scaninfo.findViewById(R.id.test_length)).setText(""+testInformation.getTestLength());
        ((TextView) scaninfo.findViewById(R.id.wall_rock_type)).setText(""+testInformation.getWallRockType());
        ((TextView) scaninfo.findViewById(R.id.support_tickness)).setText(""+testInformation.getSupportTickness());
        ((TextView) scaninfo.findViewById(R.id.separation_distance)).setText(""+testInformation.getSeparationDistance());
        ((TextView) scaninfo.findViewById(R.id.mesh_distance)).setText(""+testInformation.getMeshDistance());
        ((TextView) scaninfo.findViewById(R.id.annular_bar_distance)).setText(""+testInformation.getAnnularBarDistance());
        ((TextView) scaninfo.findViewById(R.id.reinfor_prt_tickness)).setText(""+testInformation.getReinforPrtTickness());
        ((TextView) scaninfo.findViewById(R.id.sec_line_arch_tickness)).setText(""+testInformation.getSecLineArchTickness());
        ((TextView) scaninfo.findViewById(R.id.sec_line_wall_tickness)).setText(""+testInformation.getSecLineWallTickness());
        ((TextView) scaninfo.findViewById(R.id.sec_line_inver_arch_tickness)).setText(""+testInformation.getSecLineInverArchTickness());
        ((TextView) scaninfo.findViewById(R.id.sec_line_filer_tickness)).setText(""+testInformation.getSecLineFilerTickness());
        ((TextView) scaninfo.findViewById(R.id.project_name)).setText(""+testInformation.getProjectName());
        ((TextView) scaninfo.findViewById(R.id.section_name)).setText(""+testInformation.getSectionName());
        ((TextView) scaninfo.findViewById(R.id.tunnel_name)).setText(""+testInformation.getTunnelName());
        ((TextView) scaninfo.findViewById(R.id.worksite_name)).setText(""+testInformation.getWorksiteName());
        ((TextView) scaninfo.findViewById(R.id.statute)).setText(""+testInformation.getStatute());
        ((TextView) scaninfo.findViewById(R.id.beizhu1)).setText(""+testInformation.getBeizhu1());
        ((TextView) scaninfo.findViewById(R.id.beizhu2)).setText(""+testInformation.getBeizhu2());
        ((TextView) scaninfo.findViewById(R.id.beizhu3)).setText(""+testInformation.getBeizhu3());
        ((TextView) scaninfo.findViewById(R.id.beizhu4)).setText(""+testInformation.getBeizhu4());
    }

    private void searchFile(GetRequestInterface getRequestInterface, String filename, String user_token) {
        FileMd5 md5 = new FileMd5();
        File file = new File(filename);
        md5.setFilename(mTv_project_name.getText().toString() + "-" + file.getName());
        try {
            md5.setMd5("");//FileUtils.getMd5(FileUtils.getByte(file)
        } catch (Exception e) {
            e.printStackTrace();
        }
        md5.setLen(file.length());
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gson.toJson(md5));
        Call<FileMdRes> call = getRequestInterface.verifyFile(body, user_token);
        call.enqueue(new Callback<FileMdRes>() {
            @Override
            public void onResponse(Call<FileMdRes> call, Response<FileMdRes> response) {
                if (response.body() != null) {
                    if (response.body().getExits().equals("file")) {
                        String[] str = file.getName().split("\\.");
                        Toast.makeText(mActivity, "服务器已有该文件", Toast.LENGTH_SHORT).show();
                        msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = urlrawpath = mTv_project_name.getText().toString() + "-" + str[0] + "." + str[1];
                        msg.arg2 = listPosition;
                        uiHandler.sendMessage(msg);
                    } else {
                        Toast.makeText(mActivity,"正在上传",Toast.LENGTH_SHORT).show();
                        if (file.length() > 70 * 1024 * 1024) {
                            File filepath = new File(file.getParent()+"/"+file.getName() + "_");
                            List<String> files = new ArrayList<>();
                            if (filepath.exists() && filepath.isDirectory()) {
                                File[] files1 = filepath.listFiles();
                                for (File f : files1){
                                    files.add(f.getAbsolutePath());
                                }
                            } else {
                                filepath.mkdir();
                                FileUtils utils = new FileUtils();
                                try {
                                    files = utils.splitBySize(file, 20 * 1024 * 1024, filepath.getAbsolutePath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            searchToUpFile(Integer.parseInt(response.body().getCount()), files.size()-1, files);
                        } else {
                            uploadOneFile(file,user_token);
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
                Toast.makeText(mActivity, "校验文件错误！:" + t.toString(), Toast.LENGTH_SHORT).show();
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
        mretrofit = new Retrofit.Builder()
                .baseUrl(FileUtils.IP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getRequestInterface = mretrofit.create(GetRequestInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", mTv_project_name.getText().toString() + "-" + file.getName(), body);
        Call<UpFilePath> upFilePathCall = getRequestInterface.uploadOneFileBySplit(part, user_token);
        upFilePathCall.enqueue(new Callback<UpFilePath>() {
            @Override
            public void onResponse(Call<UpFilePath> call, Response<UpFilePath> response) {
                if (response.body() == null) {
                    try {
                        Toast.makeText(mActivity, "" + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Exception " + e);
                    }
                } else {
                    String[] sts = response.body().getAvatar().split("\\.");
                    if (sts[sts.length - 1].equals("true")) {
                        t.setIsfinished(true);
                        String pathfile = file.getParent();
                        FileUtils.deleteFile(new File(pathfile));
                        String filepath =response.body().getAvatar();
                        String[] paths =filepath.split("\\.");
                        msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = urlrawpath = paths[0]+paths[1];
                        msg.arg2 = listPosition;
                        uiHandler.sendMessage(msg);
                    }else {
                        callBack.doThing();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpFilePath> call, Throwable t) {
                Toast.makeText(mActivity, "上传失败", Toast.LENGTH_LONG).show();
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
                        mfileInfos.get(listPosition).setUploadpercent(percent + "%");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onSuccess(Object t) {
                        Toast.makeText(mActivity, "上传成功", Toast.LENGTH_LONG).show();
                        tag_is_uploadraw = 1;
                        Gson gson = new Gson();
                        String str = gson.toJson(t);
                        UpFilePath path = gson.fromJson(str, UpFilePath.class);
                        urlrawpath = path.getAvatar();
                        mfileInfos.get(listPosition).setFileRemotePath(urlrawpath);
                        mfileInfos.get(listPosition).setIsfileup(true);
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
                    Log.d(TAG, "onActivityResult:  --> "+result);
                    Gson gson = new Gson();
                    try{
                        testInformation = gson.fromJson(result, TestInformation.class);
                        scaninfo.setVisibility(View.VISIBLE);
                        initScanInfo(testInformation);
                        mainPreferenceEditor.putString("testinformation",result);
                        mainPreferenceEditor.apply();
                    }catch (Exception e){
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

                    DetectionInformation deteInfo = new DetectionInformation();
                    deteInfo.setTunnelName(testInformation.getTunnelName());
                    deteInfo.setSectionName(testInformation.getSectionName());
                    deteInfo.setProjectName(testInformation.getProjectName());
                    deteInfo.setTestId(testInformation.getTestId());
                    deteInfo.setWorksiteName(testInformation.getWorksiteName());

                    deteInfo.setDetectionStartingDistance(info.getStartKM());
                    deteInfo.setDetectionEndingDistance(info.getStopKM());
                    deteInfo.setDetectionLength(info.getLinelength());
                    deteInfo.setDetectionLineBiaohao(info.getCxbh());
                    deteInfo.setDetectionData(info.getFileRemotePath());
                    deteInfo.setDetectionPhotos(info.getPhotoRemotePath());
//                    wall.setJiancerenyuanName(et_detect_username.getText().toString());
//                    wall.setZhujiXuhao(et_mainServerNum.getText().toString());
//                    wall.setBeizhu(et_remark.getText().toString() + "备注");
//                    wall.setToken(user_token);
//                Log.d(TAG, wall+"");
                    String result = null;
                    try {
                        result = tools.UploadWallData(deteInfo,user_token);
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
