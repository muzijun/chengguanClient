package com.pactera.chengguan.municipal.model.municipal;

import java.io.Serializable;

/**
 * 任务单
 * Created by huang hua
 * 2016/5/9.
 */
public class ListTask implements Serializable {

    /**
     * 新增
     */
    public static final String TASKFORM_STATUS_NEW = "new";

    /**
     * 施工
     */
    public static final String TASKFORM_STATUS_BUILDE = "builde";

    /**
     * 待验收
     */
    public static final String TASKFORM_STATUS_ACCEPT = "accept";

    /**
     * 办结
     */
    public static final String TASKFORM_STATUS_FINISH = "finish";

    //任务单id
    private int workFormId;
    //编号
    private String numbering = "";
    //作业单位
    private int sectionId;
    //联系依据
    private String rationale = "";
    //资金来源
    private String sourcesFund = "";
    //完成时间
    private String finishDate = "";
    //工作任务描述
    private String description = "";
    //下发日期
    private String issuedDate = "";
    //状态
    private String status = "";

    public int getWorkFormId() {
        return workFormId;
    }

    public void setWorkFormId(int workFormId) {
        this.workFormId = workFormId;
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

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getSourcesFund() {
        return sourcesFund;
    }

    public void setSourcesFund(String sourcesFund) {
        this.sourcesFund = sourcesFund;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
