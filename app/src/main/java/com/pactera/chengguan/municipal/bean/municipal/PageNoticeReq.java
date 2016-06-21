package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 获取通知列表请求
 * Created by huang hua
 * 2016/4/25.
 */
public class PageNoticeReq {

    //月份
    public Integer month;
    //排序方式 1. 时间排序 2.状态排序
    public int orderType;
    //每页显示数
    public int pagecount;
    //最后一个id
    public String lastid;

    public PageNoticeReq(){}

    public void setData(Integer month, int orderType, int pagecount, String lastid){
        this.month = month;
        this.orderType = orderType;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}
