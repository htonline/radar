package com.example.helper;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.entity.Wall;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpTools {
    private static final String TAG = "OkHttpTools";
    private int isok=0;
    //创建OkHttpClient对象
    private final OkHttpClient client = new OkHttpClient();
    //登录验证的方法
    public String Login() throws JSONException {

        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        //创建请求体并传递参数
        json.put("username","DQuser");
        json.put("password","123456");
        RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));
//        RequestBody formBody = new FormBody.Builder()
//                .add("username","admin")
//                .add("password","123456")
//                .build();
        //创建Request对象
        Request request = new Request.Builder()
                .url("http://39.105.125.51:8001/auth/phonelogin")
                .post(formBody)
                .build();
        //获取Response对象
        Response responsed = null;
        try (Response response = client.newCall(request).execute()) {
            responsed = response;
            //响应成功并返回响应内容
            if (response.isSuccessful()) {
                com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSON.parseObject(response.body().string());
                String  token= jsonObj.getString("token");
                return token;
                //此时代码执行在子线程，修改UI的操作使用handler跳转到UI线程
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        //响应失败返回" "
        return responsed.toString();
    }
    //注册验证的方法
    public String Register(String username,String password){
        RequestBody formBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.*.*:8080/RegisterUser")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return " ";
    }

    public String UploadWallData(Wall wall) throws JSONException {


        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        //创建请求体并传递参数
        json.put("dangqiangWeizhi",wall.getDangqiangWeizhi());
        json.put("dangqiangGao",wall.getDangqiangGao());
        json.put("dangqiangBianhao",wall.getDangqiangBianhao());
        json.put("dangqiangLeixing",wall.getDangqiangLeixing());
        json.put("cexianFangxiang",wall.getCexianFangxiang());
        json.put("dangqiangHoudu",wall.getDangqiangHoudu());
        json.put("dangqiangKuan",wall.getDangqiangKuan());
        json.put("cexianBianhao",wall.getCexianBianhao());
        json.put("cexianQidian",wall.getCexianQidian());
        json.put("cexianZhongdian",wall.getCexianZhongdian());
        json.put("zhujiXuhao",wall.getZhujiXuhao());
        json.put("jiancerenyuanName",wall.getJiancerenyuanName());
        json.put("shujuwenjianName",wall.getShujuwenjianName());
        json.put("zhaopianOne",wall.getZhaopianOne());
        json.put("zhaopianTwo",wall.getZhaopianOne());
        json.put("zhaopianThree",wall.getZhaopianOne());
        json.put("beizhu",wall.getBeizhu());
        RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));
        Request request = new Request.Builder()
                .url("http://39.105.125.51:8001/api/phoneOperate/addJianceData")
                .addHeader("Authorization",wall.getToken())
                .post(formBody)
                .build();
        Response respon = null;
        try (Response response = client.newCall(request).execute()) {
            //响应成功并返回响应内容
            if (response.isSuccessful()) {
                isok=1;
                return String.valueOf(isok);
                //此时代码执行在子线程，修改UI的操作使用handler跳转到UI线程
            }else{
                return response.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //响应失败返回" "
        return "";
    }
}