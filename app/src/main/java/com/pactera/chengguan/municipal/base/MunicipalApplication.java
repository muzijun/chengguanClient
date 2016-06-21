package com.pactera.chengguan.municipal.base;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.activity.LoginActivity;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.util.SPUtils;
import com.pactera.chengguan.municipal.view.PushNotification;

import java.util.List;

/**  存放市政的变量
 * Created by lijun on 2016/6/16.
 */
public class MunicipalApplication {
    //用户登录token
    public String access_token = "";
    //用户登录系统权限组
    public List<String> authValue;
    //用户选择子系统
    public int selectAuthIndex = 0;

    /**
     * Session过期后，跳转到登录界面
     */
    public void sessionErrorToLogin(final Context context){
        PushNotification.clearAllNotify(context);
        Toast.makeText(context, R.string.session_error, Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SPUtils.remove(context, MunicipalCache.SP_TOKEN);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        }).start();
    }

}
