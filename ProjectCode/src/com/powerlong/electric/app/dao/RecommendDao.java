/**
 * 宝龙电商
 * com.powerlong.electric.app.dao
 * NavigationBaseDao.java
 * 
 * 2013-8-20-上午11:13:15
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.powerlong.electric.app.db.DBHelper;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.RecommendEntity;
import com.tgb.lk.ahibernate.dao.impl.BaseDaoImpl;

/**
 * 
 * NavigationBaseDao
 * 
 * @author: Liang Wang
 * 2013-8-20-上午11:13:15
 * 
 * @version 1.0.0
 * 
 */
public class RecommendDao extends BaseDaoImpl<RecommendEntity> {

	public RecommendDao(Context context)
	{
		super(new DBHelper(context));
	}
	/**
	 * 创建一个新的实例 NavigationBaseDao.
	 *
	 * @param dbHelper
	 */
	public RecommendDao(SQLiteOpenHelper dbHelper) {
		super(dbHelper);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据所属ID获得附件列表
	 */
	public ArrayList<RecommendEntity> getAll()
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
	public boolean add(RecommendEntity entity)
	{
		insert(entity);
		return true;
	}
}
