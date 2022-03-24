package com.example.thread;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class WriteHeadThread extends Thread{
    private static final String TAG="WriteHeadThread";
    RandomAccessFile mraf=null;
    private static int sample_wnd;
    private static short timedelay;
    private int trace_num = 100;
    byte[] line_position = new byte[36];
    byte[] nouse = new byte[158];

    public static void setSample_wnd(int msample_wnd) {
        sample_wnd = msample_wnd;
    }

    public static void setTimedelay(short mtimedelay) {
        timedelay = mtimedelay;
    }


    public WriteHeadThread(RandomAccessFile raf){
        mraf = raf;
    }

    @Override
    public void run() {
        try {
            if (mraf!=null){
                Arrays.fill(line_position,(byte)0xCC);
                Arrays.fill(nouse, (byte) 0xCC);
                mraf.write("yf  ".getBytes(StandardCharsets.UTF_8));
                mraf.write(int2byte(sample_wnd));
                mraf.write(int2byte(trace_num));
                mraf.write(int2byte(512));
                mraf.write(int2byte(0));
                mraf.write(int2byte(1));

                mraf.write(line_position);
                mraf.write(shortToByte((short) 1));
                mraf.write(shortToByte((short) 512));
                mraf.write(shortToByte((short) 0));
                mraf.write(shortToByte(timedelay));
                mraf.write(shortToByte((short) 1));
                mraf.write(shortToByte((short) 0));
                mraf.write(int2byte(0));
                mraf.write(floatToByte((float) 1));
                mraf.write(shortToByte((short) 0));
                mraf.write(nouse);
            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    public static byte[] floatToByte(float f) {

        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> (24 - i * 8));
        }

        // 翻转数组
        int len = b.length;
        // 建立一个与源数组元素类型相同的数组
        byte[] dest = new byte[len];
        // 为了防止修改源数组，将源数组拷贝一份副本
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        // 将顺位第i个与倒数第i个交换
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }

        return dest;

    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static byte[] shortToByte(short number) {
        short temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Short((short) (temp& 0xff)).byteValue();
//                    new Integer(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = (short) (temp >> 8); // 向右移8位
        }
        return b;
    }
}
