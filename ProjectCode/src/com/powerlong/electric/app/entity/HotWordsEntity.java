/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchFilterByKeywords.java
 * 
 * 2013-8-26-下午02:04:34
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;


/**
 * 
 * HotWordsEntity 热词匹配实体
 * 
 * @author: Liang Wang
 * 2013-8-26-下午02:04:34
 * 
 * @version 1.0.0
 * 
 */
public class HotWordsEntity {
	@Column (name = "page_size")
	private int pageSize;//每页显示数量
	@Column (name = "count")
	private int count;//总记录数
	@Column (name = "page")
	private int page;//当前页
	@Column (name = "key_list")
	private String[] keyList;//匹配结果
	/**设置每页显示数量
	 * @param pageSize 每页显示数量(int)
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 获取每页显示数量
	 *
	 * @return  每页显示数量(int)
	 * @since   1.0.0
	*/
	
	public int getPageSize() {
		return pageSize;
	}
	/**设置总记录数
	 * @param count 记录数(int)
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * 获取总记录数
	 *
	 * @return  (int)
	 * @since   1.0.0
	*/
	
	public int getCount() {
		return count;
	}
	/**设置当前页
	 * @param page 当前页(int)
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * 获取当前页
	 *
	 * @return  当前页(int)
	 * @since   1.0.0
	*/
	
	public int getPage() {
		return page;
	}
	/**设置匹配结果
	 * @param keyList 匹配结果(string[])
	 */
	public void setKeyList(String[] keyList) {
		this.keyList = keyList;
	}
	/**
	 * 获取匹配结果
	 *
	 * @return  匹配结果(string[])
	 * @since   1.0.0
	*/
	
	public String[] getKeyList() {
		return keyList;
	}
}
