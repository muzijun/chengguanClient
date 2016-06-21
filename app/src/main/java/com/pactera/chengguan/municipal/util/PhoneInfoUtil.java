package com.pactera.chengguan.municipal.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 手机信息
 * Created by huang hua
 * 2016/5/3.
 */
public class PhoneInfoUtil {

    /**
     * 获取app的版本数字
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
           versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionCode;
    }

}
