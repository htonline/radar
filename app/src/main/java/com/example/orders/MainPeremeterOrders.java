package com.example.orders;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import android.R.integer;
import android.util.Log;

public class MainPeremeterOrders {
//	private char startFlag=0xcccc;
	private byte channelno=0;
	private short frequency;
	private short precision=512;
	private short m_time_wnd ;
//	private short m_step_val;
//	private short m_con_current ;
	private short delay_time_DELAY;
	private byte triggerpulsenum=10;
//	private byte [] filename=new byte [20];
	private float triggerdistance;
	
//	private char endFlag=0xdddd;
	private byte buf[]=new byte [12];
	public static final int TWND_PARA=275;
	DatagramSocket ds=null;
	DatagramPacket dp=null;
	//triggerInfo
	private short triggerInfo=0;
	//采样脉冲数
    private int numberOfPulse=0;
    //触发方式(测距轮or时间)
    private int triggerMode=-1;
    //触发方向
    private int triggerDirection=-1;
    
	
	
	public int getNumberOfPulse() {
		return numberOfPulse;
	}


	public void setNumberOfPulse(int numberOfPulse) {
		this.numberOfPulse = numberOfPulse;
	}


	public short getFrequency() {
		return frequency;
	}


	public void setFrequency(short frequency) {
		this.frequency = frequency;
	}

	public short getM_time_wnd() {
		return m_time_wnd;
	}


	public void setM_time_wnd(short m_time_wnd) {
		this.m_time_wnd = m_time_wnd;
	}

	
	public short getDelay_time_DELAY() {
		return delay_time_DELAY;
	}


	public void setDelay_time_DELAY(short delay_time_DELAY) {
		this.delay_time_DELAY = delay_time_DELAY;
	}


	public int getTriggerMode() {
		return triggerMode;
	}


	public void setTriggerMode(int triggerMode) {
		this.triggerMode = triggerMode;
	}


	public int getTriggerDirection() {
		return triggerDirection;
	}


	public void setTriggerDirection(int triggerDirection) {
		this.triggerDirection = triggerDirection;
	}


	public byte getTriggerpulsenum() {
		return triggerpulsenum;
	}


	public void setTriggerpulsenum(byte triggerpulsenum) {
		this.triggerpulsenum = triggerpulsenum;
	}


//	public byte[] getFilename() {
//		return filename;
//	}
//
//
//	public void setFilename(byte[] filename) {
//		this.filename = filename;
//		for (int i = 0; i < 20; i++) {
//			if (i<=filename.length-1) {
//				buf[20+i]=filename[i];
//			}else {
//				buf[20+i]=0;
//			}
//		}
//	}


	public float getTriggerdistance() {
		return triggerdistance;
	}


	public void setTriggerdistance(float triggerdistance) {
		this.triggerdistance = triggerdistance;
	}


	public MainPeremeterOrders(DatagramSocket ds) {
		this.ds=ds;
		buf[0]=(byte) 0xbb;
		buf[1]=(byte) 0xbb;
		buf[4]=0x00;
		buf[5]=0x02;
		
	}


//	public void calculateTwoPeremeters(){
//		if (m_time_wnd <= TWND_PARA) {
//			m_con_current = (short) 0xc800;
//			double minTstep = TWND_PARA*1.0*1000/65536;
//			m_step_val = (short)((m_time_wnd*1000/256)/minTstep + 0.999);
//		}else if(m_time_wnd <= 2560)
//		{
//			m_con_current = (short)(TWND_PARA*0xc800/m_time_wnd);
//			m_step_val = (short)(65536.0/256 + 0.999);
//		}
//
//	}
	
	public void calculateTriggerInfo(){
		if (triggerMode==0) {
			buf[11]=0x00;
		}else {
			if(triggerMode==1&&triggerDirection==1)
				buf[11]=0x11;
			else if(triggerMode==1&&triggerDirection==0)
				buf[11]=0x10;
			else
				buf[11]=0x21;
		}
		buf[10]=(byte) triggerpulsenum;

	}

	public void send() {
//		calculateTwoPeremeters();
		calculateTriggerInfo();
		buf[2]=putShort(frequency)[0];
		buf[3]=putShort(frequency)[1];
		buf[6]=putShort(m_time_wnd)[1];
		buf[7]=putShort(m_time_wnd)[0];
//		buf[10]=putShort(m_step_val)[1];
//		buf[11]=putShort(m_step_val)[0];		
//		buf[12]=putShort(m_con_current)[1];
//		buf[13]=putShort(m_con_current)[0];
		buf[8]=putShort(delay_time_DELAY)[1];
		buf[9]=putShort(delay_time_DELAY)[0];
//		buf[16]=triggermode;
//		buf[17]=validdirection;
//		buf[18]=triggerpulsenum;
//		buf[19]=0x00;
//		buf[40]=getByteArray(triggerdistance)[0];
//		buf[41]=getByteArray(triggerdistance)[1];
//		buf[42]=getByteArray(triggerdistance)[2];
//		buf[43]=getByteArray(triggerdistance)[3];
		try {
			dp=new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.0.150"),8081);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ds.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//short转byte数组
	public static byte[] putShort(short s) {
	    byte b []=new byte[2];
        b[0] = (byte) (s >> 8);  
        b[1] = (byte) (s >> 0);
        return b;
    }  
	
	
	//int 转byte数组
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		
		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}
	
	public static byte[] getByteArray(float f) {  
        int intbits = Float.floatToIntBits(f);//将float里面的二进制串解释为int整数  
        return int2byte(intbits);  
    }  

}
