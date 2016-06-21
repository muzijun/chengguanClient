package com.pactera.chengguan.municipal.util;

import android.app.Activity;

import com.pactera.chengguan.app.util.AppBaseCallback;
import com.pactera.chengguan.municipal.config.RequestListener;

/**
 * Created by lijun on 2015/12/16.
 */
public class BaseCallback extends AppBaseCallback {
    public BaseCallback(Class<?> clazz, RequestListener mCallBackListener, Activity mContext, String reqUrl) {
        super(clazz,mCallBackListener,mContext,reqUrl);

    }
}
