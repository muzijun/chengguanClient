package com.pactera.chengguan.municipal.model.municipal;

import java.io.Serializable;

/**
 * 通知列表
 * Created by huang hua
 * 2016/4/25.
 */
public class PageNotice implements Serializable {

    /**
     * 通知未发送
     */
    public static final String NOTICE_NOT_SENT = "not_sent";

    /**
     * 通知已发送
     */
    public static final String NOTICE_SENT = "sent";

    //通知内容
    private String noticeContent;
    //下发单位列表 用,分割
    private String sections;
    //通知状态
    private String status;
    //下发时间
    private String issuedTime;
    //通知标题
    private String noticeTitle;
    //通知id
    private String noticeMessageId;
    //反馈数量
    private int respondCount;

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeMessageId() {
        return noticeMessageId;
    }

    public void setNoticeMessageId(String noticeMessageId) {
        this.noticeMessageId = noticeMessageId;
    }

    public int getRespondCount() {
        return respondCount;
    }

    public void setRespondCount(int respondCount) {
        this.respondCount = respondCount;
    }
}
