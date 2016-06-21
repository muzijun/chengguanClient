package com.pactera.chengguan.municipal.model.municipal;

/**
 * 任务联系单流程日志
 * Created by huang hua
 * 2016/5/5.
 */
public class ContactFlow {

    //联系单id
    private int id;
    //环节名
    private String node;
    //账号类型
    private String type;
    //经办人
    private String operatorname;
    //办结时间
    private String createTime;
    //处理意见
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
