/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * AdvertisementEntity.java
 * 
 * 2013-9-16-下午07:04:56
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * AdvertisementEntity
 * 
 * @author: Liang Wang
 * 2013-9-16-下午07:04:56
 * 
 * @version 1.0.0
 * 
 */
public class AdvertisementEntity {
	@Column(name ="adHeight")
	private int adHeight;//图片高度
	@Column(name ="adWidth")
	private int adWidth;//图片宽度
	@Column(name ="adType")
	private String adType;//广告类型 0=单图片图片 ,1=双图片,3=轮询图片,4=文字连接
	@Column(name ="channelId")
	private long channelId;//频道编号
	@Column(name ="advertisementList")
	private String advertisementList;//广告位信息
	@Column(name ="channelOrder")
	private int channelOrder;//顺序
	@Column(name ="id")
	private int id;//栏位编号
	/**设置图片高度
	 * @param adHeight the adHeight to set
	 */
	public void setAdHeight(int adHeight) {
		this.adHeight = adHeight;
	}
	/**
	 * 获取图片高度
	 *
	 * @return  the adHeight
	 * @since   1.0.0
	*/
	
	public int getAdHeight() {
		return adHeight;
	}
	/**设置图片宽度
	 * @param adWidth the adWidth to set
	 */
	public void setAdWidth(int adWidth) {
		this.adWidth = adWidth;
	}
	/**
	 * 获取图片宽度
	 *
	 * @return  the adWidth
	 * @since   1.0.0
	*/
	
	public int getAdWidth() {
		return adWidth;
	}
	/**设置广告类型
	 * @param adType the adType to set
	 */
	public void setAdType(String adType) {
		this.adType = adType;
	}
	/**
	 * 获取广告类型
	 *
	 * @return  the adType
	 * @since   1.0.0
	*/
	
	public String getAdType() {
		return adType;
	}
	/**设置广告位渠道号
	 * @param channelId the channelId to set
	 */
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	/**
	 * 获取广告位渠道号
	 *
	 * @return  the channelId
	 * @since   1.0.0
	*/
	
	public long getChannelId() {
		return channelId;
	}
	/**
	 * @param advertisementList the advertisementList to set
	 */
	public void setAdvertisementList(String advertisementList) {
		this.advertisementList = advertisementList;
	}
	/**
	 * advertisementList
	 *
	 * @return  the advertisementList
	 * @since   1.0.0
	*/
	
	public String getAdvertisementList() {
		return advertisementList;
	}
	/**设置广告位顺序
	 * @param channelOrder the channelOrder to set
	 */
	public void setChannelOrder(int channelOrder) {
		this.channelOrder = channelOrder;
	}
	/**
	 * 获取广告位顺序
	 *
	 * @return  the channelOrder
	 * @since   1.0.0
	*/
	
	public int getChannelOrder() {
		return channelOrder;
	}
	/**设置广告位编号
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取广告位编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public int getId() {
		return id;
	}
}
