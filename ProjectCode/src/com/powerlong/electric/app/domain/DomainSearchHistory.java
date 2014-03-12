/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * SearchHistory.java
 * 
 * 2013-11-5-下午01:21:13
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * 
 * SearchHistory
 * 
 * @author: He Gao
 * 2013-11-5-下午01:21:13
 * 
 * @version 1.0.0
 * //	{
//	    "data": {
//	        "count": 3,
//	        "page": 1,
//	        "pageSize": 20,
//	        "keyList": [
//	            "测试\r",
//	            "e1测试\r",
//	            "un38.3测试\r"
//	        ]
//	    },
//	    "code": 0,
//	    "msg": ""
//	}
 * 
 */
public class DomainSearchHistory implements Serializable{
	public static List<DomainSearchHistory> convertJsonToSearchHistory(String keyList){
		String strlist = keyList.substring(1, keyList.length()-1);
		List<DomainSearchHistory> list = new ArrayList<DomainSearchHistory>();
		
		String [] keys = strlist.split("\\,");
		for(int i=0;i<keys.length;i++){
			DomainSearchHistory searchHistory = new DomainSearchHistory();
			searchHistory.setSearchWord(keys[i]);
			list.add(searchHistory);
		}
		return list;
	};
	
	
	private String searchWord;
	private int type = 0; 

	@Id
	private String id;
	
	private String _id;
	public String get_Id() {
		return this.searchWord+this.type;
	}

	public void set_Id(String _id) {
		this._id = _id;
	}
	
	
	public String getId() {
		return this.searchWord+this.type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
}
