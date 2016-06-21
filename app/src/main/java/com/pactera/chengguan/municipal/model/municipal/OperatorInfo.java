package com.pactera.chengguan.municipal.model.municipal;

/**
 * 个人信息
 * Created by huang hua
 * 2016/5/3.
 */
public class OperatorInfo {

    //id
    private String operatorId = "";
    //部门id
    private String organizationid = "";
    //姓名
    private String operatorDisplayname = "";
    //部门名称
    private String organizationname = "";
    //电话
    private String operatorPhone = "";
    //手机
    private String operatorTelephone = "";
    //电邮
    private String operatorEmail = "";
    //账户
    private String operatorLoginname = "";

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid;
    }

    public String getOperatorDisplayname() {
        return operatorDisplayname;
    }

    public void setOperatorDisplayname(String operatorDisplayname) {
        this.operatorDisplayname = operatorDisplayname;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorTelephone() {
        return operatorTelephone;
    }

    public void setOperatorTelephone(String operatorTelephone) {
        this.operatorTelephone = operatorTelephone;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getOperatorLoginname() {
        return operatorLoginname;
    }

    public void setOperatorLoginname(String operatorLoginname) {
        this.operatorLoginname = operatorLoginname;
    }
}
