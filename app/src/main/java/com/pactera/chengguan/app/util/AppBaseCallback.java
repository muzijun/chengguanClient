package com.pactera.chengguan.app.util;
import android.app.Activity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.app.error.NullStringToEmptyAdapterFactory;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.zhy.http.okhttp.callback.Callback;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lijun on 2015/12/16.
 */
public class AppBaseCallback extends Callback<String> {
    protected Class<?> mClazz;
    protected Activity mContext;
    protected RequestListener mCallBackListener;
    protected String reqUrl;

    public AppBaseCallback(Class<?> clazz, RequestListener mCallBackListener, Activity mContext, String reqUrl) {
        super();
        this.mClazz = clazz;
        this.mContext = mContext;
        this.mCallBackListener = mCallBackListener;
        this.reqUrl = reqUrl;
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        mCallBackListener.fail();
    }

    @Override
    public void onResponse(String response, int id) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        mCallBackListener.success(reqUrl, gson.fromJson(response.toString(), mClazz));
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        ProgressDlgUtil.stopProgressDlg();

    }
}
