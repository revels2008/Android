/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchResultEntity.java
 * 
 * 2013-8-6-上午11:26:57
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import java.io.Serializable;

import com.powerlong.electric.app.config.Constants;

/**
 * 
 * SearchResultEntity
 * 
 * @author: Liang Wang
 * 2013-8-6-上午11:26:57
 * 
 * @version 1.0.0
 * 
 */
public class SearchResultEntity implements Serializable {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = -5445355806367250359L;
	private String resultText = null;
	private int filterType = Constants.FilterType.GOODS;
	public SearchResultEntity(String result,int filterType){
		resultText = result;
		this.filterType = filterType;
	}
	
	public String getResultText(){
		return resultText;
	}
	public int getResultType(){
		return filterType;
	}
}
