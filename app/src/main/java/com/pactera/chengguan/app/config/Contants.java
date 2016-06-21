package com.pactera.chengguan.app.config;

import android.os.Environment;

import com.pactera.chengguan.BuildConfig;

/**
 * Created by lijun on 2015/12/16.
 */
public class Contants {

    /******************** 上传文件大小限制 *****************/
    public static final long File_total_size=1048576*30;
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
    //获取筛选项动态数据
    public static final String SELECT_SCREEN_ITEM = BuildConfig.BASE_URL + "select_screen_item.mobile";
    //获取考核库
    public static final String SELECT_CHECKLIB = BuildConfig.BASE_URL + "select_checklib.mobile";
    //获取基础数据列表
    public static final String BASIC_INFO = BuildConfig.BASE_URL + "getBasicinfo.mobile";
    //同步服务器时间
    public static final String GET_SERVER_TIME = BuildConfig.BASE_URL + "get_server_time.mobile";
    //上传文件
    public static final String FILE_UPLOAD = BuildConfig.BASE_URL + "upload.mobile";
    //获取个人信息
    public static final String GET_OPERATOR_INFO = BuildConfig.BASE_URL + "system/get_operator_info.mobile";
    //获取app版本
    public static final String GET_APP_VERSION = BuildConfig.BASE_URL + "system/get_app_version.mobile";
    //注销
    public static final String USER_LOGOUT = BuildConfig.BASE_URL + "logoff.mobile";
    //获取道路巡查列表
    public static final String PATROL_RECORD_ROAD = BuildConfig.BASE_URL + "page_patrol_record_road.mobile";
    //获取桥梁巡查列表
    public static final String PATROL_RECORD_BRIDGE = BuildConfig.BASE_URL + "page_patrol_record_bridge.mobile";
    //更改巡查任务状态
    public static final String UPDATE_PATROL_RECORD = BuildConfig.BASE_URL + "update_patrol_record.mobile";
    //巡查任务异常扣分
    public static final String POINTS_PATROL_RECORD = BuildConfig.BASE_URL + "points_patrol_record.mobile";
    //获取案件列表
    public static final String CASE_LIST = BuildConfig.BASE_URL + "case_list.mobile";
    //新建案件
    public static final String CASE_CREATE = BuildConfig.BASE_URL + "addOrUpdateCase.mobile";
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
    //获取单个案件详情
    public static final String CASE_DETAIL = BuildConfig.BASE_URL + "showCase.mobile";
    //获取通知列表
    public static final String PAGE_NOTICE_LIST = BuildConfig.BASE_URL + "page_notice_list.mobile";
    //新增或修改通知
    public static final String ADD_UPDATE_NOTICE = BuildConfig.BASE_URL + "add_update_notice.mobile";
    //获取通知详情
    public static final String SHOW_NOTICE = BuildConfig.BASE_URL + "show_notice.mobile";
    //获取养护日志列表
    public static final String PAGE_ISSUE_LIST = BuildConfig.BASE_URL+ "page_issue_list.mobile";
    //养护日志详情
    public static final String SHOW_ISSUE = BuildConfig.BASE_URL + "show_issue.mobile";
    //养护日志返工
    public static final String REFUSE_ISSUE = BuildConfig.BASE_URL + "refuse_issue.mobile";
    //养护日志结案
    public static final String CLOSE_ISSUE = BuildConfig.BASE_URL + "close_issue.mobile";
    //确认养护日志
    public static final String CONFIRM_ISSUE = BuildConfig.BASE_URL + "confirm_issue.mobile";
    //获取任务联系单列表
    public static final String GET_CONTACT_LIST = BuildConfig.BASE_URL + "page_contact_form_list.mobile";
    //获取任务联系单详情
    public static final String SHOW_CONTACT_FORM = BuildConfig.BASE_URL + "show_contact_form.mobile";
    //下发任务联系单
    public static final String UPDATE_CONTACT_FORM = BuildConfig.BASE_URL + "update_contact_form.mobile";
    //返工任务联系单
    public static final String REFUSE_CONTACT_FORM = BuildConfig.BASE_URL + "refuse_contact_form.mobile";
    //结束任务联系单
    public static final String CLOSE_CONTACT_FORM = BuildConfig.BASE_URL + "close_contact_form.mobile";
    //获取任务联系单流程日志
    public static final String CONTRACT_FLOW_LOG_LIST = BuildConfig.BASE_URL + "contract_flow_log_list.mobile";
    //获取任务单列表
    public static final String PAGE_TASK_FORM_LIST = BuildConfig.BASE_URL + "page_task_form_list.mobile";
    //获取任务单详情
    public static final String SHOW_TASK_FORM = BuildConfig.BASE_URL + "show_task_form.mobile";
    //新增任务单
    public static final String ADD_TASK_FORM = BuildConfig.BASE_URL + "add_task_form.mobile";
    //任务单返工
    public static final String REFUSE_TASK_FORM = BuildConfig.BASE_URL + "refuse_task_form.mobile";
    //任务单结案
    public static final String CLOSE_TASK_FORM = BuildConfig.BASE_URL + "close_task_form.mobile";
    //任务单流程日志
    public static final String TASK_FLOW_LOG_LIST = BuildConfig.BASE_URL + "task_flow_log_list.mobile";
    //获取通讯录列表
    public static final String PAGE_ADDRESSBOOK = BuildConfig.BASE_URL + "addressbook/page_addressbook_list.mobile";
    //添加通讯录
    public static final String ADD_ADDRESSBOOK = BuildConfig.BASE_URL + "addressbook/add_addressbook.mobile";

    /******************** 网络结果参数和错误码 *****************/
    //成功
    public static final int RESULT_OK = 0;
    //session过期
    public static final int ERROR_SESSION = 101;
    //登录失败
    public static final int ERROR_LOGIN = 102;

    /******************** 市政系统本地路径参数配置 *****************/
    //本地根路径
    public static final String PATH_SZ = "/chengguan/sz/";
    //图片压缩路径
    public static final String PATH_IMG_COMPRESS = "compress/";
    //图片缓存路径
    public static final String PATH_IMG_CACHE = "img/";
    //本地apk下载保存路径
    public static final String PATH_APK = "/chengguan/app/";
    //崩溃log日志
    public static final String CRASH_PATH= Environment.getExternalStorageDirectory()
            .getPath() + "/chengguan/crash/";
    /******************** 工作联系单刷新传参*****************/
    public static int  TASK_REFRESH = 3;

    /******************************推送标识************************************/
    public static final int NOTIFICATION_ID_LIVE = 1; // 正常状态
    public static final String NOTIFICATION_ACTION_SEND = "com.chengguan.notifications.intent.action.Click";
    public static final String NOTIFICATION_NOTIFYID="notifyId";
    public static final String NOTIFICATION_PUSH="push";
    public static final String PUSH_JSON="push_json";
 /**
     * 工作联系单
     */
    public static final String WORK_CONTACT_FORM = "contact";

    /**
     * 任务单
     */
    public static final String WORK_TASK_FORM = "task";

    /**
     * 养护日志
     */
    public static final String ISSUE = "issue";

    /**
     * 通知
     */
    public static final String NOTICE = "notice";

    /**
     * 扣分
     */
    public static final String POINTS = "points";
    /**
     * 扣分对象---案件
     */
    public static final String CASE_POINTS_TYPE = "case";
}
