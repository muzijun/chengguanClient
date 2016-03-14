package com.pactera.chengguan.model.Municipal;

import java.util.List;

/**
 * 案件信息
 * Created by huang hua
 * 2016/3/7.
 */
public class CaseInfo {

    public static final String[] dealStatusName = {"待办", "处理中", "办结"};
    public static final String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月"
            , "九月", "十月", "十一月", "十二月"};

    private int id;             //案件id
    private int dealStatus;     //处理状态
    private int caseStatus;     //案件状态
    private int category;       //类别
    private int month;          //月份
    private String date;        //日期
    private int termTime;       //期限时间
    private CaseLocation caseLocation;    //案件地址
    private String description; //案件描述
    private int checkPoint;     //考核扣分
    private String operateUnit; //作业单位
    private String operateContent;  //作业内容
    private List<PicData> beforePic; //处理前照片 时间-Url
    private List<PicData> afterPic;//处理后照片 时间-Url

    private List<CaseProcessLog> processLogList;    //流程日志
    private List<CaseExceedLog> exceedLogList;      //延期记录

    public CaseInfo(){
    }

    class CaseLocation{
        private String location;    //案件地址
        private double longitude;   //经度
        private double latitude;    //纬度
    }

    /**
     * 办理日志
     */
    class CaseProcessLog{
        private String linkContent;     //环节名
        private String attn;            //经办人
        private String processDate;     //操作时间
        private String opinion;         //备注
    }

    /**
     * 延期记录
     */
    class CaseExceedLog{
        private String exceedDate;      //延期时间
        private int exceedDays;         //延期天数
        private String attn;            //经办人
    }

}
