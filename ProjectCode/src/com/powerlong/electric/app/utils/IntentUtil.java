package com.powerlong.electric.app.utils;

import java.io.Serializable;

import org.apache.http.message.BasicNameValuePair;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.ui.ActivityDetailActivity;
import com.powerlong.electric.app.ui.AdveringWebviewActivity;
import com.powerlong.electric.app.ui.BrandActivity;
import com.powerlong.electric.app.ui.CoverFlowTestingActivity;
import com.powerlong.electric.app.ui.GroupBuyListActivity;
import com.powerlong.electric.app.ui.GrouponDetailActivity;
import com.powerlong.electric.app.ui.ItemDetailActivity;
import com.powerlong.electric.app.ui.SearchBaseActivity;
import com.powerlong.electric.app.ui.SearchBaseActivityNew;
import com.powerlong.electric.app.ui.ShopDetailActivity;
import com.powerlong.electric.app.ui.ShopDetailActivityNew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtil {
	public static void start_activity(Activity activity,Class<?> cls,BasicNameValuePair...name)
	{
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		for(int i=0;i<name.length;i++)
		{
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/**
	 * startActivity(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param activity
	 * @param class1 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	public static void startActivity(Activity activity,
			Class<SearchBaseActivityNew> cls,Serializable object) {
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		Bundle bundle = new Bundle();
		bundle.putSerializable("params", object);
		intent.putExtra("params", bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	/**
	 * 
	 * 启动登陆界面
	 * @param activity
	 * @param cls 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	
	public static void startLoginActivityForResult(Activity activity,Class<?> cls){
		
	}

	/**
	 * 首页图片点击后跳转
	 * @param mContext
	 * @param params 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	public static void startHomePageLinkActivity(Context mContext, int type,String params) {
		Intent intent = new Intent();
		switch(type){
		case Constants.RecommendLinkType.LINK:
			
			break;
		case Constants.RecommendLinkType.SHOP:
			intent.putExtra("methodParams", params);
			intent.setClass(mContext, ShopDetailActivityNew.class);
			mContext.startActivity(intent);
			break;
		case Constants.RecommendLinkType.ITEM:
			intent.putExtra("methodParams", params);
			intent.setClass(mContext, ItemDetailActivity.class);
			mContext.startActivity(intent);
			break;
		case Constants.RecommendLinkType.GROUPON:
			if(Constants.isNeedGroupon){
				intent.putExtra("methodParams", params);
				intent.setClass(mContext, GrouponDetailActivity.class);
				mContext.startActivity(intent);
			}else{
				intent.setClass(mContext, CoverFlowTestingActivity.class);
				mContext.startActivity(intent);
			}
			break;
		case Constants.RecommendLinkType.ACTIVITY:
			intent.putExtra("methodParams", params);
			intent.setClass(mContext, ActivityDetailActivity.class);
			mContext.startActivity(intent);
			break;
		case Constants.RecommendLinkType.GROUPON_LIST:
			intent.setClass(mContext, GroupBuyListActivity.class);
			mContext.startActivity(intent);
			break;
		case Constants.RecommendLinkType.Brand:
			intent.setClass(mContext, BrandActivity.class);
			mContext.startActivity(intent);
		}		
	}
	
	public static void startHomeLinkActivity(Context mContext, String params, String adDis) {
		if(adDis.equals("#") || params.equals("#")) {
			return;
		}
		Intent intent = new Intent();
		intent.putExtra("params", params);
		intent.putExtra("adDis", adDis);
		intent.setClass(mContext, AdveringWebviewActivity.class);
		mContext.startActivity(intent);
	}
}
