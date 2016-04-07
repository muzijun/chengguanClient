package com.pactera.chengguan.model.municipal;

/**
 * 泵站
 * Created by huang hua
 * 2016/3/11.
 */
public class PumpStation {

    private int id;             //id
    private String number;      //编号
    private String name;        //名称
    private String position;    //地点
    private String date;        //建设年代

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
