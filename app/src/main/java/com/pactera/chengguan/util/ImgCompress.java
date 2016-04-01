package com.pactera.chengguan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.pactera.chengguan.config.Contants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图片压缩
 * Created by huang hua
 * 2016/3/28.
 */
public class ImgCompress {

    private int width;
    private int height;
    private String filePath;
    private String fileName;
    private String desPath;

    private static final int RESIZE = 500;

    /**
     * 构造函数
     */
    public ImgCompress(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * 按照宽度还是高度进行压缩
     */
    public int resizeFix(BitmapFactory.Options options, int w, int h) {
        int be = 0;
        if (width / height > w / h) {
            be =  (int)(options.outWidth / (float)RESIZE);
            if(be <= 0){
                be = 1;
            }
        } else {
            be = (int)(options.outHeight / (float)RESIZE);
            if(be <= 0){
                be = 1;
            }
        }
        return be;
    }

    public File resizeBitmap(){
        File file;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            width = options.outWidth;
            height = options.outHeight;
            int be = resizeFix(options, RESIZE, RESIZE);
            options.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(filePath, options);

            String dir = FileDownloadUtils.makeRootDirectory(Contants.PATH_SZ + Contants.PATH_IMG_COMPRESS);
            desPath = dir + fileName;
            file = new File(desPath);
            FileOutputStream os = new FileOutputStream(desPath);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os)){
                os.flush();
                os.close();
            }
            return file;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new File(filePath);
    }

    public String getFilePath(){
        return filePath;
    }

    public String getFileName(){
        return fileName;
    }

    public String getDesPath(){
        return desPath;
    }

}
