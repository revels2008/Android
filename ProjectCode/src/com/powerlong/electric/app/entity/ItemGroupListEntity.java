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
 * ItemGroupListEntity 团购商品集列表实体
 * 
 * @author: YangCheng Miao
 * 2013-9-9-上午09:51:36
 * 
 * @version 1.0.0
 * 
 */
public class ItemGroupListEntity {
	@Column(name ="itemGroupId")
	private long itemGroupId;//品牌编号
	/**设置品牌编号
	 * @param id the id to set
	 */
	public void setItemGroupId(long itemGroupId) {
		this.itemGroupId = itemGroupId;
	}
	/**
	 * 获取品牌编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getItemGroupId() {
		return itemGroupId;
	}
}
