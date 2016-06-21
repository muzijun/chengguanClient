package com.pactera.chengguan.municipal.model.municipal;

/**
 * 过街设施
 * Created by huang hua
 * 2016/3/11.
 */
public class Crossing {

    private int id;             //id
    private String number;      //编号
    private String name;        //名称
    private String maintainUnit;//养护单位
    private String use;         //用途
    private String buildUnit;   //建设单位

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaintainUnit() {
        return maintainUnit;
    }

    public void setMaintainUnit(String maintainUnit) {
        this.maintainUnit = maintainUnit;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getBuildUnit() {
        return buildUnit;
    }

    public void setBuildUnit(String buildUnit) {
        this.buildUnit = buildUnit;
    }
}
