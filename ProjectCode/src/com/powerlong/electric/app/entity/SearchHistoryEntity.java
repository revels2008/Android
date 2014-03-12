/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchHistoryEntity.java
 * 
 * 2013-8-5-下午01:53:51
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * SearchHistoryEntity
 * 
 * @author: Liang Wang
 * 2013-8-5-下午01:53:51
 * 
 * @version 1.0.0
 * 
 */
public class SearchHistoryEntity {
	private int mFilterType = 0;
	private String mFilterContent = null;
	public SearchHistoryEntity(int type, String filter){
		mFilterType = type;
		mFilterContent = filter;
	}
	
	public int getType(){
		return mFilterType;
	}
	
	public String getContent(){
		return mFilterContent;
	}
}
