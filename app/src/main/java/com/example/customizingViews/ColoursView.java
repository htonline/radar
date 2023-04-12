package com.example.customizingViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

public class ColoursView extends View{
	private static final String TAG = "ColoursView";
	public ColoursView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		init();
		// TODO Auto-generated constructor stub
	}

	public ColoursView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ColoursView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	//数据源,里边的值是int型16进制数
//	private ArrayList<Integer> coloursDataList=null;
	//笔，用来画点
	private Paint pointPaint=null;
	//笔，用来绘制纵轴文字
	private TextPaint yTextPaint=null;
	//笔，用来绘制Y轴坐标
	private Paint yPaint=null;

	public static int getTimeWindow() {
		return timeWindow;
	}

	public static void setTimeWindow(int timeWindow) {
		ColoursView.timeWindow = timeWindow;
	}

	private static int timeWindow=25;


	public int viewWidth=1200;
	public int viewHeight=732;
	public int drawWidth;
	public int drawHeight;
	public float scale;
	private Bitmap bitmap=null;
	private int currentVertical=0;

	//总的数据List
//	private ArrayList<ArrayList<Integer>> listOfAllData=null;

	//定义行间距 line
	private float floatl=0;
	//定义列间距 arrange
	private int inta=0;
	//预留Y轴提示信息宽度
	private int intY=0;
	//预留X轴提示信息高度
	private int intX=0;
	//横轴坐标线长度
	private int intXx=0;
	//随机数(测试数据用)
	Random random=null;


	//初始化
	private void init(){
		pointPaint=new Paint();
		pointPaint.setAntiAlias(true);
		yTextPaint=new TextPaint();
		yTextPaint.setAntiAlias(true);
		yPaint=new Paint();
		yPaint.setAntiAlias(true);
		yPaint.setColor(0xff888888);
//		coloursDataList=new ArrayList<Integer>();
//		listOfAllData=new ArrayList<ArrayList<Integer>>();

//		floatl=getHeight()/25;
		inta=1;
		intY=50;
		intX=25;
		intXx=10;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		viewWidth=getWidth();
		viewHeight=getHeight();
		init();
		drawHeight=viewHeight;
		drawWidth= viewWidth;
		if (drawHeight/512.0>1) {
			scale=(float) (512.0/drawHeight);
		}else {
			scale=(float) (drawHeight/512.0);
		}
		bitmap=Bitmap.createBitmap(drawWidth,drawHeight, Bitmap.Config.ARGB_8888);
		//填充颜色
		bitmap.eraseColor(Color.parseColor("#FFFFFF"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(bitmap, 0, 0, pointPaint );
	}

	public void drawNewVertical(int[] colorList){
		for(int i=0;i<drawHeight;i++){
			bitmap.setPixel(currentVertical,i,colorList[(int) (i*scale)]);
			if (currentVertical<drawWidth-2) {
				bitmap.setPixel(currentVertical+1,i,0x0000FF);
			}
		}
		currentVertical++;
		if(currentVertical>=drawWidth){
			currentVertical=0;
		}
		this.invalidate();
	}

    public void drawNewVertical1024(int[] colorList) {
		if (drawHeight/1024.0>1) {
			scale=(float) (1024.0/drawHeight);
		}else {
			scale=(float) (drawHeight/1024.0);
		}
		for(int i=0;i<drawHeight;i++){

			bitmap.setPixel(currentVertical,i,colorList[(int) (i*scale)]);
			if (currentVertical<drawWidth-2) {
				bitmap.setPixel(currentVertical+1,i,0x0000FF);
			}
		}
		currentVertical++;
		if(currentVertical>=drawWidth){
			currentVertical=0;
		}
		this.invalidate();
    }

	public synchronized int getCurrentVertical() {
		return currentVertical;
	}
}
