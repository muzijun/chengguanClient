package com.pactera.chengguan.municipal.model.municipal;

import java.util.List;

/**
 * 通知详情
 * Created by huang hua
 * 2016/4/25.
 */
public class NoticeDetail {

    //通知ID
    private String noticeMessageId;
    //通知标题
    private String noticeTitle;
    //通知内容
    private String noticeContent;
    //发送时间
    private String issuedTime;
    //创建时间
    private String createTime;
    //状态
    private String status;
    //下发单位
    private String sections;
    //附件列表
    private List<FileData> photoList;
    //反馈列表
    private List<Respond> noticeRespondList;

    public String getNoticeMessageId() {
        return noticeMessageId;
    }

    public void setNoticeMessageId(String noticeMessageId) {
        this.noticeMessageId = noticeMessageId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public List<FileData> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<FileData> photoList) {
        this.photoList = photoList;
    }

    public List<Respond> getNoticeRespondList() {
        return noticeRespondList;
    }

    public void setNoticeRespondList(List<Respond> noticeRespondList) {
        this.noticeRespondList = noticeRespondList;
    }

    public static class FileData{
        //文件名称
        private String photoName;
        //文件路径
        private String photoPath;

        public String getPhotoName() {
            return photoName;
        }

        public void setPhotoName(String photoName) {
            this.photoName = photoName;
        }

        public String getPhotoPath() {
            return photoPath;
        }

        public void setPhotoPath(String photoPath) {
            this.photoPath = photoPath;
        }
    }

    public static class Respond{
        //反馈id
        private String noticeRespondId;
        //反馈内容
        private String respondContent;
        //反馈时间
        private String respondTime;
        //通知id
        private String noticeMessageId;
        //反馈人
        private String accounName;
        //反馈单位
        private String sectionName;

        public String getNoticeRespondId() {
            return noticeRespondId;
        }

        public void setNoticeRespondId(String noticeRespondId) {
            this.noticeRespondId = noticeRespondId;
        }

        public String getRespondContent() {
            return respondContent;
        }

        public void setRespondContent(String respondContent) {
            this.respondContent = respondContent;
        }

        public String getRespondTime() {
            return respondTime;
        }

        public void setRespondTime(String respondTime) {
            this.respondTime = respondTime;
        }

        public String getNoticeMessageId() {
            return noticeMessageId;
        }

        public void setNoticeMessageId(String noticeMessageId) {
            this.noticeMessageId = noticeMessageId;
        }

        public String getAccounName() {
            return accounName;
        }

        public void setAccounName(String accounName) {
            this.accounName = accounName;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }
    }

}
