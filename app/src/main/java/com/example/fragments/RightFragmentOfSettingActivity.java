package com.example.fragments;

import com.example.customizingViews.RadarView;
import com.example.ladarmonitor.R;

import android.R.integer;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RightFragmentOfSettingActivity extends Fragment{
	
	private RadarView radarView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_settingactivity_right, null);
		radarView=(RadarView) view.findViewById(R.id.radarView);
		return view;
	}
	
//	public void setRadarViewGainData(int gainData[]){
//		radarView.setGainData(gainData);
//	}
	
	public void drawRadarWave(short colorGap[]){
		radarView.setColorData(colorGap);

	}
}
