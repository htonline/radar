package com.example.fragments;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.example.customizingViews.ColoursView;
import com.example.ladarmonitor.R;
import com.example.yolov5.Box;
import com.example.yolov5.BoxPeak;
import com.example.yolov5.YOLOv5;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.internal.operators.observable.ObservableIgnoreElements;

public class LeftFragmentOfMainActivity extends Fragment {
    private static final String TAG = "LeftFragmentOfMainActiv";
    private ColoursView coloursView = null;
    public static final int Const_NumberOfVerticalDatas = 512;
    private int colorList[] = new int[Const_NumberOfVerticalDatas];
    private ImageView iv_showdetect;
    //	private ImageView iv_showtestl;
    private TextView tv_showrebar;
    private Bitmap newbitmap;
    private List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_mainactivity_left, null);
        coloursView = (ColoursView) view.findViewById(R.id.coloursView);
        iv_showdetect = view.findViewById(R.id.imv_showdetect);
        tv_showrebar = view.findViewById(R.id.tv_showrebar);
//		iv_showtestl = view.findViewById(R.id.imv_testshow);
        list = new ArrayList<>();
        return view;
    }

    public void drawNewVertical(int colorList[]) {
        // TODO Auto-generated method stub

        coloursView.drawNewVertical(colorList);
    }

    public void drawLines(int num) {
        coloursView.setTimeWindow(num);
    }

    public void drawNewVertical1024(int[] colorList) {
        coloursView.drawNewVertical1024(colorList);
    }

    public void showDetect() {
        list.clear();
        int currentVertical = coloursView.getCurrentVertical();
        coloursView.buildDrawingCache();
        Bitmap bitmap = coloursView.getDrawingCache();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                从原始bitmap对象中创建一个新的nleftbit位图对象。该位图是从原始位图的左边界开始，直到当前垂直位置（currentVertical），与原始位图的高度相同。
                Bitmap nleftbit = Bitmap.createBitmap(bitmap, 0, 0, currentVertical, bitmap.getHeight());
                Bitmap nright;
                Bitmap result;
                List<BoxPeak> boxPeaks;
                // 如果当前垂直位置小于400, 就对图像进行空白填充, 使其大概满足YOLO输入图像640x640的要求;
                // YOLO会对图像进行拉伸, 如果宽高差距太大，拉伸之后，钢筋图像的检测绝对会不准确.所以对宽高差距太大的图像，需要做空白填充。
                if (currentVertical < 400) {
//                    通过Bitmap.createBitmap()方法从原始bitmap对象中创建一个新的nright位图对象。该新位图是从原始位图的当前垂直位置（currentVertical）的下一行开始，直到原始位图的底部，宽度为512 - 当前垂直位置 - 1。
                    nright = Bitmap.createBitmap(bitmap, currentVertical + 1, 0, 512 - currentVertical - 1, bitmap.getHeight());
//                    直达波去除
                    Bitmap chulibitmap = getChuli(nleftbit);
//                     将处理后的图片与空白图片进行合并，是图像宽度达到512？
                    result = mergeBitmap(chulibitmap, nright);
//                   用YOLOv5检测图片中的钢筋部分并进行裁剪, 并将裁剪后的图片再次用YOLOv5检测钢筋中的顶点部分.绘制
                    boxPeaks = detectAndDraw(result);
//                    通过Bitmap.createBitmap()方法从原始bitmap对象中创建一个新的halfbitmap位图对象。该位图是从原始位图的第513列开始，直到原始位图的右边界，与原始位图的高度相同。
                    Bitmap halfbitmap = Bitmap.createBitmap(bitmap, 513, 0, bitmap.getWidth() - 513, bitmap.getHeight());
//                    将处理后的result位图和halfbitmap位图进行合并，生成一个新的位图对象result。
                    result = mergeBitmap(result, halfbitmap);
//                    调用getTransparentBitmap()方法对result位图进行处理，使其具有透明效果。
                    Bitmap transparentBitmap = getTransparentBitmap(result, 00);
//                    创建transparentBitmap位图的副本copy。
                    Bitmap copy = transparentBitmap.copy(Bitmap.Config.ARGB_8888, true);
//                    对copy位图进行绘制操作，并将boxPeaks参数传递给该方法。绘制的结果存储在tempbitmap位图对象中。
                    Bitmap tempbitmap = drawBoxPeakRects(copy, boxPeaks,result,0);
                    Bitmap finalResult = result;
                    getActivity().runOnUiThread(new Runnable() {
//                        通过在UI线程上调用runOnUiThread()方法，将tempbitmap位图对象设置为显示在名为iv_showdetect的ImageView视图中。还有将list的内容转化为字符串并设置为tv_showrebar的文本内容。
                        @Override
                        public void run() {
                            iv_showdetect.setImageBitmap(tempbitmap);
                            tv_showrebar.setText("" + list.toString());
//						Toast.makeText((Context) getActivity().getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
//						Log.d(TAG, "run: --------完成");
                        }
                    });
                } else if (currentVertical > 1200) {
                    nleftbit = Bitmap.createBitmap(bitmap, 0, 0, 700, bitmap.getHeight());
                    nright = Bitmap.createBitmap(bitmap, 701, 0, currentVertical - 701, bitmap.getHeight());
                    Bitmap nrightbit = Bitmap.createBitmap(bitmap, currentVertical + 1, 0, bitmap.getWidth() - currentVertical - 1, bitmap.getHeight());
                    Bitmap resultleft = getChuli(nleftbit);
                    Bitmap resultright = getChuli(nright);
                    List<BoxPeak> boxPeaksleft = detectAndDraw(resultleft);
                    List<BoxPeak> boxPeaksright = detectAndDraw(resultright);
                    Bitmap transparentBitmap = getTransparentBitmap(resultleft, 00);
                    Bitmap copyleft = transparentBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmapleft = drawBoxPeakRects(copyleft, boxPeaksleft,resultleft,0);

                    Bitmap transparentBitmapright = getTransparentBitmap(resultright, 00);
                    Bitmap copyright = transparentBitmapright.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmapright = drawBoxPeakRects(copyright, boxPeaksright,resultright,tempbitmapleft.getWidth());

                    Bitmap tranrr = getTransparentBitmap(nrightbit, 00);
                    Bitmap tranrrbit = tranrr.copy(Bitmap.Config.ARGB_8888, true);


                    result = mergeBitmap(tempbitmapleft, tempbitmapright);
                    result = mergeBitmap(result, tranrrbit);
                    Bitmap finalResult1 = result;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_showdetect.setImageBitmap(finalResult1);
                            tv_showrebar.setText("" + list.toString());
//						Toast.makeText((Context) getActivity().getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
//						Log.d(TAG, "run: --------完成");
                        }
                    });
                } else if (currentVertical > 1900) {
                    nleftbit = Bitmap.createBitmap(bitmap, 0, 0, 900, bitmap.getHeight());
                    nright = Bitmap.createBitmap(bitmap, 901, 0, currentVertical - 901, bitmap.getHeight());
                    Bitmap nrightbit = Bitmap.createBitmap(bitmap, currentVertical + 1, 0, bitmap.getWidth() - currentVertical - 1, bitmap.getHeight());
                    Bitmap resultleft = getChuli(nleftbit);
                    Bitmap resultright = getChuli(nright);
                    List<BoxPeak> boxPeaksleft = detectAndDraw(resultleft);
                    List<BoxPeak> boxPeaksright = detectAndDraw(resultright);
                    Bitmap transparentBitmap = getTransparentBitmap(resultleft, 00);
                    Bitmap copyleft = transparentBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmapleft = drawBoxPeakRects(copyleft, boxPeaksleft,resultleft,0);

                    Bitmap transparentBitmapright = getTransparentBitmap(resultright, 00);
                    Bitmap copyright = transparentBitmapright.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmapright = drawBoxPeakRects(copyright, boxPeaksright,resultright,tempbitmapleft.getWidth());

                    Bitmap tranrr = getTransparentBitmap(nrightbit, 00);
                    Bitmap tranrrbit = tranrr.copy(Bitmap.Config.ARGB_8888, true);


                    result = mergeBitmap(tempbitmapleft, tempbitmapright);
                    result = mergeBitmap(result, tranrrbit);
                    Bitmap finalResult1 = result;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_showdetect.setImageBitmap(finalResult1);
                            tv_showrebar.setText("" + list.toString());
//						Toast.makeText((Context) getActivity().getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
//						Log.d(TAG, "run: --------完成");
                        }
                    });
                } else {
                    nright = Bitmap.createBitmap(bitmap, currentVertical + 1, 0, bitmap.getWidth() - currentVertical - 1, bitmap.getHeight());
                    result = getChuli(nleftbit);
                    boxPeaks = detectAndDraw(result);
                    result = mergeBitmap(result, nright);
                    Bitmap transparentBitmap = getTransparentBitmap(result, 00);
                    Bitmap copy = transparentBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmap = drawBoxPeakRects(copy, boxPeaks,result,0);
                    Bitmap finalResult = result;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_showdetect.setImageBitmap(tempbitmap);
                            tv_showrebar.setText("" + list.toString());
//						Toast.makeText((Context) getActivity().getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
//						Log.d(TAG, "run: --------完成");
                        }
                    });
                }

            }
        }, "photo detect");
        thread.start();
    }


    private Bitmap mergeBitmap(Bitmap map1, Bitmap map2) {
        Bitmap result = Bitmap.createBitmap(map1.getWidth() + map2.getWidth(), map1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(map1, 0, 0, null);
        canvas.drawBitmap(map2, map1.getWidth(), 0, null);
        return result;
    }

//    检测框并绘制
    protected List<BoxPeak> detectAndDraw(Bitmap image) {
        Box[] result = null;
        List<BoxPeak> list = new ArrayList<>();
        result = YOLOv5.detect(image, 0.4, 0.4);       // 找到钢筋
        for (Box box : result) {
            Bitmap temp = getResBitmap(image, (int) (box.x0), (int) (box.y0), (int) (box.x1), (int) (box.y1));  // 裁剪钢筋
            BoxPeak[] tempresult = YOLOv5.detectCustomLayer(temp, 0.3, 0.3);    // 检测钢筋顶点
            for (BoxPeak box1 : tempresult) {
                box1.setBox(box);
                box1.x0 += box.x0;
                box1.y0 += box.y0;
                box1.x1 = box1.x1 + box.x0;
                box1.y1 = box1.y1 + box.y0;
                list.add(box1);     // 顶点数据存入list（之后绘制到主屏幕）
            }
        }
//		drawBoxPeakRects(image,list);

        return list;
    }

    protected Bitmap drawBCirle(Bitmap mutableBitmap) {
        Canvas canvas = new Canvas(mutableBitmap);
        final Paint boxPaint = new Paint();
        boxPaint.setColor(Color.RED);
        boxPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200, 200, 20, boxPaint);
        return mutableBitmap;
    }


//    绘制红色顶点和框框
    protected Bitmap drawBoxPeakRects(Bitmap mutableBitmap, List<BoxPeak> results,Bitmap rawbitmap,int offset) {
        if (results == null || results.size() <= 0) {
            return mutableBitmap;
        }
        Canvas canvas = new Canvas(mutableBitmap);
        final Paint boxPaint = new Paint();
        Paint waipaint = new Paint();
        boxPaint.setColor(Color.RED);
        waipaint.setColor(Color.RED);
        waipaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStyle(Paint.Style.FILL);
        for (BoxPeak box : results) {
            int x = (int) ((box.x0 + box.x1) / 2);
//            int y = (int) ((box.y0 + box.y1) /2);
//            int y = gety(rawbitmap, x, (int) box.y0, (int) box.y1);
            int y = (int) ((box.y0 + box.y1) / 2);
            canvas.drawCircle(x, y, 4, waipaint);
            canvas.drawCircle(x, y, 8, waipaint);
            canvas.drawCircle(x, y, 12, waipaint);
            canvas.drawCircle(x, y, 2, boxPaint);
            list.add("顶点坐标  横坐标:" + (offset+x) + ", 纵坐标:" + y + "    " + "\n   ");
        }
        return mutableBitmap;
    }

    private int gety(Bitmap bitmap, int x, int y0, int y1) {
        List<Integer> listd = new ArrayList<>();
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int[] pixel = new int[width * height];
        bitmap.getPixels(pixel, 0, width, 0, 0, width, height);
        for (int i = y0; i <= y1; i++) {
            int grey = pixel[width * i + x];
            int red = ((grey & 0x00FF0000) >> 16);
            int green = ((grey & 0x0000FF00) >> 8);
            int blue = (grey & 0x000000FF);
            grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
            listd.add(grey);
        }
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText((Context) getActivity().getApplicationContext(), listd.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        int maxNum = Collections.max(listd);
        return y0 + listd.indexOf(maxNum);
    }

    public Bitmap getResBitmap(Bitmap bitmap, int x0, int y0, int x1, int y1) {
        return Bitmap.createBitmap(bitmap, x0, y0, x1 - x0, y1 - y0);
    }

//    两层Bitmap叠加，第一层透明度设为0,仅保留红点部分，叠加在第二层bitmap上。
    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// 得到圖片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }


//    直达波去除
    private Bitmap getChuli(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixel = new int[width * height];
        int[] outpixel = new int[width * height];
        bitmap.getPixels(pixel, 0, width, 0, 0, width, height);
        System.arraycopy(pixel, 0, outpixel, 0, pixel.length);
        int min_num = Integer.MAX_VALUE;
        int max_num = Integer.MIN_VALUE;

        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            int sum = 0;
            for (int j = 0; j < width; j++) {
                int grey = outpixel[width * i + j];
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                sum += grey;
            }
            int temp = sum / width;
            for (int j = 0; j < width; j++) {
                int grey = outpixel[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = grey - temp + 128;
                if (grey > max_num) {
                    max_num = grey;
                }
                if (grey < min_num) {
                    min_num = grey;
                }
                grey = alpha | (grey << 16) | (grey << 8) | grey;// 透明度 | RGB
                outpixel[width * i + j] = grey;
            }
        }
//		Log.d(TAG, "onClick:  --- max"+max_num+"    min "+min_num);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = outpixel[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = (int) Math.round((((grey - min_num) * 1.0) / ((max_num - min_num))) * 255.0);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                outpixel[width * i + j] = grey;
            }
        }

        Bitmap newbitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        newbitmap.setPixels(outpixel, 0, width, 0, 0, width, height);
        return newbitmap;
    }

    public void showDetect(ImageView iv_test) {
        int currentVertical = coloursView.getCurrentVertical();
        coloursView.buildDrawingCache();

        Bitmap bitmap = coloursView.getDrawingCache();
        Bitmap nleftbit = Bitmap.createBitmap(bitmap, 0, 0, currentVertical, bitmap.getHeight());
        Bitmap nright = Bitmap.createBitmap(bitmap, currentVertical + 1, 0, bitmap.getWidth() - currentVertical - 1, bitmap.getHeight());
        Bitmap chulibitmap = getChuli(nleftbit);
//
//		Bitmap chulibitmap = getChuli(bitmap);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iv_test.setImageBitmap(chulibitmap);
            }
        });
    }
}
