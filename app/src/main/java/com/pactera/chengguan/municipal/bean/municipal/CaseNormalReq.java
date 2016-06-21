package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 案件通用请求，只包含案件id
 * Created by huang hua
 * 2016/3/22.
 */
public class CaseNormalReq {

    public int caseid;

    public CaseNormalReq(){}

    public void setData(int caseid){
        this.caseid = caseid;
    }

}
