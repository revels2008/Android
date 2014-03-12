/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchShopsByKeywordsEntity.java
 * 
 * 2013-8-26-下午02:39:22
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * SearchShopsByKeywordsEntity
 * 
 * @author: Liang Wang
 * 2013-8-26-下午02:39:22
 * 
 * @version 1.0.0
 * 
 */
public class SearchShopsByKeywordsEntity {
	@Column (name = "page_size")
	private int pageSize;
	@Column (name = "count")
	private int count;
	@Column (name = "page")
	private int page;
	@Column (name = "shop_list")
	private String shopList;
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * pageSize
	 *
	 * @return  the pageSize
	 * @since   1.0.0
	*/
	
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * count
	 *
	 * @return  the count
	 * @since   1.0.0
	*/
	
	public int getCount() {
		return count;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * page
	 *
	 * @return  the page
	 * @since   1.0.0
	*/
	
	public int getPage() {
		return page;
	}
	/**
	 * @param shopList the shopList to set
	 */
	public void setShopList(String shopList) {
		this.shopList = shopList;
	}
	/**
	 * shopList
	 *
	 * @return  the shopList
	 * @since   1.0.0
	*/
	
	public String getShopList() {
		return shopList;
	}
}
