package com.pactera.chengguan.app.base;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.app.model.AppRequestFile;
import com.pactera.chengguan.app.model.AppRequestPair;
import com.pactera.chengguan.app.model.AppRequestParam;
import com.pactera.chengguan.app.util.ProgressDlgUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * 请求管理
 * Created by lijun on 2015/12/9.
 */
public class ChenguanOkHttpManager {

    public static void request(AppRequestPair requestPair) {
        if (requestPair == null) {
            return;
        } else {
            if (requestPair.getMethod().equals(Contants.Get)) {
                get(requestPair);

            } else if (requestPair.getMethod().equals(Contants.Post)) {
                post(requestPair);

            } else if (requestPair.getMethod().equals(Contants.File)) {
                upload(requestPair);
            }
        }
    }

    /**
     * get
     *
     * @param request
     */
    protected static void get(AppRequestPair request) {
        RequestCall call = OkHttpUtils.get().url(request.getUrl()).tag(request.getContext()).build();
        call.execute(request.getRequest());
        if (request.getLoadingShow()) {
            ProgressDlgUtil.showProgressDlg(request.getProgressTitle(), request.getContext(), call, request.isExit());
        }


    }

    /**
     * post
     *
     * @param request
     */
    protected static void post(AppRequestPair request) {
        PostFormBuilder builder = OkHttpUtils.post().url(request.getUrl()).tag(request.getContext());
        for (AppRequestParam param : request.getParams()) {
            builder.addParams(param.getKey(), param.getValue());
        }
        RequestCall call = builder.build();
        call.execute(request.getRequest());
        if (request.getLoadingShow()) {
            ProgressDlgUtil.showProgressDlg(request.getProgressTitle(), request.getContext(), call, request.isExit());
        }

    }

    /**
     * 上传文件
     *
     * @param request
     */
    protected static void upload(AppRequestPair request) {
        PostFormBuilder builder = OkHttpUtils.post().url(request.getUrl()).tag(request.getContext());
        for (AppRequestParam param : request.getParams()) {
            builder.addParams(param.getKey(), param.getValue());
        }
        for (AppRequestFile param_file : request.getParams_files()) {
            builder.addFile("fileuploadList", param_file.getName(), param_file.getFile());
        }
        RequestCall call = builder.build().connTimeOut(1000 * 30);
        call.execute(request.getRequest());
        if (request.getLoadingShow()) {
            ProgressDlgUtil.showProgressDlg(request.getProgressTitle(), request.getContext(), call, request.isExit());
        }

    }

    /**
     * 下载文件get
     */
    public static void downGet(String url,FileCallBack fileCallBack) {
        OkHttpUtils.get()
                .url(url).build().connTimeOut(1000*30)
                .execute(fileCallBack);
    }

    /**
     * 下载文件post
     */
    public static void downPost(String url, FileCallBack fileCallBack) {
        OkHttpUtils.post()
                .url(url).build().connTimeOut(1000*30)
                .execute(fileCallBack);
    }
    /**
     * 取消请求
     */
    public static void cancel(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }

}
