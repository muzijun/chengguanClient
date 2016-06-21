package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 审核不通过请求
 * Created by huang hua
 * 2016/3/22.
 */
public class CaseNotSecondReq {

    //案件id
    public int caseid;
    //返工意见
    public String opinion;

    public CaseNotSecondReq(){}

    public void setData(int caseid, String opinion){
        this.caseid = caseid;
        this.opinion = opinion;
    }

}
