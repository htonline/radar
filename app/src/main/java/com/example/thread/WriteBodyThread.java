package com.example.thread;

import android.util.Log;
import android.widget.TextView;

import com.example.interfaces.GetCallBack;

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

    private GetCallBack getCallBack;

    public void setGetCallBack(GetCallBack getCallBack) {
        this.getCallBack = getCallBack;
    }

    @Override
    public void run() {
        try {
            if (mraf!=null){
                mraf.write(mdata);
                getCallBack.doThing();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
