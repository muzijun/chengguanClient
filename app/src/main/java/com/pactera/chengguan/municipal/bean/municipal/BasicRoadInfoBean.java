package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据道路对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicRoadInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int roadId;                  //道路id
        public String name = "";                 //名称
        public String startPoint = "";           //起点
        public String endPoint = "";             //终点
        public String relationshipName = "";     //责任主体
        public int maintenanceLevel;        //养护等级
        public String vehicleLaneLength = "";    //长度
        public String bicycleLaneLength = "";    //非机动车道长度

        public Road transformDataToRoad(){
            Road road = new Road();
            road.setId(roadId);
            road.setName(name);
            road.setStart(startPoint);
            road.setEnd(endPoint);
            road.setDuty(relationshipName);
            road.setLevel(maintenanceLevel);
            road.setLength(vehicleLaneLength);
            return road;
        }
    }

    public List<Road> transformDatas(){
        List<Road> list = new ArrayList<Road>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            Road road = data.transformDataToRoad();
            list.add(road);
        }
        return list;
    }

}
