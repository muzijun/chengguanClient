package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.PageIssue;
import com.pactera.chengguan.municipal.model.municipal.PicData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取养护日志列表Bean
 * Created by huang hua
 * 2016/4/21.
 */
public class PageIssueBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //id
        public long id;
        //编号
        public String numbering = "";
        //地址
        public String location = "";
        //问题描述
        public String issueDescribe = "";
        //问题状态
        public String issueStatue = "";
        //时间
        public String createTime = "";
        //养护前照片
        public ArrayList<Photo> beforeTreatment;
        //养护后照片
        public ArrayList<Photo> afterTreatment;
        //养护前的缩略图
        public ArrayList<Photo> beforeTreatmentS;
        //养护后的缩略图
        public ArrayList<Photo> afterTreatmentS;

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

        public PageIssue transformToIssue(){
            PageIssue pageIssue = new PageIssue();
            pageIssue.setId(id);
            pageIssue.setNumbering(numbering);
            pageIssue.setLocation(location);
            pageIssue.setIssueDescribe(issueDescribe);
            pageIssue.setIssueStatue(issueStatue);
            pageIssue.setCreateTime(createTime);
            pageIssue.setBeforeTreatment(setPhotoData(beforeTreatment));
            pageIssue.setAfterTreatment(setPhotoData(afterTreatment));
            pageIssue.setBeforeTreatmentS(setPhotoData(beforeTreatmentS));
            pageIssue.setAfterTreatmentS(setPhotoData(afterTreatmentS));
            return pageIssue;
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

    }

    public List<PageIssue> transformPageIssueList(){
        List<PageIssue> pageIssueList = new ArrayList<PageIssue>();
        if(datas == null || datas.size() <= 0){
            return pageIssueList;
        }
        for(Data data : datas){
            PageIssue pageIssue = data.transformToIssue();
            pageIssueList.add(pageIssue);
        }
        return pageIssueList;
    }

}
