package com.pactera.chengguan.municipal.bean.municipal;

import java.util.List;

/**
 * 新增或修改通知请求
 * Created by huang hua
 * 2016/4/25.
 */
public class AddUpdateNoticeReq {

    //通知id 新增为空，修改不为空
    public String noticeMessageId;
    //通知标题
    public String noticeTitle;
    //通知内容
    public String noticeContent;
    //下发单位 多个用,分割
    public String sections;
    //文件列表
    public List<String> fileuploadList;
    //0:保存  1:下发
    public int issued;

    public AddUpdateNoticeReq(){}

    public void setData(String noticeMessageId, String noticeTitle, String noticeContent
            , String sections, List<String> fileuploadList, int issued){
        this.noticeMessageId = noticeMessageId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.sections = sections;
        this.fileuploadList = fileuploadList;
        this.issued = issued;
    }

}
