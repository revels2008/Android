/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * ChannelMsg.java
 * 
 * 2013-11-2-上午11:16:26
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
 * ChannelMsg
 * 
 * @author: hegao
 * 2013-11-2-上午11:16:26
 * 
 * @version 1.0.0
 * {
    "code": "0",
    "msg": "成功",
    "appcode": "F102",
    "sign": "1a979ef40175aaef4f635073b9a880fc",
    "data": {
        "amount": "100.10",
        "serialNum": "131031F10223ASD1UBF",
        "channels": [
            {
                "channelId": "1",
                "code": "ALIPAY",
                "name": "支付宝",
                "notifyUrl": "http://192.168.171.247:8080/OCC_GATEWAY_Web/alipay/callback/mobile/pay/notify.htm",
                "partnerId": "2088011526990650",
                "sellerAccount": "plec_fuzhou@powerlong.com"
            }
        ],
        "banks": [
            {
                "code": "CMB",
                "name": "招商银行"
            },
            {
                "code": "CCB",
                "name": "中国建设银行"
            }
        ],
        "debitBanks": [
            {
                "code": "CMB-DEBIT",
                "name": "招商银行"
            },
            {
                "code": "CCB-DEBIT",
                "name": "中国建设银行"
            }
        ]
    }
}
 */
public class DomainChannelMsg {
	public static DomainChannelMsg convertJsonToChannelMsg(Context context,String json){
		DomainChannelMsg channelMsg = null;
		try {
			JSONObject jsObj = new JSONObject(json);
			channelMsg = new DomainChannelMsg();
			channelMsg.setAmount(jsObj.optString("amount", ""));
			channelMsg.setSerialNum(jsObj.optString("serialNum", ""));
			JSONArray jsArr = jsObj.getJSONArray("channels");
			List<DomainChannelItem> list = new ArrayList<DomainChannelItem>();
			for(int i =0 ;i <jsArr.length();i++){
				JSONObject jsObjItem = jsArr.getJSONObject(i);
				DomainChannelItem channelItem = new DomainChannelItem();
				channelItem.setChannelId(jsObjItem.optString("channelId", ""));
				channelItem.setCode(jsObjItem.optString("code", ""));
				channelItem.setName(jsObjItem.optString("name", ""));
				channelItem.setNotifyUrl(jsObjItem.optString("notifyUrl", ""));
				channelItem.setPartnerId(jsObjItem.optString("partnerId", ""));
				channelItem.setSellerAccount(jsObjItem.optString("sellerAccount", ""));
				list.add(channelItem);
			}
			channelMsg.setChannels(list);
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return channelMsg;
	}
	
	
	private String amount;
	private String serialNum;
	private List<DomainChannelItem> channels;
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public List<DomainChannelItem> getChannels() {
		return channels;
	}

	public void setChannels(List<DomainChannelItem> channels) {
		this.channels = channels;
	}

	
}
