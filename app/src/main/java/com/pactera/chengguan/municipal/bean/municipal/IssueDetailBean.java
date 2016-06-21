package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.IssueDetail;
import com.pactera.chengguan.municipal.model.municipal.PicData;

import java.util.ArrayList;

/**
 * 养护日志详情Bean
 * Created by huang hua
 * 2016/4/22.
 */
public class IssueDetailBean extends BaseBean {

    public Data datas;

    public static class Data{
        //id
        public long id;
        //编号
        public String numbering = "";
        //作业单位
        public int sectionId;
        //地址
        public String location = "";
        //问题描述
        public String issueDescribe = "";
        //问题状态
        public String issueStatue = "";
        //是否返工
        public String isRefuse;
        //时间
        public String createTime = "";
        //处理结果
        public String result = "";
        //作业单位名称
        public String sectionName = "";
        //返工意见列表
        public ArrayList<Refuse> refuseOpinion;
        //养护前照片
        public ArrayList<Photo> beforeTreatment;
        //养护后照片
        public ArrayList<Photo> afterTreatment;

        public static class Refuse{
            //原因
            public String refuseOpinion = "";
            //时间
            public String createTime = "";
        }

        public static class Photo{
            //名称
            public String photoName = "";
            //路径
            public String photoPath = "";
            //时间
            public String createTime = "";
            //排序
            public int orderNum;
        }

        private ArrayList<PicData> setPhotoData(ArrayList<Photo> photoList){
            if(photoList == null){
                return null;
            }
            ArrayList<PicData> picDataList = new ArrayList<PicData>();
            for(Photo photo : photoList){
                PicData pic = new PicData();
                pic.setUrl(photo.photoPath);
                pic.setDate(photo.createTime);
                pic.setName(photo.photoName);
                picDataList.add(pic);
            }
            return picDataList;
        }

        private ArrayList<IssueDetail.Refuse> setRefuseListData(){
            if(refuseOpinion == null){
                return null;
            }
            ArrayList<IssueDetail.Refuse> refuseList = new ArrayList<IssueDetail.Refuse>();
            for(Refuse refuse : refuseOpinion){
                IssueDetail.Refuse iRefuse = new IssueDetail.Refuse();
                iRefuse.setRefuseOpinion(refuse.refuseOpinion);
                iRefuse.setCreateTime(refuse.createTime);
                refuseList.add(iRefuse);
            }
            return refuseList;
        }

    }

    public IssueDetail transformToIssueDetail(){
        if(datas == null){
            return null;
        }
        IssueDetail issueDetail = new IssueDetail();
        issueDetail.setId(datas.id);
        issueDetail.setNumbering(datas.numbering);
        issueDetail.setSectionId(datas.sectionId);
        issueDetail.setLocation(datas.location);
        issueDetail.setIssueDescribe(datas.issueDescribe);
        issueDetail.setIssueStatue(datas.issueStatue);
        issueDetail.setIsRefuse(datas.isRefuse);
        issueDetail.setCreateTime(datas.createTime);
        issueDetail.setResult(datas.result);
        issueDetail.setSectionName(datas.sectionName);
        issueDetail.setRefuseList(datas.setRefuseListData());
        issueDetail.setBeforeTreatment(datas.setPhotoData(datas.beforeTreatment));
        issueDetail.setAfterTreatment(datas.setPhotoData(datas.afterTreatment));
        return issueDetail;
    }

}
