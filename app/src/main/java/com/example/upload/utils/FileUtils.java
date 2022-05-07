package com.example.upload.utils;

import android.util.Log;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.*;

/**
 * 文件处理辅助类
 *
 * @author yjmyzz@126.com
 * @version 0.2
 * @since 2014-11-17
 *
 */
public class FileUtils {
    public static final String IP = "http://192.168.3.117:8013/";
    private static final String TAG = "FileUtils";
    /**
     * 当前目录路径
     */
    public static String currentWorkDir = System.getProperty("user.dir") + "\\";

    /**
     * 左填充
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String leftPad(String str, int length, char ch) {
        if (str.length() >= length) {
            return str;
        }
        char[] chs = new char[length];
        Arrays.fill(chs, ch);
        char[] src = str.toCharArray();
        System.arraycopy(src, 0, chs, length - src.length, src.length);
        return new String(chs);
    }

    /**
     * 删除文件
     *
     * @param fileName
     *            待删除的完整文件名
     * @return
     */
    public static boolean delete(String fileName) {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            result = f.delete();

        } else {
            result = true;
        }
        return result;
    }

    /***
     * 递归获取指定目录下的所有的文件（不包括文件夹）
     *
     *
     */
    public static ArrayList<File> getAllFiles(String dirPath) {
        File dir = new File(dirPath);

        ArrayList<File> files = new ArrayList<File>();

        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File f = fileArr[i];
                if (f.isFile()) {
                    files.add(f);
                } else {
                    files.addAll(getAllFiles(f.getPath()));
                }
            }
        }
        return files;
    }

    /**
     * 获取指定目录下的所有文件(不包括子文件夹)
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles();
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }


    public static ArrayList<File> getDirFilesForPrefix(String dirPath,final String preffix){
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerPreffix = preffix.toLowerCase();
                if (lowerName.startsWith(lowerPreffix)) {
                    return true;
                }
                return false;
            }

        });
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }
    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath
     *            目录路径
     * @param suffix
     *            文件后缀
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath,
                                              final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerSuffix = suffix.toLowerCase();
                if (lowerName.endsWith(lowerSuffix)) {
                    return true;
                }
                return false;
            }

        });
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 读取文件内容
     *
     * @param fileName
     *            待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }

    /**
     * 写文件
     *
     * @param fileName
     *            目标文件名
     * @param fileContent
     *            写入的内容
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        byte[] b = fileContent.getBytes();
        fs.write(b);
        fs.flush();
        fs.close();
        result = true;
        return result;
    }

    /**
     * 追加内容到指定文件
     *
     * @param fileName
     * @param fileContent
     * @return
     * @throws IOException
     */
    public static boolean append(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            RandomAccessFile rFile = new RandomAccessFile(f, "rw");
            byte[] b = fileContent.getBytes();
            long originLen = f.length();
            rFile.setLength(originLen + b.length);
            rFile.seek(originLen);
            rFile.write(b);
            rFile.close();
        }
        result = true;
        return result;
    }

    /**
     * 拆分文件
     *
     * @param byteSize
     *            按多少字节大小拆分
     * @return 拆分后的文件名列表
     * @throws IOException
     */
    public List<String> splitBySize(File file, int byteSize,String path)
            throws IOException {
        List<String> parts = new ArrayList<String>();
        int count = (int) Math.ceil(file.length() / (double) byteSize);
        int sumcount = count;
        int countLen = (count + "").length();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,
                2, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = file.getName() + "."
                    + leftPad((i + 1) + "", countLen, '0') + "."+ sumcount+".part";
            threadPool.execute(new SplitRunnable(byteSize, (long)i * (long)byteSize,
                    partFileName, file,path));
            parts.add(path +"/"+partFileName);
        }
        while (threadPool.getActiveCount() != 0){
            try {
                Thread.sleep(50);
                while (threadPool.getActiveCount() != 0)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        return parts;
    }

    /**
     * 合并文件
     *
     * @param dirPath
     *            拆分文件所在目录名
     * @param partFileSize
     *            拆分文件的字节数大小
     * @param mergeFileName
     *            合并后的文件名
     * @throws IOException
     */
    public void mergePartFiles(String dirPath, String partFilePreffix,
                               int partFileSize, String mergeFileName) throws IOException {
        ArrayList<File> partFiles = FileUtils.getDirFilesForPrefix(dirPath,
                partFilePreffix);
        Collections.sort(partFiles, new FileComparator());
        partFiles.remove(0);
        RandomAccessFile randomAccessFile = new RandomAccessFile(mergeFileName,
                "rw");
        randomAccessFile.setLength(partFileSize * (partFiles.size() - 1)
                + partFiles.get(partFiles.size() - 1).length());
        randomAccessFile.close();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                partFiles.size(), partFiles.size() * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));

        for (int i = 0; i < partFiles.size(); i++) {
            threadPool.execute(new MergeRunnable(i * partFileSize,
                    mergeFileName, partFiles.get(i)));
        }
        while (threadPool.getActiveCount() != 0){
            try {
                Thread.sleep(50);
                while (threadPool.getActiveCount() != 0)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据文件名，比较文件
     *
     * @author yjmyzz@126.com
     *
     */
    private class FileComparator implements Comparator<File> {
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    /**
     * 分割处理Runnable
     *
     * @author yjmyzz@126.com
     *
     */
    private class SplitRunnable implements Runnable {
        int byteSize;
        String partFileName;
        File originFile;
        long startPos;
        String path;

        public SplitRunnable(int byteSize, long startPos, String partFileName,
                             File originFile,String path) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
            this.path = path;
        }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                partFileName = path +"/"+partFileName;
                byte[] b = new byte[byteSize];
                rFile.seek(startPos);// 移动指针到每“段”开头
                int s = rFile.read(b);
                os = new FileOutputStream(partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并处理Runnable
     *
     * @author yjmyzz@126.com
     *
     */
    private class MergeRunnable implements Runnable {
        long startPos;
        String mergeFileName;
        File partFile;

        public MergeRunnable(long startPos, String mergeFileName, File partFile) {
            this.startPos = startPos;
            this.mergeFileName = mergeFileName;
            this.partFile = partFile;
        }

        public void run() {
            RandomAccessFile rFile;
            try {
                rFile = new RandomAccessFile(mergeFileName, "rw");
                rFile.seek(startPos);
                FileInputStream fs = new FileInputStream(partFile);
                byte[] b = new byte[fs.available()];
                fs.read(b);
                fs.close();
                rFile.write(b);
                rFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            return false;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                f.delete();
            }
        }
        file.delete();
        return true;
    }

    public static byte[] getByte(File file) throws IOException {
        // 得到文件长度
        byte[] b = new byte[(int) file.length()];
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            try {
                System.out.println(in.read(b));
            } catch (IOException e) {

            }
        } catch (Exception e) {

            return null;
        } finally {
            in.close();
        }
        return b;
    }
    public static String getMd5(byte[] bytes) {
        // 16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(bytes);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            // 移位 输出字符串
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {

        }
        return null;
    }

}