package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.ContactFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务联系单流程日志Bean
 * Created by huang hua
 * 2016/5/5.
 */
public class ContactFlowBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //联系单id
        public int id;
        //环节名
        public String node;
        //账号类型
        public String type;
        //经办人
        public String operatorname;
        //办结时间
        public String createTime;
        //处理意见
        public String content;

        public ContactFlow transformToContactFlow(){
            ContactFlow flow = new ContactFlow();
            flow.setId(id);
            flow.setNode(node);
            flow.setType(type);
            flow.setOperatorname(operatorname);
            flow.setCreateTime(createTime);
            flow.setContent(content);
            return flow;
        }
    }

    public List<ContactFlow> transformToContactFlowList(){
        List<ContactFlow> list = new ArrayList<ContactFlow>();
        if(datas != null && datas.size() > 0){
            for(Data data : datas){
                ContactFlow flow = data.transformToContactFlow();
                list.add(flow);
            }
        }
        return list;
    }

}
