package com.example.backRadar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.backRadar.Entity.FileInfoInPath;
import com.example.ladarmonitor.R;

import java.util.List;

public class FileListAdapter extends BaseAdapter {
    List<FileInfoInPath> fileInfoInPathLists;
    Context mcontext;

    static class ViewHolder {
        TextView tv_path_file_name;
        TextView tv_path_file_updateTime;
    }

    FileListAdapter(Context context, List<FileInfoInPath> paths) {
        mcontext = context;
        fileInfoInPathLists = paths;
    }

    @Override
    public int getCount() {
        return fileInfoInPathLists.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfoInPathLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        final ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(mcontext, R.layout.path_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_path_file_name = view.findViewById(R.id.tv_path_filename);
            viewHolder.tv_path_file_updateTime = view.findViewById(R.id.tv_path_update_time);
            view.setTag(viewHolder);
        }
        FileInfoInPath infoInPath = fileInfoInPathLists.get(position);
        viewHolder.tv_path_file_updateTime.setText(infoInPath.getUpdateTime());
        viewHolder.tv_path_file_name.setText(infoInPath.getFilename());
        return view;
    }
}
