package com.pactera.chengguan.bean;

import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.util.LogUtils;

/**
 * 基类bean
 * Created by huang hua
 * 2016/3/15.
 */
public abstract class BaseBean {

    private static final String TAG = "BaseBean";

    public String message;
    public int result;

    public void checkResult(BaseHandler handler){
        if(result == Contants.RESULT_OK){
            handler.doSuccess(this, message);
        }else{
            LogUtils.e(TAG, "result:"+result+" | message:"+message);
            handler.doError(result, message);
        }
    }

}
