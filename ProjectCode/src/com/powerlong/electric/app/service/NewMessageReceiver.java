/**
 * 宝龙电商
 * com.powerlong.electric.app.service
 * NewMessageReceiver.java
 * 
 * 2013-11-25-下午1:38:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.HomeActivityNew;
import com.powerlong.electric.app.ui.MyAccountFragment;
import com.powerlong.electric.app.ui.MyMessageActivity;
import com.powerlong.electric.app.ui.MyNotificationActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.RemoteViews;

/**
 * 
 * NewMessageReceiver
 * 
 * @author: Liang Wang
 * 2013-11-25-下午1:38:43
 * 
 * @version 1.0.0
 * 
 */
public class NewMessageReceiver extends Service {

	NotificationManager mNotificationManager;
	SharedPreferences prefs;
	Editor editor;
	int iconImageId;
	String content_msg = "";
	String content_not = "";
	
	public final static String BROADCAST_NEW_MESSAGES_TIPS = "com.powerlong.broadcast.newmessage";
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		if(mReceiveMessages.getState() == Thread.State.NEW){
			mReceiveMessages.start();
		}
		super.onStart(intent, startId);
	};
	
	Thread mReceiveMessages = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				if(DataCache.UserDataCache.size() != 0){
					JSONObject obj = new JSONObject();
					try {
						obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
						obj.put("mall", Constants.mallId);
						DataUtil.queryMyMessageListData(getBaseContext(),
								mServerConnectionHandler, obj.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					HttpUtil.queryUnReadMsgJson(NewMessageReceiver.this,mServerConnectionHandler,obj.toString());
					try {
						Thread.sleep(1000*60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	});
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("MyMessageActivity", "msg.what = " + msg.what);
			LogUtil.d("MyMessageActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				if(Constants.unReadMessageNum > 0 || Constants.unReadNotifyNum > 0){
//					Intent mIntent = new Intent(BROADCAST_NEW_MESSAGES_TIPS);
//					sendBroadcast(mIntent);
					sendNotification();
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				break;
			}
		}

		
	};
	
	
	private void sendNotification() {
		mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		iconImageId = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		Intent notificationIntent = null;
		
		
		if(Constants.unReadMessageNum != 0){
			content_msg = "您有"+Constants.unReadMessageNum+"条未读消息";
//			notificationIntent = new Intent(NewMessageReceiver.this, MyMessageActivity.class);
		}else{
			content_not = "";
//			notificationIntent = new Intent(NewMessageReceiver.this, MyNotificationActivity.class);
		}
		if(Constants.unReadNotifyNum != 0){
			content_not = "您有"+Constants.unReadNotifyNum+"条未读通知";
//			notificationIntent = new Intent(NewMessageReceiver.this, MyNotificationActivity.class);
		}else{
			content_not = "";
//			notificationIntent = new Intent(NewMessageReceiver.this, MyMessageActivity.class);
		}
		
		notificationIntent = new Intent(NewMessageReceiver.this, HomeActivityNew.class);
		Notification notification = new Notification(iconImageId, content_msg+" "+content_not, when);
		RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.notify_main);
		contentView.setImageViewResource(R.id.notify_icon,R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.notify_content_msg,content_msg);
		contentView.setTextViewText(R.id.notify_content_not,content_not);
		notification.contentView = contentView;
		
		notification.defaults |=Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		Bundle bundle = new Bundle(); 
        bundle.putInt("mTabId", 5);
		notificationIntent.putExtras(bundle);

		
		
		PendingIntent contentIntent = PendingIntent.getActivity(NewMessageReceiver.this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		
		notification.contentIntent = contentIntent;
		mNotificationManager.notify(0,notification);
	}
}
