package com.pactera.chengguan.model.municipal;

import java.util.List;

/**
 * 通知
 * Created by huang hua
 * 2016/3/11.
 */
public class Notice {

    private int id;             //id
    private int month;          //月份
    private int status;         //状态
    private String title;       //标题
    private String sender;      //发送人
    private List<String> units; //接收单位
    private String date;        //下发时间
    private String content;     //内容
    private List<String> files; //附件
    private List<NoticeReply> replys;   //回复

    class NoticeReply{
        private String unit;    //作业单位
        private String reply;   //回复内容
        private String date;    //回复时间
    }

}
