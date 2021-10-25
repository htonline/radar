//package com.example.Test;
//
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItem;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.Test;
////import org.springframework.http.HttpEntity;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.file.Files;
//
//public class FileTest {
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void main(String[] args) throws IOException {
//        String filePath = "F:\\test.txt";
//        File file = new File(filePath);
//        FileInputStream fileInputStream = new FileInputStream(file);
//        // 需要导入commons-fileupload的包
//        FileItem fileItem = new DiskFileItem("copyfile.txt", Files.probeContentType(file.toPath()),false,file.getName(),(int)file.length(),file.getParentFile());
//        byte[] buffer = new byte[4096];
//        int n;
//        try (InputStream inputStream = new FileInputStream(file);
//             OutputStream os = fileItem.getOutputStream()){
//            while ( (n = inputStream.read(buffer,0,4096)) != -1){
//                os.write(buffer,0,n);
//            }
//            //也可以用IOUtils.copy(inputStream,os);
//            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
//            System.out.println(multipartFile.getName());
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    String multifilePath = null;
//    File tempfile = null;
//    MultipartFile multipartFile = null;
//    FileInputStream fileInputStream =null;
//    FileItem fileItem = null;
//    byte[] buffer;
//    String fileName=null;
//    String url=null;
//    HttpPost httpPost = null;
//    HttpClient httpClient = null;
//    MultipartEntityBuilder builder = null;
//    HttpEntity entity = null;
//    HttpResponse response =null;
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Test
//    public void filetest() throws IOException {
//        multifilePath = "F:\\123.raw";
//        tempfile= new File(multifilePath);
//        fileInputStream = new FileInputStream(tempfile);
//        // 需要导入commons-fileupload的包
//        fileItem = new DiskFileItem("file_raw", Files.probeContentType(tempfile.toPath()),false,tempfile.getName(),(int)tempfile.length(),tempfile.getParentFile());
//        buffer = new byte[4096];
//        int n;
//        try (InputStream inputStream = new FileInputStream(tempfile);
//             OutputStream os = fileItem.getOutputStream()){
//            while ( (n = inputStream.read(buffer,0,4096)) != -1){
//                os.write(buffer,0,n);
//            }
//            //也可以用IOUtils.copy(inputStream,os);
//            multipartFile = new CommonsMultipartFile(fileItem);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        fileName = multipartFile.getOriginalFilename();
//        url = "http://192.168.3.158:8090/api/raw-upload";
//        httpPost = new HttpPost(url);
//        httpClient = HttpClientBuilder.create().build();
//        builder = MultipartEntityBuilder.create();
//        builder.addBinaryBody("file", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
//        builder.addTextBody("filename", fileName);
//        entity = builder.build();
//        httpPost.setEntity(entity);
//        response = httpClient.execute(httpPost);// 执行提交
//
//    }
//
//}
