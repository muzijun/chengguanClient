package com.pactera.chengguan.bean;

import java.io.Serializable;

/**
 * Created by huang hua
 * 2016/3/15.
 */
public interface BaseHandler{

    void doSuccess(BaseBean baseBean, String message);

    void doError(int result, String message);

}
