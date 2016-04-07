package com.pactera.chengguan.model.municipal;

/**
 * 河道栏杆
 * Created by huang hua
 * 2016/3/11.
 */
public class Railing {

    private int id;             //id
    private String number;      //编号
    private String name;        //名称
    private String startPos;    //起点
    private String length;      //长度
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

    public String getStartPos() {
        return startPos;
    }

    public void setStartPos(String startPos) {
        this.startPos = startPos;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
