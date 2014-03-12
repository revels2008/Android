/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * DMNavigationShopping.java
 * 
 * 2013-11-6-下午06:09:59
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonArrayRequest;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.sqlite.Id;

/**
 * 
 * DMNavigationShopping
 * 
 * @author: hegao
 * 2013-11-6-下午06:09:59
 * 
 * @version 1.0.0
 * 
 * 
 * 
 */
public class DMNavigationShopping {
//	{
//      "count": -1,
//      "groupId": 1,
//      "icon": "",
//      "isParent": 0,
//      "level": 2,
//      "method": "",
//      "name": "服装",
//      "navId": 1,
//      "parentId": 6
//  },
//  {
//  "count": 50,
//  "data": {
//      "categoryId": "19",
//      "classification": "item",
//      "mall": 1,
//      "orderBy": 0,
//      "page": 1
//  },
//  "groupId": 1,
//  "icon": "",
//  "isParent": 1,
//  "level": 3,
//  "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
//  "name": "女装",
//  "navId": 19,
//  "parentId": 1
//},
//{
//  "count": 174,
//  "data": {
//      "categoryId": "20",
//      "classification": "item",
//      "mall": 1,
//      "orderBy": 0,
//      "page": 1
//  },
//  "groupId": 1,
//  "icon": "",
//  "isParent": 1,
//  "level": 3,
//  "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
//  "name": "男装",
//  "navId": 20,
//  "parentId": 1
//},
	public static List<DMNavigationShopping> convertJsonToDMNavigationShopping(String json){
		List<DMNavigationShopping> list = new ArrayList<DMNavigationShopping>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i=0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				DMNavigationShopping dmNavigationShopping = new DMNavigationShopping();
				dmNavigationShopping.setCount(jsObj.optString("count", ""));
				dmNavigationShopping.setData(jsObj.optString("data", ""));
				dmNavigationShopping.setGroupId(jsObj.optString("groupId", ""));
				dmNavigationShopping.setIcon(jsObj.optString("icon", ""));
				dmNavigationShopping.setIsParent(jsObj.optString("isParent", ""));
				dmNavigationShopping.setLevel(jsObj.optString("level", ""));
				dmNavigationShopping.setMethod(jsObj.optString("method", ""));
				dmNavigationShopping.setName(jsObj.optString("name", ""));
				dmNavigationShopping.setNavId(jsObj.optString("navId", ""));
				dmNavigationShopping.setParentId(jsObj.optString("parentId", ""));
				list.add(dmNavigationShopping);
			}
		} catch (JSONException e) {
			throw new RuntimeException("DMNavigationShopping json 异常", e);
		}
		return list;
	}
	public static List<DMNavigationShopping> getNavigationShoppingSecond(List<DMNavigationShopping> list,String parentId){
		List<DMNavigationShopping> list_data = new ArrayList<DMNavigationShopping>();
		for(DMNavigationShopping dmNavigationShopping : list){
			if(parentId.equals(dmNavigationShopping.getParentId())){
				list_data.add(dmNavigationShopping);
			}
		}
		return list_data;
	}
	public static List<DMNavigationShopping> getNavigationShoppingFirst(List<DMNavigationShopping> list){
		List<DMNavigationShopping> list_data = new ArrayList<DMNavigationShopping>();
		for(DMNavigationShopping dmNavigationShopping : list){
			if("0".equals(dmNavigationShopping.isParent)){
				list_data.add(dmNavigationShopping);
			}
		}
		return list_data;
	}
	
	@Id
	private String navId;
	private String count;
	private String groupId;
	private String icon;
	private String isParent;
	private String level;
	private String method;
	private String name;
	private String parentId;
	private String data;
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNavId() {
		return navId;
	}

	public void setNavId(String navId) {
		this.navId = navId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private class Data{
		private String categoryId;
		private String classification;
		private String mall;
		private String orderBy;
		private String page;
		public String getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
		public String getClassification() {
			return classification;
		}
		public void setClassification(String classification) {
			this.classification = classification;
		}
		public String getMall() {
			return mall;
		}
		public void setMall(String mall) {
			this.mall = mall;
		}
		public String getOrderBy() {
			return orderBy;
		}
		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
		}
		public String getPage() {
			return page;
		}
		public void setPage(String page) {
			this.page = page;
		}
	}
	
}
