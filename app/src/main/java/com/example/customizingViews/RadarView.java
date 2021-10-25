package com.example.customizingViews;

import java.util.Arrays;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RadarView extends View{

	public RadarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}
	public RadarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	public RadarView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
	
	private Paint pointPaint=null;
	public static final int Const_height=512;
	public static final int Const_numberOfDatas=512;
	private short colorData[]=new short [Const_numberOfDatas];
	//小横线长度
	private int intXx=15; 
	int h;
	int w;
	int mid;
	//y轴间距
	float spacing;
	//最后画的点的横坐标
	int xRawData[]=new int[Const_numberOfDatas];

	public short[] getColorData() {
		return colorData;
	}
	public void setColorData(short[] colorData) {
		this.colorData = colorData;
		invalidate();
	}


	private void init(){
		pointPaint=new Paint();
		pointPaint.setAntiAlias(true);
		pointPaint.setColor(Color.BLACK);

	}
	
	public void upDataXRawData(){
		for (int i = 0; i < Const_numberOfDatas; i++) {
			if (mid+colorData[i]>=w) {
				xRawData[i]=w;
			}else if (mid+colorData[i]<=0) {
				xRawData[i]=0;
			}else {
				xRawData[i]=mid+colorData[i];
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
//		canvas.drawText(h+"", 200, 200, pointPaint);
//		canvas.drawText(w+"", 200, 400, pointPaint);
		
		h=getHeight();
		w=getWidth();
		mid=w/2;
		spacing=(float) (h/10.0);
		//绘制小横线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(0, spacing*i, intXx, spacing*i, pointPaint);
		}
		for (int i = 0; i < Const_numberOfDatas; i++) {
			if (mid+colorData[i]>=w) {
				xRawData[i]=w;
			}else if (mid+colorData[i]<=0) {
				xRawData[i]=0;
			}else {
				xRawData[i]= (int) (mid+colorData[i]);
			}
		}
		//绘制增益曲线
		for (int i = 0; i < Const_numberOfDatas-1; i++) {
			canvas.drawLine(xRawData[i], i*h/Const_numberOfDatas, xRawData[i+1], (i+1)*h/Const_numberOfDatas, pointPaint);
		}
	}
	
	

}
