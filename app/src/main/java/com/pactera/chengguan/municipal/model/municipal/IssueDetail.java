package com.pactera.chengguan.municipal.model.municipal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 养护日志详情
 * Created by huang hua
 * 2016/4/22.
 */
public class IssueDetail implements Serializable{

    //id
    private long id;
    //编号
    private String numbering;
    //作业单位
    private int sectionId;
    //地址
    private String location;
    //问题描述
    private String issueDescribe;
    //问题状态
    private String issueStatue;
    //是否返工
    private String isRefuse;
    //时间
    private String createTime;
    //处理结果
    private String result;
    //作业单位名称
    private String sectionName;
    //返工意见列表
    private ArrayList<Refuse> refuseList;
    //养护前照片
    private ArrayList<PicData> beforeTreatment;
    //养护后照片
    private ArrayList<PicData> afterTreatment;

    public static class Refuse implements Serializable{
        //原因
        private String refuseOpinion;
        //时间
        private String createTime;

        public String getRefuseOpinion() {
            return refuseOpinion;
        }

        public void setRefuseOpinion(String refuseOpinion) {
            this.refuseOpinion = refuseOpinion;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIssueDescribe() {
        return issueDescribe;
    }

    public void setIssueDescribe(String issueDescribe) {
        this.issueDescribe = issueDescribe;
    }

    public String getIssueStatue() {
        return issueStatue;
    }

    public void setIssueStatue(String issueStatue) {
        this.issueStatue = issueStatue;
    }

    public String getIsRefuse() {
        return isRefuse;
    }

    public void setIsRefuse(String isRefuse) {
        this.isRefuse = isRefuse;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Refuse> getRefuseList() {
        return refuseList;
    }

    public void setRefuseList(ArrayList<Refuse> refuseList) {
        this.refuseList = refuseList;
    }

    public ArrayList<PicData> getBeforeTreatment() {
        return beforeTreatment;
    }

    public void setBeforeTreatment(ArrayList<PicData> beforeTreatment) {
        this.beforeTreatment = beforeTreatment;
    }

    public ArrayList<PicData> getAfterTreatment() {
        return afterTreatment;
    }

    public void setAfterTreatment(ArrayList<PicData> afterTreatment) {
        this.afterTreatment = afterTreatment;
    }
}
