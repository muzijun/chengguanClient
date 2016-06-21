package com.pactera.chengguan.municipal.bean.municipal;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据雨水管道对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicSewerInfoBean extends com.pactera.chengguan.municipal.bean.BaseBean {

    public List<Data> datas;

    public static class Data{
        public int rainwaterPipesId;    //id
        public String numbering = "";        //编号
        public String fullLength = "";       //长度
        public String roadBelongto = "";     //所属道路

        public com.pactera.chengguan.municipal.model.municipal.Sewer transformDataToSewer(){
            com.pactera.chengguan.municipal.model.municipal.Sewer sewer = new com.pactera.chengguan.municipal.model.municipal.Sewer();
            sewer.setId(rainwaterPipesId);
            sewer.setNumber(numbering);
            sewer.setLength(fullLength);
            sewer.setPosition(roadBelongto);
            return sewer;
        }
    }

    public List<com.pactera.chengguan.municipal.model.municipal.Sewer> transformDatas(){
        List<com.pactera.chengguan.municipal.model.municipal.Sewer> list = new ArrayList<com.pactera.chengguan.municipal.model.municipal.Sewer>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            com.pactera.chengguan.municipal.model.municipal.Sewer sewer = data.transformDataToSewer();
            list.add(sewer);
        }
        return list;
    }

}
