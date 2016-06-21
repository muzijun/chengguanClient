package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取桥梁巡查列表Bean
 * 对应接口 PATROL_RECORD_BRIDGE
 * Created by huang hua
 * 2016/4/12.
 */
public class PatrolRecordBridgeBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //任务id
        public int bridgePatrolRecordId;
        //任务状态
        public String clockStatue = "";
        //桥梁id
        public int bridgeId;
        //桥梁名称
        public String bridgeName = "";
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
            patrolRecord.setRecordId(data.bridgePatrolRecordId);
            patrolRecord.setStatue(data.clockStatue);
            patrolRecord.setFromId(data.bridgeId);
            patrolRecord.setFromName(data.bridgeName);
            patrolRecord.setPoints(data.points);
            patrolRecord.setComment(data.comment);
            patrolRecord.setBaseType(MunicipalContants.PATROL_TYPE_BRIDGE);
            patrolRecordList.add(patrolRecord);
        }
        return patrolRecordList;
    }

}
