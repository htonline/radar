package com.example.BackRadar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.example.adapter.AdapterOfFileList;
import com.example.ladarmonitor.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyFileManager extends ListActivity {

	private List<String> items = null;
	private List<String> paths = null;
	private String rootPath = Environment.getExternalStorageDirectory().getPath();
	private String curPath = Environment.getExternalStorageDirectory().getPath();
	private TextView mPath;
	private final static String TAG = "bb";
	private Button btn_Confirm = null;
	private Button btn_Cancle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_file_manager);
		mPath = (TextView) findViewById(R.id.tv_mPath);
		btn_Confirm = (Button) findViewById(R.id.btn_Confirm);
		btn_Cancle = (Button) findViewById(R.id.btn_Cancle);
		btn_Confirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent data = new Intent(MyFileManager.this,
						SettingActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("file", curPath);
				data.putExtras(bundle);
				setResult(2, data);
				finish();
			}
		});

		btn_Cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		getFileDir(rootPath);
		

	}

	private void getFileDir(String filePath) {
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
//		Log.e("3333333333",files.length+ "");
		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.listFiles() != null) {
				items.add(file.getName());
				paths.add(file.getPath());
			}
		}
		setListAdapter(new AdapterOfFileList(this, items, paths));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(paths.get(position));
		if (file.isDirectory()) {
			curPath = paths.get(position);
			getFileDir(paths.get(position));
		} else {
			// 可以打开文件
		}
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

}
