/**
 * 宝龙电商
 * com.powerlong.electric.app.location
 * Location.java
 * 
 * 2013-7-30-下午07:41:23
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.location;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.powerlong.electric.app.listener.PlLocationListener;
import com.powerlong.electric.app.utils.LogUtil;

import android.content.Context;

/**
 * 
 * Location
 * 
 * @author: Liang Wang
 * 2013-7-30-下午07:41:23
 * 
 * @version 1.0.0
 * 
 */
public class Location {
	private static Location s_instance = null;
	private static Context mContext = null;
	private static LocationClient mClient;
	private static LocationClientOption mOption;
	private static PlLocationListener mListener;
	
	public static Location getInstance(Context context){
		mContext = context;
		if(s_instance == null){
			s_instance = new Location();
			initLBS();
		}
		return s_instance;
	}

	private Location(){
		
	}
	
	/**
	 * initLBS(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private static void initLBS() {
		mOption = new LocationClientOption();
		mOption.setOpenGps(true);
		mOption.setCoorType("bd09ll");
		mOption.setAddrType("all");
		mOption.setScanSpan(100);
		mOption.disableCache(true);
		mOption.setPoiNumber(20);
		mOption.setPoiDistance(1000);
		mOption.setPoiExtraInfo(true);
		mOption.setPriority(LocationClientOption.GpsFirst);
		mClient = new LocationClient(mContext, mOption);
	}
	
	public void setListener(PlLocationListener listener){
		LogUtil.d("Location", "setListener mListener="+listener);
		mListener = listener;
		mClient.registerLocationListener(mListener);
	}
	
	public void start(){
		if(mClient!=null){
			mClient.start();
			mClient.requestLocation();
			mListener.startListener();
		}
	}
	
	public void stop(){
		if(mClient!=null && mClient.isStarted()){
			mClient.stop();
			LogUtil.d("Location", "mListener="+mListener);
		}
		mClient.unRegisterLocationListener(mListener);
	}

}
