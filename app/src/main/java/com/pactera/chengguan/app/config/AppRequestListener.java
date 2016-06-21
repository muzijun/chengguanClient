package com.pactera.chengguan.app.config;

/** 网络返回接口
 * Created by lijun on 2015/12/30.
 */
public interface AppRequestListener {
    public abstract void success(String reqUrl, Object result);
    public abstract void fail();
}
