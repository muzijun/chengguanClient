package com.pactera.chengguan.municipal.bean.municipal;

/**
 * Created by huang hua
 * 2016/5/9.
 */
public class RefuseTaskReq {

    //工作联系单id
    public int objectId;
    //返工意见
    public String refuseOpinion;

    public RefuseTaskReq(){}

    public void setData(int objectId, String refuseOpinion){
        this.objectId = objectId;
        this.refuseOpinion = refuseOpinion;
    }

}
