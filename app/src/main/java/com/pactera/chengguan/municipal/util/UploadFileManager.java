package com.pactera.chengguan.municipal.util;
import android.os.AsyncTask;

import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.municipal.FileUploadBean;
import com.pactera.chengguan.municipal.config.RequestListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang hua
 * 2016/4/27.
 */
public class UploadFileManager {

    private BaseActivity context;
    private FileUploadListener listener;
    private String object;
    private int type;
    private Map<String, String> fileResultMap;
    private boolean isRunning = true;
    private boolean isError = false;
    private String param;

    public static final int UPLOAD_TYPE_IMG = 1;
    public static final int UPLOAD_TYPE_FILE = 2;

    /**
     * @param context
     * @param object  图片所属，见MunicipalContants.UPLOAD_OBJECT_CASE | UPLOAD_OBJECT_NOTICE | UPLOAD_OBJECT_ISSUE
     * @param type    0-处理前 1-处理后
     */
    public UploadFileManager(BaseActivity context, FileUploadListener listener, String object, int type, String param) {
        this.context = context;
        this.listener = listener;
        this.object = object;
        this.type = type;
        this.param = param;
        fileResultMap = new HashMap<String, String>();
    }

    /**
     * 上传多个文件
     */
    public void upLoadMulFiles(final List<UpLoadFileData> fileDataList) {
        for (UpLoadFileData fileData : fileDataList) {
            upLoadFile(fileData.getFilePath(), fileData.getType());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning && !isError) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    if (isAllUploadSuccess(fileDataList.size())) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(getAllUploadUrl(fileDataList), param);
                            }
                        });
                        isRunning = false;
                    }
                }
                if (isError) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError();
                        }
                    });
                }
            }
        }).start();
    }

    private void upLoadFile(final String filePath, int type) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }
        final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        File file = null;
        if (type == UPLOAD_TYPE_IMG) {
            new PhotoTask(filePath,fileName).execute();
        } else {
            file = new File(filePath);
            requestFileUpload(file,filePath,fileName);
        }

    }

    /**
     * 判断是否全部上传完成
     *
     * @param fileNum
     * @return
     */
    private boolean isAllUploadSuccess(int fileNum) {
        return fileResultMap.size() == fileNum;
    }

    /**
     * 获得所有的上传Url
     *
     * @return
     */
    private List<String> getAllUploadUrl(List<UpLoadFileData> fileDataList) {
        List<String> fileUrlList = new ArrayList<String>();
        for (UpLoadFileData fileData : fileDataList) {
            if (fileResultMap.containsKey(fileData.getFilePath())) {
                fileUrlList.add(fileResultMap.get(fileData.getFilePath()));
            }
        }
        return fileUrlList;
    }

    /**
     * 上传
     * @param file 文件
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    private void requestFileUpload(File file, final String filePath,String fileName) {
        MunicipalRequest.requestFileUpload(context, new RequestListener() {
            @Override
            public void success(String reqUrl, Object result) {
                FileUploadBean fileUploadBean = (FileUploadBean) result;
                if (fileUploadBean.result == Contants.RESULT_OK && !fileUploadBean.datas.isEmpty()) {
                    fileResultMap.put(filePath, fileUploadBean.datas);
                } else {
                    isError = true;
                }
            }

            @Override
            public void fail() {
                isError = true;
            }
        }, object, type, file, fileName);
    }

    public static class UpLoadFileData {
        private String filePath;
        private int type;

        public UpLoadFileData(String filePath, int type) {
            this.filePath = filePath;
            this.type = type;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }


    /**
     * 图片压缩，因为耗时，在子线程运行
     */
    class PhotoTask extends AsyncTask<String, Integer, File> {
        private String filePath;
        private String fileName;

        public PhotoTask(String filePath, String fileName) {
            super();
            this.fileName = fileName;
            this.filePath = filePath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected File doInBackground(String... params) {

            ImgCompress imgCompress = new ImgCompress(filePath, fileName);
            File file = imgCompress.resizeBitmap();
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            requestFileUpload(file, filePath, fileName);
        }
    }


}
