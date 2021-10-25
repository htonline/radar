package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customizingViews.BackgrdCurveView;
import com.example.interfaces.CallBackBackgrdData;
import com.example.interfaces.OnUpActionBackgrdListener;
import com.example.ladarmonitor.R;

public class MiddleBackFragmentOfSettingActivity extends Fragment {
    private static final String TAG = "MiddleBackFragmentOfSet --------";
    private BackgrdCurveView backgrdCurveView = null;
    public CallBackBackgrdData callBackBackgrdData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_settingactivity_middlebackground, null);
        backgrdCurveView = (BackgrdCurveView) view.findViewById(R.id.backgrdCurveView);

        //接口回调，获得增益数组
        backgrdCurveView.setOnUpActionnBackgrdListener(new OnUpActionBackgrdListener() {

            @Override
            public void onUp(int[] backgrdData, float[] bRaw) {
                Log.d(TAG, "onUp: --------");
                // TODO Auto-generated method stub
                callBackBackgrdData.backgrdData(backgrdData, bRaw);
            }
        });
        return view;
    }

    public void setBRaw(float[] bRaw) {
        Log.d(TAG, "set: ---- "+bRaw[1]);
        backgrdCurveView.setbRaw(bRaw);

    }

    public void set(float[] bRaw, int[] backgrdData) {
        backgrdCurveView.returnBackData(bRaw,backgrdData);

    }

    public void setCallBackGroudData(CallBackBackgrdData callBackBackgrdData) {
        this.callBackBackgrdData = callBackBackgrdData;
    }

}
