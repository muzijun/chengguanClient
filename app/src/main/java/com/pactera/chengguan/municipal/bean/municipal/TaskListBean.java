package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.ListTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务单列表请求Bean
 * Created by huang hua
 * 2016/5/9.
 */
public class TaskListBean extends BaseBean {

    public List<Data> datas;

    public static class Data {
        //任务单id
        public int workFormId;
        //编号
        public String numbering = "";
        //作业单位
        public int sectionId;
        //联系依据
        public String rationale = "";
        //资金来源
        public String sourcesFund = "";
        //完成时间
        public String finishDate = "";
        //工作任务描述
        public String description = "";
        //下发日期
        public String issuedDate = "";
        //状态
        public String status = "";

        public ListTask transformToListTask(){
            ListTask task = new ListTask();
            task.setWorkFormId(workFormId);
            task.setNumbering(numbering);
            task.setSectionId(sectionId);
            task.setRationale(rationale);
            task.setSourcesFund(sourcesFund);
            task.setFinishDate(finishDate);
            task.setDescription(description);
            task.setIssuedDate(issuedDate);
            task.setStatus(status);
            return task;
        }
    }

    public List<ListTask> transformToTaskList(){
        List<ListTask> list = new ArrayList<ListTask>();
        if(datas != null && datas.size() > 0){
            for(Data data : datas){
                ListTask task = data.transformToListTask();
                list.add(task);
            }
        }
        return list;
    }

}
