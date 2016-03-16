package com.pactera.chengguan.model;

/**请求参数
 * Created by lijun on 2015/12/16.
 */
public class RequestParam {
    private String key;
    private String value;

    public RequestParam(){
    }

    public RequestParam(String key, String value){
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
