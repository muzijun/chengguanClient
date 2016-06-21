package com.pactera.chengguan.municipal.bean.municipal;


import com.pactera.chengguan.municipal.bean.BaseBean;

import java.util.List;

/**
 * 案件延期记录Bean
 * 对应接口 CASE_DELAY_RECORD
 * Created by huang hua
 * 2016/3/17.
 */
public class CaseDelayRecordBean extends BaseBean {

    public List<Data> datas;        //延期记录列表

    public static class Data{

        public String delayTime = "";    //延期日期
        public int delayDay;        //延期天数
        public String username = "";     //经办人
        public String reason = "";       //延期原因

    }

}
