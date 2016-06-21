package com.pactera.chengguan.municipal.bean.municipal;

import java.util.List;

/**
 * 新增任务单请求
 * Created by huang hua
 * 2016/5/9.
 */
public class AddTaskFormReq {

    //任务单id
    public String workFormId;
    //作业单位id
    public int sectionId;
    //联系依据
    public String rationale;
    //资金来源
    public String sourcesFund;
    //完成时间
    public long finishDate;
    //工作任务描述
    public String description;
    //位置
    public String location;
    //业主单位
    public String owner;
    //部门负责人
    public String departmentHead;
    //处理前图片
    public List<String> photoList;
    //附件列表
    public List<String> attachmentList;
    //是否下发  0：保存 1：下发
    public int issued;

    public AddTaskFormReq(){}

    public void setData(String workFormId, int sectionId, String rationale, String sourcesFund
            , long finishDate, String description, String location, String owner, String departmentHead
            , List<String> photoList, List<String> attachmentList, int issued) {
        this.workFormId = workFormId;
        this.sectionId = sectionId;
        this.rationale = rationale;
        this.sourcesFund = sourcesFund;
        this.finishDate = finishDate;
        this.description = description;
        this.location = location;
        this.owner = owner;
        this.departmentHead = departmentHead;
        this.photoList = photoList;
        this.attachmentList = attachmentList;
        this.issued = issued;
    }
}
