package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 案件考核请求
 * Created by huang hua
 * 2016/3/22.
 */
public class CaseCheckReq {

    //案件id
    public int caseid;
    //扣分依据分类
    public String type;
    //扣分分数
    public String point;
    //扣分计入月份 1.一月。。。12,十二月
    public int month;

    public CaseCheckReq(){}

    public void setData(int caseid, String type, String point, int month){
        this.caseid = caseid;
        this.type = type;
        this.point = point;
        this.month = month;
    }

}
