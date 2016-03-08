package com.pactera.chengguan.model;


import java.util.ArrayList;

/**照片事件接收器
 * Created by lijun on 2016/2/16.
 */
public class PhotoEvent {
    private ArrayList<String> mMsg;
    public PhotoEvent(ArrayList<String> msg) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
    }
    public ArrayList<String> getMsg(){
        return mMsg;
    }
}
