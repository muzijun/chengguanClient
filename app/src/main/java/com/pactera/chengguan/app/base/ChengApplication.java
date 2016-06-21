package com.pactera.chengguan.app.base;

import android.app.Application;
import android.os.Environment;
import com.igexin.sdk.PushManager;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.app.error.CrashHandler;
import com.pactera.chengguan.municipal.base.MunicipalApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * MyApplication
 *
 * @author lijun
 */
public class ChengApplication extends Application {

    public static ChengApplication instance;
    //是否有新版本
    public boolean hasNewVersion = false;
    public static MunicipalApplication municipalApplication;
    //推送通知id号
    public static int i=0;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        municipalApplication=new MunicipalApplication();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        PushManager.getInstance().initialize(this.getApplicationContext());
        CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
        Logger.init().setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(1000*60, TimeUnit.MILLISECONDS)
                .readTimeout(1000*60, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }





}
