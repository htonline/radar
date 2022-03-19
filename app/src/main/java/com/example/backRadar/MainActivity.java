package com.example.backRadar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.upload.MyDialogActivity;
import com.example.customizingViews.ColoursView;
import com.example.customizingViews.MyBatterView;
import com.example.customizingViews.RadarView;
import com.example.fragments.LeftFragmentOfMainActivity;
import com.example.fragments.RightFragmentOfMainActivity;
import com.example.helper.Dataprocess;
import com.example.helper.WifiTool;
import com.example.interfaces.ShowSaveFinish;
import com.example.ladarmonitor.R;
import com.example.orders.NormalOrders;
import com.example.thread.ReadThread;
import com.example.thread.WriteBodyThread;
import com.example.thread.WriteHeadThread;
import com.example.thread.WriteRearThread;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends Activity {
    private int tagForCloseSetting=0;
    private static RandomAccessFile mrafRaw;
    private static RandomAccessFile mrafColor;
    private WriteBodyThread writeBodyThread;
    private WriteRearThread writeRearThread;
    static WriteHeadThread writeHeadThread = null;
    private WriteBodyThread writeBodyThreadRaw;
    private WriteRearThread writeRearThreadRaw;
    static WriteHeadThread writeHeadThreadRaw = null;
    static ExecutorService pool;
    static ExecutorService poolRaw;
    //	public static final String defaultPath="/sdcard/datas";
//	private String path=null;
    private static final String TAG = "MainActivity";
    FragmentManager fManager = null;
    private LeftFragmentOfMainActivity lFragment;
    private RightFragmentOfMainActivity rFragment;
    private ImageButton ibtn_startAndSuspend;
//    private TextView tv_numofSave;
    private ImageButton ibtn_wifi;
    private ImageButton ibtn_setting;
    private ImageButton ibtn_stop;
    private ImageButton ibtn_upload;
    private ColoursView coloursView = null;
    private RadarView radarView = null;
    private int IfSaveTheRadar=0;
    private EditText met_radarName = null;

    private TextView tv_numberOfReceive = null;
    private TextView tv_path = null;
    private TextView tv_timeWindow = null;
    private TextView tv_frequency = null;
    private TextView tv_delay = null;
    private TextView tv_markNumber = null;
    private static Dataprocess dataprocessMain = null;
    //	private TextView tv_numberOfLose=null;
//    private TextView tv_logo = null;
    private Button btn_mark = null;


    WifiTool wifiAdmin;

    private Button btn_wifi_open;
    private Button btn_wifi_connect;

    private MyBatterView myBatterView;


    //点击wifi按钮弹出提示框
    ProgressDialog progressDialog;
    //计数器，判断继续还是暂停
    private int counter = 0;
    //计数器判断开始按钮是否点击过
    private int clickOrNot = 0;
    //发送给下位机的命令
    NormalOrders nOrders = null;
    static ReadThread readThread = null;
//    static WriteThread writeThread = null;
//	JudgeHaveThread judgeHaveThread=null;
//	JudgeMetalThread judgeMetalThread=null;



    //判断是否点击过停止按钮，如果点击过点击开始按钮的时候弹出对话框询问是否覆盖
    boolean judge_clickStopIbtn = false;

    //判断是否打标
    public static boolean judge_MartOrNot = false;
    //打标数量
    private int markNumber = 0;

    //文件序列号
    int serialNumberOfFile = 0;

    static DatagramSocket ds = null;
    DatagramPacket dp = null;
    byte creatLink[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x01, 0x00};
    byte startCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x08, 0x00};
    byte suspendCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x10, 0x00};
    byte continueCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x20, 0x00};
    byte stopCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x40, 0x00};


    //当settingActivity点了确定后，会返回true，shareJudge用于接收结果
    private SharedPreferences shareJudge = null;
    private SharedPreferences shareXRaw = null;
    private SharedPreferences shareGainData = null;
    private SharedPreferences shareCoeGain = null;
    private SharedPreferences shareBRaw = null;
    private SharedPreferences shareCoeBackgrd = null;
    private SharedPreferences shareBackgrdData = null;
    private SharedPreferences shareSumColorData = null;
    private SharedPreferences sharemhighf = null;
    private SharedPreferences sharemlowf = null;
    private SharedPreferences mainPeremeterOrders = null;
    private SharedPreferences sharemfiltermode = null;
    private SharedPreferences shareifgain = null;
    private SharedPreferences shareifbackremove = null;
    SharedPreferences.Editor mainPeremeterOrdersEditor = null;

    //静态IP地址
    public static final String STATICIP = "192.168.0.100";

    //文件类型
    public static final String type = ".raw";

    //一道数据的点数
    public static final int Const_NumberOfVerticalDatas = 512;
    private int colorList[] = new int[Const_NumberOfVerticalDatas];
    private short colorGap[] = new short[Const_NumberOfVerticalDatas];
    private short RawcolorGap[] = new short[Const_NumberOfVerticalDatas];
    private short TempColorList[] = new short[Const_NumberOfVerticalDatas];


    private int gainData[] = new int[Const_NumberOfVerticalDatas];
    private int backgrdData[] = new int[Const_NumberOfVerticalDatas];
    private short sumcolorData[] = new short[Const_NumberOfVerticalDatas];
    private float coeGain;

    public static final int Const_NEW_NUMBEROFDATA = 513;
    private short reciveData[] = new short[Const_NEW_NUMBEROFDATA];
    private short battery = -1;


    private static int tempShare;
    private static int tempifgain;
    private static int tempifbackremove;

    private static int tempSetting = 2;


    private static float numlowf;
    private static float numhighf;


    private final String[] items = {"现场检测人员", "专业设置人员"};
    private final String[] itemsForUpload = {"扫码填写模式", "手动填写模式"};
    private byte[] Rwcolorgap = new byte[Const_NumberOfVerticalDatas*2];
    private byte[] colorgap = new byte[Const_NumberOfVerticalDatas*2];

    int ddi=0 ;
    //记录当前数据是第几道
    private int numberOfLogo;
    //记录实际收到多少道数据
    private int counterOfReal;

    //点击两次退出程序，判断间隔时间
    private long firstTime = 0;
    //判断有无物体
    private int temppnum=1;
    private int judge_haveOrNot = -1;
    int ttti =0;
    private Handler handlerOfColour = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (clickOrNot != 0){
                        reciveData = (short[]) msg.obj;
                        int tempJudge = msg.arg1;
                        if (tempJudge==1){
                            judge_MartOrNot=true;
                            markNumber++;
                            tv_markNumber.setText(markNumber + "");
                        }
                        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                            colorGap[i] = reciveData[i];
                            RawcolorGap[i] = reciveData[i];
                            Rwcolorgap[2*i]=shortToByte(reciveData[i])[0];
                            Rwcolorgap[2*i+1]=shortToByte(reciveData[i])[1];
                        }
                        if(judge_MartOrNot){
                            Rwcolorgap[0]=(byte) 0xff;
                            Rwcolorgap[1]= (byte) 0x00;
                            Rwcolorgap[2]=(byte) 0xff;
                            Rwcolorgap[3]=(byte) 0x00;
                            Rwcolorgap[4]= (byte) 0xff;
                            Rwcolorgap[5]=(byte) 0x00;
                            Rwcolorgap[6]=(byte) 0xff;
                            Rwcolorgap[7]=(byte) 0x00;
                        }
//                    if (mrafRaw == null) Log.d(TAG, "handleMessage: !!!!!!!!!!!");
                        writeBodyThreadRaw = new WriteBodyThread(mrafRaw,Rwcolorgap);
                        poolRaw.execute(writeBodyThreadRaw);
//                    writeThread.setRwcolorgap(Rwcolorgap);
                        battery = reciveData[512];
//                    Log.d(TAG, "handleMessage:  --> battery --> "+battery);
                        int mbtr_num = (int)((float)(battery / 100)*1.1 - 20) * 10;
                        myBatterView.setPro(mbtr_num);
//				judgeHaveThread.setColorGap(colorGap);
//				judgeMetalThread.setColorGap(colorGap);
                        if (tempifbackremove == 1) {
                            short[] newnoise = new short[Const_NumberOfVerticalDatas];
                            for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                                newnoise[i] = (short) backgrdData[i];
                            }
//                        Log.d(TAG, "------tempifbackremove------------");
                            float[] noise = dataprocessMain.calm_gainnoiseb(newnoise);
                            colorGap = dataprocessMain.backgroundRemoveProcess(Const_NumberOfVerticalDatas, colorGap, noise, sumcolorData);
                        }

                        if (tempShare != 4) {
//                        Log.d(TAG, "--------tempShare----------");
                            dataprocessMain.m_filterP.setM_low_f(numlowf);
                            dataprocessMain.m_filterP.setM_high_f(numhighf);
                            dataprocessMain.setM_iffilter(true);
                            dataprocessMain.m_filterP.setM_mode(tempShare);
                            colorGap = dataprocessMain.dodataprocess(colorGap);
                        }
                        if (tempifgain == 1) {
//                        Log.d(TAG, "--------tempifgain----------");
                            colorGap = dataprocessMain.gainProcess(colorGap, gainData, coeGain, Const_NumberOfVerticalDatas);
                        }

                        if (judge_MartOrNot) {
                            for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                                colorList[i] = 0x0000ff;
                                colorGap[i] = (short) (colorGap[i]);
                            }
                        } else {
                            for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                                int color = ((colorGap[i]) / 256)+128;
                                colorList[i] = Color.rgb(color, color, color);
//                            TempColorList[i] = (short) (colorGap[i]);
                                colorgap[2*i]=shortToByte(colorGap[i])[0];
                                colorgap[2*i+1]=shortToByte(colorGap[i])[1];
                            }
                        }
                        if(judge_MartOrNot){
                            colorgap[0]=(byte) 0xff;
                            colorgap[1]= (byte) 0x00;
                            colorgap[2]=(byte) 0xff;
                            colorgap[3]=(byte) 0x00;
                            colorgap[4]= (byte) 0xff;
                            colorgap[5]=(byte) 0x00;
                            colorgap[6]=(byte) 0xff;
                            colorgap[7]=(byte) 0x00;
                        }
//                    writeThread.setColorgap(colorgap);
//                    writeThread.setJudgeIfRepeat(false);
//                    writeThread.setJudgeNumber(3);
//                    writeThread.setJudgeIfRepeat(true);
                        writeBodyThread = new WriteBodyThread(mrafColor,colorgap);
                        pool.execute(writeBodyThread);
                        tv_numberOfReceive.setText(msg.arg2 + "");
                        lFragment.drawNewVertical(colorList);
                        judge_MartOrNot = false;
                        break;
                    }
                case 1:
                    ibtn_wifi.setImageResource(R.drawable.wifigreen2);
                    ibtn_wifi.setEnabled(false);
                    ibtn_setting.setImageResource(R.drawable.settinggreen2);
                    ibtn_setting.setEnabled(true);
                    break;
                case 3:
//                    int sum_juegeextremum7 = msg.arg1;
//				int sum_extremum3=msg.arg2;
//				if (sum_extremum3>0) {
//					if (judge_haveOrNot==1) {
//						tv_metalOrNot.setText("金属");
//					}else if (judge_haveOrNot==0) {
//						tv_metalOrNot.setText("未检测到物体");
//					}
//				}else {
//					if (sum_juegeextremum5>5) {
//						tv_metalOrNot.setText("金属");
//					}else {
//						if (judge_haveOrNot==1) {
//							tv_metalOrNot.setText("非金属");
//						}else if(judge_haveOrNot==0){
//							tv_metalOrNot.setText("未检测到物体");
//						}
////					}
//				}
//				if (judge_haveOrNot==0) {
//					tv_metalOrNot.setText("未检测到物体");
//				}else if(judge_haveOrNot==1){
//					if (sum_juegeextremum7<0) {
//						tv_metalOrNot.setText("非金属");
//					}else {
//						tv_metalOrNot.setText("金属");
//					}
//				}
                    break;
                case 4:
                    //有物体
                    judge_haveOrNot = 1;
                    break;
                case 5:
                    //没有物体
                    judge_haveOrNot = 0;
                    break;
//                case 6:
//                    judge_MartOrNot = true;
//                    markNumber++;
//                    tv_markNumber.setText(markNumber + "");
//                    colorGap = (short[]) msg.obj;
//                    if (judge_MartOrNot) {
//                        colorGap[0] = 0xff;
//                        colorGap[1] = 0xff;
//                        colorGap[2] = 0xff;
//                        colorGap[3] = 0xff;
//                    }
////				judgeHaveThread.setColorGap(colorGap);
////				judgeMetalThread.setColorGap(colorGap);
//                    if (judge_MartOrNot) {
//                        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
//                            colorList[i] = 0x0000ff;
//                            colorGap[i] = (short) (colorGap[i] / 256);
//                        }
//                    }
////                    writeThread.setColorGap(colorGap);
////                    writeThread.setJudgeIfRepeat(false);
////                    writeThread.setJudgeNumber(3);
////                    writeThread.setJudgeIfRepeat(true);
//                    lFragment.drawNewVertical(colorList);
////				rFragment.drawRadarWave(colorGap,gainData);
//                    judge_MartOrNot = false;
//                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        checkPermission();
        lFragment = new LeftFragmentOfMainActivity();
        init();
        poolRaw =null;
        pool=null;

        ibtn_startAndSuspend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //第一次点击开启碎片
                clickOrNot++;

                if (judge_clickStopIbtn) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(R.drawable.warning);
                    builder.setTitle("警告");
                    builder.setMessage("是否覆盖原文件?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            File file = new File(tv_path.getText().toString());
                            File file_copy = new File(tv_path.getText().toString()+"-copy");
                            if (file.exists() && file.isFile()) {
                                if (file.delete()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(MainActivity.this, "覆盖成功，开始采集！", Toast.LENGTH_LONG).show();
                                }
                            }
                            if (IfSaveTheRadar==1&&file_copy.exists() && file_copy.isFile()) {
                                if (file_copy.delete()) {
                                    try {
                                        file_copy.createNewFile();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(MainActivity.this, "覆盖成功，开始采集！", Toast.LENGTH_LONG).show();
                                }
                            }

                            if (counter == 0) {
                                lFragment = new LeftFragmentOfMainActivity();
//								rFragment=new RightFragmentOfMainActivity();
                                //开启事物
                                FragmentTransaction fTransaction = fManager.beginTransaction();
                                //添加碎片
                                fTransaction.add(R.id.fl_containerLeft, lFragment);
//								fTransaction.add(R.id.fl_containerRight, rFragment);
//                                //碎片添加到回退栈
                                fTransaction.addToBackStack(null);
                                //提交
                                fTransaction.commit();
                                lFragment.drawLines(Integer.parseInt(mainPeremeterOrders.getString("timeWindow", "1")));
                            }

                            //偶数发送基数暂停
                            //第一次点击读取数据线程启动，同时发送命令给下位机开始采集数据
                            if (counter == 0) {
//                                writeThread.setJudgeNumber(1);
                                writeHeadThread = new WriteHeadThread(mrafColor);
                                writeHeadThread.setTimedelay(Short.parseShort(tv_delay.getText().toString()));
                                writeHeadThread.setSample_wnd(Integer.parseInt(tv_timeWindow.getText().toString()));
                                pool.execute(writeHeadThread);
                                if (IfSaveTheRadar==1){
                                    writeHeadThreadRaw = new WriteHeadThread(mrafRaw);
                                    writeHeadThreadRaw.setTimedelay(Short.parseShort(tv_delay.getText().toString()));
                                    writeHeadThreadRaw.setSample_wnd(Integer.parseInt(tv_timeWindow.getText().toString()));
                                    poolRaw.execute(writeHeadThreadRaw);
                                }
                                readThread.setNumberOfReceive(0);
                                //可以打标
                                btn_mark.setEnabled(true);
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        try {
                                            nOrders.send(startCollect, ds);
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                                ibtn_startAndSuspend.setImageResource(R.drawable.suspend2);
                            }


                            ibtn_setting.setImageResource(R.drawable.settinggray2);
                            ibtn_stop.setImageResource(R.drawable.stopred2);
                            ibtn_stop.setEnabled(true);
                            ibtn_setting.setEnabled(false);
                            counter++;
                            judge_clickStopIbtn = false;
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });

                    builder.show();

                } else {
                    if (counter == 0) {
                        File file = new File(tv_path.getText().toString());
                        File file_copy = new File(tv_path.getText().toString()+"-copy");
                        if (IfSaveTheRadar==1&&!file_copy.exists()) {
                            try {
                                file_copy.createNewFile();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (!file.exists()) {

                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                        lFragment = new LeftFragmentOfMainActivity();
//						rFragment=new RightFragmentOfMainActivity();
                        //开启事物
                        FragmentTransaction fTransaction = fManager.beginTransaction();
                        //添加碎片
                        fTransaction.add(R.id.fl_containerLeft, lFragment);
//						fTransaction.add(R.id.fl_containerRight, rFragment);
//                      //碎片添加到回退栈
                        fTransaction.addToBackStack(null);
                        //提交
                        fTransaction.commit();
                        lFragment.drawLines(Integer.parseInt(mainPeremeterOrders.getString("timeWindow", "1")));

                    }

                    //偶数发送基数暂停
                    //第一次点击读取数据线程启动，同时发送命令给下位机开始采集数据
                    if (counter == 0) {
                        writeHeadThread = new WriteHeadThread(mrafColor);
                        writeHeadThread.setTimedelay(Short.parseShort(tv_delay.getText().toString()));
                        writeHeadThread.setSample_wnd(Integer.parseInt(tv_timeWindow.getText().toString()));
                        pool.execute(writeHeadThread);
                        if (IfSaveTheRadar==1){
                            writeHeadThreadRaw = new WriteHeadThread(mrafRaw);
                            writeHeadThreadRaw.setTimedelay(Short.parseShort(tv_delay.getText().toString()));
                            writeHeadThreadRaw.setSample_wnd(Integer.parseInt(tv_timeWindow.getText().toString()));
                            poolRaw.execute(writeHeadThreadRaw);
                        }
                        readThread.setNumberOfReceive(0);
                        //可以打标
                        btn_mark.setEnabled(true);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                try {
                                    nOrders.send(startCollect, ds);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        ibtn_startAndSuspend.setImageResource(R.drawable.suspend2);
                    }
                    //除了第一次点击的偶数次，恢复读取数据，发送给下位机继续采样
                    if (counter % 2 == 0 && counter != 0) {
                        readThread.resume();
                        btn_mark.setEnabled(true);
//						writeThread.setJudgePause(false);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                try {
                                    nOrders.send(continueCollect, ds);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        ibtn_startAndSuspend.setImageResource(R.drawable.suspend2);

                    }
                    //奇数次暂停从下位机读取数据，同时给下位机发送命令暂停采样
                    if (counter % 2 == 1) {
                        btn_mark.setEnabled(false);
                        readThread.suspend();
//						writeThread.setJudgePause(true);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                try {
                                    nOrders.send(suspendCollect, ds);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        ibtn_startAndSuspend.setImageResource(R.drawable.startgreen2);
                    }

                    ibtn_setting.setImageResource(R.drawable.settinggray2);
                    ibtn_stop.setImageResource(R.drawable.stopred2);
                    ibtn_stop.setEnabled(true);
                    ibtn_setting.setEnabled(false);
                    counter++;

                }

            }
        });
    }

    private void init() {
        getSharedPreferences("battery", 0).edit().putInt("battery", -1).commit();
        Arrays.fill(colorGap, (short) 0);
        Arrays.fill(colorList, (short) 0);

        coeGain = 1;


        wifiAdmin = new WifiTool(MainActivity.this);

        // TODO Auto-generated method stub
        ibtn_startAndSuspend = (ImageButton) findViewById(R.id.ibtn_startAndSuspend);
        ibtn_wifi = (ImageButton) findViewById(R.id.ibtn_wifi);
        ibtn_setting = (ImageButton) findViewById(R.id.ibtn_setting);
        ibtn_stop = (ImageButton) findViewById(R.id.ibtn_stop);
        ibtn_upload = (ImageButton) findViewById(R.id.ibtn_upload);
        tv_numberOfReceive = (TextView) findViewById(R.id.tv_numberOfReceive);
        tv_path = (TextView) findViewById(R.id.tv_path);
        tv_timeWindow = (TextView) findViewById(R.id.tv_timeWindow);
        tv_frequency = (TextView) findViewById(R.id.tv_frequency);
        tv_delay = (TextView) findViewById(R.id.tv_delay);
        tv_markNumber = (TextView) findViewById(R.id.tv_markNumber);
//        tv_logo = (TextView) findViewById(R.id.tv_logo);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "PlayfairDisplay-Bold.ttf");
//        tv_logo.setTypeface(typeface);
        btn_mark = (Button) findViewById(R.id.btn_mark);
        ibtn_startAndSuspend.setImageResource(R.drawable.startgray2);
        ibtn_wifi.setImageResource(R.drawable.wifigray2);
        ibtn_wifi.setEnabled(true);
        ibtn_setting.setImageResource(R.drawable.settinggray2);
        ibtn_stop.setImageResource(R.drawable.stopgray2);
        ibtn_stop.setEnabled(false);
        ibtn_upload.setImageResource(R.drawable.upload);
        btn_mark.setEnabled(false);
        ibtn_setting.setEnabled(false);
//        ibtn_setting.setEnabled(true);
        myBatterView = findViewById(R.id.MyBatterView);
//        tv_numofSave = findViewById(R.id.tv_numofSave);
        met_radarName = findViewById(R.id.et_radarName);


        tempifbackremove = 0;
        tempifgain = 0;
        tempShare = 4;


        btn_wifi_open = findViewById(R.id.btn_openwifi);
        btn_wifi_connect = findViewById(R.id.btn_connect_wifi);
        btn_wifi_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiAdmin.openWifi();
                Toast.makeText(MainActivity.this, "成功打开wifi", Toast.LENGTH_SHORT).show();
            }
        });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                if (!intIP2StringIP(wifiAdmin.getCurrentWifiInfo().getIpAddress()).equals(STATICIP))Toast.makeText(MainActivity.this,"请手动修改静态ip地址",Toast.LENGTH_SHORT).show();
//                while (!intIP2StringIP(wifiAdmin.getCurrentWifiInfo().getIpAddress()).equals(STATICIP)) ;
//                handlerOfColour.sendEmptyMessage(1);
//                Toast.makeText(MainActivity.this, "成功连接！", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//        }).start();

        btn_wifi_connect.setOnClickListener((view) ->
                new Thread(() -> {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "等待连接！", Toast.LENGTH_SHORT).show();
//                    wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo(met_radarName.getText().toString(), met_radarName.getText().toString(), 3));
                    wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo("WGR4002102", "WGR4002102", 3));
                    while (!wifiAdmin.getCurrentWifiInfo().getSSID().equals(met_radarName.getText().toString())) ;
//                    if (!intIP2StringIP(wifiAdmin.getCurrentWifiInfo().getIpAddress()).equals(STATICIP)) {
//                        changeWifiConfiguration(false, "192.168.0.100", 24, "8.8.8.8", "192.168.0.1");
//                    }
                    Looper.loop();
                }).start()
        );


        //通过代码改变四个图片按钮的宽度，设置成和高度大小一样
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
//		tv_numberOfLose.setText(height+"");
//		tv_numberOfReceive.setText(width+"");
        LinearLayout.LayoutParams ibtn_settingLinearParams = (LinearLayout.LayoutParams) ibtn_setting.getLayoutParams();
        ibtn_settingLinearParams.width = height / 8;
        ibtn_setting.setLayoutParams(ibtn_settingLinearParams);
        LinearLayout.LayoutParams ibtn_startAndSuspendLinearParams = (LinearLayout.LayoutParams) ibtn_startAndSuspend.getLayoutParams();
        ibtn_startAndSuspendLinearParams.width = height / 8;
        ibtn_setting.setLayoutParams(ibtn_startAndSuspendLinearParams);
        LinearLayout.LayoutParams ibtn_wifiLinearParams = (LinearLayout.LayoutParams) ibtn_wifi.getLayoutParams();
        ibtn_wifiLinearParams.width = height / 8;
        ibtn_setting.setLayoutParams(ibtn_wifiLinearParams);
        LinearLayout.LayoutParams ibtn_stopLinearParams = (LinearLayout.LayoutParams) ibtn_stop.getLayoutParams();
        ibtn_stopLinearParams.width = height / 8;
        ibtn_setting.setLayoutParams(ibtn_stopLinearParams);
        LinearLayout.LayoutParams ibtn_uploadLinearParams = (LinearLayout.LayoutParams) ibtn_upload.getLayoutParams();
        ibtn_uploadLinearParams.width = height / 8;
        ibtn_setting.setLayoutParams(ibtn_uploadLinearParams);

        shareJudge = getSharedPreferences("judge", 0);
        shareXRaw = getSharedPreferences("xRaw", 0);

        try {
            try {
                ds = new DatagramSocket(8081);
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        nOrders = new NormalOrders();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    nOrders.send(stopCollect, ds);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

        readThread = new ReadThread(ds);
        readThread.setHandler(handlerOfColour);
        readThread.start();
//        writeThread = new WriteThread();
//        writeThread.setShowSaveNum(new ShowSaveNum() {
//            @Override
//            public void showsaveNum(int num) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_numofSave.setText(""+num);
//                    }
//                });
//
//            }
//        });
//        writeThread.start();

//		judgeHaveThread=new JudgeHaveThread(this,handlerOfColour);
//		judgeHaveThread.start();
//		judgeMetalThread=new JudgeMetalThread(handlerOfColour);
//		judgeMetalThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //打标按钮点击事件
    public void btnMark(View view) {
        judge_MartOrNot = true;
        markNumber++;
        tv_markNumber.setText(markNumber + "");
    }

    //停止按钮点击事件，下位机停止发送数据，碎片出栈,计数器counter恢复为0；
    public void stopIbtn(View view) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    nOrders.send(stopCollect, ds);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
        fManager.popBackStack();
        judge_clickStopIbtn = true;
        ibtn_stop.setImageResource(R.drawable.stopgray2);
        ibtn_stop.setEnabled(false);
        ibtn_startAndSuspend.setImageResource(R.drawable.startgreen2);
        ibtn_setting.setEnabled(true);
        ibtn_setting.setImageResource(R.drawable.settinggreen2);
        btn_mark.setEnabled(false);
        counter = 0;
        //打标数归零
        markNumber = 0;
        tv_markNumber.setText(markNumber + "");

        //判断是否点击开始暂停按钮置0
        clickOrNot = 0;
//        writeThread.setTrace_num(Integer.parseInt(tv_numberOfReceive.getText().toString()));
//        writeThread.setJudgeNumber(1);
        writeRearThread = new WriteRearThread(mrafColor);
        writeRearThread.setShowSaveNum(new ShowSaveFinish() {
            @Override
            public void showsavefinish() {
                Looper.prepare();
                Toast.makeText(MainActivity.this,"存储完成",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
        pool.execute(writeRearThread);
        if (IfSaveTheRadar==1){
            writeRearThreadRaw = new WriteRearThread(mrafRaw);
            writeRearThreadRaw.setShowSaveNum(new ShowSaveFinish() {
                @Override
                public void showsavefinish() {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,"原始文件存储完成",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            });
            poolRaw.execute(writeRearThreadRaw);
        }


//        pool.shutdown();

//		writeThread.stop();

//		judgeHaveThread.setJudge_NumberOfData(0);
        //如果在暂停的情况下点了停止，读线程会处于阻塞状态，再次点击开始接收不到数据，需要唤醒
        readThread.resume();
    }

    public void uploadIbtn(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setSingleChoiceItems(itemsForUpload, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Log.d(TAG, i + "00000000000000000000000onClick:");
                switch (i) {
                    case 0:
//                        Log.d(TAG, "onClick: 1111111111111111111111");
                        Intent intent = new Intent(MainActivity.this, MyDialogActivity.class);
                        intent.putExtra("type",0);
                        startActivity(intent);
                        break;
                    case 1:
//                        Log.d(TAG, "onClick: 22222222222222222222222222");
//                        Intent intent2 = new Intent(MainActivity.this, MyDialogActivity.class);
//                        intent2.putExtra("type",1);
//                        startActivity(intent2);
                        Toast.makeText(MainActivity.this,"该模式暂未开放！",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;


                }
            }
        });
        builder1.setCancelable(true);
        builder1.show();


    }

    //设置按钮的点击事件
    public void settingIbtn(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tempSetting = i;

                if (tempSetting == 1) {
                    if (clickOrNot > 0 || judge_clickStopIbtn) {
                        mainPeremeterOrdersEditor.putInt("serialNumberOfFile", ++serialNumberOfFile);
                        mainPeremeterOrdersEditor.commit();
//			Map<String,?> key_Value=(Map<String, ?>) mainPeremeterOrders.getAll();
//			Toast.makeText(this, key_Value.size()+"", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else if (tempSetting == 0) {
                    if (clickOrNot > 0 || judge_clickStopIbtn) {
                        mainPeremeterOrdersEditor.putInt("serialNumberOfFile", ++serialNumberOfFile);
                        mainPeremeterOrdersEditor.commit();
//			Map<String,?> key_Value=(Map<String, ?>) mainPeremeterOrders.getAll();
//			Toast.makeText(this, key_Value.size()+"", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(MainActivity.this, SettingActivityForNormal.class);
                    startActivity(intent);
                }
//                Toast.makeText(MainActivity.this, "select " + sequence, Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    //wifi的点击事件
    public void wifiIbtn(View view) {


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//		String ssid=wifiInfo.getSSID();

        //检测wifi是否已经连接网络，如果已经连接且ip地址对，提示已经连接，否则跳转到wifi连接界面
        if (wifiNetworkInfo.isConnected()) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
            if (ipAddress.equals(STATICIP)) {
                Toast.makeText(this, "连接成功！", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        while (true) {

                            if (readThread.getJudge_creatLink() != 0) {
                                readThread.setJudge_creatLink(0);
                                handlerOfColour.sendEmptyMessage(1);
                                break;
                            } else {
                                try {
                                    Thread.sleep(100);
                                    nOrders.send(creatLink, ds);

                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
            } else {
                Toast.makeText(this, "请检查连接的wifi是否正确", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                startActivity(intent);
            }
//			ibtn_wifi.setImageResource(R.drawable.wifigreen2);

//			Toast.makeText(MainActivity.this, "已经连接", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "请先连接wifi", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
            startActivity(intent);

        }

    }

    private String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    //当在settingActivity中退出来后，系统会自动调用这个函数，所以把需要改变能不能点击的事件放在这里.created by gaoweihang
    //updated by wuxiaoxuan,只是activity的绘制流程罢了，不应该当成关闭后才调用的，会导致很多错误。
    @Override
    protected void onResume() {

        // TODO Auto-generated method stub
//		Log.e("--------------", "onresume");
        shareGainData = getSharedPreferences("gainData", 0);
        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
            gainData[i] = shareGainData.getInt(i + "", 1);
        }
        shareBackgrdData = getSharedPreferences("backgrdData", 0);
        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
            backgrdData[i] = shareBackgrdData.getInt(i + "", 1);
        }

        shareSumColorData = getSharedPreferences("sumdata", 0);
        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
            sumcolorData[i] = (short) shareSumColorData.getInt(i + "", 1);
        }


        battery = (short) getSharedPreferences("battery", 0).getInt("battery", -1);
        if (battery == -1) {
            myBatterView.setPro(-1);
        } else {
            int mbtr_num = (battery / 100 - 20) * 10;
            myBatterView.setPro(mbtr_num);
        }


        shareCoeGain = getSharedPreferences("coeGain", 0);
        coeGain = shareCoeGain.getFloat("coeGain", 1);

        shareCoeBackgrd = getSharedPreferences("coeBackgrd", 0);

        sharemfiltermode = getSharedPreferences("mfiltermode", 0);
        tempShare = sharemfiltermode.getInt("mfiltermode", 4);
        shareifgain = getSharedPreferences("ifgain", 0);
        tempifgain = shareifgain.getInt("ifgain", 0);

        shareifbackremove = getSharedPreferences("ifbackremove", 0);
        tempifbackremove = shareifbackremove.getInt("ifbackremove", 0);

        sharemlowf = getSharedPreferences("mlowf", 0);
        numlowf = sharemlowf.getFloat("mlowf", 100);


        sharemhighf = getSharedPreferences("mhighf", 0);
        numhighf = sharemhighf.getFloat("mhighf", 1500);


        dataprocessMain = new Dataprocess();
        dataprocessMain.init();

        ibtn_startAndSuspend.setEnabled(shareJudge.getBoolean("judge", false));
        readThread.setHandler(handlerOfColour);

        if (shareJudge.getBoolean("judge", false)) {
            //再次设置后重置判断参数
            judge_clickStopIbtn = false;

            mainPeremeterOrders = getSharedPreferences("mainPeremeterOrders", 0);
            mainPeremeterOrdersEditor = mainPeremeterOrders.edit();
//			Toast.makeText(this, "path:"+mainPeremeterOrders.getString("path", "gwh/11")
//					+"\n timeWindow:"+mainPeremeterOrders.getString("timeWindow", "1")
//					+"\n frequncy:"+mainPeremeterOrders.getString("frequency", "1")
//					+"\n delay:"+mainPeremeterOrders.getString("delay", "100"), Toast.LENGTH_LONG).show();
            serialNumberOfFile = mainPeremeterOrders.getInt("serialNumberOfFile", 0);
            tv_path.setText(mainPeremeterOrders.getString("path", " ") + "/" + mainPeremeterOrders.getString("nameOfMainFile", "error") + mainPeremeterOrders.getInt("serialNumberOfFile", 0) + type);
            tv_timeWindow.setText(mainPeremeterOrders.getString("timeWindow", "1"));
            tv_frequency.setText(mainPeremeterOrders.getString("frequency", "1"));
            tv_delay.setText(mainPeremeterOrders.getString("delay", "100"));
            IfSaveTheRadar = mainPeremeterOrders.getInt("saveRadar",1);
//            writeThread.setAnoStart(IfSaveTheRadar);
            ibtn_startAndSuspend.setImageResource(R.drawable.startgreen2);
            readThread.setNumberOfReceive(0);

        }

        if (tv_path.getText().length()>5){
            try {
                mrafColor = new RandomAccessFile(tv_path.getText().toString(), "rw");
                pool = Executors.newFixedThreadPool(1);//建立一个无界队列的线程池
                if (IfSaveTheRadar==1){
                    mrafRaw = new RandomAccessFile(tv_path.getText().toString()+"-copy","rw");
                    poolRaw = Executors.newFixedThreadPool(1);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }


    //双击返回键退出界面，清除所有记录，并且为了防止在采集数据的时候退出硬件依然在采数据，退出的时候需要发送停止采集命令
    //如果没有点击开始按钮，点击虚拟导航栏返回键，提示双击退出；如果点击开始采集按钮后点击返回键，提示是否保存数据，确定保存数据
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再次点击返回键退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();

            } else {
                //发送停止采集命令
//	            	Toast.makeText(this,"else",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            nOrders.send(stopCollect, ds);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();

//                SharedPreferences.Editor shareJudgeEditor = shareJudge.edit();
//                SharedPreferences.Editor shareXRawEditor = shareXRaw.edit();
//                SharedPreferences.Editor shareGainDataEditor = shareGainData.edit();
//                shareJudgeEditor.clear();
//                shareJudgeEditor.commit();
//                shareXRawEditor.clear();
//                shareXRawEditor.commit();
//                shareGainDataEditor.clear();
//                shareGainDataEditor.commit();
//                mainPeremeterOrdersEditor.clear();
//                mainPeremeterOrdersEditor.commit();
                //线程停止
                if (clickOrNot > 0) {
                    //没点停止按钮直接退出的情况时，写线程停止
//                    writeThread.setTrace_num(Integer.parseInt(tv_numberOfReceive.getText().toString()));
//                    writeThread.setJudgeNumber(1);
                    pool.execute(new WriteRearThread(mrafColor));
                    if (IfSaveTheRadar==1){
                        poolRaw.execute(new WriteRearThread(mrafRaw));
                    }
//                    writeThread.stop();

                }
                readThread.setJudge(false);


                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        }
    }


    void changeWifiConfiguration(boolean dhcp, String ip, int prefix, String dns1, String gateway) {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wm.isWifiEnabled()) {
            // wifi is disabled
            return;
        }
        // get the current wifi configuration
        WifiConfiguration wifiConf = null;
        WifiInfo connectionInfo = wm.getConnectionInfo();
        List<WifiConfiguration> configuredNetworks = wm.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration conf : configuredNetworks) {
                if (conf.networkId == connectionInfo.getNetworkId()) {
                    wifiConf = conf;
                    break;
                }
            }
        }

        if (wifiConf == null) {

            // wifi is not connected
            return;
        }

        try {
            Class<?> ipAssignment = wifiConf.getClass().getMethod("getIpAssignment").invoke(wifiConf).getClass();
            Object staticConf = wifiConf.getClass().getMethod("getStaticIpConfiguration").invoke(wifiConf);
            if (dhcp) {
                wifiConf.getClass().getMethod("setIpAssignment", ipAssignment).invoke(wifiConf, Enum.valueOf((Class<Enum>) ipAssignment, "DHCP"));
                if (staticConf != null) {
                    staticConf.getClass().getMethod("clear").invoke(staticConf);
                }
            } else {

                wifiConf.getClass().getMethod("setIpAssignment", ipAssignment).invoke(wifiConf, Enum.valueOf((Class<Enum>) ipAssignment, "STATIC"));
                if (staticConf == null) {
                    Class<?> staticConfigClass = Class.forName("android.net.StaticIpConfiguration");
                    staticConf = staticConfigClass.newInstance();
                }
                // STATIC IP AND MASK PREFIX
                Constructor<?> laConstructor = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    laConstructor = LinkAddress.class.getConstructor(InetAddress.class, int.class);
                }
                LinkAddress linkAddress = (LinkAddress) laConstructor.newInstance(
                        InetAddress.getByName(ip),
                        prefix);
                staticConf.getClass().getField("ipAddress").set(staticConf, linkAddress);
                // GATEWAY
                staticConf.getClass().getField("gateway").set(staticConf, InetAddress.getByName(gateway));
                // DNS
                List<InetAddress> dnsServers = (List<InetAddress>) staticConf.getClass().getField("dnsServers").get(staticConf);
                dnsServers.clear();
                dnsServers.add(InetAddress.getByName(dns1));
                dnsServers.add(InetAddress.getByName("8.8.8.8")); // Google DNS as DNS2 for safety
                // apply the new static configuration
                wifiConf.getClass().getMethod("setStaticIpConfiguration", staticConf.getClass()).invoke(wifiConf, staticConf);
            }
            // apply the configuration change

            boolean result = (wm.updateNetwork(wifiConf) != -1); //apply the setting
            if (result) result = wm.saveConfiguration(); //Save it
            if (result) wm.reassociate(); // reconnect with the new static IP
            int netId = wm.addNetwork(wifiConf);
//            Toast.makeText(TestWifi.this,netId+"",Toast.LENGTH_SHORT).show();
            wm.disableNetwork(netId);
            wm.enableNetwork(netId, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkPermission() {
        int targetSdkVersion = 0;
        String[] PermissionString = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WAKE_LOCK
        };
        try {
            final PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;//获取应用的Target版本
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            Log.e("err", "检查权限_err0");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Build.VERSION.SDK_INT是获取当前手机版本 Build.VERSION_CODES.M为6.0系统
            //如果系统>=6.0
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                //第 1 步: 检查是否有相应的权限
                boolean isAllGranted = checkPermissionAllGranted(PermissionString);
                if (isAllGranted) {
                    //Log.e("err","所有权限已经授权！");
                    return;
                }
                // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
                ActivityCompat.requestPermissions(this,
                        PermissionString, 1);
            }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false;
            }
        }
        return true;
    }

    //申请权限结果返回处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 所有的权限都授予了
                Log.e("err", "权限都授权了");
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //容易判断错
                //MyDialog("提示", "某些权限未开启,请手动开启", 1) ;
            }
        }
    }

    public static byte[] shortToByte(short number) {
        short temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Short((short) (temp& 0xff)).byteValue();
//                    new Integer(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = (short) (temp >> 8); // 向右移8位
        }
        return b;
    }


}
