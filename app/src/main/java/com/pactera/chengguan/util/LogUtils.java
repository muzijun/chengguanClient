package com.pactera.chengguan.util;

import android.util.Log;

/**
 * log工具类
 * Created by huang hua
 * 2016/3/15.
 */
public class LogUtils {

    public static final boolean isShowLog = true;

    public static final String DEFAULT_TAG="TUDUR";

    public static void v(String tag, String msg) {
        if (isShowLog) {
            if (tag==null) {
                tag=DEFAULT_TAG;
                Log.v(tag, getCaller()+msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }


    public static void d(String tag, String msg) {
        if (isShowLog) {
            if (tag==null) {
                tag=DEFAULT_TAG;
                Log.d(tag, getCaller()+msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }


    public static void i(String tag, String msg) {
        if (isShowLog) {
            if (tag==null) {
                tag=DEFAULT_TAG;
                Log.i(tag, getCaller() + msg);
            } else {
                Log.i(tag, msg);
            }
        }
    }


    public static void w(String tag, String msg) {
        if (isShowLog) {
            if (tag==null) {
                tag=DEFAULT_TAG;
                Log.w(tag, getCaller()+msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }


    public static void e(String tag, String msg) {
        if (isShowLog) {
            if (tag==null) {
                tag=DEFAULT_TAG;
                Log.e(tag, getCaller()+msg);
            } else {
                Log.e(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (tag==null) tag=DEFAULT_TAG;
        if (isShowLog) Log.e(tag, msg, e);
    }

    public static void e(Throwable e) {
        if (isShowLog) Log.e(DEFAULT_TAG, e.getMessage(), e);
    }

    public static String getCaller(){
        StringBuffer buffer=new StringBuffer();
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        StackTraceElement ste=stack[2];
        buffer.append(ste.getClassName());
        buffer.append(".").append(ste.getMethodName());
        buffer.append("(").append(ste.getLineNumber()).append(") ");
        return buffer.toString();
    }

}
