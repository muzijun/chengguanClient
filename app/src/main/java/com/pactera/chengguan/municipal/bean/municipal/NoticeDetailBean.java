package com.pactera.chengguan.municipal.bean.municipal;


import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.NoticeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知详情Bean
 * Created by huang hua
 * 2016/4/25.
 */
public class NoticeDetailBean extends BaseBean {

    public Data datas;

    public static class Data{
        //通知ID
        public String noticeMessageId = "";
        //通知标题
        public String noticeTitle = "";
        //通知内容
        public String noticeContent = "";
        //发送时间
        public String issuedTime;
        //创建时间
        public String createTime;
        //状态
        public String status = "";
        //下发单位
        public String sections = "";
        //附件列表
        public List<FileData> photoList;
        //反馈列表
        public List<Respond> noticeRespondList;

        public static class FileData{
            //文件名称
            public String photoName = "";
            //文件路径
            public String photoPath = "";

            public NoticeDetail.FileData transformToFileData(){
                NoticeDetail.FileData fileData = new NoticeDetail.FileData();
                fileData.setPhotoName(photoName);
                fileData.setPhotoPath(photoPath);
                return fileData;
            }
        }

        public static class Respond{
            //反馈id
            public String noticeRespondId = "";
            //反馈内容
            public String respondContent = "";
            //反馈时间
            public String respondTime;
            //通知id
            public String noticeMessageId = "";
            //反馈人
            public String accounName = "";
            //反馈单位
            public String sectionName = "";

            public NoticeDetail.Respond transformToRespond(){
                NoticeDetail.Respond respond = new NoticeDetail.Respond();
                respond.setNoticeRespondId(noticeRespondId);
                respond.setRespondContent(respondContent);
                respond.setRespondTime(respondTime);
                respond.setNoticeMessageId(noticeMessageId);
                respond.setAccounName(accounName);
                respond.setSectionName(sectionName);
                return respond;
            }
        }

        public NoticeDetail transformToNoticeDetail(){
            NoticeDetail noticeDetail = new NoticeDetail();
            noticeDetail.setNoticeMessageId(noticeMessageId);
            noticeDetail.setNoticeTitle(noticeTitle);
            noticeDetail.setNoticeContent(noticeContent);
            noticeDetail.setIssuedTime(issuedTime);
            noticeDetail.setCreateTime(createTime);
            noticeDetail.setStatus(status);
            noticeDetail.setSections(sections);
            List<NoticeDetail.FileData> fileDataList = new ArrayList<NoticeDetail.FileData>();
            if(photoList != null && photoList.size() > 0){
                for(FileData fData : photoList){
                    NoticeDetail.FileData fileData = fData.transformToFileData();
                    fileDataList.add(fileData);
                }
            }
            noticeDetail.setPhotoList(fileDataList);
            List<NoticeDetail.Respond> respondList = new ArrayList<NoticeDetail.Respond>();
            if(noticeRespondList != null && noticeRespondList.size() > 0){
                for(Respond respond : noticeRespondList){
                    NoticeDetail.Respond nRespond = respond.transformToRespond();
                    respondList.add(nRespond);
                }
            }
            noticeDetail.setNoticeRespondList(respondList);
            return noticeDetail;
        }
    }

}
