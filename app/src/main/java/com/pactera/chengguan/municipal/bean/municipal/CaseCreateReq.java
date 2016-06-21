package com.pactera.chengguan.municipal.bean.municipal;

import java.util.List;

/**
 * 新建案件请求对象
 * Created by huang hua
 * 2016/3/21.
 */
public class CaseCreateReq {

    //操作类别 1.保存，2.下发
    public int type;
    //案件id 如果新建案件发null
    public String caseId;
    //案件描述
    public String description;
    //考核扣分
    public String point;
    //期限时间
    public int taskTerm;
    //案件地址
    public String case_addree;
    //经度
    public double longitude;
    //纬度
    public double latitude;
    //作业单位id
    public int unit;
    //类别 1.月度，2.季度，3.年度，4.日常
    public int category;
    //月份 1.一月。。。12,十二月
    public int month;
    //照片
    public List<String> photoList;

    public CaseCreateReq(){}

    public void setData(int type, String caseId, String description, String point, int taskTerm, String case_addree
            , double longitude, double latitude, int unit, int category, int month, List<String> photoList){
        this.type = type;
        this.caseId = caseId;
        this.description = description;
        this.point = point;
        this.taskTerm = taskTerm;
        this.case_addree = case_addree;
        this.longitude = longitude;
        this.latitude = latitude;
        this.unit = unit;
        this.category = category;
        this.month = month;
        this.photoList = photoList;
    }
}
