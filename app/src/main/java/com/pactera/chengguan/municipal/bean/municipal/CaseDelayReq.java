package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 案件延期请求
 * Created by huang hua
 * 2016/3/22.
 */
public class CaseDelayReq {

    //案件id
    public int caseid;
    //延期时间
    public int termtime;
    //延期原因
    public String opinion;

    public CaseDelayReq(){}

    public void setData(int caseid, int termtime, String opinion){
        this.caseid = caseid;
        this.termtime = termtime;
        this.opinion = opinion;
    }

}
