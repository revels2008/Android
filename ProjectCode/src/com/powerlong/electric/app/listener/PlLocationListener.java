/**
 * 宝龙电商
 * com.powerlong.electric.app.listener
 * PlLocationListener.java
 * 
 * 2013-7-31-上午09:49:58
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.listener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;


/**
 * 
 * PlLocationListener
 * 
 * @author: Liang Wang
 * 2013-7-31-上午09:49:58
 * 
 * @version 1.0.0
 * 
 */
public abstract class PlLocationListener implements BDLocationListener{
	private Context mContext = null;
	private final int TIME_OUT = 3*1000;
	/* (non-Javadoc)
	 * @see com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu.location.BDLocation)
	 */
	private Handler mHandler = new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		
	};
	
	private Runnable mTask = new Runnable() {
		@Override
		public void run() {
			OnTimeout();
		}
	};
	
	public PlLocationListener(Context context){
		mContext = context;
	}
	
	public void startListener(){
		mHandler.postDelayed(mTask, TIME_OUT);
	}
	
	@Override
	public void onReceiveLocation(BDLocation bdlocation) {
		if(TextUtils.isEmpty(bdlocation.getCity()))
			return;
		mHandler.removeCallbacks(mTask);
		OnGetLocationSuccess(bdlocation);
	}

	/* (non-Javadoc)
	 * @see com.baidu.location.BDLocationListener#onReceivePoi(com.baidu.location.BDLocation)
	 */
	@Override
	public void onReceivePoi(BDLocation bdlocation) {
		
	}
	
	public abstract void OnTimeout();
	public abstract void OnGetLocationSuccess(BDLocation location);
}
