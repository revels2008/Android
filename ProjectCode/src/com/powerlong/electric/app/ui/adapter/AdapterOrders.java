/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterOrders.java
 * 
 * 2013-11-11-上午11:35:02
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.oned.rss.FinderPattern;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainChannelMsg;
import com.powerlong.electric.app.domain.DomainOrderMsg;
import com.powerlong.electric.app.domain.OrderMsgChild;
import com.powerlong.electric.app.domain.OrderMsgParent;
import com.powerlong.electric.app.entity.OrderDetailEntity;
import com.powerlong.electric.app.entity.ShoppingCartSettleEntity;
import com.powerlong.electric.app.ui.MyOrderDetailActivity;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.JSONUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.MD5Utils;
import com.powerlong.electric.app.utils.NotificatinUtils;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.widget.PopWindowPayList;

/**
 * 
 * AdapterOrders
 * 
 * @author: Hegao
 * 2013-11-11-上午11:35:02
 * 
 * @version 1.0.0
 * 
 */
public class AdapterOrders<T> extends AdapterBaseCommon<T> implements View.OnClickListener{

	private long orderId;
	private int stat;
	private String orderNo;
	private PopWindowPayList popWindowPayList;
	private View root;
	private String totalPrice;
	DomainOrderMsg orderMsg;
	int allNums;
	int allNum;
	String totalName="";
	private boolean isTooLong = false;
	private Context mContext;
	public static boolean isAllGroupon = true;
	/**
	 * 创建一个新的实例 AdapterOrders.
	 *
	 * @param context
	 */
	public AdapterOrders(Context context, View view) {
		super(context);
		mContext = context;
		root = view;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder vh = null;
//		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.adapter_orders, null);
			
			vh.tv_order_status = (TextView) convertView.findViewById(R.id.tv_order_status);
			vh.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);

			vh.order1_msg = (RelativeLayout) convertView.findViewById(R.id.order1_msg);
			vh.img_order1 = (ImageView) convertView.findViewById(R.id.img_order1);
			vh.tv_order1_name = (TextView) convertView.findViewById(R.id.tv_order1_name);
			vh.tv_order1_description1 = (TextView) convertView.findViewById(R.id.tv_order1_description1);
//			vh.tv_order1_description2 = (TextView) convertView.findViewById(R.id.tv_order1_description2);
//			vh.tv_order1_description3 = (TextView) convertView.findViewById(R.id.tv_order1_description3);
			vh.tv_order1_price = (TextView) convertView.findViewById(R.id.tv_order1_price);
			vh.tv_order1_num = (TextView) convertView.findViewById(R.id.tv_order1_num);
			vh.orderFlag1 = (ImageView) convertView.findViewById(R.id.orderFlag);

			vh.order2_msg = (RelativeLayout) convertView.findViewById(R.id.order2_msg);
			vh.img_order2 = (ImageView) convertView.findViewById(R.id.img_order2);
			vh.tv_order2_name = (TextView) convertView.findViewById(R.id.tv_order2_name);
			vh.tv_order2_description1 = (TextView) convertView.findViewById(R.id.tv_order2_description1);
//			vh.tv_order2_description2 = (TextView) convertView.findViewById(R.id.tv_order2_description2);
//			vh.tv_order2_description3 = (TextView) convertView.findViewById(R.id.tv_order2_description3);
			vh.tv_order2_price = (TextView) convertView.findViewById(R.id.tv_order2_price);
			vh.tv_order2_num = (TextView) convertView.findViewById(R.id.tv_order2_num);
			vh.orderFlag2 = (ImageView) convertView.findViewById(R.id.orderFlag2);
//			public TextView tv_more;
			vh.ll_more = (LinearLayout) convertView.findViewById(R.id.ll_more);
			vh.tv_more = (TextView) convertView.findViewById(R.id.tv_more);

			vh.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			vh.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			vh.btn_pay = (Button) convertView.findViewById(R.id.btn_pay);
			vh.btn_pay.setTag(position);
			vh.btn_pay.setOnClickListener(this);
			vh.tv_price_content = (TextView) convertView.findViewById(R.id.price);
			vh.tv_count_content = (TextView) convertView.findViewById(R.id.num);
			
			vh.order1_msg.setOnClickListener(this);
			vh.order2_msg.setOnClickListener(this);
			vh.tv_more.setOnClickListener(this);
			convertView.setTag(vh);
//		}
		vh = (ViewHolder) convertView.getTag();
		OrderMsgParent orderMsgParent = (OrderMsgParent) getDomain(position);
		allNums = Integer.parseInt(orderMsgParent.getAllNum());
		allNum = allNums;
		stat = orderMsgParent.getStat();
//		orderNo = orderMsgParent.getOrderNo();
		String orderStatus = orderMsgParent.getOrderStatus();
		SetTextView(vh.tv_order_status, orderStatus);
		
		if(stat == 0) {
			vh.tv_order_time.setText("订单时间："+orderMsgParent.getCreatedDate());
			vh.btn_pay.setText("立即支付");
		} else if(stat == 1) {
			vh.tv_order_time.setText("订单时间："+orderMsgParent.getCreatedDate());
			vh.btn_pay.setVisibility(View.GONE);
		} else if(stat == 2) {
			vh.tv_order_time.setText("订单时间："+orderMsgParent.getCreatedDate());
			vh.btn_pay.setVisibility(View.GONE);
		} else {
			if("110".equals(orderStatus) || "130".equals(orderStatus) || "140".equals(orderStatus) || "150".equals(orderStatus)) {
				vh.tv_order_status.setTextColor(mContext.getResources().getColor(R.color.grey));
				vh.tv_order_time.setTextColor(mContext.getResources().getColor(R.color.grey));
			} else {
				vh.tv_order_status.setTextColor(mContext.getResources().getColor(R.color.green));
				vh.tv_order_time.setTextColor(mContext.getResources().getColor(R.color.green));
			}
			
			vh.tv_order_time.setText("下单时间："+orderMsgParent.getCreatedDate());
			
			vh.btn_pay.setVisibility(View.GONE);
			vh.tv_order1_name.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order1_description1.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order1_price.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order1_num.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order2_name.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order2_description1.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order2_price.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_order2_num.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_price.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_num.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_price_content.setTextColor(mContext.getResources().getColor(R.color.grey));
			vh.tv_count_content.setTextColor(mContext.getResources().getColor(R.color.grey));
		}
 
		List<OrderMsgChild> order_child_list = OrderMsgChild.convertJsonToOrderMsgChild(context, orderMsgParent.getItemList());
	
//		public ImageView img_order1;
//		public TextView tv_order1_name;
//		public TextView tv_order1_description1;
//		public TextView tv_order1_price;
//		public TextView tv_order1_num;
		OrderMsgChild orderMsgChild1 = order_child_list.get(0);
		vh.tv_order1_name.setText(orderMsgChild1.getItemName());
		vh.tv_order1_description1.setText(orderMsgChild1.getProp());
		vh.tv_order1_price.setText(Constants.moneyTag +orderMsgChild1.getPlPrice());
		vh.tv_order1_num.setText(Constants.numPrfix+orderMsgChild1.getBuyNum());
		if(orderMsgChild1.getType().equals("0")) {
			vh.orderFlag1.setVisibility(View.GONE);
		} else {
			vh.orderFlag1.setVisibility(View.VISIBLE);
		}
		asynchronousLoadImage(vh.img_order1, orderMsgChild1.getImage());
		
		orderId = StringUtil.toLong(orderMsgParent.getId());
		LogUtil.v("orderId", orderId+"");
		allNums = allNums - Integer.parseInt(orderMsgChild1.getBuyNum());
		
		if(order_child_list.size()>1){
			vh.order2_msg.setVisibility(View.VISIBLE);

			OrderMsgChild orderMsgChild2 = order_child_list.get(1);
			vh.tv_order2_name.setText(orderMsgChild2.getItemName());
			vh.tv_order2_description1.setText(orderMsgChild2.getProp());
			vh.tv_order2_price.setText(Constants.moneyTag +orderMsgChild2.getPlPrice());
			vh.tv_order2_num.setText(Constants.numPrfix+orderMsgChild2.getBuyNum());
			if(orderMsgChild2.getType().equals("0")) {
				vh.orderFlag2.setVisibility(View.GONE);
			} else {
				vh.orderFlag2.setVisibility(View.VISIBLE);
			}
			asynchronousLoadImage(vh.img_order2, orderMsgChild2.getImage());
			allNums = allNums - Integer.parseInt(orderMsgChild2.getBuyNum());
		}else{
			vh.order2_msg.setVisibility(View.GONE);
		}
		
		if(allNums>0){
			vh.ll_more.setVisibility(View.VISIBLE);
			vh.tv_more.setText("显示其余"+allNums+"件");
		}else{
			vh.ll_more.setVisibility(View.GONE);
		}
		
		vh.tv_price.setText(Constants.moneyTag + orderMsgParent.getRealMon());
		
		vh.tv_num.setText(Constants.numPrfix+orderMsgParent.getAllNum());
		vh.order1_msg.setTag(orderId);
		vh.order2_msg.setTag(orderId);
		vh.tv_more.setTag(orderId);
		
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tv_order_status;
		public TextView tv_order_time;
		
		public RelativeLayout order1_msg;
		public ImageView img_order1;
		public TextView tv_order1_name;
		public TextView tv_order1_description1;
//		public TextView tv_order1_description2;
//		public TextView tv_order1_description3;
		public TextView tv_order1_price;
		public TextView tv_order1_num;
		
		public RelativeLayout order2_msg;
		public ImageView img_order2;
		public TextView tv_order2_name;
		public TextView tv_order2_description1;
//		public TextView tv_order2_description2;
//		public TextView tv_order2_description3;
		public TextView tv_order2_price;
		public TextView tv_order2_num;
		
		public LinearLayout ll_more;
		public TextView tv_more;
		
		public TextView tv_price;
		public TextView tv_num;
		public Button btn_pay;
		public TextView tv_price_content;
		public TextView tv_count_content;
		
		public ImageView orderFlag1;
		public ImageView orderFlag2;
	}

	private void getOrderNum(int position) {
		FinalHttp finalHttp = new FinalHttp();
		JSONObject jsParam = null;
		OrderMsgParent orderMsgParent = (OrderMsgParent) getDomain(position);
		List<OrderMsgChild> order_child_list = OrderMsgChild.convertJsonToOrderMsgChild(context, orderMsgParent.getItemList());
		totalName="";
		for(int i = 0;i < order_child_list.size();i++){
			String itemName = order_child_list.get(i).getItemName();
			if((totalName+itemName).length() <= 50 ){
				totalName += itemName+" ";
			}else{
				isTooLong = true;
				if(totalName.length() == 0){
					totalName = itemName.substring(0,49);
					isTooLong = false;
				}
				
			}
			if(order_child_list.get(i).getType().equals("0")){
				isAllGroupon=false;
			}
		}
		if(!totalName.contains("等等") && isTooLong  && totalName.length() != 50){
			totalName += "等等";
			isTooLong = false;
		}
		popWindowPayList = new PopWindowPayList(context, totalName);
		orderNo = orderMsgParent.getOrderNo();
		totalPrice = orderMsgParent.getRealMon();
		LogUtil.v("totalprice ", orderMsgParent.getRealMon());
		try {
			jsParam = new JSONObject();
			jsParam.put("orderNo", orderNo);
			jsParam.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			jsParam.put("mall", Constants.mallId);
		}
		catch (Exception e1) {
			Toast.makeText(context, e1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		AjaxParams ajaxParams_channel = new AjaxParams();
		ajaxParams_channel.put("data", jsParam.toString());
		finalHttp.addHeader("type", "APP");
		if(DataCache.UserDataCache.size() > 0) {
			finalHttp.addHeader("TGC", DataCache.UserDataCache.get(0).getTGC());
		}
		
		finalHttp.addHeader("mac", Constants.mac);
		finalHttp.addHeader("uid", Constants.userId+"");
		finalHttp.addHeader("client", "android");
		finalHttp.addHeader("version", Constants.version);
		LogUtil.v("getOrderNum url = ", Constants.ServerUrl.GET_NEW_ORDER_NO);
		LogUtil.v("getOrderNum prama = ", ajaxParams_channel.toString());
		
		finalHttp.post(Constants.ServerUrl.GET_NEW_ORDER_NO, ajaxParams_channel, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
				if(t!=null && strMsg !=null){
					Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onSuccess(String t) {
				try {
					JSONObject jsObj = new JSONObject(t);
					if(0 != jsObj.getInt("code")){
						Toast.makeText(context, jsObj.getString("msg"),Toast.LENGTH_SHORT).show();
					}else{
//						Toast.makeText(context, jsObj.toString(), Toast.LENGTH_SHORT).show();
						LogUtil.v("getOrderNum result = ", jsObj.toString());
						try{
							JSONObject data = JSONUtil.getJSONObject(jsObj,
									Constants.JSONKeyName.BRAND_DETAIL_JSON_TOPEST_DATA, null);
							final String result = JSONUtil.getString(data, "paymentNo", "");
							orderNo = result;
						
						}catch (Exception e1) {
							Toast.makeText(context, e1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
							return;
						}
						getChannelMsg();
					}
				}catch (Exception e) {
					Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void getChannelMsg() {
		FinalHttp finalHttp_channel = new FinalHttp();
		JSONObject jsParam = null;
		try {
//			List<DomainOrderMsg> listOrderMsg = DomainOrderMsg.convertJsToOrderMsg(context, jsObj.getJSONObject("data").getString("orderList"));
//			orderMsg = listOrderMsg.get(0);
			jsParam = new JSONObject();
			jsParam.put("appCode", Constants.APPCODE);
			jsParam.put("bizCode", orderNo);
			jsParam.put("bizType", "1");
			jsParam.put("bizDescription", totalName);
			jsParam.put("amount",totalPrice);
			jsParam.put("type", "1");
			jsParam.put("userId", Constants.userId);
			jsParam.put("userName", DataCache.UserDataCache.get(0).getUsername());
			jsParam.put("sign", makeSignString(jsParam));
			LogUtil.v("totalprice 2 = ", totalPrice);
			
		} catch (Exception e1) {
			Toast.makeText(context, e1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		AjaxParams ajaxParams_channel = new AjaxParams();
		ajaxParams_channel.put("data", jsParam.toString());
		
		LogUtil.v("getChannelMsg url=", Constants.ServerUrl.GET_ACTIVE_CHANNEL);
		LogUtil.v("getChannelMsg param=", jsParam.toString());
		finalHttp_channel.addHeader("type", "APP");
		if(DataCache.UserDataCache.size() > 0) {
			finalHttp_channel.addHeader("TGC", DataCache.UserDataCache.get(0).getTGC());
		}
		
		finalHttp_channel.addHeader("mac", Constants.mac);
		finalHttp_channel.addHeader("uid", Constants.userId+"");
		finalHttp_channel.addHeader("client", "android");
		finalHttp_channel.addHeader("version", Constants.version);
		finalHttp_channel.post(Constants.ServerUrl.GET_ACTIVE_CHANNEL, ajaxParams_channel, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if(t!=null && strMsg !=null){
					Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onSuccess(String str_channel) {
				try {
					JSONObject jsObj = new JSONObject(str_channel);
					if(0!=jsObj.getInt("code")){
						NotificatinUtils.showCustomToast(context, jsObj.getString("msg"));
						LogUtil.v("getChannelMsg msg =", jsObj.getString("msg"));
					}else{
/*							DomainChannelMsg channelMsg = DomainChannelMsg.convertJsonToChannelMsg(context, jsObj.toString());
						
						LogUtil.v("getChannelMsg data =", jsObj.toString());
						popWindowPayList.clearData();
						popWindowPayList.setChannelMsg(channelMsg);
						popWindowPayList.addData(channelMsg.getChannels());
						popWindowPayList.show(root);*/
						payRequest(jsObj);
					}
				} catch (Exception e) {
					Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
}

	private void payRequest(JSONObject jsObj) {
		FinalHttp finalHttp_channel = new FinalHttp();
		JSONObject jsParam = null;
		try {
	//		List<DomainOrderMsg> listOrderMsg = DomainOrderMsg.convertJsToOrderMsg(context, jsObj.getJSONObject("data").getString("orderList"));
	//		DomainOrderMsg orderMsg = listOrderMsg.get(0);
			jsParam = new JSONObject();
			jsParam.put("appCode", Constants.APPCODE);
			jsParam.put("bizCode", orderNo);
			jsParam.put("bizType", "1");
			jsParam.put("bizDescription", totalName);
			jsParam.put("amount", totalPrice);
			jsParam.put("type", "1");
			jsParam.put("userId", Constants.userId);
			jsParam.put("userName", DataCache.UserDataCache.get(0).getUsername());
			jsParam.put("responseType", "json");
			jsParam.put("serialNum", jsObj.get("serialNum"));
			jsParam.put("sign", makeSecondSignString(jsParam));
			
		} catch (Exception e1) {
			Toast.makeText(context, e1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		AjaxParams ajaxParams_channel = new AjaxParams();
		ajaxParams_channel.put("data", jsParam.toString());
		
		LogUtil.v("payRequest url=", Constants.ServerUrl.GET_REQUEST_PAY);
		LogUtil.v("payRequest param=", jsParam.toString());
		finalHttp_channel.addHeader("type", "APP");
		if(DataCache.UserDataCache.size() > 0) {
			finalHttp_channel.addHeader("TGC", DataCache.UserDataCache.get(0).getTGC());
		}		
		finalHttp_channel.addHeader("mac", Constants.mac);
		finalHttp_channel.addHeader("uid", Constants.userId+"");
		finalHttp_channel.addHeader("client", "android");
		finalHttp_channel.addHeader("version", Constants.version);
		finalHttp_channel.post(Constants.ServerUrl.GET_REQUEST_PAY, ajaxParams_channel, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if(t!=null && strMsg !=null){
					Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onSuccess(String str_channel) {
				try {
					JSONObject jsObj = new JSONObject(str_channel);
					if(0!=jsObj.getInt("code")){
						NotificatinUtils.showCustomToast(context, jsObj.getString("msg"));
						LogUtil.v("payRequest msg =", jsObj.getString("msg"));
					}else{
						DomainChannelMsg channelMsg = DomainChannelMsg.convertJsonToChannelMsg(context, jsObj.getString("data"));
						
						LogUtil.v("payRequest data =", jsObj.getString("data"));
						View rl = layoutInflater.inflate(R.layout.activity_my_order, null);
						popWindowPayList.clearData();
						popWindowPayList.setChannelMsg(channelMsg);
						popWindowPayList.addData(channelMsg.getChannels());
						popWindowPayList.show(rl);
					}
				} catch (Exception e) {
					Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					LogUtil.v("adapterOrder ex", e.getLocalizedMessage());
				}
			}
		});
	}
	
	private String makeSignString(JSONObject jsParam) {
	//	amount={value}&appCode={value}&bizCode={value}&bizDescription={value}
	//	&bizType={value}&type=1&username={value}&{key}
	//	sign =com.plocc.framework.utils.Md5Utility.getMD5String(生成签名的字符串)
		
	//	jsParam.put("appCode", Constants.APPCODE);
	//	jsParam.put("bizCode", orderMsg.getOrderNo());
	//	jsParam.put("bizType", "1");
	//	jsParam.put("bizDescription", "测试用totalName");
	//	jsParam.put("amount","0.01");
	//	jsParam.put("type", "1");
	//	jsParam.put("userName", DataCache.UserDataCache.get(0).getUsername());
		
		StringBuilder sb = new StringBuilder("");
		sb.append("amount=");
		sb.append(jsParam.optString("amount", ""));
		sb.append("&appCode=");
		sb.append(jsParam.optString("appCode", ""));
		sb.append("&bizCode=");
		sb.append(jsParam.optString("bizCode", ""));
		sb.append("&bizDescription=");
		sb.append(jsParam.optString("bizDescription", ""));
		sb.append("&bizType=");
		sb.append(jsParam.optString("bizType", ""));
		sb.append("&type=");
		sb.append(jsParam.optString("type", ""));
		sb.append("&userId=");
		sb.append(jsParam.optString("userId", ""));
		sb.append("&userName=");
		sb.append(jsParam.optString("userName", ""));
		sb.append("&");
		sb.append(Constants.KEY);
		LogUtil.v("getChannelMsg befor Md5 = ", sb.toString());
		return MD5Utils.getMd5Str(sb.toString());
	}
	
	private String makeSecondSignString(JSONObject jsParam) {
	//	amount={value}&appCode={value}&bizCode={value}&bizDescription={value}
	//	&bizType={value}&type=1&username={value}&{key}
	//	sign =com.plocc.framework.utils.Md5Utility.getMD5String(生成签名的字符串)
		
	//	jsParam.put("appCode", Constants.APPCODE);
	//	jsParam.put("bizCode", orderMsg.getOrderNo());
	//	jsParam.put("bizType", "1");
	//	jsParam.put("bizDescription", "测试用totalName");
	//	jsParam.put("amount","0.01");
	//	jsParam.put("type", "1");
	//	jsParam.put("userName", DataCache.UserDataCache.get(0).getUsername());
		
		StringBuilder sb = new StringBuilder("");
		sb.append("amount=");
		sb.append(jsParam.optString("amount", ""));
		sb.append("&appCode=");
		sb.append(jsParam.optString("appCode", ""));
		sb.append("&bizCode=");
		sb.append(jsParam.optString("bizCode", ""));
		sb.append("&bizDescription=");
		sb.append(jsParam.optString("bizDescription", ""));
		sb.append("&bizType=");
		sb.append(jsParam.optString("bizType", ""));
		sb.append("&responseType=");
		sb.append(jsParam.optString("responseType", ""));
		sb.append("&serialNum=");
		sb.append(jsParam.optString("serialNum", ""));
		sb.append("&type=");
		sb.append(jsParam.optString("type", ""));
		sb.append("&userId=");
		sb.append(jsParam.optString("userId", ""));
		sb.append("&userName=");
		sb.append(jsParam.optString("userName", ""));
		sb.append("&");
		sb.append(Constants.KEY);
		LogUtil.v("getChannelMsg befor Md5 = ", sb.toString());
		return MD5Utils.getMd5Str(sb.toString());
	}
	private void SetTextView(TextView textView, String state) {
		// orderStat 订单状态 0：已提交 待支付 1：已付款 待发货 2：已发货 待客户确认
		// 3：申请退款（已经提交申请，已发货）待商家审核
		// 4：申请退款（已经提交申请，未发货）待商家审核 5：商家审核未通过
		// 6：商家审核通过，待客户退货 7：待退款 8：退款成功
		// 9：交易成功 10：取消订单 11：评论 12：拒签

		if (state.equals("0")) {
			textView.setText("等待买家付款");
			
		} else if (state.equals("10")) {
			textView.setText("等待商家发货");
			
		} else if (state.equals("20")) {
			textView.setText("已拣货");
			
		} else if (state.equals("25")) {
			textView.setText("缺货");
			
		} else if (state.equals("30")) {
			textView.setText("等待买家确认收货");
			
		} else if (state.equals("40")) {
			textView.setText("买家已收到货，申请退货");
			
		} else if (state.equals("50")) {
			textView.setText("买家未收到货，申请退款");
			
		} else if (state.equals("60")) {
			textView.setText("商家拒绝退款/退货");
			
		} else if (state.equals("70")) {
			textView.setText("商家同意退货");
			
		} else if (state.equals("80")) {
			textView.setText("已退货，待退款");
			
		} else if (state.equals("90")) {
			textView.setText("退款成功");
			
		} else if (state.equals("100")) {
			textView.setText("交易完成");
			
		} else if (state.equals("110")) {
			textView.setText("交易关闭");
			
		} else if (state.equals("120")) {
			textView.setText("已评论");
			
		} else if (state.equals("130")) {
			textView.setText("交易关闭");
			
		} else if (state.equals("140")) {
			textView.setText("交易关闭");
			
		} else if (state.equals("150")) {
			textView.setText("交易关闭");
			
		}
	}
	
	@Override
	public void onClick(View v) {
//		Toast.makeText(context, "Order detail page will be going soon", Toast.LENGTH_SHORT).show();
		switch (v.getId()) {
		case R.id.btn_pay:
//			Intent intent = new Intent(context, MyOrderDetailActivity.class);
//			intent.putExtra("orderItemId", orderId);
//			context.startActivity(intent);
			
			getOrderNum((Integer)v.getTag());
			
			break;	
		case R.id.order1_msg:
			Intent intent1 = new Intent(context, MyOrderDetailActivity.class);
			intent1.putExtra("orderItemId", v.getTag()+"");
			intent1.putExtra("stat", stat);
			intent1.putExtra("allNum", allNum);
			context.startActivity(intent1);
			break;
		case R.id.order2_msg:
			Intent intent2 = new Intent(context, MyOrderDetailActivity.class);
			intent2.putExtra("orderItemId", v.getTag()+"");
			intent2.putExtra("stat", stat);
			intent2.putExtra("allNum", allNum);
			context.startActivity(intent2);
			break;
		case R.id.tv_more:
			Intent intent3 = new Intent(context, MyOrderDetailActivity.class);
			intent3.putExtra("orderItemId", v.getTag()+"");
			intent3.putExtra("stat", stat);
			intent3.putExtra("allNum", allNum);
			context.startActivity(intent3);
			break;
		default:
			break;
		}
	}
}
