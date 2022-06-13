package com.example.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ReadThread implements Runnable {
    private static final String TAG = "ReadThread";
    public Thread t;
    boolean suspended = false;
    public Handler handler = null;
    public static final int Const_NUMBEROFDATA = 512;
    public static final int Const_NEW_NUMBEROFDATA = 513;
    public static final int Const_NUMBER_1024 = 1024;

    Bundle bundle = new Bundle();
    short arrayListOfColour[] = new short[Const_NUMBEROFDATA];
    short arrayListOfNewColour[] = new short[Const_NEW_NUMBEROFDATA];
    short arrayListOfNewColour_1024[] = new short[Const_NUMBER_1024];

    //记录当前数据是第几道
    private int numberOfLogo;
    //记录实际收到多少道数据
    private int numberOfReceive = 0;
    ArrayList<byte[]> arrayListOfBytes = null;
    DatagramSocket ds = null;
    DatagramPacket dp = null;
    byte b1[] = new byte[1036];
    byte bytes[] = new byte[2060];
    Message message = null;
    byte receive[] = new byte[4];
    byte receiveCC[] = new byte[2];
    private int judge_creatLink = 0;
    private int judge_cc_Mart = 0;
    private int judge_startSetting = 0;
    private int judge_endSetting = 0;
    private int judge_startCollect = 0;
    private int judge_suspendCollect = 0;
    private int judge_continueCollect = 0;
    private int judge_stopCollect = 0;
    private boolean judge = true;
    byte creatLink[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x01, 0x00};
    byte startSetting[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x02, 0x00};
    byte endSetting[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x04, 0x00};
    byte startCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x08, 0x00};
    byte suspendCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x10, 0x00};
    byte continueCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x20, 0x00};
    byte stopCollect[] = {(byte) 0xaa, (byte) 0xaa, (byte) 0x40, 0x00};
    byte martCC[] = {(byte) 0xcc,(byte)0xcc};
    byte battery[] = {(byte) 0x99,(byte)0x99};

    int tempJudge=0;

    int status[] = new int[10];
    int number[] = new int[10];
    int index = 0;

    public ReadThread(DatagramSocket ds) {
        this.ds = ds;
        init();
    }
    File file = new File("storage/emulated/0/ok");
    FileOutputStream os;
    public void init() {
        dp = new DatagramPacket(b1, b1.length);
    }
    int tempnum=1;

    public void run() {
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) {
            if (judge) {
                try {
                    ds.receive(dp);
                    System.arraycopy(b1, 0, receive, 0, 4);
                    System.arraycopy(b1,0,receiveCC,0,2);
                    if (Arrays.equals(receive, creatLink)) {
                        judge_creatLink++;
                    } else if (Arrays.equals(receive, startSetting)) {
                        judge_startSetting++;
                    } else if (Arrays.equals(receive, endSetting)) {
                        judge_endSetting++;
                    } else if (Arrays.equals(receive, startCollect)) {
                        judge_startCollect++;
                    } else if (Arrays.equals(receive, suspendCollect)) {
                        judge_suspendCollect++;
                    } else if (Arrays.equals(receive, continueCollect)) {
                        judge_continueCollect++;
                    } else if (Arrays.equals(receive, stopCollect)) {
                        judge_stopCollect++;
                    } else if(Arrays.equals(receiveCC,martCC)){
                        tempJudge=1;
                    }
                    else //if (receive[0]==0xee&&receive[1]==0xee){
                    {
                        byte[] precision = Arrays.copyOfRange(b1,9,10);
                        arrayListOfNewColour = toShortArray(Arrays.copyOfRange(b1, 10, 1036));
                        if (precision[0] == 0x01){
                            numberOfReceive++;
                            message = Message.obtain();
                            message.what = 0;
                            message.arg1 = tempJudge;
                            message.arg2 = numberOfReceive;
                            message.obj = arrayListOfNewColour;
                            handler.sendMessage(message);
                            tempJudge=0;
                        }else if (precision[0] == 0x02){
                            byte[] precinum = Arrays.copyOfRange(b1,8,9);
                            if (precinum[0] == 0x00){
                                Arrays.fill(arrayListOfNewColour_1024, (short) 0);
                                System.arraycopy(arrayListOfNewColour,0,arrayListOfNewColour_1024,0,512);
                                continue;
                            }
                            if (precinum[0] == 0x01){
                                System.arraycopy(arrayListOfNewColour,0,arrayListOfNewColour_1024,512,512);
                                numberOfReceive++;
                                message = Message.obtain();
                                message.what = 0;
                                message.arg1 = tempJudge;
                                message.arg2 = -numberOfReceive;
                                message.obj = arrayListOfNewColour_1024;
                                handler.sendMessage(message);
                                tempJudge=0;
                                continue;
                            }
                        }
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                break;
            }

            synchronized (this) {
                while (suspended) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }


        //初始化数据，先给10个byte数组都赋上值
//    		if (index==0) {
//    			for (i= 0; i<10; i++) {
//    				dp=new DatagramPacket(arrayListOfBytes.get(i), arrayListOfBytes.get(i).length);
//    				try {
//    					ds.receive(dp);
//    					System.arraycopy(arrayListOfBytes.get(i), 0, receive, 0, 5);
//    					if (Arrays.equals(receive, creatLink)) {
//							judge_creatLink++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, startSetting)) {
//							judge_startSetting++;
//							Log.d("--------------", "judge_startSetting"+judge_startSetting);
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, endSetting)) {
//							judge_endSetting++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, startCollect)) {
//							judge_startCollect++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, suspendCollect)) {
//							judge_suspendCollect++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, continueCollect)) {
//							judge_continueCollect++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, stopCollect)) {
//							judge_stopCollect++;
//							i--;
//							continue;
//						}else {					
//							number[i]=(int)(arrayListOfBytes.get(i)[2]);
//							status[i]=1;
//						}
//    				} catch (IOException e) {
//    					// TODO Auto-generated catch block
//    					e.printStackTrace();
//    				}
//    			}
//    			index++;
//			}
//    		
//    		    		
//    		//先遍历集合，判断number[]里边最小的道数min和index相等不，如果相等，把status[i]置为0，并且handler发送消息；
//    		//如果不相等，index=min,相当于丢包，舍去该数据包。
//    		min=number[0];
//    		j=0;
//    		for (i = 0; i < 10;i++) {
//				if (min>number[i]) {
//					min=number[i];
//					j=i;
//				}
//			}
//    		if (index==min) {
//				status[j]=0;
//				
//				
//				arrayListOfColour=toShortArray(Arrays.copyOfRange(arrayListOfBytes.get(j), 6, 1030));
//				message.what=0;
//				message.obj=arrayListOfColour;
//				handler.sendMessage(message);
//				index++;
//			}else {
//				index=min;
//			}
//    		
//    		//判断该byte数组是不是为空，为空覆盖
//    		//开始判断
//    		for (i = 0; i < 10; i++) {
//				if (status[i]==0) {
//					try {
//						dp=new DatagramPacket(arrayListOfBytes.get(i), arrayListOfBytes.get(i).length);
//						ds.receive(dp);
//						System.arraycopy(arrayListOfBytes.get(i), 0, receive, 0, 5);
//    					if (Arrays.equals(receive, creatLink)) {
//							judge_creatLink++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, startSetting)) {
//							judge_startSetting++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, endSetting)) {
//							judge_endSetting++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, startCollect)) {
//							judge_startCollect++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, suspendCollect)) {
//							judge_suspendCollect++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, continueCollect)) {
//							judge_continueCollect++;
//							i--;
//							continue;
//						}else if (Arrays.equals(receive, stopCollect)) {
//							judge_stopCollect++;
//							i--;
//							continue;
//						}else {					
//							number[i]=(int)(arrayListOfBytes.get(i)[2]);
//							status[i]=1;
//						}
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			}


    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public static short LowToShort(short a) {
        return (short) (((a & 0x00FF) << 8) | ((a >> 8) & 0x00FF));
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

    /**
     * 暂停
     */
    public void suspend() {
        suspended = true;
    }

    /**
     * 继续
     */
    public synchronized void resume() {
        suspended = false;
        notify();
    }
    public static short[] toShortArray1(byte[] src) {

        int count = src.length >> 1;
        short[] dest = new short[count];
        for (int i = 0; i < count; i++) {
            dest[i] = (short) ((src[i * 2]&0xFF) << 8 | src[2 * i + 1] & 0xff);
        }

        return dest;
    }
    public static short[] toShortArray(byte[] src) {

        int count = src.length >> 1;
        short[] dest = new short[count];
        for (int i = 0; i < count; i++) {
            dest[i] = (short) ((src[i * 2]&0xFF) << 8 | src[2 * i + 1] & 0xff);
            dest[i] = LowToShort(dest[i]);
        }

        return dest;
    }


    public int getNumberOfLogo() {
        return numberOfLogo;
    }

    public void setNumberOfLogo(int numberOfLogo) {
        this.numberOfLogo = numberOfLogo;
    }

    public int getJudge_cc_Mart() {
        return judge_cc_Mart;
    }

    public void setJudge_cc_Mart(int judge_cc_Mart) {
        this.judge_cc_Mart = judge_cc_Mart;
    }

    public int getNumberOfReceive() {
        return numberOfReceive;
    }

    public void setNumberOfReceive(int numberOfReceive) {
        this.numberOfReceive = numberOfReceive;
    }

    public boolean isJudge() {
        return judge;
    }

    public void setJudge(boolean judge) {
        this.judge = judge;
    }

    public int getJudge_creatLink() {
        return judge_creatLink;
    }

    public void setJudge_creatLink(int judge_creatLink) {
        this.judge_creatLink = judge_creatLink;
    }

    public int getJudge_startSetting() {
        return judge_startSetting;
    }

    public void setJudge_startSetting(int judge_startSetting) {
        this.judge_startSetting = judge_startSetting;
    }

    public int getJudge_endSetting() {
        return judge_endSetting;
    }

    public void setJudge_endSetting(int judge_endSetting) {
        this.judge_endSetting = judge_endSetting;
    }

    public int getJudge_startCollect() {
        return judge_startCollect;
    }

    public void setJudge_startCollect(int judge_startCollect) {
        this.judge_startCollect = judge_startCollect;
    }

    public int getJudge_suspendCollect() {
        return judge_suspendCollect;
    }

    public void setJudge_suspendCollect(int judge_suspendCollect) {
        this.judge_suspendCollect = judge_suspendCollect;
    }

    public int getJudge_continueCollect() {
        return judge_continueCollect;
    }

    public void setJudge_continueCollect(int judge_continueCollect) {
        this.judge_continueCollect = judge_continueCollect;
    }

    public int getJudge_stopCollect() {
        return judge_stopCollect;
    }

    public void setJudge_stopCollect(int judge_stopCollect) {
        this.judge_stopCollect = judge_stopCollect;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    // byte转为char
    public static char[] byteToChar(byte[] bytes) {
        Charset charset = Charset.forName("ISO-8859-1");
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        CharBuffer charBuffer = charset.decode(byteBuffer);
        return charBuffer.array();
    }


    /**
     * 根据字节数组，输出对应的格式化字符串
     * @param bytes 字节数组
     * @return 字节数组字符串
     */
    public static String printBytesByStringBuilder(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();

        for (byte aByte : bytes) {
            stringBuilder.append(byte2String(aByte));
        }

        return stringBuilder.toString();

    }

    public static String byte2String(byte b){

        return String.format("%02x ",b);
    }

    public static int bytesToInt(byte[] a){
        int ans=0;
        for(int i=0;i<4;i++){
            ans<<=8;
            ans|=(a[3-i]&0xff);
        }
        return ans;
    }
}