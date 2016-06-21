package com.pactera.chengguan.app.model;

import android.app.Activity;

import com.pactera.chengguan.app.util.AppBaseCallback;

import java.util.ArrayList;

/**  请求对象
 * Created by lijun on 2015/12/16.
 */
public class AppRequestPair {
    protected Activity context;
    //请求UI
    protected String url="";
    //请求进度条的标题
    protected String progressTitle="";
    //是否显示进度条
    protected boolean LoadingShow=false;
    //返回类型
    protected AppBaseCallback request;
    //是否退出activity
    protected boolean isExit=false;
    //请求方法
    protected String method="get";
    //请求参数
    protected ArrayList<AppRequestParam> params=new ArrayList<AppRequestParam>();
    //上传文件
    protected ArrayList<AppRequestFile> params_files=new ArrayList<AppRequestFile>();

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProgressTitle() {
        return progressTitle;
    }

    public void setProgressTitle(String progressTitle) {
        this.progressTitle = progressTitle;
    }

    public boolean getLoadingShow() {
        return LoadingShow;
    }

    public void setLoadingShow(boolean loadingShow) {
        LoadingShow = loadingShow;
    }

    public AppBaseCallback getRequest() {
        return request;
    }

    public void setRequest(AppBaseCallback request) {
        this.request = request;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setIsExit(boolean isExit) {
        this.isExit = isExit;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<AppRequestParam> getParams() {
        return params;
    }

    public void setParams(ArrayList<AppRequestParam> params) {
        this.params = params;
    }

    public ArrayList<AppRequestFile> getParams_files() {
        return params_files;
    }

    public void setParams_files(ArrayList<AppRequestFile> params_files) {
        this.params_files = params_files;
    }

    public boolean isLoadingShow() {
        return LoadingShow;
    }

}
