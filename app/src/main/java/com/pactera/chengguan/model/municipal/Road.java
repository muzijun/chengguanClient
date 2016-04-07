package com.pactera.chengguan.model.municipal;

/**
 * 基础数据-道路
 * Created by huang hua
 * 2016/3/11.
 */
public class Road {

    private int id;             //id
    private String name;        //名称
    private int level;          //养护等级
    private String length;      //长度
    private String start;       //起点
    private String end;         //终点
    private String duty;        //责任主体

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }
}
