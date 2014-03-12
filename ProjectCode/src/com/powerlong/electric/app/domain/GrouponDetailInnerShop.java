/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * GrouponDetailInnerShop.java
 * 
 * 2013-11-12-下午02:21:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * GrouponDetailInnerShop
 * 
 * @author: Hegao
 * 2013-11-12-下午02:21:46
 * 
 * @version 1.0.0
 * "shopList": [
//            {
//                "evaluation": "",
//                "id": 122,
//                "name": "老鸭粉丝汤",
//                "prop": ""
//            }
//        ]
 */
public class GrouponDetailInnerShop {
	public static List<GrouponDetailInnerShop> convertJsonToInnerShop(Context context,String json){
		List<GrouponDetailInnerShop> list = new ArrayList<GrouponDetailInnerShop>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i= 0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				GrouponDetailInnerShop innerShop = new GrouponDetailInnerShop();
				innerShop.setEvaluation(jsObj.optString("evaluation", ""));
				innerShop.setId(jsObj.optString("id", ""));
				innerShop.setName(jsObj.optString("name", ""));
				innerShop.setProp(jsObj.optString("prop", ""));
				list.add(innerShop);
			}
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return list;
	}
	private String evaluation;
	private String id;
	private String name;
	private String prop;
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	
}
