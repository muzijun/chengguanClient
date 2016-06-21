package com.pactera.chengguan.municipal.bean.municipal;

/**
 * 养护日志返工接口
 * Created by huang hua
 * 2016/4/25.
 */
public class RefuseIssueReq {

    public String issueId;
    public String refuseOpinion;

    public RefuseIssueReq(){}

    public void setData(String issueId, String refuseOpinion){
        this.issueId = issueId;
        this.refuseOpinion = refuseOpinion;
    }

}
