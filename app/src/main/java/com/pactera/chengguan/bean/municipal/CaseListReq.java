package com.pactera.chengguan.bean.municipal;

/**
 * 获取案件列表请求
 * Created by huang hua
 * 2016/3/22.
 */
public class CaseListReq {

    //处理状态 1.待办，2.处理中，3.办结
    public int status;
    //类别 1.月度，2.季度，3.年度，4.日常
    public int category;
    //月份 1.一月。。。12,十二月
    public int month;
    //排序 1.按时间排序，2.按超限排序
    public int sort;
    //单页显示数量
    public int pagecount;
    //当前页最后一项id
    public int lastid;

    public CaseListReq(){}

    public void setData(int status, int category, int month, int sort, int pagecount, int lastid){
        this.status = status;
        this.category = category;
        this.month = month;
        this.sort = sort;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}
