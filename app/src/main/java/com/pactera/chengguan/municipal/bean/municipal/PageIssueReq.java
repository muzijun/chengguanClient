package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 获取养护日志列表请求
 * Created by huang hua
 * 2016/4/21.
 */
public class PageIssueReq {

    //作业单位
    public int sectionId;
    //月份
    public Integer month;
    //标签 1：代办；2：处理中；3：办结
    public int option;
    //排序 1. 时间排序 2.状态排序
    public int orderType;
    //每页显示数
    public int pagecount;
    //最后一个id
    public long lastid;

    public PageIssueReq(){}

    public void setData(int option, int sectionId, Integer month, int orderType, int pagecount, long lastid){
        this.sectionId = sectionId;
        this.orderType = orderType;
        this.month = month;
        this.option = option;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}
