package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 获取任务单列表请求
 * Created by huang hua
 * 2016/5/9.
 */
public class TaskListReq {

    //作业单位id
    public int sectionId;
    //月份
    public Integer month;
    //标签 1.待办，2.处理中，3.办结
    public int option;
    //每页显示数
    public int pagecount;
    //最后一个id
    public int lastid;

    public TaskListReq(){}

    public void setData(int sectionId, Integer month, int option, int pagecount, int lastid){
        this.sectionId = sectionId;
        this.month = month;
        this.option = option;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}
