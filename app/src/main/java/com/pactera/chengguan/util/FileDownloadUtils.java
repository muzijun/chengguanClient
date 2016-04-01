package com.pactera.chengguan.util;

import android.os.Environment;
import android.widget.ImageView;

import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.config.Contants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.pactera.chengguan.model.municipal.PicData;
import com.pactera.chengguan.view.ImageItemCycle.ImageCycleViewListener;

/**
 * 图片或文件下载
 * Created by huang hua
 * 2016/3/29.
 */
public class FileDownloadUtils {

    /**
     * 图片流下载
     * @param mImageCycleViewListener
     * @param imageView
     * @param token
     * @param picData
     */
    public static void downloadLauncher(final ImageCycleViewListener mImageCycleViewListener
            , final ImageView imageView, final String token, final PicData picData) {
        String localUrl = picData.getLocalUrl();
        if (localUrl != null && localUrl.length() > 0) {
            mImageCycleViewListener.displayImage(localUrl, imageView);
            return;
        }
        String tmpPath = Environment.getExternalStorageDirectory() + Contants.PATH_SZ
                + Contants.PATH_IMG_CACHE + picData.getName();
        File tmpFile = new File(tmpPath);
        if(tmpFile.exists()){
            picData.setLocalUrl(tmpPath);
            mImageCycleViewListener.displayImage(tmpPath, imageView);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String postPath = BuildConfig.BASE_URL + picData.getUrl() + "&access_token=" + token;
                    URL url = new URL(postPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/octet-stream");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    int response = connection.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        byte[] bytes = new byte[1024];
                        InputStream is = connection.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        int ch = -1;
                        while ((ch = is.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, ch);
                        }
                        byte[] fileBytes = outputStream.toByteArray();
                        outputStream.close();
                        is.close();

                        String filePath = getImageDir() + picData.getName();
                        String tmpFilePath = filePath + ".tmp";
                        File file = new File(tmpFilePath);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(fileBytes);
                        fos.flush();
                        fos.close();
                        if(file.length() > 0) {
                            file.renameTo(new File(filePath));
                        }

                        mImageCycleViewListener.displayImage(filePath, imageView);
                        picData.setLocalUrl(filePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    deleteTmpImage();
                }
            }
        }).start();
    }

    /**
     * 获取图片保存地址
     * @return
     */
    public static String getImageDir(){
        String dir = makeRootDirectory(Contants.PATH_SZ + Contants.PATH_IMG_CACHE);
        return dir;
    }

    public static String makeRootDirectory(String filePath) {
        File file = null;
        try {
            String path = Environment.getExternalStorageDirectory() + File.separator;
            String[] dirList = filePath.split("/");
            for(String dir : dirList){
                if(dir == null || dir.length() <= 0){
                    continue;
                }
                path = path + dir + File.separator;
                file = new File(path);
                if(!file.exists()){
                    file.mkdir();
                }
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除临时tmp文件
     */
    public static void deleteTmpImage(){
        File dirFile = new File(getImageDir());
        File[] fileList = dirFile.listFiles();
        for(File file : fileList){
            if(file.getName().endsWith(".tmp")){
                file.delete();
            }
        }
    }

}
