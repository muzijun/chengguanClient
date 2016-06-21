package com.pactera.chengguan.municipal.bean.municipal;


import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.Bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据桥梁对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicBridgeInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int bridgeId;            //桥梁id
        public String name = "";             //桥梁名称
        public String location = "";         //桥梁地点
        public String structureType = "";    //桥梁类型
        public int maintenanceLevel;    //桥梁养护等级id

        public Bridge transformDataToBridge(){
            Bridge bridge = new Bridge();
            bridge.setId(bridgeId);
            bridge.setName(name);
            bridge.setPosition(location);
            bridge.setType(structureType);
            bridge.setLevel(maintenanceLevel);
            return bridge;
        }
    }

    public List<Bridge> transformDatas(){
        List<Bridge> list = new ArrayList<Bridge>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            Bridge bridge = data.transformDataToBridge();
            list.add(bridge);
        }
        return list;
    }

}
