/**
 * 宝龙电商
 * com.powerlong.electric.app.dao
 * NavigationBrandDao.java
 * 
 * 2013-8-20-上午10:11:25
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.dao;

import java.util.ArrayList;

import android.database.sqlite.SQLiteOpenHelper;

import com.powerlong.electric.app.entity.NavigationFoodsEntity;
import com.tgb.lk.ahibernate.dao.impl.BaseDaoImpl;

/**
 * 
 * NavigationFoodsDao
 * 
 * @author: Liang Wang
 * 2013-8-20-上午10:11:25
 * 
 * @version 1.0.0
 * 
 */
public class NavigationFoodsDao extends BaseDaoImpl<NavigationFoodsEntity> {

	
	/**
	 * 创建一个新的实例 NavigationBrandDao.
	 *
	 * @param dbHelper
	 */
	public NavigationFoodsDao(SQLiteOpenHelper dbHelper) {
		super(dbHelper);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据所属ID获得附件列表
	 */
	public ArrayList<NavigationFoodsEntity> getBySelfId(String selfId)
	{
		return find(null, "self_id = ?", new String[] { selfId }, null, null,
				null, null);
	}

	/**
	 * 添加的时候应该先清空
	 * 
	 */
	/*
	 * public void deleteByField(String fieldName,String fieldValue) {
	 * deleteByField("belongId", fieldValue); }
	 */

	/**
	 * 添加
	 * 
	 */
	public boolean add(NavigationFoodsEntity entity)
	{
		insert(entity);
		return true;
	}

}
