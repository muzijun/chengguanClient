package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.model.municipal.PicData;

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

        public void transformData(CaseInfo caseInfo){
            caseInfo.setId(caseId);
            caseInfo.setCaseStatus(caseStatus);
            caseInfo.setCategory(type);
            caseInfo.setMonth(month);
            caseInfo.setDate(createDate);
            caseInfo.setTermTime(deadlineDate);
            caseInfo.setLocation(case_addree);
            caseInfo.setLongitude(longitude);
            caseInfo.setLatitude(latitude);
            caseInfo.setDescription(caseDescribe);
            caseInfo.setCheckPoint(points);
            caseInfo.setOperateUnitId(companyId);
            caseInfo.setOperateContent(process_context);
            caseInfo.setBeforePic(setPhotoData(frontPhoto));
            caseInfo.setAfterPic(setPhotoData(backPhoto));
        }

        private List<PicData> setPhotoData(List<Photo> photoList){
            if(photoList == null){
                return null;
            }
            List<PicData> picDataList = new ArrayList<PicData>();
            for(Photo photo : photoList){
                PicData pic = new PicData();
                pic.setUrl(photo.photo_path);
                pic.setDate(photo.photo_time);
                picDataList.add(pic);
            }
            return picDataList;
        }

    }

    public List<CaseInfo> transformCaseInfo(){
        List<CaseInfo> caseInfoList = new ArrayList<CaseInfo>();
        for(Data data : datas){
            CaseInfo caseInfo = new CaseInfo();
            data.transformData(caseInfo);
            caseInfoList.add(caseInfo);
        }
        return caseInfoList;
    }

}
