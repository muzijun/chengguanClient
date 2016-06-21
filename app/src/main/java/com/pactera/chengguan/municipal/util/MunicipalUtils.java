package com.pactera.chengguan.municipal.util;


import com.pactera.chengguan.municipal.config.MunicipalCache;

/**
 * 市政工具类
 * Created by huang hua
 * 2016/4/6.
 */
public class MunicipalUtils {

    /**
     * 通过作业单位名称获得id
     * @param name
     * @return
     */
    public static int getSectionIdByName(String name){
        for(int i=0;i< MunicipalCache.sectionList.size();i++){
            MunicipalCache.DataObject dataObject = MunicipalCache.sectionList.get(i);
            if(dataObject.getName().equals(name)){
                return dataObject.getId();
            }
        }
        return -1;
    }

    /**
     * 通过作业单位id获得名称
     * @param id
     * @return
     */
    public static String getSectionNameById(int id){
        for(int i=0;i<MunicipalCache.sectionList.size();i++){
            MunicipalCache.DataObject dataObject = MunicipalCache.sectionList.get(i);
            if(dataObject.getId() == id){
                return dataObject.getName();
            }
        }
        return "";
    }

    /**
     * 通过养护等级id获得名称
     * @param id
     * @return
     */
    public static String getLevelNameById(int id){
        for(int i=0;i<MunicipalCache.levelList.size();i++){
            MunicipalCache.DataObject dataObject = MunicipalCache.levelList.get(i);
            if(dataObject.getId() == id){
                return dataObject.getName();
            }
        }
        return "";
    }

}
