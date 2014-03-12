/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * FilterEntity.java
 * 
 * 2013-8-15-下午02:21:10
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.powerlong.electric.app.adapter.base.AdapterBase;

/**
 * 
 * FilterEntity
 * 
 * @author: Liang Wang
 * 2013-8-15-下午02:21:10
 * 
 * @version 1.0.0
 * 
 */
public class FilterEntity{
	private int mGroupId = 0;
	private int mChildId = 0;
	private String mValue = null;
	private boolean mIsChecked;
	private Number miniumm;
	private Number maxiumm;
	
	public FilterEntity(int groupId,int childId,String value, boolean isChecked){
		mGroupId= groupId;
		mChildId = childId;
		mValue = value;
		mIsChecked = isChecked;
	}
	
	public FilterEntity(Number min, Number max){
		maxiumm = max;
		miniumm = min;
	}
	
	public int getGroupId(){
		return mGroupId;
	}
	
	public int getChildId(){
		return mChildId;
	}
	
	public String getValue(){
		return mValue;
	}
	
	public boolean isChecked(){
		return mIsChecked;
	}
	
	public Number getMaxium(){
		return maxiumm;
	}
	
	public Number getMinium(){
		return miniumm;
	}
	
}
