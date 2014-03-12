/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * JSONParser.java
 * 
 * 2013-8-20-下午08:33:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.util.ArrayList;

import org.json.JSONArray;
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
import com.powerlong.electric.app.domain.DomainShopDetailCategory;
import com.powerlong.electric.app.entity.ActivityDetailEntity;
import com.powerlong.electric.app.entity.ActivityItemEntity;
import com.powerlong.electric.app.entity.AddOrderEntity;
import com.powerlong.electric.app.entity.AddressQueryEntity;
import com.powerlong.electric.app.entity.BannerEntity;
import com.powerlong.electric.app.entity.BrandListEntity;
import com.powerlong.electric.app.entity.BarginListEntity;
import com.powerlong.electric.app.entity.CartCountEntity;
import com.powerlong.electric.app.entity.ChatMsgEntity;
import com.powerlong.electric.app.entity.ChatMsgListEntity;
import com.powerlong.electric.app.entity.DateTimeEntity;
import com.powerlong.electric.app.entity.GrouponCouponEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ItemFavourListEntity;
import com.powerlong.electric.app.entity.ItemGroupItemListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.CashCouponEntity;
import com.powerlong.electric.app.entity.GrouponDetailShopEntity;
import com.powerlong.electric.app.entity.GrouponDetailsEntity;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.entity.ImageListEntity;
import com.powerlong.electric.app.entity.ItemCommentEntity;
import com.powerlong.electric.app.entity.ItemDetailEntity;
import com.powerlong.electric.app.entity.ItemGroupListEntity;
import com.powerlong.electric.app.entity.LogisticsEntity;
import com.powerlong.electric.app.entity.NavigationActivityEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.NavigationBrandDetailsEntity;
import com.powerlong.electric.app.entity.NavigationFloorDetailsEntity;
import com.powerlong.electric.app.entity.NavigationGrouponDetailsEntity;
import com.powerlong.electric.app.entity.NearbySearchEntity;
import com.powerlong.electric.app.entity.NearbyShopEntity;
import com.powerlong.electric.app.entity.NotifyListEntity;
import com.powerlong.electric.app.entity.OrderDetailEntity;
import com.powerlong.electric.app.entity.OrderListEntity;
import com.powerlong.electric.app.entity.PayEntity;
import com.powerlong.electric.app.entity.PlCashCouponEntity;
import com.powerlong.electric.app.entity.PresentEntity;
import com.powerlong.electric.app.entity.ProfileEntity;
import com.powerlong.electric.app.entity.PropEntity;
import com.powerlong.electric.app.entity.RecommendEntity;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.powerlong.electric.app.entity.SearchResultEntity;
import com.powerlong.electric.app.entity.ShopDetailsEntity;
import com.powerlong.electric.app.entity.ShopFavourListEntity;
import com.powerlong.electric.app.entity.ShopItemListEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.ui.CashCouponActivity;
import com.powerlong.electric.app.ui.LoginActivity;
import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * JSONParser
 * 
 * @author: Liang Wang 2013-8-20-下午08:33:08
 * 
 * @version 1.0.0
 * 
 */
public class JSONParser {
	/**
	 * 解析活动列表JSON
	 * 
	 * @param dao
	 *            数据库实例
	 * @param jsonString
	 *            待解析的json字符串 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseActivityListJson(String jsonString) {
		JSONObject original = null;
		try {
			original = new JSONObject(jsonString);
			JSONObject listData = JSONUtil.getJSONObject(original,
					Constants.JSONKeyName.ACTIVITY_LIST_JSON_TOPEST_DATA, null);
			if (listData == null)
				return false;
			JSONArray listArray = JSONUtil.getJSONArray(listData,
					Constants.JSONKeyName.ACTIVITY_LIST_ARRAY_KEY_NAME, null);
			if (listArray != null) {
				for (int i = 0; i < listArray.length(); i++) {
					JSONObject child = listArray.getJSONObject(i);
					ActivityItemEntity entity = new ActivityItemEntity();
					long id = JSONUtil.getLong(child,
							Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_ID, 0);
					String name = JSONUtil.getString(child,
							Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_NAME,
							null);
					String image = JSONUtil.getString(child,
							Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_IMAGE,
							null);
					String classification = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_CLASSFICATION,
									null);
					String duration = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_DURATION,
									null);
					String address = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_ADDRESS,
									null);
					int isPlazaActivity = JSONUtil.getInt(child,
							Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_ID, 0);
					entity.setAddress(address);
					entity.setClassification(classification);
					entity.setId(id);
					entity.setDuration(duration);
					entity.setImage(image);
					entity.setIsPlazaActivity(isPlazaActivity);
					entity.setName(name);

					DataCache.NavActivityListCache.add(entity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 解析活动详情
	 * 
	 * @param dao
	 *            数据库实例
	 * @param jsonString
	 *            待解析的json字符串 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseActivityDetailJson(String jsonString) {
		JSONObject original = null;
		try {
			original = new JSONObject(jsonString);
			JSONObject listData = JSONUtil.getJSONObject(original,
					Constants.JSONKeyName.ACTIVITY_LIST_JSON_TOPEST_DATA, null);
			if (listData == null)
				return false;

			if (listData != null) {
				ActivityDetailEntity entity = new ActivityDetailEntity();
				long id = JSONUtil.getLong(listData,
						Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_ID, 0);
				String name = JSONUtil.getString(listData,
						Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_NAME, null);
				String image = JSONUtil
						.getString(
								listData,
								Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_IMAGE,
								null);
				/*
				 * String classification = JSONUtil.getString(listData,
				 * Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_CLASSFICATION,
				 * null);
				 */
				/*
				 * String duration = JSONUtil.getString(listData,
				 * Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_DURATION, null);
				 */
				/*
				 * String address = JSONUtil.getString(listData,
				 * Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_ADDRESS, null);
				 */
				String isPlazaActivity = JSONUtil.getString(listData,
						Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_ID, null);
				/*
				 * int rule = JSONUtil.getInt(listData,
				 * Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_RULES, 0); int
				 * tips = JSONUtil.getInt(listData,
				 * Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_TIPS, 0);
				 */
				String introduction = JSONUtil
						.getString(
								listData,
								Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_INTRODUCTION,
								null);
				// entity.setAddress(address);
				// entity.setClassification(classification);
				entity.setId(id);
				// entity.setDuration(duration);
				entity.setImage(image);
				entity.setIsPlazaActivity(isPlazaActivity);
				entity.setName(name);
				entity.setIntroduction(introduction);
				// entity.setRule(rule);
				// entity.setTips(tips);

				LogUtil.d("jsonparser", "parseActivityDetailJson id =" + id);
				DataCache.NavActivityDetaillsCache.put(id, entity);

				LogUtil.d("jsonparser",
						"parseActivityDetailJson introduction ="
								+ DataCache.NavActivityDetaillsCache.get(id)
										.getIntroduction());
				/*
				 * String[] imageList = JSONUtil .getStringArray( listData,
				 * Constants.JSONKeyName.ACTIVITY_LIST_OBJ_KEY_IMAGELIST, null);
				 * ArrayList<ImageListEntity> entities = new
				 * ArrayList<ImageListEntity>(); for (String imgPath :
				 * imageList) { ImageListEntity imageEntity = new
				 * ImageListEntity(); imageEntity.setImageName(imgPath);
				 * entities.add(imageEntity); }
				 */
				// DataCache.NavActivityImageListCache.put(id, entities);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 解析导航栏各个ITEM
	 * 
	 * @param dao
	 *            数据库实例
	 * @param navId
	 *            导航栏分类标识
	 * @param jsonString
	 *            待解析的json字符串 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseNavItemJson(NavigationBaseDao dao, String navId,
			String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray array = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA_KEY_NAME, null);
		if (array != null) {
			ArrayList<NavigationBaseEntity> entities = new ArrayList<NavigationBaseEntity>();
			for (int index = 0; index < array.length(); index++) {
				try {
					JSONObject obj = array.getJSONObject(index);
//					int selfId = JSONUtil.getInt(obj,
//							Constants.JSONKeyName.NAV_OBJ_KEY_SELFID, 0);
//					int navIdNew = JSONUtil.getInt(obj, Constants.JSONKeyName.NAV_OBJ_KEY_NAVID, 0);
					String iconUrl = JSONUtil.getString(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_ICON, null);
					int count = JSONUtil.getInt(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_COUNT, 0);
					String name = JSONUtil.getString(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_NAME, null);
					int groupId = JSONUtil.getInt(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_GROUPID, 0);
					String method = JSONUtil.getString(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_METHOD, null);
					int isParent = JSONUtil.getInt(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_ISPARENT, 0);
					int parentId = JSONUtil.getInt(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_PARENTID, 0);
					int level = JSONUtil.getInt(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_LEVEL, 0);
					String dataChild = JSONUtil.getString(obj,
							Constants.JSONKeyName.NAV_OBJ_KEY_DATA, null);
					
					LogUtil.d("ElectricApp",
							"parseNavItemJson + navIdNew = "
									+ navId);
					
					NavigationBaseEntity entity = new NavigationBaseEntity();
					entity.setNavId(navId);
//					entity.setSelfId(selfId);
					entity.setIcon(iconUrl);
					entity.setCount(count);
					entity.setName(name);
					entity.setGroupId(groupId);
					entity.setMethod(method);
					entity.setIsParent(isParent);
					entity.setParentId(parentId);
					entity.setLevel(level);
					entity.setMethodParams(dataChild);
					dao.insert(entity);
					entities.add(entity);

				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
			DataCache.NavItemCache.put(navId, entities);
		}
		return true;
	}

	/**
	 * 获取楼层店铺详情 (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param result
	 *            待解析的json字符串
	 * @return void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseFloorDetailJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.FLOOR_DETAIL_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray array = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.FLOOR_DETAIL_ARRAY_KEY_NAME, null);
		if (array != null) {
			for (int index = 0; index < array.length(); index++) {
				NavigationFloorDetailsEntity entity = new NavigationFloorDetailsEntity();
				try {
					JSONObject child = array.getJSONObject(index);
					int selfId = JSONUtil.getInt(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SELFID,
							0);
					String level = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_LEVEL,
							null);
					String itemList = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMLIST,
									null);
					String itemId = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMID,
							null);
					int favourNum = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_FAVOURNUM,
									0);
					String shopName = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPNAME,
									null);
					String shopClassficId = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPCLASSID,
									null);
					String shopClass = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPCLASS,
									null);
					String businessRange = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_BUSSNINESSRANGE,
									null);
					String mallId = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_MAILLID,
							null);
					String floorNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_FLOOR_NUM,
									null);
					String brief = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_BRIEF,
							null);
					String logo = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_LOGO,
							null);
					String recommendItemNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_RECOMMENDITEMNUM,
									null);
					String newItemMonthlyNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_NEWITEMMONTHLYNUM,
									null);
					String hotItemNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_HOTITEMNUM,
									null);
					String userId = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_USERID,
							null);
					String shopUserName = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPUSERNAME,
									null);
					String classification = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_CLASS,
							null);
					int fScore = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_EVALUATION,
									0);
					String newItemNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_NEWITEMNUM,
									null);
					String itemListReCommend = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMLISTRECOMMEND,
									null);
					String itemListHot = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMLISTHOT,
									null);

					entity.setSelfId(selfId);
					entity.setLevel(level);
					entity.setItemList(itemList);
					entity.setItemId(itemId);
					entity.setFavourNum(favourNum);
					entity.setShopName(shopName);
					entity.setShopClassficId(shopClassficId);
					entity.setShopClass(shopClass);
					entity.setBusinessRange(businessRange);
					entity.setMallId(mallId);
					entity.setFloorNum(floorNum);
					entity.setBrief(brief);
					entity.setLogo(logo);
					entity.setRecommendItemNum(recommendItemNum);
					entity.setNewItemMonthlyNum(newItemMonthlyNum);
					entity.setHotItemNum(hotItemNum);
					entity.setUserId(userId);
					entity.setShopUserName(shopUserName);
					entity.setClassification(classification);
					entity.setfScore(fScore);
					entity.setNewItemNum(newItemNum);
					entity.setItemListReCommend(itemListReCommend);
					entity.setItemListHot(itemListHot);

					DataCache.NavFloorDetailsCache.add(entity);

				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 解析搜索分类JSON
	 * 
	 * @param dao
	 *            数据库实例
	 * @param result
	 *            待解析的json字符串
	 * @return void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseSearchCategoriesJson(SearchCategoriesDao dao,
			String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.SEARCH_CATEGORY_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray array = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.SEARCH_CATEGORY_ARRAY_KEY_NAME, null);
		if (array != null) {
			for (int index = 0; index < array.length(); index++) {
				SearchCategoryEntity entity = new SearchCategoryEntity();
				try {
					JSONObject child = array.getJSONObject(index);
					long selfId = JSONUtil
							.getLong(
									child,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_SELFID,
									0);
					int level = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_LEVEL,
									0);
					int mallId = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_MAILLID,
									0);
					int appItemCategoryId = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_APPITEMCATEGORYID,
									0);
					String name = JSONUtil.getString(child,
							Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_NAME,
							null);
					String logo = JSONUtil.getString(child,
							Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_LOGO,
							null);
					int pid = JSONUtil.getInt(child,
							Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_PID,
							0);
					String description = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_DESCRIPTION,
									null);
					String lowerCategoryList = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_ARRAY_KEY_NAME,
									null);

					entity.setSelfId(selfId);
					entity.setLevel(level);
					entity.setLogo(logo);
					entity.setMallId(mallId);
					entity.setAppItemCategoryId(appItemCategoryId);
					entity.setName(name);
					entity.setPid(pid);
					entity.setDescription(description);
					entity.setLowerCategoryList(lowerCategoryList);

					dao.insert(entity);
					DataCache.SearchCategoriesCache.add(entity);

				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 解析关键字搜索JSON
	 * 
	 * @param jsonString
	 *            待解析的json字符串
	 * @param filterTyppe
	 *            关键字类型
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseSearchCategoriesJson(String jsonString,
			int filterTyppe) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.KEYWORDS_SEARCH_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		if (filterTyppe == Constants.KEYWORDS_TYPE_HOTWORDS) {
			String[] hotKeyList = JSONUtil
					.getStringArray(
							data,
							Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_HOTWORDS_LIST,
							null);
			if (hotKeyList != null) {
				for (String hotKey : hotKeyList) {
					LogUtil.d("JSONParser",
							"parseSearchCategoriesJson + hotKey=" + hotKey);
					SearchResultEntity entity = new SearchResultEntity(hotKey,
							Constants.FilterType.GOODS);
					DataCache.SearchResultEntity.add(entity);
				}
			}
		} else if (filterTyppe == Constants.KEYWORDS_TYPE_SHOP) {
			JSONArray array = JSONUtil.getJSONArray(data,
					Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_SHOPS_LIST,
					null);
			if (array == null)
				return false;
			for (int index = 0; index < array.length(); index++) {
				NavigationFloorDetailsEntity entity = new NavigationFloorDetailsEntity();
				JSONObject child;
				try {
					child = array.getJSONObject(index);
					int selfId = JSONUtil.getInt(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SELFID,
							0);
					String level = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_LEVEL,
							null);
					String itemList = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMLIST,
									null);
					String itemId = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMID,
							null);
					int favourNum = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_FAVOURNUM,
									0);
					String shopName = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPNAME,
									null);
					String shopClassficId = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPCLASSID,
									null);
					String shopClass = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPCLASS,
									null);
					String businessRange = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_BUSSNINESSRANGE,
									null);
					String mallId = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_MAILLID,
							null);
					String floorNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_FLOOR_NUM,
									null);
					String brief = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_BRIEF,
							null);
					String logo = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_LOGO,
							null);
					String recommendItemNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_RECOMMENDITEMNUM,
									null);
					String newItemMonthlyNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_NEWITEMMONTHLYNUM,
									null);
					String hotItemNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_HOTITEMNUM,
									null);
					String userId = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_USERID,
							null);
					String shopUserName = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_SHOPUSERNAME,
									null);
					String classification = JSONUtil.getString(child,
							Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_CLASS,
							null);
					int fScore = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_EVALUATION,
									0);
					String newItemNum = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_NEWITEMNUM,
									null);
					String itemListReCommend = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMLISTRECOMMEND,
									null);
					String itemListHot = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.FLOOR_DETAIL_OBJ_KEY_ITEMLISTHOT,
									null);

					entity.setSelfId(selfId);
					entity.setLevel(level);
					entity.setItemList(itemList);
					entity.setItemId(itemId);
					entity.setFavourNum(favourNum);
					entity.setShopName(shopName);
					entity.setShopClassficId(shopClassficId);
					entity.setShopClass(shopClass);
					entity.setBusinessRange(businessRange);
					entity.setMallId(mallId);
					entity.setFloorNum(floorNum);
					entity.setBrief(brief);
					entity.setLogo(logo);
					entity.setRecommendItemNum(recommendItemNum);
					entity.setNewItemMonthlyNum(newItemMonthlyNum);
					entity.setHotItemNum(hotItemNum);
					entity.setUserId(userId);
					entity.setShopUserName(shopUserName);
					entity.setClassification(classification);
					entity.setfScore(fScore);
					entity.setNewItemNum(newItemNum);
					entity.setItemListReCommend(itemListReCommend);
					entity.setItemListHot(itemListHot);

					DataCache.SearchShopResultCache.add(entity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		} else if (filterTyppe == Constants.KEYWORDS_TYPE_GOODS) {
			JSONArray array = JSONUtil.getJSONArray(data,
					Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_GOODS_LIST,
					null);
			if (array == null)
				return false;
			for (int index = 0; index < array.length(); index++) {
				JSONObject child;
				try {
					child = array.getJSONObject(index);
					long id = JSONUtil.getLong(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_ID, 0);
					String sequence = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_SEQUENCE,
									null);
					String sort = JSONUtil.getString(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_SORT,
							null);
					String name = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_ITEMNMAE,
									null);
					String sortType = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_SORTTYPE,
									null);
					String picPath = JSONUtil.getString(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_PICPATH,
							null);
					String shopId = JSONUtil.getString(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_SHOPID,
							null);
					double listPrice = JSONUtil
							.getDouble(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_LISTPRICE,
									0);
					double plPrice = JSONUtil.getDouble(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_PLPRICE,
							0);
					String plPriceBegin = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_PLPRICE_BEGIN,
									null);
					String plPriceEnd = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_PLPRICE_END,
									null);
					int evaCount = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_EVACOUNT,
									0);
					String classificId = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_CLASSFICATIONID,
									null);
					String style = JSONUtil.getString(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_STYLE,
							null);
					long favourNum = JSONUtil
							.getLong(
									child,
									Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_FAVOURNUM,
									0);
					int sellNum = JSONUtil.getInt(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_SELLNUM,
							0);
					String type = JSONUtil.getString(child,
							Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_TYPE,
							null);
					GrouponItemEntity entity = new GrouponItemEntity();
					entity.setId(id);
					entity.setName(name); 
					entity.setImage(picPath);
					entity.setListPrice(listPrice);
					entity.setPlPrice(plPrice);
					entity.setSellNum(sellNum);
					entity.setType(type);
					DataCache.NavGrouponListCache.add(entity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		} else if (filterTyppe == Constants.KEYWORDS_TYPE_GROUPON) {
			JSONArray array = JSONUtil.getJSONArray(data,
					Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_GOODS_LIST,
					null);
			if (array == null)
				return false;
			for (int index = 0; index < array.length(); index++) {
				JSONObject child;
				try {
					child = array.getJSONObject(index);
					int selfId = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SELFID,
									0);
					String type = JSONUtil.getString(child,
							Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_TYPE,
							null);
					String path = JSONUtil.getString(child,
							Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_PATH,
							null);
					String name = JSONUtil.getString(child,
							Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_NAME,
							null);
					String notice = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_NOTICE,
									null);
					int saleVolume = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SALEVOLUME,
									0);
					String price = JSONUtil.getString(child,
							Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_PRICE,
							null);
					String groupPrice = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_GROUPONPRICE,
									null);

				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 解析关键字搜索JSON
	 * 
	 * @param jsonString
	 *            待解析的json字符串
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseCartList(String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.CART_LIST_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray shopArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.CART_LIST_JSON_ARRAY_KEYNAME, null);
		double totalPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.CART_LIST_JSON_ARRAY_KEY_TOTALPRICE, 0);
		long cartId = JSONUtil.getLong(data,
				Constants.JSONKeyName.CART_LIST_JSON_ARRAY_KEY_CARTID, 0);
		DataCache.totalPrice = totalPrice;
		DataCache.cartId = cartId;
		if (shopArray == null)
			return false;
		DataCache.CartShopListCache.clear();
		// ArrayList<CartShopListEntity> shopEntities = new
		// ArrayList<CartShopListEntity>();
		for (int i = 0; i < shopArray.length(); i++) {
			CartShopListEntity shopEntity = new CartShopListEntity();
			JSONObject shop;
			try {
				shop = shopArray.getJSONObject(i);
				int shopId = JSONUtil
						.getInt(shop,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_SHOPID,
								0);
				String shopName = JSONUtil
						.getString(
								shop,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_SHOPNAME,
								null);
				int totalSNum = JSONUtil
						.getInt(shop,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_TOTALNUM,
								0);
				int totalSPrice = JSONUtil
						.getInt(shop,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_TOTALPRICE,
								0);
				shopEntity.setShopId(shopId);
				shopEntity.setTotalNum(totalSNum);
				shopEntity.setTotalPrice(totalSPrice);
				shopEntity.setShopName(shopName);

				// shopEntities.add(shopEntity);
				DataCache.CartShopListCache.add(shopEntity);

				ArrayList<ItemListEntity> itemEntities = new ArrayList<ItemListEntity>();
				JSONArray itemArray = JSONUtil
						.getJSONArray(
								shop,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_ITEMLIST,
								null);
				if (itemArray != null) {
					for (int j = 0; j < itemArray.length(); j++) {
						ItemListEntity itemEntity = new ItemListEntity();
						JSONObject item;
						try {
							item = itemArray.getJSONObject(j);
							long itemId = JSONUtil
									.getLong(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMID,
											0);
							String image = JSONUtil
									.getString(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_IMAGE,
											null);
							String isGroupon = JSONUtil
									.getString(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMTYPE,
											null);
							String itemName = JSONUtil
									.getString(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMNAME,
											null);
							String plPrice = JSONUtil
									.getString(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PLPRICE,
											null);
							String listPrice = JSONUtil
									.getString(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_LISTPRICE,
											null);
							int storeNum = JSONUtil
									.getInt(item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_STORENUM,
											0);
							int buyNum = JSONUtil
									.getInt(item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_BUYNUM,
											0);
							LogUtil.d("json", "parseCartList listPrice=  "
									+ listPrice);
							LogUtil.d("json", "parseCartList plPrice=  "
									+ plPrice);
							itemEntity.setItemId(itemId);
							itemEntity.setImage(image);
							itemEntity.setItemName(itemName);
							itemEntity.setIsGroupon(isGroupon);
							itemEntity.setListPrice(listPrice+"");
							itemEntity.setplPrice(plPrice+"");
							itemEntity.setStoreNum(storeNum);
							itemEntity.setBuyNum(buyNum);

							itemEntities.add(itemEntity);
							
							ArrayList<ItemBargainListEntity> itemBargainEntities = new ArrayList<ItemBargainListEntity>();
							JSONArray itemBargainArray = JSONUtil
									.getJSONArray(
											item,
											Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMBARGAINLIST,
											null);
							if (itemBargainArray != null) {
								for (int k = 0; k < itemBargainArray.length(); k++) {
									ItemBargainListEntity itemBargainEntity = new ItemBargainListEntity();
									JSONObject itemBargain;
									try {
										itemBargain = itemBargainArray.getJSONObject(k);
										String bargainName = JSONUtil
												.getString(
														itemBargain,
														Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_NAME,
														null);
										String type = JSONUtil
												.getString(
														itemBargain,
														Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_TYPE,
														null);
										itemBargainEntity.setName(bargainName);
										itemBargainEntity.setType(type);
										itemBargainEntities.add(itemBargainEntity);
									} catch (JSONException e) {
										e.printStackTrace();
										return false;
									}
									
								}
								int id = new Long(itemId).intValue();
								DataCache.itemBargainListCache.put(id
										, itemBargainEntities);
							}

						} catch (JSONException e) {
							e.printStackTrace();
							return false;
						}
					}

					DataCache.CartItemListCache.put(shopId, itemEntities);
				}

				ArrayList<BarginListEntity> bargainEntities = new ArrayList<BarginListEntity>();
				JSONArray bargainArray = JSONUtil
						.getJSONArray(
								shop,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_BARGAINLIST,
								null);
				LogUtil.d("json", "bargainArray=  " + bargainArray);

				if (bargainArray != null) {
					for (int j = 0; j < bargainArray.length(); j++) {
						BarginListEntity bargainEntity = new BarginListEntity();
						JSONObject bargain;
						try {
							bargain = bargainArray.getJSONObject(j);
							String bargainName = JSONUtil
									.getString(
											bargain,
											Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_NAME,
											null);
							bargainEntity.setBagainName(bargainName);
							LogUtil.d("json", "bargainName=  " + bargainName);
							bargainEntities.add(bargainEntity);
						} catch (JSONException e) {
							e.printStackTrace();
							return false;
						}
					}

					DataCache.CartBagainListCache.put(shopId, bargainEntities);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析品牌详情信息
	 * 
	 * @param jsonString
	 *            品牌详情json
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseBrandDetailsJson(String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.BRAND_DETAIL_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONObject shop = JSONUtil.getJSONObject(data,
				Constants.JSONKeyName.BRAND_DETAIL_ARRAY_KEY_NAME, null);
		if (shop == null)
			return false;
		String shopName = JSONUtil.getString(data,
				Constants.JSONKeyName.BRAND_DETAIL_OBJ_KEY_SHOPNAME, null);
		String brief = JSONUtil.getString(data,
				Constants.JSONKeyName.BRAND_DETAIL_OBJ_KEY_SHOPBRIEF, null);
		String logo = JSONUtil.getString(data,
				Constants.JSONKeyName.BRAND_DETAIL_OBJ_KEY_SHOPLOGO, null);
		double fscore = JSONUtil.getDouble(shop,
				Constants.JSONKeyName.BRAND_DETAIL_OBJ_KEY_SHOPFSCORE, 0);
		int favourNum = JSONUtil.getInt(shop,
				Constants.JSONKeyName.BRAND_DETAIL_OBJ_KEY_SHOPFAVOURNUM, 0);
		String bussinessRange = JSONUtil.getString(shop,
				Constants.JSONKeyName.BRAND_DETAIL_OBJ_KEY_SHOPBUSSINESSRANGE,
				null);

		NavigationBrandDetailsEntity entity = new NavigationBrandDetailsEntity();
		entity.setShopName(shopName);
		entity.setBussinessRange(bussinessRange);
		entity.setFscore(fscore);
		entity.setLogo(logo);
		entity.setFavourNum(favourNum);
		entity.setBrief(brief);

		DataCache.NavBrandDetailsCache.add(entity);
		return true;
	}

	/**
	 * 解析团购详情信息
	 * 
	 * @param jsonString
	 *            团购详情json
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseGrouponDetailsJson(String jsonString) {
		NavigationGrouponDetailsEntity entity = new NavigationGrouponDetailsEntity();
		JSONArray array = JSONUtil.getJSONArray(jsonString,
				Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_GOODS_LIST, null);
		if (array == null)
			return false;
		for (int index = 0; index < array.length(); index++) {
			JSONObject child;
			try {
				child = array.getJSONObject(index);
				int selfId = JSONUtil.getInt(child,
						Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SELFID, 0);
				String type = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_TYPE,
								null);
				String picPath = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_PATH,
								null);
				String name = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_NAME,
								null);
				String notice = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_NOTICE,
						null);
				double saleVolume = JSONUtil
						.getDouble(
								child,
								Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SALEVOLUME,
								0);
				String price = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_PRICE,
						null);
				String grouponPrice = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_GROUPONPRICE,
								null);

				entity.setGrouponPrice(StringUtil.toDouble(grouponPrice));
				entity.setName(name);
				entity.setNotice(notice);
				entity.setPicPath(picPath);
				entity.setPrice(price);
				entity.setSaleVolume(saleVolume);
				entity.setSelfId(selfId);
				entity.setType(type);

				DataCache.NavGrouponDetailsCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析购物车结算信息
	 * 
	 * @param jsonString
	 *            结算信息json
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseCartCount(String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.CART_LIST_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;

		CartCountEntity countEntity = new CartCountEntity();
		String address = JSONUtil.getString(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_ADDRESS, null);
		int addressId = JSONUtil.getInt(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_ADDRESSID, 0);
		double allPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_ALLPRICE, 0);
		double freight = JSONUtil.getDouble(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_FREIGHT, 0);
		double listPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_LISTPRICE, 0);
		double reducePrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_REDUCEPRICE, 0);
		String receiveTime = JSONUtil
				.getString(
						data,
						Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_RECEIVETIME,
						null);
		String receiver = JSONUtil.getString(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_RECEIVER, null);
		String telNum = JSONUtil.getString(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_TELNUM, null);
		int allNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_ALL_NUM, 0);
		int isOtherCommunity = JSONUtil.getInt(data,
				Constants.JSONKeyName.CART_COUNT_JSON_OBJ_KEY_IS_OTHER_COMMUNITY, 0);
		int view = JSONUtil.getInt(data, "view", 0);
		countEntity.setAddress(address);
		countEntity.setAddressId(addressId);
		countEntity.setAllPrice(allPrice);
		countEntity.setFreight(freight);
		countEntity.setListPrice(listPrice);
		countEntity.setReceiver(receiver);
		countEntity.setReceiveTime(receiveTime);
		countEntity.setReducePrice(reducePrice);
		countEntity.setTel(telNum);
		countEntity.setAllNum(allNum);
		countEntity.setIsOtherCommunity(isOtherCommunity);
		countEntity.setView(view);

		DataCache.CartCountEntityCache.add(countEntity);

		JSONArray shopArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.CART_LIST_JSON_ARRAY_KEYNAME, null);
		if (shopArray != null) {
			for (int i = 0; i < shopArray.length(); i++) {
				CartShopListEntity shopEntity = new CartShopListEntity();
				JSONObject shop;
				try {
					shop = shopArray.getJSONObject(i);
					int shopId = JSONUtil
							.getInt(shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_SHOPID,
									0);
					String shopName = JSONUtil
							.getString(
									shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_SHOPNAME,
									null);
					int totalSNum = JSONUtil
							.getInt(shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_TOTALNUM,
									0);
					double totalSPrice = JSONUtil
							.getDouble(shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_TOTALPRICE,
									0);
					shopEntity.setShopId(shopId);
					shopEntity.setTotalNum(totalSNum);
					shopEntity.setTotalPrice(totalSPrice);
					shopEntity.setShopName(shopName);

					// shopEntities.add(shopEntity);
					DataCache.CartCountShopListCache.add(shopEntity);

					ArrayList<ItemListEntity> itemEntities = new ArrayList<ItemListEntity>();
					JSONArray itemArray = JSONUtil
							.getJSONArray(
									shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_ITEMLIST,
									null);
					if (itemArray != null) {
						for (int j = 0; j < itemArray.length(); j++) {
							ItemListEntity itemEntity = new ItemListEntity();
							JSONObject item;
							try {
								item = itemArray.getJSONObject(j);
								long itemId = JSONUtil
										.getLong(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMID,
												0);
								String image = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_IMAGE,
												null);
								String isGroupon = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMTYPE,
												null);
								String itemName = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMNAME,
												null);
								String plPrice = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PLPRICE,
												null);
								String listItemPrice = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.CART_COUNT_SHOPLIST_JSON_OBJ_KEY_TOTALPRICE,
												null);
								int storeNum = JSONUtil
										.getInt(item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_STORENUM,
												0);
								int buyNum = JSONUtil
										.getInt(item,
												Constants.JSONKeyName.CART_COUNT_ITEMLIST_JSON_OBJ_KEY_TOTALNUM,
												0);

								itemEntity.setItemId(itemId);
								itemEntity.setImage(image);
								itemEntity.setItemName(itemName);
								itemEntity.setIsGroupon(isGroupon);
								itemEntity.setListPrice(listItemPrice+"");
								itemEntity.setplPrice(plPrice+"");
								itemEntity.setStoreNum(storeNum);
								itemEntity.setBuyNum(buyNum);

								itemEntities.add(itemEntity);
								
								ArrayList<ItemBargainListEntity> itemBargainEntities = new ArrayList<ItemBargainListEntity>();
								JSONArray itemBargainArray = JSONUtil
										.getJSONArray(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMBARGAINLIST,
												null);
								if (itemBargainArray != null) {
									for (int k = 0; k < itemBargainArray.length(); k++) {
										ItemBargainListEntity itemBargainEntity = new ItemBargainListEntity();
										JSONObject itemBargain;
										try {
											itemBargain = itemBargainArray.getJSONObject(k);
											String bargainName = JSONUtil
													.getString(
															itemBargain,
															Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_NAME,
															null);
											String type = JSONUtil
													.getString(
															itemBargain,
															Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_TYPE,
															null);
											itemBargainEntity.setName(bargainName);
											itemBargainEntity.setType(type);
											itemBargainEntities.add(itemBargainEntity);
										} catch (JSONException e) {
											e.printStackTrace();
											return false;
										}
										
									}
									int id = new Long(itemId).intValue();
									DataCache.itemBargainListCache.put(id
											, itemBargainEntities);
								}
							} catch (JSONException e) {
								e.printStackTrace();
								return false;
							}
						}
						DataCache.CarCountItemListCache.put(shopId,
								itemEntities);
						
						
					}

					ArrayList<PresentEntity> presentEntities = new ArrayList<PresentEntity>();
					JSONArray presentArray = JSONUtil
							.getJSONArray(
									shop,
									Constants.JSONKeyName.CART_COUNT_SHOP_PRESENTLIST_JSON_ARRAY_KEY,
									null);
					/*
					 * if (presentArray == null) return false; if (presentArray
					 * != null) { for (int j = 0; j < presentArray.length();
					 * j++) { PresentEntity entity = new PresentEntity();
					 * JSONObject presentObj = presentArray.getJSONObject(j);
					 * int presentId = JSONUtil .getInt(presentObj,
					 * Constants.JSONKeyName
					 * .CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_ID, 0); String
					 * name = JSONUtil .getString( presentObj,
					 * Constants.JSONKeyName
					 * .CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_NAME, null);
					 * String image = JSONUtil .getString( presentObj,
					 * Constants.
					 * JSONKeyName.CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_IMAGE
					 * , null); int num = JSONUtil .getInt(presentObj,
					 * Constants.
					 * JSONKeyName.CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_NUM,
					 * 0);
					 * 
					 * entity.setImage(image); entity.setName(name);
					 * entity.setNum(num); entity.setPresentId(presentId);
					 * 
					 * presentEntities.add(entity); }
					 * DataCache.ShopPresentListCache.put(shopId,
					 * presentEntities); }
					 */

					ArrayList<BarginListEntity> bargainEntities = new ArrayList<BarginListEntity>();
					JSONArray bargainArray = JSONUtil
							.getJSONArray(
									shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_BARGAINLIST,
									null);
					if (bargainArray != null) {
						for (int j = 0; j < bargainArray.length(); j++) {
							BarginListEntity bargainEntity = new BarginListEntity();
							JSONObject bargain;
							try {
								bargain = bargainArray.getJSONObject(j);
								String bargainName = JSONUtil
										.getString(
												bargain,
												Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_NAME,
												null);
								bargainEntity.setBagainName(bargainName);

								bargainEntities.add(bargainEntity);
							} catch (JSONException e) {
								e.printStackTrace();
								return false;
							}
						}
					}

					DataCache.CartCountBagainListCache.put(shopId,
							bargainEntities);

					ArrayList<CashCouponEntity> cashCouponEntities = new ArrayList<CashCouponEntity>();
					JSONArray cashCouponArray = JSONUtil
							.getJSONArray(
									shop,
									Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_ARRAY_KEY,
									null);
					if (cashCouponArray != null) {
						for (int j = 0; j < cashCouponArray.length(); j++) {
							CashCouponEntity cashCouponEntity = new CashCouponEntity();
							JSONObject cashCouponObj;
							try {
								cashCouponObj = cashCouponArray
										.getJSONObject(j);
								String cashCouponName = JSONUtil
										.getString(
												cashCouponObj,
												Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_NAME,
												null);
								int cashCouponId = JSONUtil
										.getInt(cashCouponObj,
												Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_ID,
												0);
								double cashCouponPrice = JSONUtil
										.getDouble(
												cashCouponObj,
												Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_PRICE,
												0);
								cashCouponEntity.setName(cashCouponName);
								cashCouponEntity.setCouponId(cashCouponId);
								cashCouponEntity.setPrice(cashCouponPrice);

								cashCouponEntities.add(cashCouponEntity);
							} catch (JSONException e) {
								e.printStackTrace();
								return false;
							}
						}

						DataCache.CashCouponListCache.put(shopId,
								cashCouponEntities);
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		
		JSONArray logisticsArray = JSONUtil
				.getJSONArray(
						data,
						Constants.JSONKeyName.CART_COUNT_SHOP_LOGISTICS_JSON_ARRAY_KEY,
						null);
		if (logisticsArray != null) {
			for (int j = 0; j < logisticsArray.length(); j++) {
				LogisticsEntity logisticsEntity = new LogisticsEntity();
				JSONObject logisticsObj;
				try {
					logisticsObj = logisticsArray
							.getJSONObject(j);
					String logisticsName = JSONUtil
							.getString(
									logisticsObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_LOGISTICS_JSON_OBJ_KEY_NAME,
									null);
					int logisticsId = JSONUtil
							.getInt(logisticsObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_LOGISTICS_JSON_OBJ_KEY_ID,
									0);

					logisticsEntity.setName(logisticsName);
					logisticsEntity.setLogisticId(logisticsId);

					DataCache.LogisticListCache.add(logisticsEntity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}

		}
		
		JSONArray date = JSONUtil.getJSONArray(data, "dateTimeList", null);
		if (date != null) {
			for (int k = 0; k < date.length(); k++) {
				JSONObject child;
				try {
					child = date.getJSONObject(k);
					DateTimeEntity dateEntity = new DateTimeEntity();
					String date1 = JSONUtil.getString(child,"data", null);
					dateEntity.setDate(date1);
					DataCache.dateListCache.add(dateEntity);
					JSONArray timeList = JSONUtil.getJSONArray(child, "timeList", null);
					ArrayList<DateTimeEntity> timeEntities = new ArrayList<DateTimeEntity>();
					if (timeList != null) {

						for (int j = 0; j < timeList.length(); j++) {
							JSONObject child1;
							child1 = timeList.getJSONObject(j);
							DateTimeEntity timeEntity = new DateTimeEntity();
							
							String dateTime = JSONUtil
									.getString(
											child1,
											"dateTime",
											null);
							
							timeEntity.setTime(dateTime);;
							timeEntities.add(timeEntity);
						}

					}
						DataCache.timeListCache.put(date1, timeEntities);
					}catch (JSONException e) {
						e.printStackTrace();
						return false;
					}
				}
				
		}
		
		JSONArray payArray = JSONUtil
				.getJSONArray(
						data,
						Constants.JSONKeyName.CART_COUNT_SHOP_PAY_JSON_ARRAY_KEY,
						null);
		if (payArray != null) {
			for (int j = 0; j < payArray.length(); j++) {
				PayEntity payEntity = new PayEntity();
				JSONObject payObj;
				try {
					payObj = payArray
							.getJSONObject(j);
					String payName = JSONUtil
							.getString(
									payObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_LOGISTICS_JSON_OBJ_KEY_NAME,
									null);
					int payId = JSONUtil
							.getInt(payObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_LOGISTICS_JSON_OBJ_KEY_ID,
									0);

					payEntity.setName(payName);
					payEntity.setPayId(payId);

					DataCache.payListCache.add(payEntity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * 解析下订单
	 * 
	 * @param jsonString
	 *            结算信息json
	 * @exception
	 * @since 1.0.0
	 */

	public static boolean parseEnterSettle(String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.CART_LIST_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;

		JSONArray orderArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_KEYNAME, null);
		if (orderArray == null)
			return false;
		for (int i = 0; i < orderArray.length(); i++) {
			AddOrderEntity orderEntity = new AddOrderEntity();
			JSONObject order;
			try {
				order = orderArray.getJSONObject(i);
				String orderNo = JSONUtil.getString(order,
						Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_ORDER_NO,
						null);
				int pakageNum = JSONUtil
						.getInt(order,
								Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_PACKAGE_NUM,
								0);
				int itemNum = JSONUtil
						.getInt(order,
								Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_ITEM_NUM,
								0);
				double Price = JSONUtil.getDouble(order,
						Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_PRICE, 0);

				orderEntity.setOrderNo(orderNo);
				orderEntity.setPakageNum(pakageNum);
				orderEntity.setItemNum(itemNum);
				orderEntity.setPrice(Price);

				DataCache.ADDORDEREntityCache.add(orderEntity);

			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析宝龙抵用券信息
	 * 
	 * @param jsonString
	 *            json信息
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parsePlCashCoupon(String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.PL_CASHCOUPON_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		
		JSONArray CouponArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.PL_CASHCOUPON_JSON_TOPEST_PLCASHCOUPONLIST, null);
		if (CouponArray == null)
			return false;
		
		for (int i = 0; i < CouponArray.length(); i++) {
			PlCashCouponEntity entity = new PlCashCouponEntity();
			JSONObject cashCoupon;
			try {
				cashCoupon = CouponArray.getJSONObject(i);
				int cashCouponId = JSONUtil.getInt(cashCoupon,
						Constants.JSONKeyName.PL_CASHCOUPON_JSON_OBJ_KEY_ID, 0);
				double cashCouponPrice = JSONUtil.getDouble(cashCoupon,
						Constants.JSONKeyName.PL_CASHCOUPON_JSON_OBJ_KEY_PRICE,
						0);
				String isActive = JSONUtil.getString(cashCoupon,
						Constants.JSONKeyName.PL_CASHCOUPON_JSONOBJ_KEY_ACTIVE,
						null);
				String cashCouponName = JSONUtil.getString(cashCoupon,
						Constants.JSONKeyName.PL_CASHCOUPON_JSON_OBJ_KEY_NAME,
						null);
				entity.setCouponId(cashCouponId);
				entity.setIsActive(isActive);
				entity.setName(cashCouponName);
				entity.setPrice(cashCouponPrice);

				DataCache.PLCashCouponListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}

		}
		return true;
	}

	/**
	 * 解析用户收货地址
	 * 
	 * @param jsonString
	 *            json信息
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseUserAddress(String jsonString) {
		JSONObject data = JSONUtil.getJSONObject(jsonString,
				Constants.JSONKeyName.USER_ADDRESS_JSON_TOPEST_DATA, null);
		JSONArray dataArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.USER_ADDRESS_JSON_ARRAY_KEY, null);
		if (dataArray == null)
			return false;
		for (int i = 0; i < dataArray.length(); i++) {
			UserAddressEntity entity = new UserAddressEntity();
			try {
				JSONObject dataObj = dataArray.getJSONObject(i);
				String address = JSONUtil
						.getString(
								dataObj,
								Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_ADDRESS,
								null);
				String area = JSONUtil.getString(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_AREA,
						null);
				String areaCode = JSONUtil
						.getString(
								dataObj,
								Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_AREACODE,
								null);
				String consignee = JSONUtil
						.getString(
								dataObj,
								Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_CONSIGNEE,
								null);
				String ext = JSONUtil.getString(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_EXT,
						null);
				String mobile = JSONUtil.getString(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_MOBILE,
						null);
				String city = JSONUtil.getString(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_CITY,
						null);
				String province = JSONUtil
						.getString(
								dataObj,
								Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_PROVINCE,
								null);
				String tel = JSONUtil.getString(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_TEL,
						null);
				String zipCode = JSONUtil
						.getString(
								dataObj,
								Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_ZIPCODE,
								null);
				int id = JSONUtil.getInt(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_ID, 0);
				int isDefault = JSONUtil
						.getInt(dataObj,
								Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_ISDEFULT,
								0);
				String communityName = JSONUtil.getString(dataObj,
						Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_COMMUNITY_NAME,
						null);
				String communityId = JSONUtil.getString(dataObj, Constants.JSONKeyName.USER_ADDRESS_JSON_OBJ_KEY_COMMUNITY_ID,
						null);

				entity.setAddress(address);
				entity.setAddressId(id);
				entity.setArea(area);
				entity.setAreaCode(areaCode);
				entity.setCity(city);
				entity.setConsignee(consignee);
				entity.setExt(ext);
				entity.setMobile(mobile);
				entity.setProvince(province);
				entity.setTel(tel);
				entity.setZipCode(zipCode);
				entity.setCommunityName(communityName);
				entity.setCommunityId(communityId);
				entity.setIsDefault(isDefault);

				DataCache.UserAddressListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}

		}
		return true;
	}

	/**
	 * 解析品牌列表
	 * 
	 * @param result
	 *            json字串 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseBrandList(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.BRAND_JSON_TOPEST_DATA, null);
		JSONArray dataArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.BRAND_JSON_TOPEST_DATA_KEY_NAME, null);
		if (dataArray == null)
			return false;
		for (int i = 0; i < dataArray.length(); i++) {
			try {
				JSONObject child = dataArray.getJSONObject(i);
				BrandListEntity entity = new BrandListEntity();
				String icon = JSONUtil.getString(child,
						Constants.JSONKeyName.BRAND_OBJ_KEY_ICON, null);
				String enName = JSONUtil.getString(child,
						Constants.JSONKeyName.BRAND_OBJ_KEY_ENNAME, null);
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.BRAND_OBJ_KEY_SELFID, null);
				entity.setEnName(enName);
				entity.setIcon(icon);
				entity.setId(id);
				DataCache.BrandListCache.add(entity);

			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析店铺详情
	 * 
	 * @param result
	 *            json字串 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseShopDetails(String result) {
		DataCache.SHOP_DETAIL_ALL = result;
		DataCache.CategoryListCache.clear();
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.SHOP_DETAIL_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		String classification = JSONUtil.getString(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_CLASSIFICATION, null);
		double evaluation = JSONUtil.getDouble(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_EVALUATION, 0);
		int favourNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_FAVOURNUM, 0);
		String image = JSONUtil.getString(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_IMAGE, null);
		String name = JSONUtil.getString(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_NAME, null);
		String instroduction = JSONUtil.getString(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_INSTRODUCTION, null);
		int itemNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_ITEMNUM, 0);
		// int orderType = JSONUtil.getInt(data,
		// Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_ORDERTYPE, 0);
		// int showType = JSONUtil.getInt(data,
		// Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_SHOWTYPE, 0);
		ShopDetailsEntity entity = new ShopDetailsEntity();
		entity.setClassification(classification);
		entity.setEvaluation(evaluation);
		entity.setFavourNum(favourNum);
		entity.setImage(image);
		entity.setItemNum(itemNum);
		// entity.setOrderType(orderType);
		// entity.setShowType(showType);
		entity.setInstroduction(instroduction);
		entity.setName(name);

		DataCache.ShopDetailsCache.add(entity);

		JSONArray itemListArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.SHOP_DETAIL_OBJ_KEY_ITEMLIST, null);
		if (itemListArray != null) {
			for (int i = 0; i < itemListArray.length(); i++) {
				JSONObject item;
				try {
					item = itemListArray.getJSONObject(i);
					ShopItemListEntity itemEntity = new ShopItemListEntity();
					long itemId = JSONUtil.getLong(item,
							Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_ID,
							0);
					String itemImage = JSONUtil
							.getString(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_IMAGE,
									null);
					String itemName = JSONUtil
							.getString(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_NAME,
									null);
					LogUtil.d("gridadpter", "itemId =" + itemId);
					LogUtil.d("gridadpter", "itemName =" + itemName);
					int isCart = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_ISCART,
									0);
					int isFavour = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_ISFAVOUR,
									0);
					int sellNumMonth = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_SELLNUMMONTH,
									0);
					int type = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_TYPE,
									0);
					double listPrice = JSONUtil
							.getDouble(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_LISTPRICE,
									0);
					double plPrice = JSONUtil
							.getDouble(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_PLPRICE,
									0);
					itemEntity.setId(itemId);
					itemEntity.setImage(itemImage);
					itemEntity.setIsCart(isCart);
					itemEntity.setIsFavour(isFavour);
					itemEntity.setListPrice(listPrice);
					itemEntity.setPlPrice(plPrice);
					itemEntity.setName(itemName);
					itemEntity.setSellNumMonth(sellNumMonth);
					itemEntity.setType(type);
					DataCache.ShopItemListCache.add(itemEntity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
			
			
			JSONArray categoryList = JSONUtil.getJSONArray(data, "categoryList", null);
			if (categoryList != null) {
				for (int i = 0; i < categoryList.length(); i++) {
					JSONObject item;
					try {
						item = categoryList.getJSONObject(i);
						DomainShopDetailCategory itemEntity = new DomainShopDetailCategory();
						long id = JSONUtil.getLong(item,"id", 0);				
						
						String name1 = JSONUtil.getString(
										item,"name", null);
						long parentId = JSONUtil
								.getLong(
										item,
										"parentId",
										0);
						int level = JSONUtil.getInt(
								item,"level", null);
						itemEntity.setId(id+"");
						itemEntity.setName(name1);
						itemEntity.setParentId(parentId+"");
						itemEntity.setLevel(level+"");
						DataCache.CategoryListCache.add(itemEntity);
					} catch (JSONException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static void addShopItemToCache(JSONArray itemListArray){
		if (itemListArray != null) {
			
			for (int i = 0; i < itemListArray.length(); i++) {
				JSONObject item;
				try {
					item = itemListArray.getJSONObject(i);
					ShopItemListEntity itemEntity = new ShopItemListEntity();
					long itemId = JSONUtil.getLong(item,
							Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_ID,
							0);
					String itemImage = JSONUtil
							.getString(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_IMAGE,
									null);
					String itemName = JSONUtil
							.getString(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_NAME,
									null);
					LogUtil.d("gridadpter", "itemId =" + itemId);
					LogUtil.d("gridadpter", "itemName =" + itemName);
					int isCart = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_ISCART,
									0);
					int isFavour = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_ISFAVOUR,
									0);
					int sellNumMonth = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_SELLNUMMONTH,
									0);
					int type = JSONUtil
							.getInt(item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_TYPE,
									0);
					double listPrice = JSONUtil
							.getDouble(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_LISTPRICE,
									0);
					double plPrice = JSONUtil
							.getDouble(
									item,
									Constants.JSONKeyName.SHOP_ITEM_DETAIL_OBJ_KEY_PLPRICE,
									0);
					itemEntity.setId(itemId);
					itemEntity.setImage(itemImage);
					itemEntity.setIsCart(isCart);
					itemEntity.setIsFavour(isFavour);
					itemEntity.setListPrice(listPrice);
					itemEntity.setPlPrice(plPrice);
					itemEntity.setName(itemName);
					itemEntity.setSellNumMonth(sellNumMonth);
					itemEntity.setType(type);
					DataCache.ShopItemListCache.clear();
					DataCache.ShopItemListCache.add(itemEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 解析商品详情
	 * 
	 * @param result
	 *            json字串 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseItemDetails(String result) {
		DataCache.ItemsDetailsCache.clear();
		DataCache.ItemsBarginListCache.clear();
		DataCache.ItemsPropList1Cache.clear();
		DataCache.ItemsPropList2Cache.clear();

		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.ITEM_DETAIL_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		int sellNum30 = JSONUtil.getInt(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_SELLNUM30, 0);
		int commentNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_BARGINLIST, 0);
		int favourNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_FAVOURNUM, 0);
		String itemName = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_NAME, null);
		String itemProp = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_PROP, null);
		String shopImage = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_SHOPIMAGE, null);
		String shopName = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_SHOPNAME, null);
		String shopProp = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_SHOPPROP, null);
		double shopEvaluation = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_SHOPEVALUATION, 0);
		double evaluation = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_EVALUATION, 0);
		double listPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_LISTPRICE, 0);
		double plPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_PLPRICE, 0);
		// double freight = JSONUtil.getDouble(data,
		// Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_FREIGHT, 0);
		long shopId = JSONUtil.getLong(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_SHOPID, 0);
		String detailUrl = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_DETAILURL, null);
		String prop1Name = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_PROP1NAME, null);
		String prop2Name = JSONUtil.getString(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_PROP2NAME, null);
		int type = JSONUtil.getInt(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_ITEM_TYPE, 0);
		long grouponId = JSONUtil.getLong(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_GROUPON_ID, 0);
		int stat = JSONUtil.getInt(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_STAT, 1);
		
		ItemDetailEntity itemEntity = new ItemDetailEntity();
		itemEntity.setCommentNum(commentNum);
		itemEntity.setDetailUrl(detailUrl);
		itemEntity.setEvaluation(evaluation);
		itemEntity.setFavourNum(favourNum);
		// itemEntity.setFreight(freight);
		itemEntity.setListPrice(listPrice);
		itemEntity.setName(itemName);
		itemEntity.setPlPrice(plPrice);
		itemEntity.setProp(itemProp);
		itemEntity.setProp1Name(prop1Name);
		itemEntity.setProp2Name(prop2Name);
		itemEntity.setSellNum30(sellNum30);
		itemEntity.setShopEvaluation(shopEvaluation);
		itemEntity.setShopId(shopId);
		itemEntity.setShopImage(shopImage);
		itemEntity.setShopName(shopName);
		itemEntity.setShopProp(shopProp);
		itemEntity.setType(type);
		itemEntity.setGrouponId(grouponId);
		itemEntity.setStat(stat);
		LogUtil.d("parseItemDetails", "parseItemDetails shopName= " + shopName);
		DataCache.ItemsDetailsCache.add(itemEntity);

		String[] bargainList = JSONUtil.getStringArray(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_BARGINLIST,
				new String[] {});
		LogUtil.d("json", "bargainList=  " + bargainList);
		for (String barginName : bargainList) {
			BarginListEntity bargainEntity = new BarginListEntity();
			bargainEntity.setBagainName(barginName);
			DataCache.ItemsBarginListCache.add(bargainEntity);
		}

		JSONArray propList1 = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_COLORLIST, null);
		if (propList1 != null) {
			for (int i = 0; i < propList1.length(); i++) {
				JSONObject child;
				try {
					child = propList1.getJSONObject(i);
					PropEntity prop1Entity = new PropEntity();
					int isDefault = JSONUtil.getInt(child,
							Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ISDEFAULT,
							0);
					// int isEmpty = JSONUtil.getInt(child,
					// Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ISEMPTY, 0);
					String name = JSONUtil.getString(child,
							Constants.JSONKeyName.PROP_OBJ_KEY_PROP_NAME, null);
					// int order = JSONUtil.getInt(child,
					// Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ORDER, 0);
					int stockNum = JSONUtil
							.getInt(child,
									Constants.JSONKeyName.PROP_OBJ_KEY_PROP_STOCKNUM,
									0);
					int itemId = JSONUtil.getInt(child,
							Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ITEMID, 0);
					prop1Entity.setIsDefault(isDefault);
					// prop1Entity.setIsEmpty(isEmpty);
					prop1Entity.setName(name);
					// prop1Entity.setOrder(order);
					prop1Entity.setStockNum(stockNum);
					prop1Entity.setItemId(itemId);
					DataCache.ItemsPropList1Cache.add(prop1Entity);
					JSONArray propList2 = JSONUtil.getJSONArray(child,
							Constants.JSONKeyName.PROP_OBJ_KEY_SIZELIST, null);
					ArrayList<PropEntity> prop2Entites = new ArrayList<PropEntity>();
					if (propList2 != null) {

						for (int j = 0; j < propList2.length(); j++) {
							JSONObject child1;
							child1 = propList2.getJSONObject(j);
							PropEntity prop2Entity = new PropEntity();
							int isDefault2 = JSONUtil
									.getInt(child1,
											Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ISDEFAULT,
											0);
							// int isEmpty2 = JSONUtil
							// .getInt(child1,
							// Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ISEMPTY,
							// 0);
							String name2 = JSONUtil
									.getString(
											child1,
											Constants.JSONKeyName.PROP_OBJ_KEY_PROP_NAME,
											null);
							// int order2 = JSONUtil.getInt(child1,
							// Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ORDER,
							// 0);
							int stockNum2 = JSONUtil
									.getInt(child1,
											Constants.JSONKeyName.PROP_OBJ_KEY_PROP_STOCKNUM,
											0);
							int itemId2 = JSONUtil
									.getInt(child1,
											Constants.JSONKeyName.PROP_OBJ_KEY_PROP_ITEMID,
											0);
							prop2Entity.setIsDefault(isDefault2);
							// prop2Entity.setIsEmpty(isEmpty2);
							prop2Entity.setName(name2);
							// prop2Entity.setOrder(order2);
							prop2Entity.setStockNum(stockNum2);
							prop2Entity.setItemId(itemId2);
							prop2Entites.add(prop2Entity);
						}

						DataCache.ItemsPropList2Cache.put(name, prop2Entites);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}

			}
		}
		String[] imageList = JSONUtil.getStringArray(data,
				Constants.JSONKeyName.ITEM_DETAIL_OBJ_KEY_IMAGELIST, null);
		LogUtil.d("json", "imageList=  " + imageList);
		for (String imageName : imageList) {
			ImageListEntity imageEntity = new ImageListEntity();
			imageEntity.setImageName(imageName);
			DataCache.ItemsImageListCache.add(imageEntity);
		}
		return true;
	}

	/**
	 * 解析用户评价信息
	 * 
	 * @param result
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseItemComments(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.ITEM_COMMENTS_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray listArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.ITEM_COMMENTS_JSON_ARRAY_KEY, null);
		if (listArray == null)
			return false;
		for (int i = 0; i < listArray.length(); i++) {
			try {
				JSONObject child = listArray.getJSONObject(i);
				String content = JSONUtil.getString(child,
						Constants.JSONKeyName.ITEM_COMMENTS_OBJ_KEY_CONTENT,
						null);
				String createdDate = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.ITEM_COMMENTS_OBJ_KEY_CREATEDDATE,
								null);
				/*
				 * String isUsernameHidden = JSONUtil .getString( child,
				 * Constants.JSONKeyName.ITEM_COMMENTS_OBJ_KEY_ISUSERNAMEHIDDEN,
				 * null);
				 */
				String nickname = JSONUtil.getString(child,
						Constants.JSONKeyName.ITEM_COMMENTS_OBJ_KEY_NICKNAME,
						null);
				double evaluation = JSONUtil.getDouble(child,
						Constants.JSONKeyName.ITEM_COMMENTS_OBJ_KEY_EVALUATION,
						0);
				String prop = JSONUtil.getString(child,
						Constants.JSONKeyName.ITEM_COMMENTS_OBJ_KEY_PROP, null);

				ItemCommentEntity entity = new ItemCommentEntity();
				entity.setContent(content);
				entity.setCreatedDate(createdDate);
				entity.setEvaluation(evaluation);
				// entity.setIsUsernameHidden(isUsernameHidden);
				entity.setNickname(nickname);
				entity.setProp(prop);
				DataCache.ItemCommentsListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析团购列表信息
	 * 
	 * @param result
	 *            json信息
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseGroupList(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.GROUPON_LIST_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray grouponArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.GROUPON_LIST_JSON_ARRAY_KEY, null);
		if (grouponArray == null)
			return false;
		for (int i = 0; i < grouponArray.length(); i++) {
			try {
				JSONObject child = grouponArray.getJSONObject(i);
				GrouponItemEntity entity = new GrouponItemEntity();
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.GROUPON_ITEM_OBJ_KEY_ID, 0);
				double listPrice = JSONUtil
						.getDouble(
								child,
								Constants.JSONKeyName.GROUPON_ITEM_OBJ_KEY_LISTPRICE,
								0);
				String image = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_ITEM_OBJ_KEY_IMAGE, null);
				String name = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_ITEM_OBJ_KEY_NAME, null);
				double plPrice = JSONUtil.getDouble(child,
						Constants.JSONKeyName.GROUPON_ITEM_OBJ_KEY_PLPRICE, 0);
				int sellNum = JSONUtil.getInt(child,
						Constants.JSONKeyName.GROUPON_ITEM_OBJ_KEY_SELLNUM, 0);
				entity.setId(id);
				entity.setListPrice(listPrice);
				entity.setName(name);
				entity.setImage(image);
				entity.setPlPrice(plPrice);
				entity.setSellNum(sellNum);
				DataCache.NavGrouponListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析用户信息
	 * 
	 * @param result
	 *            json信息 void
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseUserDataJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		long userId = JSONUtil.getLong(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_ID, 0);
		String TGC = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_TGC, null);
		String nickname = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NICKNAME, null);
		String username = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_USERNAME, null);
		String email = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_EMAIL, null);
		String mobile = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_MOBILE, null);
		String birthday = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_BIRTHDAY, null);
		String lastlogin = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_LASTLOGIN, null);
		String sex = JSONUtil.getString(data,
				Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_SEX, null);

		SharedPreferences pref = context.getSharedPreferences("profile",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_TGC, TGC);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NICKNAME,
				nickname);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_USERNAME,
				username);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_EMAIL,
				email);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_MOBILE,
				mobile);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_BIRTHDAY,
				birthday);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_LASTLOGIN,
				lastlogin);
		editor.putString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_SEX, sex);
		editor.commit();

		ProfileEntity entity = new ProfileEntity();

		entity.setBirthday(birthday);
		entity.setEmail(email);
		entity.setLastlogin(lastlogin);
		entity.setMobile(mobile);
		entity.setNickname(nickname);
		entity.setSex(sex);
		entity.setTGC(TGC);
		entity.setUsername(username);

		Constants.userId = userId;
		

		DataCache.UserDataCache.add(entity);

		return true;
	}

	/**
	 * 解析团购详情
	 * 
	 * @param context
	 * @param result
	 * @return
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseGrouponDetailDataJson(Context context,
			String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		String str = "";
		if (data == null)
			return false;
		String buyNotify = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_BUYNOTIFY, null);// 购买须知
		int buyNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_BUYNUM, null);// 已经团购数量
		int commentNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_COMMENTNUM, null);
		long startDate = JSONUtil.getLong(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_START_DATE,
				0);
		long countDownTime = JSONUtil.getLong(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_COUNTDOWNTIME,
				0);// 倒计时
		String content = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_CONTENT, null);// 团购内容
		long shopId = JSONUtil.getLong(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOP_ID,
				0);// 倒计时
		String shopName = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOP_NAME, null);
		String shopProp = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOP_PROP, null);
		String shopImage = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOP_IMAGE, null);
		double shopEvaluation = JSONUtil.getDouble(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOP_EVALUATION, 0);
		JSONArray image = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_IMAGE, null);// 图片地址
		if (image != null) {

			for (int i = 0; i < image.length(); i++) {
				JSONObject child;
				try {
					child = image.getJSONObject(i);
					ImageListEntity entity = new ImageListEntity();
					String img = JSONUtil.getString(child,
							Constants.JSONKeyName.GOODS_DETAIL_OBJ_KEY_PICPATH,
							null);// 图片地址
					entity.setImageName(img);
					DataCache.GrouponShopImgCache.add(entity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		String isPaidfor = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ISPAIDFOR, null);// 是否假一赔三
																				// 0
																				// 是
																				// 1否
		String isReturnItem = JSONUtil
				.getString(
						data,
						Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ISRETURNITEM,
						null);// 是否无理由退货 0 是 1否
		String isReturnMoney = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ISRETURNMONEY,
				null);// 是否无理由退货 0 是 1否
		String itemNum = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ITEMNUM, "0");// 包含商品数
		double listPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_LISTPRICE, 0);// 市场价
		String name = JSONUtil.getString(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_NAME, null);// 团购名称
		double plPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_PLPRICE, 0);// 价格
		int stat = JSONUtil.getInt(data, "stat", 0);
		JSONArray shopList = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOPLIST, null);// 适用商家信息
		if (shopList != null) {
			for (int i = 0; i < shopList.length(); i++) {
				JSONObject child;
				try {
					child = shopList.getJSONObject(i);
					GrouponDetailShopEntity entity = new GrouponDetailShopEntity();
					String listShopName = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOPLIST_NAME,
									null);// 店铺名称
					double evaluation = JSONUtil
							.getDouble(
									child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOPLIST_EVALUATION,
									0);// 店铺评级
					String prop = JSONUtil
							.getString(
									child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOPLIST_PROP,
									null);// 店铺属性
					long id = JSONUtil
							.getLong(
									child,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_SHOPLIST_ID,
									0);// 商家编号
					entity.setEvaluation(evaluation);
					entity.setName(listShopName);
					entity.setId(id);
					entity.setProp(prop);
					DataCache.GrouponShopCache.add(entity);
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		JSONArray itemGroupList = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ITEM_GROUP_LIST, null);
		if (itemGroupList != null) {
			
			for (int i = 0; i < itemGroupList.length(); i++) {
				JSONObject itemGroup;
				try{
					itemGroup = itemGroupList.getJSONObject(i);
					ItemGroupListEntity entity = new ItemGroupListEntity();
					long itemGroupId = JSONUtil
							.getLong(
									itemGroup,
									Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ITEM_GROUP_ID,
									0);
					entity.setItemGroupId(itemGroupId);
					DataCache.GrouponItemListCache.add(entity);
					
					JSONArray itemGroupItemList = JSONUtil.getJSONArray(itemGroup,
							Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ITEM_LIST, null);
					if (itemGroupItemList != null) {
						
						for (int j = 0; j < itemGroupItemList.length(); j++) {
							JSONObject itemGroupItem;
							 
							try{
								itemGroupItem = itemGroupItemList.getJSONObject(j);
								ItemGroupItemListEntity itemEntity = new ItemGroupItemListEntity();
								long itemGroupItmeId = JSONUtil
										.getLong(
												itemGroupItem,
												Constants.JSONKeyName.GROUPON_DETAIL_OBJ_KEY_ITEM_GROUP_ID,
												0);
								long id = JSONUtil
										.getLong(
												itemGroupItem,
												"id",
												0);
								String itemName = JSONUtil
										.getString(
												itemGroupItem,
												"itemName",
												null);
								double plPrice2 = JSONUtil
										.getDouble(
												itemGroupItem,
												"plPrice",
												0);
								int num = JSONUtil
										.getInt(
												itemGroupItem,
												"num",
												0);
								itemEntity.setItemGrouptId(itemGroupItmeId);
								itemEntity.setId(id);
								itemEntity.setItemName(itemName);
								itemEntity.setNum(num);
								itemEntity.setPlPrice(plPrice2);
								
								DataCache.itemGrouponItemListCache.add(itemEntity);
								
								
							}catch (JSONException e) {
								e.printStackTrace();
								return false;
							}
						}
					}
					
				}catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		
		GrouponDetailsEntity entity = new GrouponDetailsEntity();
		entity.setStat(stat);
		entity.setBuyNotify(buyNotify);
		entity.setBuyNum(buyNum);
		entity.setCommentNum(commentNum);
		entity.setContent(content);
		entity.setStartDate(startDate);
		entity.setCountDownTime(countDownTime);
		entity.setImage(null);
		entity.setIsPaidfor(isPaidfor);
		entity.setIsReturnItem(isReturnItem);
		entity.setItemNum(StringUtil.toInt(itemNum));
		entity.setListPrice(listPrice);
		entity.setName(name);
		entity.setPlPrice(plPrice);
		entity.setShopList(null);
		entity.setIsReturnMoney(isReturnMoney);
		entity.setShopId(shopId);
		entity.setShopEvaluation(shopEvaluation+"");
		entity.setShopImage(shopImage);
		entity.setShopName(shopName);
		entity.setShopProp(shopProp);
		if (str.length() > 0) {
			entity.setItemGroupList(str.substring(0, str.length()-1));
		} 
		

		DataCache.GrouponDetailCache.add(entity);

		return true;
	}

	/**
	 * 解析我的抵用券信息
	 * 
	 * @param context
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyBarginListJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null) {
			LogUtil.d("jsonparser", "parseMyBarginListJson return false 1");
			return false;
		}
		JSONArray array = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_BARGAINLIST,
				null);
		if (array == null) {
			LogUtil.d("jsonparser", "parseMyBarginListJson return false 3");
			return false;
		}
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject child = array.getJSONObject(i);
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_ID,
						0);
				String limitTime = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_LIMITTIME,
								null);
				double price = JSONUtil
						.getDouble(
								child,
								Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_PRICE,
								0);
				String prop = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_PROP,
								null);
				String shopName = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_RANGE,
								null);
				int stat = JSONUtil
						.getInt(child,
								Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_STAT,
								0);
				BarginListEntity entity = new BarginListEntity();
				entity.setBagainName(null);
				entity.setBargainId(id);
				entity.setLimitTime(limitTime);
				entity.setPrice(price);
				entity.setProp(prop);
				entity.setShopName(shopName);
				entity.setStat(stat);

				DataCache.MyBargainListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				LogUtil.d("jsonparser", "parseMyBarginListJson return false 3");
				return false;
			}
		}

		LogUtil.d("jsonparser", "parseMyBarginListJson return true");
		return true;
	}

	/**
	 * 解析数据缓存版本号
	 * 
	 * @param context
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseVesionCodeJson(Context context, String result) {
		return false;
	}

	/**
	 * 解析我的订单信息
	 * 
	 * @param context
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	/**	public static boolean parseMyOrderListJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray orderArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_KEY, null);
		if (orderArray == null)
			return false;
		// long orderId = 0;

		for (int i = 0; i < orderArray.length(); i++) {
			try {
				JSONObject child = orderArray.getJSONObject(i);
				int restNum = JSONUtil
						.getInt(child,
								Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_RESTNUM,
								0);// 订单未显示商品数量
				double allPrice = JSONUtil.getDouble(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ALLPRICE,
						0);// 订单总价格
				String orderStat = JSONUtil.getString(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ORDERSTAT,
						null);// 订单状态
				int orderType = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ORDERTYPE,
						0);// 订单类型 0：普通订单 1：团购订单
				int allNum = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ALLNUM, 0);// 订单类商品总数
				int totalNum = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_TOTALNUM,
						0);
				long orderId = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ID, 0);
				LogUtil.d("jsonparser", "parseMyOrderListJson id=" + orderId);

				JSONArray itemArray = JSONUtil
						.getJSONArray(
								child,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_ITEMLIST,
								null);
				if (itemArray != null) {
					ArrayList<ItemListEntity> itemEntities = new ArrayList<ItemListEntity>();
					for (int j = 0; j < itemArray.length(); j++) {
						ItemListEntity itemEntity = new ItemListEntity();
						JSONObject item;
						item = itemArray.getJSONObject(j);
						long itemId = JSONUtil
								.getLong(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMID,
										0);
						String image = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_IMAGE,
										null);
						String isGroupon = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMTYPE,
										null);
						String itemName = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMNAME,
										null);
						String plPrice = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PLPRICE,
										null);
						String listPrice = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_LISTPRICE,
										null);
						String prop = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PROP,
										null);
						int storeNum = JSONUtil
								.getInt(item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_STORENUM,
										0);
						int buyNum = JSONUtil
								.getInt(item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_BUYNUM,
										0);

						itemEntity.setItemId(itemId);
						itemEntity.setImage(image);
						itemEntity.setItemName(itemName);
						itemEntity.setIsGroupon(isGroupon);
						itemEntity.setListPrice(listPrice);
						itemEntity.setplPrice(plPrice);
						itemEntity.setStoreNum(storeNum);
						itemEntity.setBuyNum(buyNum);
						itemEntity.setProp(prop);

						itemEntities.add(itemEntity);
					}

					OrderListEntity entity = new OrderListEntity();
					entity.setId(orderId);
					entity.setAllNum(allNum);
					entity.setAllPrice(allPrice);
					entity.setOrderStat(orderStat);
					entity.setOrderType(orderType);
					entity.setRestNum(restNum);

					LogUtil.d("jsonparser",
							"itemEntities.size=" + itemEntities.size());

					DataCache.UserOrderListCache.add(entity);
					DataCache.UserOrderItemListCache.put(orderId, itemEntities);
					// orderId++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		LogUtil.d("jsonparser", "parseMyOrderListJson return true");
		return true;
	}*/
	
	/**
	 * 解析我的订单信息
	 * 
	 * @param context
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyOrderListJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray orderArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.ORDER_LIST_JSON_ARRAY_KEY, null);
		if (orderArray == null)
			return false;
		// long orderId = 0;

		for (int i = 0; i < orderArray.length(); i++) {
			try {
				JSONObject child = orderArray.getJSONObject(i);
				int restNum = JSONUtil
						.getInt(child,
								Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_RESTNUM,
								0);// 订单未显示商品数量
				double allPrice = JSONUtil.getDouble(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ALLPRICE,
						0);// 订单总价格
				String orderStat = JSONUtil.getString(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ORDERSTAT,
						null);// 订单状态
				int orderType = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ORDERTYPE,
						0);// 订单类型 0：普通订单 1：团购订单
				int allNum = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ALLNUM, 0);// 订单类商品总数
				int totalNum = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_TOTALNUM,
						0);
				long orderId = JSONUtil.getInt(child,
						Constants.JSONKeyName.ORDERLIST_JSON_OBJ_KEY_ID, 0);
				LogUtil.d("jsonparser", "parseMyOrderListJson id=" + orderId);

				JSONArray itemArray = JSONUtil
						.getJSONArray(
								child,
								Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_ITEMLIST,
								null);
				if (itemArray != null) {
					ArrayList<ItemListEntity> itemEntities = new ArrayList<ItemListEntity>();
					for (int j = 0; j < itemArray.length(); j++) {
						ItemListEntity itemEntity = new ItemListEntity();
						JSONObject item;
						item = itemArray.getJSONObject(j);
						long itemId = JSONUtil
								.getLong(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMID,
										0);
						String image = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_IMAGE,
										null);
						String isGroupon = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMTYPE,
										null);
						String itemName = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMNAME,
										null);
						String plPrice = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PLPRICE,
										null);
						String listPrice = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_LISTPRICE,
										null);
						String prop = JSONUtil
								.getString(
										item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PROP,
										null);
						int storeNum = JSONUtil
								.getInt(item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_STORENUM,
										0);
						int buyNum = JSONUtil
								.getInt(item,
										Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_BUYNUM,
										0);

						itemEntity.setItemId(itemId);
						itemEntity.setImage(image);
						itemEntity.setItemName(itemName);
						itemEntity.setIsGroupon(isGroupon);
						itemEntity.setListPrice(listPrice+"");
						itemEntity.setplPrice(plPrice+"");
						itemEntity.setStoreNum(storeNum);
						itemEntity.setBuyNum(buyNum);
						itemEntity.setProp(prop);

						itemEntities.add(itemEntity);
					}

					OrderListEntity entity = new OrderListEntity();
					entity.setId(orderId);
					entity.setAllNum(allNum);
					entity.setAllPrice(allPrice);
					entity.setOrderStat(orderStat);
					entity.setOrderType(orderType);
					entity.setRestNum(restNum);

					LogUtil.d("jsonparser",
							"itemEntities.size=" + itemEntities.size());

					DataCache.UserOrderListCache.add(entity);
					DataCache.UserOrderItemListCache.put(orderId, itemEntities);
					// orderId++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		LogUtil.d("jsonparser", "parseMyOrderListJson return true");
		return true;
	}

	/**
	 * 解析用户商品收藏信息
	 * 
	 * @param context
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseItemFavourListJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray itemArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_GOODS_LIST, null);
		if (itemArray == null) {
			return false;
		}
		for (int i = 0; i < itemArray.length(); i++) {
			try {
				JSONObject item = itemArray.getJSONObject(i);
				long favourId = JSONUtil.getLong(item,
						Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_FAVOURID,
						0);// 收藏编号
				long itemId = JSONUtil
						.getLong(
								item,
								Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_ITEMID,
								0);// 商品编号
				String picturePath = JSONUtil.getString(item,
						Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_PICPATH,
						null);// 收藏编号
				String itemName = JSONUtil.getString(item,
						Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_ITEMNAME,
						null);// 商品名称
				double listPrice = JSONUtil
						.getDouble(
								item,
								Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_LISTPRICE,
								0);// 原价
				double plPrice = JSONUtil.getDouble(item,
						Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_PLPRICE,
						0);// 销量
				String sellNum = JSONUtil.getString(item,
						Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_SELLNUM,
						null);// 折后价格
				double favourNum = JSONUtil
						.getDouble(
								item,
								Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_FAVOURNUM,
								0);// 收藏数
				String type = JSONUtil.getString(item,
						Constants.JSONKeyName.ITEM_FAVOURLIST_OBJ_KEY_TYPE,
						"商品");// 商品类型 0：普通商品 1：团购

				int itemType = type.equals("商品") ? 0 : 1;

				ItemFavourListEntity entity = new ItemFavourListEntity();
				entity.setFavourId(favourId);
				entity.setFavourNum(favourNum);
				entity.setItemId(itemId);
				entity.setItemName(itemName);
				entity.setListPrice(listPrice);
				entity.setPicturePath(picturePath);
				entity.setPlPrice(plPrice);
				entity.setSellNum(sellNum);
				entity.setType(itemType);

				DataCache.ItemFavourListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析用户店铺收藏信息
	 * 
	 * @param context
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseShopFavourListJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray itemArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.KEYWORDS_SEARCH_OBJ_KEY_SHOPS_LIST, null);
		if (itemArray == null) {
			return false;
		}
		for (int i = 0; i < itemArray.length(); i++) {
			try {
				JSONObject item = itemArray.getJSONObject(i);
				long favourId = JSONUtil.getLong(item,
						Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_FAVOURID,
						0);// 收藏编号
				long shopId = JSONUtil
						.getLong(
								item,
								Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_SHOPID,
								0);// 店铺编号
				String image = JSONUtil.getString(item,
						Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_IMAGE,
						null);// 收藏编号
				String shopName = JSONUtil.getString(item,
						Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_SHOPNAME,
						null);// 店铺名称
				String brief = JSONUtil.getString(item,
						Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_BRIEF,
						null);// 店铺简介
				long pageviewNum = JSONUtil
						.getLong(
								item,
								Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_PAGEVIEWNUM,
								0);// 浏览人数
				long favourNum = JSONUtil
						.getLong(
								item,
								Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_FAVOURNUM,
								0);// 收藏人数
				String classification = JSONUtil
						.getString(
								item,
								Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_CLASSFICATION,
								null);// 店铺分类
				double evaluation = JSONUtil
						.getDouble(
								item,
								Constants.JSONKeyName.SHOP_FAVOURLIST_OBJ_KEY_EVALUATION,
								0);// 店铺星级
				ShopFavourListEntity entity = new ShopFavourListEntity();
				entity.setFavourId(favourId);
				entity.setClassification(classification);
				entity.setEvaluation(evaluation);
				entity.setFavourNum(favourNum);
				entity.setImage(image);
				entity.setPageviewNum(pageviewNum);
				entity.setShopId(shopId);
				entity.setShopName(shopName);
				entity.setBrief(brief);

				DataCache.ShopFavourListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * parseAdvertisementJson(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param context
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static boolean parseAdvertisementJson(Context context, String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray seatArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.SEAT_LIST_JSON_ARRAY_KEY, null);
		if (seatArray == null) {
			return false;
		}
		
		DataCache.RecommendCache.clear();
		DataCache.BannerCache.clear();
		
		BannerDao bannerDao = new BannerDao(context);
		bannerDao.deleteAll();
		RecommendDao recommendDao = new RecommendDao(context);
		recommendDao.deleteAll();
		
		Constants.TEMPLATE_COLL.clear();
		Constants.VIEW_COUNT = 0;
		
		ArrayList<String> middleList = new ArrayList<String>();
		int middleValue = 0;
		for (int i = 0; i < seatArray.length(); i++) {
			try {
				JSONObject seatObj = seatArray.getJSONObject(i);
				int adHeight = JSONUtil.getInt(seatObj,
						Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_ADHEIGHT, 0);// 图片高度
				int adWidth = JSONUtil.getInt(seatObj,
						Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_ADWIDTH, 0);// 图片宽度
				String adType = JSONUtil.getString(seatObj,
						Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_ADTYPE, null);// 广告类型
																				// 0=单图片图片
																				// ,1=双图片,3=轮询图片,4=文字连接
				long channelId = JSONUtil.getLong(seatObj,
						Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_CHANNELID, 0);// 频道编号
				int channelOrder = JSONUtil
						.getInt(seatObj,
								Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_CHANNELORDER,
								0);// 顺序
				int id = JSONUtil.getInt(seatObj,
						Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_ID, 0);// 栏位编号
				JSONArray childArray = JSONUtil
						.getJSONArray(
								seatObj,
								Constants.JSONKeyName.SEAT_LIST_OBJ_KEY_ADVERTISEMENTLIST,
								null);
				if (childArray != null) {
					ArrayList<BannerEntity> bannerEntities = new ArrayList<BannerEntity>();
					ArrayList<RecommendEntity> recommendEntities = new ArrayList<RecommendEntity>();
					int middleIndex = 0;
					for (int j = 0; j < childArray.length(); j++) {
						JSONObject childObj = childArray.getJSONObject(j);
						String adDis = JSONUtil
								.getString(
										childObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_ADDIS,
										null);// 广告描述
						String adImage = JSONUtil
								.getString(
										childObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_ADIMAGE,
										null);// 广告位图片
						String adLink = JSONUtil
								.getString(
										childObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_ADLINCK,
										null);// link address
						String params = StringUtil
								.getAdvertiseLinkDataInfo(adLink);

						long adseatId = JSONUtil
								.getLong(
										childObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_ADSEATID,
										0);// 广告位编号
						int adOrder = JSONUtil
								.getInt(childObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_ADORDER,
										0);// 顺序
						String type = JSONUtil
								.getString(
										childObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_TYPE,
										"0");// 类型 0:链接；1：商家；2：商品；3：团购；4：活动
						LogUtil.d("parseAdvertisementJson", "type = " + type);
						int childId = JSONUtil
								.getInt(seatObj,
										Constants.JSONKeyName.ADVERTISE_LIST_OBJ_KEY_ID,
										0);//

						if (adType.equals("2")) {
							BannerEntity bannerEntity = new BannerEntity();
							bannerEntity.setAdHeight(adHeight);
							bannerEntity.setAdDis(adDis);
							bannerEntity.setAdImage(adImage);
							bannerEntity.setAdLink(adLink);
							bannerEntity.setAdOrder(adOrder);
							bannerEntity.setAdseatId(adseatId);
							bannerEntity.setId(childId);
							bannerEntity.setType(StringUtil.toInt(type));
							bannerEntity.setParams(params);
							bannerDao.add(bannerEntity);
							DataCache.BannerCache.add(bannerEntity);
						} else {
							RecommendEntity recommendEntity = new RecommendEntity();
							recommendEntity.setAdHeight(adHeight);
							recommendEntity.setAdDis(adDis);
							recommendEntity.setAdImage(adImage);
							recommendEntity.setAdLink(adLink);
							recommendEntity.setAdOrder(adOrder);
							recommendEntity.setAdseatId(adseatId);
							recommendEntity.setId(childId);
							recommendEntity.setLinkType(StringUtil.toInt(type));
							recommendEntity.setParams(params);
							if (adType.equals("0")) {
								// if(Constants.MIDDLE_INDEX == null)
								// Constants.MIDDLE_INDEX = new int[]{};
								// Constants.MIDDLE_INDEX[middleIndex] =
								// middleValue;
								// middleIndex++;
								Constants.TEMPLATE_COLL.add(middleValue);
								middleList.add(middleValue+"");
							}
							middleValue++;
							recommendDao.add(recommendEntity);
							DataCache.RecommendCache.add(recommendEntity);
							Constants.VIEW_COUNT++;
						}
					}
					SharedPreferences pref = context.getSharedPreferences("recommend",
							Context.MODE_PRIVATE);
					Editor editor = pref.edit();
					editor.putInt("view_count", Constants.VIEW_COUNT);
					String middleValues ="";
					for(int n=0;n<middleList.size();n++){
						if(n==0)
							middleValues += middleList.get(n);
						else
							middleValues += ","+middleList.get(n);
					}
					editor.putString("middle_value", middleValues);
					editor.commit();
				}

			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * 解析我的信息列表
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyMsgListJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.MESSAGE_LIST_JSON_ARRAY_KEY, null);
		if (msgArray == null)
			return false;
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				String content = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_CONTENT,
								"");// 消息内容
				String createDate = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_CREATEDATE,
								"");// 消息创建时间
				String sender = JSONUtil.getString(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_SENDER,
						"");// 发送人
				String image = JSONUtil.getString(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_IMGAE,
						"");// 发送方 0：自己发 1：他人发
				long shopId = JSONUtil.getLong(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_SHOPID,
						0);// 店家ID

				ChatMsgListEntity entity = new ChatMsgListEntity();
				entity.setContent(content);
				entity.setCreateDate(createDate);
				entity.setImage(image);
				entity.setSender(sender);
				entity.setShopId(shopId);

				DataCache.MyMsgListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析订单详情
	 * 
	 * @param context
	 * @param result
	 * @param orderId
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyOrderDetailJson(Context context,
			String result, long orderId) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;

		ArrayList<OrderDetailEntity> orderDetailEntities = new ArrayList<OrderDetailEntity>();
		String address = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_ADDRESS, null);
		double allPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_ALLPRICE, 0);
		double freight = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_FREIGHT, 0);
		double listPrice = JSONUtil.getDouble(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_LISTPRICE, 0);
		String receiveTime = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_RECEIVEDTIME,
				null);
		String receiver = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_RECEIVER, null);
		String telNum = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_TELNUM, null);
		int allNum = JSONUtil.getInt(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_ALLNUM, null);// 商品总数
		String logistics = JSONUtil
				.getString(
						data,
						Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_LOGISTICS,
						null);// 最近一个物流状态
		String logisticsId = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_LOGISTICSID,
				null);// 物流编号
		String logisticsTime = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_LOGISTICSTIME,
				null);// 最近一个物流状态时间
		String orderNo = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_ORDERNO, null);// 订单编号
		String orderStat = JSONUtil
				.getString(
						data,
						Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_ORDERSTAT,
						null);// 订单状态
		String pakageNum = JSONUtil
				.getString(
						data,
						Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_PAKAGENUM,
						null);// 包裹数
		String pay = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_PAY, null);// 支付方式描述
		String payId = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_PAYID, null);// 支付方式编号
		String payNo = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_PAYNO, null);// 支付宝订单号
		String receivedTime = JSONUtil.getString(data,
				Constants.JSONKeyName.ORDER_DETAIL_JSON_OBJ_KEY_RECEIVEDTIME,
				null);// 收货时间
		OrderDetailEntity orderEntity = new OrderDetailEntity();
		orderEntity.setAddress(address);
		orderEntity.setAllNum(allNum);
		orderEntity.setAllPrice(allPrice);
		orderEntity.setFreight(freight);
		orderEntity.setListPrice(listPrice);
		orderEntity.setLogistics(logistics);
		orderEntity.setLogisticsId(logisticsId);
		orderEntity.setLogisticsTime(logisticsTime);
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderStat(orderStat);
		orderEntity.setPakageNum(pakageNum);
		orderEntity.setPay(pay);
		orderEntity.setPayId(payId);
		orderEntity.setPayNo(payNo);
		orderEntity.setReceivedTime(receivedTime);
		orderEntity.setReceiver(receiver);
		orderEntity.setReceiveTime(receiveTime);
		orderEntity.setTel(telNum);

	    orderDetailEntities.add(orderEntity);

		DataCache.UserOrderDetailCache.add(orderEntity);
 
		JSONArray shopArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.CART_LIST_JSON_ARRAY_KEYNAME, null);
		if (shopArray != null) {
			for (int i = 0; i < shopArray.length(); i++) {
				CartShopListEntity shopEntity = new CartShopListEntity();
				JSONObject shop;
				try {
					shop = shopArray.getJSONObject(i);
					int shopId = JSONUtil
							.getInt(shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_SHOPID,
									0);
					String shopName = JSONUtil
							.getString(
									shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_SHOPNAME,
									null);
					String tel = JSONUtil
							.getString(
									shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_OBJ_KEY_TEL,
									null);

					shopEntity.setShopId(shopId);
					shopEntity.setTel(tel);
					shopEntity.setShopName(shopName);

					DataCache.UserOrderDetailShopListCache.add(shopEntity);

					ArrayList<ItemListEntity> itemEntities = new ArrayList<ItemListEntity>();
					JSONArray itemArray = JSONUtil
							.getJSONArray(
									shop,
									Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_ITEMLIST,
									null);
					if (itemArray != null) {
						for (int j = 0; j < itemArray.length(); j++) {
							ItemListEntity itemEntity = new ItemListEntity();
							JSONObject item;
							try {
								item = itemArray.getJSONObject(j);
								long itemId = JSONUtil
										.getLong(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMID,
												0);
								String image = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_IMAGE,
												null);
								String isGroupon = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMTYPE,
												null);
								String itemName = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMNAME,
												null);
								String plPrice = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_PLPRICE,
												null);
								String listItemPrice = JSONUtil
										.getString(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_LISTPRICE,
												null);
								int storeNum = JSONUtil
										.getInt(item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_STORENUM,
												0);
								int buyNum = JSONUtil
										.getInt(item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_BUYNUM,
												0);
								itemEntity.setItemId(itemId);
								itemEntity.setImage(image);
								itemEntity.setItemName(itemName);
								itemEntity.setIsGroupon(isGroupon);
								itemEntity.setListPrice(listItemPrice+"");
								itemEntity.setplPrice(plPrice+"");
								itemEntity.setStoreNum(storeNum);
								itemEntity.setBuyNum(buyNum);

								itemEntities.add(itemEntity);
								
								ArrayList<ItemBargainListEntity> itemBargainEntities = new ArrayList<ItemBargainListEntity>();
								JSONArray itemBargainArray = JSONUtil
										.getJSONArray(
												item,
												Constants.JSONKeyName.ITEMLIST_JSON_OBJ_KEY_ITEMBARGAINLIST,
												null);
								if (itemBargainArray != null) {
									for (int k = 0; k < itemBargainArray.length(); k++) {
										ItemBargainListEntity itemBargainEntity = new ItemBargainListEntity();
										JSONObject itemBargain;
										try {
											itemBargain = itemBargainArray.getJSONObject(k);
											String bargainName = JSONUtil
													.getString(
															itemBargain,
															Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_NAME,
															null);
											String type = JSONUtil
													.getString(
															itemBargain,
															Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_TYPE,
															null);
											itemBargainEntity.setName(bargainName);
											itemBargainEntity.setType(type);
											itemBargainEntities.add(itemBargainEntity);
										} catch (JSONException e) {
											e.printStackTrace();
											return false;
										}
										
									}
									int id = new Long(itemId).intValue();
									DataCache.orderItemBargainListCache.put(id
											, itemBargainEntities);
								}
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}

							DataCache.UserOrderDetailItemListCache.put(shopId,
									itemEntities);
						}
						ArrayList<ItemBargainListEntity> bargainEntities = new ArrayList<ItemBargainListEntity>();
						JSONArray bargainArray = JSONUtil
								.getJSONArray(
										shop,
										Constants.JSONKeyName.CART_SHOPLIST_JSON_ARRAY_KEY_BARGAINLIST,
										null);
						if (bargainArray != null) {
							for (int k = 0; k < bargainArray.length(); k++) {
								ItemBargainListEntity bargainEntity = new ItemBargainListEntity();
								JSONObject bargain;
								try {
									bargain = bargainArray.getJSONObject(k);
									String bargainName = JSONUtil
											.getString(
													bargain,
													Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_NAME,
													null);
									String type = JSONUtil
											.getString(
													bargain,
													Constants.JSONKeyName.CART_BARGAINLIST_JSON_OBJ_KEY_TYPE,
													null);
									bargainEntity.setName(bargainName);
									bargainEntity.setType(type);
									bargainEntities.add(bargainEntity);
								} catch (JSONException e) {
									e.printStackTrace();
									return false;
								}
							}

							DataCache.UserOrderDetailBagainListCache.put(
									shopId, bargainEntities);
						}

						ArrayList<CashCouponEntity> cashCouponEntities = new ArrayList<CashCouponEntity>();
						JSONArray cashCouponArray = JSONUtil
								.getJSONArray(
										shop,
										Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_ARRAY_KEY,
										null);
						if (cashCouponArray != null) {
							for (int h = 0; h < cashCouponArray.length(); h++) {
								CashCouponEntity cashCouponEntity = new CashCouponEntity();
								JSONObject cashCouponObj;
								try {
									cashCouponObj = cashCouponArray
											.getJSONObject(h);
									String cashCouponName = JSONUtil
											.getString(
													cashCouponObj,
													Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_NAME,
													null);
									int cashCouponId = JSONUtil
											.getInt(cashCouponObj,
													Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_ID,
													0);
									double cashCouponPrice = JSONUtil
											.getDouble(
													cashCouponObj,
													Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_PRICE,
													0);
									cashCouponEntity.setName(cashCouponName);
									cashCouponEntity.setCouponId(cashCouponId);
									cashCouponEntity.setPrice(cashCouponPrice);

									cashCouponEntities.add(cashCouponEntity);
								} catch (JSONException e) {
									e.printStackTrace();
									return false;
								}
							}

							DataCache.UserOrderDetailCashCouponListCache.put(
									shopId, cashCouponEntities);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		JSONArray plCashArray = JSONUtil
				.getJSONArray(
						data,
						Constants.JSONKeyName.ORDER_DETAIL_JSON_ARRAY_KEY_PLCASHCOUPONLIST,
						null);
		if (plCashArray != null) {
			ArrayList<CashCouponEntity> plCashCouponEntities = new ArrayList<CashCouponEntity>();
			for (int j = 0; j < plCashArray.length(); j++) {
				CashCouponEntity cashCouponEntity = new CashCouponEntity();
				JSONObject cashCouponObj;
				try {
					cashCouponObj = plCashArray.getJSONObject(j);
					String cashCouponName = JSONUtil
							.getString(
									cashCouponObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_NAME,
									null);
					int cashCouponId = JSONUtil
							.getInt(cashCouponObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_ID,
									0);
					double cashCouponPrice = JSONUtil
							.getDouble(
									cashCouponObj,
									Constants.JSONKeyName.CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_PRICE,
									0);
					cashCouponEntity.setName(cashCouponName);
					cashCouponEntity.setCouponId(cashCouponId);
					cashCouponEntity.setPrice(cashCouponPrice);

					plCashCouponEntities.add(cashCouponEntity);
					
					int id = Integer.parseInt(orderId+"");
					DataCache.UserOrderDetailPlCashCouponListCache.put(id,
							plCashCouponEntities);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 解析消息详情
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyMsgDetailJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.MESSAGE_LIST_JSON_ARRAY_KEY, null);
		if (msgArray == null)
			return false;
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				String content = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_CONTENT,
								"");// 消息内容
				String createDate = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_CREATEDATE,
								"");// 消息创建时间
				String sender = JSONUtil.getString(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_SENDER,
						"");// 发送人
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_ID, 0);// 店家ID
				int type = JSONUtil
						.getInt(child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_TYPE,
								0);
				
				String image = JSONUtil.getString(child,
						"image",
						"");// 发送人
				LogUtil.d("parseMyMsgDetailJson", "content = " + content);
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setContent(content);
				entity.setCreateDate(createDate);
				entity.setSender(sender);
				entity.setId(id);
				entity.setType(type);
				entity.setImage(image);

				DataCache.MyMsgDetailCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析我的通知列表信息
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyNotifyJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.NOTIFY_LIST_JSON_ARRAY_KEY, null);
		if (msgArray == null)
			return false;
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				String content = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_CONTENT,
								"");// 消息内容
				String createDate = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.NOTIFY_LIST_JSON_OBJ_KEY_CREATEDATE,
								"");// 消息创建时间
				String sender = JSONUtil.getString(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_SENDER,
						"");// 发送人
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_ID, 0);// 店家ID
				int type = JSONUtil
						.getInt(child,
								Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_TYPE,
								0);
				String image = JSONUtil.getString(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_IMGAE,
						"");// 发送方 0：自己发 1：他人发

				NotifyListEntity entity = new NotifyListEntity();
				entity.setContent(content);
				entity.setCreateDate(createDate);
				entity.setSender(sender);
				entity.setId(id);
				entity.setType(type);
				entity.setImage(image);

				DataCache.MyNotifyListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析我的团购券列表
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseMyGrouponCouponJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray groupArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.GROUPON_COUPON_JSON_ARRAY_KEY, null);
		if (groupArray == null)
			return false;
		for (int i = 0; i < groupArray.length(); i++) {
			try {
				JSONObject child = groupArray.getJSONObject(i);
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.GROUPON_COUPON_JSON_KEY_ID, 0);// 编号
				String name = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_COUPON_JSON_KEY_NAME, "");// 名称
				String limitTime = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.GROUPON_COUPON_JSON_KEY_LIMITTIME,
								"");// 有效期
				String grouponName = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_COUPON_JSON_KEY_GROUPON_NAME, "");
				String grouponTicketCode = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_COUPON_JSON_KEY_TICKET_CODE, "");
				String updateDate = JSONUtil.getString(child,
						Constants.JSONKeyName.GROUPON_COUPON_JSON_KEY_UPDATE_DATE, "");

				GrouponCouponEntity entity = new GrouponCouponEntity();
				entity.setId(id);
				entity.setLimitTime(limitTime);
				entity.setName(name);
				entity.setGrouponName(grouponName);
				entity.setGrouponTicketCode(grouponTicketCode);
				entity.setUpdateDate(updateDate);

				DataCache.MyGrouponCouponListCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * parseUnReadMsgJson(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseUnReadMsgJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		Constants.unReadNotifyNum = JSONUtil.getInt(data, "unReadNotifyNum", 0);
		Constants.unReadMessageNum = JSONUtil.getInt(data, "unReadMessageNum",
				0);
		Constants.userIcon = JSONUtil.getString(data, "userIcon", "");
		Constants.userName = JSONUtil.getString(data, "userName", "");
		Constants.nickName = JSONUtil.getString(data, "nickName", "");
		Constants.id = JSONUtil.getInt(data, "id", 0);

		return true;
	}
	
	
	/**
	 * parseUnReadMsgJson(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param result
	 * @return boolean
	 * @exception"finishCount":11,"noPayCount":0,"waitRecvCount":0,"waitSendCount
	 * @since 1.0.0
	 */
	public static boolean parsePayMsgJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		Constants.finishCount = JSONUtil.getInt(data, "finishCount", 0);
		Constants.noPayCount = JSONUtil.getInt(data, "noPayCount", 0);
		Constants.waitRecvCount = JSONUtil.getInt(data, "waitRecvCount", 0);
		Constants.waitSendCount = JSONUtil.getInt(data, "waitSendCount", 0);
		
		return true;
	}

	/**
	 * 解析附近的店铺查询信息
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseNearbyShopJson(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.NEARBY_SHOPLIST, null);
		if (msgArray == null)
			return false;
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				long id = JSONUtil.getLong(child,
						Constants.JSONKeyName.MESSAGE_LIST_JSON_OBJ_KEY_SHOPID,
						0);
				String picturePath = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SHOP_PICTURE_PATH, "");//
				String shopName = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SHOP__NAME, "");//
				String brief = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SHOP_BRIEF, "");//
				String classification = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SHOP_CLASSIFICATION, "");//
				double evaluation = JSONUtil.getDouble(child,
						Constants.JSONKeyName.NEARBY_SHOP_EVALUATION, 0);
				String notice = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SHOP_NOTICE, "");//

				NearbyShopEntity entity = new NearbyShopEntity();
				entity.setId(id);
				entity.setPicturePath(picturePath);
				entity.setShopName(shopName);
				entity.setBrief(brief);
				entity.setClassification(classification);
				entity.setEvaluation(evaluation);
				entity.setNotice(notice);

				DataCache.NearbyShopCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析附近查询的店铺信息
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseNearbySearchJson(String result) {
		DataCache.NearbySearchCache.clear();
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.NEARBY_SEARCH_SHOPLIST, null);
		if (msgArray == null)
			return false;
		
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				long shopid = JSONUtil.getLong(child,
						Constants.JSONKeyName.NEARBY_SEARCH_SHOPID,
						0);
				String floor = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_FLOOR, "");//
				String shopName = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_SHOP_NAME, "");//
				String className = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_CLASS_NAME, "");//
				String locx = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_LOC_X, "");//
				String locy = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_LOC_Y, "");
				String storeNo = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_STORE_NUM, "");//
				String locate = JSONUtil.getString(child,
						Constants.JSONKeyName.NEARBY_SEARCH_LOCATE, "");//
				long floorid = JSONUtil.getLong(child,
						Constants.JSONKeyName.NEARBY_SEARCH_FLOOR_ID,
						0);

				NearbySearchEntity entity = new NearbySearchEntity();
				entity.setShopid(shopid);
				entity.setFloor(floor);
				entity.setShopname(shopName);
				entity.setClassname(className);
				entity.setLocx(locx);
				entity.setLocy(locy);
				entity.setStoreNo(storeNo);
				entity.setLocate(locate);
				entity.setFloorid(floorid);

				DataCache.NearbySearchCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析查询区域信息
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseQueryAddressJson(String result) {
		DataCache.AddressQueryCache.clear();
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.QUERY_ADDRESS_COMMUNITY_LIST, null);
		if (msgArray == null)
			return false;
		
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				int id = JSONUtil.getInt(child,
						Constants.JSONKeyName.QUERY_ADDRESS_ID,
						0);
				String name = JSONUtil.getString(child,
						Constants.JSONKeyName.QUERY_ADDRESS_NAME, "");//
				String province = JSONUtil.getString(child,
						Constants.JSONKeyName.QUERY_ADDRESS_PROVINCE, "");//
				String city = JSONUtil.getString(child,
						Constants.JSONKeyName.QUERY_ADDRESS_CITY, "");//
				String area = JSONUtil.getString(child,
						Constants.JSONKeyName.QUERY_ADDRESS_AREA, "");//
				String zipCode = JSONUtil.getString(child,
						Constants.JSONKeyName.QUERY_ADDRESS_ZIPCODE, "");

				AddressQueryEntity entity = new AddressQueryEntity();
				entity.setId(id);
				entity.setName(name);
				entity.setProvince(province);
				entity.setCity(city);
				entity.setArea(area);
				entity.setZipCode(zipCode);

				DataCache.AddressQueryCache.add(entity);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析版本号
	 * 
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseVersionCode(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.JSON_OBJ_KEY_VERSION_CODE_LIST, null);
		if (msgArray == null)
			return false;
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				String versionCode = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.JSON_OBJ_KEY_VERSION_CODE,
								"");// 消息内容
				DataCache.versionCode = versionCode;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean parseAddOrder(String result) {
		JSONObject data = JSONUtil.getJSONObject(result,
				Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
		if (data == null)
			return false;
		JSONArray msgArray = JSONUtil.getJSONArray(data,
				Constants.JSONKeyName.JSON_OBJ_KEY_VERSION_CODE_LIST, null);
		if (msgArray == null)
			return false;
		for (int i = 0; i < msgArray.length(); i++) {
			try {
				JSONObject child = msgArray.getJSONObject(i);
				String versionCode = JSONUtil
						.getString(
								child,
								Constants.JSONKeyName.JSON_OBJ_KEY_VERSION_CODE,
								"");// 消息内容
				DataCache.versionCode = versionCode;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析当前城市
	 * @param result
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static String parseCity(String result) {
		String city = "";
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			JSONObject jsonArrayComponents = (JSONObject) jsonArray.get(0);
			JSONArray jsonArrayCity = jsonArrayComponents.getJSONArray("address_components");
			for (int i = 0; i < jsonArrayCity.length(); i ++) {
				JSONObject jsonChild = (JSONObject) jsonArrayCity.get(i);
				JSONArray strArray = (JSONArray) jsonChild.get("types");
				if ("locality".equals(strArray.get(0))) {
					city = jsonChild.getString("short_name");
					break;
				}
			}
		} catch (JSONException e) {
			city = "";
		}
		return city;
	}
}
