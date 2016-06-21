package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;

import java.util.List;

/**
 * 案件流程日志Bean
 * 对应接口 CASE_FLOW
 * Created by huang hua
 * 2016/3/17.
 */
public class CaseFlowBean extends BaseBean {

    public List<Data> datas;        //流程日志列表

    public static class Data{

        public String node = "";         //环节名
        public String username = "";     //经办人
        public String processTime = "";  //操作日期
        public String content = "";      //备注

    }

}
