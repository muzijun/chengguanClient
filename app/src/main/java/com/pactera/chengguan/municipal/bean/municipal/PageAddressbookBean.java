package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.Addressbook;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取通讯录列表Bean
 * Created by huang hua
 * 2016/4/21.
 */
public class PageAddressbookBean extends BaseBean {

    public List<Data> datas;

    public static class Data{
        //id
        public int id;
        //姓名
        public String name = "";
        //电话
        public String telephone = "";
        //邮箱
        public String email = "";
        //部门id
        public int organization;
        //部门名称
        public String organizationName = "";

        private Addressbook transformToAddressbook(){
            Addressbook addressbook = new Addressbook();
            addressbook.setName(name);
            addressbook.setTelephone(telephone);
            addressbook.setEmail(email);
            addressbook.setOrganizationName(organizationName);
            addressbook.setOrganization(organization);
            return addressbook;
        }
    }

    public List<Addressbook> transformToAddressList(){
        List<Addressbook> addressbookList = new ArrayList<Addressbook>();
        if(datas == null || datas.size() <= 0){
            return addressbookList;
        }
        for(Data data : datas){
            Addressbook addressbook = data.transformToAddressbook();
            addressbookList.add(addressbook);
        }
        return addressbookList;
    }

}
