/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * GroupMoreDetailList.java
 * 
 * 2013-10-30-下午03:24:44
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
 * 
 * GroupMoreDetailList
 * 
 * @author: hegao
 * 2013-10-30-下午03:24:44
 * 
 * @version 1.0.0
 * 1.8	读取团购商品列表（顾诗杰）
http://www.powerlong-plaza.com/grouponWeb/mobile/grouponItemList.htm?data={"page":"1","type":"1","grouponId":"98"}

 * 	{
	    "code": 0,
	    "msg": "回复消息",
	    "data": {
	        "count": 2,
	        "itemList": [
	            {
	                "favourNum": 0,
	                "id": 336,
	                "itemName": "【宝龙测试】宝龙专用购物袋",
	                "listPrice": 10000,
	                "picturePath": "http://192.168.180.119:8090/group1/M00/00/00/Cv4BH1JVaTmAK3gUAABhpyzWupM776_130_130.jpg",
	                "plPrice": 99998,
	                "sellNum": 5
	            },
	            {
	                "favourNum": 0,
	                "id": 336,
	                "itemName": "【宝龙测试】宝龙专用购物袋",
	                "listPrice": 10000,
	                "picturePath": "http://192.168.180.119:8090/group1/M00/00/00/Cv4BH1JVaTmAK3gUAABhpyzWupM776_130_130.jpg",
	                "plPrice": 99998,
	                "sellNum": 5
	            }
	        ]
	    }
	}
 * 
 */
public class GroupMoreDetail {
	public static List<GroupMoreDetail> convertJson2GroupMoreDetailList(String json){
		List<GroupMoreDetail> list = new ArrayList<GroupMoreDetail>();
		try {
			JSONObject jsObj = new JSONObject(json);
			if(jsObj.getInt("count")>0){
				JSONArray jsArr = jsObj.getJSONArray("itemList");
				for(int i=0;i<jsArr.length();i++){
					GroupMoreDetail groupMoreDetail = new GroupMoreDetail();
					JSONObject jsObjItem = jsArr.getJSONObject(i);
					groupMoreDetail.setFavourNum(jsObjItem.getString("favourNum"));
					groupMoreDetail.setId(jsObjItem.getString("id"));
					groupMoreDetail.setItemName(jsObjItem.getString("itemName"));
					groupMoreDetail.setListPrice(jsObjItem.getString("listPrice"));
					groupMoreDetail.setPicturePath(jsObjItem.getString("picturePath"));
					groupMoreDetail.setPlPrice(jsObjItem.getString("plPrice"));
					groupMoreDetail.setSellNum(jsObjItem.getString("sellNum"));
					list.add(groupMoreDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	};
	public String favourNum;
	public String id;
	public String itemName;
	public String listPrice;
	public String picturePath;
	public String plPrice;
	public String sellNum;
	public String getFavourNum() {
		return favourNum;
	}
	public void setFavourNum(String favourNum) {
		this.favourNum = favourNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getListPrice() {
		return listPrice;
	}
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getPlPrice() {
		return plPrice;
	}
	public void setPlPrice(String plPrice) {
		this.plPrice = plPrice;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	
}
