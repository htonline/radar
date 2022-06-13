package com.example.customizingViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.interfaces.OnUpActionBackgrdListener;

public class BackgrdCurveView extends View{
	private static int is512Or1024 = 512;

	public static int getIs512Or1024() {
		return is512Or1024;
	}

	public static void setIs512Or1024(int is512Or1024) {
		BackgrdCurveView.is512Or1024 = is512Or1024;
	}

	public BackgrdCurveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}
	public BackgrdCurveView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	public BackgrdCurveView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
	public static int Const_NumberOfVerticalDatas=512;
	//矩形中心的点
	private float bRaw[]=new float [17];
	private Paint paint=null;
	private int ySpace;
	private int width;
	boolean isDown=false;

	public int [] backgrdData=new int [Const_NumberOfVerticalDatas];
	private OnUpActionBackgrdListener mUp=null;
	
	
	public void setOnUpActionnBackgrdListener(OnUpActionBackgrdListener mUp) {
		this.mUp = mUp;
	}

	public int[] getBackgrdData() {
		return backgrdData;
	}

	public void setBackgrdData(int[] backgrdData) {
		this.backgrdData = backgrdData;
	}

	public float[] getbRaw() {
		return bRaw;
	}
	public void setbRaw(float[] bRaw) {
		this.bRaw = bRaw;
		if (is512Or1024 == 512){
			if (Const_NumberOfVerticalDatas != 512){
				Const_NumberOfVerticalDatas = 512;
				backgrdData = new int[Const_NumberOfVerticalDatas];
			}
		}else {
			if (Const_NumberOfVerticalDatas != 1024){
				Const_NumberOfVerticalDatas = 1024;
				backgrdData = new int[Const_NumberOfVerticalDatas];
			}
		}
		invalidate();
		calculateBackData(bRaw);
		mUp.onUp(backgrdData,bRaw);
	}
	public void init(){
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xffffffff);
	
	}
	public void refresh(){
		invalidate();
		calculateBackData(bRaw);
		mUp.onUp(backgrdData, bRaw);

	}
	public void returnBackData(float[] bRaw, int[] backgrdData){
		if (backgrdData.length == 512){
			if (Const_NumberOfVerticalDatas != 512){
				Const_NumberOfVerticalDatas = 512;
				this.backgrdData = new int[Const_NumberOfVerticalDatas];
			}
		}else {
			if (Const_NumberOfVerticalDatas != 1024){
				Const_NumberOfVerticalDatas = 1024;
				this.backgrdData = new int[Const_NumberOfVerticalDatas];
			}
		}
		this.bRaw = bRaw;
		this.backgrdData = backgrdData;
		calculateBackData(bRaw);
		invalidate();
		mUp.onUp(this.backgrdData,bRaw);
	}
	
	//计算每个点的坐标
	public void calculateBackData(float bRaw[]){
		if (is512Or1024 == 512){
			if (Const_NumberOfVerticalDatas != 512){
				Const_NumberOfVerticalDatas = 512;
				backgrdData = new int[Const_NumberOfVerticalDatas];
			}
		}else {
			if (Const_NumberOfVerticalDatas != 1024){
				Const_NumberOfVerticalDatas = 1024;
				backgrdData = new int[Const_NumberOfVerticalDatas];
			}
		}
		for (int i = 0; i < 16; i++) {
			if (bRaw[i]==bRaw[i+1]) {
				for (int j = 0; j < 32; j++) {
					backgrdData[32*i+j]=(int) ((bRaw[i]+20)/30);
				}
			}else {				
				float k=(ySpace/(bRaw[i+1]-bRaw[i]));
				float b=(ySpace*(i+1)-k*(bRaw[i+1]+20));
				for (int j = 0; j < 32; j++) {
					if (j==0) {
						backgrdData[32*i]=(int) ((bRaw[i]+20)/30);
					}else {
						backgrdData[32*i+j]=(int)((i*ySpace+j*ySpace/32-b)/k/30);
					}
				}
			}
		}


	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isDown=true;
			
			break;
		case MotionEvent.ACTION_MOVE:
			for (int i = 0; i < 17; i++) {
				if (i==0) {
					if (event.getY()>=ySpace*i&&event.getY()<=ySpace*i+10) {
						if (event.getX()<0) {
							bRaw[i]=10;
						}else if (event.getX()+20>=width) {
							bRaw[i]=width-10;
						}else {							
							bRaw[i]=event.getX()+10;
						}
					}
				}else if (event.getY()>=ySpace*i-10&&event.getY()<=ySpace*i+10) {
					if (event.getX()<0) {
						bRaw[i]=10;
					}else if (event.getX()+20>=width) {
						bRaw[i]=width-10;
					}else {
						bRaw[i]=event.getX()+10;
					}
				}
				invalidate();
				calculateBackData(bRaw);
				mUp.onUp(backgrdData,bRaw);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isDown) {
				for (int i = 0; i < 17; i++) {
					if (i==0) {
						if (event.getY()>=ySpace*i&&event.getY()<=ySpace*i+10) {
							if (event.getX()<0) {
								bRaw[i]=10;
							}else if (event.getX()+20>=width) {
								bRaw[i]=width-10;
							}else {	
								bRaw[i]=event.getX()+10;
							}
							break;
						}
					}else if (event.getY()>=ySpace*i-10&&event.getY()<=ySpace*i+10) {
						if (event.getX()<0) {
							bRaw[i]=10;
						}else if (event.getX()+20>=width) {
							bRaw[i]=width-10;
						}else {
							bRaw[i]=event.getX()+10;
						}
						break;
					}
					
				}
				invalidate();
				calculateBackData(bRaw);
				mUp.onUp(backgrdData,bRaw);
			}
			isDown=false;
			break;
		default:
			break;
		}
		
		return true;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		ySpace=getHeight()/16;
		width=getWidth();
		for (int i = 0; i < 17; i++) {
			if (i==0) {
				//画第一个小方格
				canvas.drawRect(bRaw[i]-10, 0, bRaw[i]+10, i*ySpace+10, paint);
			}else {
				//画剩下的小方格
				canvas.drawRect(bRaw[i]-10, i*ySpace-10, bRaw[i]+10, i*ySpace+10, paint);
			}
		}
		for (int i = 0; i < 16; i++) {
			//画小方格间的线
			canvas.drawLine(bRaw[i], i*ySpace, bRaw[i+1], (i+1)*ySpace, paint);
		}
	}

}
