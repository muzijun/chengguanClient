package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取道路巡查列表Bean
 * 对应接口 PATROL_RECORD_ROAD
 * Created by huang hua
 * 2016/4/12.
 */
public class PatrolRecordRoadBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //任务id
        public int roadPatrolRecordId;
        //任务状态
        public String clockStatue = "";
        //道路id
        public int roadId;
        //道路名称
        public String roadName = "";
        //扣分
        public String points = "";
        //修改原因
        public String comment = "";
    }

    public List<PatrolRecord> transformPatrolRecord(){
        List<PatrolRecord> patrolRecordList = new ArrayList<PatrolRecord>();
        if(datas == null || datas.size() <= 0){
            return patrolRecordList;
        }
        for(Data data : datas){
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setRecordId(data.roadPatrolRecordId);
            patrolRecord.setStatue(data.clockStatue);
            patrolRecord.setFromId(data.roadId);
            patrolRecord.setFromName(data.roadName);
            patrolRecord.setPoints(data.points);
            patrolRecord.setComment(data.comment);
            patrolRecord.setBaseType(MunicipalContants.PATROL_TYPE_ROAD);
            patrolRecordList.add(patrolRecord);
        }
        return patrolRecordList;
    }

}
