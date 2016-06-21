package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 巡查任务异常扣分请求
 * Created by huang hua
 * 2016/4/13.
 */
public class PointsPatrolReq {

    public int objectId;
    public String objectType;
    public String basicsType;

    public PointsPatrolReq(){}

    public void setData(int objectId, String objectType, String basicsType){
        this.objectId = objectId;
        this.objectType = objectType;
        this.basicsType = basicsType;
    }

}
