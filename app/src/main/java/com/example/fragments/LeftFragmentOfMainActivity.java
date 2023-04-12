package com.example.fragments;


import java.util.ArrayList;
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
                Bitmap nleftbit = Bitmap.createBitmap(bitmap, 0, 0, currentVertical, bitmap.getHeight());
                Bitmap nright;
                Bitmap result;
                List<BoxPeak> boxPeaks;
                if (currentVertical < 400) {
                    nright = Bitmap.createBitmap(bitmap, currentVertical + 1, 0, 512 - currentVertical - 1, bitmap.getHeight());
                    Bitmap chulibitmap = getChuli(nleftbit);
                    result = mergeBitmap(chulibitmap, nright);
                    boxPeaks = detectAndDraw(result);
                    Bitmap halfbitmap = Bitmap.createBitmap(bitmap, 513, 0, bitmap.getWidth() - 513, bitmap.getHeight());
                    result = mergeBitmap(result, halfbitmap);
                    Bitmap transparentBitmap = getTransparentBitmap(result, 00);
                    Bitmap copy = transparentBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmap = drawBoxPeakRects(copy, boxPeaks);
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
                    Bitmap tempbitmapleft = drawBoxPeakRects(copyleft, boxPeaksleft);

                    Bitmap transparentBitmapright = getTransparentBitmap(resultright, 00);
                    Bitmap copyright = transparentBitmapright.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmapright = drawBoxPeakRects(copyright, boxPeaksright);

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
                    Bitmap tempbitmapleft = drawBoxPeakRects(copyleft, boxPeaksleft);

                    Bitmap transparentBitmapright = getTransparentBitmap(resultright, 00);
                    Bitmap copyright = transparentBitmapright.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap tempbitmapright = drawBoxPeakRects(copyright, boxPeaksright);

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
                    Bitmap tempbitmap = drawBoxPeakRects(copy, boxPeaks);
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

    protected List<BoxPeak> detectAndDraw(Bitmap image) {
        Box[] result = null;
        List<BoxPeak> list = new ArrayList<>();
        result = YOLOv5.detect(image, 0.4, 0.4);
        for (Box box : result) {
            Bitmap temp = getResBitmap(image, (int) (box.x0), (int) (box.y0), (int) (box.x1), (int) (box.y1));
            BoxPeak[] tempresult = YOLOv5.detectCustomLayer(temp, 0.2, 0.2);
            for (BoxPeak box1 : tempresult) {
                box1.setBox(box);
                box1.x0 += box.x0;
                box1.y0 += box.y0;
                box1.x1 = box1.x1 + box.x0;
                box1.y1 = box1.y1 + box.y0;
                list.add(box1);
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


    protected Bitmap drawBoxPeakRects(Bitmap mutableBitmap, List<BoxPeak> results) {
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
        BoxPeak peakfirst = results.get(0);
//		float last = (peakfirst.y0 + peakfirst.y1)/2;
        for (BoxPeak box : results) {
//			if ((box.y0+box.y1)/2 - last > 10){
//				continue;
//			}
//			Log.d(TAG, "drawBoxPeakRects: ---- "+box.x0);
            canvas.drawCircle((box.x0 + box.x1) / 2, (box.y0 + box.y1) / 2, 4, waipaint);
            canvas.drawCircle((box.x0 + box.x1) / 2, (box.y0 + box.y1) / 2, 8, waipaint);
            canvas.drawCircle((box.x0 + box.x1) / 2, (box.y0 + box.y1) / 2, 12, waipaint);
            canvas.drawCircle((box.x0 + box.x1) / 2, (box.y0 + box.y1) / 2, 2, boxPaint);
            list.add("顶点位置:" + (box.x0 + box.x1) / 2 + "," + (box.y0 + box.y1) / 2 + "\n   ");
        }
        return mutableBitmap;
    }

    public Bitmap getResBitmap(Bitmap bitmap, int x0, int y0, int x1, int y1) {
        return Bitmap.createBitmap(bitmap, x0, y0, x1 - x0, y1 - y0);
    }

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
                grey = alpha | (grey << 16) | (grey << 8) | grey;
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
