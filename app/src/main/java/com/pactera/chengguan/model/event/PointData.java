package com.pactera.chengguan.model.event;

/**扣分数据
 * Created by lijun on 2016/3/24.
 */
public class PointData {
    //分类
    private String type;
    //标准
    private String standard;
    //分数
    private String number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
