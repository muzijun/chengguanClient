package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取通知列表Bean
 * Created by huang hua
 * 2016/4/25.
 */
public class PageNoticeBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //通知内容
        public String noticeContent = "";
        //下发单位列表 用,分割
        public String sections = "";
        //通知状态
        public String status = "";
        //下发时间
        public String issuedTime;
        //通知标题
        public String noticeTitle = "";
        //通知id
        public String noticeMessageId = "";
        //反馈数量
        public int respondCount;

        public com.pactera.chengguan.municipal.model.municipal.PageNotice transformToPageNotice(){
            com.pactera.chengguan.municipal.model.municipal.PageNotice pageNotice = new com.pactera.chengguan.municipal.model.municipal.PageNotice();
            pageNotice.setNoticeContent(noticeContent);
            pageNotice.setSections(sections);
            pageNotice.setStatus(status);
            pageNotice.setIssuedTime(issuedTime);
            pageNotice.setNoticeTitle(noticeTitle);
            pageNotice.setNoticeMessageId(noticeMessageId);
            pageNotice.setRespondCount(respondCount);
            return pageNotice;
        }
    }

    public List<com.pactera.chengguan.municipal.model.municipal.PageNotice> transformToPageNoticeList(){
        List<com.pactera.chengguan.municipal.model.municipal.PageNotice> pageNoticeList = new ArrayList<com.pactera.chengguan.municipal.model.municipal.PageNotice>();
        if(datas == null || datas.size() <= 0){
            return pageNoticeList;
        }
        for(Data data : datas){
            com.pactera.chengguan.municipal.model.municipal.PageNotice pageNotice = data.transformToPageNotice();
            pageNoticeList.add(pageNotice);
        }
        return pageNoticeList;
    }

}
