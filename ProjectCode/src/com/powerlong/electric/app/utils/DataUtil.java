/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * DataUtil.java
 * 
 * 2013-8-20-下午01:53:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dao.BannerDao;
import com.powerlong.electric.app.dao.NavigationActivityDao;
import com.powerlong.electric.app.dao.NavigationBaseDao;
import com.powerlong.electric.app.dao.RecommendDao;
import com.powerlong.electric.app.dao.SearchCategoriesDao;
import com.powerlong.electric.app.dao.SearchCategoryDetailDao;
import com.powerlong.electric.app.entity.BannerEntity;
import com.powerlong.electric.app.entity.CartCountEntity;
import com.powerlong.electric.app.entity.ChangeItemEntity;
import com.powerlong.electric.app.entity.NavigationActivityEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.OrderDetailEntity;
import com.powerlong.electric.app.entity.ProfileEntity;
import com.powerlong.electric.app.entity.RecommendEntity;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;

/**
 * 
 * DataUtil:获取数据
 * 
 * @author: Liang Wang 2013-8-20-下午01:53:29
 * 
 * @version 1.0.0
 * 
 */
public class DataUtil {
	/**
	 * 获取导航楼层信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static/* ArrayList<NavigationBaseEntity> */void getNavFloorData(
			Context context, ServerConnectionHandler handler) {
		String navId = Constants.JSONKeyName.NavItemId.FLOOR.getValue();
		ArrayList<NavigationBaseEntity> entity = DataCache.NavItemCache
				.get(navId);
		if (entity != null && entity.size() > 0) {
			LogUtil.d("ElectricApp", "getNavFloorData step 0");
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
			return;
		}
		LogUtil.d("ElectricApp", "getNavFloorData step 1");
		NavigationBaseDao dao = new NavigationBaseDao(context);
		entity = dao.getDataById(navId);
		if (entity == null || entity.size() == 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("navId", navId);
				obj.put("mall", Constants.mallId);
				HttpUtil.getSyncJson(dao, navId,
						Constants.ServerUrl.NAVIGATION_URL, obj.toString(),
						handler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (entity.size() > 0) {
			DataCache.NavItemCache.put(navId, entity);
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
		}
		LogUtil.d("ElectricApp", "getNavFloorData out...");
	}

	/**
	 * 获取导航楼层详情信息
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getNavFloorDetailsData(Context context, String method,
			String methodParams, ServerConnectionHandler handler) {
		DataCache.NavFloorDetailsCache.clear();
		HttpUtil.getFloorDetailsJson(context, method, methodParams, handler);
	}
	
	public static void getNewNavFloorDetailsData(Context context, String method,
			String methodParams, ServerConnectionHandler handler) {
		//DataCache.NavFloorDetailsCache.clear();
		HttpUtil.getFloorDetailsJson(context, method, methodParams, handler);
	}

	/**
	 * 获取导航品牌信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param mallId
	 *            商场编号
	 * @return
	 */
	public static/** ArrayList<NavigationBaseEntity> */void getNavBrandData(
			Context context, ServerConnectionHandler handler) {
		DataCache.BrandListCache.clear();

		HttpUtil.getBrandListJson(Constants.ServerUrl.GET_BRAND_LIST_URL,
				handler);
	}

	/**
	 * 获取店铺详情信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param param
	 *            json参数
	 * @return
	 */
	public static/** ArrayList<NavigationBaseEntity> */void getShopDetails(
			Context context, ServerConnectionHandler handler, String param,boolean isRefresh) {
		if(isRefresh){
			DataCache.ShopDetailsCache.clear();
			DataCache.ShopItemListCache.clear();
		}
		

		HttpUtil.getShopDetailsJson(Constants.ServerUrl.GET_SHOP_DETAILS_URL,
				handler, param);
	}

	/**
	 * 获取商品详情信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param itemId
	 *            商品编号
	 * @return
	 */
	public static/** ArrayList<NavigationBaseEntity> */void getItemDetails(
			Context context, ServerConnectionHandler handler, long itemId) {
		DataCache.ItemsDetailsCache.clear();
		DataCache.ItemsBarginListCache.clear();
		DataCache.ItemsImageListCache.clear();
		DataCache.ItemsPropList1Cache.clear();
		DataCache.ItemsPropList2Cache.clear();
		HttpUtil.getItemDetailsJson(Constants.ServerUrl.GET_ITEM_DETAILS_URL,
				handler, itemId);
	}

	/**
	 * 获取商品评论详情信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param param
	 *            传参
	 * @return
	 */
	public static/** ArrayList<NavigationBaseEntity> */void getItemComments(
			Context context, ServerConnectionHandler handler, String param) {
		DataCache.ItemCommentsListCache.clear();
		HttpUtil.getItemCommentsJson(Constants.ServerUrl.GET_ITEM_COMMENTS_URL,
				handler, param);
	}

	/**
	 * 获取导航品牌详情信息
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getNavBrandDetailsData(Context context, String method,
			String methodParams, ServerConnectionHandler handler) {
		DataCache.NavBrandDetailsCache.clear();
		HttpUtil.getBrandDetailsJson(context, method, methodParams, handler);
	}

	/**
	 * 获取导航活动列表
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param pageIndex 第几页
	 * @return
	 */
	public static/* ArrayList<NavigationActivityEntity> */void getNavActvityData(
			Context context, ServerConnectionHandler handler,int pageIndex) {
		DataCache.NavActivityListCache.clear();
		HttpUtil.getNavActvityJson(Constants.ServerUrl.GET_ACTIVITIES_LIST_URL,
				handler,pageIndex);
	}

	/**
	 * 获取导航活动详细
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param activityId
	 *            活动编号
	 * @return
	 */
	public static/* ArrayList<NavigationActivityEntity> */void getNavActvityDetails(
			Context context, ServerConnectionHandler handler, long activityId) {
		DataCache.NavActivityDetaillsCache.clear();
		HttpUtil.getNavActvityDetailsJson(
				Constants.ServerUrl.GET_ACTIVITIES_DETAIL_URL, handler,
				activityId);
	}

	/**
	 * 获取导航美食信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static/* ArrayList<NavigationBaseEntity> */void getNavFoodsData(
			Context context, ServerConnectionHandler handler) {
		String navId = Constants.JSONKeyName.NavItemId.FOODS.getValue();
		ArrayList<NavigationBaseEntity> entity = DataCache.NavItemCache
				.get(navId);
		if (entity != null && entity.size() > 0) {
			LogUtil.d("ElectricApp", "getNavFoodsData step 0");
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
			return;
		}
		LogUtil.d("ElectricApp", "getNavFoodsData step 1");
		NavigationBaseDao dao = new NavigationBaseDao(context);
		entity = dao.getDataById(navId);
		if (entity == null || entity.size() == 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("navId", navId);
				obj.put("mall", Constants.mallId);
				HttpUtil.getSyncJson(dao, navId,
						Constants.ServerUrl.NAVIGATION_URL, obj.toString(),
						handler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (entity.size() > 0) {
			DataCache.NavItemCache.put(navId, entity);
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
		}
	}

	/**
	 * 获取导航娱乐信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static/* ArrayList<NavigationBaseEntity> */void getNavEnterData(
			Context context, ServerConnectionHandler handler) {
		String navId = Constants.JSONKeyName.NavItemId.ENTERTAINMENT.getValue();
		ArrayList<NavigationBaseEntity> entity = DataCache.NavItemCache
				.get(navId);
		if (entity != null && entity.size() > 0) {
			LogUtil.d("ElectricApp", "getNavEnterData step 0");
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
			return;
		}
		LogUtil.d("ElectricApp", "getNavEnterData step 1");
		NavigationBaseDao dao = new NavigationBaseDao(context);
		entity = dao.getDataById(navId);
		if (entity == null || entity.size() == 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("navId", navId);
				obj.put("mall", Constants.mallId);
				HttpUtil.getSyncJson(dao, navId,
						Constants.ServerUrl.NAVIGATION_URL, obj.toString(),
						handler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (entity.size() > 0) {
			DataCache.NavItemCache.put(navId, entity);
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
		}
	}

	/**
	 * 获取导航购物信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static/* ArrayList<NavigationBaseEntity> */void getNavShoppingData(
			Context context, ServerConnectionHandler handler) {
		String navId = Constants.JSONKeyName.NavItemId.SHOPPING.getValue();
		ArrayList<NavigationBaseEntity> entity = DataCache.NavItemCache
				.get(navId);
		if (entity != null && entity.size() > 0) {
			LogUtil.d("ElectricApp", "getNavShoppingData step 0");
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
			return;
		}
		LogUtil.d("ElectricApp", "getNavShoppingData step 1");
		NavigationBaseDao dao = new NavigationBaseDao(context);
		entity = dao.getDataById(navId);
		if (entity == null || entity.size() == 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("navId", navId);
				obj.put("mall", Constants.mallId);
				HttpUtil.getSyncJson(dao, navId,
						Constants.ServerUrl.NAVIGATION_URL, obj.toString(),
						handler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (entity.size() > 0) {
			DataCache.NavItemCache.put(navId, entity);
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
		}
	}

	/**
	 * 获取导航购物单项详细信息
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static/* ArrayList<NavigationBaseEntity> */void getNavShoppingItemData(
			Context context, String method, String methodParams,
			ServerConnectionHandler handler) {
		DataCache.NavFloorDetailsCache.clear();
		HttpUtil.getNavShoppingItemJson(context, method, methodParams, handler);

	}

	/**
	 * 获取导航团购列表信息
	 */
	public static void getGrouponListData(Context context,
			ServerConnectionHandler handler, String param) {
		DataCache.NavGrouponListCache.clear();
		HttpUtil.getGroupListJson(Constants.ServerUrl.GET_GROUPON_LIST_URL,
				param, handler);
	}

	/**
	 * 获取导航团购信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static/* ArrayList<NavigationBaseEntity> */void getNavGrouponData(
			Context context, ServerConnectionHandler handler) {
		String navId = Constants.JSONKeyName.NavItemId.GROUPON.getValue();
		ArrayList<NavigationBaseEntity> entity = DataCache.NavItemCache
				.get(navId);
		if (entity != null && entity.size() > 0) {
			LogUtil.d("ElectricApp", "getNavGrouponData step 0");
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
			return;
		}
		LogUtil.d("ElectricApp", "getNavGrouponData step 1");
		NavigationBaseDao dao = new NavigationBaseDao(context);
		entity = dao.getDataById(navId);
		if (entity == null || entity.size() == 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("nav_id", navId);
				obj.put("mall", Constants.mallId);
				HttpUtil.getSyncJson(dao, navId,
						Constants.ServerUrl.NAVIGATION_URL, obj.toString(),
						handler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (entity.size() > 0) {
			DataCache.NavItemCache.put(navId, entity);
			handler.obtainMessage(Constants.HttpStatus.SUCCESS,
					StringUtil.toInt(navId), -1, entity).sendToTarget();
		}
	}

	/**
	 * 获取团购详情信息
	 * 
	 * @param context
	 * @param groupId
	 *            团购编号
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getGrouponDetailsData(Context context, long groupId,ServerConnectionHandler handler) {
		DataCache.GrouponDetailCache.clear();
		DataCache.GrouponShopCache.clear();
		DataCache.GrouponShopImgCache.clear();
		DataCache.itemGrouponItemListCache.clear();
		DataCache.GrouponItemListCache.clear();
		HttpUtil.getGrouponDetailsJson(context, groupId, handler);
	}
	
	/**
	 * 获取导航团购详情信息
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getNavGrouponDetailsData(Context context, String method,
			String methodParams, ServerConnectionHandler handler) {
		DataCache.NavGrouponDetailsCache.clear();
		HttpUtil.getBGrouponDetailsJson(context, method, methodParams, handler);
	}

	/**
	 * 获取搜索界面大类列表信息
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param mallId
	 *            商场编号
	 * @return
	 */
	public static void getSearchCategoriesData(Context context,
			ServerConnectionHandler handler,int mallId) {
		LogUtil.d("ElectricApp", "getSearchCategoriesData");
		ArrayList<SearchCategoryEntity> entity = DataCache.SearchCategoriesCache;

		if (entity != null && entity.size() > 0) {
			LogUtil.d("ElectricApp", "getSearchCategoriesData step 0");
			handler.obtainMessage(Constants.HttpStatus.SUCCESS, entity)
					.sendToTarget();
			return;
		}

		SearchCategoriesDao dao = new SearchCategoriesDao(context);
		entity = dao.getAll();
		dao.creatDetaildDao(context);
		SearchCategoryDetailDao detailDao = dao.getDetailDao();
		ArrayList<SearchCategoryDetailEntity> detailEntity = detailDao.getAll();
		if ((entity == null || entity.size() == 0)
				|| (detailEntity == null || detailEntity.size() == 0)) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				HttpUtil.getSearchCategroyJson(dao,
						Constants.ServerUrl.GET_SEARCH_CATEGORIES_URL,
						obj.toString(), handler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (entity.size() > 0) {
			DataCache.SearchCategoriesCache.addAll(entity);
			for (SearchCategoryEntity child : entity) {
				long pid = child.getSelfId();
				LogUtil.d("ElectricApp", "SearchCategoryEntity pid = "+pid);
//				DataCache.SearchCategoryDetailCache.put(pid,
//						detailDao.getAllByParentId(pid));
			}
			handler.obtainMessage(Constants.HttpStatus.SUCCESS, entity)
					.sendToTarget();
		}
	}

	/**
	 * 根据关键字在不同的种类中搜索结果
	 * 
	 * @param filter
	 *            关键字
	 * @param filterType
	 *            要搜索的数据类型
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getMatchedObjByFilter(String filter, int filterType,
			ServerConnectionHandler handler) {
		DataCache.NavGrouponListCache.clear();
		DataCache.SearchShopResultCache.clear();
		HttpUtil.getHotKeywordsJson(
				Constants.ServerUrl.GET_MATCHED_OBJ_BY_KEYWORDS_URL, filter,
				filterType, handler);
	}
	
	/**
	 * 根据关键字在不同的种类中搜索结果
	 * 
	 * @param param
	 * 			  请求参数
	 * @param filterType
	 *            要搜索的数据类型
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getMatchedObjByParams(String params, int filterType,
			ServerConnectionHandler handler) {
		DataCache.NavGrouponListCache.clear();
		DataCache.SearchShopResultCache.clear();
		HttpUtil.getHotKeywordsParamsJson(
				Constants.ServerUrl.GET_MATCHED_OBJ_BY_KEYWORDS_URL, params,filterType,
				handler);
	}

	/**
	 * 获取购物车列表信息
	 * 
	 * @param handler
	 *            回调实例
	 * @param param 
	 *           参数		{"mall":1,"TGC":" 32AC7F87DCD544948BA991EF40957BCC"}
	 * @return
	 */
	public static void getCartListData(ServerConnectionHandler handler) {
		DataCache.totalPrice = -1;
		DataCache.CartShopListCache.clear();
		DataCache.CartItemListCache.clear();
		DataCache.CartBagainListCache.clear();
		HttpUtil.getCartListJson(handler);
	}

	/**
	 * 获取购物车结算信息
	 * 
	 * @param handler
	 *            回调实例
	 *  @param param 
	 *           参数{"mall":1,"TGC":"9B883D4AFB314458BB9E6447BF39F71D","shopList":{"id":"77","itemList":{"id":"205","type":"0","buyNum":"1"}}}
	 * @return
	 */
	public static void getCartCountData(ServerConnectionHandler handler,String param) {
		DataCache.CartCountEntityCache.clear();
		DataCache.CartCountBagainListCache.clear();
		DataCache.CartCountShopListCache.clear();
		DataCache.ShopPresentListCache.clear();
		DataCache.CashCouponListCache.clear();
		DataCache.LogisticListCache.clear();
		DataCache.payListCache.clear();
		DataCache.itemBargainListCache.clear();
		DataCache.dateListCache.clear();
		DataCache.timeListCache.clear();

		HttpUtil.getCartCountJson(handler,param);
	}

	/**
	 * 获取宝龙抵用券信息
	 * 
	 * @param handler
	 *            回调实例
	 * @param param 
	 * 			     参数{"mall":4,"TGC":"F5ED2F5FE9884DACAE44A8F802E224B2","price":"500"}
	 * @return
	 */
	public static void getPlCashCouponListData(ServerConnectionHandler handler,String param) {
		DataCache.PLCashCouponListCache.clear();

		HttpUtil.getPlCashCouponListJson(handler,param);
	}

	/**
	 * 获取用户收货地址列表信息
	 * 
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getUserAddressListData(ServerConnectionHandler handler) {
		DataCache.UserAddressListCache.clear();

		HttpUtil.getUserAddressListJson(handler);
	}
	
	/**
	 * 获取用户某个收货地址信息
	 * 
	 * @param handler
	 *            回调实例
	 * @return
	 */
	public static void getUserAddressData(ServerConnectionHandler handler,long addressId) {
		DataCache.UserAddressListCache.clear();
		HttpUtil.getUserAddressJson(handler,addressId);
	}

	/**
	 * 删除用户收货地址
	 * 
	 * @param handler
	 *            回调实例
	 * @param addressId
	 *            要删除的地址编号
	 * @return
	 */
	public static void deleteUserAddressDataById(
			ServerConnectionHandler handler, String addressId) {
		HttpUtil.delUserAddressById(handler, addressId);
	}

	/**
	 * 设置用户默认收货地址
	 * 
	 * @param handler
	 *            回调实例
	 * @param addressId
	 *            要删除的地址编号
	 * @return
	 */
	public static void setUserDefaultAddress(ServerConnectionHandler handler,
			String addressId) {
		HttpUtil.setUserDefaultAddress(handler, addressId);
	}

	/**
	 * 修改/添加用户收货地址
	 * 
	 * @param handler
	 *            回调实例
	 * @param param
	 *            地址信息参数
	 * @return
	 */
	public static void addOrModifyUserAddress(ServerConnectionHandler handler,
			String param) {
		HttpUtil.AddUserAddress(handler, param);
	}

	/**
	 * 加减购物车商品
	 * 
	 * @param handler
	 *            回调实例
	 * @param addEntity
	 *            商品变化实体
	 * @return
	 */
	public static void changeItemNum(ServerConnectionHandler handler,
			ChangeItemEntity addEntity) {
		HttpUtil.ChangeItemNum(handler, addEntity);
	}

	/**
	 * 发送收藏夹操作消息
	 * 
	 * @param handler
	 *            回调实例
	 * @param param
	 *            参数
	 * @param operType
	 *            操作种类 ：0：添加；1：删除
	 * @return
	 */
	public static void sendUserFavourOperation(ServerConnectionHandler handler,
			String param, int operType) {
		HttpUtil.requestAddOrDeleteItemToFavour(handler, param, operType);
	}
	
	/**
	 * 获取我的账户抵用券列表
	 * @param pageIndex 第几页
	 */
	public static void getBargainListDta(Context context,ServerConnectionHandler handler
			,int pageIndex, String keyword, int type) {
		DataCache.MyBargainListCache.clear();
		for(int i = 1;i < 8;i++){
		HttpUtil.getMyBargainListData(context,handler,i, keyword, type);
		}
	}
	
	
	/**
	 * 获取数据缓存版本号
	 */
	public static void checkDataVersionCode(Context context,ServerConnectionHandler handler,String param) {
		DataCache.MyBargainListCache.clear();
		HttpUtil.getDataVersionCode(context,handler,param);
	}
	


	/**
	 * 判断缓存内是否存在用户信息
	 */
	public static boolean isUserDataExisted(Context context) {
		
		String tgc = null;
		SharedPreferences pref = context.getSharedPreferences("profile",
				Context.MODE_PRIVATE);

		if (DataCache.UserDataCache == null
				|| DataCache.UserDataCache.size() == 0) {
			if (pref == null)
				return false;
			tgc = pref.getString(
					Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_TGC, null);
			if (StringUtil.isEmpty(tgc)) {
				return false;
			} else {
				String nickname = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NICKNAME,
						null);
				String username = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_USERNAME,
						null);
				String email = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_EMAIL, null);
				String mobile = pref
						.getString(
								Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_MOBILE,
								null);
				String birthday = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_BIRTHDAY,
						null);
				String lastlogin = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_LASTLOGIN,
						null);
				String sex = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_SEX, null);

				ProfileEntity entity = new ProfileEntity();

				entity.setBirthday(birthday);
				entity.setEmail(email);
				entity.setLastlogin(lastlogin);
				entity.setMobile(mobile);
				entity.setNickname(nickname);
				entity.setSex(sex);
				entity.setTGC(tgc);
				entity.setUsername(username);
				DataCache.UserDataCache.clear();
				DataCache.UserDataCache.add(entity);
			}
		} else {
			tgc = DataCache.UserDataCache.get(0).getTGC();
			if (StringUtil.isEmpty(tgc)) {
				tgc = pref.getString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_TGC, null);
				if (StringUtil.isEmpty(tgc)) {
					return false;
				} else {
					String nickname = pref.getString(
							Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NICKNAME,
							null);
					String username = pref.getString(
							Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_USERNAME,
							null);
					String email = pref.getString(
							Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_EMAIL, null);
					String mobile = pref
							.getString(
									Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_MOBILE,
									null);
					String birthday = pref.getString(
							Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_BIRTHDAY,
							null);
					String lastlogin = pref.getString(
							Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_LASTLOGIN,
							null);
					String sex = pref.getString(
							Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_SEX, null);

					ProfileEntity entity = new ProfileEntity();

					entity.setBirthday(birthday);
					entity.setEmail(email);
					entity.setLastlogin(lastlogin);
					entity.setMobile(mobile);
					entity.setNickname(nickname);
					entity.setSex(sex);
					entity.setTGC(tgc);
					entity.setUsername(username);
					DataCache.UserDataCache.clear();
					DataCache.UserDataCache.add(entity);
				}
			}
		}
		return true;
	}

	/**
	 * 清除用户登录信息
	 * @param settingsActivity 
	 * void
	 * @exception 
	 * @since  1.0.0
	*/
	public static void clearUserData(Context context) {
		DataCache.UserDataCache.clear();
		SharedPreferences pref = context.getSharedPreferences("profile",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 获取我的订单列表
	 * @param context 
	 * @param handler 回调实例
	 * @param keyWords
	 *        关键字
	 * @param pageIndex
	 *        第几页
	 * @exception 
	 * @since  1.0.0
	*/
	public static void getMyOrderList(Context context,ServerConnectionHandler handler,String keyWords,int pageIndex) {
		DataCache.UserOrderListCache.clear();
		DataCache.UserOrderItemListCache.clear();
		HttpUtil.getMyOrderListJson(context,handler,keyWords,pageIndex);
	}
	
	/**
	 * 获取订单详情
	 * @param context 
	 * @param handler 回调实例
	 * @param orderId
	 *        订单号
	 * @exception 
	 * @since  1.0.0
	*/
	public static void getMyOrderLDetails(Context context,ServerConnectionHandler handler,long orderId, int stat) {
		/*ArrayList<OrderDetailEntity> orderDetailEntities = DataCache.UserOrderDetailCache.get(orderId);
		if(orderDetailEntities !=null &&orderDetailEntities.size()>0){
			handler.obtainMessage(
					Constants.HttpStatus.SUCCESS, "订单详情查询成功.")
					.sendToTarget();
		}*/
		DataCache.UserOrderDetailCache.clear();
		DataCache.UserOrderDetailBagainListCache.clear();
		DataCache.UserOrderDetailCashCouponListCache.clear();
		DataCache.UserOrderDetailItemListCache.clear();
		DataCache.UserOrderDetailPlCashCouponListCache.clear();
		DataCache.UserOrderDetailShopListCache.clear();
		
		HttpUtil.getMyOrderDetailJson(context,handler,orderId, stat);
	}
	
	/**
	 * 获取我的商品收藏列表
	 * @param context 
	 * @param handler 回调实例
	 * @param pageIndex
	 *        第几页
	 * @exception 
	 * @since  1.0.0
	*/
	public static void getMyItemFavourList(Context context,ServerConnectionHandler handler,int pageIndex) {
		DataCache.ItemFavourListCache.clear();
		HttpUtil.getItemFavourListJson(context, handler, pageIndex);
	}
	
	/**
	 * 获取我的店铺收藏列表
	 * @param context 
	 * @param handler 回调实例
	 * @param pageIndex
	 *        第几页
	 * @exception 
	 * @since  1.0.0
	*/
	public static void getMyShopFavourList(Context context,ServerConnectionHandler handler,int pageIndex) {
		DataCache.ShopFavourListCache.clear();
		HttpUtil.getShopFavourListJson(context, handler, pageIndex);
	}
	
	/**
	 * 
	 * 获取首页相关数据
	 * @param context
	 * @param handler
	 * @param param 查询参数
	 * @param isRefresh 是否刷新数据  
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void queryAdvertisementData(Context context,ServerConnectionHandler handler,String param,boolean isRefresh){
		if(isRefresh){
			HttpUtil.queryAdvertisementJson(context,handler,param);
			return;
		}
		
		if(DataCache.RecommendCache.size()!=0&&DataCache.BannerCache.size()!=0){
			handler.obtainMessage(Constants.HttpStatus.SUCCESS).sendToTarget();
			return;
		}
		
		BannerDao bannerDao = new BannerDao(context);
		ArrayList<BannerEntity> bannerEntity = bannerDao.getAll();
		RecommendDao recommendDao = new RecommendDao(context);
		ArrayList<RecommendEntity> recommendEntity = recommendDao.getAll();
		if ((bannerEntity == null || bannerEntity.size() == 0)
				|| (recommendEntity == null || recommendEntity.size() == 0)) {
			
		}else{
			DataCache.RecommendCache.clear();
			DataCache.RecommendCache.addAll(recommendEntity);
			DataCache.BannerCache.clear();
			DataCache.BannerCache.addAll(bannerEntity);
			handler.obtainMessage(Constants.HttpStatus.SUCCESS)
					.sendToTarget();
			return;
		}
		
		SharedPreferences pref = context.getSharedPreferences("homepage",
				Context.MODE_PRIVATE);
		String json = pref.getString("homepage", "");
		if(JSONParser.parseAdvertisementJson(context,json)){
			handler.obtainMessage(Constants.HttpStatus.SUCCESS).sendToTarget();
			return;
		}
		
		
		HttpUtil.queryAdvertisementJson(context,handler,param);
	}
	
	/**
	 *  查询我的消息列表
	 * @param context
	 * @param handler
	 * @param param 传参
	 */
	public static void queryMyMessageListData(Context context,ServerConnectionHandler handler,String param){
		DataCache.MyMsgListCache.clear();
		HttpUtil.queryMyMessageListJson(context,handler,param);
	}
	
	/**
	 *  查询消息详情
	 * @param context
	 * @param handler
	 * @param param 传参
	 */
	public static void queryMyMessageDetail(Context context,ServerConnectionHandler handler,String param){
		DataCache.MyMsgDetailCache.clear();
		HttpUtil.queryMyMessageDetail(context,handler,param);
	}
	
	/**
	 *  查询我的通知列表
	 * @param context
	 * @param handler
	 * @param param 传参
	 */
	public static void queryMyNotifyListData(Context context,ServerConnectionHandler handler,String param){
		DataCache.MyNotifyListCache.clear();
		HttpUtil.queryMyNotifyListJson(context,handler,param);
	}
	
	/**
	 *  查询我的团购券
	 * @param context
	 * @param handler
	 * @param param 传参
	 */
	public static void queryGrouponCouponListData(Context context,ServerConnectionHandler handler,String param){
		DataCache.MyNotifyListCache.clear();
		DataCache.MyGrouponCouponListCache.clear();
		HttpUtil.queryGrouponCouponListJson(context,handler,param);
	}
	
	/**
	 *  附近店铺查询信息
	 * @param context
	 * @param handler
	 * @param param 传参
	 */
	public static void queryNearbyListData(Context context,ServerConnectionHandler handler,int mall, String mac, String skip, String size){
		DataCache.NearbyShopCache.clear();
		HttpUtil.queryNearbyShopJson(context,handler,mall, mac, skip, size);
	}
	
	public static void queryMallListData(Context context,ServerConnectionHandler handler){
		DataCache.mallList.clear();
		HttpUtil.getMallList(context, handler);
	}
}
