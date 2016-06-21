package com.pactera.chengguan.municipal.bean.municipal;

import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.OperatorInfo;

/**
 * 获取个人信息Bean
 * Created by huang hua
 * 2016/5/3.
 */
public class OperatorInfoBean extends BaseBean {

    public Data datas;

    public static class Data{
        //id
        public String operatorId = "";
        //部门id
        public String organizationid = "";
        //姓名
        public String operatorDisplayname = "";
        //部门名称
        public String organizationname = "";
        //电话
        public String operatorPhone = "";
        //手机
        public String operatorTelephone = "";
        //电邮
        public String operatorEmail = "";
        //账户
        public String operatorLoginname = "";
    }

    public OperatorInfo transformToOperatorInfo(){
        OperatorInfo info = new OperatorInfo();
        if(datas != null){
            info.setOperatorId(datas.operatorId);
            info.setOrganizationid(datas.organizationid);
            info.setOperatorDisplayname(datas.operatorDisplayname);
            info.setOrganizationname(datas.organizationname);
            info.setOperatorPhone(datas.operatorPhone);
            info.setOperatorTelephone(datas.operatorTelephone);
            info.setOperatorEmail(datas.operatorEmail);
            info.setOperatorLoginname(datas.operatorLoginname);
        }
        return info;
    }

}
