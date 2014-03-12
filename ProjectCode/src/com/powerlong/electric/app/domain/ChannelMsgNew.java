/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * ChannelMsgNew.java
 * 
 * 2013-11-12-下午05:29:18
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * ChannelMsgNew
 * 
 * @author: He Gao
 * 2013-11-12-下午05:29:18
 * 
 * @version 1.0.0
 * {
    "userId": null,
    "bizDescription": "测试用商品",
    "amount": "0.01",
    "bizType": "1",
    "serialNum": "131112F10287989I6RA",
    "appCode": "F102",
    "msg": "成功",
    "code": "0",
    "key": "UIPRWQTNEWASDUPASDJLXIKOPAD",
    "type": "1",
    "sign": "bcfcb9fc69253ac237e5a0302cf0a0e1",
    "historySerialNum": null,
    "userName": "PL.813903464",
    "signString": "amount=0.01&appCode=F102&bizCode=xccieqlk1390787989&bizDescription=测试用商品&bizType=1&code=0&msg=成功&serialNum=131112F10287989I6RA&type=1&userName=PL.813903464&",
    "bizCode": "xccieqlk1390787989"
}
 * 
 */
public class ChannelMsgNew {
	public static ChannelMsgNew convertJsonToChannelMsgNew(Context context,String json){
		ChannelMsgNew channelMsgNew = null;
		try {
			JSONObject jsObj = new JSONObject(json);
			channelMsgNew = new ChannelMsgNew();
			channelMsgNew.setAmount(jsObj.optString("amount", ""));
			channelMsgNew.setAppCode(jsObj.optString("appCode", ""));
			channelMsgNew.setBizCode(jsObj.optString("bizCode", ""));
			channelMsgNew.setBizDescription(jsObj.optString("bizDescription", "商品"));
			channelMsgNew.setBizType(jsObj.optString("bizType", ""));
			channelMsgNew.setCode(jsObj.optString("code", ""));
			channelMsgNew.setHistorySerialNum(jsObj.optString("historySerialNum", ""));
			channelMsgNew.setKey(jsObj.optString("key", ""));
			channelMsgNew.setMsg(jsObj.optString("msg", ""));
			channelMsgNew.setSerialNum(jsObj.optString("serialNum", ""));
			channelMsgNew.setSign(jsObj.optString("sign", ""));
			channelMsgNew.setSignString(jsObj.optString("signString", ""));
			channelMsgNew.setType(jsObj.optString("type", ""));
			channelMsgNew.setUserId(jsObj.optString("userId", ""));
			channelMsgNew.setUserName(jsObj.optString("userName", ""));
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return channelMsgNew;
	}
	private String userId;
	private String bizDescription;
	private String amount;
	private String bizType;
	private String serialNum;
	private String appCode;
	private String msg;
	private String code;
	private String key;
	private String type;
	private String sign;
	private String historySerialNum;
	private String userName;
	private String signString;
	private String bizCode;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBizDescription() {
		return bizDescription;
	}
	public void setBizDescription(String bizDescription) {
		this.bizDescription = bizDescription;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getHistorySerialNum() {
		return historySerialNum;
	}
	public void setHistorySerialNum(String historySerialNum) {
		this.historySerialNum = historySerialNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
}
