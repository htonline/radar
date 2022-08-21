//package com.example.thread;
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.example.upload.UpLoadFileListAdapter;
//import com.example.upload.entity.UpLoadFileInfo;
//
//import java.util.List;
//
//public class ShowProgressThread extends Thread{
////    new Thread(new Runnable() {
////        @Override
////        public void run() {
////            Log.d(TAG, "run:  --> start "+start);
////            Log.d(TAG, "run:  --> stop "+stop);
////            final float[] startnum = {((float) (start ) / (float)(stop+1) ) * 100};
////            float stopnum = ((float) start+1 / (float) (stop+1)) * 100;
////            while (startnum[0] <= stopnum) {
////                Log.d(TAG, "run:  --> startnum "+startnum[0]);
////                Log.d(TAG, "run:  --> stopnum "+stopnum);
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                mActivity.runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        mfileInfos.get(listPosition).setUploadpercent(startnum[0] +"%");
////                        startnum[0] +=1;
////                        adapter.notifyDataSetChanged();
////                    }
////                });
////            }
////        }
////    }).start();
//
//
//    private int start;
//    private int stop;
//    private volatile boolean isfinished = false;
//    private volatile int num;
//    private UpLoadFileListAdapter adapter;
//    private List<UpLoadFileInfo> mfileInfos;
//    private Handler handler;
//    private int position;
//
//    public ShowProgressThread(int position,int start, int stop, boolean isfinished,
//                              int num, UpLoadFileListAdapter adapter,
//                              List<UpLoadFileInfo> mfileInfos, Handler handler) {
//        this.position = position;
//        this.start = start;
//        this.stop = stop;
//        this.isfinished = isfinished;
//        this.num = num;
//        this.adapter = adapter;
//        this.mfileInfos = mfileInfos;
//        this.handler = handler;
//    }
//
//    public void setStart(int start) {
//        this.start = start;
//    }
//
//    public void setIsfinished(boolean isfinished) {
//        this.isfinished = isfinished;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }
//
//    @Override
//    public void run() {
//        float startnum = ((float) (start ) / (float)(stop+1) ) * 100;
//        float stopnum = ((float) (start+1) / (float) (stop+1)) * 100;
//        Message msg ;
//        while (startnum <= stopnum && !isfinished) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            msg = Message.obtain();
////            msg.what = 1;
//            msg.arg1 = (int) startnum;
//            msg.arg2 = (int) position;
//            handler.sendMessage(msg);
//            if (startnum != 99){
//                startnum += 1;
//            }
//        }
//    }
//}
