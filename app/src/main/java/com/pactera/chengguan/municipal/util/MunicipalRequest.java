package com.pactera.chengguan.municipal.util;

import android.app.Activity;

import com.google.gson.Gson;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.app.base.ChenguanOkHttpManager;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.app.model.AppRequestFile;
import com.pactera.chengguan.app.model.AppRequestPair;
import com.pactera.chengguan.app.model.AppRequestParam;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.municipal.*;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 市政接口请求集合
 * Created by huang hua
 * 2016/3/21.
 */
public class MunicipalRequest {

    /**
     * 发送请求并判断是否需要登录
     * @param requestPair
     * @param isNeedToken
     */
    public static void requestIfNeedToken(AppRequestPair requestPair, boolean isNeedToken, String systemId){
        if(isNeedToken){
            ArrayList<AppRequestParam> params = requestPair.getParams();
            params.add(new AppRequestParam("access_token",ChengApplication.instance.municipalApplication.access_token));
            params.add(new AppRequestParam("systemid", systemId));
            params.add(new AppRequestParam("loginMobile", ""+ Contants.MOBILE_CHENGGUAN));
            requestPair.setParams(params);
        }
        ChenguanOkHttpManager.request(requestPair);
    }
    /**
     * 用户登录接口 USER_LOGIN
     * @param context
     * @param mCallBackListener
     * @param user
     * @param password
     */
    public static void requestLogin(BaseActivity context, RequestListener mCallBackListener
            , String user, String password,String client_id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("user", user));
        params.add(new AppRequestParam("pwd", password));
        params.add(new AppRequestParam(("client_id"),client_id));
        AppRequestPair j= new AppRequestPair ();
        j.setContext(context);
        j.setRequest(new BaseCallback(LoginBean.class,mCallBackListener,context, Contants.USER_LOGIN));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.USER_LOGIN);
        j.setParams(params);
        ChenguanOkHttpManager.request(j);
    }

    /**
     * 获取动态筛选项数据 CHECK_STANDARD_DATA
     * @param context
     * @param mCallBackListener
     */
    public static void requestCheckStandardData(BaseActivity context, RequestListener mCallBackListener){
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(StandardDataBean.class, mCallBackListener, context, Contants.SELECT_SCREEN_ITEM));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.SELECT_SCREEN_ITEM);
        j.setLoadingShow(true);
       requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取考核库
     * @param context
     * @param mCallBackListener
     * @param version
     */
    public static void requestCheckLib(BaseActivity context, RequestListener mCallBackListener, String version){
        VersionReq req = new VersionReq();
        req.setData(version);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, VersionReq.class)));
        AppRequestPair   j= new AppRequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(CheckLibBean.class, mCallBackListener, context, Contants.SELECT_CHECKLIB));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.SELECT_CHECKLIB);
        j.setLoadingShow(true);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 同步服务器时间
     * @param context
     * @param mCallBackListener
     */
    public static void requestServerTime(Activity context, RequestListener mCallBackListener){
        AppRequestPair   j= new AppRequestPair ();
        j.setContext(context);
        j.setRequest(new BaseCallback(ServerTimeBean.class, mCallBackListener, context, Contants.GET_SERVER_TIME));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.GET_SERVER_TIME);
        j.setLoadingShow(false);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取个人信息
     * @param context
     * @param mCallBackListener
     */
    public static void requestGetOperatorInfo( BaseActivity context, RequestListener mCallBackListener){
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(OperatorInfoBean.class, mCallBackListener, context, Contants.GET_OPERATOR_INFO));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.GET_OPERATOR_INFO);
        j.setLoadingShow(true);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取app新版本
     * @param context
     * @param mCallBackListener
     */
    public static void requestGetAppVersion(BaseActivity context, RequestListener mCallBackListener){
        int versionCode = PhoneInfoUtil.getAppVersionCode(context);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("version", "" + versionCode));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(AppVersionBean.class, mCallBackListener, context, Contants.GET_APP_VERSION));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.GET_APP_VERSION);
        j.setLoadingShow(false);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 用户注销
     * @param context
     * @param mCallBackListener
     */
    public static void requestUserLogout( BaseActivity context, RequestListener mCallBackListener){
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class, mCallBackListener, context, Contants.USER_LOGOUT));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.USER_LOGOUT);
        j.setLoadingShow(true);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取基础数据列表 BASIC_INFO
     * @param context
     * @param mCallBackListener
     * @param basicid
     * @param screenitem1
     * @param screenitem2
     * @param screenitem3
     * @param pagecount
     * @param lastid
     */
    public static void requestBasicInfo( BaseActivity context, RequestListener mCallBackListener, int basicid
            , int screenitem1, int screenitem2, int screenitem3, int pagecount, int lastid){
        BasicInfoReq req = new BasicInfoReq();
        req.setData(basicid, screenitem1, screenitem2, screenitem3, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, BasicInfoReq.class)));
        BaseCallback callback = null;
        switch (basicid){
            case MunicipalContants.BASIC_ROAD_ID:
                callback = new BaseCallback(BasicRoadInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
            case MunicipalContants.BASIC_BRIDGE_ID:
                callback = new BaseCallback(BasicBridgeInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
            case MunicipalContants.BASIC_SEWER_ID:
                callback = new BaseCallback(BasicSewerInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
            case MunicipalContants.BASIC_PUMPSTATION_ID:
                callback = new BaseCallback(BasicPumpInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
            case MunicipalContants.BASIC_SQUARE_ID:
                callback = new BaseCallback(BasicSquareInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
            case MunicipalContants.BASIC_RAILING_ID:
                callback = new BaseCallback(BasicRailingInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
            case MunicipalContants.BASIC_CROSSING_ID:
                callback = new BaseCallback(BasicCrossingInfoBean.class,mCallBackListener,context, Contants.BASIC_INFO);
                break;
        }
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(callback);
        j.setMethod(Contants.Post);
        j.setUrl(Contants.BASIC_INFO);
        j.setLoadingShow(false);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取道路巡查列表
     * @param context
     * @param mCallBackListener
     * @param type  1.本周期，2.上周期，3.历史
     * @param maintenanceLevel  养护等级的id
     * @param orderType  0-时间排序 1-状态排序
     * @param pagecount
     * @param lastid
     */
    public static void requestPatrolRecordRoad( BaseActivity context, RequestListener mCallBackListener
            , int type, int maintenanceLevel, int orderType, int pagecount, int lastid){
        PatrolRecordReq req = new PatrolRecordReq();
        req.setData(type, maintenanceLevel, orderType, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, PatrolRecordReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(PatrolRecordRoadBean.class,mCallBackListener,context, Contants.PATROL_RECORD_ROAD));
        j.setMethod(Contants.Post);
        j.setLoadingShow(false);
        j.setUrl(Contants.PATROL_RECORD_ROAD);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取桥梁巡查列表
     * @param context
     * @param mCallBackListener
     * @param type  1.本周期，2.上周期，3.历史
     * @param maintenanceLevel   养护等级的id
     * @param orderType  0-时间排序 1-状态排序
     * @param pagecount
     * @param lastid
     */
    public static void requestPatrolRecordBridge( BaseActivity context, RequestListener mCallBackListener
            , int type, int maintenanceLevel, int orderType, int pagecount, int lastid){
        PatrolRecordReq req = new PatrolRecordReq();
        req.setData(type, maintenanceLevel, orderType, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, PatrolRecordReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(PatrolRecordBridgeBean.class,mCallBackListener,context, Contants.PATROL_RECORD_BRIDGE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(false);
        j.setUrl(Contants.PATROL_RECORD_BRIDGE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 更改巡查任务状态
     * @param context
     * @param mCallBackListener
     * @param objectId
     * @param comment
     * @param type
     */
    public static void requestUpdatePatrolRecord( BaseActivity context, RequestListener mCallBackListener
            , int objectId, String comment, String type){
        UpdatePatrolRecordReq req = new UpdatePatrolRecordReq();
        req.setData(objectId, comment, type);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, UpdatePatrolRecordReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.UPDATE_PATROL_RECORD));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.UPDATE_PATROL_RECORD);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 巡查任务异常扣分
     * @param context
     * @param mCallBackListener
     * @param patrolRecord
     */
    public static void requestPointsPatrolRecord( BaseActivity context, RequestListener mCallBackListener
            ,  PatrolRecord patrolRecord){
        PointsPatrolReq req = new PointsPatrolReq();
        req.setData(patrolRecord.getRecordId(), MunicipalContants.POINTS_TYPE_PATROL, patrolRecord.getBaseType());
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, PointsPatrolReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.POINTS_PATROL_RECORD));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.POINTS_PATROL_RECORD);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
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
    public static void requestCaseList( BaseActivity context, RequestListener mCallBackListener
            , int status, Integer category, Integer month, int sort, int pagecount, int lastid, int requestStatus){
        CaseListReq req = new CaseListReq();
        req.setData(status, category, month, sort, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, CaseListReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseListBean.class, mCallBackListener, context, Contants.CASE_LIST));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.CASE_LIST);
        if(requestStatus == com.pactera.chengguan.municipal.activity.CaseListActivity.STATUS_INIT){
            j.setLoadingShow(false);
        }else {
            j.setLoadingShow(true);
        }
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
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
    public static void requestCreateCase( BaseActivity context, RequestListener mCallBackListener
            , int type, String caseId, String description, String point, int termtime, String case_addree
            , double longitude, double latitude, int unit, int category, int month, List<String> pic){
        CaseCreateReq req = new CaseCreateReq();
        req.setData(type, caseId, description, point, termtime, case_addree, longitude, latitude
                , unit, category, month, pic);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, CaseCreateReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class, mCallBackListener, context, Contants.CASE_CREATE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_CREATE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件延期接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     * @param termtime  延期时间
     * @param opinion   延期原因
     */
    public static void requestCaseDelay( BaseActivity context, RequestListener mCallBackListener
            , int caseid, int termtime, String opinion){
        CaseDelayReq req = new CaseDelayReq();
        req.setData(caseid, termtime, opinion);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, CaseDelayReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class, mCallBackListener, context, Contants.CASE_DELAY));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_DELAY);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件结案接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     */
    public static void requestCaseFinish( BaseActivity context, RequestListener mCallBackListener
            , int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new  AppRequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
       AppRequestPair   j= new  AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class, mCallBackListener, context, Contants.CASE_FINISH));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_FINISH);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取案件延期记录接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     */
    public static void requestCaseDelayRecord( BaseActivity context, RequestListener mCallBackListener
            , int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseDelayRecordBean.class,mCallBackListener,context, Contants.CASE_DELAY_RECORD));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_DELAY_RECORD);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取案件流程日志
     * @param context
     * @param mCallBackListener
     * @param caseid
     */
    public static void requestCaseFlow( BaseActivity context, RequestListener mCallBackListener
            , int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseFlowBean.class,mCallBackListener,context, Contants.CASE_FLOW));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_FLOW);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件考核接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     * @param type      扣分依据分类
     * @param point     扣分分数
     * @param month     扣分计入月份 1.一月。。。12,十二月
     */
    public static void requestCaseCheck( BaseActivity context, RequestListener mCallBackListener
            , int caseid, String type, String point, int month){
        CaseCheckReq req = new CaseCheckReq();
        req.setData(caseid, type, point, month);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CaseCheckReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.CASE_CHECK));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_CHECK);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件不通过返工接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     * @param opinion   返工意见
     */
    public static void requestCaseNotSecond( BaseActivity context, RequestListener mCallBackListener
            , int caseid, String opinion){
        CaseNotSecondReq req = new CaseNotSecondReq();
        req.setData(caseid, opinion);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CaseNotSecondReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.CASE_NOT_SECOND));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_NOT_SECOND);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取单个案件详情
     * @param context
     * @param mCallBackListener
     * @param caseid
     */
    public static void requestCaseDetail( BaseActivity context, RequestListener mCallBackListener, int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseDetailBean.class,mCallBackListener,context, Contants.CASE_DETAIL));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_DETAIL);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取通知列表
     * @param context
     * @param mCallBackListener
     * @param month
     * @param orderType 排序方式 1. 时间排序 2.状态排序
     * @param pagecount
     * @param lastid
     */
    public static void requestPageNoticeList( BaseActivity context, RequestListener mCallBackListener
            , Integer month, int orderType, int pagecount, String lastid){
        PageNoticeReq req = new PageNoticeReq();
        req.setData(month, orderType, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, PageNoticeReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(PageNoticeBean.class,mCallBackListener,context, Contants.PAGE_NOTICE_LIST));
        j.setMethod(Contants.Post);
        j.setLoadingShow(false);
        j.setUrl(Contants.PAGE_NOTICE_LIST);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 新增或修改通知
     * @param context
     * @param mCallBackListener
     * @param noticeMessageId 通知id 新增为空，修改不为空
     * @param noticeTitle
     * @param noticeContent
     * @param sections 下发单位 多个用,分割
     * @param fileuploadList
     * @param issued 0:保存  1:下发
     */
    public static void requestAddUpdateNotice( BaseActivity context, RequestListener mCallBackListener
            , String noticeMessageId, String noticeTitle, String noticeContent, String sections
            , List<String> fileuploadList, int issued){
        AddUpdateNoticeReq req = new AddUpdateNoticeReq();
        req.setData(noticeMessageId, noticeTitle, noticeContent, sections, fileuploadList, issued);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, AddUpdateNoticeReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.ADD_UPDATE_NOTICE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.ADD_UPDATE_NOTICE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取通知详情
     * @param context
     * @param mCallBackListener
     * @param noticeMessageId
     */
    public static void requestShowNotice( BaseActivity context, RequestListener mCallBackListener
            , String noticeMessageId){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", noticeMessageId));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(NoticeDetailBean.class,mCallBackListener,context, Contants.SHOW_NOTICE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.SHOW_NOTICE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取养护日志列表
     * @param context
     * @param mCallBackListener
     * @param option 标签 1：代办；2：处理中；3：办结
     * @param sectionId
     * @param month
     * @param orderType 排序 1. 时间排序 2.状态排序
     * @param pagecount
     * @param lastid
     * @param requestStatus
     */
    public static void requestPageIssueList( BaseActivity context, RequestListener mCallBackListener
            , int option, int sectionId, Integer month, int orderType, int pagecount, long lastid, int requestStatus){
        PageIssueReq req = new PageIssueReq();
        req.setData(option, sectionId, month, orderType, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, PageIssueReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(PageIssueBean.class, mCallBackListener, context, Contants.PAGE_ISSUE_LIST));
        j.setMethod(Contants.Post);
        if(requestStatus == com.pactera.chengguan.municipal.activity.MaintainListAcivity.STATUS_INIT){
            j.setLoadingShow(false);
        }else {
            j.setLoadingShow(false);
        }
        j.setUrl(Contants.PAGE_ISSUE_LIST);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取养护日志详情
     * @param context
     * @param mCallBackListener
     * @param id    养护日志id
     */
    public static void requestShowIssue( BaseActivity context, RequestListener mCallBackListener, String id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(IssueDetailBean.class, mCallBackListener, context, Contants.SHOW_ISSUE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.SHOW_ISSUE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 养护日志返工
     * @param context
     * @param mCallBackListener
     * @param issueId
     * @param refuseOpinion
     */
    public static void requestRefuseIssue( BaseActivity context, RequestListener mCallBackListener
            , String issueId, String refuseOpinion){
        RefuseIssueReq req = new RefuseIssueReq();
        req.setData(issueId, refuseOpinion);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, RefuseIssueReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class, mCallBackListener, context, Contants.REFUSE_ISSUE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.REFUSE_ISSUE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 养护日志结案
     * @param context
     * @param mCallBackListener
     * @param id
     */
    public static void requestCloseIssue( BaseActivity context, RequestListener mCallBackListener, String id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.CLOSE_ISSUE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CLOSE_ISSUE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 确认养护日志
     * @param context
     * @param mCallBackListener
     * @param id
     */
    public static void requestConfirmIssue( BaseActivity context, RequestListener mCallBackListener, String id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.CONFIRM_ISSUE));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CONFIRM_ISSUE);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取任务联系单列表
     * @param context
     * @param mCallBackListener
     * @param sectionId
     * @param month
     * @param option  1.待办，2.处理中，3.办结
     * @param pagecount
     * @param lastid
     */
    public static void requestGetPageContactList( BaseActivity context, RequestListener mCallBackListener
            , int sectionId, Integer month, int option, int pagecount, int lastid){
        ContactListReq req = new ContactListReq();
        req.setData(sectionId, month, option, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, ContactListReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(ContactListBean.class,mCallBackListener,context, Contants.GET_CONTACT_LIST));
        j.setMethod(Contants.Post);
        j.setLoadingShow(false);
        j.setUrl(Contants.GET_CONTACT_LIST);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取任务联系单详情
     * @param context
     * @param mCallBackListener
     * @param id
     */
    public static void requestContactDetail( BaseActivity context, RequestListener mCallBackListener, int id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", ""+id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(ContactDetailBean.class,mCallBackListener,context, Contants.SHOW_CONTACT_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setIsExit(true);
        j.setUrl(Contants.SHOW_CONTACT_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 下发任务联系单
     * @param context
     * @param mCallBackListener
     * @param id
     * @param owner
     * @param ownerDescription
     * @param issuedCompanyId
     * @param ownerSchedule
     * @param departmentHead
     * @param sourcesFund
     * @param leaderOpinion
     * @param attachmentList
     * @param issued 是否下达 0：保存  1：下达
     */
    public static void requestUpdateContact( BaseActivity context, RequestListener mCallBackListener
            , int id, String owner, String ownerDescription, String issuedCompanyId
            , String ownerSchedule, String departmentHead, String sourcesFund, String leaderOpinion,String contractType
            , List<String> attachmentList, int issued){
        UpdateContactReq req = new UpdateContactReq();
        req.setData(id, owner, ownerDescription, issuedCompanyId, ownerSchedule, departmentHead, sourcesFund
                , leaderOpinion, contractType, attachmentList, issued);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, UpdateContactReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class, mCallBackListener, context, Contants.UPDATE_CONTACT_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.UPDATE_CONTACT_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 返工任务联系单
     * @param context
     * @param mCallBackListener
     * @param objectId
     * @param refuseOpinion
     */
    public static void requestRefuseContact( BaseActivity context, RequestListener mCallBackListener
            , String objectId, String refuseOpinion){
        RefuseContactReq req = new RefuseContactReq();
        req.setData(objectId, refuseOpinion);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, RefuseContactReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.REFUSE_CONTACT_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.REFUSE_CONTACT_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 结束任务联系单
     * @param context
     * @param mCallBackListener
     * @param id
     * @param reason
     */
    public static void requestCloseContact( BaseActivity context, RequestListener mCallBackListener
            , int id, String reason){
        CloseContactReq req = new CloseContactReq();
        req.setData(id, reason);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CloseContactReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.CLOSE_CONTACT_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CLOSE_CONTACT_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取任务联系单流程日志
     * @param context
     * @param mCallBackListener
     * @param id
     */
    public static void requestContactFlowList( BaseActivity context, RequestListener mCallBackListener, int id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", ""+id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(ContactFlowBean.class,mCallBackListener,context, Contants.CONTRACT_FLOW_LOG_LIST));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CONTRACT_FLOW_LOG_LIST);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取任务单列表
     * @param context
     * @param mCallBackListener
     * @param sectionId
     * @param month
     * @param option 1.待办，2.处理中，3.办结
     * @param pagecount
     * @param lastid
     */
    public static void requestPageTaskList( BaseActivity context, RequestListener mCallBackListener
            , int sectionId, Integer month, int option, int pagecount, int lastid){
        TaskListReq req = new TaskListReq();
        req.setData(sectionId, month, option, pagecount, lastid);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, TaskListReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(TaskListBean.class,mCallBackListener,context, Contants.PAGE_TASK_FORM_LIST));
        j.setMethod(Contants.Post);
        j.setLoadingShow(false);
        j.setUrl(Contants.PAGE_TASK_FORM_LIST);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取任务单详情
     * @param context
     * @param mCallBackListener
     * @param id
     */
    public static void requestTaskDetail( BaseActivity context, RequestListener mCallBackListener, int id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", ""+id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(TaskDetailBean.class,mCallBackListener,context, Contants.SHOW_TASK_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.SHOW_TASK_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 新增或修改任务单
     * @param context
     * @param mCallBackListener
     * @param workFormId
     * @param sectionId
     * @param rationale
     * @param sourcesFund
     * @param finishDate
     * @param description
     * @param location
     * @param owner
     * @param departmentHead
     * @param photoList
     * @param attachmentList
     * @param issued 0：保存 1：下发
     */
    public static void requestAddTaskForm( BaseActivity context, RequestListener mCallBackListener
            , String workFormId, int sectionId, String rationale, String sourcesFund
            , long finishDate, String description, String location, String owner, String departmentHead
            , List<String> photoList, List<String> attachmentList, int issued){
        AddTaskFormReq req = new AddTaskFormReq();
        req.setData(workFormId, sectionId, rationale, sourcesFund, finishDate, description, location
                , owner, departmentHead, photoList, attachmentList, issued);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, AddTaskFormReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.ADD_TASK_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.ADD_TASK_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 返工任务单
     * @param context
     * @param mCallBackListener
     * @param objectId
     * @param refuseOpinion
     */
    public static void requestRefuseTask( BaseActivity context, RequestListener mCallBackListener
            , int objectId, String refuseOpinion){
        RefuseTaskReq req = new RefuseTaskReq();
        req.setData(objectId, refuseOpinion);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, RefuseTaskReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.REFUSE_TASK_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.REFUSE_TASK_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 结束任务单
     * @param context
     * @param mCallBackListener
     * @param workFormId
     * @param reason
     */
    public static void requestCloseTask( BaseActivity context, RequestListener mCallBackListener
            , int workFormId, String reason){
        CloseTaskFormReq req = new CloseTaskFormReq();
        req.setData(workFormId, reason);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, CloseTaskFormReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.CLOSE_TASK_FORM));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CLOSE_TASK_FORM);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取任务单流程日志
     * @param context
     * @param mCallBackListener
     * @param id
     */
    public static void requestTaskFlowList( BaseActivity context, RequestListener mCallBackListener, int id){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", ""+id));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(TaskFlowBean.class,mCallBackListener,context, Contants.TASK_FLOW_LOG_LIST));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.TASK_FLOW_LOG_LIST);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取通讯录列表
     * @param context
     * @param mCallBackListener
     * @param searchText
     */
    public static void requestPageAddressbook( BaseActivity context, RequestListener mCallBackListener
            , String searchText){
        PageAddressbookReq req = new PageAddressbookReq();
        req.setData(searchText, searchText, searchText);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, PageAddressbookReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(PageAddressbookBean.class,mCallBackListener,context, Contants.PAGE_ADDRESSBOOK));
        j.setMethod(Contants.Post);
        j.setLoadingShow(false);
        j.setUrl(Contants.PAGE_ADDRESSBOOK);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 添加通讯录
     * @param context
     * @param mCallBackListener
     * @param name
     * @param telephone
     * @param email
     * @param organization
     * @param organizationName
     */
    public static void requestAddAddressbook( BaseActivity context, RequestListener mCallBackListener
            , String name, String telephone, String email, int organization, String organizationName){
        AddAddressbookReq req = new AddAddressbookReq();
        req.setData(name, telephone, email, organization, organizationName);
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("json", new Gson().toJson(req, AddAddressbookReq.class)));
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(com.pactera.chengguan.municipal.bean.BaseBean.class,mCallBackListener,context, Contants.ADD_ADDRESSBOOK));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.ADD_ADDRESSBOOK);
        j.setParams(params);
        requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }
    /**
     * 文件上传
     * @param context
     * @param mCallBackListener
     * @param object    图片所属，见MunicipalContants.UPLOAD_OBJECT_CASE | UPLOAD_OBJECT_NOTICE | UPLOAD_OBJECT_ISSUE
     * @param type  0-处理前 1-处理后
     * @param file
     * @param fileName
     */
    public static void requestFileUpload( BaseActivity context, RequestListener mCallBackListener
            , String object, int type, File file, String fileName){
        ArrayList<AppRequestParam> params = new ArrayList<AppRequestParam>();
        params.add(new AppRequestParam("access_token",  ChengApplication.instance.municipalApplication.access_token));
        params.add(new AppRequestParam("system", Contants.SYSTEM_MUNICIPAL));
        params.add(new AppRequestParam("object", object));
        params.add(new AppRequestParam("type", ""+type));
        ArrayList<AppRequestFile> params_files=new ArrayList<AppRequestFile>();
        AppRequestFile rFile = new AppRequestFile();
        rFile.setFile(file);
        rFile.setName(fileName);
        params_files.add(rFile);
        AppRequestPair   j= new AppRequestPair  ();
        j.setContext(context);
        j.setRequest(new BaseCallback(FileUploadBean.class, mCallBackListener, context, Contants.FILE_UPLOAD));
        j.setMethod(Contants.File);
        j.setUrl(Contants.FILE_UPLOAD);
        j.setLoadingShow(true);
        j.setParams(params);
        j.setParams_files(params_files);
         ChenguanOkHttpManager.request(j);
    }

}
