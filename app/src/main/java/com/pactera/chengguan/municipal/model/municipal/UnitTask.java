package com.pactera.chengguan.municipal.model.municipal;

import java.util.List;

/**
 * 作业单位问题报备
 * Created by huang hua
 * 2016/3/11.
 */
public class UnitTask {

    private int id;             //id
    private String operateUnit; //作业单位
    private int month;          //月份
    private String date;        //日期
    private String location;    //地址
    private String description; //描述
    private String operateContent;  //作业内容
    private List<PicData> beforePic; //处理前照片 时间-Url
    private List<PicData> afterPic;//处理后照片 时间-Url

}
