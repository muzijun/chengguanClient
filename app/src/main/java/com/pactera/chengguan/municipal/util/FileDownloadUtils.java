package com.pactera.chengguan.municipal.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.app.base.ChenguanOkHttpManager;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.app.util.DownloadUtils;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.view.ImageItemCycle;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;

/**
 * 图片或文件下载
 * Created by huang hua
 * 2016/3/29.
 */
public class FileDownloadUtils extends DownloadUtils {

    /**
     * 图片流下载
     * @param mContext
     * @param mImageCycleViewListener
     * @param imageView
     * @param token
     * @param picData
     */
    public static void downloadLauncher(final BaseActivity mContext, final ImageItemCycle.ImageCycleViewListener mImageCycleViewListener
            , final ImageView imageView, final String token, final PicData picData) {
        String tmpPath = Environment.getExternalStorageDirectory() + Contants.PATH_SZ
                + Contants.PATH_IMG_CACHE + picData.getName();
        File tmpFile = new File(tmpPath);
        if(tmpFile.exists()){
            mImageCycleViewListener.displayImage(tmpPath, imageView);
            return;
        }
        String postPath = BuildConfig.BASE_URL + picData.getUrl() + "&access_token=" + token;
        String filePath = getImageDir();
        ChenguanOkHttpManager.downPost(postPath, new FileCallBack(filePath, picData.getName()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {
                mImageCycleViewListener.displayImage(response.getAbsolutePath(), imageView);
            }
        });
    }



//    public static void downloadLauncher(final BaseActivity mContext, final ImageItemCycle.ImageCycleViewListener mImageCycleViewListener
//            , final ImageView imageView, final String token, final PicData picData) {
//        String tmpPath = Environment.getExternalStorageDirectory() + Contants.PATH_SZ
//                + Contants.PATH_IMG_CACHE + picData.getName();
//        File tmpFile = new File(tmpPath);
//        if(tmpFile.exists()){
//            mImageCycleViewListener.displayImage(tmpPath, imageView);
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String tmpFileName = "";
//                try {
//                    String postPath = BuildConfig.BASE_URL + picData.getUrl() + "&access_token=" + token;
//                    URL url = new URL(postPath);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setConnectTimeout(30000);
//                    connection.setRequestMethod("POST");
//                    connection.setDoOutput(true);
//                    connection.setRequestProperty("Content-Type", "application/octet-stream");
//                    connection.setRequestProperty("Connection", "Keep-Alive");
//                    int response = connection.getResponseCode();
//                    if (response == HttpURLConnection.HTTP_OK) {
//                        byte[] bytes = new byte[1024];
//                        InputStream is = connection.getInputStream();
//                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                        int ch = -1;
//                        while ((ch = is.read(bytes)) != -1) {
//                            outputStream.write(bytes, 0, ch);
//                        }
//                        byte[] fileBytes = outputStream.toByteArray();
//                        outputStream.close();
//                        is.close();
//
//                        final String filePath = getImageDir() + picData.getName();
//                        String tmpFilePath = filePath + ".tmp";
//                        tmpFileName = picData.getName()+".tmp";
//                        File file = new File(tmpFilePath);
//                        FileOutputStream fos = new FileOutputStream(file);
//                        fos.write(fileBytes);
//                        fos.flush();
//                        fos.close();
//                        if(file.length() > 0) {
//                            file.renameTo(new File(filePath));
//                        }
//                        mContext.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mImageCycleViewListener.displayImage(filePath, imageView);
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
    /**
     * 市政获取图片保存地址
     * @return
     */
    public static String getImageDir(){
        String dir = makeRootDirectory(Contants.PATH_SZ + Contants.PATH_IMG_CACHE);
        return dir;
    }

}
