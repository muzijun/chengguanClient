package com.pactera.chengguan.municipal.model;

import java.io.Serializable;

/**
 * 推送内容
 * Created by lijun on 2016/5/30.
 */
public class PushData implements Serializable {
    //类型
    private String type;
    //内容
    private String message;
    //标题
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
