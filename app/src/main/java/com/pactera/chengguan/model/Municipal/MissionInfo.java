package com.pactera.chengguan.model.Municipal;

import java.util.List;

/**
 * 工作联系单信息
 * Created by huang hua
 * 2016/3/9.
 */
public class MissionInfo {

    public static final int MISSION_WORK = 1;   //任务单
    public static final int NORMAL_TASK = 2;    //普通任务联系单
    public static final int IMPORTANT_TASK = 3; //重大任务联系单

    public static final String[] dealStatusName = {"待办", "处理中", "办结"};
    public static final String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月"
            , "九月", "十月", "十一月", "十二月"};

    private int type;           //类型 (MISSION_WORK，NORMAL_TASK，IMPORTANT_TASK)
    private int dealStatus;     //处理状态
    private int missionStatus;  //任务状态
    private int month;          //月份
    private String title;       //标题
    private String content;     //内容
    private String startDate;   //开始时间
    private String endDate;     //工期结束时间
    private String operateUnit; //作业单位
    private String manager;     //项目经理

    /**
     * 任务上报
     */
    class MissionReport{
        private String reportUnit;      //上报单位
        private String reportManager;   //上报项目经理
        private String reportDate;      //上报日期
        private List<PicData> beforePic;//上报图片
        private List<String> beforeFile;//上报附件
    }

    /**
     * 任务下派
     */
    class MissionAssign{
        private String assignUnit;      //下发的单位
        private String fundSource;      //资金来源
        private String assignOpinion;   //下发意见
    }

    /**
     * 任务处理
     */
    class MissionProcess{
        private List<PicData> afterPic;    //处理后照片 时间-Url
        private List<String> afterFile;     //处理后附件
        private String processContent;  //处理内容
    }

}
