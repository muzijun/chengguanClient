package com.pactera.chengguan.bean;

import android.content.Context;

import com.pactera.chengguan.base.ChengApplication;
import com.pactera.chengguan.config.Contants;

/**
 * 基类bean
 * Created by huang hua
 * 2016/3/15.
 */
public class BaseBean {

    private static final String TAG = "BaseBean";

    public String message;
    public int result;

    public void checkResult(Context context, BaseHandler handler){
        if(result == Contants.RESULT_OK){
            handler.doSuccess(this, message);
        }else{
            if(result == Contants.ERROR_SESSION){
                ChengApplication.instance.sessionErrorToLogin(context);
            }else {
                handler.doError(result, message);
            }
        }
    }

}
