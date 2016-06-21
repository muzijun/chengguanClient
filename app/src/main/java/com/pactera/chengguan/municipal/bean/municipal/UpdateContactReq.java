package com.pactera.chengguan.municipal.bean.municipal;

import java.util.List;

/**
 * 下发任务联系单请求
 * Created by huang hua
 * 2016/5/5.
 */
public class UpdateContactReq {

    //工作联系单id
    public int id;
    //业主单位
    public String owner;
    //签字情况描述
    public String ownerDescription;
    //下达单位
    public String issuedCompanyId;
    //业主方的工期
    public String ownerSchedule;
    //部门负责人
    public String departmentHead;
    //资金来源
    public String sourcesFund;
    //分管领导意见
    public String leaderOpinion;
    //联系单类型
    public String contractType;
    //附件路径
    public List<String> attachmentList;
    //是否下达 0：保存  1：下达
    public int issued;

    public UpdateContactReq(){}

    public void setData(int id, String owner, String ownerDescription, String issuedCompanyId
            , String ownerSchedule, String departmentHead, String sourcesFund, String leaderOpinion,String contractType
            , List<String> attachmentList, int issued) {
        this.id = id;
        this.owner = owner;
        this.ownerDescription = ownerDescription;
        this.issuedCompanyId = issuedCompanyId;
        this.ownerSchedule = ownerSchedule;
        this.departmentHead = departmentHead;
        this.sourcesFund = sourcesFund;
        this.leaderOpinion = leaderOpinion;
        this.attachmentList = attachmentList;
        this.issued = issued;
        this.contractType=contractType;
    }
}
