package com.pactera.chengguan.config;

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


}
