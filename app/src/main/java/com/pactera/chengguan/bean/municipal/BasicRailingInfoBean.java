package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.model.municipal.Railing;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据河道栏杆对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicRailingInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int riverRailingId;      //id
        public String numbering;        //编号
        public String name;             //名称
        public String startPoint;       //起点
        public String length;           //长度
        public String constructionUnit; //建设单位

        public Railing transformDataToRailing(){
            Railing railing = new Railing();
            railing.setId(riverRailingId);
            railing.setNumber(numbering);
            railing.setName(name);
            railing.setStartPos(startPoint);
            railing.setLength(length);
            railing.setUnit(constructionUnit);
            return railing;
        }
    }

    public List<Railing> transformDatas(){
        List<Railing> list = new ArrayList<Railing>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            Railing railing = data.transformDataToRailing();
            list.add(railing);
        }
        return list;
    }

}
