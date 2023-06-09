package com.example.backRadar;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import com.example.fragments.RightFragmentOfSettingActivity;
import com.example.helper.SaveData;
import com.example.ladarmonitor.NoneActivity;
import com.example.ladarmonitor.R;
import com.example.orders.MainPeremeterOrders;
import com.example.orders.NormalOrders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingActivityForNormal extends Activity{
    private static final String TAG = "SettingActivityLog";
    //    public LeftFragmentOfSettingActivity leftFragmentOfSettingActivity;
    public RightFragmentOfSettingActivity rightFragmentOfSettingActivity;
    //    public MiddleBackFragmentOfSettingActivity middleBackFragmentOfSettingActivity;
    public static final int Const_NumberOfVerticalDatas=512;
    //    public static final int Const_NumberOfXRawDatas=17;
//    public static final int Const_NumberOfBRawDatas=17;
    public short colorGap[]=new short [Const_NumberOfVerticalDatas];
    public short sumcolorGap[]=new short [Const_NumberOfVerticalDatas];

    public static final int Const_NEW_NUMBEROFDATA = 513;
    private short reciveData[] = new short[Const_NEW_NUMBEROFDATA];
    private short battery;

//    public int gainData[]=new int [Const_NumberOfVerticalDatas];
//    public int backgrdData[]=new int[Const_NumberOfVerticalDatas];
//    public float xRaw[]=new float [17];
//    public float bRaw[]=new float [17];
//    public float coeGain=1;

    private static int countNumRadar = 0;
    //自动设置按钮暂停判断计数器
    boolean judge_automaticSetting=false;
    //水平增益判断
    int counteOfHorizontalGain=0;
    //水平背景判断
    int counteOfHorizontalBackgrd=0;
//    private static int tempIffliter=4;
//    private static int tempGain=0;
//    private static int tempBackgrd=0;

//    private static float lowf= (float) 0.0;
//    private static float highf= (float) 0.0;


//    private static Dataprocess dataprocess=null;


    //文件类型
    public static final String type=".raw";
    //默认路径
    public static final String defaultPath="/storage/emulated/0/datas";




    //存储路径,点击确定按钮后需要把这个地址传给MainActivity，采集数据的同时把数据保存在这个路径下
    private String path=null;
    //文件名，点击确定按钮后需要把这个地址传给MainActivity，采集数据的同时把数据保存在这个文件名中
    private String fileName=".raw";
    private Button btn_creatPath=null;
    private TextView tv_settingPath=null;
    private EditText et_nameOfMainFile=null;
    private EditText et_serialNumberOfFile=null;
    private TextView tv_storeFile=null;
    private TextView tv_none=null;
    //    private Spinner sp_frequency=null;
    private int frequency=70;

    //	private Spinner sp_oneWay=null;
//    private EditText et_timeWindow=null;
//    private EditText et_delay=null;
//    private List<Integer> listOfFrequency=null;
    //	private List<String> listOfTheWayOfPulse=null;
//    private ArrayAdapter<Integer> adapterOfFrequency=null;
    //	private ArrayAdapter<String> adapterOfTheWayOfPulse=null;
//    private RadioButton rd_timing=null;
//    private RadioButton rd_wheel=null;
//    private RadioButton rd_oneWay=null;
//    private RadioButton rd_twoWays=null;

//    private RadioButton rb_lowpassfilter= null;
//    private RadioButton rb_highpassfilter= null;
//    private RadioButton rb_bandpassfilter = null;
//    private RadioButton rb_fillingfilter=null;



//    private EditText et_numberOfPulse=null;
//    private EditText et_singlePulse=null;
//    private EditText et_singleDistance=null;
//    private EditText et_samplingInterval=null;


//    private EditText et_horiGain_coe = null;

//    private EditText et_m_high_f=null;
//    private EditText et_m_low_f=null;

    //    private CheckBox cb_gain=null;
//    private CheckBox cb_backremove=null;
//    private CheckBox cb_filter=null;
    //startActivityForResult
    public static final int FILE_RESULT_CODE = 1;

//    private static SharedPreferences sharemfiltermode;
//    private static SharedPreferences.Editor sharemfiltermodeEd;
//    private static SharedPreferences shareifgain;
//    private static SharedPreferences.Editor shareifgainEd;
//    private static SharedPreferences shareifbackremove;
//    private static SharedPreferences.Editor shareifbackremoveEd;

//    private static SharedPreferences sharemhighf;
//    private static SharedPreferences.Editor sharemhighfEd;
//    private static SharedPreferences sharemlowf;
//    private static SharedPreferences.Editor sharemlowfEd;

    SharedPreferences mainPeremeterOrders=null;
    private SharedPreferences shareSettinglog = null;
    //当前系统时间的年月日时分
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private Calendar calendar=null;

    //判断文件是否存在
    File file=null;

    //存储主参数
    SharedPreferences.Editor mainPeremeterOrdersEditor=null;

    static SaveData saveData=null;

    //发送的命令
    NormalOrders nOrders=null;
    MainPeremeterOrders pOrders=null;

    byte startSetting []={(byte) 0xaa,(byte) 0xaa,(byte) 0x02,0x00};
    byte endSetting []={(byte) 0xaa,(byte) 0xaa,(byte) 0x04,0x00};
    byte startCollect []={(byte) 0xaa,(byte) 0xaa,(byte) 0x08,0x00};
    byte suspendCollect []={(byte) 0xaa,(byte) 0xaa,(byte) 0x10,0x00};
    byte continueCollect []={(byte) 0xaa,(byte) 0xaa,(byte) 0x20,0x00};
    byte stopCollect []={(byte) 0xaa,(byte) 0xaa,(byte) 0x40,0x00};

    //接收readThread接收回来的数据，更新ui
    @SuppressLint("HandlerLeak")
    private Handler handlerOfColour=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    countNumRadar++;

                    reciveData = (short[]) msg.obj;
                    for (int i =0;i<Const_NumberOfVerticalDatas;i++){
                        colorGap[i] = reciveData[i];
                    }
//                    colorGap=(short[]) msg.obj;


                    if (countNumRadar==2){
//                        System.arraycopy(colorGap,0,sumcolorGap,0,Const_NumberOfVerticalDatas);
                        battery = reciveData[512];
                        getSharedPreferences("battery",0).edit().putInt("battery",battery).commit();

                    }

//                    if (countNumRadar==2){
//                        System.arraycopy(colorGap,0,sumcolorGap,0,Const_NumberOfVerticalDatas);
//                    }
//                    if(tempBackgrd==1){
//                        short[] newnoise =  new short[Const_NumberOfVerticalDatas];
//                        for (int i =0;i<Const_NumberOfVerticalDatas;i++){
//                            newnoise[i] = (short)backgrdData[i];
//                        }
//                        float[] noise=dataprocess.calm_gainnoiseb(newnoise);
//                        colorGap=dataprocess.backgroundRemoveProcess(Const_NumberOfVerticalDatas,colorGap,noise,colorGap);
//                    }

//                    if (tempIffliter!=4) {
//                        dataprocess.setM_iffilter(true);
//                        dataprocess.m_filterP.setM_mode(tempIffliter);
//                        colorGap=dataprocess.dodataprocess(colorGap);
//                    }

//                    if (tempGain==1){
////						Log.d(TAG, gainData[255]+"");
//                        colorGap=dataprocess.gainProcess(colorGap,gainData,coeGain,Const_NumberOfVerticalDatas);
//                    }

                    for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                        colorGap[i]=(short) (colorGap[i]/256);
                    }


//				Log.d(TAG, dataprocess.m_filterP.getM_mode()+"");

                    rightFragmentOfSettingActivity.drawRadarWave(colorGap);
                    break;
                case 1:
//                    if (Integer.parseInt(et_delay.getText().toString())>=10000) {
//                        et_delay.setText("100");
//                    }else {
//                        et_delay.setText(Integer.parseInt(et_delay.getText().toString())+100+"");
//                    }
//                    pOrders.setDelay_time_DELAY(Short.parseShort(et_delay.getText().toString()));
//                    new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            pOrders.send();
//                        }
//                    }).start();
//                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_for_normal);
        try {
            init();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

//        rb_fillingfilter.setOnCheckedChangeListener(radiochecklistener);
//        rb_lowpassfilter.setOnCheckedChangeListener(radiochecklistener);
//        rb_highpassfilter.setOnCheckedChangeListener(radiochecklistener);
//        rb_bandpassfilter.setOnCheckedChangeListener(radiochecklistener);
//
//        cb_gain.setOnCheckedChangeListener(checkboxclickListener);
//        cb_filter.setOnCheckedChangeListener(checkboxclickListener);
//        cb_backremove.setOnCheckedChangeListener(checkboxclickListener);
//        rd_timing.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                rd_oneWay.setEnabled(false);
//                rd_twoWays.setEnabled(false);
////				sp_oneWay.setSelection(0);
////				sp_oneWay.setEnabled(false);
//                rd_oneWay.setChecked(false);
//                rd_twoWays.setChecked(false);
//                //脉冲参数不能点击
//                et_numberOfPulse.setEnabled(false);
//                et_singleDistance.setEnabled(false);
//                et_singlePulse.setEnabled(false);
//                pOrders.setTriggerMode(0);
//
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        pOrders.send();
//                    }
//                }).start();
//            }
//        });
//
//        rd_wheel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                rd_oneWay.setEnabled(true);
//                rd_twoWays.setEnabled(true);
//                pOrders.setTriggerMode(1);
//////				sp_oneWay.setSelection(0);
////				//脉冲参数可以点击
//                et_numberOfPulse.setEnabled(true);
//                et_singleDistance.setEnabled(true);
//                et_singlePulse.setEnabled(true);
//
//                //默认选中单向触发
//                rd_oneWay.setChecked(true);
//                pOrders.setTriggerDirection(0);
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        pOrders.send();
//                    }
//                }).start();
//            }
//        });
//
//        rd_oneWay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
////				sp_oneWay.setEnabled(true);
//                pOrders.setTriggerDirection(0);
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        pOrders.send();
//                    }
//                }).start();
//            }
//        });
//
//        rd_twoWays.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
////				sp_oneWay.setEnabled(false);
//                pOrders.setTriggerDirection(1);
////				sp_oneWay.setSelection(0);
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        pOrders.send();
//                    }
//                }).start();
//            }
//        });




//		et_nameOfMainFile.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (et_nameOfMainFile.getText().toString().equals("")) {
//					Toast.makeText(SettingActivity.this, "主文件名不能为空", Toast.LENGTH_SHORT).show();
//					et_nameOfMainFile.setText("file");
//				}
//				tv_storeFile.setText(et_nameOfMainFile.getText().toString()+et_serialNumberOfFile.getText().toString()+type);
//				if (tv_settingPath.getText().toString().equals("")) {
//
//				}else {
//					path=tv_settingPath.getText().toString()+"/"+tv_storeFile.getText().toString();
//					Toast.makeText(SettingActivity.this, "path:"+path, Toast.LENGTH_LONG).show();
//				}
//				return false;
//			}
//		});

        //文件名改变监控
        et_nameOfMainFile.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId==EditorInfo.IME_ACTION_DONE) {
                    if (et_nameOfMainFile.getText().toString().equals("")) {
                        Toast.makeText(SettingActivityForNormal.this, "主文件名不能为空", Toast.LENGTH_SHORT).show();
                        et_nameOfMainFile.setText("file");
                    }
                    tv_storeFile.setText(et_nameOfMainFile.getText().toString()+et_serialNumberOfFile.getText().toString()+type);

                }
                tv_none.setFocusable(true);
                tv_none.setFocusableInTouchMode(true);
                tv_none.requestFocus();
                return false;
            }
        });
        et_nameOfMainFile.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                }else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivityForNormal.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
                    startActivity(intent);
                }
            }
        });



//		et_serialNumberOfFile.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (et_serialNumberOfFile.getText().toString().equals("")) {
//					Toast.makeText(SettingActivity.this, "文件序号不能为空", Toast.LENGTH_SHORT).show();
//					et_serialNumberOfFile.setText("1");
//				}
//				tv_storeFile.setText(et_nameOfMainFile.getText().toString()+et_serialNumberOfFile.getText().toString()+type);
//				if (tv_settingPath.getText().toString().equals("")) {
//
//				}else {
//					path=tv_settingPath.getText().toString()+"/"+tv_storeFile.getText().toString();
//					Toast.makeText(SettingActivity.this, "path:"+path, Toast.LENGTH_LONG).show();
//				}
//				return false;
//			}
//		});
        //文件号改变监控
        et_serialNumberOfFile.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId==EditorInfo.IME_ACTION_DONE) {
                    if (et_serialNumberOfFile.getText().toString().equals("")) {
                        Toast.makeText(SettingActivityForNormal.this, "文件序号不能为空", Toast.LENGTH_SHORT).show();
                        et_serialNumberOfFile.setText("1");
                    }
                    tv_storeFile.setText(et_nameOfMainFile.getText().toString()+et_serialNumberOfFile.getText().toString()+type);

                    tv_none.setFocusable(true);
                    tv_none.setFocusableInTouchMode(true);
                    tv_none.requestFocus();
                }
                return false;
            }
        });
        et_serialNumberOfFile.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                }else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivityForNormal.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
                    startActivity(intent);
                }
            }
        });



        //采样脉冲数改变监听
//        et_numberOfPulse.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                // TODO Auto-generated method stub
//
//                int numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//                int singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//                float singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//                if (actionId==EditorInfo.IME_ACTION_DONE) {
//                    if (et_numberOfPulse.getText().toString().equals("")) {
//                        Toast.makeText(SettingActivityForNormal.this, "采样脉冲数不能为空", Toast.LENGTH_SHORT).show();
//                        et_numberOfPulse.setText("1");
//                    }else if (Integer.parseInt(et_numberOfPulse.getText().toString())<=0) {
//                        Toast.makeText(SettingActivityForNormal.this, "采样脉冲数必须为不等于0的非负数", Toast.LENGTH_SHORT).show();
//                        et_numberOfPulse.setText("1");
//                    }
//
//                    tv_none.setFocusable(true);
//                    tv_none.setFocusableInTouchMode(true);
//                    tv_none.requestFocus();
//
//                    numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//                    et_samplingInterval.setText(String.valueOf(singleDistance*numberOfPulse/singlePulse));
//                    pOrders.setNumberOfPulse(Integer.parseInt(et_numberOfPulse.getText().toString()));
//                }
//                return false;
//            }
//        });
//
//        et_numberOfPulse.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus) {
//
//                }else {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivityForNormal.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

//		et_numberOfPulse.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				int numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//				int singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//				float singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//				if (et_numberOfPulse.getText().toString().equals("")) {
//					Toast.makeText(SettingActivity.this, "采样脉冲数不能为空", Toast.LENGTH_SHORT).show();
//					et_numberOfPulse.setText("20");
//				}if (Integer.parseInt(et_numberOfPulse.getText().toString())<=0) {
//					Toast.makeText(SettingActivity.this, "采样脉冲数不能小于等于0", Toast.LENGTH_SHORT).show();
//					et_numberOfPulse.setText("20");
//				}
//
//					numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//					et_samplingInterval.setText(String.valueOf(singleDistance*numberOfPulse/singlePulse));
//					pOrders.setNumberOfPulse(Integer.parseInt(et_numberOfPulse.getText().toString()));
//
////				mainPeremeterOrdersEditor.putInt("numberOfPulse",numberOfPulse );
//
//				return false;
//			}
//		});

//        et_singleDistance.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                // TODO Auto-generated method stub
//
//                int numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//                int singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//                float singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//                if (actionId==EditorInfo.IME_ACTION_DONE) {
//                    if (Integer.parseInt(et_singleDistance.getText().toString())<=0) {
//                        Toast.makeText(SettingActivityForNormal.this, "单圈距离必须为不等于0的非负数", Toast.LENGTH_SHORT).show();
//                        et_singleDistance.setText("0.30");
//                    }else if (et_singleDistance.getText().toString().equals("")) {
//                        Toast.makeText(SettingActivityForNormal.this, "单圈距离不能为空", Toast.LENGTH_SHORT).show();
//                        et_singleDistance.setText("0.30");
//
//                    }
//
//                    tv_none.setFocusable(true);
//                    tv_none.setFocusableInTouchMode(true);
//                    tv_none.requestFocus();
//
//                    singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//                    et_samplingInterval.setText(String.valueOf(singleDistance*numberOfPulse/singlePulse));
//                }
//                return false;
//            }
//        });
//
//        et_singleDistance.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus) {
//
//                }else {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivityForNormal.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

//		et_singleDistance.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				int numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//				int singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//				float singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//				if (Integer.parseInt(et_singleDistance.getText().toString())==0) {
//					Toast.makeText(SettingActivity.this, "单圈距离不能为0", Toast.LENGTH_SHORT).show();
//					et_singleDistance.setText("0.52");
//				}else if (et_singleDistance.getText().toString().equals("")) {
//					Toast.makeText(SettingActivity.this, "单圈距离不能为空", Toast.LENGTH_SHORT).show();
//					et_singleDistance.setText("0.52");
//
//				}
//					singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//					et_samplingInterval.setText(String.valueOf(singleDistance*numberOfPulse/singlePulse));
//
//				return false;
//			}
//		});

//        et_singlePulse.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                // TODO Auto-generated method stub
//                int numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//                int singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//                float singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//                if (actionId==EditorInfo.IME_ACTION_DONE) {
//                    if (Integer.parseInt(et_singlePulse.getText().toString())<=0) {
//                        Toast.makeText(SettingActivityForNormal.this, "单圈脉冲必须为不等于0的非负数", Toast.LENGTH_SHORT).show();
//                        et_singlePulse.setText("100");
//                    }else if (et_singlePulse.getText().toString().equals("")) {
//                        Toast.makeText(SettingActivityForNormal.this, "单圈脉冲不能为空", Toast.LENGTH_SHORT).show();
//                        et_singlePulse.setText("100");
//                    }
//
//                    tv_none.setFocusable(true);
//                    tv_none.setFocusableInTouchMode(true);
//                    tv_none.requestFocus();
//
//                    singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//                    et_samplingInterval.setText(String.valueOf(singleDistance*numberOfPulse/singlePulse));
//                }
//                return false;
//            }
//        });
//
//        et_singlePulse.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus) {
//
//                }else {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivityForNormal.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

//		et_singlePulse.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				int numberOfPulse=Integer.parseInt(et_numberOfPulse.getText().toString());
//				int singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//				float singleDistance=Float.parseFloat(et_singleDistance.getText().toString());
//				if (Integer.parseInt(et_singlePulse.getText().toString())==0) {
//					Toast.makeText(SettingActivity.this, "单圈脉冲不能为0", Toast.LENGTH_SHORT).show();
//					et_singlePulse.setText("100");
//				}else if (et_singlePulse.getText().toString().equals("")) {
//					Toast.makeText(SettingActivity.this, "单圈脉冲不能为空", Toast.LENGTH_SHORT).show();
//					et_singlePulse.setText("100");
//				}
//					singlePulse=Integer.parseInt(et_singlePulse.getText().toString());
//					et_samplingInterval.setText(String.valueOf(singleDistance*numberOfPulse/singlePulse));
//
//
//				return false;
//			}
//		});

        //单向触发方式点击事件
//		sp_oneWay.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				switch (position) {
//				case 1:
//					pOrders.setValiddirection((byte)0);
//					break;
//				case 2:
//					pOrders.setValiddirection((byte)1);
//					break;
//				default:
//					break;
//				}
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						pOrders.send();
//					}
//				}).start();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//
//			}
//		});

        //频率点击事件
//        sp_frequency.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                // TODO Auto-generated method stub
//                frequency=Integer.parseInt(adapterOfFrequency.getItem(position).toString());
//                mainPeremeterOrdersEditor.putInt("index_frequency", position);
//                if (frequency==10) {
//                    pOrders.setFrequency((short) 0x0a00);
//                }else if (frequency==20) {
//                    pOrders.setFrequency((short) 0x1400);
//                }else if (frequency==30) {
//                    pOrders.setFrequency((short) 0x1e00);
//                }else if (frequency==40) {
//                    pOrders.setFrequency((short) 0x2800);
//                }else if (frequency==50) {
//                    pOrders.setFrequency((short) 0x3200);
//                }
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        try {
//                            pOrders.send();
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                    }
//                }).start();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//        et_timeWindow.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                // TODO Auto-generated method stub
//                if (actionId==EditorInfo.IME_ACTION_DONE) {
//                    if (et_timeWindow.getText().toString().equals("")) {
//                        Toast.makeText(SettingActivityForNormal.this, "时间窗不能为空", Toast.LENGTH_SHORT).show();
//                        et_timeWindow.setText("70");
//                    }else if (Integer.parseInt(et_timeWindow.getText().toString())<=0) {
//                        Toast.makeText(SettingActivityForNormal.this, "时间窗必须为不等于0的非负数", Toast.LENGTH_SHORT).show();
//                        et_timeWindow.setText("70");
//                    }
//
//
//                    tv_none.setFocusable(true);
//                    tv_none.setFocusableInTouchMode(true);
//                    tv_none.requestFocus();
//
//
//                    pOrders.setM_time_wnd(Short.parseShort(et_timeWindow.getText().toString()));
//                    new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//
//                            try {
//                                pOrders.send();
//                            } catch (Exception e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//                }
//
//                return false;
//            }
//        });
//        et_timeWindow.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus) {
//
//                }else {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivityForNormal.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

//		et_timeWindow.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (et_timeWindow.getText().toString().equals("")) {
//						Toast.makeText(SettingActivity.this, "时间窗不能为空", Toast.LENGTH_SHORT).show();
//						et_timeWindow.setText("70");
//					}else if (Integer.parseInt(et_timeWindow.getText().toString())==0) {
//						Toast.makeText(SettingActivity.this, "时间窗不能为0", Toast.LENGTH_SHORT).show();
//						et_timeWindow.setText("70");
//					}
//
//
//				if (event.getAction()==event.ACTION_UP){
//
//						pOrders.setM_time_wnd(Short.parseShort(et_timeWindow.getText().toString()));
//						new Thread(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//
//								try {
//									pOrders.send();
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}).start();
//					}
//
//				return false;
//
//			}
//		});

//		et_delay.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (et_delay.getText().toString().equals("")) {
//					Toast.makeText(SettingActivity.this, "延时不能为空", Toast.LENGTH_SHORT).show();
//					et_delay.setText("2000");
//				}else if (Integer.parseInt(et_delay.getText().toString())<0) {
//					Toast.makeText(SettingActivity.this, "延时必须为非负数", Toast.LENGTH_SHORT).show();
//					et_delay.setText("2000");
//				}
//
//				if (event.getAction()==event.ACTION_UP) {
//
//					pOrders.setDelay_time_DELAY(Short.parseShort(et_delay.getText().toString()));
//					new Thread(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							try {
//								pOrders.send();
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						}
//					}).start();
//				}
//
//				return false;
//			}
//		});
//        et_delay.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                // TODO Auto-generated method stub
//                if (actionId==EditorInfo.IME_ACTION_DONE) {
//                    if (et_delay.getText().toString().equals("")) {
//                        Toast.makeText(SettingActivityForNormal.this, "延时不能为空", Toast.LENGTH_SHORT).show();
//                        et_delay.setText("2000");
//                    }else if (Integer.parseInt(et_delay.getText().toString())<=0) {
//                        Toast.makeText(SettingActivityForNormal.this, "延时必须为不等于0的非负数", Toast.LENGTH_SHORT).show();
//                        et_delay.setText("2000");
//                    }
//
//                    tv_none.setFocusable(true);
//                    tv_none.setFocusableInTouchMode(true);
//                    tv_none.requestFocus();
//
//                    pOrders.setDelay_time_DELAY(Short.parseShort(et_delay.getText().toString()));
//                    new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            try {
//                                pOrders.send();
//                            } catch (Exception e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }).start();
//                }
//                return false;
//            }
//        });
//        et_delay.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus) {
//
//                }else {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivity.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    Intent intent = new Intent(SettingActivityForNormal.this,NoneActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

        //接口回调获得增益数组
//        leftFragmentOfSettingActivity.setCallBackGainData(new CallBackGainData() {
//
//            @Override
//            public void gainData(int[] gaindata,float [] xRaw) {
//
//                // TODO Auto-generated method stub
//                System.arraycopy(gaindata, 0, gainData, 0, gaindata.length);
//                System.arraycopy(xRaw, 0, SettingActivityForNormal.this.xRaw, 0, xRaw.length);
//                tempGain=1;
//                coeGain=(Float.parseFloat(et_horiGain_coe.getText().toString())) ;
//            }
//        });
//        middleBackFragmentOfSettingActivity.setCallBackGainData(new CallBackBackgrdData() {
//            @Override
//            public void backgrdData(int[] backgrddata, float[] bRaw) {
//                System.arraycopy(backgrddata, 0, backgrdData, 0, backgrdData.length);
//                tempBackgrd=1;
//                System.arraycopy(bRaw, 0, SettingActivityForNormal.this.bRaw, 0, bRaw.length);
//            }
//        });

    }
    //    public CompoundButton.OnCheckedChangeListener radiochecklistener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//            switch (compoundButton.getId()){
//                case R.id.rb_lowpassfilter:
//                    if (b) {
//                        tempIffliter=0;
//                        cb_filter.setChecked(true);
//                    }
//                    break;
//                case R.id.rb_highpassfilter:
//                    if (b) {
//                        tempIffliter=1;
//                        cb_filter.setChecked(true);
//                    }
//                    break;
//                case R.id.rb_bandpassfilter:
//                    if (b) {
//                        tempIffliter=2;
//                        cb_filter.setChecked(true);
//                    }
//                    break;
//                case R.id.rb_fillingfilter:
//                    if (b) {
//                        tempIffliter=3;
//                        cb_filter.setChecked(true);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
    private void init() throws Exception {
        // TODO Auto-generated method stub
        MainActivity.readThread.setHandler(handlerOfColour);
        pOrders=new MainPeremeterOrders(MainActivity.ds);
//        dataprocess=new Dataprocess();
//        dataprocess.init();
        //刚进入SettingActivity发送开始参数设置命令的进程
        Arrays.fill(sumcolorGap, (short) 0);
        rightFragmentOfSettingActivity=(RightFragmentOfSettingActivity) getFragmentManager().findFragmentById(R.id.fr_settingActivity_right);
//        leftFragmentOfSettingActivity=(LeftFragmentOfSettingActivity) getFragmentManager().findFragmentById(R.id.fr_settingActivity_left);
//        middleBackFragmentOfSettingActivity=(MiddleBackFragmentOfSettingActivity) getFragmentManager().findFragmentById(R.id.fr_settingActivity_backgroundCenter);
        btn_creatPath=(Button) findViewById(R.id.btn_creatPath);
        tv_settingPath=(TextView) findViewById(R.id.tv_settingPath);
        et_nameOfMainFile=(EditText) findViewById(R.id.et_nameOfMainFile);
        et_serialNumberOfFile=(EditText) findViewById(R.id.et_serialNumberOfFile);
        tv_storeFile=(TextView) findViewById(R.id.tv_storeFile);
//        sp_frequency=(Spinner) findViewById(R.id.sp_frequency);
        tv_none=(TextView) findViewById(R.id.tv_none);
//		sp_oneWay=(Spinner) findViewById(R.id.sp_oneWay);
//        et_timeWindow=(EditText) findViewById(R.id.et_timeWindow);
//        et_delay=(EditText) findViewById(R.id.et_delay);
//        rd_timing=(RadioButton) findViewById(R.id.rb_timing);
//        rd_wheel=(RadioButton) findViewById(R.id.rb_wheel);
//        rd_oneWay=(RadioButton) findViewById(R.id.rb_oneWay);
//        rd_twoWays=(RadioButton) findViewById(R.id.rb_twoWays);
//        et_numberOfPulse=(EditText) findViewById(R.id.et_numberOfPulse);
//        et_singlePulse=(EditText) findViewById(R.id.et_singlePulse);
//        et_singleDistance=(EditText) findViewById(R.id.et_singleDistance);
//        et_samplingInterval=(EditText) findViewById(R.id.et_samplingInterval);
//        et_horiGain_coe=(EditText)findViewById(R.id.et_horGain_coe);

//        rb_fillingfilter = findViewById(R.id.rb_fillingfilter);
//        rb_lowpassfilter = findViewById(R.id.rb_lowpassfilter);
//        rb_highpassfilter= findViewById(R.id.rb_highpassfilter);
//        rb_bandpassfilter = findViewById(R.id.rb_bandpassfilter);
//
//
//        et_m_high_f=(EditText)findViewById(R.id.et_m_high_f);
//        et_m_low_f=(EditText)findViewById(R.id.et_m_low_f);
//
//        cb_gain = findViewById(R.id.checkbox_gain);
//        cb_backremove = findViewById(R.id.checkbox_backremove);
//        cb_filter = findViewById(R.id.checkbox_filter);


//		sp_oneWay.setEnabled(false);
//        rd_oneWay.setEnabled(false);
//        rd_twoWays.setEnabled(false);
//        et_numberOfPulse.setEnabled(false);
//        et_singleDistance.setEnabled(false);
//        et_singlePulse.setEnabled(false);
//        et_samplingInterval.setEnabled(false);

//        SharedPreferences shareXRaw=getSharedPreferences("xRaw", 0);
//        SharedPreferences shareGainData=getSharedPreferences("gainData", 0);
//        SharedPreferences shareCoeGain = getSharedPreferences("coeGain",0);
//        SharedPreferences shareBRaw=getSharedPreferences("bRaw",0);
//
//        SharedPreferences shareBackgrdData = getSharedPreferences("backgrdData",0);
//        SharedPreferences shareSumData = getSharedPreferences("sumdata",0);

//        sharemfiltermode = getSharedPreferences("mfiltermode",0);
//        shareifgain = getSharedPreferences("ifgain",0);
//        shareifgainEd = shareifgain.edit();
//        sharemfiltermodeEd = sharemfiltermode.edit();
//        shareifbackremove = getSharedPreferences("ifbackremove",0);
//        shareifbackremoveEd = shareifbackremove.edit();
//
//        sharemhighf = getSharedPreferences("mhighf",0);
//        sharemhighfEd = sharemhighf.edit();
//        sharemlowf = getSharedPreferences("mlowf",0);
//        sharemlowfEd = sharemlowf.edit();



        mainPeremeterOrders=getSharedPreferences("mainPeremeterOrders", 0);
        mainPeremeterOrdersEditor=mainPeremeterOrders.edit();

        //初始化触发方式spinner数据
//		listOfTheWayOfPulse=new ArrayList<String>();
//		listOfTheWayOfPulse.add(" ");
//		listOfTheWayOfPulse.add("正向");
//		listOfTheWayOfPulse.add("反向");
//		adapterOfTheWayOfPulse=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listOfTheWayOfPulse);
//		sp_oneWay.setAdapter(adapterOfTheWayOfPulse);

        //文件名、序列号、总文件名、存储路径赋值
//		Map<String,?> key_Value=(Map<String, ?>) mainPeremeterOrders.getAll();
//		Toast.makeText(this, key_Value.size()+"", Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, mainPeremeterOrders.getString("nameOfMainFile", "file"), Toast.LENGTH_LONG).show();
        et_nameOfMainFile.setText(mainPeremeterOrders.getString("nameOfMainFile", "file"));
        et_serialNumberOfFile.setText(mainPeremeterOrders.getInt("serialNumberOfFile", 1)+"");
        tv_storeFile.setText(et_nameOfMainFile.getText().toString()+et_serialNumberOfFile.getText().toString()+type);
        tv_settingPath.setText(mainPeremeterOrders.getString("path", " "));


        //初始化频率spinner数据
//        listOfFrequency=new ArrayList<Integer>();
//        listOfFrequency.add(10);
//        listOfFrequency.add(20);
//        listOfFrequency.add(30);
//        listOfFrequency.add(40);
//        listOfFrequency.add(50);
//        adapterOfFrequency=new ArrayAdapter<Integer>(this, R.layout.spinner_item, listOfFrequency);
//        sp_frequency.setAdapter(adapterOfFrequency);

        //以存取的spinner位置作为默认值
//        sp_frequency.setSelection(mainPeremeterOrders.getInt("index_frequency", 0), true);
//        frequency=Integer.parseInt(adapterOfFrequency.getItem(mainPeremeterOrders.getInt("index_frequency", 0)).toString());
//        if (frequency==10) {
//            pOrders.setFrequency((short) 0x0a00);
//        }else if (frequency==20) {
//            pOrders.setFrequency((short) 0x1400);
//        }else if (frequency==30) {
//            pOrders.setFrequency((short) 0x1e00);
//        }else if (frequency==40) {
//            pOrders.setFrequency((short) 0x2800);
//        }else if (frequency==50) {
//            pOrders.setFrequency((short) 0x3200);
//        }

//        et_numberOfPulse.setText(String.valueOf(500));
//        et_singlePulse.setText(String.valueOf(500));
//        et_singleDistance.setText(String.valueOf(0.30));
//        et_samplingInterval.setText(String.valueOf(calculateSamplingInterval()));
//        et_timeWindow.setText(mainPeremeterOrders.getString("timeWindow", "100"));
//
//
//
//
//        et_delay.setText(mainPeremeterOrders.getString("delay", "100"));



        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                nOrders=new NormalOrders();
                //发送开始设置命令
                while(true){
                    if (MainActivity.readThread.getJudge_startSetting()!=0) {
                        MainActivity.readThread.setJudge_startSetting(0);
                        break;
                    }else {
                        try {
                            nOrders.send(startSetting,MainActivity.ds);
                            Thread.sleep(100);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                //发送设置参数
                try {
                    pOrders.setFrequency((short) 0x3200);
                    pOrders.setTriggerMode(0);
                    pOrders.setTriggerDirection(-1);
                    pOrders.setTriggerpulsenum((byte)20);
//						pOrders.setFilename("file1.raw".getBytes());
                    pOrders.setTriggerdistance(2);
                    pOrders.setM_time_wnd((short) 50);
                    pOrders.setDelay_time_DELAY((short) 100);
                    pOrders.send();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }).start();


//        for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//            xRaw[i]=shareXRaw.getFloat(i+"", 10);
//        }
//        for (int i = 0; i <Const_NumberOfVerticalDatas; i++) {
//            gainData[i]=shareGainData.getInt(i+"", 1);
//        }
//        Arrays.fill(bRaw,10);
//        leftFragmentOfSettingActivity.setXRaw(xRaw);
//        middleBackFragmentOfSettingActivity.setBRaw(bRaw);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }


//    public double calculateSamplingInterval(){
//        return Double.parseDouble(et_singleDistance.getText().toString())/(Integer.parseInt(et_singlePulse.getText().toString())/Integer.parseInt(et_numberOfPulse.getText().toString()));
//    }
    //水平增益按钮点击事件
//    public void btn_horizongtalGain(View view){
//        cb_gain.setChecked(true);
//        switch (counteOfHorizontalGain%5) {
//            case 0:
//                for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//                    xRaw[i]=354;
//                }
//                break;
//            case 1:
//                for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//                    xRaw[i]=177;
//                }
//                break;
//            case 2:
//                for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//                    xRaw[i]=88;
//                }
//                break;
//            case 3:
//                for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//                    xRaw[i]=44;
//                }
//                break;
//            case 4:
//                for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//                    xRaw[i]=10;
//                }
//                break;
//            default:
//                break;
//        }
//        tempGain=1;
//        leftFragmentOfSettingActivity.setXRaw(xRaw);
////		middleBackFragmentOfSettingActivity.setBRaw(xRaw);
//        counteOfHorizontalGain++;
//    }
//
//    public void btn_dynamic_backgrd(View view){
//        cb_backremove.setChecked(true);
//        tempBackgrd=1;
//        for (int i = 0; i < Const_NumberOfBRawDatas; i++) {
//            bRaw[i]=354;
//        }
//        middleBackFragmentOfSettingActivity.setBRaw(bRaw);
//    }
//
//    public void btn_gain_apply(View view){
//        cb_gain.setChecked(true);
//        tempGain=1;
//        leftFragmentOfSettingActivity.setXRaw(xRaw);
//    }
//    public void btn_lowhighf_apply(View view){
//        cb_filter.setChecked(true);
//        dataprocess.setM_iffilter(true);
//        lowf = Float.parseFloat(et_m_low_f.getText().toString());
//        highf = Float.parseFloat(et_m_high_f.getText().toString());
//        dataprocess.m_filterP.setM_low_f(lowf);
//        dataprocess.m_filterP.setM_high_f(highf);
//    }
//
//
//    //步进增益按钮点击事件
//    public void btn_stableKGain(View view){
//        cb_gain.setChecked(true);
//        for (int i = 0; i < Const_NumberOfXRawDatas; i++) {
//            xRaw[i]=10+10*i;
//        }
//        tempGain=1;
//        leftFragmentOfSettingActivity.setXRaw(xRaw);
//
////		middleBackFragmentOfSettingActivity.setBRaw(xRaw);
//    }
//    public void btn_renew_filter(View view){
//
//        cb_gain.setChecked(false);
//        cb_filter.setChecked(false);
//        cb_backremove.setChecked(false);
//        rb_bandpassfilter.setChecked(false);
//        rb_highpassfilter.setChecked(false);
//        rb_lowpassfilter.setChecked(false);
//        rb_fillingfilter.setChecked(false);
//
//        Arrays.fill(xRaw,10);
//        Arrays.fill(bRaw,10);
//
//        dataprocess.init();
//        dataprocess.setM_iffilter(false);
//        leftFragmentOfSettingActivity.setXRaw(xRaw);
//        middleBackFragmentOfSettingActivity.setBRaw(bRaw);
//        Arrays.fill(gainData,1);
//        tempGain=0;
//        tempIffliter=4;
//        tempBackgrd=0;
//
//
//    }

    public void btn_sure(View view){
//		Toast.makeText(this, tv_settingPath.getText().toString(), Toast.LENGTH_SHORT).show();
        if (tv_settingPath.getText().toString().length()<3) {
            Toast.makeText(this, "路径不能为空", Toast.LENGTH_SHORT).show();
        }else {


            shareSettinglog = getSharedPreferences("settinglog",0);
            SharedPreferences.Editor shareSettinglogEditor=shareSettinglog.edit();
            shareSettinglogEditor.putInt("settinglog",1);
            shareSettinglogEditor.commit();

            path=tv_settingPath.getText().toString();
            file=new File(path+"/"+tv_storeFile.getText().toString());
//			if (pOrders.getTriggerMode()==(byte)1&&pOrders.getTriggerDirection()==(byte)-1){
//				Toast.makeText(this, "请检查测距轮触发是否选择完整", Toast.LENGTH_SHORT).show();
//			}
            if (file.exists()) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivityForNormal.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("警告");
                builder.setMessage("该文件夹下已有该文件，是否覆盖?");
                builder.setPositiveButton("确定", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (file.exists()&&file.isFile()) {
                            if (file.delete()) {

                                Toast.makeText(SettingActivityForNormal.this, "覆盖成功！", Toast.LENGTH_LONG).show();
                            }
                        }

//                        SharedPreferences.Editor shareGainData=getSharedPreferences("gainData", 0).edit();
//                        SharedPreferences.Editor shareXRaw=getSharedPreferences("xRaw", 0).edit();
                        SharedPreferences.Editor shareJudge=getSharedPreferences("judge", 0).edit();
//                        SharedPreferences.Editor shareBackgrdData=getSharedPreferences("backgrdData",0).edit();
//                        SharedPreferences.Editor shareBRaw=getSharedPreferences("bRaw",0).edit();
//                        SharedPreferences.Editor shareCoeGain = getSharedPreferences("coeGain",0).edit();

                        SharedPreferences.Editor shareSumData = getSharedPreferences("sumdata",0).edit();

//                        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
//                            shareGainData.putInt(i+"", gainData[i]);
//                        }
//                        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
//                            shareBackgrdData.putInt(i+"", backgrdData[i]);
//                        }
                        for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                            shareSumData.putInt(i+"", sumcolorGap[i]);
                        }

//                        for (int i = 0; i < 17; i++) {
//                            shareXRaw.putFloat(i+"", xRaw[i]);
//                        }
//
//                        for (int i = 0; i < 17; i++) {
//                            shareBRaw.putFloat(i+"", bRaw[i]);
//                        }
//                        shareCoeGain.putFloat("coeGain",coeGain);

                        shareJudge.putBoolean("judge", true);
                        mainPeremeterOrdersEditor.putString("timeWindow","50");
                        mainPeremeterOrdersEditor.putString("delay", "100");
                        mainPeremeterOrdersEditor.putString("path", path);
                        mainPeremeterOrdersEditor.putString("frequency",frequency+"");
                        mainPeremeterOrdersEditor.putString("nameOfMainFile", et_nameOfMainFile.getText().toString());
                        mainPeremeterOrdersEditor.putInt("serialNumberOfFile", Integer.parseInt(et_serialNumberOfFile.getText().toString()));
//                        sharemfiltermodeEd.putInt("mfiltermode",tempIffliter);
//                        shareifgainEd.putInt("ifgain",tempGain);
//                        shareifbackremoveEd.putInt("ifbackremove",tempBackgrd);
//
//                        sharemhighfEd.putFloat("mhighf",highf);
//                        sharemlowfEd.putFloat("mlowf",lowf);

//                        shareGainData.commit();
//                        shareXRaw.commit();
                        shareJudge.commit();
//                        shareBackgrdData.commit();
//                        shareBRaw.commit();
//                        shareCoeGain.commit();

//                        sharemlowfEd.commit();
//                        sharemhighfEd.commit();

                        shareSumData.commit();
//                        sharemfiltermodeEd.commit();
//                        shareifgainEd.commit();
//                        shareifbackremoveEd.commit();
                        mainPeremeterOrdersEditor.commit();
//                        countNumRadar=0;
                        //发送停止采集命令
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

                                try {
                                    nOrders.send(endSetting, MainActivity.ds);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();
//                        MainActivity.writeThread.setPath(path+"/"+tv_storeFile.getText().toString());
//                        MainActivity.writeThread.setSample_wnd(Integer.parseInt("50"));
//                        MainActivity.writeThread.setTimedelay(Short.parseShort("100"));
////                        MainActivity.writeThread.setPath_ano(path+"/"+tv_storeFile.getText().toString());
//                        MainActivity.writeHeadThread.setSample_wnd(Integer.parseInt("50"));
//                        MainActivity.writeHeadThread.setTimedelay(Short.parseShort("100"));

                        finish();

                    }
                });

                builder.setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
                builder.show();
            }else {
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                //在settingActivity上存储gainData[],从mainActivity中读取
//                SharedPreferences.Editor shareGainData=getSharedPreferences("gainData", 0).edit();
//                SharedPreferences.Editor shareXRaw=getSharedPreferences("xRaw", 0).edit();
                SharedPreferences.Editor shareJudge=getSharedPreferences("judge", 0).edit();
//                SharedPreferences.Editor shareBackgrdData=getSharedPreferences("backgrdData",0).edit();
//                SharedPreferences.Editor shareBRaw=getSharedPreferences("bRaw",0).edit();
//                SharedPreferences.Editor shareCoeGain = getSharedPreferences("coeGain",0).edit();

//                SharedPreferences.Editor shareSumData = getSharedPreferences("sumdata",0).edit();
//                for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
//                    shareGainData.putInt(i+"", gainData[i]);
//                }
//                for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
//                    shareBackgrdData.putInt(i+"", backgrdData[i]);
//                }
//                for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
//                    shareSumData.putInt(i+"", sumcolorGap[i]);
//
//                }
////                for (int i = 0; i < 17; i++) {
//                    shareXRaw.putFloat(i+"", xRaw[i]);
//                }
//
//                for (int i = 0; i < 17; i++) {
//                    shareBRaw.putFloat(i+"", bRaw[i]);
//                }
//                shareCoeGain.putFloat("coeGain",coeGain);

                shareJudge.putBoolean("judge", true);
                mainPeremeterOrdersEditor.putString("timeWindow","50");
                mainPeremeterOrdersEditor.putString("delay", "100");
                mainPeremeterOrdersEditor.putString("path", path);
                mainPeremeterOrdersEditor.putString("frequency",frequency+"");
                mainPeremeterOrdersEditor.putString("nameOfMainFile", et_nameOfMainFile.getText().toString());
                mainPeremeterOrdersEditor.putInt("serialNumberOfFile", Integer.parseInt(et_serialNumberOfFile.getText().toString()));
//                sharemfiltermodeEd.putInt("mfiltermode",tempIffliter);
//                shareifgainEd.putInt("ifgain",tempGain);
//                shareifbackremoveEd.putInt("ifbackremove",tempBackgrd);
//                sharemhighfEd.putFloat("mhighf",highf);
//                sharemlowfEd.putFloat("mlowf",lowf);
//
//                sharemhighfEd.commit();
//                sharemlowfEd.commit();
//                shareGainData.commit();
//                shareXRaw.commit();
                shareJudge.commit();
//                shareBackgrdData.commit();
//                shareBRaw.commit();
//                shareCoeGain.commit();

//                shareSumData.commit();
//                sharemfiltermodeEd.commit();
//                shareifgainEd.commit();
//                shareifbackremoveEd.commit();
                mainPeremeterOrdersEditor.commit();
//                countNumRadar=0;
                //发送停止采集命令
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            nOrders.send(endSetting, MainActivity.ds);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
//                MainActivity.writeThread.setPath(path+"/"+tv_storeFile.getText().toString());
//                MainActivity.writeThread.setSample_wnd(Integer.parseInt("50"));
//                MainActivity.writeThread.setTimedelay(Short.parseShort("100"));
////                MainActivity.writeThread.setPath_ano(path+"/"+tv_storeFile.getText().toString());
//                MainActivity.writeHeadThread.setSample_wnd(Integer.parseInt("50"));
//                MainActivity.writeHeadThread.setTimedelay(Short.parseShort("100"));


                finish();

            }
        }
    }

    //创建路径按钮点击事件
    public void btn_creatPath(View view){
        calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH) + 1;
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        mHour=calendar.get(Calendar.HOUR_OF_DAY);
        mMinute=calendar.get(Calendar.MINUTE);
        String creatFolderName="data"+"-"+mYear+"-"+mMonth+"-"+mDay+"-"+mHour+"-"+mMinute;
        File creatFolder=new File(defaultPath, creatFolderName);
        if (!creatFolder.exists()) {
            creatFolder.mkdirs();
            path=defaultPath+"/"+creatFolderName;
            tv_settingPath.setText(path.toString());
//			path=path+"/"+tv_storeFile.getText().toString();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(FILE_RESULT_CODE == requestCode){
            Bundle bundle = null;
            if(data!=null&&(bundle=data.getExtras())!=null){
                path=bundle.getString("file");
                tv_settingPath.setText(path);
//                path=path+"/"+tv_storeFile.getText().toString();
            }
        }
    }

    //设置路径按钮点击事件
    public void btn_settingPath(View view){
        Intent intent = new Intent(SettingActivityForNormal.this,MyFileManager.class);
        startActivityForResult(intent, FILE_RESULT_CODE);
    }

    public void btn_automaticSetting(View view){
        judge_automaticSetting=!judge_automaticSetting;
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (judge_automaticSetting) {

                    try {
                        handlerOfColour.sendEmptyMessage(1);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

//    public CompoundButton.OnCheckedChangeListener checkboxclickListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            switch (compoundButton.getId()){
//                case R.id.checkbox_gain:
//                    if (b){
//                        tempGain=1;
//                    }else {
//                        Arrays.fill(xRaw,10);
//                        leftFragmentOfSettingActivity.setXRaw(xRaw);
//                        Arrays.fill(gainData,1);
//                        tempGain=0;
//                    }
//                    break;
//                case R.id.checkbox_backremove:
//                    if (b){
//                        cb_backremove.setChecked(true);
//                        tempBackgrd=1;
//                        for (int i = 0; i < Const_NumberOfBRawDatas; i++) {
//                            bRaw[i]=354;
//                        }
//                        middleBackFragmentOfSettingActivity.setBRaw(bRaw);
//                    }else {
//                        Arrays.fill(bRaw,10);
//                        middleBackFragmentOfSettingActivity.setBRaw(bRaw);
//                        tempBackgrd=0;
//                    }
//                    break;
//                case R.id.checkbox_filter:
//                    if (b){
//
//                    }else {
//                        dataprocess.init();
//                        dataprocess.setM_iffilter(false);
//                        tempIffliter=4;
//                    }
//                    break;
//            }
//
//        }
//    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();


    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {//按键的按下事件
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            nOrders.send(endSetting, MainActivity.ds);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else if (event.getAction() == KeyEvent.ACTION_UP && event.getRepeatCount() == 0) {//按键的抬起事件
            }
        }
        return super.dispatchKeyEvent(event);
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

//	 public static void NavigationBarStatusBar(Activity activity,boolean hasFocus){
//	        if (hasFocus ) {
//	            View decorView = activity.getWindow().getDecorView();
//	            decorView.setSystemUiVisibility(
//	                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//	                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//	                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//	                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//	                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//	                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//	        }
//	 }


//	protected void hideBottomUIMenu() {
//        //隐藏虚拟按键，并且全屏
//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//    }



}
