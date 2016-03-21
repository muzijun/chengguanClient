package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;

import java.util.List;

/**
 * 案件列表Bean
 * 对应接口 CASE_LIST
 * Created by huang hua
 * 2016/3/17.
 */
public class CaseListBean extends BaseBean {

    public List<Data> datas;        //案件列表

    public static class Data{

        public int caseId;          //案件id
        public int disposeStatus;   //处理状态
        public int caseStatus;      //案件状态
        public int type;            //类别
        public int month;           //月份
        public String createDate;   //日期
        public int deadlineDate;    //期限日期
        public String case_addree;  //案件地址
        public double longitude;    //地址经度
        public double latitude;     //地址纬度
        public String caseDescribe; //案件描述
        public int points;          //考核扣分
        public int companyId;       //作业单位
        public String process_context;  //作业内容
        public List<Photo> frontPhoto;  //处理前照片
        public List<Photo> backPhoto;   //处理后照片

        public static class Photo{
            public String photo_path;   //照片地址
            public String photo_time;   //照片时间
        }

    }

}
