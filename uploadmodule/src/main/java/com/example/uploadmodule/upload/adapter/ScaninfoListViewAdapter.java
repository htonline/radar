package com.example.uploadmodule.upload.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.uploadmodule.R;

import java.util.AbstractMap;
import java.util.List;

public class ScaninfoListViewAdapter extends BaseAdapter {
    private static final String TAG = "ScaninfoListViewAdapter";
    private List<AbstractMap.SimpleEntry<String,String>> scaninfolists;
    private Context mcontext;
    public ScaninfoListViewAdapter(Context context, List<AbstractMap.SimpleEntry<String, String>> scaninfolists){
        this.scaninfolists = scaninfolists;
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return scaninfolists.size();
    }

    @Override
    public Object getItem(int position) {
        return scaninfolists.get(position);
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
        }else{
            view = View.inflate(mcontext, R.layout.listview_scaninfo_item,null);
            holer = new ViewHoler();
            holer.mtv_key = view.findViewById(R.id.tv_scaninfo_key);
            holer.mtv_value = view.findViewById(R.id.tv_scaninfo_value);
            view.setTag(holer);
        }
        holer.mtv_key.setText(scaninfolists.get(position).getKey());
        holer.mtv_value.setText(scaninfolists.get(position).getValue());
        return view;
    }

    static class ViewHoler {
        TextView mtv_key;
        TextView mtv_value;
    }
}
