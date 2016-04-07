package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.model.municipal.Sewer;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据雨水管道对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicSewerInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int rainwaterPipesId;    //id
        public String numbering;        //编号
        public String fullLength;       //长度
        public String roadBelongto;     //所属道路

        public Sewer transformDataToSewer(){
            Sewer sewer = new Sewer();
            sewer.setId(rainwaterPipesId);
            sewer.setNumber(numbering);
            sewer.setLength(fullLength);
            sewer.setPosition(roadBelongto);
            return sewer;
        }
    }

    public List<Sewer> transformDatas(){
        List<Sewer> list = new ArrayList<Sewer>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            Sewer sewer = data.transformDataToSewer();
            list.add(sewer);
        }
        return list;
    }

}
