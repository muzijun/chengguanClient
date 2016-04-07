package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.model.municipal.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据公共广场对象Bean
 * 对应接口 BASIC_INFO
 * Created by huang hua
 * 2016/4/7.
 */
public class BasicSquareInfoBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        public int squareId;            //id
        public String numbering;        //编号
        public String name;             //名称
        public String location;         //地点
        public String area;             //面积
        public String constructionUnit; //建设单位
        public String relationshipName; //责任主体

        public Square transformDataToSquare(){
            Square square = new Square();
            square.setId(squareId);
            square.setNumber(numbering);
            square.setName(name);
            square.setPosition(location);
            square.setArea(area);
            square.setUnit(constructionUnit);
            square.setDuty(relationshipName);
            return square;
        }
    }

    public List<Square> transformDatas(){
        List<Square> list = new ArrayList<Square>();
        if(datas == null || datas.size() <= 0){
            return list;
        }
        for(Data data : datas){
            Square square = data.transformDataToSquare();
            list.add(square);
        }
        return list;
    }

}
