/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ItemBargainListEntity.java
 * 
 * 2013-9-18-下午01:57:03
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ItemBargainListEntity
 * 
 * @author: Liang Wang 2013-9-18-下午01:57:03
 * 
 * @version 1.0.0
 * 
 */
public class ItemBargainListEntity {
	@Column(name = "name")
	private String name;// 优惠名称
	@Column(name = "type")
	private String type;// 类别 0：普通优惠；1：赠品

	/**
	 * 设置优惠名称
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取优惠名称
	 * 
	 * @return the name
	 * @since 1.0.0
	 */

	public String getName() {
		return name;
	}

	/**
	 * 设置类别
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取类别
	 * 
	 * @return the type 0：普通优惠；1：赠品
	 * @since 1.0.0
	 */

	public String getType() {
		return type;
	}
}
