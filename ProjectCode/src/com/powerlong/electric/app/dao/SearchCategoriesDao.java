/**
 * 宝龙电商
 * com.powerlong.electric.app.dao
 * SearchCategoriesDao.java
 * 
 * 2013-8-26-上午10:12:03
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.powerlong.electric.app.db.DBHelper;
import com.powerlong.electric.app.entity.NavigationActivityEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.tgb.lk.ahibernate.dao.impl.BaseDaoImpl;

/**
 * 
 * SearchCategoriesDao
 * 
 * @author: Liang Wang
 * 2013-8-26-上午10:12:03
 * 
 * @version 1.0.0
 * 
 */
public class SearchCategoriesDao extends BaseDaoImpl<SearchCategoryEntity>{
	private SearchCategoryDetailDao detailDao = null;
	
	public SearchCategoriesDao(Context context)
	{
		super(new DBHelper(context));
	}
	
	public void creatDetaildDao(Context context){
		detailDao = new SearchCategoryDetailDao(context);
	}
	
	public SearchCategoryDetailDao getDetailDao(){
		return detailDao;
	}
	/**
	 * 创建一个新的实例 NavigationBrandDao.
	 *
	 * @param dbHelper
	 */
	public SearchCategoriesDao(SQLiteOpenHelper dbHelper) {
		super(dbHelper);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据所属ID获得附件列表
	 */
	public ArrayList<SearchCategoryEntity> getAll()
	{
		return this.find();
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
	public boolean add(SearchCategoryEntity entity)
	{
		insert(entity);
		return true;
	}
}
