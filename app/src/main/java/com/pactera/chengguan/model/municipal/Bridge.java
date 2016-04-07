package com.pactera.chengguan.model.municipal;

/**
 * 基础数据-桥梁
 * Created by huang hua
 * 2016/3/11.
 */
public class Bridge {

    private int id;                 //id
    private String name;            //名称
    private int level;              //养护等级
    private String type;            //桥梁类别
    private String position;        //所在地点

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
