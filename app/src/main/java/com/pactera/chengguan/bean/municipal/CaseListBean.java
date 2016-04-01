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
        public int caseStatue;      //案件状态
        public int sort;            //类别
        public int month;           //月份
        public String createTime;   //日期
        public int remainDays;      //期限日期
        public String caseAddress;  //案件地址
        public double longitude;    //地址经度
        public double latitude;     //地址纬度
        public String caseDescribe; //案件描述
        public int points;          //考核扣分
        public int sectionId;       //作业单位
        public String result;       //作业内容
        public List<Photo> beforeTreatment;  //处理前照片
        public List<Photo> afterTreatment;   //处理后照片

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
            caseInfo.setOperateContent(result);
            caseInfo.setBeforePic(setPhotoData(beforeTreatment));
            caseInfo.setAfterPic(setPhotoData(afterTreatment));
        }

        private List<PicData> setPhotoData(List<Photo> photoList){
            if(photoList == null){
                return null;
            }
            List<PicData> picDataList = new ArrayList<PicData>();
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
