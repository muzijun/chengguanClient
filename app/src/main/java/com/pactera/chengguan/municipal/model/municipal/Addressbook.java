package com.pactera.chengguan.municipal.model.municipal;

/**
 * 通讯录
 * Created by huang hua
 * 2016/4/21.
 */
public class Addressbook {

    //姓名
    private String name;
    //电话
    private String telephone;
    //邮箱
    private String email;
    //部门名称
    private String organizationName;
    //部门id
    private int organization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrganization() {
        return organization;
    }

    public void setOrganization(int organization) {
        this.organization = organization;
    }
}
