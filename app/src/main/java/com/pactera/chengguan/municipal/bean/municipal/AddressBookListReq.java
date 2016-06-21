package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;

/**
 * 获取通讯录请求
 * Created by wangzhen
 * 2016/4/21.
 */
public class AddressBookListReq extends BaseBean {

    //关键字条件
    public String condition;

    //排序 1.姓名排序
    public int sort;
    //单页显示数量
    public int pagecount;
    //当前页最后一项id
    public int lastid;

    public AddressBookListReq(){}

    public void setData(String condition, int sort, int pagecount, int lastid){

        this.condition = condition;
        this.sort = sort;
        this.pagecount = pagecount;
        this.lastid = lastid;
    }

}