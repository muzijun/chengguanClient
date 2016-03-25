package com.pactera.chengguan.config;

import com.pactera.chengguan.BuildConfig;

/**
 * Created by lijun on 2015/12/16.
 */
public class Contants {
    //请求方法
    public  final static String Get="get";
    public  final static String Post="post";
    public  final static String File="file";
    //数据库名
    public  final static String DB_NAME="chengguan";

    /******************** 添加图片的参数配置 *****************/
    //单次最多发送图片数
    public static final int MAX_IMAGE_SIZE = 8;
    //首选项:临时图片
    public static final String PREF_TEMP_IMAGES = "pref_temp_images";
    //当前选择的照片位置
    public static final String EXTRA_CURRENT_IMG_POSITION = "current_img_position";
    //相册中图片对象集合
    public static final String EXTRA_IMAGE_LIST = "image_list";
    //相册名称
    public static final String EXTRA_BUCKET_NAME = "buck_name";
    //可添加的图片数量
    public static final String EXTRA_CAN_ADD_IMAGE_SIZE = "can_add_image_size";

    /******************** 客户端区分字段 **************************/
    public static final int MOBILE_CHENGGUAN = 1;       //城管客户端
    public static final int MOBILE_UNIT = 2;            //作业单位客户端

    /******************** 系统类别字段 **************************/
    public static final String SYSTEM_MUNICIPAL = "SZ";     //市政

    /******************** 网络接口地址 *************************/
    //登陆
    public static final String USER_LOGIN = BuildConfig.BASE_URL + "login_mobile";
    //获取案件列表
    public static final String CASE_LIST = BuildConfig.BASE_URL + "case_list.mobile";
    //新建案件
    public static final String CASE_CREATE = BuildConfig.BASE_URL + "newOrUpdateCase.mobile";
    //案件延期
    public static final String CASE_DELAY = BuildConfig.BASE_URL + "caseDelay.mobile";
    //案件结案
    public static final String CASE_FINISH = BuildConfig.BASE_URL + "caseFinish.mobile";
    //获取案件延期记录
    public static final String CASE_DELAY_RECORD = BuildConfig.BASE_URL + "caseDelayRecord.mobile";
    //获取案件流程日志
    public static final String CASE_FLOW = BuildConfig.BASE_URL + "caseFlow.mobile";
    //案件考核
    public static final String CASE_CHECK = BuildConfig.BASE_URL + "caseCheck.mobile";
    //案件不通过返工
    public static final String CASE_NOT_SECOND = BuildConfig.BASE_URL + "caseNotSecond.mobile";


    /******************** 网络结果参数和错误码 *****************/
    //成功
    public static final int RESULT_OK = 0;
    //session过期
    public static final int ERROR_SESSION = 101;
    //登录失败
    public static final int ERROR_LOGIN = 102;

}
