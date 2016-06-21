package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.TaskFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务单流程日志Bean
 * Created by huang hua
 * 2016/5/9.
 */
public class TaskFlowBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //id
        public int taskFormFlowLogId;
        //任务单id
        public int workformId;
        //环节名
        public String node = "";
        //账号类型
        public String type = "";
        //经办人
        public String operatorname = "";
        //处理时间
        public String createTime = "";
        //处理意见
        public String content = "";

        public TaskFlow transformToTaskFlow(){
            TaskFlow flow = new TaskFlow();
            flow.setTaskFormFlowLogId(taskFormFlowLogId);
            flow.setWorkformId(workformId);
            flow.setNode(node);
            flow.setType(type);
            flow.setOperatorname(operatorname);
            flow.setCreateTime(createTime);
            flow.setContent(content);
            return flow;
        }
    }

    public List<TaskFlow> transformToFlowList(){
        List<TaskFlow> flowList = new ArrayList<TaskFlow>();
        if(datas != null && datas.size() > 0){
            for(Data data : datas){
                TaskFlow flow = data.transformToTaskFlow();
                flowList.add(flow);
            }
        }
        return flowList;
    }

}
