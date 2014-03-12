/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * DomainShopDetailCategory.java
 * 
 * 2013-11-7-下午08:59:21
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  image
	itemId
	name
	listPrice
	plPrice
	sellNumMonth
	type
	isFavour
	isCart
	
	image
itemId
name
listPrice
plPrice
sellNumMonth
type
isFavour
isCart

 * 
 * 
 * 
 * 
 * 
 * 
 * DomainShopDetailCategory
 * 
 * @author: hegao
 * 2013-11-7-下午08:59:21
 * 
 * @version 1.0.0
*		name
		id
		parentId
		level
		
		categoryList": [
            {
                "id": 263,
                "level": 1,
                "name": "连衣裙",
                "parentId": 0
            },
            {
                "id": 264,
                "level": 1,
                "name": "外套",
                "parentId": 0
            },
            {
                "id": 265,
                "level": 1,
                "name": "毛衣",
                "parentId": 0
            },
            {
                "id": 296,
                "level": 2,
                "name": "高领",
                "parentId": 265
            },
            {
                "id": 297,
                "level": 2,
                "name": "V领",
                "parentId": 265
            }
        ],

 * 
 */
public class DomainShopDetailCategory {
	public static List<DomainShopDetailCategory> convertJsonToCategory(String json){
		List<DomainShopDetailCategory> list = new ArrayList<DomainShopDetailCategory>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i= 0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				DomainShopDetailCategory shopDetailCategory = new DomainShopDetailCategory();
				shopDetailCategory.setId(jsObj.optString("id", ""));
				shopDetailCategory.setLevel(jsObj.optString("level", ""));
				shopDetailCategory.setName(jsObj.optString("name", ""));
				shopDetailCategory.setParentId(jsObj.optString("parentId", ""));
				shopDetailCategory.setSelect(false);
				list.add(shopDetailCategory);
			}
		} catch (Exception e) {
			throw new RuntimeException("DomainShopDetailCategory convertJsonToCategory", e);
		}
		return list;
	}
	
	
	private String name;
	private String id;
	private String parentId;
	private String level;
	private boolean isSelect = false;
	
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
