package com.pactera.chengguan.bean.municipal;

import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.config.MunicipalCache;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用数据获取
 * 对应接口 CHECK_STANDARD_DATA
 * Created by huang hua
 * 2016/4/6.
 */
public class StandardDataBean extends BaseBean{

    public Data datas;

    public static class Data {
        public List<Section> sectionList;
        public List<BasicData> roadwayList;
        public List<BasicData> bridgeList;
        public List<BasicData> levelList;

        public static class Section {
            public int objectId;        //作业单位id
            public String objectName;   //作业单位名称
        }

        public static class BasicData {
            public int baseDataId;      //id
            public String baseName;     //名称
        }

        public List<MunicipalCache.DataObject> transformSection() {
            List<MunicipalCache.DataObject> list = new ArrayList<MunicipalCache.DataObject>();
            if (sectionList == null || sectionList.size() <= 0) {
                return list;
            }
            for (Section section : sectionList) {
                MunicipalCache.DataObject dataObject = new MunicipalCache.DataObject();
                dataObject.setId(section.objectId);
                dataObject.setName(section.objectName);
                list.add(dataObject);
            }
            return list;
        }

        public List<MunicipalCache.DataObject> transformBasicData(List<BasicData> basicList) {
            List<MunicipalCache.DataObject> list = new ArrayList<MunicipalCache.DataObject>();
            if (basicList == null || basicList.size() <= 0) {
                return list;
            }
            for (BasicData basicData : basicList) {
                MunicipalCache.DataObject dataObject = new MunicipalCache.DataObject();
                dataObject.setId(basicData.baseDataId);
                dataObject.setName(basicData.baseName);
                list.add(dataObject);
            }
            return list;
        }
    }

}
