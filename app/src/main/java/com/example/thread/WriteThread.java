package com.example.thread;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import android.R.integer;
import android.util.Log;

public class WriteThread implements Runnable {
    private String path_ano = null;
    public Thread t;
    private String path = null;
    private int judgeNumber = 0;
    private int anoStart = 0;
    private boolean judgePause = false;
    private boolean judgeStart = true;
    private boolean judgeIfRepeat = false;
    private static final int Const_NumberOfVerticalDatas = 512;
    private short colorGap[] = new short[Const_NumberOfVerticalDatas];
//    private short tempColorGap[] =new short[Const_NumberOfVerticalDatas];
    private static final String TAG = "WriteThread";

    public short[] getRAWCOLORGAP() {
        return RAWCOLORGAP;
    }

    public void setRAWCOLORGAP(short[] RAWCOLORGAP) {
        this.RAWCOLORGAP = RAWCOLORGAP;
    }

    private short RAWCOLORGAP[] = new short[Const_NumberOfVerticalDatas];
//    private short TEMPRAWCOLORGAP[] = new short[Const_NumberOfVerticalDatas];

    private int sample_wnd;
    private short timedelay;
    private int trace_num = 100;
    RandomAccessFile raf = null;
    RandomAccessFile raf2 = null;
//	private final ByteBuffer buffer=ByteBuffer.allocate(1024);
//	private FileChannel fChannel=null;

    byte[] line_position = new byte[36];
    byte[] nouse = new byte[158];

    public int getAnoStart() {
        return anoStart;
    }

    public void setAnoStart(int anoStart) {
        this.anoStart = anoStart;
    }

    public int getSample_wnd() {
        return sample_wnd;
    }

    public void setSample_wnd(int sample_wnd) {
        this.sample_wnd = sample_wnd;
    }

    public short getTimedelay() {
        return timedelay;
    }

    public void setTimedelay(short timedelay) {
        this.timedelay = timedelay;
    }

    public int getTrace_num() {
        return trace_num;
    }

    public void setTrace_num(int trace_num) {
        this.trace_num = trace_num;
    }

    public int getJudgeNumber() {
        return judgeNumber;
    }

    public void setJudgeNumber(int judgeNumber) {
        this.judgeNumber = judgeNumber;
    }

    public boolean isJudgePause() {
        return judgePause;
    }

    public void setJudgePause(boolean judgePause) {
        this.judgePause = judgePause;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPath_ano(String path) {
        this.path_ano = path + "-copy";
    }

    public short[] getColorGap() {
        return colorGap;
    }

    public void setColorGap(short[] colorGap) {
        this.colorGap = colorGap;
    }

    public boolean isJudgeStart() {
        return judgeStart;
    }

    public void setJudgeStart(boolean judgeStart) {
        this.judgeStart = judgeStart;
    }

    public boolean isJudgeIfRepeat() {
        return judgeIfRepeat;
    }

    public void setJudgeIfRepeat(boolean judgeIfRepeat) {
        this.judgeIfRepeat = judgeIfRepeat;
    }

    int tempnn = 1;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (judgeStart) {
            if (path != null) {
                switch (judgeNumber) {
                    case 1:
//					try {
//						fos=new FileOutputStream(path);
//						bos=new BufferedOutputStream(fos);
//						try {
//							bos.write("yf  ".getBytes());
//							bos.write(int2byte(sample_wnd));
//							bos.write(int2byte(trace_num));
//							bos.write(int2byte(512));
//							bos.write(int2byte(0));
//							bos.write(int2byte(1));
//							bos.write(line_position);
//							bos.write(shortToByte((short)1));
//							bos.write(shortToByte((short)512));
//							bos.write(shortToByte((short)0));
//							bos.write(shortToByte(timedelay));
//							bos.write(shortToByte((short)1));
//							bos.write(shortToByte((short)0));
//							bos.write(int2byte(0));
//							bos.write(floatToByte((float)1));
//							bos.write(shortToByte((short)0));
//							bos.write("  ".getBytes());
//							bos.write(nouse);
//							bos.flush();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//					} catch (FileNotFoundException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
                        Arrays.fill(line_position,(byte)0xCC);
                        Arrays.fill(nouse, (byte) 0xCC);
                        if (anoStart == 1) {
                            try {
                                raf2 = new RandomAccessFile(path_ano, "rw");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            try {
                                raf2.write("yf  ".getBytes(StandardCharsets.UTF_8));
                                raf2.write(int2byte(sample_wnd));
                                raf2.write(int2byte(trace_num));
                                raf2.write(int2byte(512));
                                raf2.write(int2byte(0));
                                raf2.write(int2byte(1));
                                raf2.write(line_position);
                                raf2.write(shortToByte((short) 1));
                                raf2.write(shortToByte((short) 512));
                                raf2.write(shortToByte((short) 0));
                                raf2.write(shortToByte(timedelay));
                                raf2.write(shortToByte((short) 1));
                                raf2.write(shortToByte((short) 0));
                                raf2.write(int2byte(0));
                                raf2.write(floatToByte((float) 1));
                                raf2.write(shortToByte((short) 0));
//                                raf2.write("    ".getBytes(StandardCharsets.UTF_8));
                                raf2.write(nouse);

                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                        try {
                            raf = new RandomAccessFile(path, "rw");
                        } catch (FileNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try {
                            raf.write("yf  ".getBytes(StandardCharsets.UTF_8));
                            raf.write(int2byte(sample_wnd));
                            raf.write(int2byte(trace_num));
                            raf.write(int2byte(512));
                            raf.write(int2byte(0));
                            raf.write(int2byte(1));

                            raf.write(line_position);
                            raf.write(shortToByte((short) 1));
                            raf.write(shortToByte((short) 512));
                            raf.write(shortToByte((short) 0));
                            raf.write(shortToByte(timedelay));
                            raf.write(shortToByte((short) 1));
                            raf.write(shortToByte((short) 0));
                            raf.write(int2byte(0));
                            raf.write(floatToByte((float) 1));
                            raf.write(shortToByte((short) 0));
                            raf.write(nouse);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        judgeNumber++;
                        break;
                    case 2:
                        break;
                    case 3:
                        if (!judgeIfRepeat) {
                            try {
                                for (int i = 0; i < Const_NumberOfVerticalDatas; i++) {
                                    raf.write(shortToByte(colorGap[i]));
                                    if (anoStart == 1) {
                                        raf2.write(shortToByte(RAWCOLORGAP[i]));
                                    }
                                }
//								bos.flush();

                                judgeIfRepeat = true;
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        break;
                    case 4:
//					try {
//						raf=new RandomAccessFile(path, "rw");
//					} catch (FileNotFoundException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					try {						
//						raf.write("yf  ".getBytes());
//						raf.write(int2byte(sample_wnd));
//						raf.write(int2byte(trace_num));
//						raf.write(int2byte(512));
//						raf.write(int2byte(0));
//						raf.write(int2byte(1));
//						raf.write(line_position);
//						raf.write(shortToByte((short)1));
//						raf.write(shortToByte((short)512));
//						raf.write(shortToByte((short)0));
//						raf.write(shortToByte(timedelay));
//						raf.write(shortToByte((short)1));
//						raf.write(shortToByte((short)0));
//						raf.write(int2byte(0));
//						raf.write(floatToByte((float)1));
//						raf.write(shortToByte((short)0));
//						raf.write("  ".getBytes());
//						raf.write(nouse);
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 开始
     */
    public void start() {

        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }


    public void stop() {
        try {
            judgeStart = false;
            raf.close();
            if (anoStart == 1) raf2.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setJudgeStart(false);
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

    /**
     * 浮点转换为字节
     *
     * @param f
     * @return
     */
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


    public static short ShortToLow(short a) {
        return (short) ((((a & 0xFF00) >> 8)& 0x00FF) | (((a& 0x00FF) << 8) & 0xFF00));
    }

}
