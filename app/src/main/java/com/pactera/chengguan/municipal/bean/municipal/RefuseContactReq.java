package com.pactera.chengguan.municipal.bean.municipal;

/**
 * Created by huang hua
 * 2016/5/5.
 */
public class RefuseContactReq {

    //工作联系单id
    public String objectId;
    //返工意见
    public String refuseOpinion;

    public RefuseContactReq(){}

    public void setData(String objectId, String refuseOpinion){
        this.objectId = objectId;
        this.refuseOpinion = refuseOpinion;
    }

}
