package com.pactera.chengguan.app.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.app.base.AppBaseActivity;
import com.pactera.chengguan.app.base.ChenguanOkHttpManager;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.activity.MainActivity;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lijun on 2016/6/20.
 */
public class DownloadUtils {
    /**
     * 版本更新
     */
    public static void versionUpdate( AppBaseActivity activity,  FileCallBack fileCallBack,String appUrl){
        String url=BuildConfig.BASE_URL + appUrl;
        ChenguanOkHttpManager.downGet(url,fileCallBack);
    }


//
//    /**
//     * 版本更新
//     */
//    public static void versionUpdate(final BaseActivity activity, final Handler mHandler, final String appUrl, final long size){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    final File file = new File(getAppDir(), "update.apk");
//                    URL url = new URL("http://hot.m.shouji.360tpcdn.com/160318/dccf2943b210d6ba26e994a324e8b38a/com.qihoo.appstore_300050091.apk");
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    InputStream is = connection.getInputStream();
//                    FileOutputStream fileOutputStream = null;
//                    if(is != null) {
//                        long downsize = 0;
//                        long percent = 0;
//                        fileOutputStream = new FileOutputStream(file);
//                        byte[] bytes = new byte[1024];
//                        int ch = -1;
//                        while ((ch = is.read(bytes)) != -1) {
//                            fileOutputStream.write(bytes, 0, ch);
//                            downsize = downsize + ch;
//                            long speed = (downsize * 100) / size;
//                            if (speed - percent > 1) {
//                                mHandler.sendMessage(
//                                        mHandler.obtainMessage(MainActivity.DOWN_NOTIFY, ""+speed));
//                                percent = speed;
//                            }
//                        }
//                        fileOutputStream.flush();
//                        if (fileOutputStream != null) {
//                            fileOutputStream.close();
//                        }
//                        mHandler.sendMessage(
//                                mHandler.obtainMessage(MainActivity.DOWN_NOTIFY, "100"));
//                        if (file.exists()) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                            activity.startActivity(intent);
//                            activity.finish();
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }


    /**
     * 获取apk保存地址
     * @return
     */
    public static String getAppDir(){
        String dir = makeRootDirectory(Contants.PATH_APK);
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

}
