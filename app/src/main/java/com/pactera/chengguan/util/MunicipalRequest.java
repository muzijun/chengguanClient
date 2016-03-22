package com.pactera.chengguan.util;

import com.google.gson.Gson;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.ChenguanOkHttpManager;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.municipal.CaseCreateReq;
import com.pactera.chengguan.bean.municipal.CaseDelayReq;
import com.pactera.chengguan.bean.municipal.CaseListBean;
import com.pactera.chengguan.bean.municipal.CaseListReq;
import com.pactera.chengguan.bean.municipal.LoginBean;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.model.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 市政接口请求集合
 * Created by huang hua
 * 2016/3/21.
 */
public class MunicipalRequest {

    /**
     * 用户登录接口 USER_LOGIN
     * @param context
     * @param mCallBackListener
     * @param user
     * @param password
     */
    public static void requestLogin(BaseActivity context, RequestListener mCallBackListener
            , String user, String password){
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("user", user));
        params.add(new RequestParam("pwd", password));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(LoginBean.class,mCallBackListener,context));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.USER_LOGIN);
        j.setParams(params);
        ChenguanOkHttpManager.request(j);
    }

    /**
     * 获取案件列表
     * @param context
     * @param mCallBackListener
     * @param status    处理状态 1.待办，2.处理中，3.办结
     * @param category  类别 1.月度，2.季度，3.年度，4.日常
     * @param month     月份 1.一月。。。12,十二月
     * @param sort      排序 1.按时间排序，2.按超限排序
     * @param pagecount 单页显示数量
     * @param lastid    当前页最后一项id
     */
    public static void requestCaseList(BaseActivity context, RequestListener mCallBackListener
            , int status, int category, int month, int sort, int pagecount, int lastid){
        CaseListReq req = new CaseListReq();
        req.setData(status, category, month, sort, pagecount, lastid);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseListReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseListBean.class,mCallBackListener,context));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.CASE_LIST);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 新建或保存案件
     * @param context
     * @param mCallBackListener
     * @param type  操作类别 1.保存，2.下发
     * @param caseId 案件id 如果新建案件发null
     * @param description   案件描述
     * @param point 考核扣分
     * @param termtime  期限时间
     * @param case_addree   案件地址
     * @param longitude 地址经度
     * @param latitude  地址纬度
     * @param unit  作业单位id
     * @param category  类别
     * @param month 月份
     * @param pic   照片
     */
    public static void requestCreateCase(BaseActivity context, RequestListener mCallBackListener
            , int type, String caseId, String description, int point, int termtime, String case_addree
            , double longitude, double latitude, int unit, int category, int month, List<String> pic){
        CaseCreateReq req = new CaseCreateReq();
        req.setData(type, caseId, description, point, termtime, case_addree, longitude, latitude
                , unit, category, month, pic);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseCreateReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_CREATE);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件延期接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     * @param termtime  延期时间
     * @param opinion   延期原因
     */
    public static void requestCaseDelay(BaseActivity context, RequestListener mCallBackListener
            , int caseid, int termtime, String opinion){
        CaseDelayReq req = new CaseDelayReq();
        req.setData(caseid, termtime, opinion);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseDelayReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.CASE_DELAY);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

}
