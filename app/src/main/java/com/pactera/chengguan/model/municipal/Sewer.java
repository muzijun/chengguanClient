package com.pactera.chengguan.model.municipal;

/**
 * 雨水管道
 * Created by huang hua
 * 2016/3/11.
 */
public class Sewer {

    private int id;             //id
    private String number;      //编号
    private String position;    //所属道路
    private String length;      //长度

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
