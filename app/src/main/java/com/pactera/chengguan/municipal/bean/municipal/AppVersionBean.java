package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;

/**
 * 获取app版本
 * Created by huang hua
 * 2016/5/3.
 */
public class AppVersionBean extends BaseBean {

    public Data datas;

    public static class Data{
        //地址
        public String url = "";
        //说明
        public String desc = "";
        //大小
        public long fileSize;
    }

}
