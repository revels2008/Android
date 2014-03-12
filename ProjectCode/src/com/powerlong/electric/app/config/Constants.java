/**
 * 宝龙电商
 * com.powerlong.electric.app.config
 * Constants.java
 * 
 * 2013-7-25-上午09:19:12
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.config;

import java.util.ArrayList;
import java.util.Collection;

import com.example.volleytest.cache.BitmapCache;
import com.powerlong.electric.app.cache.LCache;

/**
 * 
 * Constants
 * 
 * @author: Liang Wang 2013-7-25-上午09:19:12
 * 
 * @version 1.0.0
 * 
 */ 
public class Constants {
	// URL参数标识 用来替换
	public static final String replaceTag = "xxxx";
	public static boolean ISPOWERLONG = true;
	public static final String IMG_CACHE_PATH = "/sdcard/powerlong/imgs";
	public static final String APPCODE = "F102";
	public static final String KEY = "UIPRWQTNEWASDUPASDJLXIKOPAD";
	public static final String MD5_KEY = "APWMFIVJDAUVCHWOQQFDVB";
	public static final String AES_KEY = "CXSOKJTSQSAZCVGHGHVDSDCG";
	
	public static String mac = "";
	public static final String WIFI_SSID = "";
	public static final String version = "2.0.9";

	public static final String moneyTag = "¥";
	public static final String numPrfix = "x";
	
	public static final int ORDER_SUCCESS = 10;
	
	public static final boolean isNeedGroupon = true;//是否需要团购功能

	public static final boolean isShowDefaultUi = false;//当数据错误时是否显示默认的详细页 false不显示
	public static final boolean isShowNearGallery = false;//附近界面是否显示画廊视图 true显示
	public static final boolean isUpdate = false; //是否强制更新  true强制更新

//	public static final boolean isShowDeaultUi = true;//当数据错误时是否显示默认的详细页 false不显示
//	public static final boolean isShowNearGallery = false;//附近界面是否显示画廊视图 true显示S
	
	
	// define server url
	public static final class ServerUrl {
		public final static String NEARBY_SERVER_IP = "locate.ipowerlong.com";
//		public final static String SERVER_IP = "192.168.170.46:8090";
//		public final static String NEARBY_SERVER_IP = "plocc.powerlong.com";
//		public final static String SERVER_IP = "192.168.20.133";
		public final static String SERVER_IP = "plocc.powerlong.com";
//		public final static String SERVER_IP = "www.ipowerlong.com";
//		public final static String SERVER_IP = "192.168.0.86:10000";
//		public final static String SERVER_IP = "192.168.170.211:8080";
//		public final static String SERVER_IP = "192.168.171.209:8088"; //liuboyu
//		public final stat ic String SERVER_IP = "10.254.18.81";
//		public final static String SERVER_IP = "192.168.20.105";
//		public final static String SERVER_IP = "www.powerlongmall.cn";
//		public final static String SERVER_IP = "www.pl-mall.com";
//		public final static String SERVER_IP = "192.168.180.104";
//		public final static String SERVER_IP = "192.168.180.115:8081";
//		public final statiwc String SERVER_IP = "192.168.170.176:8081";
//		public final static String SERVER_IP = "172.168.0.124:8090";
		
		private final static String SERVER_PORT = "8081";
		private final static String SERVER_PORT_NEARBY = "8080";

		// private final static String SERVER_PORT = "8081";
		public final static String URL_PRFIX_NEARBY = "http://" + NEARBY_SERVER_IP /* + ":" */
		/* + SERVER_PORT */+ "/";
		public final static String URL_PRFIX = "http://" + SERVER_IP /* + ":" */
				/* + SERVER_PORT */+ "/";
//		private final static String URL_PRFIX_NEARBY = "http://" + NEARBY_SERVER_IP  /*+ ":" 
//				 + SERVER_PORT_NEARBY */+ "/";
		// navigation start
		
		public static final String LOGIN_URL = URL_PRFIX+"OCC_SSO_Web/mobile.htm?";// 登录URl data={"loginname":"admin","loginpwd":"123456"}
		public static final String SCANNING_RESULT_URL = URL_PRFIX + "OCC_QR_CODE_Web/mobile/activity.htm?";

//		public static final String GET_ACTIVE_CHANNEL = "http://192.168.171.134:10000"+"/OCC_GATEWAY_Web/mobile/getActiveChannel.htm";
		public static final String GET_ACTIVE_CHANNEL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)+"OCC_GATEWAY_Web/service/access_serial.htm?";
		public static final String GET_REQUEST_PAY = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)+"OCC_GATEWAY_Web/pay/prepare.htm?";
//		public static final String SAVE_CHANNEL = "http://192.168.171.134:10000"+"/OCC_GATEWAY_Web/mobile/saveChannel.htm";
		public static final String SAVE_CHANNEL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)+"OCC_GATEWAY_Web/pay/toChannel.htm?";
		public static final String GET_NEW_ORDER_NO = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)+"tradeWeb/mobile/orderPay.htm?";

		public static final String NAVIGATION_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/findNavigation.htm?";// 导航列表.参数：data={"mall":”1”,"nav_id":"1"}
		// brand list
		public static final String GET_BRAND_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/brandList.htm?";// 品牌列表.参数：data={"mall":”1”,"nav_id":"1"}
		public static final String GET_SHOP_DETAILS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/loadShop.htm?";// 店铺详情.参数：data={"shopId":"1"}
		public static final String GET_SHOP_ITEM_DETAIL_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
		+ "shopWeb/mobile/loadShopItemDetail.htm?";// 店铺详情.参数：data={"shopId":"1"}
		public static final String GET_ITEM_DETAILS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/loadItem.htm?";// 商品详情.参数：data={"itemId":"1"}
		public static final String GET_ITEM_COMMENTS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/itemCommentList.htm?";// 商品详情.参数：data={"page":"1","type":"0","itemId":"1"}
		public static final String GET_GROUPON_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "grouponWeb/mobile/grouponList.htm?";// 团购列表.参数：data={"mall":"1","page":1}
		public static final String GET_GROUPON_DETAILS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "grouponWeb/mobile/loadGroupon.htm?";// 团购详情.参数：data={"grouponId":"1"}
		public static final String URL_GET_GROUPON_DETAIL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
		+ "grouponWeb/mobile/loadGroupon.htm";
		public static final String GET_HOME_RECOMMEND_INFO = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/queryAdvertisement.htm?";
		public static final String GET_GROUPON_MORE_DETAIL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
		+ "grouponWeb/mobile/grouponItemList.htm";
		// 读取首页广告位信息data={"mall":1,"channelCode":"baolongtiandi","platform":1}
		public static final String GET_MY_BARGAINLIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/shopCashCouponListMobile.htm?";// 读取我的抵用券列表
																			// data={"TGC":"EC78B2914CB24DA89736323ADE6ED812","page":"1"}
		public static final String GET_MESSAGE_CODE_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/registerMobileNo.htm?";// 获取短信验证码data={"mobile":"13812345678"}
		public static final String IS_NEED_VERIGY = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/validateOpenSms.htm?";// 
		
		public static final String SEND_REGISTER_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/register.htm?";
		
		public static final String NEW_SEND_REGISTER_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/newRegister.htm?";
		// 用户注册
		// data={"mobile":"13812345679","mall":1,"pwd":"123456","code":"1234"}"
		public static final String CHECK_VERSIONCODE_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/findAllinterfaceVersion.htm?";
		// 检查缓存版本号data={"mall":"1"}"
		public static final String EDIT_PROFILEINFO_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/modifyUserInfo.htm?";// 修改用户信息data={"newEmail":"sdf@sd.com","TGC":"340C28C3483D43EBAAFDF9656B7392EF"}"
		public static final String FLOOR_SHOPS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/findShopListByFloor.htm?";// 楼层店铺列表.参数：data={"page":1,"floor":"1"}
		public static final String SHOPS_INFO_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/getShopInfo.htm?";// 店铺信息.参数：data={"mall":"1","id":1}
		public static final String GET_ACTIVITIES_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/acitivityList.htm?";// 活动列表.参数：data={"mall":"1","page":1}
		public static final String GET_ACTIVITIES_DETAIL_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/loadActivity.htm?";// 活动详情.参数：data={"mall":"1","activityId":1}
		public static final String CLASSIFICATION_SHOPS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/findShopListByClassification.htm?";// 分类店铺列表.参数：data={"mall":"1","page":1,"classification_id":"1"}
		public static final String CATEGORY_GOODS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/findItemListByCategory.htm?";// 商品类目列表.参数：data={"mall":"1","page":1,"classification_id":"1"}
		public static final String CATEGORY_GOODS_INFO_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/getItemInfo.htm?";// 商品详情.参数：{"mall":"1","id":1}
		public static final String GROUPON_INFO_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/getGrouponInfo.htm?";// 团购详情.参数：data={"mall":"1","id":"1"}
		public static final String SHOP_RECOMMEND_GOODS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/findRecommendItemListByShop.htm?";// 店铺推荐商品列表.参数：data={"mall":"1","page":1,"shop_id":1}
		public static final String GET_ORDER_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/loadOrderList.htm?";// 用户中心
														// 我的订单.参数：data={"page":1,"TGC":1,"mall":"1","keyword":""}
		public static final String GET_ORDER_LIST_STAT_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/loadOrderListByStat.htm?";
		public static final String GET_MY_GROUPON_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/shopGrouponListMobile.htm?";// 用户中心
		// 我的订单.参数：data={"TGC":"3FEE2F07AFB04799A5C89939BFF69790","type":"1","page":1}
		public static final String GET_PARENT_ORDER_DETAIL_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/loadParentOrder.htm?";// 静态数据 用户中心
													// 我的订单详情.参数：data={"orderId":"443"}
		public static final String GET_ORDER_DETAIL_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/loadOrder.htm?";
		public static final String GET_MY_MESSAGE_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/messageListMobile.htm?";// 用户中心
																	// 我的消息列表
																	// data={"page":"1","TGC":"20AAB47D87CB2B424CB3C6A24C91C93BCE"}
		public static final String GET_MESSAGE_BY_SHOPID_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/loadMessage.htm?";// 获取某个店家的所有消息data={"page":"1","TGC":"B6E6AA0AACF14387B0B38BE52B8D8CE6","shopId":"1"}
		public static final String GET_MY_NOTIFICATION_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/notifyListMobile.htm?";// 用户中心
																	// 我的通知列表data={"page":"1","TGC":"EC78B2914CB24DA89736323ADE6ED812","type":"0"}

		public static final String GET_UNREAD_NMSG_NUM_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/unReadNumsMobile.htm?";// 用户中心
																	// 未读信息数目查询
																	// data={"TGC":"EC78B2914CB24DA89736323ADE6ED812"}
		public static final String GET_PAY_NMSG_NUM_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/loadOrderCount.htm?";//用户中心,获取各订单数目
		// cart start
		public static final String GET_CART_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/loadCart.htm?";// 获取购物车列表
		public static final String GET_CART_COUNT_INFO_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/gotoCount.htm?";// 获取结算信息
		public static final String ADD_ORDER_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/addOrder.htm?";// 获取结算信息
		public static final String GET_PL_CASHCOUPON_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/loadPlCashCoupon.htm?";// 获取宝龙抵用券
		public static final String GET_USER_ADDRESS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/findAllAddress.htm?";// 获取用户地址信息
		public static final String GET_USER_ADDRESS_ByID_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/findAddressById?";// 获取用户地址信息
		public static final String DEL_USER_ADDRESS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/deleteAddress.htm?";// 删除用户地址信息
		public static final String SET_USER_DEFAULT_ADDRESS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/defaultAddress.htm?";// 设置用户默认收货地址
		public static final String ADD_USER_ADDRESS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/addAddress.htm?";// 修改/添加用户收货地址
		public static final String CHANGE_ITEM_NUM_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/changeItemNum.htm?";// 加减购物车商品
		public static final String ADD_ITEM_TO_FAVOUR_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/mobileAddItemFavour.htm?";// 添加商品到收藏
		public static final String ADD_FAVOUR_TO_CART_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/addItemsCart.htm?";// 收藏商品加入购物车
		public static final String DEL_ITEM_FROM_FAVOUR_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/mobileRemoveItemFavour.htm?";// 将商品移出购收藏
		public static final String SEND_ADD_ORDER_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/addOrder.htm?";// 用户下订单 待完成
		public static final String LOAD_COMMUNITY_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/loadCommunity.htm?";// 读取地域信息 待完成

		public static final String GET_ITEM_FAVOUR_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/itemFavourList.htm?";// 获取用户商品收藏列表data={"mall":1,"TGC":"8EAE0AA526814E8AA93B0E75E1734EE9","page":"1"}
		public static final String CHECK_VERSION_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/findAllinterfaceVersion.htm?";;//获取版本号的URL地址
		public static final String GET_SHOP_FAVOUR_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/storeListMobile.htm?";// 获取用户店铺收藏列表data={"mall":1,"page":"1","TGC":"B6E6AA0AACF14387B0B38BE52B8D8CE6"}

		public static final String ADD_ITEM_TO_CART = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/itemBuy.htm?";
		public static final String DEL_ITEM_FROM_CART = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/deleteItem.htm?";// 从购物车内删除商品data={"TGC":"32AC7F87DCD544948BA991EF40957BCC","mall":1,"shopId":79,"itemId":204,"type":1,"cartId":66}
		public static final String GET_CART_ITEM_COUNT = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "tradeWeb/mobile/getCartItemNum.htm?";
		// cart end

		// user center start
		public static final String FAVOUR_SHOPS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "/userWeb/userMobileCenter/storeListMobile.htm?";// 收藏店铺列表.参数：data={"page":"1","itemsPerPage":1,"tgc":1}
		public static final String DELETE_FAVOUR_SHOP_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/mobileRemoveStoreFavour.htm?";// 根据ID删除已收藏店铺.参数：data={"favourId":"1","tgc":1}
		public static final String FAVOUR_GOODS_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "/userWeb/userMobileCenter/itemListMobile.htm?";// 收藏商品列表.参数：data={"page":"1","itemsPerPage":1,"tgc":1}
		public static final String DELETE_FAVOUR_GOOD_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/mobileRemoveItemFavour.htm?";// 根据ID删除已收藏商品.参数：data={"favourId":"1","tgc":1}
		public static final String CASH_COUPON_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/shopCashCouponListMobile.htm?";// 优惠券列表.参数：data={"page":"1","itemsPerPage":1,"tgc":1}
		public static final String GROUPON_COUPON_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/purchaseTickets.htm?";// 团购券列表.参数：data={"page":"1","itemsPerPage":1,"tgc":1}
		public static final String MESSAGE_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/messageListMobile.htm?";// 提醒信息列表.参数：data={"tgc":1}
		public static final String UNREAD_MESSAGE_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/unReadMessageListMobile.htm?";// 未读信息列表.参数：data={"tgc":1}
		public static final String UPDATE_MESSAGE_STATUS_READED_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/updateMsgRead.htm?";// 未读信息列表.参数：data={"tgc":1}
		// user center end

		// search model start
		public static final String GET_SEARCH_CATEGORIES_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/getAppItemCategorys.htm?";// 搜索分类列表.参数：data={"mall":"1"}
		public static final String GET_MATCHED_OBJ_BY_KEYWORDS_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/mobileSearch.htm?";// 匹配关键字搜索：根据传参不同可以搜索店铺 商铺等
		// search model end
		
		public static final String GET_NEARBY_SHOP_URL = URL_PRFIX_NEARBY
				+ "locate_web/shopInfo/getShopInfo.htm?";
		
		public static final String GET_LARGE_IMAGE_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "shopWeb/mobile/pictureHandle.htm?";
		public static final String GET_FLOOR_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "locate_web/location/searchFloor.htm?";
		public static final String GET_NEARBY_SEARCH_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "locate_web/shopInfo/searchShopInfo.htm?";
		public static final String GET_NEARBY_SEARTCH_INFO_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "locate_web/shopInfo/searchShopInfo.htm?";
		
		public static final String ADD_SHOP_TO_FAVOUR_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+ "userWeb/userMobileCenter/favourShop.htm?";
		
		public static final String SEARCH_GET_CATEGORIS = URL_PRFIX+ "shopWeb/mobile/getAppItemCategorys.htm";
		public static final String SEARCH_GET_SEARCH_LIKE = URL_PRFIX+"shopWeb/mobile/mobileSearch.htm";
		
//		public static final String LOGIN_WIFI_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
//				+ "OCC_SSO_Web/appLogin.htm?";
		public static final String LOGIN_WIFI_URL = "http://172.16.0.243:8000/wifi_auth_web/appLogin.htm?";
		public static final String LOGIN_WIFI_URL2 = "http://172.16.0.253:8000/wifi_auth_web/appLogin.htm?";

		public static final String SEND_MESSAGE = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
						+ "userWeb/userMobileCenter/sendMessage.htm?";
		public static final String MALL_LIST_URL = (ISPOWERLONG ? URL_PRFIX: URL_PRFIX_NEARBY)
				+"userWeb/userMobileCenter/getMallInfo.htm?";
	}

	public static final int KEYWORDS_TYPE_HOTWORDS = 0;
	public static final int KEYWORDS_TYPE_GOODS = 1;
	public static final int KEYWORDS_TYPE_SHOP = 2;
	public static final int KEYWORDS_TYPE_GROUPON = 3;

	// define json key name
	public static final class JSONKeyName {
		// navigation
		public static final int navItemCount = 7;

		public static enum NavItemId {
			FLOOR("1"), BRAND("2"), ACTIVITY("3"), FOODS("4"), ENTERTAINMENT(
					"5"), SHOPPING("6"), GROUPON("7"), ;

			private String value;

			private NavItemId(String value) {
				this.value = value;
			}

			public String getValue() {
				return this.value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		};

		public static final String[] NavItemIds = new String[] {
				NavItemId.FLOOR.getValue(), /*
											 * NavItemId.BRAND.getValue(),
											 * NavItemId.ACTIVITY.getValue(),
											 */NavItemId.FOODS.getValue(),
				NavItemId.ENTERTAINMENT.getValue(),
				NavItemId.SHOPPING.getValue() /* , NavItemId.GROUPON.getValue() */};

		public static final String SERVER_JSON_TOPEST_DATA = "data";
		public static final String SERVER_JSON_TOPEST_TYPE = "type";
		public static final String SERVER_JSON_TOPEST_MSG = "msg";
		public static final String SERVER_JSON_TOPEST_CODE = "code";

		public static final String LOGIN_JSON_KEY_USERNAME = "loginname";
		public static final String LOGIN_JSON_KEY_PWD = "loginpwd";

		public static final String NAV_JSON_TOPEST_DATA = "data";
		public static final String NAV_JSON_TOPEST_TYPE = "type";
		public static final String NAV_JSON_TOPEST_MSG = "msg";
		public static final String NAV_JSON_TOPEST_CODE = "code";
		public static final String NAV_JSON_TOPEST_DATA_KEY_NAME = "navigationList";
		public static final String NAV_JSON_TOPEST_DATA_OBJ_VERSIONNO = "version";

		public static final String NAV_OBJ_KEY_SELFID = "id";
		public static final String NAV_OBJ_KEY_ICON = "icon";
		public static final String NAV_OBJ_KEY_COUNT = "count";
		public static final String NAV_OBJ_KEY_NAME = "name";
		public static final String NAV_OBJ_KEY_GROUPID = "groupId";
		public static final String NAV_OBJ_KEY_METHOD = "method";
		public static final String NAV_OBJ_KEY_ISPARENT = "isParent";
		public static final String NAV_OBJ_KEY_PARENTID = "parentId";
		public static final String NAV_OBJ_KEY_LEVEL = "level";
		public static final String NAV_OBJ_KEY_DATA = "data";
		public static final String NAV_OBJ_KEY_NAVID = "navId";
		// navigation end

		// brand list start
		public static final String BRAND_JSON_TOPEST_DATA = "data";
		public static final String BRAND_JSON_TOPEST_TYPE = "type";
		public static final String BRAND_JSON_TOPEST_MSG = "msg";
		public static final String BRAND_JSON_TOPEST_CODE = "code";
		public static final String BRAND_JSON_TOPEST_DATA_KEY_NAME = "shopList";

		public static final String BRAND_OBJ_KEY_SELFID = "id";
		public static final String BRAND_OBJ_KEY_ICON = "icon";
		public static final String BRAND_OBJ_KEY_ENNAME = "enName";
		// brand list end

		// activity list start
		public static final String ACTIVITY_LIST_JSON_TOPEST_DATA = "data";
		public static final String ACTIVITY_LIST_JSON_TOPEST_TYPE = "type";
		public static final String ACTIVITY_LIST_JSON_TOPEST_MSG = "msg";
		public static final String ACTIVITY_LIST_JSON_TOPEST_CODE = "code";

		public static final String ACTIVITY_LIST_ARRAY_KEY_NAME = "activityList";
		public static final String ACTIVITY_LIST_OBJ_KEY_CLASSFICATION = "classification";
		public static final String ACTIVITY_LIST_OBJ_KEY_ID = "id";
		public static final String ACTIVITY_LIST_OBJ_KEY_IMAGE = "image";
		public static final String ACTIVITY_LIST_OBJ_KEY_NAME = "name";
		public static final String ACTIVITY_LIST_OBJ_KEY_DURATION = "duration";
		public static final String ACTIVITY_LIST_OBJ_KEY_ADDRESS = "address";
		public static final String ACTIVITY_LIST_OBJ_KEY_ISPLAZAACTIVITY = "isPlazaActivity";

		public static final String ACTIVITY_LIST_OBJ_KEY_INTRODUCTION = "introduction";
		public static final String ACTIVITY_LIST_OBJ_KEY_TIPS = "tips";
		public static final String ACTIVITY_LIST_OBJ_KEY_RULES = "rules";

		public static final String ACTIVITY_DETAIULS_OBJ_KEY_ID = "id";
		public static final String ACTIVITY_DETAIULS_OBJ_KEY_IMAGE = "image";
		public static final String ACTIVITY_DETAIULS_OBJ_KEY_INTRODUCTION = "introduction";
		public static final String ACTIVITY_DETAIULS_OBJ_KEY_ISPLAZAACTIVITY = "isPlazaActivity";
		public static final String ACTIVITY_DETAIULS_OBJ_KEY_NAME = "name";

		// public static final String ACTIVITY_LIST_OBJ_KEY_IMAGE = "image";

		// activity list end

		// floor's shop details start
		public static final String FLOOR_DETAIL_JSON_TOPEST_DATA = "data";
		public static final String FLOOR_DETAIL_JSON_TOPEST_TYPE = "type";
		public static final String FLOOR_DETAIL_JSON_TOPEST_MSG = "msg";
		public static final String FLOOR_DETAIL_JSON_TOPEST_CODE = "code";

		public static final String FLOOR_DETAIL_ARRAY_KEY_NAME = "shopList";
		public static final String FLOOR_DETAIL_OBJ_KEY_SELFID = "id";
		public static final String FLOOR_DETAIL_OBJ_KEY_FLOOR_NUM = "floor";
		public static final String FLOOR_DETAIL_OBJ_KEY_LEVEL = "level";
		public static final String FLOOR_DETAIL_OBJ_KEY_MAILLID = "mallId";
		public static final String FLOOR_DETAIL_OBJ_KEY_SHOPNAME = "shopName";
		public static final String FLOOR_DETAIL_OBJ_KEY_ITEMLIST = "itemList";
		public static final String FLOOR_DETAIL_OBJ_KEY_ITEMID = "itemId";
		public static final String FLOOR_DETAIL_OBJ_KEY_FAVOURNUM = "favourNum";
		public static final String FLOOR_DETAIL_OBJ_KEY_SHOPCLASSID = "shopClassificationId";
		public static final String FLOOR_DETAIL_OBJ_KEY_SHOPCLASS = "shopClassification";
		public static final String FLOOR_DETAIL_OBJ_KEY_BUSSNINESSRANGE = "bussinessRange";
		public static final String FLOOR_DETAIL_OBJ_KEY_BRIEF = "brief";
		public static final String FLOOR_DETAIL_OBJ_KEY_LOGO = "logo";
		public static final String FLOOR_DETAIL_OBJ_KEY_RECOMMENDITEMNUM = "recommendItemNum";
		public static final String FLOOR_DETAIL_OBJ_KEY_NEWITEMMONTHLYNUM = "newItemMonthlyNum";
		public static final String FLOOR_DETAIL_OBJ_KEY_HOTITEMNUM = "hotItemNum";
		public static final String FLOOR_DETAIL_OBJ_KEY_USERID = "userId";
		public static final String FLOOR_DETAIL_OBJ_KEY_SHOPUSERNAME = "shopUserName";
		public static final String FLOOR_DETAIL_OBJ_KEY_CLASS = "classification";
		public static final String FLOOR_DETAIL_OBJ_KEY_EVALUATION = "evaluation";
		public static final String FLOOR_DETAIL_OBJ_KEY_NEWITEMNUM = "newItemNum";
		public static final String FLOOR_DETAIL_OBJ_KEY_ITEMLISTRECOMMEND = "itemListReCommend";
		public static final String FLOOR_DETAIL_OBJ_KEY_ITEMLISTHOT = "itemListHot";
		// floor's shop details end

		// brand details start
		public static final String BRAND_DETAIL_JSON_TOPEST_DATA = "data";
		public static final String BRAND_DETAIL_JSON_TOPEST_TYPE = "type";
		public static final String BRAND_DETAIL_JSON_TOPEST_MSG = "msg";
		public static final String BRAND_DETAIL_JSON_TOPEST_CODE = "code";

		public static final String BRAND_DETAIL_ARRAY_KEY_NAME = "shop";
		public static final String BRAND_DETAIL_OBJ_KEY_SHOPNAME = "shopName";
		public static final String BRAND_DETAIL_OBJ_KEY_SHOPLOGO = "logo";
		public static final String BRAND_DETAIL_OBJ_KEY_SHOPBRIEF = "brief";
		public static final String BRAND_DETAIL_OBJ_KEY_SHOPFAVOURNUM = "favourNum";
		public static final String BRAND_DETAIL_OBJ_KEY_SHOPBUSSINESSRANGE = "bussinessRange";
		public static final String BRAND_DETAIL_OBJ_KEY_SHOPFSCORE = "service";
		// brand details end

		// shop details start 店铺详情
		public static final String SHOP_DETAIL_JSON_TOPEST_DATA = "data";
		public static final String SHOP_DETAIL_JSON_TOPEST_TYPE = "type";
		public static final String SHOP_DETAIL_JSON_TOPEST_MSG = "msg";
		public static final String SHOP_DETAIL_JSON_TOPEST_CODE = "code";

		public static final String SHOP_DETAIL_ARRAY_KEY_NAME = "shop";
		public static final String SHOP_DETAIL_OBJ_KEY_NAME = "name";
		public static final String SHOP_DETAIL_OBJ_KEY_IMAGE = "image";
		public static final String SHOP_DETAIL_OBJ_KEY_INSTRODUCTION = "introduction";
		public static final String SHOP_DETAIL_OBJ_KEY_ITEMNUM = "itemNum";
		public static final String SHOP_DETAIL_OBJ_KEY_CLASSIFICATION = "classification";
		public static final String SHOP_DETAIL_OBJ_KEY_FAVOURNUM = "favourNum";
		public static final String SHOP_DETAIL_OBJ_KEY_EVALUATION = "evaluation";
		public static final String SHOP_DETAIL_OBJ_KEY_CATEGORYLIST = "categoryList";
		public static final String SHOP_DETAIL_OBJ_KEY_ITEMLIST = "itemList";
		// public static final String SHOP_DETAIL_OBJ_KEY_ORDERTYPE =
		// "orderType";
		// public static final String SHOP_DETAIL_OBJ_KEY_SHOWTYPE = "showType";
		// shop details end

		// SHOP Item list start 店铺商品列表
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_NAME = "name";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_IMAGE = "image";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_ID = "itemId";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_ISCART = "isCart";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_ISFAVOUR = "isFavour";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_PLPRICE = "plPrice";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_SELLNUMMONTH = "sellNumMonth";
		public static final String SHOP_ITEM_DETAIL_OBJ_KEY_TYPE = "type";
		// SHOP item list end

		// Item details start 商品详情
		public static final String ITEM_DETAIL_JSON_TOPEST_DATA = "data";
		public static final String ITEM_DETAIL_JSON_TOPEST_TYPE = "type";
		public static final String ITEM_DETAIL_JSON_TOPEST_MSG = "msg";
		public static final String ITEM_DETAIL_JSON_TOPEST_CODE = "code";

		public static final String ITEM_DETAIL_OBJ_KEY_NAME = "name";
		public static final String ITEM_DETAIL_OBJ_KEY_COMMENTNUM = "commentNum";
		public static final String ITEM_DETAIL_OBJ_KEY_DETAILURL = "detailUrl";
		public static final String ITEM_DETAIL_OBJ_KEY_EVALUATION = "evaluation";
		public static final String ITEM_DETAIL_OBJ_KEY_FAVOURNUM = "favourNum";
		public static final String ITEM_DETAIL_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String ITEM_DETAIL_OBJ_KEY_PLPRICE = "plPrice";
		public static final String ITEM_DETAIL_OBJ_KEY_PROP = "prop";
		public static final String ITEM_DETAIL_OBJ_KEY_PROP1NAME = "prop1Name";
		public static final String ITEM_DETAIL_OBJ_KEY_PROP2NAME = "prop2Name";
		public static final String ITEM_DETAIL_OBJ_KEY_SELLNUM30 = "sellNumMonth";
		public static final String ITEM_DETAIL_OBJ_KEY_SHOPEVALUATION = "shopEvaluation";
		public static final String ITEM_DETAIL_OBJ_KEY_SHOPID = "shopId";
		public static final String ITEM_DETAIL_OBJ_KEY_SHOPPROP = "shopProp";
		public static final String ITEM_DETAIL_OBJ_KEY_SHOPIMAGE = "shopImage";
		public static final String ITEM_DETAIL_OBJ_KEY_SHOPNAME = "shopName";
		public static final String ITEM_DETAIL_OBJ_KEY_COLORLIST = "propList1";
		public static final String ITEM_DETAIL_OBJ_KEY_IMAGELIST = "imageList";
		public static final String ITEM_DETAIL_OBJ_KEY_BARGINLIST = "bargainList";
		// public static final String ITEM_DETAIL_OBJ_KEY_FREIGHT = "freight";
		public static final String ITEM_DETAIL_OBJ_KEY_ITEM_TYPE = "itemType";
		public static final String ITEM_DETAIL_OBJ_KEY_GROUPON_ID = "grouponId";
		public static final String ITEM_DETAIL_OBJ_KEY_STAT = "stat";

		// Item details end

		// Item Favour list
		public static final String ITEM_FAVOURLIST_OBJ_KEY_FAVOURID = "favourId";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_ITEMID = "id";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_PICPATH = "picturePath";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_ITEMNAME = "itemName";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_PLPRICE = "plPrice";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_SELLNUM = "sellNum";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_FAVOURNUM = "favourNum";
		public static final String ITEM_FAVOURLIST_OBJ_KEY_TYPE = "type";

		// Shop Favour list
		public static final String SHOP_FAVOURLIST_OBJ_KEY_FAVOURID = "id";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_SHOPID = "shopId";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_IMAGE = "image";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_SHOPNAME = "shopName";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_BRIEF = "brief";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_PAGEVIEWNUM = "pageviewNum";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_FAVOURNUM = "favourNum";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_CLASSFICATION = "classification";
		public static final String SHOP_FAVOURLIST_OBJ_KEY_EVALUATION = "evaluation";

		// prop list start 商品规格
		public static final String PROP_OBJ_KEY_SIZELIST = "propList2";
		public static final String PROP_OBJ_KEY_PROP_ISDEFAULT = "isDefault";
		// public static final String PROP_OBJ_KEY_PROP_ISEMPTY = "isEmpty";
		public static final String PROP_OBJ_KEY_PROP_NAME = "name";
		// public static final String PROP_OBJ_KEY_PROP_ORDER = "order";
		public static final String PROP_OBJ_KEY_PROP_STOCKNUM = "stockNum";
		public static final String PROP_OBJ_KEY_PROP_ITEMID = "itemId";
		// prop list end

		// item comments start商品评价详情
		public static final String ITEM_COMMENTS_JSON_TOPEST_DATA = "data";
		public static final String ITEM_COMMENTS_JSON_TOPEST_TYPE = "type";
		public static final String ITEM_COMMENTS_JSON_TOPEST_MSG = "msg";
		public static final String ITEM_COMMENTS_JSON_TOPEST_CODE = "code";
		public static final String ITEM_COMMENTS_JSON_ARRAY_KEY = "commentList";

		public static final String ITEM_COMMENTS_OBJ_KEY_CONTENT = "content";
		public static final String ITEM_COMMENTS_OBJ_KEY_CREATEDDATE = "createdDate";
		public static final String ITEM_COMMENTS_OBJ_KEY_EVALUATION = "evaluation";
		// public static final String ITEM_COMMENTS_OBJ_KEY_ISUSERNAMEHIDDEN =
		// "isUsernameHidden";
		public static final String ITEM_COMMENTS_OBJ_KEY_NICKNAME = "nickname";
		public static final String ITEM_COMMENTS_OBJ_KEY_PROP = "prop";
		// item comments end

		// seat list
		public static final String SEAT_LIST_JSON_ARRAY_KEY = "seatList";

		public static final String SEAT_LIST_OBJ_KEY_ADHEIGHT = "adHeight";
		public static final String SEAT_LIST_OBJ_KEY_ADTYPE = "adType";
		public static final String SEAT_LIST_OBJ_KEY_ADWIDTH = "adWidth";
		public static final String SEAT_LIST_OBJ_KEY_ADVERTISEMENTLIST = "advertisementList";
		public static final String SEAT_LIST_OBJ_KEY_CHANNELID = "channelId";
		public static final String SEAT_LIST_OBJ_KEY_CHANNELORDER = "channelOrder";
		public static final String SEAT_LIST_OBJ_KEY_ID = "id";

		public static final String ADVERTISE_LIST_OBJ_KEY_ADDIS = "adDis";
		public static final String ADVERTISE_LIST_OBJ_KEY_ADIMAGE = "adImage";
		public static final String ADVERTISE_LIST_OBJ_KEY_ADLINCK = "adLink";
		public static final String ADVERTISE_LIST_OBJ_KEY_ADSEATID = "adseatId";
		public static final String ADVERTISE_LIST_OBJ_KEY_ID = "id";
		public static final String ADVERTISE_LIST_OBJ_KEY_TYPE = "type";
		public static final String ADVERTISE_LIST_OBJ_KEY_ADORDER = "adOrder";

		// groupon list start
		public static final String GROUPON_LIST_JSON_TOPEST_DATA = "data";
		public static final String GROUPON_LIST_JSON_TOPEST_TYPE = "type";
		public static final String GROUPON_LIST_JSON_TOPEST_MSG = "msg";
		public static final String GROUPON_LIST_JSON_TOPEST_CODE = "code";
		public static final String GROUPON_LIST_JSON_ARRAY_KEY = "grouponList";

		public static final String GROUPON_ITEM_OBJ_KEY_NAME = "name";
		public static final String GROUPON_ITEM_OBJ_KEY_ID = "id";
		public static final String GROUPON_ITEM_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String GROUPON_ITEM_OBJ_KEY_PLPRICE = "plPrice";
		public static final String GROUPON_ITEM_OBJ_KEY_IMAGE = "image";
		public static final String GROUPON_ITEM_OBJ_KEY_SELLNUM = "sellNum";
		// groupon list end

		// groupon details start
		public static final String GROUPON_DETAIL_OBJ_KEY_BUYNOTIFY = "buyNotify";
		public static final String GROUPON_DETAIL_OBJ_KEY_BUYNUM = "sellNum";
		public static final String GROUPON_DETAIL_OBJ_KEY_COMMENTNUM = "commentNum";
		public static final String GROUPON_DETAIL_OBJ_KEY_CONTENT = "content";
		public static final String GROUPON_DETAIL_OBJ_KEY_COUNTDOWNTIME = "endDate";
		public static final String GROUPON_DETAIL_OBJ_KEY_START_DATE = "startDate";
		public static final String GROUPON_DETAIL_OBJ_KEY_IMAGE = "imageList";
		public static final String GROUPON_DETAIL_OBJ_KEY_ISPAIDFOR = "isPaidfor";
		public static final String GROUPON_DETAIL_OBJ_KEY_ISRETURNITEM = "isReturnItem";
		public static final String GROUPON_DETAIL_OBJ_KEY_ISRETURNMONEY = "isReturnMoney";
		public static final String GROUPON_DETAIL_OBJ_KEY_ITEMNUM = "itemNum";
		public static final String GROUPON_DETAIL_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String GROUPON_DETAIL_OBJ_KEY_NAME = "name";
		public static final String GROUPON_DETAIL_OBJ_KEY_PLPRICE = "plPrice";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOPLIST = "shopList";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOPLIST_EVALUATION = "evaluation";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOPLIST_ID = "id";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOPLIST_NAME = "name";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOPLIST_PROP = "prop";
		public static final String GROUPON_DETAIL_OBJ_KEY_ITEM_GROUP_LIST =  "itemGroupList";
		public static final String GROUPON_DETAIL_OBJ_KEY_ITEM_GROUP_ID =  "itemGrpoupId";
		public static final String GROUPON_DETAIL_OBJ_KEY_ITEM_LIST =  "itemList";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOP_ID =  "shopId";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOP_IMAGE =  "shopImage";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOP_NAME =  "shopName";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOP_PROP =  "shopProp";
		public static final String GROUPON_DETAIL_OBJ_KEY_SHOP_EVALUATION =  "shopEvaluation";
		// groupon details end

		// order detail start
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_ADDRESS = "address";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_ALLNUM = "allNum";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_ALLPRICE = "allPrice";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_FREIGHT = "freight";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_LOGISTICS = "logistics";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_LOGISTICSID = "logisticsId";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_LOGISTICSTIME = "logisticsTime";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_ORDERNO = "orderNo";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_ORDERSTAT = "orderStat";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_PAKAGENUM = "pakageNum";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_PAY = "pay";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_PAYID = "payId";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_PAYNO = "payNo";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_RECEIVETIME = "receiveTime";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_RECEIVER = "receiver";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_RECEIVEDTIME = "receivedTime";
		public static final String ORDER_DETAIL_JSON_OBJ_KEY_TELNUM = "mobile";

		public static final String ORDER_DETAIL_JSON_ARRAY_KEY_PLCASHCOUPONLIST = "plCashCouponList";

		// search categroy start
		public static final String SEARCH_CATEGORY_JSON_TOPEST_DATA = "data";
		public static final String SEARCH_CATEGORY_JSON_TOPEST_TYPE = "type";
		public static final String SEARCH_CATEGORY_JSON_TOPEST_MSG = "msg";
		public static final String SEARCH_CATEGORY_JSON_TOPEST_CODE = "code";

		public static final String SEARCH_CATEGORY_ARRAY_KEY_NAME = "navigationList";
		public static final String SEARCH_CATEGORY_OBJ_KEY_SELFID = "id";
		public static final String SEARCH_CATEGORY_OBJ_KEY_LEVEL = "level";
		public static final String SEARCH_CATEGORY_OBJ_KEY_MAILLID = "mall";
		public static final String SEARCH_CATEGORY_OBJ_KEY_NAME = "name";
		public static final String SEARCH_CATEGORY_OBJ_KEY_PID = "pid";
		public static final String SEARCH_CATEGORY_OBJ_KEY_APPITEMCATEGORYID = "appItemCategoryId";
		public static final String SEARCH_CATEGORY_OBJ_KEY_LOGO = "logo";
		public static final String SEARCH_CATEGORY_OBJ_KEY_DESCRIPTION = "description";
		// search category end

		// search categroy detail start
		public static final String SEARCH_CATEGORY_DETAIL_JSON_TOPEST_DATA = "data";
		public static final String SEARCH_CATEGORY_DETAIL_JSON_TOPEST_TYPE = "type";
		public static final String SEARCH_CATEGORY_DETAIL_JSON_TOPEST_MSG = "msg";
		public static final String SEARCH_CATEGORY_DETAIL_JSON_TOPEST_CODE = "code";

		public static final String SEARCH_CATEGORY_DETAIL_ARRAY_KEY_NAME = "lowerCategoryList";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_SELFID = "id";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_LEVEL = "level";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_MAILLID = "mall";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_NAME = "name";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_PID = "pid";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_METHOD = "method";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_APPITEMCATEGORYID = "appItemCategoryId";
		public static final String SEARCH_CATEGORY_DETAIL_OBJ_KEY_DATA = "data";
		// search category detail end

		// keywords search start
		public static final String KEYWORDS_SEARCH_JSON_TOPEST_DATA = "data";
		public static final String KEYWORDS_SEARCH_JSON_TOPEST_TYPE = "type";
		public static final String KEYWORDS_SEARCH_JSON_TOPEST_MSG = "msg";
		public static final String KEYWORDS_SEARCH_JSON_TOPEST_CODE = "code";

		public static final String KEYWORDS_SEARCH_OBJ_KEY_PAGESIZE = "page_size";
		public static final String KEYWORDS_SEARCH_OBJ_KEY_COUNT = "count";
		public static final String KEYWORDS_SEARCH_OBJ_KEY_HOTWORDS_LIST = "keyList";
		public static final String KEYWORDS_SEARCH_OBJ_KEY_GOODS_LIST = "itemList";
		public static final String KEYWORDS_SEARCH_OBJ_KEY_SHOPS_LIST = "shopList";
		// keywords search end

		// goods details start
		public static final String GOODS_DETAIL_OBJ_KEY_ID = "id";
		public static final String GOODS_DETAIL_OBJ_KEY_SEQUENCE = "sequence";
		public static final String GOODS_DETAIL_OBJ_KEY_SORT = "sort";
		public static final String GOODS_DETAIL_OBJ_KEY_ITEMNMAE = "itemName";
		public static final String GOODS_DETAIL_OBJ_KEY_SORTTYPE = "sortType";
		public static final String GOODS_DETAIL_OBJ_KEY_PICPATH = "picturePath";
		public static final String GOODS_DETAIL_OBJ_KEY_SHOPID = "shopId";
		public static final String GOODS_DETAIL_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String GOODS_DETAIL_OBJ_KEY_PLPRICE = "plPrice";
		public static final String GOODS_DETAIL_OBJ_KEY_FAVOURNUM = "favourNum";
		public static final String GOODS_DETAIL_OBJ_KEY_SELLNUM = "sellNum";
		public static final String GOODS_DETAIL_OBJ_KEY_PLPRICE_BEGIN = "plPriceBegin";
		public static final String GOODS_DETAIL_OBJ_KEY_PLPRICE_END = "plPriceEnd";
		public static final String GOODS_DETAIL_OBJ_KEY_EVACOUNT = "evaCount";
		public static final String GOODS_DETAIL_OBJ_KEY_CLASSFICATIONID = "classificationId";
		public static final String GOODS_DETAIL_OBJ_KEY_STYLE = "style";
		
		// goods details end

		// groupon details start
		public static final String GROUPON_DETAIL_OBJ_KEY_SELFID = "id";
		public static final String GROUPON_DETAIL_OBJ_KEY_TYPE = "type";
		public static final String GROUPON_DETAIL_OBJ_KEY_PATH = "path";
		public static final String GROUPON_DETAIL_OBJ_KEY_SALEVOLUME = "saleVolume";
		// public static final String GROUPON_DETAIL_OBJ_KEY_NAME =
		// "grouponName";
		public static final String GROUPON_DETAIL_OBJ_KEY_PRICE = "price";
		public static final String GROUPON_DETAIL_OBJ_KEY_NOTICE = "notice";
		public static final String GROUPON_DETAIL_OBJ_KEY_GROUPONPRICE = "grouponPrice";
		// groupon details end

		// cart list start
		public static final String CART_LIST_JSON_TOPEST_DATA = "data";
		public static final String CART_LIST_JSON_TOPEST_TYPE = "type";
		public static final String CART_LIST_JSON_TOPEST_MSG = "msg";
		public static final String CART_LIST_JSON_TOPEST_CODE = "code";

		public static final String CART_LIST_JSON_ARRAY_KEYNAME = "shopList";
		public static final String CART_LIST_JSON_ARRAY_KEY_TOTALPRICE = "plPrice";
		public static final String CART_LIST_JSON_ARRAY_KEY_CARTID = "cartId";

		// order list
		public static final String ORDER_LIST_JSON_ARRAY_KEYNAME = "orderList";
		public static final String ORDER_LIST_JSON_ARRAY_ORDER_NO = "orderNo";
		public static final String ORDER_LIST_JSON_ARRAY_PACKAGE_NUM = "pakageNum";
		public static final String ORDER_LIST_JSON_ARRAY_ITEM_NUM = "itemNum";
		public static final String ORDER_LIST_JSON_ARRAY_PRICE = "price";

		// cart's shoplist
		public static final String CART_SHOPLIST_JSON_ARRAY_KEY_BARGAINLIST = "bargainList";
		public static final String CART_SHOPLIST_JSON_ARRAY_KEY_ITEMLIST = "itemList";
		public static final String CART_SHOPLIST_JSON_OBJ_KEY_SHOPID = "id";
		public static final String CART_SHOPLIST_JSON_OBJ_KEY_SHOPNAME = "name";
		public static final String CART_SHOPLIST_JSON_OBJ_KEY_TOTALPRICE = "buyPriceSum";
		public static final String CART_SHOPLIST_JSON_OBJ_KEY_TOTALNUM = "buyNumSum";
		public static final String CART_SHOPLIST_JSON_OBJ_KEY_TEL = "tel";

		// cart shop's bargain list
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_NAME = "name";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_ID = "id";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_LIMITTIME = "limitTime";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_PRICE = "price";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_PROP = "prop";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_SHOPNAME = "shopName";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_RANGE = "range";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_STAT = "stat";
		public static final String CART_BARGAINLIST_JSON_OBJ_KEY_TYPE = "type";
		// cart shop's item list

		public static final String ITEMLIST_JSON_OBJ_KEY_ITEMID = "id";
		public static final String ITEMLIST_JSON_OBJ_KEY_ITEMNAME = "itemName";
		public static final String ITEMLIST_JSON_OBJ_KEY_IMAGE = "image";
		public static final String ITEMLIST_JSON_OBJ_KEY_BUYNUM = "buyNum";
		public static final String ITEMLIST_JSON_OBJ_KEY_STORENUM = "storeNum";
		public static final String ITEMLIST_JSON_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String ITEMLIST_JSON_OBJ_KEY_PLPRICE = "plPrice";
		public static final String ITEMLIST_JSON_OBJ_KEY_ITEMTYPE = "type";
		public static final String ITEMLIST_JSON_OBJ_KEY_PROP = "prop";
		public static final String ITEMLIST_JSON_OBJ_KEY_ITEMBARGAINLIST = "itemBargainList";

		//cart itemBargainList
		public static final String ITEMBARGAINLIST_NAME = "name";
		public static final String ITEMBARGAINLIST_TYPE = "type";
		
		//cart logisticsList
		public static final String LOGISTCSLIST_NAME = "name";
		public static final String LOGISTCSLIST_ID = "id";
		
		//cart payList
		public static final String PAYLIST_NAME = "name";
		public static final String PAYLIST_ID = "id";
		
		// order item list
		public static final String ORDER_LIST_JSON_ARRAY_KEY = "orderList";
		public static final String ORDERLIST_JSON_OBJ_KEY_RESTNUM = "restNum";
		public static final String ORDERLIST_JSON_OBJ_KEY_ALLPRICE = "allPrice";
		public static final String ORDERLIST_JSON_OBJ_KEY_ORDERSTAT = "orderStat";
		public static final String ORDERLIST_JSON_OBJ_KEY_ORDERTYPE = "orderType";
		public static final String ORDERLIST_JSON_OBJ_KEY_ALLNUM = "allNum";
		public static final String ORDERLIST_JSON_OBJ_KEY_ID = "id";
		public static final String ORDERLIST_JSON_OBJ_KEY_TOTALNUM = "totalNum";

		// cart count
		public static final String CART_COUNT_JSON_OBJ_KEY_ADDRESS = "address";
		public static final String CART_COUNT_JSON_OBJ_KEY_ADDRESSID = "addressId";
		public static final String CART_COUNT_JSON_OBJ_KEY_ALLPRICE = "allPrice";
		public static final String CART_COUNT_JSON_OBJ_KEY_FREIGHT = "freight";
		public static final String CART_COUNT_JSON_OBJ_KEY_LISTPRICE = "listPrice";
		public static final String CART_COUNT_JSON_OBJ_KEY_RECEIVETIME = "receiveTime";
		public static final String CART_COUNT_JSON_OBJ_KEY_RECEIVER = "receiver";
		public static final String CART_COUNT_JSON_OBJ_KEY_REDUCEPRICE = "reducePrice";
		public static final String CART_COUNT_JSON_OBJ_KEY_ALL_NUM = "allNum";
		public static final String CART_COUNT_JSON_OBJ_KEY_IS_OTHER_COMMUNITY = "isOtherCommunity";
		public static final String CART_COUNT_JSON_OBJ_KEY_TELNUM = "mobile";

		public static final String CART_COUNT_SHOPLIST_JSON_OBJ_KEY_TOTALPRICE = "allPrice";
		public static final String CART_COUNT_SHOPLIST_JSON_OBJ_KEY_TOTALNUM = "buyNumSum";
		
		public static final String CART_COUNT_ITEMLIST_JSON_OBJ_KEY_TOTALNUM = "buyNum";

		public static final String CART_COUNT_SHOP_BARGAINLIST_JSON_ARRAY_KEY = "bargainList";
		public static final String CART_COUNT_SHOP_BARGAINLIST_JSON_OBJ_KEY_NAME = "name";

		// cash coupon list
		public static final String CART_COUNT_SHOP_CASHCOUPONLIST_JSON_ARRAY_KEY = "cashCouponList";
		public static final String CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_ID = "id";
		public static final String CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_NAME = "name";
		public static final String CART_COUNT_SHOP_CASHCOUPONLIST_JSON_OBJ_KEY_PRICE = "price";

		// present list
		public static final String CART_COUNT_SHOP_PRESENTLIST_JSON_ARRAY_KEY = "presentList";
		public static final String CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_ID = "id";
		public static final String CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_NAME = "itemName";
		public static final String CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_IMAGE = "image";
		public static final String CART_COUNT_SHOP_PRESENTLIST_JSON_OBJ_KEY_NUM = "num";
		
		//logisticsList配送方式
		public static final String CART_COUNT_SHOP_LOGISTICS_JSON_ARRAY_KEY = "logisticsList";
		public static final String CART_COUNT_SHOP_LOGISTICS_JSON_OBJ_KEY_ID = "id";
		public static final String CART_COUNT_SHOP_LOGISTICS_JSON_OBJ_KEY_NAME = "name";
		
		public static final String CART_COUNT_SHOP_PAY_JSON_ARRAY_KEY = "payList";
		// cart list end

		/**
		 * Pl CashCoupon
		 */
		public static final String PL_CASHCOUPON_JSON_TOPEST_DATA = "data";
		public static final String PL_CASHCOUPON_JSON_TOPEST_PLCASHCOUPONLIST = "plCashCouponList";
		public static final String PL_CASHCOUPON_JSON_TOPEST_TYPE = "type";
		public static final String PL_CASHCOUPON_JSON_TOPEST_MSG = "msg";
		public static final String PL_CASHCOUPON_JSON_TOPEST_CODE = "code";

		public static final String PL_CASHCOUPON_JSONOBJ_KEY_ACTIVE = "isActive";
		public static final String PL_CASHCOUPON_JSON_OBJ_KEY_ID = "id";
		public static final String PL_CASHCOUPON_JSON_OBJ_KEY_NAME = "name";
		public static final String PL_CASHCOUPON_JSON_OBJ_KEY_PRICE = "price";

		/**
		 * user address
		 */
		public static final String USER_ADDRESS_JSON_TOPEST_DATA = "data";
		public static final String USER_ADDRESS_JSON_TOPEST_TYPE = "type";
		public static final String USER_ADDRESS_JSON_TOPEST_MSG = "msg";
		public static final String USER_ADDRESS_JSON_TOPEST_CODE = "code";

		public static final String USER_ADDRESS_JSON_ARRAY_KEY = "addressList";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_ADDRESS = "address";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_AREA = "area";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_AREACODE = "areaCode";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_CITY = "city";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_CONSIGNEE = "consignee";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_EXT = "ext";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_ID = "id";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_ISDEFULT = "isDefault";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_MOBILE = "mobile";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_PROVINCE = "province";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_TEL = "tel";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_ZIPCODE = "zipCode";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_OPERTYPE = "oper_type";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_COMMUNITY_NAME = "communityName";
		public static final String USER_ADDRESS_JSON_OBJ_KEY_COMMUNITY_ID = "isOtherCommunity";
		// change item num
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_SHOPID = "shopId";
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_ITEMID = "id";
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_USERID = "user_id";
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_MALL = "mall";
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_ITEMNUM = "buyNum";
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_ITEMTYPE = "type";
		public static final String CHANGE_ITEM_NUM_JSON_OBJ_KEY_CARDID = "cardId";

		// Profile start
		public static final String PROFILE_JSON_OBJ_KEY_ID = "id";
		public static final String PROFILE_JSON_OBJ_KEY_TGC = "TGC";
		public static final String PROFILE_JSON_OBJ_KEY_NICKNAME = "nickname";
		public static final String PROFILE_JSON_OBJ_KEY_USERNAME = "username";
		public static final String PROFILE_JSON_OBJ_KEY_EMAIL = "email";
		public static final String PROFILE_JSON_OBJ_KEY_MOBILE = "mobile";
		public static final String PROFILE_JSON_OBJ_KEY_BIRTHDAY = "birthday";
		public static final String PROFILE_JSON_OBJ_KEY_LASTLOGIN = "lastlogin";
		public static final String PROFILE_JSON_OBJ_KEY_SEX = "sex";

		public static final String PROFILE_JSON_OBJ_KEY_NEW_NICKNAME = "newNickname";
		public static final String PROFILE_JSON_OBJ_KEY_NEW_EMAIL = "newEmail";
		public static final String PROFILE_JSON_OBJ_KEY_NEW_MOBILE = "newMobile";

		// Profile end

		// message and notify list
		public static final String MESSAGE_LIST_JSON_ARRAY_KEY = "messageList";
		public static final String NOTIFY_LIST_JSON_ARRAY_KEY = "notifyList";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_CREATEDATE = "createDate";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_SHOPID = "shopId";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_SENDER = "sender";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_CONTENT = "content";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_IMGAE = "image";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_TYPE = "type";

		public static final String JSON_OBJ_KEY_VERSION_CODE_LIST = "versionList";
		public static final String JSON_OBJ_KEY_VERSION_CODE = "version";
		
		public static final String NOTIFY_LIST_JSON_OBJ_KEY_CREATEDATE = "createdDate";
		public static final String MESSAGE_LIST_JSON_OBJ_KEY_ID = "id";

		// groupon coupon list
		public static final String GROUPON_COUPON_JSON_ARRAY_KEY = "grouponCouponList";
		public static final String GROUPON_COUPON_JSON_KEY_ID = "id";// 编号
		public static final String GROUPON_COUPON_JSON_KEY_NAME = "name";// 名称
		public static final String GROUPON_COUPON_JSON_KEY_LIMITTIME = "limitTime";// 有效期
		public static final String GROUPON_COUPON_JSON_KEY_GROUPON_NAME = "grouponName";
		public static final String GROUPON_COUPON_JSON_KEY_TICKET_CODE = "grouponTicketCode";
		public static final String GROUPON_COUPON_JSON_KEY_UPDATE_DATE = "updateDate";
		
		//nearby shop
		public static final String NEARBY_SHOPLIST = "shopList";
		public static final String NEARBY_SHOP_ID = "id";
		public static final String NEARBY_SHOP_PICTURE_PATH = "picturePath";
		public static final String NEARBY_SHOP__NAME = "shopName";
		public static final String NEARBY_SHOP_BRIEF = "brief";
		public static final String NEARBY_SHOP_FAVOUR_NUM = "favourNum";
		public static final String NEARBY_SHOP_CLASSIFICATION = "classification";
		public static final String NEARBY_SHOP_EVALUATION = "evaluation";
		public static final String NEARBY_SHOP_NOTICE = "notify";
		
		//nearby search
		public static final String NEARBY_SEARCH_SHOPLIST = "shopList";
		public static final String NEARBY_SEARCH_SHOPID = "shopId";
		public static final String NEARBY_SEARCH_FLOOR = "floor";
		public static final String NEARBY_SEARCH_SHOP_NAME = "shopName";
		public static final String NEARBY_SEARCH_CLASS_NAME = "classification";
		public static final String NEARBY_SEARCH_LOC_X = "locationX";
		public static final String NEARBY_SEARCH_LOC_Y = "locationY";
		public static final String NEARBY_SEARCH_STORE_NUM = "storeNo";
		public static final String NEARBY_SEARCH_LOCATE = "rotate";
		public static final String NEARBY_SEARCH_FLOOR_ID = "floorid";
		
		//读取区域信息
		public static final String QUERY_ADDRESS_COMMUNITY_LIST = "communityList";
		public static final String QUERY_ADDRESS_ID = "id";
		public static final String QUERY_ADDRESS_NAME = "name";
		public static final String QUERY_ADDRESS_PROVINCE = "province";
		public static final String QUERY_ADDRESS_CITY = "city";
		public static final String QUERY_ADDRESS_AREA = "area";
		public static final String QUERY_ADDRESS_ZIPCODE = "zipCode";

	}

	// define dialog identity
	public static final class DialogIdentity {
		public static final int DLG_LOGOUT = 0;
		public static final int DLG_EDIT_GOODS = 1;
	}

	// define httpclient callback
	public static final class HttpStatus {
		public static final int SUCCESS = 0;// 拉取数据成功
		public static final int NORMAL_EXCEPTION = 1;// 服务器回复的异常
		public static final int CONNECTION_EXCEPTION = 2;// 服务器通信异常,通信超时等
	}

	// define location status
	public static final class LocationStatus {
		public static final int LOADING = 0;
		public static final int SUCCESS = 1;
		public static final int FAILED = 2;
	}

	// define search list type
	public static final class ListType {
		public static final int NORMAL = 0;
		public static final int SEARCHING = 1;
		public static final int MATCHED = 2;
		public static final int SUMMARY = 3;
	}

	// define search history filter type
	public static final class FilterType {
		public static final int ALL = 0;// 商品&店铺
		public static final int GOODS = 1;// 商品
		public static final int SHOPS = 2;// 店铺
	}

	// define sort type
	public static final class SortType {
		public static final int POPULAR = 0;// 人气
		public static final int SALES = 1;// 销量
		public static final int PRICE = 2;// 价格
		public static final int CATEGORY = 3;
		public static final int GRADE = 4;

		public static final int USED = 2;// 已使用
		public static final int NOT_USED = 1;// 未使用
		public static final int EXPIRED = 0;// 已过期

		public static final int ALL = 9;// 全部
		public static final int SYSTEM = 0;// 系统
		public static final int TRADE = 1;// 交易
	}

	// define favour operation type
	public static final class FAVOUROPERATION {
		public static final int ADD = 0;// 添加
		public static final int DEL = 1;// 删除
	}

	// define Registraion type
	public static final class RegisterType {
		public static final int MOBILE = 0;// 手机注册
		public static final int MAIL = 1;// 邮箱注册
	}
	
	//define recommend link type
	public static final class RecommendLinkType{
		public static final int LINK = 0;//链接
		public static final int SHOP = 1;//商家
		public static final int ITEM = 2;//商品
		public static final int GROUPON = 3;//团购
		public static final int ACTIVITY = 4;//活动
		public static final int GROUPON_LIST = 5;
		public static final int Brand = 6;
	}

	public static int displayWidth;
	public static int displayHeight;
	public static float screen_density;

	public static int mallId = 1;
	public static boolean isMallChanged = false;
	public static final class ResultType {
		public static final int RESULT_FROM_ADDRESS = 100;
		public static final int RESULT_FROM_COUPON = 101;
		public static final int RESULT_FROM_SEND = 103;
		public static final int RESULT_FROM_PAY = 104;
		public static final int RESULT_FROM_TIME = 105;
		public static final int RESULT_FROM_SHOP_COUPON = 106;
		public static final int RESULT_FROM_MANAGE_ADDRESS = 107;
		public static final int RESULT_FROM_ADD_ADDRESS = 108;
		public static final int RESULT_FROM_DELETE_ADDRESS = 109;
		public static final int RESULT_FROM_UPOMP = 2001;
	}

	public static int[] MIDDLE_INDEX = null;

	public static String channelCode = "baolongtiandi";

	public static String platform = "1";
	public static Collection TEMPLATE_COLL = new ArrayList();
	public static int VIEW_COUNT = 0;

	public static int unReadNotifyNum = 0;
	public static int unReadMessageNum = 0;
	public static String userIcon;
	public static String userName;
	public static String nickName;
	public static String logUserName;
	public static String logUserPassword;
	public static int id;
	public static long userId;

	public static LCache mBmpCache = null;
	public static int finishCount=0;
	public static int noPayCount=0;
	public static int waitRecvCount=0;
	public static int waitSendCount=0;
	
	public final static int GROUPON_STATE_WAIT = 0;
	public final static int GROUPON_STATE_START = 1;
	public final static int GROUPON_STATE_END = 2;

}
