package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 结束任务单请求
 * Created by huang hua
 * 2016/5/9.
 */
public class CloseTaskFormReq {

    //任务单id
    public int workFormId;
    //理由
    public String reason;

    public CloseTaskFormReq(){}

    public void setData(int workFormId, String reason){
        this.workFormId = workFormId;
        this.reason = reason;
    }

}
