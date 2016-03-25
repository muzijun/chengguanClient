package com.pactera.chengguan.model.municipal;

import com.pactera.chengguan.bean.municipal.CaseListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 案件信息
 * Created by huang hua
 * 2016/3/7.
 */
public class CaseInfo implements Serializable{

    public static final String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月"
            , "九月", "十月", "十一月", "十二月"};
    public static final int CASE_NEW = 0;       //案件新增
    public static final int CASE_PROCESS = 1;   //案件处理中
    public static final int CASE_CHECK = 2;     //案件待审核
    public static final int CASE_FINISH = 3;    //案件结案

    private int id;             //案件id
    private int caseStatus;     //案件状态   0. 案件新增，1.案件处理中，2. 案件待审核，3. 案件结案
    private int category;       //类别    1.月度，2.季度，3.年度，4.日常
    private int month;          //月份
    private String date;        //日期
    private int termTime;       //期限时间
    private String location;    //案件地址
    private double longitude;   //经度
    private double latitude;    //纬度
    private String description; //案件描述
    private int checkPoint;     //考核扣分
    private int operateUnitId; //作业单位id
    private String operateContent;  //作业内容
    private List<PicData> beforePic; //处理前照片 时间-Url
    private List<PicData> afterPic;//处理后照片 时间-Url

    private List<CaseProcessLog> processLogList;    //流程日志
    private List<CaseExceedLog> exceedLogList;      //延期记录

    public CaseInfo(){
    }

    /**
     * 办理日志
     */
    class CaseProcessLog{
        private String linkContent;     //环节名
        private String attn;            //经办人
        private String processDate;     //操作时间
        private String opinion;         //备注
    }

    /**
     * 延期记录
     */
    class CaseExceedLog{
        private String exceedDate;      //延期时间
        private int exceedDays;         //延期天数
        private String attn;            //经办人
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(int caseStatus) {
        this.caseStatus = caseStatus;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTermTime() {
        return termTime;
    }

    public void setTermTime(int termTime) {
        this.termTime = termTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(int checkPoint) {
        this.checkPoint = checkPoint;
    }

    public int getOperateUnitId() {
        return operateUnitId;
    }

    public void setOperateUnitId(int operateUnitId) {
        this.operateUnitId = operateUnitId;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public List<PicData> getBeforePic() {
        return beforePic;
    }

    public void setBeforePic(List<PicData> beforePic) {
        this.beforePic = beforePic;
    }

    public List<PicData> getAfterPic() {
        return afterPic;
    }

    public void setAfterPic(List<PicData> afterPic) {
        this.afterPic = afterPic;
    }

    public List<CaseProcessLog> getProcessLogList() {
        return processLogList;
    }

    public void setProcessLogList(List<CaseProcessLog> processLogList) {
        this.processLogList = processLogList;
    }

    public List<CaseExceedLog> getExceedLogList() {
        return exceedLogList;
    }

    public void setExceedLogList(List<CaseExceedLog> exceedLogList) {
        this.exceedLogList = exceedLogList;
    }

    public List<String> getBeforePicUrlList(){
        List<String> urlList = new ArrayList<String>();
        for(PicData pic : beforePic){
            urlList.add(pic.getUrl());
        }
        return urlList;
    }
}