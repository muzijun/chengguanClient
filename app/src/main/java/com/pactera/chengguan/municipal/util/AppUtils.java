package com.pactera.chengguan.municipal.util;

import android.content.Context;
import android.view.View;

import com.pactera.chengguan.municipal.config.MunicipalCache;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lijun on 2016/5/13.
 */
public class AppUtils {

    /**
     * 作业单位id
     *
     * @param id
     * @return 返回作业单位名称
     */
    public static String getsectionname(int id) {
        String name = "";
        for (MunicipalCache.DataObject dataObject : MunicipalCache.sectionList) {
            if (dataObject.getId() == id) {
                name = dataObject.getName();
                break;
            }
        }
        return name;
    }

    /**
     * 返回作业单位id
     *
     * @param name
     * @return
     */
      public static int getsectionid(String name) {
        int id = 0;
        for (MunicipalCache.DataObject dataObject : MunicipalCache.sectionList) {
            if (dataObject.getName().equals(name)) {
                id = dataObject.getId();
                break;
            }
        }
        return id;
    }

    //使用String的split 方法
    public static List convertStrToArray(String str){
        List<String> list=null;
        list = Arrays.asList(str.split(",")); //拆分字符为"," ,然后把结果交给数组strArray
        return list;
    }
    /**
     * 连续点击view提示
     *
     * @param
     * @return
     */
    public static void setClickable(Context context, View view, boolean click) {
        if (click) {
            view.setClickable(true);
        } else {
            view.setClickable(false);
        }

    }
}
