package com.example.thread;

import android.util.Log;

import com.example.interfaces.ShowSaveFinish;


import java.io.IOException;
import java.io.RandomAccessFile;

public class WriteRearThread extends Thread {
    private static final String TAG = "WriteRearThread";
    RandomAccessFile mraf = null;
    public WriteRearThread(RandomAccessFile raf){
        mraf = raf;
    }
    ShowSaveFinish mshowSaveFinish;

    public void setShowSaveNum(ShowSaveFinish showSaveF) {
        this.mshowSaveFinish = showSaveF;
    }
    @Override
    public void run() {
        if (mraf!=null){
            try {
                mraf.close();
                if (mshowSaveFinish!=null)mshowSaveFinish.showsavefinish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
