package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 更改巡查任务状态请求
 * Created by huang hua
 * 2016/4/12.
 */
public class UpdatePatrolRecordReq {

    //任务id
    public int objectId;
    //修改原因
    public String comment;
    //类型
    public String type;

    public UpdatePatrolRecordReq(){}

    public void setData(int objectId, String comment, String type){
        this.objectId = objectId;
        this.comment = comment;
        this.type = type;
    }

}
