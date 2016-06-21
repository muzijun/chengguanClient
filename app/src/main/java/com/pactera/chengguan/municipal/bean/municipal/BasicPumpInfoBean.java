package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.PumpStation;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据泵站对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicPumpInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int pumpStationId;       //id
        public String numbering = "";        //编号
        public String name = "";             //名称
        public String buildingYear = "";     //建设年代
        public String location = "";         //地点

        public PumpStation transformDataToPumpStation(){
            PumpStation pumpStation = new PumpStation();
            pumpStation.setId(pumpStationId);
            pumpStation.setNumber(numbering);
            pumpStation.setName(name);
            pumpStation.setDate(buildingYear);
            pumpStation.setPosition(location);
            return pumpStation;
        }
    }

    public List<PumpStation> transformDatas(){
        List<PumpStation> list = new ArrayList<PumpStation>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            PumpStation pumpStation = data.transformDataToPumpStation();
            list.add(pumpStation);
        }
        return list;
    }

}
