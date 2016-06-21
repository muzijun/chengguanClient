package com.pactera.chengguan.municipal.model;

/**选择事件接收器
 * Created by lijun on 2016/3/16.
 */
public class SelectEvent {
    //目的地，需要传给哪个界面
    private String address;
    //类型
    private String type;
    //内容
    private String mMsg;
    //对象
    private Object object;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
