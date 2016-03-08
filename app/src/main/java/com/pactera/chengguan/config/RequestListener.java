package com.pactera.chengguan.config;

/** 网络返回接口
 * Created by lijun on 2015/12/30.
 */
public interface RequestListener {
    public abstract void success(Object result);
    public abstract void fail();
}
