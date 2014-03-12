/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * BrandListEntity.java
 * 
 * 2013-9-9-上午09:51:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * BrandListEntity 品牌列表实体
 * 
 * @author: Liang Wang
 * 2013-9-9-上午09:51:36
 * 
 * @version 1.0.0
 * 
 */
public class BrandListEntity {
	@Column(name ="id")
	private long id;//品牌编号
	@Column(name="enName")
	private String enName;//品牌首字母
	@Column(name="icon")
	private String icon;//品牌图片地址
	/**设置品牌编号
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取品牌编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
	/**设置品牌首字母
	 * @param enName the enName to set
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}
	/**
	 * 获取品牌首字母
	 * @return  the enName
	 * @since   1.0.0
	*/
	
	public String getEnName() {
		return enName;
	}
	/**设置品牌图片路径
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取品牌图片路径
	 * @return  the icon
	 * @since   1.0.0
	*/
	
	public String getIcon() {
		return icon;
	}
}
