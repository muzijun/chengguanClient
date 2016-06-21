package com.pactera.chengguan.app.model;

/**请求参数
 * Created by lijun on 2015/12/16.
 */
public class AppRequestParam {
    private String key;
    private String value;

    public AppRequestParam(){
    }

    public AppRequestParam(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
