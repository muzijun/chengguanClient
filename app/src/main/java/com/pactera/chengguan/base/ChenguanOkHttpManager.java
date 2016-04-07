package com.pactera.chengguan.base;

import android.widget.Toast;

import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.model.RequestFile;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.model.RequestParam;
import com.pactera.chengguan.util.NetUtils;
import com.pactera.chengguan.util.ProgressDlgUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;

/**
 * 请求管理
 * Created by lijun on 2015/12/9.
 */
public class ChenguanOkHttpManager {

    /**
     * 发送请求并判断是否需要登录
     * @param requestPair
     * @param isNeedToken
     */
    public static void requestIfNeedToken(RequestPair requestPair, boolean isNeedToken, String systemId){
        if(isNeedToken){
            ArrayList<RequestParam> params = requestPair.getParams();
            params.add(new RequestParam("access_token", ChengApplication.instance.access_token));
            params.add(new RequestParam("systemid", systemId));
            params.add(new RequestParam("loginMobile", ""+Contants.MOBILE_CHENGGUAN));
            requestPair.setParams(params);
        }
        request(requestPair);
    }

    public static void request(RequestPair requestPair) {
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
    protected static void get(RequestPair request) {
        RequestCall call = OkHttpUtils.get().url(request.getUrl()).build();
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
    protected static void post(RequestPair request) {
        PostFormBuilder builder = OkHttpUtils.post().url(request.getUrl());
        for (RequestParam param : request.getParams()) {
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
    protected static void upload(RequestPair request) {
        PostFormBuilder builder = OkHttpUtils.post().url(request.getUrl());
        for (RequestParam param : request.getParams()) {
            builder.addParams(param.getKey(), param.getValue());
        }
        for (RequestFile param_file : request.getParams_files()) {
            builder.addFile("fileuploadList", param_file.getName(), param_file.getFile());
        }
        RequestCall call = builder.build().connTimeOut(1000*30);
        call.execute(request.getRequest());
        if (request.getLoadingShow()) {
            ProgressDlgUtil.showProgressDlg(request.getProgressTitle(), request.getContext(), call, request.isExit());
        }

    }

    /**
     * 取消请求
     */
    public static void cancel(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }

}
