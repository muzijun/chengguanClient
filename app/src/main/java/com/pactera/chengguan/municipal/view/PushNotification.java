package com.pactera.chengguan.municipal.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.model.PushData;

public class PushNotification {

	private Context mContext;
	private PushData  data;
	/** Notification构造器 */
	private NotificationCompat.Builder mBuilder;
	private NotificationManager mNotificationManager;

	public PushNotification(Context context, PushData data) {
		this.mContext = context;
		this.data = data;
	}

	/**
	 * 初始化要用到的系统服务
	 */
	private void initService() {
		mNotificationManager = (NotificationManager) mContext
				.getSystemService(mContext.NOTIFICATION_SERVICE);
	}

	/** 初始化通知栏 */
	private void initNotify(int notifyId) {
		mBuilder = new NotificationCompat.Builder(mContext);
		Intent intent = new Intent(Contants.NOTIFICATION_ACTION_SEND);
		intent.putExtra(Contants.NOTIFICATION_PUSH, data);
		intent.putExtra(Contants.NOTIFICATION_NOTIFYID, notifyId);
		mBuilder.setContentTitle(data.getTitle())
				.setContentText(data.getMessage())
				.setContentIntent(
						PendingIntent.getBroadcast(mContext,
								notifyId, intent, PendingIntent.FLAG_CANCEL_CURRENT))
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_SOUND)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				.setSmallIcon(R.mipmap.logo)
		        .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo));

	}

	/** 显示通知栏 */
	public void showNotify(int notifyId) {
		initService();
		initNotify(notifyId);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * 清除当前创建的通知栏
	 */
	public static void clearNotify(Context mContext,int notifyId) {
		NotificationManager mNotificationManager=(NotificationManager) mContext
				.getSystemService(mContext.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyId);// 删除一个特定的通知ID对应的通知
		// mNotification.cancel(getResources().getString(R.string.app_name));
	}

	/**
	 * 清除所有通知栏
	 * */
	public static void clearAllNotify(Context mContext) {
		NotificationManager mNotificationManager=(NotificationManager) mContext
				.getSystemService(mContext.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();// 删除你发的所有通知
	}

}
