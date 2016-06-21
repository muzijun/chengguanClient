package com.pactera.chengguan.municipal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.activity.LoginActivity;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.model.PushData;
import com.pactera.chengguan.municipal.util.SPUtils;
import com.pactera.chengguan.municipal.view.PushNotification;

/**
 * 广播接收器
 *
 * @author 00225075
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent == null || context == null) {
            return;
        }
        String action = intent.getAction();
        if (action.equals(Contants.NOTIFICATION_ACTION_SEND)) {
            int notifyid = intent
                    .getIntExtra(Contants.NOTIFICATION_NOTIFYID, 0);
            PushData pushData = (PushData)intent.getSerializableExtra(Contants.NOTIFICATION_PUSH);
            String token = (String) SPUtils.get(context, MunicipalCache.SP_TOKEN, "");
            if(token.length()>0) {
                    Intent push_intent = new Intent(context,
                            LoginActivity.class);
                    push_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    push_intent.putExtra(Contants.PUSH_JSON, pushData);
                    context.startActivity(push_intent);

            }
            PushNotification.clearNotify(context, notifyid);
        }
    }

}
