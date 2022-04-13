package com.example.orders;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class HardwaregainOrders {
    private static final String TAG = "HardwaregainOrders";
    private  byte[] bytes;
    private  DatagramPacket dp = null;
    private  DatagramSocket ds = null;
    public HardwaregainOrders(DatagramSocket ds) {
        bytes = new byte[1028];
        bytes[0] = 0x22;
        bytes[1] = 0x22;
        bytes[2] = 0x00;
        bytes[3] = 0x00;
        this.ds = ds;
    }

    public  void send(int[] gains) {
        byte[] tb ;
        for (int i = 0; i < gains.length; i++) {
            tb = int2byte(gains[i]*2600);
            bytes[2 * i + 4] = tb[0];
            bytes[2 * i + 5] = tb[1];
        }
        try {
            dp=new DatagramPacket(bytes, bytes.length, InetAddress.getByName("192.168.0.150"),8081);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ds.send(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //int 转byte数组
    private  byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }
}
