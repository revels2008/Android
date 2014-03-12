/**
 * 宝龙电商
 * com.powerlong.electric.app.dao
 * SearchCategoryDetailDao.java
 * 
 * 2013-8-27-下午05:59:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.powerlong.electric.app.db.DBHelper;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.tgb.lk.ahibernate.dao.impl.BaseDaoImpl;

/**
 * 
 * SearchCategoryDetailDao
 * 
 * @author: Liang Wang
 * 2013-8-27-下午05:59:08
 * 
 * @version 1.0.0
 * 
 */
public class SearchCategoryDetailDao extends BaseDaoImpl<SearchCategoryDetailEntity>{
	public SearchCategoryDetailDao(Context context)
	{
		super(new DBHelper(context));
	}
	/**
	 * 创建一个新的实例 NavigationBrandDao.
	 *
	 * @param dbHelper
	 */
	public SearchCategoryDetailDao(SQLiteOpenHelper dbHelper) {
		super(dbHelper);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据所属ID获得附件列表
	 */
	public ArrayList<SearchCategoryDetailEntity> getAll()
	{
		return this.find();
	}
	
	/**
	 * 根据所属子类上级ID获得附件列表
	 */
	public ArrayList<SearchCategoryDetailEntity> getAllByParentId(long pid)
	{
		return find(null, "pid = ?", new String[] {Long.toString(pid)}, null, null,
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
	public boolean add(SearchCategoryDetailEntity entity)
	{
		insert(entity);
		return true;
	}
}
