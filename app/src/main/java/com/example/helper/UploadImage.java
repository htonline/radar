package com.example.helper;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadImage {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    private static final String BOUNDARY = "FlPm4LpSXsE" ; //UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
    private static final String PREFIX="--";
    private static final String LINE_END="\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型

    /** * android上传文件到服务器
     * @param file 需要上传的文件
     * @param requestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file,String requestURL,String token) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod("POST"); //请求方式

            conn.setRequestProperty("Charset", CHARSET);//设置编码
            //头信息
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            conn.setRequestProperty("Authorization", token);
            if(file!=null) {
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam=conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                //file内容
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"file\";filename=" + "\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream"+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                //读取文件的内容
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                //写入文件二进制内容
                dos.write(LINE_END.getBytes());
                //写入end data
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
//                Log.e(TAG, "response code:"+res);
                if(res==200) {
                    String oneLine;
                    StringBuffer response = new StringBuffer();
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((oneLine = input.readLine()) != null) {
                        response.append(oneLine);
                    }
                    return response.toString();
                }else{
                    return res+"";
                }
            }else{
                return "file not found";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "MalformedURLException";
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException";
        }
    }

}