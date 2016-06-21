package com.pactera.chengguan.municipal.model.municipal;

import java.io.Serializable;

/**
 * 图片数据
 * Created by huang hua
 * 2016/3/11.
 */
public class PicData implements Serializable {

    private String url;         //地址
    private String date;        //时间
    private String name;        //名称

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
