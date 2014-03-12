/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchGoodsByKeywordsEntity.java
 * 
 * 2013-8-26-下午02:37:03
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * SearchGoodsByKeywordsEntity
 * 
 * @author: Liang Wang
 * 2013-8-26-下午02:37:03
 * 
 * @version 1.0.0
 * 
 */
public class SearchGoodsByKeywordsEntity {
	@Column (name = "page_size")
	private int pageSize;
	@Column (name = "count")
	private int count;
	@Column (name = "page")
	private int page;
	@Column (name = "item_list")
	private String itemList;
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
	 * @param itemList the itemList to set
	 */
	public void setItemList(String itemList) {
		this.itemList = itemList;
	}
	/**
	 * itemList
	 *
	 * @return  the itemList
	 * @since   1.0.0
	*/
	
	public String getItemList() {
		return itemList;
	}
}
