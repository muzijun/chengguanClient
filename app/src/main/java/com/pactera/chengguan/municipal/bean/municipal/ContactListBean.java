package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.ListContact;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务联系单列表Bean
 * Created by huang hua
 * 2016/5/5.
 */
public class ContactListBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //任务联系单id
        public int id;
        //作业单位ID
        public int sectionId;
        //标题
        public String title = "";
        //工期
        public String schedule = "";
        //工作任务描述
        public String description = "";
        //状态
        public String status = "";
        //项目经理
        public String projectManager = "";
        //上报日期
        public String reportDate = "";
        //联系单类型
        public String contractType = "";

        public ListContact transformToListContact(){
            ListContact listContact = new ListContact();
            listContact.setId(id);
            listContact.setSectionId(sectionId);
            listContact.setTitle(title);
            listContact.setSchedule(schedule);
            listContact.setDescription(description);
            listContact.setStatus(status);
            listContact.setProjectManager(projectManager);
            listContact.setReportDate(reportDate);
            listContact.setContractType(contractType);
            return listContact;
        }
    }

    public List<ListContact> transformToContactList(){
        List<ListContact> list = new ArrayList<ListContact>();
        if(datas != null && datas.size() > 0){
            for(Data data : datas){
                ListContact contact = data.transformToListContact();
                list.add(contact);
            }
        }
        return list;
    }

}
