package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 巡查记录请求
 * Created by huang hua
 * 2016/4/12.
 */
public class PatrolRecordReq {

    //类别 1.本周期，2.上周期，3.历史
    public int type;
    //养护等级id
    public int maintenanceLevel;
    //排序方式
    public int orderType;
    //单页显示数量
    public int pagecount;
    //当前页最后一项id
    public int lastid;

    public PatrolRecordReq(){}

    public void setData(int type, int maintenanceLevel, int orderType, int pagecount, int lastid){
        this.type = type;
        this.maintenanceLevel = maintenanceLevel;
        this.orderType = orderType;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}
