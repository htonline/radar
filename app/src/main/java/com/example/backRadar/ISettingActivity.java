package com.example.backRadar;

import android.view.View;

import com.example.backRadar.Entity.FileInfoInPath;

import java.util.List;

interface ISettingActivity {
    public void btn_save_params(View view);
    public void btn_import_params(View view);
    public void showPathFiles(List<FileInfoInPath> lists_path);
}
