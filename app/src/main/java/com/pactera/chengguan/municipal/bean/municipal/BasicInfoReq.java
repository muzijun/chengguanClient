package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 获取基础数据列表请求
 * Created by huang hua
 * 2016/4/6.
 */
public class BasicInfoReq {

    //基础数据类别id 1.道路 2.桥梁 3.雨水管道 4.泵站 5.公共广场 6.河道栏杆 7.过街设施
    public int basicid;
    //筛选项1
    public int screenitem1;
    //筛选项2
    public int screenitem2;
    //筛选项3
    public int screenitem3;
    //单页显示数量
    public int pagecount;
    //当前页最后一项id
    public int lastid;

    public BasicInfoReq(){}

    public void setData(int basicid, int screenitem1, int screenitem2, int screenitem3, int pagecount, int lastid){
        this.basicid = basicid;
        this.screenitem1 = screenitem1;
        this.screenitem2 = screenitem2;
        this.screenitem3 = screenitem3;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}
