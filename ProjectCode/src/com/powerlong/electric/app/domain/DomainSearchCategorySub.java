/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * SearchCategorySub.java
 * 
 * 2013-11-5-上午09:41:57
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.config.Constants;

/**
 * 
 * SearchCategorySub
 * 
 * @author: hegao
 * 2013-11-5-上午09:41:57
 * 
 * @version 1.0.0
 * 
 */
public class DomainSearchCategorySub implements Serializable{
	public static List<DomainSearchCategorySub> convertJsonToSearchCategorySub(String json){
		List<DomainSearchCategorySub> list = new ArrayList<DomainSearchCategorySub>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i =0;i<jsArr.length();i++){
			 JSONObject jsObj = jsArr.getJSONObject(i);
			 DomainSearchCategorySub searchCategorySub = new DomainSearchCategorySub();
			 searchCategorySub.setAppItemCategoryId(jsObj.optString("appItemCategoryId",""));
			 searchCategorySub.setData(jsObj.optString("data",""));
			 searchCategorySub.setDescription(jsObj.optString("description", ""));
			 searchCategorySub.setId(jsObj.optString("id", ""));
			 searchCategorySub.setLevel(jsObj.optString("level", ""));
			 searchCategorySub.setLogo(jsObj.optString("logo", ""));
			 searchCategorySub.setMallId(jsObj.optString("mallId", ""));
			 searchCategorySub.setMethod(jsObj.optString("method", ""));
			 searchCategorySub.setName(jsObj.optString("name", ""));
			 searchCategorySub.setPid(jsObj.optString("pid", ""));
			 list.add(searchCategorySub);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		return list;
	}
	
//	"lowerCategoryList": [
//	                      {
//	                          "appItemCategoryId": 110,
//    "description": " ",
//    "id": 57,
//    "level": 2,
//    "logo": " ",
//    "mallId": 1,
//    "method": "http://plocc.powerlong.com/shopWeb/mobile/mobileSearch.htm",
//    "name": "饮品",
//    "pid": 9
//	                          "data": {
//	                              "categoryId": "110",
//	                              "classification": "item",
//	                              "mall": 1,
//	                              "orderBy": 0,
//	                              "page": 1
//	                          },

//	                      }
//	                  ],
	private String appItemCategoryId;
	private Data data;
	private String description;
	private String id;
	private String level;
	private String logo;
	private String mallId;
	private String method;
	private String name;
	private String pid;
	public String getAppItemCategoryId() {
		return appItemCategoryId;
	}
	public void setAppItemCategoryId(String appItemCategoryId) {
		this.appItemCategoryId = appItemCategoryId;
	}
	public Data getData() {
		return data;
	}
	public void setData(String strData) {
//		   "categoryId": "110",
//      "classification": "item",
//      "mall": 1,
//      "orderBy": 0,
//      "page": 1
		JSONObject jsObj = null;
		try {
			jsObj = new JSONObject(strData);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		Data data = new Data();
		data.setCategoryId(jsObj.optString("categoryId", ""));
		data.setClassification(jsObj.optString("classification", ""));
		data.setMall(jsObj.optString("mall", Constants.mallId+""));
		data.setOrderBy(jsObj.optString("orderBy", ""));
		data.setPage(jsObj.optString("page", ""));
		this.data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getMallId() {
		return mallId;
	}
	public void setMallId(String mallId) {
		this.mallId = mallId;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	private class Data implements Serializable{
//		   "categoryId": "110",
//           "classification": "item",
//           "mall": 1,
//           "orderBy": 0,
//           "page": 1
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
