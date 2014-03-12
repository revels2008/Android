/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * DataCache.java
 * 
 * 2013-8-6-下午02:16:14
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;

import com.powerlong.electric.app.domain.DomainShopDetailCategory;
import com.powerlong.electric.app.entity.ActivityDetailEntity;
import com.powerlong.electric.app.entity.ActivityItemEntity;
import com.powerlong.electric.app.entity.AddOrderEntity;
import com.powerlong.electric.app.entity.AddressQueryEntity;
import com.powerlong.electric.app.entity.BannerEntity;
import com.powerlong.electric.app.entity.BarginListEntity;
import com.powerlong.electric.app.entity.BrandListEntity;
import com.powerlong.electric.app.entity.CartCountEntity;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.CashCouponEntity;
import com.powerlong.electric.app.entity.Categeories;
import com.powerlong.electric.app.entity.ChatMsgEntity;
import com.powerlong.electric.app.entity.ChatMsgListEntity;
import com.powerlong.electric.app.entity.DateTimeEntity;
import com.powerlong.electric.app.entity.FilterEntity;
import com.powerlong.electric.app.entity.GrouponCouponEntity;
import com.powerlong.electric.app.entity.GrouponDetailShopEntity;
import com.powerlong.electric.app.entity.GrouponDetailsEntity;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.entity.ImageListEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ItemCommentEntity;
import com.powerlong.electric.app.entity.ItemDetailEntity;
import com.powerlong.electric.app.entity.ItemFavourListEntity;
import com.powerlong.electric.app.entity.ItemGroupItemListEntity;
import com.powerlong.electric.app.entity.ItemGroupListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
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
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.powerlong.electric.app.entity.SearchEntity;
import com.powerlong.electric.app.entity.SearchHistoryEntity;
import com.powerlong.electric.app.entity.SearchResultEntity;
import com.powerlong.electric.app.entity.ShopDetailsEntity;
import com.powerlong.electric.app.entity.ShopFavourListEntity;
import com.powerlong.electric.app.entity.ShopItemListEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.ui.model.Mall;

/**
 * 
 * DataCache
 * 
 * @author: Liang Wang 2013-8-6-下午02:16:14
 * 
 * @version 1.0.0
 * 
 */
public class DataCache {
	public static List<SearchEntity> SearchListEntity = new ArrayList<SearchEntity>();
	public static List<SearchCategoryEntity> SearchListEntityNew = new ArrayList<SearchCategoryEntity>();
	public static List<SearchHistoryEntity> SearchHistroyEntity = new ArrayList<SearchHistoryEntity>();
	public static List<SearchResultEntity> SearchResultEntity = new ArrayList<SearchResultEntity>();
	public static List<String> SearchDetailsEntity = new ArrayList<String>();
	public static List<Categeories> ExSearchListEntity = new ArrayList<Categeories>();
	public static HashMap<Integer,FilterEntity>  ExShopServiceChecked = new HashMap<Integer,FilterEntity>();
	public static HashMap<Integer,FilterEntity>  ExBarginChecked = new HashMap<Integer,FilterEntity>();
	public static HashMap<Integer,FilterEntity>  ExPriceChecked = new HashMap<Integer,FilterEntity>();
	
	/**
	 * 搜索店铺缓存
	 */
	public static ArrayList<NavigationFloorDetailsEntity>  SearchShopResultCache = new ArrayList<NavigationFloorDetailsEntity>();
	/**
	 * 导航项目缓存
	 */
	public static HashMap<String,ArrayList<NavigationBaseEntity>>  NavItemCache = new HashMap<String, ArrayList<NavigationBaseEntity>>();
	/**
	 * 品牌列表缓存
	 */
	public static ArrayList<BrandListEntity> BrandListCache = new ArrayList<BrandListEntity>();
	/**
	 * 店铺详情缓存
	 */
	public static ArrayList<ShopDetailsEntity> ShopDetailsCache = new ArrayList<ShopDetailsEntity>();
	public static String SHOP_DETAIL_ALL = null;
	/**
	 * 店铺商品列表缓存
	 */
	public static ArrayList<ShopItemListEntity> ShopItemListCache = new ArrayList<ShopItemListEntity>();
	/**
	 * 类目列表缓存
	 */
	public static ArrayList<DomainShopDetailCategory> CategoryListCache = new ArrayList<DomainShopDetailCategory>();
	/**
	 * 商品详情缓存
	 */
	public static ArrayList<ItemDetailEntity> ItemsDetailsCache = new ArrayList<ItemDetailEntity>();
	
	/**
	 * 商品详情优惠活动列表缓存
	 */
	public static ArrayList<BarginListEntity> ItemsBarginListCache = new ArrayList<BarginListEntity>();
	/**
	 * 商品详情图片列表缓存
	 */
	public static ArrayList<ImageListEntity> ItemsImageListCache = new ArrayList<ImageListEntity>();
	
	/**
	 * 商品详情商品规格1列表缓存
	 */
	public static ArrayList<PropEntity> ItemsPropList1Cache = new ArrayList<PropEntity>();
	/**
	 * 商品详情商品规格2列表缓存  hashKEY: PROP1's Name
	 */
	public static HashMap<String,ArrayList<PropEntity>> ItemsPropList2Cache = new HashMap<String, ArrayList<PropEntity>>();
	/**
	 * 商品用户评价详情
	 * 
	 */
	public static ArrayList<ItemCommentEntity> ItemCommentsListCache = new ArrayList<ItemCommentEntity>();
	/**
	 * 团购列表缓存
	 */
	public static ArrayList<GrouponItemEntity> NavGrouponListCache = new ArrayList<GrouponItemEntity>();
	/**
	 * 团购详情缓存
	 */
	public static ArrayList<GrouponDetailsEntity> GrouponDetailCache = new ArrayList<GrouponDetailsEntity>();
	/**
	 * 团购详情店铺信息
	 */
	public static ArrayList<GrouponDetailShopEntity> GrouponShopCache = new ArrayList<GrouponDetailShopEntity>();
	/**
	 * 团购详情商品集
	 */
	public static ArrayList<ItemGroupListEntity> GrouponItemListCache = new ArrayList<ItemGroupListEntity>();
	/**
	 * 团购详情商品集商品列表
	 */
	public static ArrayList<ItemGroupItemListEntity> itemGrouponItemListCache = new ArrayList<ItemGroupItemListEntity>();
	/**
	 * 团购详情店铺图片
	 */
	public static ArrayList<ImageListEntity> GrouponShopImgCache = new ArrayList<ImageListEntity>();
	/** 
	 * 我的订单列表缓存
	 */
	public static ArrayList<BarginListEntity> MyBargainListCache = new ArrayList<BarginListEntity>();
	
	/**
	 * 活动列表缓存
	 */
	public static ArrayList<ActivityItemEntity> NavActivityListCache = new ArrayList<ActivityItemEntity>();
	/**
	 * 活动详细缓存
	 */
	public static HashMap<Long,ActivityDetailEntity> NavActivityDetaillsCache = new HashMap<Long,ActivityDetailEntity>();
	/**
	 * 活动详细图片列表缓存
	 */
	public static HashMap<Long,ArrayList<ImageListEntity>> NavActivityImageListCache = new HashMap<Long,ArrayList<ImageListEntity>>();
	
	/**
	 * 活动列表缓存
	 */
	public static ArrayList<NavigationActivityEntity> ActivityListCache = new ArrayList<NavigationActivityEntity>();
	/**
	 * 楼层详细缓存
	 */
	public static ArrayList<NavigationFloorDetailsEntity> NavFloorDetailsCache = new ArrayList<NavigationFloorDetailsEntity>();
	/**
	 * 品牌详细缓存
	 */
	public static ArrayList<NavigationBrandDetailsEntity> NavBrandDetailsCache = new ArrayList<NavigationBrandDetailsEntity>();
	
	/**
	 * 团购详细缓存
	 */
	public static ArrayList<NavigationGrouponDetailsEntity> NavGrouponDetailsCache = new ArrayList<NavigationGrouponDetailsEntity>();
	/**
	 * 搜索大类缓存
	 */
	public static ArrayList<SearchCategoryEntity> SearchCategoriesCache = new ArrayList<SearchCategoryEntity>();
	/**
	 * 搜索大类明细缓存
	 */
//	public static HashMap<Long,ArrayList<SearchCategoryDetailEntity>> SearchCategoryDetailCache = new HashMap<Long, ArrayList<SearchCategoryDetailEntity>>();
	/**
	 * 购物车列表缓存
	 */
	public static double totalPrice = 0;
	/**
	 * 购物车店铺列表缓存
	 */
	public static List<CartShopListEntity> CartShopListCache = new ArrayList<CartShopListEntity>();
	/**
	 * 购物车结算信息缓存
	 */
	public static List<CartCountEntity> CartCountEntityCache = new ArrayList<CartCountEntity>();
	/**
	 * 下订单获取订单列表
	 */
	public static List<AddOrderEntity> ADDORDEREntityCache = new ArrayList<AddOrderEntity>();
	
	/**CartCountEntity
	 * 购物车结算店铺列表缓存
	 */
	public static List<CartShopListEntity> CartCountShopListCache = new ArrayList<CartShopListEntity>();
	/**店铺优惠券列表缓存
	 * 
	 */
	public static HashMap<Integer, ArrayList<BarginListEntity>> CartBagainListCache = new HashMap<Integer, ArrayList<BarginListEntity>>();
	/**
	 * 购物车结算优惠券列表缓存
	 */
	public static HashMap<Integer, ArrayList<BarginListEntity>> CartCountBagainListCache = new HashMap<Integer, ArrayList<BarginListEntity>>();
	/**
	 * 店铺已放入购物车商品列表缓存
	 */
	public static HashMap<Integer, ArrayList<ItemListEntity>> CartItemListCache = new HashMap<Integer, ArrayList<ItemListEntity>>();
	/**
	 * 店铺已选购商品列表缓存
	 */
	public static HashMap<Integer, ArrayList<ItemListEntity>> CarCountItemListCache = new HashMap<Integer, ArrayList<ItemListEntity>>();
	/**
	 * 店铺赠品列表缓存
	 */
	public static HashMap<Integer, ArrayList<PresentEntity>> ShopPresentListCache = new HashMap<Integer, ArrayList<PresentEntity>>();
	/**
	 * 店铺抵用券列表缓存
	 */
	public static HashMap<Integer, ArrayList<CashCouponEntity>> CashCouponListCache = new HashMap<Integer, ArrayList<CashCouponEntity>>();
	/**
	 * 商品优惠信息列表
	 */
	public static HashMap<Integer, ArrayList<ItemBargainListEntity>> itemBargainListCache = new HashMap<Integer, ArrayList<ItemBargainListEntity>>();
	/**
	 * 送货日期
	 */
	public static ArrayList<DateTimeEntity> dateListCache = new ArrayList<DateTimeEntity>();	
	/**
	 * 送货时间段
	 */
	public static HashMap<String, ArrayList<DateTimeEntity>> timeListCache = new HashMap<String, ArrayList<DateTimeEntity>>();
	/**
	 * 商品配送方式列表缓存
	 */
	public static ArrayList<LogisticsEntity> LogisticListCache = new ArrayList<LogisticsEntity>();
	/**
	 * 支付方式列表缓存
	 */
	public static ArrayList<PayEntity> payListCache = new ArrayList<PayEntity>();
	/**
	 * 宝龙抵用券列表缓存
	 */
	public static List<PlCashCouponEntity>PLCashCouponListCache = new ArrayList<PlCashCouponEntity>();
	/**
	 * 用户地址列表缓存
	 */
	public static List<UserAddressEntity>UserAddressListCache = new ArrayList<UserAddressEntity>();
	/**
	 * 读取区域信息
	 */
	public static ArrayList<AddressQueryEntity> AddressQueryCache = new ArrayList<AddressQueryEntity>();
	
	/**
	 * 用户信息缓存
	 */
	public static List<ProfileEntity> UserDataCache = new ArrayList<ProfileEntity>();
	
	/**
	 * 用户订单列表缓存
	 */
	public static List<OrderListEntity> UserOrderListCache = new ArrayList<OrderListEntity>();
	/**
	 * 用户订单详情缓存 根据订单号取订单基本信息
	 */
	public static  ArrayList<OrderDetailEntity> UserOrderDetailCache =  new ArrayList<OrderDetailEntity>();
	/**
	 *  * 用户订单详情店铺列表缓存 根据订单号取店铺信息
	 */
	public static  ArrayList<CartShopListEntity>UserOrderDetailShopListCache = new ArrayList<CartShopListEntity>();
	/**
	 * 用户订单详情店铺商品列表缓存 根据店铺号查询
	 */
	public static HashMap<Integer, ArrayList<ItemListEntity>>  UserOrderDetailItemListCache = new HashMap<Integer, ArrayList<ItemListEntity>>();
	/**
	 * 用户订单详情优惠券列表缓存
	 */
	public static HashMap<Integer, ArrayList<ItemBargainListEntity>>  UserOrderDetailBagainListCache = new HashMap<Integer, ArrayList<ItemBargainListEntity>>();
	/**
	 * 訂單商品优惠信息列表
	 */
	public static HashMap<Integer, ArrayList<ItemBargainListEntity>> orderItemBargainListCache = new HashMap<Integer, ArrayList<ItemBargainListEntity>>();
	/**
	 * 用户订单详情店铺抵用券列表缓存
	 */
	public static HashMap<Integer, ArrayList<CashCouponEntity>> UserOrderDetailCashCouponListCache = new HashMap<Integer, ArrayList<CashCouponEntity>>();
	/**
	 * 用户订单详情宝龙抵用券列表缓存
	 */
	public static HashMap<Integer, ArrayList<CashCouponEntity>> UserOrderDetailPlCashCouponListCache = new HashMap<Integer, ArrayList<CashCouponEntity>>();
	/**
	 * 用户信息缓存
	 */
	public static HashMap<Long, ArrayList<ItemListEntity>> UserOrderItemListCache = new HashMap<Long, ArrayList<ItemListEntity>>();

	/**
	 * 用户商品收藏列表缓存
	 */
	public static ArrayList<ItemFavourListEntity> ItemFavourListCache = new ArrayList<ItemFavourListEntity>();
	/**
	 * 用户店铺收藏列表缓存
	 */
	public static ArrayList<ShopFavourListEntity> ShopFavourListCache = new ArrayList<ShopFavourListEntity>();
	
	/**
	 * 附近店铺查询信息
	 */
	public static ArrayList<NearbyShopEntity> NearbyShopCache = new ArrayList<NearbyShopEntity>();
	/**
	 * 附近搜索信息
	 */
	public static ArrayList<NearbySearchEntity> NearbySearchCache = new ArrayList<NearbySearchEntity>();
	/**
	 * 
	 */
	public static ArrayList<RecommendEntity> RecommendCache = new ArrayList<RecommendEntity>();
	public static ArrayList<BannerEntity> BannerCache = new ArrayList<BannerEntity>();
	public static long cartId;
	/**
	 * 我的消息列表缓存
	 */
	public static ArrayList<ChatMsgListEntity> MyMsgListCache = new ArrayList<ChatMsgListEntity>();
	public static ArrayList<ChatMsgEntity> MyMsgDetailCache = new ArrayList<ChatMsgEntity>();
	public static ArrayList<NotifyListEntity> MyNotifyListCache = new ArrayList<NotifyListEntity>();
	public static ArrayList<GrouponCouponEntity> MyGrouponCouponListCache = new ArrayList<GrouponCouponEntity>();
	/*
	 * 版本号
	 */
	public static String versionCode = new String();

	/**
	 * 首页广告图片缓存
	 */
	public static LruCache<String, BitmapDrawable> mMemoryCache = new  LruCache<String, BitmapDrawable>(1024*1024*5);
	public static ArrayList<Mall> mallList = new ArrayList<Mall>();

}
