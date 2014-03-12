/**
 * 宝龙电商
 * com.powerlong.electric.app
 * ElectricApp.java
 * 
 * 2013-7-24-上午09:21:30
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.cache.LCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dao.NavigationBaseDao;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.JSONUtil;
import com.powerlong.electric.app.utils.NetworkUtil;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * ElectricApp
 * 
 * @author: Liang Wang 2013-7-24-上午09:21:30
 * 
 * @version 1.0.0
 * 
 */
public class ElectricApp extends Application {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		//.d("ElectricApp", "oncreate in..");
		 CrashHandler crashHandler = CrashHandler.getInstance();   
	     crashHandler.init(getApplicationContext());   
	     
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        Constants.screen_density = displayMetrics.density;
        Constants.displayWidth = displayMetrics.widthPixels;
        Constants.displayHeight = displayMetrics.heightPixels;
        //.d("ElectricApp", "screen_density = "+ Constants.screen_density);
        //.d("ElectricApp", "displayWidth = "+ Constants.displayWidth);
        //.d("ElectricApp", "displayHeight = "+ Constants.displayHeight);
        
        Constants.mBmpCache = new LCache();
        
        DataUtil.isUserDataExisted(getBaseContext());
        
        SharedPreferences pref = getBaseContext().getSharedPreferences("recommend",
				Context.MODE_PRIVATE);
		String middleValue = pref.getString("middle_value", "");
		String[] middleValues = middleValue.split(",");
		for(int n=0;n<middleValues.length;n++){
			if(!StringUtil.isEmpty(middleValues[n]))
				Constants.TEMPLATE_COLL.add(StringUtil.toInt(middleValues[n]));
		}
		Constants.VIEW_COUNT = pref.getInt("view_count", 0);
		//.d("ElectricApp", "VIEW_COUNT = " + Constants.VIEW_COUNT);

		
        final NavigationBaseDao dao = new NavigationBaseDao(this);
		for (int i = 0; i < Constants.JSONKeyName.NavItemIds.length; i++) {
			
			if(!NetworkUtil.isConnectInternet(getBaseContext())){
				break;
			}
			
			final String navId = Constants.JSONKeyName.NavItemIds[i];
			//.d("ElectricApp", "navId = " + navId);
			if (dao.getDataById(navId) != null
					&& dao.getDataById(navId).size() > 0){
				DataCache.NavItemCache.put(navId, dao.getDataById(navId));
				continue;
			}
			JSONObject params = new JSONObject();
			try {
				params.put("mall", Constants.mallId);
				params.put("navId", navId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			HttpUtil.getJson(Constants.ServerUrl.NAVIGATION_URL,
					params.toString(), new JsonHttpResponseHandler() {

						
						 /* (non-Javadoc)
						 * 
						 * @see
						 * com.loopj.android.http.AsyncHttpResponseHandler#onFailure
						 * (java.lang.Throwable)
						 */

						@Override
						public void onFailure(Throwable error) {
							//.d("ElectricApp", error.toString());
							// Toast.makeText(getApplicationContext(),
							// error.toString(), Toast.LENGTH_SHORT).show();
						}

						
						 /* (non-Javadoc)
						 * 
						 * @see
						 * com.loopj.android.http.AsyncHttpResponseHandler#onSuccess
						 * (int, java.lang.String)
						 */

						@Override
						public void onSuccess(int statusCode, JSONObject object) {
							//.d("ElectricApp", "onSuccess + content ="+ object.toString());
							JSONObject data = JSONUtil.getJSONObject(object,
									Constants.JSONKeyName.NAV_JSON_TOPEST_DATA,
									null);
							JSONArray array = JSONUtil.getJSONArray(data,
									Constants.JSONKeyName.NAV_JSON_TOPEST_DATA_KEY_NAME,
									null);
							ArrayList<NavigationBaseEntity> entities = new ArrayList<NavigationBaseEntity>();
							for (int index = 0; index < array.length(); index++) {
								try {
									JSONObject obj = array.getJSONObject(index);
									int selfId = JSONUtil.getInt(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_SELFID, -1);
									String iconUrl = JSONUtil.getString(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_ICON, null);
									int count = JSONUtil.getInt(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_COUNT, -1);
									String name = JSONUtil.getString(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_NAME, null);
									int groupId = JSONUtil.getInt(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_GROUPID, -1);
									String method = JSONUtil.getString(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_METHOD, null);
									int isParent = JSONUtil.getInt(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_ISPARENT, -1);
									int parentId = JSONUtil.getInt(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_PARENTID, -1);
									int level = JSONUtil.getInt(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_LEVEL, -1);
									String dataChild = JSONUtil.getString(obj,
											Constants.JSONKeyName.NAV_OBJ_KEY_DATA, null);
									
//									int navIdNew = JSONUtil.getInt(obj, Constants.JSONKeyName.NAV_OBJ_KEY_NAVID, 0);
									
									NavigationBaseEntity entity = new NavigationBaseEntity();
									entity.setNavId(navId);
									entity.setSelfId(selfId);
									entity.setIcon(iconUrl);
									entity.setCount(count);
									entity.setName(name);
									entity.setGroupId(groupId);
									entity.setMethod(method);
									entity.setIsParent(isParent);
									entity.setParentId(parentId);
									entity.setLevel(level);
									entity.setMethodParams(dataChild);
									entities.add(entity);
									dao.insert(entity);

								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							ArrayList<NavigationBaseEntity> result = new ArrayList<NavigationBaseEntity>();
							result.addAll(dao.find());

							DataCache.NavItemCache.put(navId, entities);

							for (NavigationBaseEntity base : result) {
								//.d("ElectricApp","name" + base.getName());
							}
						}

						
						 /* (non-Javadoc)
						 * 
						 * @see
						 * com.loopj.android.http.JsonHttpResponseHandler#onSuccess
						 * (int, org.json.JSONArray)
						 */

						@Override
						public void onSuccess(int statusCode, JSONArray response) {
							//.d("ElectricApp", "Onsuccess + content = "+ response.toString());

						}
					});
		}
	}
}
