package com.example.fragments;

import com.example.customizingViews.GainCurveView;
import com.example.interfaces.CallBackGainData;
import com.example.interfaces.OnUpActionListener;
import com.example.ladarmonitor.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LeftFragmentOfSettingActivity extends Fragment {
    private static final String TAG = "LeftFragmentOfSettingAc";
    private GainCurveView gainCurveView = null;
    public CallBackGainData callBackGainData=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_settingactivity_left, null);
        gainCurveView = (GainCurveView) view.findViewById(R.id.gainCurveView);

        //接口回调，获得增益数组
        gainCurveView.setOnUpActionListener(new OnUpActionListener() {

            @Override
            public void onUp(int[] gainData, float[] xRaw) {
                // TODO Auto-generated method stub
                callBackGainData.gainData(gainData, xRaw);
            }

            @Override
            public void onUp1024(int[] gainData, float[] xRaw) {
                callBackGainData.gainData1024(gainData,xRaw);
            }
        });
        gainCurveView.post(new Runnable() {
            @Override
            public void run() {
                gainCurveView.refresh();
            }
        });
        return view;
    }

    public void setXRaw(float[] xRaw) {

        gainCurveView.setxRaw(xRaw);

    }

    public void set(float[] xRaw, int[] gainData) {
        gainCurveView.returnGainData(xRaw,gainData);

    }

    public void setCallBackGainData(CallBackGainData callBackGainData) {
        this.callBackGainData = callBackGainData;
    }

}
