package com.example.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import android.R.integer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.helper.Complex;
import com.example.helper.FFT;

public class JudgeMetalThread implements Runnable {

	public static final int Const_NumberOfRow=512;
//	public static final int Const_NumberOfColumn=10;
//	private double data [][]=new double [Const_NumberOfRow][Const_NumberOfColumn];
	private short colorGap []=new short[Const_NumberOfRow];
	//金属数据极值，分别在excel第36、48、57行
	private double extremum_Metal[]={8153.26072435028,-14053.2808683853,12025.3976620395};
	//非金属数据极值，分别在excel第36、47、57行
	private double extremum_Nonmetal[]={8092.60999039385,-11566.9932756964,4500.12722809265};
	//数据的前三个极值
	private double extremum_NewDatas[]=new double[7];
	//记录极值更接近于金属还是非金属,2为金属，1为非金属
	private int tars[]=new int [3];
	//归一化后的数据
	private double oneColumnDatas[]=new double [Const_NumberOfRow];
	//对512个点微分及求斜率
	private double slope[]=new double[Const_NumberOfRow-1];
	private int judge[]=new int[50];
	private double judge_extremum3[]=new double[50];
//	private int judge_NumberOfData=0;
	private int judge_NumberOfNewDatas=0;
	private int judge_Datas=0;
	private boolean judge_end=false;
	boolean t1;
	boolean t2;
	boolean t3;
	boolean t4;
	//如果距离金属近变为1，否则加1，大于50则认为是非金属
	private int judge_MetalOrNot=0;
	public Thread t;
	private Handler handler=null;
	Message message=null;
	private double extremum[]=new double[20];
	private int index[]=new int[20];
	
	
//	File file = null; 
//    OutputStreamWriter osw=null;
//    BufferedWriter bw=null;
    private double text[]=new double[512];
	
	public JudgeMetalThread(Handler handler) {
		super();
		this.handler=handler;
		init();
	}
	
	public void init(){
//		file = new File("/sdcard/datas/contrast.txt");
//		try {
//			osw=new OutputStreamWriter(new FileOutputStream(file),"utf-8");
//			bw=new BufferedWriter(osw);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public short[] getColorGap() {
		return colorGap;
	}


	public void setColorGap(short[] colorGap) {
		this.colorGap = colorGap;
		for (int i = 0; i < Const_NumberOfRow; i++) {
			oneColumnDatas[i]=colorGap[i];
//			text[i]+=colorGap[i];
//			Log.e("^^^^^^^^^^^^^^^", "第"+(judge_NumberOfNewDatas+1)+"道数据的第"+(i+1)+"个数据是"+oneColumnDatas[i]);
		}
//		if (judge_NumberOfNewDatas==0) {
//			for (int i = 0; i < Const_NumberOfRow; i++) {
//				text[i]/=1;
//				try {
//					bw.write(text[i]+"");
//					bw.write("\r\n");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			try {
//				bw.flush();
//				bw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Log.e("^^^^^^^^^^^^^", "完成");
//		}
		
		for (int i = 0; i < Const_NumberOfRow-1; i++) {
			slope[i]=oneColumnDatas[i+1]-oneColumnDatas[i];
		}
		judge_NumberOfNewDatas++;
	}

	public boolean isJudge_end() {
		return judge_end;
	}

	public void setJudge_end(boolean judge_end) {
		this.judge_end = judge_end;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!judge_end) {
			if (judge_NumberOfNewDatas!=judge_Datas) {
				int t=0;
				int n=0;
				for (int i = 0; i < Const_NumberOfRow; i++) {
					if (Math.abs(oneColumnDatas[i])>3000) {
						n=i;
						break;
					}
				}
				for (int i = n; i < Const_NumberOfRow-3; i++) {
					t1=slope[i-2]*slope[i+2]<=0?true:false;
					t2=slope[i-1]*slope[i+1]<=0?true:false;
					t3=slope[i-2]*slope[i-1]>=0?true:false;
					t4=slope[i-1]*slope[i]>=0?true:false;
					if (t1&&t2&&t3&&t4&&t<7) {
						extremum_NewDatas[t]=oneColumnDatas[i+1];
							
						if (t==6||t==0) {
							
//						Log.e("%%%%%%%%%%%%%%%", "第"+judge_NumberOfNewDatas+"道数据的第"+(t+1)+"个极值是第"+(i+2)+"个数据的"+extremum_NewDatas[t]);
						}
					
						
//						index[t]+=i+2;
						
//						if (t==2) {
////							Log.e("$$$$$$$$$$$$$", extremum_NewDatas[2]+"");
//							if (extremum_NewDatas[2]>4000&&extremum_NewDatas[2]<4800) {
//								judge_extremum3[(judge_NumberOfNewDatas-1)%50]=1;
//							}else {
//								judge_extremum3[(judge_NumberOfNewDatas-1)%50]=-1;
//							}
//							
//						}
//						if (t==4) {
//							if (extremum_NewDatas[4]<1500&&(i+2)>80&&(i+2)<=85) {
//								judge[(judge_NumberOfNewDatas-1)%50]=-1;
//							}else if(extremum_NewDatas[4]>1500){
//								judge[(judge_NumberOfNewDatas-1)%50]=1;
//							}else {
//								judge[(judge_NumberOfNewDatas-1)%50]=0;
//							}
////							Log.e("&&&&&&&&&&&&&&&&", "judge["+judge_NumberOfNewDatas+"]="+judge[judge_NumberOfNewDatas%50]+"");
//						}
						
//						if (t==6) {
//							if (extremum_NewDatas[6]<1800&&extremum_NewDatas[6]>-800) {
//								if (i+2==98||i+2==99||i+2==100) {
//									judge[(judge_NumberOfNewDatas-1)%50]=1;
//								}else {
//									judge[(judge_NumberOfNewDatas-1)%50]=-1;									
//								}
//							}else if (extremum_NewDatas[6]>1800&&(i+2)!=95&&(i+2)!=96&&(i+2)!=97) {
//								judge[(judge_NumberOfNewDatas-1)%50]=1;								
//							}else {
//								judge[(judge_NumberOfNewDatas-1)%50]=0;		
//							}							
//						}
						
						if (t==6) {
							if (extremum_NewDatas[6]<1800&&extremum_NewDatas[6]>-800) {
								if (i+2==98||i+2==99||i+2==100||i+2==97||i+2==97) {
									judge[(judge_NumberOfNewDatas-1)%50]=1;
								}else {
									judge[(judge_NumberOfNewDatas-1)%50]=-1;									
								}
							}else {
								if (i+2==98||i+2==99||i+2==100||i+2==97||i+2==97) {
									judge[(judge_NumberOfNewDatas-1)%50]=-1;
								}else {
										judge[(judge_NumberOfNewDatas-1)%50]=1;
								}
							}
						}
						
						t++;
					}
				}
				
				//把20个极值求平均，以文件形式存在pad中
//				for (int i = 0; i < 20; i++) {
//					extremum[i]+=extremum_NewDatas[i];
//				}
//				if (judge_NumberOfNewDatas==10000) {
//					for (int i = 0; i < 20; i++) {
//						extremum[i]/=10000;
//						try {
//							bw.write(extremum[i]+"");
//							bw.write("\r\n");
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					for (int i = 0; i < 20; i++) {
//						index[i]/=10000;
//						try {
//							bw.write(index[i]+"");
//							bw.write("\r\n");
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					try {
//						bw.flush();
//						bw.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
				
				
				if (judge_NumberOfNewDatas%50==0) {
					int sum_juegeextremum7=0;
//					double sum_extremum3=0;
					for (int i = 0; i < 50; i++) {
						sum_juegeextremum7+=judge[i];
//						sum_extremum3+=judge_extremum3[i];
					}
//					Log.e("&&&&&&&&&&&", sum_juegeextremum5+"");
					message=Message.obtain();
					message.what=3;
					message.arg1=sum_juegeextremum7;
//					message.arg2=(int) sum_extremum3;
					handler.sendMessage(message);
				}
				judge_Datas++;
				t1=t2=t3=t4=false;
				
			}
		}
	}
	
	public void start(){     
        if(t==null){
            t=new Thread(this);
            t.start();
        }
    }

	public void stop(){
		setJudge_end(true);
	}
}
