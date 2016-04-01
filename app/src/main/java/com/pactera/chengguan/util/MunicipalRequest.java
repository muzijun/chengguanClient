package com.pactera.chengguan.util;

import android.os.Environment;

import com.google.gson.Gson;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.ChenguanOkHttpManager;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.municipal.CaseCheckReq;
import com.pactera.chengguan.bean.municipal.CaseCreateReq;
import com.pactera.chengguan.bean.municipal.CaseDelayRecordBean;
import com.pactera.chengguan.bean.municipal.CaseDelayReq;
import com.pactera.chengguan.bean.municipal.CaseFlowBean;
import com.pactera.chengguan.bean.municipal.CaseListBean;
import com.pactera.chengguan.bean.municipal.CaseListReq;
import com.pactera.chengguan.bean.municipal.CaseNormalReq;
import com.pactera.chengguan.bean.municipal.CaseNotSecondReq;
import com.pactera.chengguan.bean.municipal.LoginBean;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.RequestFile;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.model.RequestParam;

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
        j.setRequest(new BaseCallback(LoginBean.class,mCallBackListener,context, Contants.USER_LOGIN));
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
        j.setRequest(new BaseCallback(CaseListBean.class,mCallBackListener,context, Contants.CASE_LIST));
        j.setMethod(Contants.Post);
        j.setUrl(Contants.CASE_LIST);
        j.setLoadingShow(true);
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
        ArrayList<RequestFile> params_files=new ArrayList<RequestFile>();
        if(pic != null) {
            for (String path : pic) {
                String name = path.substring(path.lastIndexOf("/") + 1);
                //压缩
                ImgCompress compress = new ImgCompress(path, name);
                File file = compress.resizeBitmap();
                RequestFile rFile = new RequestFile();
                rFile.setFile(file);
                rFile.setName(name);
                params_files.add(rFile);
            }
        }
        CaseCreateReq req = new CaseCreateReq();
        req.setData(type, caseId, description, point, termtime, case_addree, longitude, latitude
                , unit, category, month, pic);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseCreateReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context, Contants.CASE_CREATE));
        j.setMethod(Contants.File);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_CREATE);
        j.setParams(params);
        j.setParams_files(params_files);
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
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context, Contants.CASE_DELAY));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_DELAY);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件结案接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     */
    public static void requestCaseFinish(BaseActivity context, RequestListener mCallBackListener
            , int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context, Contants.CASE_FINISH));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_FINISH);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取案件延期记录接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     */
    public static void requestCaseDelayRecord(BaseActivity context, RequestListener mCallBackListener
            , int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseDelayRecordBean.class,mCallBackListener,context, Contants.CASE_DELAY_RECORD));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_DELAY_RECORD);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 获取案件流程日志
     * @param context
     * @param mCallBackListener
     * @param caseid
     */
    public static void requestCaseFlow(BaseActivity context, RequestListener mCallBackListener
            , int caseid){
        CaseNormalReq req = new CaseNormalReq();
        req.setData(caseid);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseNormalReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(CaseFlowBean.class,mCallBackListener,context, Contants.CASE_FLOW));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_FLOW);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
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
    public static void requestCaseCheck(BaseActivity context, RequestListener mCallBackListener
            , int caseid, String type, int point, int month){
        CaseCheckReq req = new CaseCheckReq();
        req.setData(caseid, type, point, month);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseCheckReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context, Contants.CASE_CHECK));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_CHECK);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

    /**
     * 案件不通过返工接口
     * @param context
     * @param mCallBackListener
     * @param caseid    案件id
     * @param opinion   返工意见
     */
    public static void requestCaseNotSecond(BaseActivity context, RequestListener mCallBackListener
            , int caseid, String opinion){
        CaseNotSecondReq req = new CaseNotSecondReq();
        req.setData(caseid, opinion);
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("json", new Gson().toJson(req, CaseNotSecondReq.class)));
        RequestPair j= new RequestPair();
        j.setContext(context);
        j.setRequest(new BaseCallback(BaseBean.class,mCallBackListener,context, Contants.CASE_NOT_SECOND));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.CASE_NOT_SECOND);
        j.setParams(params);
        ChenguanOkHttpManager.requestIfNeedToken(j, true, Contants.SYSTEM_MUNICIPAL);
    }

}
