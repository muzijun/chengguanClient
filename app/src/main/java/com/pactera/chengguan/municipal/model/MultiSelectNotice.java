package com.pactera.chengguan.municipal.model;

/**通知多选
 * Created by lijun on 2016/6/13.
 */
public class MultiSelectNotice {
    //名字
    private String name;
    //是否被选中
    private boolean checkItem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheckItem() {
        return checkItem;
    }

    public void setCheckItem(boolean checkItem) {
        this.checkItem = checkItem;
    }
}
