package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 添加通讯录请求
 * Created by huang hua
 * 2016/4/21.
 */
public class AddAddressbookReq {

    public String name;
    public String telephone;
    public String email;
    public int organization;
    public String organizationName;

    public AddAddressbookReq(){}

    public void setData(String name, String telephone, String email, int organization, String organizationName){
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.organization = organization;
        this.organizationName = organizationName;
    }

}
