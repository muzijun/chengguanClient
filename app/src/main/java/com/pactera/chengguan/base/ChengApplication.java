package com.pactera.chengguan.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.chengguan.dao.DaoMaster;
import com.chengguan.dao.DaoSession;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.db.ChengguanOpenHelper;
import com.pactera.chengguan.db.DBHelper;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * MyApplication
 *
 * @author lijun
 */
public class ChengApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Logger.init().setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Environment.getExternalStorageDirectory();
        Cache cache = new Cache(this.getCacheDir(), cacheSize);
        OkHttpUtils.getInstance().getOkHttpClient().setCache(cache);
        OkHttpUtils.getInstance().getOkHttpClient().interceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        OkHttpUtils.getInstance().getOkHttpClient().setConnectTimeout(100000, TimeUnit.MILLISECONDS);
    }



    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "only-if-cached")
                    .build();
        }
    };
}
