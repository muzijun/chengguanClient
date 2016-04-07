package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.model.municipal.Crossing;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据过街设施对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicCrossingInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int crossstreetId;           //id
        public String numbering;            //编号
        public String name;                 //名称
        public String sectionName;          //养护单位
        public String purpose;              //用途
        public String constructionUnit;     //建设单位

        public Crossing transformDataToCrossing(){
            Crossing crossing = new Crossing();
            crossing.setId(crossstreetId);
            crossing.setNumber(numbering);
            crossing.setName(name);
            crossing.setMaintainUnit(sectionName);
            crossing.setUse(purpose);
            crossing.setBuildUnit(constructionUnit);
            return crossing;
        }
    }

    public List<Crossing> transformDatas(){
        List<Crossing> list = new ArrayList<Crossing>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            Crossing crossing = data.transformDataToCrossing();
            list.add(crossing);
        }
        return list;
    }

}
