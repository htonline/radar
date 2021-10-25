package com.example.orders;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import android.util.Log;

public class NormalOrders {
	private byte []buf=new byte[4];
	DatagramPacket dp=null;
	
	public NormalOrders() {
		super();
	
	}

	public void send(byte buf[],DatagramSocket ds) throws Exception{
		this.buf=buf;
		dp=new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.0.150"),8081);
		ds.send(dp);
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
	
}
