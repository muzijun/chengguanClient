package com.pactera.chengguan.model;

import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.util.BaseCallback;

import java.util.ArrayList;

/**  请求对象
 * Created by lijun on 2015/12/16.
 */
public class RequestPair {
    private BaseActivity context;
    //请求UI
    private String url="";
    //请求进度条的标题
    private String progressTitle="";
    //是否显示进度条
    private boolean LoadingShow=false;
    //返回类型
    private BaseCallback request;
    //是否退出activity
    private boolean isExit=false;
    //请求方法
    private String method="get";
    //请求参数
    private ArrayList<RequestParam> params=new ArrayList<RequestParam>();
    //上传文件
    private ArrayList<RequestFile> params_files=new ArrayList<RequestFile>();

    public BaseActivity getContext() {
        return context;
    }

    public void setContext(BaseActivity context) {
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

    public BaseCallback getRequest() {
        return request;
    }

    public void setRequest(BaseCallback request) {
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

    public ArrayList<RequestParam> getParams() {
        return params;
    }

    public void setParams(ArrayList<RequestParam> params) {
        this.params = params;
    }

    public ArrayList<RequestFile> getParams_files() {
        return params_files;
    }

    public void setParams_files(ArrayList<RequestFile> params_files) {
        this.params_files = params_files;
    }

    public boolean isLoadingShow() {
        return LoadingShow;
    }

}
