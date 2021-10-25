package com.example.customizingViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.ladarmonitor.R;

/**
 * Created by inks on 2018/9/21 0021.
 */

public class MyBatterView extends View {

    private Paint mPaint;//文字的画笔
    private Paint mBatteryPaint;//电池画笔
    private Paint mPowerPaint;//电量画笔
    private float mBatteryStroke = 5;//电池框宽度

    private RectF mBatteryRect;//电池矩形
    private RectF mCapRect;//电池盖矩形
    private RectF mPowerRect;//电量矩形

    public static Typeface typeFace;
    private int specWidthSize, specHeightSize;
    private float textSize;
    private int batteryColor;//电池框颜色
    private int powerColor;//电量颜色
    private int lowPowerColor;//低电颜色

    private int textColor;

    private boolean isShowText;

    private int power = -1;//当前电量（满电100）

    private float textWidth = 0;//电量文字长度
    private float mCapWidth;


    public void setPro(int power) {
        if (power==-1){
            power=-1;
        }else if (power < 0) {
            power = 0;
        } else if (power > 100) {
            power = 100;
        }
        this.power = power;
        invalidate();
    }


    public MyBatterView(Context context) {
        this(context, null);
    }


    public MyBatterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MyBatterView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    public MyBatterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/" + Datas.logoFont);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyBatteryView);
        textSize = typedArray.getDimension(R.styleable.MyBatteryView_batteryTextSize, 22);
        batteryColor = typedArray.getColor(R.styleable.MyBatteryView_batteryColor, Color.argb(255, 150, 150, 150));
        powerColor = typedArray.getColor(R.styleable.MyBatteryView_powerColor, Color.argb(255, 0, 255, 0));
        lowPowerColor = typedArray.getColor(R.styleable.MyBatteryView_lowPowerColor, Color.argb(255, 255, 0, 0));
        textColor = typedArray.getColor(R.styleable.MyBatteryView_textColor,Color.argb(255,0,0,0));

        isShowText = typedArray.getBoolean(R.styleable.MyBatteryView_showText, false);
        mCapWidth = typedArray.getDimension(R.styleable.MyBatteryView_mCapWidth, 20);
        typedArray.recycle();
        initPaint();

    }

    public void initPaint() {
        /**
         * 设置电池画笔
         */
        mBatteryPaint = new Paint();
        mBatteryPaint.setColor(batteryColor);
        mBatteryPaint.setAntiAlias(true);
        mBatteryPaint.setStyle(Paint.Style.STROKE);
        mBatteryPaint.setStrokeWidth(mBatteryStroke);
        /**
         * 设置电量画笔
         */
        mPowerPaint = new Paint();
        mPowerPaint.setAntiAlias(true);
        mPowerPaint.setStyle(Paint.Style.FILL);

        /**
         * 设置文字画笔
         */
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        specWidthSize = MeasureSpec.getSize(widthMeasureSpec);//宽
        specHeightSize = MeasureSpec.getSize(heightMeasureSpec);//高

//        L.e("specWidthSize:"+specWidthSize);
//        L.e("specHeightSize:"+specHeightSize);
        setMeasuredDimension(specWidthSize, specHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (power==-1){
            mPowerPaint.setColor(0);
            mPaint.setColor(0);
        }
        else if (power <= 20) {
            mPowerPaint.setColor(lowPowerColor);
            mPaint.setColor(textColor);
        } else {
            mPowerPaint.setColor(powerColor);
            mPaint.setColor(textColor);
        }
        if (isShowText) {
            String textString = power + "%";
            Rect textRect = new Rect();
            mPaint.getTextBounds(textString, 0, textString.length(), textRect);
            textWidth = textRect.width();
//            L.e("textWidth："+textWidth);
            float textHeight = textRect.height();
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float top = fontMetrics.top;//为基线到字体上边框的距离
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
            int baseLineY = (int) (specHeightSize / 2 - top / 2 - bottom / 2);//基线中间点的y轴计算公式
            // canvas.drawText(textString, (int) (width*0.5), baseLineY, mPaint);
            //(-5  距离最右边5)
            canvas.drawText(textString, specWidthSize - textWidth , baseLineY, mPaint);

        } else {
            textWidth = 0;
        }

        /**
         * 设置电池矩形
         * 2 间隔距离
         */
        mBatteryRect = new RectF(2, 2, specWidthSize - textWidth - 10 - mCapWidth, specHeightSize - 4);
        /**
         * 设置电池盖矩形
         */
        mCapRect = new RectF(specWidthSize - textWidth - 10 - mCapWidth, (specHeightSize - 2) * 0.25f, specWidthSize - textWidth - 10,
                (specHeightSize - 4) * 0.75f);
        /**
         * 设置电量矩形
         */
        float right;
        if (power < 20) {
            right = (specWidthSize - textWidth - 10 - mCapWidth - 2) / 100.0f * 20 - 2;

        } else {
            right = (specWidthSize - textWidth - 10 - mCapWidth - 2) / 100.0f * power - 2;
        }
        mPowerRect = new RectF(mBatteryStroke + 2, 2 + mBatteryStroke, right, specHeightSize - (2 + mBatteryStroke) - 2);


        canvas.drawRoundRect(mBatteryRect, 5, 5, mBatteryPaint);
        canvas.drawRoundRect(mCapRect, 5, 5, mBatteryPaint);// 画电池盖
        canvas.drawRoundRect(mPowerRect, 5, 5, mPowerPaint);// 画电量


    }

}