/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * ChannelItem.java
 * 
 * 2013-11-2-上午11:37:44
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * ChannelItem
 * 
 * @author: hegao
 * 2013-11-2-上午11:37:44
 * 
 * @version 1.0.0
 * //					{
//					    "code": "0",
//					    "msg": "成功",
//					    "appcode": "F102",
//					    "sign": "1a979ef40175aaef4f635073b9a880fc",
//					    "data": {
//					        "amount": "100.10",
//					        "serialNum": "131031F10223ASD1UBF",
//					        "channels": [
//					            {
//					                "channelId": "1",
//					                "code": "ALIPAY",
//					                "name": "支付宝",
//					                "notifyUrl": "http://192.168.171.247:8080/OCC_GATEWAY_Web/alipay/callback/mobile/pay/notify.htm",
//					                "partnerId": "2088011526990650",
//					                "sellerAccount": "plec_fuzhou@powerlong.com"
//					            }
//					        ],
//					        "banks": [
//					            {
//					                "code": "CMB",
//					                "name": "招商银行"
//					            },
//					            {
//					                "code": "CCB",
//					                "name": "中国建设银行"
//					            }
//					        ],
//					        "debitBanks": [
//					            {
//					                "code": "CMB-DEBIT",
//					                "name": "招商银行"
//					            },
//					            {
//					                "code": "CCB-DEBIT",
//					                "name": "中国建设银行"
//					            }
//					        ]
//					    }
//					}
 * 
 */
public class DomainChannelItem{
//    "channels": [
//    {
//        "channelId": "1",
//        "code": "ALIPAY",
//        "name": "支付宝",
//        "notifyUrl": "http://192.168.171.247:8080/OCC_GATEWAY_Web/alipay/callback/mobile/pay/notify.htm",
//        "partnerId": "2088011526990650",
//        "sellerAccount": "plec_fuzhou@powerlong.com"
//    }
//],
	public static List<DomainChannelItem> convertJsonToChannelItem(Context context,String json){
		List<DomainChannelItem> list = new ArrayList<DomainChannelItem>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i=0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				DomainChannelItem item = new DomainChannelItem();
				item.setChannelId(jsObj.optString("channelId", ""));
				item.setCode(jsObj.optString("code", ""));
				item.setName(jsObj.optString("name", ""));
				item.setNotifyUrl(jsObj.optString("notifyUrl", ""));
				item.setPartnerId(jsObj.optString("partnerId", ""));
				item.setSellerAccount(jsObj.optString("sellerAccount", ""));
			}
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return list;
	}
	private String channelId;
	private String code;
	private String name;
	private String notifyUrl;
	private String partnerId;
	private String sellerAccount;
	private String serialNum;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getSellerAccount() {
		return sellerAccount;
	}
	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}
	/**
	 * serialNum
	 *
	 * @return  the serialNum
	 * @since   1.0.0
	 */
	
	public String getSerialNum() {
		return serialNum;
	}
	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
}
