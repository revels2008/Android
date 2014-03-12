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
import android.util.Log;

import com.powerlong.electric.app.db.DBHelper;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
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
public class NavigationBaseDao extends BaseDaoImpl<NavigationBaseEntity> {

	public NavigationBaseDao(Context context)
	{
		super(new DBHelper(context));
	}
	/**
	 * 创建一个新的实例 NavigationBaseDao.
	 *
	 * @param dbHelper
	 */
	public NavigationBaseDao(SQLiteOpenHelper dbHelper) {
		super(dbHelper);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据所属ID获得附件列表
	 */
/*	public ArrayList<NavigationBaseEntity> getDataByNavId(String navId, String level, String parentId)
	{
		if(StringUtil.isEmpty(level)&&StringUtil.isEmpty(parentId))
		return find(null, "navId = ?", new String[] { navId}, null, null,
				null, null);
//		Log.v("NavigationBaseDao", "");
		else{
			LogUtil.d("NavigationBaseDao", "parentId = " + parentId + "level ="+level);
			if(!StringUtil.isEmpty(navId)&&!StringUtil.isEmpty(level)){
				return find(null, "parent_id = ? AND level=?", new String[] { navId,level}, null, null,
						null, null);
			}
			else if(!StringUtil.isEmpty(level)&&StringUtil.isEmpty(parentId)){
				return find(null, "level = ?", new String[] { level}, null, null,
						null, null);
			}else if(!StringUtil.isEmpty(parentId)&&StringUtil.isEmpty(level)){
				return find(null, "parent_id = ?", new String[] { parentId}, null, null,
						null, null);
			}else{
				return find(null, "level = ? AND parent_id = ?", new String[] { level,parentId}, null, null,
						null, null);
			}
		}
	}*/

	public ArrayList<NavigationBaseEntity> getDataById(String navId)
	{

		return find(null, "nav_id = ?", new String[] { navId}, null, null,
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
	public boolean add(NavigationBaseEntity entity)
	{
		insert(entity);
		return true;
	}
}
