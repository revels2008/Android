/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * HttpUtil.java
 * 
 * 2013-8-8-上午11:26:03
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Entity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Handler;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.CustomerHttpClient;
import com.powerlong.electric.app.dao.NavigationActivityDao;
import com.powerlong.electric.app.dao.NavigationBaseDao;
import com.powerlong.electric.app.dao.SearchCategoriesDao;
import com.powerlong.electric.app.entity.ChangeItemEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;

/**
 * 
 * HttpUtil:服务器通信工具类
 * 
 * 
 * @author: Liang Wang 2013-8-8-上午11:26:03
 * 
 * @version 1.0.0
 * 
 */
public class HttpUtil {
	private final static int DEFAULT_TIME_OUT = 15 * 1000;

	/**
	 * 从对应URL获取服务器返回的数据
	 * 
	 * @param url
	 *            获取数据的url
	 * @param JsonHttpResponseHandler
	 *            网络通信结果回调
	 */
	public static void getJson(String url, String requestParams,
			JsonHttpResponseHandler handler) {
		LogUtil.d("HttpUtil", "url = " + url + ",requestParams = "
				+ requestParams);

		makeRequest(url, requestParams, DEFAULT_TIME_OUT, handler);
	}

	/**
	 * 服务器请求函数
	 * 
	 * @param url
	 *            获取数据的url
	 * @param timeout
	 *            网络通信连接时限
	 * @param JsonHttpResponseHandler
	 *            网络通信结果回调 String
	 * @exception
	 * @since 1.0.0
	 */
	private static void makeRequest(String url, String requestParams,
			int timeout, JsonHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (!StringUtil.isEmpty(requestParams))
			params.put("data", requestParams);
		client.setTimeout(timeout);
		client.post(url, params, handler);
	}

	/**
	 * 从对应URL获取服务器返回的数据
	 * 
	 * @param <T>
	 *            数据库泛型
	 * @param url
	 *            获取数据的url
	 * @param JsonHttpResponseHandler
	 *            网络通信结果回调
	 */
	public static <T> void getSyncJson(final T entityType, final String navId,
			final String url, final String requestParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getSyncJson:url = " + url + ",requestParams = "
				+ requestParams);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data",
							requestParams));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);

					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getSyncJson:status= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());

						if (!checkResultCode(result, handler)) {
							return;
						}

						LogUtil.d("HttpUtil", "getSyncJson:result= " + result);
						if (entityType instanceof NavigationActivityDao) {
							NavigationActivityDao dao = (NavigationActivityDao) entityType;
							LogUtil.d("HttpUtil", "NavigationActivityDao");
							boolean ret = JSONParser
									.parseActivityListJson(result);
							if (ret)
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS,
										dao.find()).sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else if (entityType instanceof NavigationBaseDao) {
							LogUtil.d("HttpUtil", "NavigationBaseDao");
							NavigationBaseDao dao = (NavigationBaseDao) entityType;
							boolean ret = JSONParser.parseNavItemJson(dao,
									navId, result);
							if (ret)
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS,
										StringUtil.toInt(navId), -1,
										dao.getDataById(navId)).sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}

						}
					} else {
//						handler.obtainMessage(
//								Constants.HttpStatus.CONNECTION_EXCEPTION,
//								"网络通信异常,请检查网络状态." + status).sendToTarget();
					}
				} catch (Exception e) {
					/*
					 * e.printStackTrace(); handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}

			}
		}).start();
	}

	/**
	 * checkParserStatus(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param ret
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected static void checkParserStatus(boolean ret,
			ServerConnectionHandler handler) {
		if (ret)
			handler.obtainMessage(Constants.HttpStatus.SUCCESS, "加载成功.")
					.sendToTarget();
		else {
			handler.obtainMessage(Constants.HttpStatus.NORMAL_EXCEPTION,
					"数据解析失败.").sendToTarget();
		}
	}

	/**
	 * 根据关键字获取匹配信息
	 * 
	 * @param url
	 *            服务器地址
	 * @param param
	 *            请求参数
	 * @param filterTyppe
	 *            关键字类型
	 * @param ServerConnectionHandler
	 *            回调实例
	 * @exception
	 * @since 1.0.0
	 */
	public static void getFloorDetailsJson(Context context,
			final String method, final String methodParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getFloorDetailsJson method = " + method
				+ ",methodParams = " + methodParams);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data",
							methodParams));
					String url = method;
					if (!method.endsWith("?"))
						url = method + "?";
					LogUtil.d(
							"HttpUtil",
							"getFloorDetailsJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getFloorDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getFloorDetailsJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseFloorDetailJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					/*
					 * e.printStackTrace(); handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取导航楼层详情
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getFloorDetailsJsonOld(Context context,
			final String method, final String methodParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getDetailsJson method = " + method
				+ ",methodParams = " + methodParams);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data",
							methodParams));
					String url = method;
					if (!method.endsWith("?"))
						url = method + "?";
					httpPost = new HttpPost(url);
					LogUtil.v("HttpUtil", "getDetailsJson url = " + url);
					LogUtil.v("HttpUtil", "getDetailsJson methodParams = "
							+ methodParams);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getDetailsJson:status= " + status);

					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getDetailsJson:result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseFloorDetailJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					// LogUtil.d("HttpUtil", "getDetailsJson:err= " +
					// "网络通信异常,请检查网络状态.");
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget(); e.printStackTrace();
					 */

				}
			}
		}).start();
	}

	/**
	 * 获取搜索分类列表信息
	 * 
	 * @param dao
	 *            数据库实例
	 * @param url
	 *            服务器地址
	 * @param jsonParams
	 *            服务器参数
	 * @param handler
	 *            回调实例
	 * @return void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getSearchCategroyJson(final SearchCategoriesDao dao,
			final String url, final String jsonParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getSearchCategroyJson url = " + url);
		LogUtil.d("HttpUtil", "getSearchCategroyJson jsonParams = "
				+ jsonParams);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair
							.add(new BasicNameValuePair("data", jsonParams));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getSearchCategroyJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getSearchCategroyJson result=  "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseSearchCategoriesJson(dao,
								result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					/*
					 * e.printStackTrace(); handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 根据关键字获取匹配信息
	 * 
	 * @param url
	 *            服务器地址
	 * @param filter
	 *            关键字
	 * @param filterTyppe
	 *            关键字类型
	 * @param ServerConnectionHandler
	 *            回调实例
	 * @exception
	 * @since 1.0.0
	 */
	public static void getHotKeywordsJson(final String url,
			final String filter, final int filterTyppe,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getHotKeywordsJson url = " + url + ",filter = "
				+ filter + ",filterType =" + filterTyppe);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("mall", Constants.mallId);
					jsonParams.put("keyword", filter);
					if (filterTyppe == Constants.KEYWORDS_TYPE_HOTWORDS) {
						jsonParams.put("page", 1);
						jsonParams.put("classification", "keyword");
					} else if (filterTyppe == Constants.KEYWORDS_TYPE_GOODS) {
						jsonParams.put("page", 1);
						jsonParams.put("classification", "item");
					} else if (filterTyppe == Constants.KEYWORDS_TYPE_SHOP) {
						jsonParams.put("page", 1);
						jsonParams.put("classification", "shop");
					} else if (filterTyppe == Constants.KEYWORDS_TYPE_GROUPON) {
						jsonParams.put("page", 1);
						jsonParams.put("classification", "groupon");
					}

					LogUtil.d("HttpUtil", "getHotKeywordsJson params = "
							+ jsonParams.toString());
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));
					LogUtil.d(
							"HttpUtil",
							"getHotKeywordsJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getHotKeywordsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getHotKeywordsJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseSearchCategoriesJson(
								result, filterTyppe);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					/*
					 * e.printStackTrace(); handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 根据关键字获取匹配信息
	 * 
	 * @param url
	 *            服务器地址
	 * @param param
	 *            请求参数
	 * @param filterTyppe
	 *            关键字类型
	 * @param ServerConnectionHandler
	 *            回调实例
	 * @exception
	 * @since 1.0.0
	 */
	public static void getHotKeywordsParamsJson(final String url,
			final String param, final int filterTyppe,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getHotKeywordsParamsJson url = " + url
				+ ",params = " + param + ",filterTyppe =" + filterTyppe);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"getHotKeywordsParamsJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getHotKeywordsParamsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"getHotKeywordsParamsJson result= " + result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseSearchCategoriesJson(
								result, filterTyppe);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取购物车列表信息
	 * 
	 * @param handler
	 *            回调实例
	 * @exception
	 * @since 1.0.0
	 */
	public static void getCartListJson(final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getCartListJson...");

		// LogUtil.d("HttpUtil", "getCartListJson..tgc = "+
		// DataCache.UserDataCache.get(0).getTGC());

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject obj = new JSONObject();
					obj.put("mall", Constants.mallId + "");
					obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					LogUtil.d("HttpUtil",
							"getCartListJson param= " + obj.toString());
					nameValuePair.add(new BasicNameValuePair("data", obj
							.toString()));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_CART_LIST_URL);
					LogUtil.d("HttpUtil", "getCartListJson url= "
							+ Constants.ServerUrl.GET_CART_LIST_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getCartListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getCartListJson result=  "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseCartList(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取品牌详情
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getBrandDetailsJson(Context context,
			final String method, final String methodParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getBrandDetailsJson url = " + method);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data",
							methodParams));
					httpPost = new HttpPost(method);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getBrandDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getBrandDetailsJson result=  "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseBrandDetailsJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取团购详情
	 * 
	 * @param context
	 * @param method
	 *            方法
	 * @param methodParams
	 *            方法参数
	 * @param handler
	 *            回调实例
	 * @exception
	 * @since 1.0.0
	 */
	public static void getBGrouponDetailsJson(Context context,
			final String method, final String methodParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getBGrouponDetailsJson url = " + method);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data",
							methodParams));
					httpPost = new HttpPost(method);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getBGrouponDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"getBGrouponDetailsJson result=  " + result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser
								.parseGrouponDetailsJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取购物车结算信息
	 * 
	 * @param handler
	 * @param param
	 *            参数{"mall":1,"TGC":"9B883D4AFB314458BB9E6447BF39F71D",
	 *            "shopList"
	 *            :{"id":"77","itemList":{"id":"205","type":"0","buyNum":"1"}}}
	 * @exception
	 * @since 1.0.0
	 */
	public static void getCartCountJson(final ServerConnectionHandler handler,
			final String param) {
		LogUtil.d("HttpUtil", "getCartCountJson...");
		LogUtil.d("HttpUtil", "getCartCountJson...+param=" + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d("HttpUtil", "getCartCountJson...+param=" + param);
					String url = Constants.ServerUrl.GET_CART_COUNT_INFO_URL;
					httpPost = new HttpPost(url);
					LogUtil.d("HttpUtil", "getCartCountJson...+url=" + url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getCartCountJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getCartCountJson result=  "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseCartCount(result);
						// checkParserStatus(ret,handler);
						handler.obtainMessage(Constants.HttpStatus.SUCCESS)
								.sendToTarget();
					} else {
						handler.obtainMessage(
								Constants.HttpStatus.CONNECTION_EXCEPTION,
								"" + status).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.obtainMessage(
							Constants.HttpStatus.CONNECTION_EXCEPTION,
							e.toString()).sendToTarget();
				}
			}
		}).start();
	}

	/**
	 * 下订单
	 * 
	 * @param handler
	 * @param param
	 *            参数{"mall":1,"TGC":"EC78B2914CB24DA89736323ADE6ED812",
	 *            "addressId":"1","logisticsId":"1","receiveTime":"2013-01-01",
	 *            "price"
	 *            :"100","freight":"20","plCashCouponId":"1","shopList":{"id"
	 *            :"1","cashCouponId":"1","message":"测试数据"}}
	 * @exception
	 * @since 1.0.0
	 */
	public static void enterSettle(final ServerConnectionHandler handler,
			final String param) {
		LogUtil.d("HttpUtil", "getCartCountJson prama=" + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(Constants.ServerUrl.ADD_ORDER_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getCartCountJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getCartCountJson result=  "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseEnterSettle(result);
						// checkParserStatus(ret,handler);
						handler.obtainMessage(Constants.HttpStatus.SUCCESS)
								.sendToTarget();
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取宝龙抵用券信息
	 * 
	 * @param handler
	 *            回调实例
	 * @param param
	 *            参数 {"mall":4,"TGC":"F5ED2F5FE9884DACAE44A8F802E224B2","price":
	 *            "500"}
	 * @exception
	 * @since 1.0.0
	 */
	public static void getPlCashCouponListJson(
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "getPlCashCouponListJson...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_PL_CASHCOUPON_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getPlCashCouponListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"getPlCashCouponListJson result=  " + result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parsePlCashCoupon(result);
						checkParserStatus(ret, handler);
					} else {
						handler.obtainMessage(
								Constants.HttpStatus.CONNECTION_EXCEPTION,
								"" + status).sendToTarget();
					}
				} catch (Exception e) {
//					e.printStackTrace();
//					handler.obtainMessage(
//							Constants.HttpStatus.CONNECTION_EXCEPTION,
//							"网络通信异常,请检查网络状态.").sendToTarget();
				}
			}
		}).start();
	}

	/**
	 * 获取用户收货地址
	 * 
	 * @param handler
	 * @exception
	 * @since 1.0.0
	 */
	public static void getUserAddressListJson(
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getUserAddressListJson...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject json = new JSONObject();
					json.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					json.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", json
							.toString()));
					String url = "192.168.0.77:8080/userWeb/userMobileCenter/findAllAddress.htm?";
					httpPost = new HttpPost(//url
							Constants.ServerUrl.GET_USER_ADDRESS_LIST_URL);
					LogUtil.d("HttpUtil", "getUserAddressListJson url= " + "192.168.0.77:8080/userWeb/userMobileCenter/findAllAddress.htm?");
					LogUtil.d("HttpUtil", "getUserAddressListJson url= " + Constants.ServerUrl.GET_USER_ADDRESS_LIST_URL);
					LogUtil.d("HttpUtil", "getUserAddressListJson param= " + json
							.toString());
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getUserAddressListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"getUserAddressListJson result=  " + result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseUserAddress(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 删除用户地址信息
	 * 
	 * @param handler
	 *            回调实例
	 * @param addressId
	 *            要删除的地址编号
	 * @exception
	 * @since 1.0.0
	 */
	public static void delUserAddressById(
			final ServerConnectionHandler handler, final String addressId) {
		LogUtil.d("HttpUtil", "getUserAddressListJson...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject json = new JSONObject();
					json.put("id", addressId);
					json.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", json
							.toString()));
					httpPost = new HttpPost(
							Constants.ServerUrl.DEL_USER_ADDRESS_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getUserAddressListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									status).sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.CONNECTION_EXCEPTION,
									msg).sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
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
	public static void setUserDefaultAddress(
			final ServerConnectionHandler handler, final String addressId) {
		LogUtil.d("HttpUtil", "setUserDefaultAddress...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					JSONObject json = new JSONObject();
					json.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					json.put("id", addressId);
					json.put("mall", Constants.mallId);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", json
							.toString()));
					httpPost = new HttpPost(
							Constants.ServerUrl.SET_USER_DEFAULT_ADDRESS_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "setUserDefaultAddress= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									status).sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.CONNECTION_EXCEPTION,
									msg).sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
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
	public static void AddUserAddress(final ServerConnectionHandler handler,
			final String param) {
		LogUtil.d("HttpUtil", "AddUserAddress...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.ADD_USER_ADDRESS_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					LogUtil.d("AddressAddActivity  url=",
							Constants.ServerUrl.ADD_USER_ADDRESS_URL);
					LogUtil.d("AddressAddActivity  param=", param);
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "AddUserAddress= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									status).sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.CONNECTION_EXCEPTION,
									msg).sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 读取区域信息
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryAddressInfoJson(final Context context,
			final ServerConnectionHandler handler, final String name) {
		LogUtil.d("HttpUtil", "queryAddressInfoJson...+ name = " + name);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					JSONObject json = new JSONObject();
					json.put("mall", Constants.mallId);
					json.put("name", name);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", json
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.LOAD_COMMUNITY_URL);
					LogUtil.d("HttpUtil", "queryAddressInfoJson...+ uri = "
							+ Constants.ServerUrl.LOAD_COMMUNITY_URL);
					LogUtil.d("HttpUtil", "queryAddressInfoJson...+ param = "
							+ name);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryAddressInfoJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "queryAddressInfoJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseQueryAddressJson(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, "查询成功")
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"查询关键字失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
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
	public static void ChangeItemNum(final ServerConnectionHandler handler,
			final ChangeItemEntity addEntity) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				LogUtil.d("HttpUtil", "ChangeItemNum...");

				JSONObject param = new JSONObject();
				HttpPost httpPost = null;
				try {

					param.put(
							Constants.JSONKeyName.CHANGE_ITEM_NUM_JSON_OBJ_KEY_ITEMID,
							addEntity.getItemId());
					param.put(
							Constants.JSONKeyName.CHANGE_ITEM_NUM_JSON_OBJ_KEY_SHOPID,
							addEntity.getShopId());
					param.put(
							Constants.JSONKeyName.CHANGE_ITEM_NUM_JSON_OBJ_KEY_USERID,
							addEntity.getUserId());
					param.put(
							Constants.JSONKeyName.CHANGE_ITEM_NUM_JSON_OBJ_KEY_ITEMNUM,
							addEntity.getItemNum());
					param.put(
							Constants.JSONKeyName.CHANGE_ITEM_NUM_JSON_OBJ_KEY_MALL,
							addEntity.getMallId());
					param.put(
							Constants.JSONKeyName.CHANGE_ITEM_NUM_JSON_OBJ_KEY_CARDID,
							addEntity.getCartId());
					// 绑定到请求 Entry
					StringEntity se = new StringEntity(param.toString());
					LogUtil.d("HttpUtil", "param=" + param);
					httpPost = new HttpPost(
							Constants.ServerUrl.CHANGE_ITEM_NUM_URL);
					httpPost.setEntity(se);
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "ChangeItemNum= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"修改商品数量成功.").sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 收藏商品进入购物车
	 * 
	 * @param handler
	 *            回调实例
	 * @param praram
	 *            数据传参
	 *            data={"mall":"1","TGC":"1",itemList":[{"buyNum":"10","id":"
	 *            1","type":"1"},{"buyNum":"10","id":"2","type":"1"}]}
	 * @return
	 */
	public static void AddFavoirToCart(final ServerConnectionHandler handler,
			final String param) {
		LogUtil.d("HttpUtil", "AddFavoirToCart...");
		LogUtil.d("HttpUtil", "AddFavoirToCart...+param=" + param);
		Log.i("CartFinal", "add to favour param = " + param);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					LogUtil.d("HttpUtil", "AddFavoirToCart params = " + param);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					String url = Constants.ServerUrl.ADD_FAVOUR_TO_CART_URL;

					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "AddFavoirToCart status= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									status).sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						handler.obtainMessage(
								Constants.HttpStatus.CONNECTION_EXCEPTION,
								"" + status).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
//					handler.obtainMessage(
//							Constants.HttpStatus.CONNECTION_EXCEPTION,
//							"网络通信异常,请检查网络状态.").sendToTarget();
				}
			}
		}).start();

	}

	/**
	 * 加减购物车商品
	 * 
	 * @param handler
	 *            回调实例
	 * @param param
	 *            数据传参
	 *            data={"TGC":"32AC7F87DCD544948BA991EF40957BCC","mall":1,"shopId"
	 *            :75,"itemId":180,"itemNum":11,"type":1,"shoppingId":70}
	 * @param context
	 *            上下文实例
	 * @return
	 */
	public static void requestChangeItemNum(
			final ServerConnectionHandler handler, final String param,
			Context context) {
		LogUtil.d("HttpUtil", "requestChangeItemNum...");
		LogUtil.d("HttpUtil", "requestChangeItemNum...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));
					LogUtil.d("HttpUtil",
							"requestChangeItemNum params1 = "
									+ new UrlEncodedFormEntity(nameValuePair,
											"utf-8").toString());
					// URI uri =
					// URI.create(Constants.ServerUrl.CHANGE_ITEM_NUM_URL);
					String url = Constants.ServerUrl.CHANGE_ITEM_NUM_URL;
					LogUtil.d("HttpUtil", "requestChangeItemNum...+ uri = "
							+ url);
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestChangeItemNum= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"修改商品数量成功.").sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 将商品添加/移出收藏
	 * 
	 * @param handler
	 *            回调实例
	 * @param addEntity
	 *            商品变化实体
	 * @param operType
	 *            操作种类 ：0：添加；1：删除
	 * @return
	 */
	public static void requestAddOrDeleteItemToFavour(
			final ServerConnectionHandler handler, final String param,
			final int operType) {
		LogUtil.d("HttpUtil", "sendUserFavourOperation...");
		LogUtil.d("HttpUtil", "sendUserFavourOperation...+param=" + param);
		LogUtil.d("HttpUtil", "sendUserFavourOperation...+operType=" + operType);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					LogUtil.d("HttpUtil", "sendUserFavourOperation params = "
							+ param);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					String url = operType == Constants.FAVOUROPERATION.ADD ? Constants.ServerUrl.ADD_ITEM_TO_FAVOUR_URL
							: Constants.ServerUrl.DEL_ITEM_FROM_FAVOUR_URL;
					LogUtil.d("HttpUtil", "sendUserFavourOperation url = "
							+ url);
					String info = operType == Constants.FAVOUROPERATION.ADD ? "添加到收藏成功."
							: "移出收藏成功.";
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "sendUserFavourOperation status= "
							+ status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									info).sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						handler.obtainMessage(
								Constants.HttpStatus.CONNECTION_EXCEPTION,
								"" + status).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.obtainMessage(
							Constants.HttpStatus.CONNECTION_EXCEPTION,
							"网络通信异常,请检查网络状态.").sendToTarget();
				}
			}
		}).start();
	}

	/**
	 * 获取品牌李彪
	 * 
	 * @param handler
	 *            回调实例
	 * @param url
	 *            服务器地址 void
	 * @param mallId
	 *            商场编号
	 * @exception
	 * @since 1.0.0
	 */
	public static void getBrandListJson(final String url,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getBrandListJson...+url =" + url);
		LogUtil.d("HttpUtil", "getBrandListJson...+mallId =" + Constants.mallId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("mall", Constants.mallId);

					LogUtil.d("HttpUtil", "getBrandListJson params = "
							+ jsonParams.toString());
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));
					LogUtil.d(
							"HttpUtil",
							"getBrandListJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());

					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getBrandListJson status= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getBrandListJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseBrandList(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取店铺详情
	 * 
	 * @param 服务器地址
	 * @param handler
	 *            回调实例
	 * @param param
	 *            json参数
	 * @exception
	 * @since 1.0.0
	 */
	public static void getShopDetailsJson(final String url,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "getShopDetailsJson...+ url=" + url);
		LogUtil.d("HttpUtil", "getShopDetailsJson...+ param=" + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					/*
					 * JSONObject jsonParams = new JSONObject();
					 * jsonParams.put("shopId", shopId+"");
					 * jsonParams.put("orderType", 0);
					 */
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getShopDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getShopDetailsJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseShopDetails(result);
						checkParserStatus(ret, handler);
					} else {
						handler.obtainMessage(
								Constants.HttpStatus.CONNECTION_EXCEPTION,
								"" + status).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
//					handler.obtainMessage(
//							Constants.HttpStatus.CONNECTION_EXCEPTION,
//							"网络通信异常,请检查网络状态.").sendToTarget();
				}
			}
		}).start();
	}

	/**
	 * 获取商品详情
	 * 
	 * @param 服务器地址
	 * @param handler
	 *            回调实例
	 * @param itemId
	 *            商品编号
	 * @exception
	 * @since 1.0.0
	 */
	public static void getItemDetailsJson(final String url,
			final ServerConnectionHandler handler, final long itemId) {
		LogUtil.d("HttpUtil", "getItemDetailsJson... + url=" + url);
		LogUtil.d("HttpUtil", "getItemDetailsJson...+ itemId=" + itemId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("itemId", itemId);
					jsonParams.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getItemDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemDetailsJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseItemDetails(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取商品评价信息 (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param url
	 *            地址
	 * @param handler
	 *            回调实例
	 * @param param
	 *            传参
	 * @exception
	 * @since 1.0.0
	 */
	public static void getItemCommentsJson(final String url,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "getItemCommentsJson... + url=" + url);
		LogUtil.d("HttpUtil", "getItemCommentsJson...+ param=" + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					// JSONObject jsonParams = new JSONObject();
					// jsonParams.put("itemId", itemId);

					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getItemDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemDetailsJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseItemComments(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * getGroupListJson(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param url
	 *            地址
	 * @param classId
	 *            团购类型
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getGroupListJson(final String url, final String param,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getGroupListJson... + url=" + url);
		LogUtil.d("HttpUtil", "getGroupListJson...+ param=" + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getGroupListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getGroupListJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseGroupList(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取活动列表
	 * 
	 * @param url
	 *            地址
	 * @param handler
	 *            回调实例 void
	 * 
	 * @param pageIndex
	 *            第几页
	 * @exception
	 * @since 1.0.0
	 */
	public static void getNavActvityJson(final String url,
			final ServerConnectionHandler handler, final int pageIndex) {
		LogUtil.d("HttpUtil", "getNavActvityJson... + url=" + url);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("mall", Constants.mallId);
					jsonParams.put("page", pageIndex);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getNavActvityJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getNavActvityJson result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseActivityListJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取活动详情
	 * 
	 * @param url
	 *            地址
	 * @param handler
	 * @param activityId
	 *            活动编号 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getNavActvityDetailsJson(final String url,
			final ServerConnectionHandler handler, final long activityId) {
		LogUtil.d("HttpUtil", "getNavActvityDetailsJson... + url=" + url);
		LogUtil.d("HttpUtil", "getNavActvityDetailsJson...+ activityId="
				+ activityId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("activityId", activityId);
					jsonParams.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getNavActvityDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"getNavActvityDetailsJson result= " + result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser
								.parseActivityDetailJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	public static boolean checkResultCode(String result,
			ServerConnectionHandler handler) {
		int code = JSONUtil.getInt(result,
				Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE, -1);
		if (code == 0) {
			return true;
		} else {
			String msg = JSONUtil.getString(result,
					Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG, null);
			handler.obtainMessage(Constants.HttpStatus.NORMAL_EXCEPTION, msg)
					.sendToTarget();
			return false;
		}
	}

	/**
	 * 获取导航购物单项详细信息
	 * 
	 * @param context
	 * @param method
	 * @param methodParams
	 * @param handler
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getNavShoppingItemJson(Context context,
			final String method, final String methodParams,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getNavShoppingItemJson method = " + method
				+ ",methodParams = " + methodParams);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data",
							methodParams));
					String url = method;
					if (!method.endsWith("?"))
						url = method + "?";
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getNavShoppingItemJson:status= "
							+ status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getNavShoppingItemJson:result= "
								+ result);
						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parseFloorDetailJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
					e.printStackTrace();

				}
			}
		}).start();
	}

	/**
	 * 发送登录请求
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            用户密码
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void requestLogin(final Context context,
			final String username, final String pwd,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "requestLogin...");
		LogUtil.d("HttpUtil", "username =" + username);
		LogUtil.d("HttpUtil", "pwd = " + pwd);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put(Constants.JSONKeyName.LOGIN_JSON_KEY_PWD,
							pwd);
					jsonParams.put(
							Constants.JSONKeyName.LOGIN_JSON_KEY_USERNAME,
							username);
					jsonParams.put("mall", Constants.mallId);
					LogUtil.d("HttpUtil",
							"requestLogin params = " + jsonParams.toString());
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));

					URI uri = URI.create(Constants.ServerUrl.LOGIN_URL);
					LogUtil.d("HttpUtil",
							"requestLogin url = " + uri.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestLogin= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestLogin result= " + result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseUserDataJson(context, result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取团购详情
	 * 
	 * @param context
	 * @param groupId
	 *            团购编号
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getGrouponDetailsJson(final Context context,
			final long groupId, final ServerConnectionHandler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("grouponId", groupId);
					jsonParams.put("mall", Constants.mallId);
					LogUtil.d("HttpUtil", "getGrouponDetailsJson params = "
							+ jsonParams.toString());
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.GET_GROUPON_DETAILS_URL);
					httpPost = new HttpPost(uri);
					LogUtil.d("HttpUtil",
							"getGrouponDetailsJson uri = " + uri.toString());
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getGrouponDetailsJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getGrouponDetailsJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseGrouponDetailDataJson(context,
									result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取我的抵用券列表
	 * 
	 * @param context
	 * @param handler
	 * @param pageIndex
	 *            第几页 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getMyBargainListData(final Context context,
			final ServerConnectionHandler handler, final int pageIndex,
			final String keyword, final int type) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					JSONObject jsonParams = new JSONObject();
					jsonParams.put("TGC", DataCache.UserDataCache.get(0)
							.getTGC());
					jsonParams.put("page", pageIndex);
					jsonParams.put("type", type);
					jsonParams.put("keyword", keyword);
					jsonParams.put("mall", Constants.mallId);
					LogUtil.d("HttpUtil", "getMyBargainListData params = "
							+ jsonParams.toString());
					nameValuePair.add(new BasicNameValuePair("data", jsonParams
							.toString()));
					LogUtil.d(
							"HttpUtil",
							"getMyBargainListData params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI
							.create(Constants.ServerUrl.GET_MY_BARGAINLIST_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getMyBargainListData= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getMyBargainListData result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseMyBarginListJson(context,
									result)) {
								LogUtil.d("HttpUtil",
										"getMyBargainListData out 1");
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							} else {
								LogUtil.d("HttpUtil",
										"getMyBargainListData out 2");
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							LogUtil.d("HttpUtil", "getMyBargainListData out 3");
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						LogUtil.d("HttpUtil", "getMyBargainListData out 4");
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.d("HttpUtil", "getMyBargainListData out 5");
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 判断用户是否需要短信验证
	 * 
	 * @param param
	 *            参数
	 * @param context
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void isNeedVerify(final Context context,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "isNeedVerify...");
		// LogUtil.d("HttpUtil", "isNeedVerify... param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", ""));
					URI uri = URI.create(Constants.ServerUrl.IS_NEED_VERIGY);
					LogUtil.d("HttpUtil", "isNeedVerify uri=" + uri);
					httpPost = new HttpPost(uri);
					// httpPost.setEntity(new
					// UrlEncodedFormEntity(nameValuePair,
					// "utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}

					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "isNeedVerify= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "isNeedVerify result= " + result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							JSONObject data = JSONUtil.getJSONObject(result,
									Constants.JSONKeyName.NAV_JSON_TOPEST_DATA,
									null);
							if (data == null)
								return;
							int type = JSONUtil.getInt(data, "type", -1);
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									type).sendToTarget();
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 
	 * 获取短信验证码
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param params
	 *            参数 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getMessageCode(final Context context,
			final ServerConnectionHandler handler, final String param) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					LogUtil.d("HttpUtil", "getMessageCode params = " + param);
					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"getMessageCode param1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI
							.create(Constants.ServerUrl.GET_MESSAGE_CODE_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getMessageCode= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getMessageCode result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseMyBarginListJson(context,result)){
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									msg).sendToTarget();
							// }
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 发送注册请求
	 * 
	 * @param param
	 *            参数
	 * @param context
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void requestRegister(final Context context,
			final String param, final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "requestRegister...");
		LogUtil.d("HttpUtil", "requestRegister... param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"requestRegister params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI.create(Constants.ServerUrl.SEND_REGISTER_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestRegister= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestRegister result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									msg).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	public static void requestNewRegister(final Context context,
			final String param, final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "requestRegister...");
		LogUtil.d("HttpUtil", "requestRegister... param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"requestRegister params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI
							.create(Constants.ServerUrl.NEW_SEND_REGISTER_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestRegister= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestRegister result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									msg).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 发送修改用户信息请求
	 * 
	 * @param param
	 *            参数
	 * @param context
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void requestEditProfile(final Context context,
			final String param, final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "requestRegister...");
		LogUtil.d("HttpUtil", "requestRegister... param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"requestLogin params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI
							.create(Constants.ServerUrl.EDIT_PROFILEINFO_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestLogin= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestLogin result= " + result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							saveUserData(context, param);
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									msg).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 将商品加入/移出购物车
	 * 
	 * @param param
	 *            {"mall":"1","TGC":"1",itemList":[{"buyNum":"10","id":"1","type
	 *            ":"1"},{"buyNum":"10","id":"2","type":"1"}]}
	 */
	public static void requestAddOrDeleteItemTocart(final Context context,
			final String param, final ServerConnectionHandler handler,
			final int operType) {
		LogUtil.d("HttpUtil", "requestAddItemTocart...");
		LogUtil.d("HttpUtil", "requestAddItemTocart... requestAddItemTocart = "
				+ param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"requestAddItemTocart params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					String url = operType == Constants.FAVOUROPERATION.ADD ? Constants.ServerUrl.ADD_ITEM_TO_CART
							: Constants.ServerUrl.DEL_ITEM_FROM_CART;
					LogUtil.d("HttpUtil", "requestAddItemTocart url = " + url);
					URI uri = URI.create(url);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestAddItemTocart= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestAddItemTocart result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"添加购物车成功").sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 查询购物车商品数
	 * 
	 * @param param
	 *            {"mall":"1","TGC":"1",itemList":[{"buyNum":"10","id":"1","type
	 *            ":"1"},{"buyNum":"10","id":"2","type":"1"}]}
	 */
	public static void getCartItemCount(final Context context,final ServerConnectionHandler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					param.put("mall", Constants.mallId);

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));
					LogUtil.d("HttpUtil","getCartItemCount params = "+ param.toString());
					String url = Constants.ServerUrl.GET_CART_ITEM_COUNT;
					LogUtil.d("HttpUtil", "requestAddItemTocart url = " + url);
					URI uri = URI.create(url);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestAddItemTocart= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestAddItemTocart result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							JSONObject data = JSONUtil.getJSONObject(result,
							Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
							if (data == null)
								return;
							long cartItemNum = JSONUtil.getLong(data,"cartItemNum", 0);
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									cartItemNum).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	public static void getCartTotalCount(final Context context,final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					param.put("mall", Constants.mallId);

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));
					LogUtil.d("HttpUtil","getCartItemCount params = "+ param.toString());
					String url = Constants.ServerUrl.GET_CART_ITEM_COUNT;
					LogUtil.d("HttpUtil", "requestAddItemTocart url = " + url);
					URI uri = URI.create(url);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestAddItemTocart= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestAddItemTocart result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							JSONObject data = JSONUtil.getJSONObject(result,
							Constants.JSONKeyName.NAV_JSON_TOPEST_DATA, null);
							if (data == null)
								return;
							long cartItemNum = JSONUtil.getLong(data,"cartItemNum", 0);
							handler.obtainMessage(7,
									cartItemNum).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}
	
	/**
	 * saveUserData(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected static void saveUserData(Context ctx, String param) {
		SharedPreferences pref = ctx.getSharedPreferences("profile",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();

		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(param);
			if (jsonObj
					.has(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NEW_MOBILE)) {
				String mobile = jsonObj
						.getString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NEW_MOBILE);
				DataCache.UserDataCache.get(0).setMobile(mobile);
				editor.putString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_MOBILE,
						mobile);
			}

			if (jsonObj
					.has(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NEW_EMAIL)) {
				String email = jsonObj
						.getString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NEW_EMAIL);
				DataCache.UserDataCache.get(0).setEmail(email);
				editor.putString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_EMAIL, email);
			}
			if (jsonObj
					.has(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NEW_NICKNAME)) {
				String nickname = jsonObj
						.getString(Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NEW_NICKNAME);
				DataCache.UserDataCache.get(0).setNickname(nickname);
				editor.putString(
						Constants.JSONKeyName.PROFILE_JSON_OBJ_KEY_NICKNAME,
						nickname);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据缓存版本号
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 * @exception
	 * @since 1.0.0
	 */
	public static void getDataVersionCode(final Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "getDataVersionCode...");
		LogUtil.d("HttpUtil", "getDataVersionCode... param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"getDataVersionCode params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI.create(Constants.ServerUrl.SEND_REGISTER_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "requestLogin= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "requestLogin result= " + result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseVesionCodeJson(context, result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取我的订单列表
	 * 
	 * @param context
	 * @param handler
	 *            回调实例 void
	 * @param keyWords
	 *            关键字
	 * @param pageIndex
	 *            第几页
	 * @exception
	 * @since 1.0.0
	 */
	public static void getMyOrderListJson(final Context context,
			final ServerConnectionHandler handler, final String keyWords,
			final int pageIndex) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					param.put("page", pageIndex);
					param.put("mall", Constants.mallId);
					param.put("keyword", keyWords);

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					LogUtil.d("HttpUtil", "getMyOrderListJson params1 = "
							+ param.toString());
					URI uri = URI
							.create(Constants.ServerUrl.GET_ORDER_LIST_URL);
					httpPost = new HttpPost(uri);
					LogUtil.d("HttpUtil", "getMyOrderListJson uri = " + uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getMyOrderListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getMyOrderListJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser
									.parseMyOrderListJson(context, result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取用户商品收藏列表
	 * 
	 * @param context
	 * @param handler
	 * @param pageIndex
	 *            第几页 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getItemFavourListJson(final Context context,
			final ServerConnectionHandler handler, final int pageIndex) {
		LogUtil.d("HttpUtil", "getItemFavourListJson");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {
					JSONObject obj = new JSONObject();
					obj.put("mall", Constants.mallId);
					obj.put("page", pageIndex);
					obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());

					LogUtil.d("getItemFavourListJson", "mall:"
							+ Constants.mallId + "  TGC:"
							+ DataCache.UserDataCache.get(0).getTGC()
							+ "  page:" + pageIndex);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", obj
							.toString()));
					LogUtil.d(
							"HttpUtil",
							"getItemFavourListJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI
							.create(Constants.ServerUrl.GET_ITEM_FAVOUR_LIST_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getItemFavourListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemFavourListJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseItemFavourListJson(context,
									result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取用户店铺收藏列表
	 * 
	 * @param context
	 * @param handler
	 * @param pageIndex
	 *            第几页
	 * @exception
	 * @since 1.0.0
	 */
	public static void getShopFavourListJson(final Context context,
			final ServerConnectionHandler handler, final int pageIndex) {
		LogUtil.d("HttpUtil", "getShopFavourListJson ");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject obj = new JSONObject();
					obj.put("mall", Constants.mallId);
					obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					obj.put("page", pageIndex);
					nameValuePair.add(new BasicNameValuePair("data", obj
							.toString()));
					LogUtil.d("HttpUtil", "getShopFavourListJson params1 = "
							+ obj.toString());
					URI uri = URI
							.create(Constants.ServerUrl.GET_SHOP_FAVOUR_LIST_URL);
					httpPost = new HttpPost(uri);
					LogUtil.d("HttpUtil", "getShopFavourListJson uri = " + uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getShopFavourListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getShopFavourListJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseShopFavourListJson(context,
									result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取用户某个收货地址
	 * 
	 * @param handler
	 * @param addressId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getUserAddressJson(
			final ServerConnectionHandler handler, final long addressId) {
		LogUtil.d("HttpUtil", "getUserAddressJson...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject json = new JSONObject();
					json.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					json.put("id", addressId);
					json.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", json
							.toString()));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_USER_ADDRESS_ByID_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getUserAddressJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getUserAddressJson result=  "
								+ result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseUserAddress(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * queryAdvertisementJson(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryAdvertisementJson(final Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryAdvertisementJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_HOME_RECOMMEND_INFO);

					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					// if(DataCache.UserDataCache.size() > 0) {
					// httpPost.setHeader("TGC",
					// DataCache.UserDataCache.get(0).getTGC());
					// }
					// httpPost.setHeader("mac", Constants.mac);
					// httpPost.setHeader("uid", Constants.userId+"");
					// httpPost.setHeader("client", "android");
					// httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryAdvertisementJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"queryAdvertisementJson result=  " + result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseAdvertisementJson(
								context, result);
						if (ret) {
							SharedPreferences pref = context
									.getSharedPreferences("homepage",
											Context.MODE_PRIVATE);
							Editor editor = pref.edit();
							editor.putString("homepage", result);
							editor.commit();
						}

						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 查询我的消息列表
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            传参
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryMyMessageListJson(Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryMyMessageListJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_MY_MESSAGE_LIST_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryMyMessageListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"queryMyMessageListJson result=  " + result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseMyMsgListJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取订单详情
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param orderId
	 *            订单号
	 * @exception
	 * @since 1.0.0
	 */
	public static void getMyOrderDetailJson(final Context context,
			final ServerConnectionHandler handler, final long orderId,
			final int stat) {
		LogUtil.d("HttpUtil", "getMyOrderDetailJson...+ orderId = " + orderId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("orderId", orderId + "");
					param.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					LogUtil.d(
							"HttpUtil",
							"getMyOrderDetailJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri;
					if (stat == 0) {
						uri = URI
								.create(Constants.ServerUrl.GET_PARENT_ORDER_DETAIL_URL);
					} else {
						uri = URI
								.create(Constants.ServerUrl.GET_ORDER_DETAIL_URL);
					}

					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getMyOrderDetailJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getMyOrderDetailJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseMyOrderDetailJson(context,
									result, orderId))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS,
										"订单详情查询成功").sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"订单详情解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * queryMyMessageDetail(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryMyMessageDetail(Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryMyMessageDetail...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_MESSAGE_BY_SHOPID_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryMyMessageDetail= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "queryMyMessageDetail result=  "
								+ result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseMyMsgDetailJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取通知列表
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryMyNotifyListJson(Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryMyNotifyListJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_MY_NOTIFICATION_LIST_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryMyNotifyListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "queryMyNotifyListJson result=  "
								+ result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseMyNotifyJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 查询我的团购券
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryGrouponCouponListJson(Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryGrouponCouponListJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_MY_GROUPON_LIST_URL);
					LogUtil.d(
							"HttpUtil",
							"queryGrouponCouponListJson...+ url = "
									+ Constants.ServerUrl.GET_MY_GROUPON_LIST_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryGrouponCouponListJson= "
							+ status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil",
								"queryGrouponCouponListJson result=  " + result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser
								.parseMyGrouponCouponJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 查询未读信息数目
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryUnReadMsgJson(Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryUnReadMsgJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_UNREAD_NMSG_NUM_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryUnReadMsgJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "queryUnReadMsgJson result=  "
								+ result);

						if (!checkResultCode(result, handler)) {
							return;
						}

						boolean ret = JSONParser.parseUnReadMsgJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}
	
	
	/**
	 * 查询未读信息数目
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryPayNumMsgJson(Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "queryUnReadMsgJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(
							Constants.ServerUrl.GET_PAY_NMSG_NUM_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryUnReadMsgJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "queryUnReadMsgJson result=  "
								+ result);

						if (!checkResultCode(result, handler)) {
							return;
						}
						boolean ret = JSONParser.parsePayMsgJson(result);
						checkParserStatus(ret, handler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取附近店铺信息
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void queryNearbyShopJson(final Context context,
			final ServerConnectionHandler handler, final int mall,
			final String MAC, final String skip, final String size) {
		LogUtil.d("HttpUtil", "queryNearbyShopJson...+ mall = " + mall);
		LogUtil.d("HttpUtil", "queryNearbyShopJson...+ MAC = " + MAC);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("mall", Constants.mallId);
					param.put("mac", MAC);
					param.put("skip", skip);
					param.put("size", size);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.GET_NEARBY_SHOP_URL);
					LogUtil.d("HttpUtil", "queryNearbyShopJson...+ uri = "
							+ Constants.ServerUrl.GET_NEARBY_SHOP_URL);
					LogUtil.d("HttpUtil", "queryNearbyShopJson...+ param = "
							+ param.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "queryNearbyShopJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "queryNearbyShopJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseNearbyShopJson(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS,
										"查询附近店铺信息成功").sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"查询附近店铺信息失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取大图片地址
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getItemLargeImgJson(final Context context,
			final ServerConnectionHandler handler, final String picPath,
			final int panelHeight, final int panelWidth) {
		LogUtil.d("HttpUtil", "getItemLargeImgJson...+ mall = " + picPath);
		LogUtil.d("HttpUtil", "getItemLargeImgJson...+ MAC = " + panelHeight);
		LogUtil.d("HttpUtil", "getItemLargeImgJson...+ mall = " + panelWidth);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("picPath", picPath);
					param.put("panelHeight", panelHeight);
					param.put("panelWidth", panelWidth);
					param.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.GET_LARGE_IMAGE_URL);
					LogUtil.d("HttpUtil", "getItemLargeImgJson...+ uri = "
							+ Constants.ServerUrl.GET_LARGE_IMAGE_URL);
					LogUtil.d("HttpUtil", "getItemLargeImgJson...+ param = "
							+ param.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getItemLargeImgJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemLargeImgJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							JSONObject data = JSONUtil.getJSONObject(result,
									Constants.JSONKeyName.NAV_JSON_TOPEST_DATA,
									null);
							if (data == null)
								return;
							String bigImagUrl = JSONUtil.getString(data,
									"picPath", null);
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									bigImagUrl).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}

					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 楼层切换
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getFloorUrl(final Context context,
			final ServerConnectionHandler handler, final int floor,
			final int mall) {
		LogUtil.d("HttpUtil", "getFloorUrl...+ floor = " + floor);
		LogUtil.d("HttpUtil", "getFloorUrl...+ mall = " + mall);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("floor", floor);
					param.put("mall", Constants.mallId);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					URI uri = URI.create(Constants.ServerUrl.GET_FLOOR_URL);
					LogUtil.d("HttpUtil", "getFloorUrl...+ uri = "
							+ Constants.ServerUrl.GET_LARGE_IMAGE_URL);
					LogUtil.d("HttpUtil",
							"getFloorUrl...+ param = " + param.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getFloorUrl= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getFloorUrl result= " + result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							JSONObject data = JSONUtil.getJSONObject(result,
									Constants.JSONKeyName.NAV_JSON_TOPEST_DATA,
									null);
							if (data == null)
								return;
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"选择楼层成功").sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}

					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 联想搜索
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void searchNearbyJson(final Context context,
			final ServerConnectionHandler handler, final int mall,
			final String info) {
		LogUtil.d("HttpUtil", "searchNearbyJson...+ mall = " + mall);
		LogUtil.d("HttpUtil", "searchNearbyJson...+ info = " + info);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					JSONObject param = new JSONObject();
					param.put("mall", Constants.mallId);
					param.put("info", info);
					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.GET_NEARBY_SEARCH_URL);
					LogUtil.d("HttpUtil", "searchNearbyJson...+ uri = "
							+ Constants.ServerUrl.GET_NEARBY_SEARCH_URL);
					LogUtil.d("HttpUtil", "searchNearbyJson...+ param = "
							+ param.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "searchNearbyJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "searchNearbyJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseNearbySearchJson(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, "搜索成功")
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"查询附近店铺信息失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 按钮搜索和点击信息搜索
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void searchNearbyInfoJson(final Context context,
			final ServerConnectionHandler handler, final String param) {
		LogUtil.d("HttpUtil", "searchNearbyInfoJson...+ param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.GET_NEARBY_SEARCH_URL);
					LogUtil.d("HttpUtil", "searchNearbyInfoJson...+ uri = "
							+ Constants.ServerUrl.GET_NEARBY_SEARTCH_INFO_URL);
					LogUtil.d("HttpUtil", "searchNearbyInfoJson...+ param = "
							+ param.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "searchNearbyInfoJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "searchNearbyInfoJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseNearbySearchJson(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, "搜索成功")
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"查询附近店铺信息失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 商品详情界面收藏店铺
	 * 
	 * @param context
	 * @param handler
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void AddShopToFavourUrl(final Context context,
			final ServerConnectionHandler handler, final String param) {
		// LogUtil.d("HttpUtil", "getFloorUrl...+ param = "+param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));

					URI uri = URI
							.create(Constants.ServerUrl.ADD_SHOP_TO_FAVOUR_URL);
					LogUtil.d("HttpUtil", "AddShopToFavourUrl...+ uri = "
							+ Constants.ServerUrl.ADD_SHOP_TO_FAVOUR_URL);
					LogUtil.d("HttpUtil", "AddShopToFavourUrl...+ param = "
							+ param.toString());
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "AddShopToFavourUrl= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "AddShopToFavourUrl result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							JSONObject data = JSONUtil.getJSONObject(result,
									Constants.JSONKeyName.NAV_JSON_TOPEST_DATA,
									null);
							if (data == null)
								return;
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"收藏店铺成功").sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}

					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION,
						 * "网络通信异常,请检查网络状态." + status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 删除已收藏的店铺
	 * 
	 * @param handler
	 *            回调实例
	 * @param addressId
	 *            要删除的地址编号
	 * @exception
	 * @since 1.0.0
	 */
	public static void delShopFromFavour(final ServerConnectionHandler handler,
			final String param) {
		LogUtil.d("HttpUtil", "delShopFromFavour...");
		LogUtil.d("HttpUtil", "delShopFromFavour param= " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));
					httpPost = new HttpPost(
							Constants.ServerUrl.DELETE_FAVOUR_SHOP_URL);
					LogUtil.d("HttpUtil", "delShopFromFavour url= "
							+ Constants.ServerUrl.DELETE_FAVOUR_SHOP_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "delShopFromFavour= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)

							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"删除店铺成功").sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.CONNECTION_EXCEPTION,
									msg).sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	public static void LoginWifi(final String param,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "LoginWifi...");
		LogUtil.d("HttpUtil", "LoginWifi param= " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param
							.toString()));
					httpPost = new HttpPost(Constants.ServerUrl.LOGIN_WIFI_URL);
					LogUtil.d("HttpUtil", "LoginWifi url= "
							+ Constants.ServerUrl.LOGIN_WIFI_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "LoginWifi= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						if (code == 0)

							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									"登录wifi成功").sendToTarget();
						else {
							String msg = JSONUtil
									.getString(
											result,
											Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
											null);
							handler.obtainMessage(
									Constants.HttpStatus.CONNECTION_EXCEPTION,
									msg).sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 发送消息
	 * 
	 * @param param
	 *            参数
	 * @param context
	 * @param handler
	 *            回调实例 void
	 * @exception
	 * @since 1.0.0
	 */
	public static void sendMessage(final Context context, final String param,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "sendMessage...");
		LogUtil.d("HttpUtil", "sendMessage... param = " + param);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"sendMessage params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI.create(Constants.ServerUrl.SEND_MESSAGE);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "sendMessage= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "sendMessage result= " + result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							// if(JSONParser.parseUserDataJson(context,result))
							handler.obtainMessage(Constants.HttpStatus.SUCCESS,
									msg).sendToTarget();
							// else{
							// handler.obtainMessage(
							// Constants.HttpStatus.NORMAL_EXCEPTION, "数据解析失败.")
							// .sendToTarget();
							// }
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}

	/**
	 * 获取订单详情
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param orderId
	 *            版本号
	 * @exception
	 * @since 1.0.0
	 */
	public static void getVersionCode(final Context context,
			final ServerConnectionHandler handler) {
		LogUtil.d("HttpUtil", "getItemFavourListJson");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					JSONObject obj = new JSONObject();
					obj.put("mall", Constants.mallId);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", obj
							.toString()));
					LogUtil.d(
							"HttpUtil",
							"getItemFavourListJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI.create(Constants.ServerUrl.CHECK_VERSION_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();

					LogUtil.d("HttpUtil", "getItemFavourListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemFavourListJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseVersionCode(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();

	}
	
	/**
	 * 获取订单详情
	 * 
	 * @param context
	 * @param handler
	 *            回调实例
	 * @param orderId
	 *            版本号
	 * @exception
	 * @since 1.0.0
	 */
	public static void getOrderFromServer(final Context context,
			final ServerConnectionHandler handler, final String param ) {
		LogUtil.d("HttpUtil", "getItemFavourListJson");
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					LogUtil.d(
							"HttpUtil",
							"getItemFavourListJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI.create(Constants.ServerUrl.ADD_ORDER_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();

					LogUtil.d("HttpUtil", "getItemFavourListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemFavourListJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);

						if (code == 0) {
							if (JSONParser.parseVersionCode(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();

	}
	
	
	public static void getMallList(final Context context,
			final ServerConnectionHandler handler){
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {

					JSONObject obj = new JSONObject();
					obj.put("mall", Constants.mallId);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", obj
							.toString()));
					LogUtil.d(
							"HttpUtil",
							"getItemFavourListJson params1 = "
									+ new UrlEncodedFormEntity(nameValuePair)
											.toString());
					URI uri = URI.create(Constants.ServerUrl.MALL_LIST_URL);
					httpPost = new HttpPost(uri);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, DEFAULT_TIME_OUT);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));
					
					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();

					LogUtil.d("HttpUtil", "getItemFavourListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.d("HttpUtil", "getItemFavourListJson result= "
								+ result);
						int code = JSONUtil.getInt(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_CODE,
								-1);
						String msg = JSONUtil.getString(result,
								Constants.JSONKeyName.SERVER_JSON_TOPEST_MSG,
								null);
						LogUtil.d("mall list get", result+"");
						if (code == 0) {
							if (JSONParser.parseVersionCode(result))
								handler.obtainMessage(
										Constants.HttpStatus.SUCCESS, msg)
										.sendToTarget();
							else {
								handler.obtainMessage(
										Constants.HttpStatus.NORMAL_EXCEPTION,
										"数据解析失败.").sendToTarget();
							}
						} else {
							handler.obtainMessage(
									Constants.HttpStatus.NORMAL_EXCEPTION, msg)
									.sendToTarget();
						}
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * handler.obtainMessage(
					 * Constants.HttpStatus.CONNECTION_EXCEPTION,
					 * "网络通信异常,请检查网络状态.").sendToTarget();
					 */
				}
			}
		}).start();
	}
}
