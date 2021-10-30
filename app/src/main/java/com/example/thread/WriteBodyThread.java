package com.example.thread;

import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;

public class WriteBodyThread extends Thread {
    private static final String TAG = "WriteBodyThread------";
    RandomAccessFile mraf=null;
    private static final int Const_NumberOfVerticalDatas = 512;
    private byte[] mdata = new byte[Const_NumberOfVerticalDatas*2];
    public WriteBodyThread(RandomAccessFile raf, byte[] data){
        mraf = raf;
        mdata = data;

    }

    @Override
    public void run() {
        try {
            if (mraf!=null){
                Log.d(TAG, "run: --> "+mraf.length());
                mraf.write(mdata);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
