package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.CheckLib;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取考核库Bean
 * 对应接口 SELECT_CHECKLIB
 * Created by huang hua
 * 2016/4/12.
 */
public class CheckLibBean extends BaseBean {

    public Data datas;

    public static class Data{
        //版本号
        public String version = "";
        public List<LibData> datas;

        public static class LibData{
            //分类id
            public int checkClassifyId;
            //分类名称
            public String classifyName = "";
            //分类内容
            public List<ContentData> standardList;

            public static class ContentData{
                //内容id
                public int checkScoreStandardId;
                //内容
                public String checkStandard = "";
                //默认扣分
                public String pointsStandard = "";

                private CheckLib.Content transformToContent(){
                    CheckLib.Content content = new CheckLib.Content();
                    content.setId(checkScoreStandardId);
                    content.setContent(checkStandard);
                    content.setPoints(pointsStandard);
                    return content;
                }
            }

            private CheckLib transformToLib(){
                CheckLib checkLib = new CheckLib();
                checkLib.setId(checkClassifyId);
                checkLib.setName(classifyName);
                List<CheckLib.Content> contents = new ArrayList<CheckLib.Content>();
                if(standardList != null && standardList.size() > 0){
                    for(ContentData contentData : standardList){
                        CheckLib.Content content = contentData.transformToContent();
                        contents.add(content);
                    }
                }
                checkLib.setContents(contents);
                return checkLib;
            }
        }

        public List<CheckLib> transformToCheckLibList(){
            List<CheckLib> checkLibList = new ArrayList<CheckLib>();
            if(datas == null || datas.size() <= 0){
                return checkLibList;
            }
            for(LibData libData : datas){
                CheckLib checkLib = libData.transformToLib();
                checkLibList.add(checkLib);
            }
            return checkLibList;
        }
    }

}
