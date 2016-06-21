package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 结束任务联系单
 * Created by huang hua
 * 2016/5/5.
 */
public class CloseContactReq {

    //工作联系单id
    public int id;
    //理由
    public String reason;

    public CloseContactReq(){}

    public void setData(int id, String reason){
        this.id = id;
        this.reason = reason;
    }

}
