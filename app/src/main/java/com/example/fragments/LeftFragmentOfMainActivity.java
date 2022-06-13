package com.example.fragments;

import java.util.Random;

import com.example.customizingViews.ColoursView;
import com.example.ladarmonitor.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LeftFragmentOfMainActivity extends Fragment{
	
	private ColoursView coloursView=null;
	public static final int Const_NumberOfVerticalDatas=512;
	private int colorList []=new int [Const_NumberOfVerticalDatas];
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_mainactivity_left, null);
		coloursView=(ColoursView) view.findViewById(R.id.coloursView);
		return view;
	}
	
	public void drawNewVertical(int colorList[]) {
		// TODO Auto-generated method stub
		
		coloursView.drawNewVertical(colorList);
	}

	public void drawLines(int num){
		coloursView.setTimeWindow(num);
	}

    public void drawNewVertical1024(int[] colorList) {
		coloursView.drawNewVertical1024(colorList);
    }
}
