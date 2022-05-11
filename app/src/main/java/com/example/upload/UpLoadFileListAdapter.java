package com.example.upload;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.interfaces.CallBackToDo;
import com.example.ladarmonitor.R;
import com.example.upload.entity.UpLoadFileInfo;

import java.util.List;

public class UpLoadFileListAdapter extends BaseAdapter {
    List<UpLoadFileInfo> mfileInfos;
    private Context mcontext;
    private static final String TAG = "UpLoadFileListAdapter";
    private String filepath;
    private SharedPreferences sharePath;
    private CallBackToDo mtoDo;

    public UpLoadFileListAdapter(Context context, CallBackToDo tod, List<UpLoadFileInfo> infos) {
        mcontext = context;
        mfileInfos = infos;
        mtoDo = tod;
        initData();
    }

    private void initData() {
        sharePath = mcontext.getSharedPreferences("mainPeremeterOrders", 0);
        filepath = sharePath.getString("path", "/storage/emulated/0");
    }

    @Override
    public int getCount() {
        return mfileInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mfileInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoler holer;
        View view;
        if (convertView != null) {
            view = convertView;
            holer = (ViewHoler) view.getTag();
        } else {
            view = View.inflate(mcontext, R.layout.list_item_layout_uploadfile, null);
            holer = new ViewHoler();
            holer.mtv_cxbh = view.findViewById(R.id.detectionLineBiaohao);
            holer.met_start = view.findViewById(R.id.detectionStartingDistance);
            holer.met_stop = view.findViewById(R.id.detectionEndingDistance);
            holer.met_length = view.findViewById(R.id.detectionLength);
            holer.mbtn_searchPathRadarFile = view.findViewById(R.id.my_dialog_addFile);
            holer.mtv_radarFileName = view.findViewById(R.id.tv_pathoffile);
            holer.mtv_uploadProgress = view.findViewById(R.id.upload_percent);
            holer.mbtn_uploadRadarFile = view.findViewById(R.id.ib_uploadFile);
            holer.mbtn_uploadPhoto = view.findViewById(R.id.ib_upload_pic_01);
            view.setTag(holer);
        }
        if (position % 2 == 0) {
            LinearLayout layout = view.findViewById(R.id.lyt_item);
            try {
                layout.setBackgroundColor(Color.parseColor("#ffffff"));
            } catch (Exception e) {
                Log.d(TAG, "getView: e " + e);
            }
        }
        holer.mtv_cxbh.setText(mfileInfos.get(position).getCxbh());
        if (mfileInfos.get(position).getFilePath() != null) {
            String[] sts = mfileInfos.get(position).getFilePath().split("/");
            holer.mtv_radarFileName.setText(sts[sts.length - 1]);
        } else {
            holer.mtv_radarFileName.setText(null);
        }
        if (mfileInfos.get(position).getIsfileup()) {
            holer.mbtn_uploadRadarFile.setImageResource(R.drawable.ok16);
        }
        if (mfileInfos.get(position).getIsphotoup()) {
            holer.mbtn_uploadPhoto.setImageResource(R.drawable.ok16);
        }
        holer.mtv_uploadProgress.setText(mfileInfos.get(position).getUploadpercent());
        holer.mbtn_searchPathRadarFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtoDo.callBackdoSearchFile(position);
            }
        });
        holer.mbtn_uploadRadarFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtoDo.callBackdoUploadFile(position);
            }
        });
        holer.mbtn_uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtoDo.callBackdoUploadPhoto(position);
            }
        });
        return view;
    }

    static class ViewHoler {
        TextView mtv_cxbh;
        EditText met_start;
        EditText met_stop;
        EditText met_length;
        ImageButton mbtn_searchPathRadarFile;
        TextView mtv_radarFileName;
        TextView mtv_uploadProgress;
        ImageButton mbtn_uploadRadarFile;
        ImageButton mbtn_uploadPhoto;
    }




}
