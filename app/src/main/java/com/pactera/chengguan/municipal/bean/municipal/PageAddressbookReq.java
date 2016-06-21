package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 获取通讯录列表请求
 * Created by huang hua
 * 2016/4/21.
 */
public class PageAddressbookReq {

    public String name;
    public String telephone;
    public String organization;

    public PageAddressbookReq(){}

    public void setData(String name, String telephone, String organization){
        this.name = name;
        this.telephone = telephone;
        this.organization = organization;
    }

}
