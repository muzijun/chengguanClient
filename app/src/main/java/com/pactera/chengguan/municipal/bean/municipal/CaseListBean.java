package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.CaseInfo;
import com.pactera.chengguan.municipal.model.municipal.PicData;

import java.util.ArrayList;
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
        public int caseStatue;      //案件状态
        public int sort;            //类别
        public int month;           //月份
        public String createTime = "";   //日期
        public int remainDays;      //期限日期
        public String caseAddress = "";  //案件地址
        public double longitude;    //地址经度
        public double latitude;     //地址纬度
        public String caseDescribe = ""; //案件描述
        public String points = "";          //考核扣分
        public int sectionId;       //作业单位
//        public String result = "";       //作业内容 已废弃，改用Task.result
        public Task task;
        public ArrayList<Photo> beforeTreatment;  //处理前照片
        public ArrayList<Photo> afterTreatment;   //处理后照片
        public ArrayList<Photo> beforeTreatmentS; //处理前的缩略图
        public ArrayList<Photo> afterTreatmentS;  //处理后的缩略图

        public static class Task{
            public String taskId = "";
            public String result = "";
        }

        public static class Photo{
            public String photoPath;   //照片地址
            public String createTime;  //照片时间
            public String photoName;    //照片名称
        }

        public void transformData(CaseInfo caseInfo){
            caseInfo.setId(caseId);
            caseInfo.setCaseStatus(caseStatue);
            caseInfo.setCategory(sort);
            caseInfo.setMonth(month);
            caseInfo.setDate(createTime);
            caseInfo.setTermTime(remainDays);
            caseInfo.setLocation(caseAddress);
            caseInfo.setLongitude(longitude);
            caseInfo.setLatitude(latitude);
            caseInfo.setDescription(caseDescribe);
            caseInfo.setCheckPoint(points);
            caseInfo.setOperateUnitId(sectionId);
            if(task != null) {
                caseInfo.setOperateContent(task.result);
            }
            caseInfo.setBeforePic(setPhotoData(beforeTreatment));
            caseInfo.setAfterPic(setPhotoData(afterTreatment));
            caseInfo.setBeforeSmallPic(setPhotoData(beforeTreatmentS));
            caseInfo.setAfterSmallPic(setPhotoData(afterTreatmentS));
        }

        private ArrayList<PicData> setPhotoData(List<Photo> photoList){
            if(photoList == null){
                return null;
            }
            ArrayList<PicData> picDataList = new ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData>();
            for(Photo photo : photoList){
                PicData pic = new PicData();
                pic.setUrl(photo.photoPath);
                pic.setDate(photo.createTime);
                pic.setName(photo.photoName);
                picDataList.add(pic);
            }
            return picDataList;
        }

    }

    public List<CaseInfo> transformCaseInfo(){
        List<CaseInfo> caseInfoList = new ArrayList<CaseInfo>();
        if(datas == null || datas.size() <= 0){
            return caseInfoList;
        }
        for(Data data : datas){
            CaseInfo caseInfo = new CaseInfo();
            data.transformData(caseInfo);
            caseInfoList.add(caseInfo);
        }
        return caseInfoList;
    }

}
