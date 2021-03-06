package com.pactera.chengguan.municipal.config;
import com.pactera.chengguan.municipal.model.municipal.CheckLib;

import java.util.List;

/**
 * 市政系统缓存，保存市政系统常规数据
 * Created by huang hua
 * 2016/3/21.
 */
public class MunicipalCache {

    //本地token保存字段Key
    public static final String SP_TOKEN = "token";

    //考核标准库保存版本号字段Key
    public static final String SP_CHECKLIB_VERSION = "checklib_version";

    //市政作业单位集合
    public static List<DataObject> sectionList;

    //道路类型集合
    public static List<DataObject> roadwayList;

    //桥梁类型集合
    public static List<DataObject> bridgeList;

    //养护等级集合
    public static List<DataObject> levelList;

    //部门集合
    public static List<DataObject> organizationList;

    //考核库集合
    public static List<CheckLib> checkLibList;

    public static class DataObject{
        private int id;
        private String name;

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
    }

}
