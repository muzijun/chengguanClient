package com.pactera.chengguan.municipal.model.municipal;

import java.io.Serializable;

/**
 * 巡查记录
 * Created by huang hua
 * 2016/4/12.
 */
public class PatrolRecord implements Serializable{
    /**
     * 巡查正常状态
     */
    public static final String PATROL_RECORD_SUCCESS = "success";

    /**
     * 巡查异常状态
     */
    private static final String PATROL_RECORD_FAIL = "fail";

    /**正常状态*/
    public static final int STATUS_SUCCESS = 1;
    /**异常状态*/
    public static final int STATUS_FAIL = 2;
    /**异常已扣分状态*/
    public static final int STATUS_POINTS_DONE = 3;
    /**异常已修改正常状态*/
    public static final int STATUS_MODIFY_SUCCESS = 4;

    //记录id
    private int recordId;
    //状态
    private String statue;
    //道路或桥梁id
    private int fromId;
    //道路或桥梁名称
    private String fromName;
    //扣分
    private String points;
    //修改原因
    private String comment;
    //道路或桥梁类型
    private String baseType;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    /**
     * 获得巡查记录的状态
     * @return
     */
    public int getRecordStatus(){
        if(statue.equals(PATROL_RECORD_SUCCESS)){
            if(comment != null && comment.length() > 0){
                return STATUS_MODIFY_SUCCESS;
            }else{
                return STATUS_SUCCESS;
            }
        }else{
            double dPoint = Double.parseDouble(points);
            if(dPoint > 0){
                return STATUS_POINTS_DONE;
            }else{
                return STATUS_FAIL;
            }
        }
    }

}
