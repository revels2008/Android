/**
 * 宝龙电商
 * com.powerlong.electric.app.dao
 * SearchHistoryDao.java
 * 
 * 2013-11-5-下午02:35:28
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.dao;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Context;

import com.powerlong.electric.app.domain.DomainSearchHistory;

/**
 * 
 * SearchHistoryDao
 * 
 * @author: hegao
 * 2013-11-5-下午02:35:28
 * 
 * @version 1.0.0
 * 
 */
public class SearchHistoryDao {
	private Context context;
	private FinalDb db;
	
	public SearchHistoryDao(Context context){
		this.context = context;
		db = FinalDb.create(context);
	}
	
	public void clear(){
		db.deleteByWhere(DomainSearchHistory.class, "");
	}
	
	public void insert(DomainSearchHistory searchHistory){
		if(isHistoryExists(searchHistory)){
			return;
		}
		db.save(searchHistory);
	}
	
	public List<DomainSearchHistory> getAll(){
		return db.findAll(DomainSearchHistory.class);
	}
	
	private boolean isHistoryExists(DomainSearchHistory searchHistory){
		DomainSearchHistory searchHistory2 =  db.findById(searchHistory.getId(), DomainSearchHistory.class);
		if(searchHistory2!=null){
			return true;
		}
		return false;
	}
}
