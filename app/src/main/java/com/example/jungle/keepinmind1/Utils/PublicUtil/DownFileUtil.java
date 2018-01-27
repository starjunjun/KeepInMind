package com.example.jungle.keepinmind1.Utils.PublicUtil;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jungle on 2017/12/20.
 */

public class DownFileUtil {

    public static boolean CheckExistFile(String path){
        if (null == path || "".equals(path.trim())) {
            return false;
        }

        File targetFile = new File(path);
        return targetFile.exists();
    }



    public static void download(String fileName1,String urls) {
        try {

            URL url = new URL(urls);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            //获得长度
            int contentLength = conn.getContentLength();
            //创建文件夹 MyDownLoad，在存储卡下

            String dirName = Environment.getExternalStorageDirectory() + "/Download/tessdata/";
            File file = new File(dirName);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            String fileName = dirName + fileName1;
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(fileName);
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
