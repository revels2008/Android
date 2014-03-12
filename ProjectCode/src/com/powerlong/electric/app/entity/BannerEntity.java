/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * BannerEntity.java
 * 
 * 2013-9-16-下午07:25:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Id;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * 
 * BannerEntity  广告轮询实体
 * 
 * @author: Liang Wang
 * 2013-9-16-下午07:25:05
 * 
 * @version 1.0.0
 * 
 */
@Table(name="banner")
public class BannerEntity {
	@Column (name ="adHeight")
	private int adHeight;//广告高度
	@Column (name ="adDis")
	private String adDis;//广告描述
	@Column (name ="adImage")
	private String adImage;//广告位图片
	@Column (name ="adLink")
	private String adLink;//link address
	@Column (name ="adseatId")
	private long adseatId;//广告位编号
	@Column (name ="adOrder")
	private int adOrder;//顺序
	@Column (name ="type")
	private int type;//类型 0:链接；1：商家；2：商品；3：团购；4：活动
	@Id
	@Column(name ="id")
	private int id;//
	@Column(name="params")
	private String params;
	/**设置广告位描述
	 * @param adDis the adDis to set
	 */
	public void setAdDis(String adDis) {
		this.adDis = adDis;
	}
	/**
	 * 获取广告位描述
	 *
	 * @return  the adDis
	 * @since   1.0.0
	*/
	
	public String getAdDis() {
		return adDis;
	}
	/**设置图片地址
	 * @param adImage the adImage to set
	 */
	public void setAdImage(String adImage) {
		this.adImage = adImage;
	}
	/**
	 *获取图片地址
	 *
	 * @return  the adImage
	 * @since   1.0.0
	*/
	
	public String getAdImage() {
		return adImage;
	}
	/**设置链接地址
	 * @param adLink the adLink to set
	 */
	public void setAdLink(String adLink) {
		this.adLink = adLink;
	}
	/**
	 * 获取链接地址
	 *
	 * @return  the adLink
	 * @since   1.0.0
	*/
	
	public String getAdLink() {
		return adLink;
	}
	/**设置广告位编号
	 * @param adseatId the adseatId to set
	 */
	public void setAdseatId(long adseatId) {
		this.adseatId = adseatId;
	}
	/**
	 *获取广告位编号
	 *
	 * @return  the adseatId
	 * @since   1.0.0
	*/
	
	public long getAdseatId() {
		return adseatId;
	}
	/**设置广告位顺序
	 * @param adOrder the adOrder to set
	 */
	public void setAdOrder(int adOrder) {
		this.adOrder = adOrder;
	}
	/**
	 * 获取广告位顺序
	 *
	 * @return  the adOrder
	 * @since   1.0.0
	*/
	
	public int getAdOrder() {
		return adOrder;
	}
	/**设置广告位类型
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 获取广告位类型
	 *
	 * @return  the type
	 * @since   1.0.0
	*/
	
	public int getType() {
		return type;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public int getId() {
		return id;
	}
	/**
	 * @param adHeight the adHeight to set
	 */
	public void setAdHeight(int adHeight) {
		this.adHeight = adHeight;
	}
	/**
	 * 获取广告位高度
	 *
	 * @return  the adHeight
	 * @since   1.0.0
	*/
	
	public int getAdHeight() {
		return adHeight;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取超链接参数
	 *
	 * @return  the params
	 * @since   1.0.0
	*/
	
	public String getParams() {
		return params;
	}
	
}
