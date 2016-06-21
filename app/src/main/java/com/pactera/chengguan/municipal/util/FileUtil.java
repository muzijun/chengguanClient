package com.pactera.chengguan.municipal.util;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 文件操作
 * Created by lijun on 2015/12/29.
 */
public class FileUtil {

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
    /**
     * 文件是否存在
     *
     * @param filePathName
     * @return
     */
    public static boolean isExist(String filePathName) {
        File file = new File(filePathName);
        return (!file.isDirectory() && file.exists());
    }

    public static boolean isDirExist(String filePathName) {
        if (!filePathName.endsWith("/")) filePathName += "/";
        File file = new File(filePathName);
        return (file.isDirectory() && file.exists());
    }

    /**
     * 获取路径，不带文件名，末尾带'/'
     *
     * @param filePathName
     * @return
     */
    public static String getPath(String filePathName) {
        try {
            return filePathName.substring(0, filePathName.lastIndexOf('/') + 1);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取目录的名称 注意：只能获取如：/aaaa/ssss/ 或 /aaaa/dsddd
     *
     * @param filePathName
     * @return
     */
    public static String getDirPathName(String filePathName) {
        try {
            if (filePathName.endsWith("/"))
                filePathName = filePathName.substring(0, filePathName.lastIndexOf('/'));
            return filePathName.substring(filePathName.lastIndexOf("/") + 1, filePathName.length());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取文件名，带后缀
     *
     * @param filePathName
     * @return
     */
    public static String getName(String filePathName) {
        try {
            return filePathName.substring(filePathName.lastIndexOf('/') + 1);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取文件名，不带后缀
     *
     * @return
     */
    public static String getNameNoPostfix(String filePathName) {
        try {
            return filePathName.substring(filePathName.lastIndexOf('/') + 1, filePathName.lastIndexOf('.'));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 重命名
     *
     * @param filePathName
     * @param newPathName
     */
    public static void rename(String filePathName, String newPathName) {
        if (isNullString(filePathName)) return;
        if (isNullString(newPathName)) return;

        delete(newPathName); // 新名称对应的文件可能已经存在，先删除

        File file = new File(filePathName);
        File newFile = new File(newPathName);
        file.renameTo(newFile);
    }

    /**
     * 删除文件
     */
    public static void delete(String filePathName) {
        if (isNullString(filePathName)) return;
        File file = new File(filePathName);
        if (file.isFile() && file.exists()) {
            boolean flag = file.delete();
        }
    }

    /**
     * 创建目录，整个路径上的目录都会创建
     *
     * @param path
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 尝试创建空文件
     * <br> 如果文件已经存在不操作,返回true
     *
     * @param path 路径
     * @return 如果创建失败(Exception) 返回false，否则true
     */
    public static boolean createEmptyFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取文件大小
     *
     * @param filePathName
     * @return
     */
    public static long getSize(String filePathName) {
        if (isNullString(filePathName)) return 0;
        File file = new File(filePathName);
        if (file.isFile()) return file.length();
        return 0;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }



    /**
     * 判断当前字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullString(String str) {
        if (str == null || str.equals("")) return true;
        return false;
    }


    /**
     * 根据zip文件路径转换为文件路径
     *
     * @param zipFullPath 必须带.zip
     * @return
     */
    public static String zip2FileFullPath(String zipFullPath) {
        int zipIndex = zipFullPath.lastIndexOf(".zip");
        int zipIndexTmp = zipFullPath.lastIndexOf(".ZIP");
        String tmp = "";
        if (zipIndex > -1) {
            tmp = zipFullPath.substring(0, zipIndex);
        } else if (zipIndexTmp > -1) {
            tmp = zipFullPath.substring(0, zipIndexTmp);
        }
        return tmp;
    }

}
