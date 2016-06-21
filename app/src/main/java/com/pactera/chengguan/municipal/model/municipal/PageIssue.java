package com.pactera.chengguan.municipal.model.municipal;

import java.util.ArrayList;

/**
 * 养护日志列表数据
 * Created by huang hua
 * 2016/4/22.
 */
public class PageIssue {

    /**
     * 养护日志待确认状态
     */
    public static final String ISSUE_STATUS_CONFIRM = "confirm";
    /**
     * 养护日志处理中状态
     */
    public static final String ISSUE_STATUS_PROCESS = "process";

    /**
     * 养护日志待审核状态
     */
    public static final String ISSUE_STATUS_CENSOR = "censor";

    /**
     * 养护日志结案状态
     */
    public static final String ISSUE_STATUS_FINISH = "finish";

    //id
    private long id;
    //编号
    private String numbering;
    //地址
    private String location;
    //问题描述
    private String issueDescribe;
    //问题状态
    private String issueStatue;
    //时间
    private String createTime;
    //养护前照片
    private ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> beforeTreatment;
    //养护后照片
    private ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> afterTreatment;
    //养护前的缩略图
    private ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> beforeTreatmentS;
    //养护后的缩略图
    private ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> afterTreatmentS;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> getBeforeTreatment() {
        return beforeTreatment;
    }

    public void setBeforeTreatment(ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> beforeTreatment) {
        this.beforeTreatment = beforeTreatment;
    }

    public ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> getAfterTreatment() {
        return afterTreatment;
    }

    public void setAfterTreatment(ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> afterTreatment) {
        this.afterTreatment = afterTreatment;
    }

    public ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> getBeforeTreatmentS() {
        return beforeTreatmentS;
    }

    public void setBeforeTreatmentS(ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> beforeTreatmentS) {
        this.beforeTreatmentS = beforeTreatmentS;
    }

    public ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> getAfterTreatmentS() {
        return afterTreatmentS;
    }

    public void setAfterTreatmentS(ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> afterTreatmentS) {
        this.afterTreatmentS = afterTreatmentS;
    }
}
