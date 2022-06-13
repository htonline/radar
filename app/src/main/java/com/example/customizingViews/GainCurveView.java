package com.example.customizingViews;

import java.util.Arrays;

import com.example.interfaces.OnUpActionListener;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GainCurveView extends View {
    private static final String TAG = "GainCurveView  =========================";
    private static int is512or1024 = 512;

    public GainCurveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        // TODO Auto-generated constructor stub
    }

    public static int getIs512or1024() {
        return is512or1024;
    }

    public static void setIs512or1024(int is512or1024) {
        GainCurveView.is512or1024 = is512or1024;
        if (is512or1024 == 512) {
            Const_NumberOfVerticalDatas = 512;
        } else {
            Const_NumberOfVerticalDatas = 1024;
        }
    }

    public GainCurveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public GainCurveView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public static int Const_NumberOfVerticalDatas = 512;
    //矩形中心的点
    private float xRaw[] = new float[17];
    private Paint paint = null;
    private int ySpace;
    private int width;
    boolean isDown = false;
    //增益数组
    public int[] gainData = new int[Const_NumberOfVerticalDatas];
    private OnUpActionListener mUp = null;


    public void setOnUpActionListener(OnUpActionListener mUp) {
        this.mUp = mUp;
    }

    public int[] getGainData() {
        return gainData;
    }

    public void setGainData(int[] gainData) {
        if (this.gainData.length != 512) {
            Const_NumberOfVerticalDatas = 512;
            gainData = new int[Const_NumberOfVerticalDatas];
        }
        this.gainData = gainData;
    }

    public void setGainData1024(int[] gainData) {
        if (this.gainData.length != 1024) {
            Const_NumberOfVerticalDatas = 512;
            gainData = new int[Const_NumberOfVerticalDatas];
        }
        this.gainData = gainData;
    }

    public void returnGainData(float[] xRaw, int[] gainData) {
        if (gainData.length == 512) {
            if (Const_NumberOfVerticalDatas != 512) {
                Const_NumberOfVerticalDatas = 512;
                this.gainData = new int[Const_NumberOfVerticalDatas];
            }
            this.xRaw = xRaw;
            this.gainData = gainData;
            calculateGainData(xRaw);
            invalidate();
            mUp.onUp(this.gainData, xRaw);
        }else{
            if (Const_NumberOfVerticalDatas != 1024) {
                Const_NumberOfVerticalDatas = 1024;
                this.gainData = new int[Const_NumberOfVerticalDatas];
            }
            this.xRaw = xRaw;
            this.gainData = gainData;
            calculateGainData(xRaw);
            invalidate();
            mUp.onUp1024(this.gainData, xRaw);
        }
    }


    public float[] getxRaw() {
        return xRaw;
    }
    public void refresh(){
        invalidate();
        calculateGainData(xRaw);
        if (gainData.length == 512) {
            mUp.onUp(gainData, xRaw);
        } else {
            mUp.onUp1024(gainData, xRaw);
        }
    }

    public void setxRaw(float[] mxRaw) {
        if (is512or1024 == 1024) {
            if (this.gainData.length != 1024) {
                Const_NumberOfVerticalDatas = 512;
                gainData = new int[Const_NumberOfVerticalDatas];
            }
        } else {
            if (this.gainData.length != 512) {
                Const_NumberOfVerticalDatas = 512;
                gainData = new int[Const_NumberOfVerticalDatas];
            }
        }
        this.xRaw = mxRaw;
        invalidate();
        calculateGainData(xRaw);
        if (gainData.length == 512) {
            mUp.onUp(gainData, xRaw);
        } else {
            mUp.onUp1024(gainData, xRaw);
        }
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xffffffff);

    }

    //计算每个点的坐标
    public void calculateGainData(float xRaw[]) {
        if (gainData.length != Const_NumberOfVerticalDatas) {
            gainData = new int[Const_NumberOfVerticalDatas];
        }
        int tempjiange = Const_NumberOfVerticalDatas / 16;


//        gainData[0]= (int) ((xRaw[0]+20)/30);
//
//        float[] tempgain = new float[512];
//        tempgain[0]= ((xRaw[0]+20)/30);
//        for (int i = 0;i<16;i++){
//            for (int j=0;j<32;j++){
//                if (i*32+j>0)tempgain[i*32+j]=(((xRaw[i+1]+20)/30-(xRaw[i]+20)/30))/32+tempgain[i*32+j-1];
//                else tempgain[0]= ((xRaw[0]+20)/30);
//            }
//        }
//        for (int z= 0 ;z<512;z++){
//            gainData[z] = (int)tempgain[z];
//        }


        for (int i = 0; i < 16; i++) {
            if (xRaw[i] == xRaw[i + 1]) {
                for (int j = 0; j < tempjiange; j++) {
                    gainData[tempjiange * i + j] = (int) ((xRaw[i] + 20) / 30);
                }
            } else {
                float k = (ySpace / (xRaw[i + 1] - xRaw[i]));
                float b = (ySpace * (i + 1) - k * (xRaw[i + 1] + 20));
                for (int j = 0; j < tempjiange; j++) {
                    if (j == 0) {
                        gainData[tempjiange * i] = (int) ((xRaw[i] + 20) / 30);
                    } else {
                        gainData[tempjiange * i + j] = (int) ((i * ySpace + j * ySpace / 32 - b) / k / 30);
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
                isDown = true;

                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < 17; i++) {
                    if (i == 0) {
                        if (event.getY() >= ySpace * i && event.getY() <= ySpace * i + 10) {
                            if (event.getX() < 0) {
                                xRaw[i] = 10;
                            } else if (event.getX() + 20 >= width) {
                                xRaw[i] = width - 10;
                            } else {
                                xRaw[i] = event.getX() + 10;
                            }
                        }
                    } else if (event.getY() >= ySpace * i - 10 && event.getY() <= ySpace * i + 10) {
                        if (event.getX() < 0) {
                            xRaw[i] = 10;
                        } else if (event.getX() + 20 >= width) {
                            xRaw[i] = width - 10;
                        } else {
                            xRaw[i] = event.getX() + 10;
                        }
                    }
                    invalidate();
                    calculateGainData(xRaw);
                    if (gainData.length == 512) {
                        mUp.onUp(gainData, xRaw);
                    } else {
                        mUp.onUp1024(gainData, xRaw);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDown) {
                    for (int i = 0; i < 17; i++) {
                        if (i == 0) {
                            if (event.getY() >= ySpace * i && event.getY() <= ySpace * i + 10) {
                                if (event.getX() < 0) {
                                    xRaw[i] = 10;
                                } else if (event.getX() + 20 >= width) {
                                    xRaw[i] = width - 10;
                                } else {
                                    xRaw[i] = event.getX() + 10;
                                }
                                break;
                            }
                        } else if (event.getY() >= ySpace * i - 10 && event.getY() <= ySpace * i + 10) {
                            if (event.getX() < 0) {
                                xRaw[i] = 10;
                            } else if (event.getX() + 20 >= width) {
                                xRaw[i] = width - 10;
                            } else {
                                xRaw[i] = event.getX() + 10;
                            }
                            break;
                        }

                    }
                    invalidate();

                    calculateGainData(xRaw);
                    if (gainData.length == 512) {
                        mUp.onUp(gainData, xRaw);
                    } else {
                        mUp.onUp1024(gainData, xRaw);
                    }
                }
                isDown = false;
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
        ySpace = getHeight() / 16;
        width = getWidth();
        for (int i = 0; i < 17; i++) {
            if (i == 0) {
                //画第一个小方格
                canvas.drawRect(xRaw[i] - 10, 0, xRaw[i] + 10, i * ySpace + 10, paint);
            } else {
                //画剩下的小方格
                canvas.drawRect(xRaw[i] - 10, i * ySpace - 10, xRaw[i] + 10, i * ySpace + 10, paint);
            }
        }
        for (int i = 0; i < 16; i++) {
            //画小方格间的线
            canvas.drawLine(xRaw[i], i * ySpace, xRaw[i + 1], (i + 1) * ySpace, paint);
        }
    }

}
