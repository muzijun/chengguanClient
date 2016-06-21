package com.pactera.chengguan.municipal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.orhanobut.logger.Logger;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.model.PushData;
import com.pactera.chengguan.municipal.util.SPUtils;
import com.pactera.chengguan.municipal.view.PushNotification;

public class PushReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    Gson gson = new Gson();
                    PushData pushdata = gson.fromJson(data, PushData.class);
                    Logger.v("data","data"+data);
                    String token = (String) SPUtils.get(context, MunicipalCache.SP_TOKEN, "");
                    if(token.length()>0) {
                        PushNotification pushNotification = new PushNotification(
                                context, pushdata);
                        pushNotification.showNotify(ChengApplication.instance.i++);
                    }
                }

                // TODO:接收处理透传（payload）数据
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");

                break;

            default:
                break;
        }
    }
}
