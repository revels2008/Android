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

/**
 * 
 * ChannelItem
 * 
 * @author: hegao
 * 2013-11-2-上午11:37:44
 * 
 * @version 1.0.0
 * 
 */
public class ChannelItem{
	private String channelId;
	private String code;
	private String name;
	private String notifyUrl;
	private String partnerId;
	private String sellerAccount;
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
}
