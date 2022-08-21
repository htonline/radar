package com.example.uploadmodule.helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class SaveData {
	private String path;
	public static final int Const_NumberOfVerticalDatas=512;
	public short colorGap[]=new short [Const_NumberOfVerticalDatas];
	
	
	
	public SaveData(String path) {
		super();
		this.path = path;
	}


	public void printTitle() throws Exception {    
       FileOutputStream fos=null;
       try {
		fos=new FileOutputStream(path);
       } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
       }
       BufferedOutputStream bos=new BufferedOutputStream(fos);
       short sig=(short)0x5555;
       int offeset=688;
       int trace_num=1;
       byte reverse []=new byte [100];
       try {
		bos.write(shortToByte(sig));
		bos.write(offeset);
		bos.write(trace_num);
		bos.write(reverse);
		bos.flush();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		bos.close();
	}
    }    
	
	
	public void printData(short colorGap[] ) throws Exception{
		this.colorGap=colorGap;
		File file=new File(path);
		RandomAccessFile raf=new RandomAccessFile(file, "rw");
		raf.seek(file.length());
		raf.writeFloat(0);
		raf.writeFloat(0);
		raf.writeFloat(0);
		raf.write(0);
		for (int i = 0; i < colorGap.length; i++) {
			raf.writeShort(colorGap[i]);
		}
		raf.close();
	}
	
	
	/**  
	    * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在后，高位在前)的顺序。 和bytesToInt（）配套使用 
	    * @param value  
	    *            要转换的int值 
	    * @return byte数组 
	    */    
	
	public static byte[] intToBytes( int value )   
	{   
	    byte[] src = new byte[4];  
	    src[3] =  (byte) ((value>>24) & 0xFF);  
	    src[2] =  (byte) ((value>>16) & 0xFF);  
	    src[1] =  (byte) ((value>>8) & 0xFF);    
	    src[0] =  (byte) (value & 0xFF);                  
	    return src;   
	}  
	
	/*将int转为低字节在前，高字节在后的byte数组 
	b[0] = 11111111(0xff) & 01100001 
	b[1] = 11111111(0xff) & (n >> 8)00000000 
	b[2] = 11111111(0xff) & (n >> 8)00000000 
	b[3] = 11111111(0xff) & (n >> 8)00000000 
	*/  
	public byte[] IntToByteArray(int n) {    
	        byte[] b = new byte[4];    
	        b[0] = (byte) (n & 0xff);    
	        b[1] = (byte) (n >> 8 & 0xff);    
	        b[2] = (byte) (n >> 16 & 0xff);    
	        b[3] = (byte) (n >> 24 & 0xff);    
	        return b;    
	}  
	
	public static byte[] shortToByte(short number) { 
        int temp = number; 
        byte[] b = new byte[2]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Integer(temp & 0xff).byteValue();
            //将最低位保存在最低位 
            temp = temp >> 8; // 向右移8位 
        } 
        return b; 
    } 
	
	
	
	
	
	 public static byte[] toByteArray(short[] src) {

	        int count = src.length;
	        byte[] dest = new byte[count << 1];
	        for (int i = 0; i < count; i++) {
	                dest[i * 2] = (byte) (src[i] >> 8);
	                dest[i * 2 + 1] = (byte) (src[i] >> 0);
	        }

	        return dest;
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

}