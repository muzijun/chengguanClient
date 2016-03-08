package com.pactera.chengguan.util;

import android.util.Log;

import com.google.gson.Gson;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.config.RequestListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by lijun on 2015/12/16.
 */
public  class BaseCallback extends Callback<String> {
    private  Class<?> mClazz;
    private BaseActivity mContext;
    protected RequestListener mCallBackListener;


    public BaseCallback(Class<?> clazz,RequestListener mCallBackListener,BaseActivity mContext) {
        super();
        this.mClazz = clazz;
        this.mContext=mContext;
        this.mCallBackListener=mCallBackListener;

    }

    @Override
    public String parseNetworkResponse(Response response) throws IOException {
        return response.body().string();
    }


    @Override
    public void onError(Request request, Exception e) {
        mCallBackListener.fail();
    }

    @Override
    public void onResponse(String response) {
//    mCallBackListener.success(new Gson().fromJson(response.toString(), mClazz));
    }

}
