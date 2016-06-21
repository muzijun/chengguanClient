package com.pactera.chengguan.municipal.model.municipal;

/**
 * 公共广场
 * Created by huang hua
 * 2016/3/11.
 */
public class Square {

    private int id;             //id
    private String number;      //编号
    private String name;        //名称
    private String position;    //地点
    private String area;        //面积
    private String duty;        //责任主体
    private String unit;        //建设单位

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
