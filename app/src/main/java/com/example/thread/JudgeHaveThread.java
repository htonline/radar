package com.example.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.helper.MatrixOperation;

public class JudgeHaveThread implements Runnable{
	
	public static final int Const_NumberOfRow=512;
	public static final int Const_NumberOfColumn=10;
	//data数组相当于T矩阵
	private double data [][]=new double [Const_NumberOfRow][Const_NumberOfColumn];
	private short colorGap []=new short[Const_NumberOfRow];
	private int judge_NumberOfData=0;
	private boolean judge_end=false;
	private double b []={0.00582562206283736,0.00582346010220595,0.00576137097046081,0.00498692026331976,0.00266107421973731,-0.00196365096203424,-0.00924100859541960,-0.0188863708363877,-0.0298636172000837,-0.0404507610881519,
		    -0.0484909990679092,-0.0517908203395052,-0.0485879209079561,-0.0379885991739306,-0.0202733553318098,
		    0.00300813706131149,0.0291898512822791,0.0548751088916658,0.0764892721908147,0.0908952141978058,
		    0.0959506192433226,0.0908952141978058,0.0764892721908147,0.0548751088916658,0.0291898512822791,
		    0.00300813706131149,-0.0202733553318098,-0.0379885991739306,-0.0485879209079561,-0.0517908203395052,
		    -0.0484909990679092,-0.0404507610881519,-0.0298636172000837,-0.0188863708363877,-0.00924100859541960,
		    -0.00196365096203424,0.00266107421973731,0.00498692026331976,0.00576137097046081,0.00582346010220595,
		    0.00582562206283736};
	private double enhance[]=new double[512];
	private double T2[]=new double[Const_NumberOfRow+40];
	private double T_fir[][]=new double[Const_NumberOfRow][Const_NumberOfColumn];
	private double T_fir2[][]=new double[Const_NumberOfRow][Const_NumberOfColumn];
	private double rec;
	//相减之后的矩阵data_sub
	private double data_sub[][]=new double[Const_NumberOfRow][Const_NumberOfColumn];
	private double data_contrast[][]=new double[Const_NumberOfRow][Const_NumberOfColumn];
	private Context context=null;
	public Thread t;
	private Handler handler=null;
	Message message=null;
	
	public JudgeHaveThread(Context context,Handler handler) {
		super();
		this.context=context;
		this.handler=handler;
		init();
	}
	public short[] getColorGap() {
		return colorGap;
	}
	public void setColorGap(short[] colorGap) {
		this.colorGap = colorGap;
		for (int i = 0; i < Const_NumberOfRow; i++) {
			//对数据归一化处理
			data[i][judge_NumberOfData%10]=colorGap[i]/32768.0;		
		}
		judge_NumberOfData++;
	}
	
	public boolean isJudge_end() {
		return judge_end;
	}
	public void setJudge_end(boolean judge_end) {
		this.judge_end = judge_end;
	}

	public int getJudge_NumberOfData() {
		return judge_NumberOfData;
	}
	public void setJudge_NumberOfData(int judge_NumberOfData) {
		this.judge_NumberOfData = judge_NumberOfData;
	}

	public void init(){
		for (int i = 0; i < 40; i++) {
			T2[i]=0;
		}
		//增益矩阵
		for (int i = 0; i < Const_NumberOfRow; i++) {
			if (i<65) {
				enhance[i]=5;
			}else {
				enhance[i]=5+0.5*(i-64);
			}
		}
		
		//读取文件中的对照数据
		try {
			InputStream in=context.getAssets().open("contrast.txt");
			InputStreamReader read=new InputStreamReader(in, "GBK");
			BufferedReader bufferedReader=new BufferedReader(read);
			String string=null;
			int i=0;
			while ((string=bufferedReader.readLine())!=null) {
				//这个是对照数据，我把它放在txt格式的文件里，直接读出来
				data_contrast[i][0]=Double.parseDouble(string)/32768.0;
				data_contrast[i][1]=Double.parseDouble(string)/32768.0;
				data_contrast[i][2]=Double.parseDouble(string)/32768.0;
				data_contrast[i][3]=Double.parseDouble(string)/32768.0;
				data_contrast[i][4]=Double.parseDouble(string)/32768.0;
				data_contrast[i][5]=Double.parseDouble(string)/32768.0;
				data_contrast[i][6]=Double.parseDouble(string)/32768.0;
				data_contrast[i][7]=Double.parseDouble(string)/32768.0;
				data_contrast[i][8]=Double.parseDouble(string)/32768.0;
				data_contrast[i][9]=Double.parseDouble(string)/32768.0;
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < Const_NumberOfColumn; i++) {
			for (int n = 0; n < Const_NumberOfRow; n++) {
				T2[n+40]=data_contrast[n][i];
			}
			for (int j = 0; j < Const_NumberOfRow; j++) {
				double t_sum=0;
				for (int k = 0; k < 41; k++) {
					t_sum=t_sum+T2[j+40-k]*b[k];
//					Log.d("***************", "j+40-k"+(j+40-k)+" k"+k);
				}
				T_fir2[j][i]=t_sum;
			}
		}
		for (int i = 0; i < Const_NumberOfRow; i++) {
			for (int j = 0; j < Const_NumberOfColumn; j++) {
				T_fir2[i][j]=enhance[i]*T_fir2[i][j]*32767;
			}
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!judge_end) {
			if (judge_NumberOfData!=0&&judge_NumberOfData%10==9) {
				for (int i = 0; i < Const_NumberOfColumn; i++) {
					for (int n = 0; n < Const_NumberOfRow; n++) {
						T2[n+40]=data[n][i];
					}
					for (int j = 0; j < Const_NumberOfRow; j++) {
						double t_sum=0;
						for (int k = 0; k < 41; k++) {
							t_sum=t_sum+T2[j+40-k]*b[k];
//							Log.d("***************", "j+40-k"+(j+40-k)+" k"+k);
						}
						T_fir[j][i]=t_sum;
					}
				}
				for (int i = 0; i < Const_NumberOfRow; i++) {
					for (int j = 0; j < Const_NumberOfColumn; j++) {
						T_fir[i][j]=enhance[i]*T_fir[i][j]*32767;
					}
				}
				//高阶矩
				data_sub=MatrixOperation.sub(T_fir, T_fir2);
				double sum_end=0;
				for (int i = 0; i < Const_NumberOfRow/2; i++) {
					double sum_row=0;
					for (int j = 0; j < Const_NumberOfColumn ; j++) {
						sum_row+=data_sub[i][j];
					}
					sum_row/=Const_NumberOfColumn;
					sum_end+=Math.pow(sum_row, 4);
				}
				Log.e("-------------------",sum_end+"");
				
				message=Message.obtain();
				if (sum_end<1.3*Math.pow(10, 20)) {
					//没有检测到物体
					handler.sendEmptyMessage(5);
				}else {
					//检测到物体
					handler.sendEmptyMessage(4);				
				}
				
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
