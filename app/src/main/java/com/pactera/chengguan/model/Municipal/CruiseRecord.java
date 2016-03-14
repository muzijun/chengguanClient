package com.pactera.chengguan.model.Municipal;

/**
 * 巡查记录
 * Created by huang hua
 * 2016/3/11.
 */
public class CruiseRecord {

    public static final int CRUISE_OK = 1;      //巡查成功
    public static final int CRUISE_ERROR = 2;   //巡查异常

    private int id;             //id
    private int type;           //类型
    private String name;        //名称
    private int status;         //状态
    private int point;          //扣分分数
    private String modifyReason;//状态修改原因

}
