package com.example.UpLoad;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.helper.UploadUtil;
import com.example.ladarmonitor.R;

import java.util.HashMap;
import java.util.Map;

public class SelectPicActivity extends Activity implements OnClickListener, UploadUtil.OnUploadProcessListener {

    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2;  //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;
    /***
     * 这里的这个URL是我服务器的javaEE环境URL
     */
    private static String requestURL = "http://39.105.125.51:8001/api/phoneOperate/updateTupian";
    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /***
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";

    private static final String TAG = "SelectPicActivity";

    private LinearLayout dialogLayout;
    private Button takePhotoBtn,pickPhotoBtn,cancelBtn;

    /**获取到的图片路径*/
    private String picPath;
    private String temptoken;
    private Intent lastIntent ;

    private Uri photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);
        initView();

    }
    /**
     * 初始化加载View
     */
    private void initView() {
        takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
        takePhotoBtn.setOnClickListener(this);
        pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
        pickPhotoBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(this);

        lastIntent = getIntent();
        temptoken=lastIntent.getStringExtra("token");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                takePhoto();
                break;
            case R.id.btn_pick_photo:
                pickPhoto();
                break;
            default:
                setResult(Activity.RESULT_CANCELED, lastIntent);
                finish();
                break;
        }
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        }else{
            Toast.makeText(this,"内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setResult(Activity.RESULT_CANCELED, lastIntent);
        finish();
        return super.onTouchEvent(event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
        {
            doPhoto(requestCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode,Intent data)
    {
        if(requestCode == SELECT_PIC_BY_PICK_PHOTO )  //从相册取图片，有些手机有异常情况，请注意
        {
            if(data == null)
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if(photoUri == null )
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null,null);
        Log.d(TAG, cursor.toString());
        if(cursor != null )
        {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            if(Build.VERSION.SDK_INT < 14) {
                cursor.close();
            }

        }
        Log.i(TAG, "imagePath = "+picPath);


        if(picPath!=null)
        {
            handler.sendEmptyMessage(TO_UPLOAD_FILE);
        }else{
            Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
        }
    }
    private void toUploadFile()
    {
            Toast.makeText(SelectPicActivity.this,"正在上传中...",Toast.LENGTH_SHORT).show();
            String fileKey = "multipartFile";
            UploadUtil uploadUtil = UploadUtil.getInstance();;
            uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态
            Map<String, String> params = new HashMap<String, String>();
            params.put("orderId", "11111");
            uploadUtil.uploadFile( picPath,fileKey, requestURL,params,temptoken);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;
                case UPLOAD_FILE_DONE:
                    String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+UploadUtil.getRequestTime()+"秒";
                    if (msg.arg1==1){
                        Toast.makeText(SelectPicActivity.this,"上传照片成功",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(SelectPicActivity.this,result+"",Toast.LENGTH_SHORT).show();
                    lastIntent.putExtra("UPPICRES",msg.obj.toString());
                    lastIntent.putExtra("UPLOADIMG_RES", msg.arg1);
                    setResult(Activity.RESULT_OK, lastIntent);
                    finish();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }
}
