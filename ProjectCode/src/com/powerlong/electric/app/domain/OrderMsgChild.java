/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * OrderMsgChild.java
 * 
 * 2013-11-11-下午02:27:53
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
 * OrderMsgChild
 * 
 * @author: He Gao
 * 2013-11-11-下午02:27:53
 * 
 * @version 1.0.0
 * "itemList": [
                    {
                        "buyNum": 1,
                        "id": 738,
                        "image": "http://192.168.180.119:8090/group1/M00/01/B0/Cv4BIFJl94SAXTZJAABmq9IrZJQ579_130_130.jpg",
                        "itemName": "抽绳风衣 M/L深卡其",
                        "listPrice": 290,
                        "plPrice": 290,
                        "prop": " 尺码 : M/L 颜色 : 深卡其",
                        "salesPrice": 290,
                        "type": "0"
                    }
                ],
 * 
 */
public class OrderMsgChild {
	public static List<OrderMsgChild> convertJsonToOrderMsgChild(Context context,String json){
		List<OrderMsgChild> list = new ArrayList<OrderMsgChild>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i=0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				OrderMsgChild orderMsgChild = new OrderMsgChild();
				orderMsgChild.setBuyNum(jsObj.optString("buyNum", ""));
				orderMsgChild.setId(jsObj.optString("id", ""));
				orderMsgChild.setImage(jsObj.optString("image", ""));
				orderMsgChild.setItemName(jsObj.optString("itemName", ""));
				orderMsgChild.setListPrice(jsObj.optString("listPrice", ""));
				orderMsgChild.setPlPrice(jsObj.optString("plPrice", ""));
				orderMsgChild.setProp(jsObj.optString("prop", ""));
				orderMsgChild.setSalesPrice(jsObj.optString("salesPrice", ""));
				orderMsgChild.setType(jsObj.optString("type", ""));
				orderMsgChild.setDate(jsObj.optString("createdDate", ""));
				list.add(orderMsgChild);
			}
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return list;
	}
	
	
	private String buyNum;
	private String id;
	private String image;
	private String itemName;
	private String listPrice;
	private String plPrice;
	private String prop;
	private String salesPrice;
	private String type;
	private String date;
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public String getPlPrice() {
		return plPrice;
	}
	public void setPlPrice(String plPrice) {
		this.plPrice = plPrice;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * date
	 *
	 * @return  the date
	 * @since   1.0.0
	 */
	
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
