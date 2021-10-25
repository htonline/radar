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
	//标记有几个list集合
//	private int index=0;
	//判断应该替换还是添加(0添加，大于0替换)
//	private int judge=0;

//	//获取view宽高
//	private int viewHeight=getHeight();
//	private int viewWidth=getWidth();

//	public ArrayList<Integer> getList(){
//		return coloursDataList;
//	}
//
//	public void setList(ArrayList<Integer> coloursDataList){
//		Log.d("11111111111", "in");
//		this.coloursDataList=coloursDataList;
//		if (judge==0) {
//			listOfAllData.add(index,coloursDataList);
//			Log.d("2222222222", index+"");
//		}else {
//			listOfAllData.set(index,coloursDataList);
//		}
//		if (getIndex()==700) {
//			setIndex(0);
//			judge++;
//		}else
//			setIndex(++index);
//		invalidate();
//	}
//
//	public int getIndex() {
//		return index;
//	}
//
//	public void setIndex(int index) {
//		this.index = index;
//	}

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
		drawHeight=viewHeight-intX;
		drawWidth= (viewWidth-32);
		if (drawHeight/512.0>1) {
			scale=(float) (512.0/drawHeight);
		}else {
			scale=(float) (drawHeight/512.0);
		}
		bitmap=Bitmap.createBitmap(drawWidth,drawHeight, Bitmap.Config.RGB_565);
		//填充颜色
		bitmap.eraseColor(Color.parseColor("#FFFFFF"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//获取上下左右边距
//		int pl=getPaddingLeft();
//		int pr=getPaddingRight();
//		int pt=getPaddingTop();
//		int pb=getPaddingBottom();


		floatl=(viewHeight-intX)/25.0f;

		//绘制Y轴和X轴
		for (int i = 0; i <= timeWindow; i++) {
			if (i%(timeWindow/5)==0) {
				if (i!=timeWindow) {
					//带数字的横线

					canvas.drawLine(0, viewHeight-(viewHeight-(viewHeight/timeWindow)*i)-intX, yTextPaint.measureText(2.0+"")+intXx+5, viewHeight-(viewHeight-(viewHeight/timeWindow)*i)-intX, yPaint);
				}else {
					//X轴
					canvas.drawLine(0, viewHeight-(viewHeight-(viewHeight/timeWindow)*i)-intX, viewWidth, viewHeight-(viewHeight-(viewHeight/timeWindow)*i)-intX, yPaint);
				}
				//绘制数字
				canvas.drawText(String.valueOf((double)(i)),0, viewHeight-(viewHeight-(viewHeight/timeWindow)*i)-intX, yTextPaint);

//				Log.d(TAG, viewHeight-(viewHeight-(viewHeight/timeWindow)*i)+"");
			}
//			Log.d(TAG, viewHeight+"viewHeight==================");
//			else if (i%(timeWindow/25)==0){
//				//最短的横线
//				canvas.drawLine(yTextPaint.measureText(2.0+"")+5, (viewHeight/timeWindow)*i, yTextPaint.measureText(2.0+"")+intXx+5, (viewHeight/timeWindow)*i, yPaint);
//			}
		}
		//Y轴
		canvas.drawLine(yTextPaint.measureText(2.0+"")+intXx+5, 0, yTextPaint.measureText(2.0+"")+intXx+5, viewHeight, yPaint);
		canvas.drawBitmap(bitmap, 32, 0, pointPaint );



		//height,25		canvas.drawText(intX+"", 200, 200, yPaint);
//		canvas.drawText(yTextPaint.measureText(2.0+"")+intXx+5+"", 200, 200, yPaint);


//		canvas.drawText(viewHeight-intX+"", 200, 200, yTextPaint);
//		canvas.drawText(viewWidth-yTextPaint.measureText(2.0+"")-intXx-5+"", 200, 400, yTextPaint);
//		for(int j=0;j<15;j++){
//			for (int i = 0; i < 512; i++) {
//				coloursDataList.add(i,random.nextInt(255));
//			}
//				listOfAllData.add(j, coloursDataList);
//			
//		}

//		for(int j=0;j<listOfAllData.size();j++){
//			
//			for (int i = 0; i < coloursDataList.size(); i++) {
//				pointPaint.setColor(Color.rgb(listOfAllData.get(j).get(i), listOfAllData.get(j).get(i), listOfAllData.get(j).get(i)));
//				canvas.drawPoint(yTextPaint.measureText(2.0+"")+intXx+5+2*j+1, i*2, pointPaint);
//				canvas.drawPoint(yTextPaint.measureText(2.0+"")+intXx+5+2*j+2, i*2, pointPaint);
//				canvas.drawPoint(yTextPaint.measureText(2.0+"")+intXx+5+2*j+1, i*2+1, pointPaint);
//				canvas.drawPoint(yTextPaint.measureText(2.0+"")+intXx+5+2*j+2, i*2+1, pointPaint);
//			}
//			
//		}	
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

}
