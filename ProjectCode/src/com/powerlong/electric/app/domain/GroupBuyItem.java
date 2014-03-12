/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * GroupBuyItem.java
 * 
 * 2013-10-31-下午05:19:15
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.powerlong.electric.app.utils.NotificatinUtils;

/**
 * 
 * GroupBuyItem
 * 
 * @author: hegao
 * 2013-10-31-下午05:19:15
 * 
 * @version 1.0.0
 * 
 */
public class GroupBuyItem {
	public static List<GroupBuyItem> convertJsonToGroupBuyItemList(Context context,String json){
		List<GroupBuyItem> list = new ArrayList<GroupBuyItem>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i=0; i<jsArr.length();i++ ){
				GroupBuyItem groupBuyItem = new GroupBuyItem();
				JSONObject jsObj = jsArr.getJSONObject(i);
				groupBuyItem.setId(jsObj.optString("id", ""));
				groupBuyItem.setImage(jsObj.optString("image", ""));
				groupBuyItem.setLink(jsObj.optString("link", ""));
				groupBuyItem.setListPrice(jsObj.optString("listPrice", ""));
				groupBuyItem.setName(jsObj.optString("name", ""));
				groupBuyItem.setPlPrice(jsObj.optString("plPrice", ""));
				groupBuyItem.setSellNum(jsObj.optString("sellNum", ""));
				list.add(groupBuyItem);
			}
		} catch (Exception e) {
			NotificatinUtils.showCustomToast(context, e.getLocalizedMessage());
		}
		return list;
	}
//	{
//	    "code": 0,
//	    "msg": "回复消息",
//	    "data": {
//	        "grouponList": [
//	            {
//	                "id": 187,
//	                "image": "http://192.168.180.119:8090/group1/M00/00/00/Cv4BIFJVZk2AMy-sAAPbZJ8wjZI441_400_720_130_130.jpg",
//	                "link": "",
//	                "listPrice": 9,
//	                "name": "大和屋章鱼烧套餐",
//	                "plPrice": 9,
//	                "sellNum": 3
//	            },
//	            {
//	                "id": 188,
//	                "image": "http://192.168.180.119:8090/group1/M00/00/00/Cv4BIFJVa1uANRP4AABhpyzWupM031_400_720_130_130.jpg",
//	                "link": "",
//	                "listPrice": 10000,
//	                "name": "宝龙测试_宝龙团购测试",
//	                "plPrice": 10000,
//	                "sellNum": 3
//	            },
//	            {
//	                "id": 190,
//	                "image": "http://192.168.180.119:8090/group1/M00/01/A3/Cv4BH1JkjkSARdMOAADXfMGkPpY785_280_440_130_130.jpg",
//	                "link": "",
//	                "listPrice": 199,
//	                "name": "宝龙测试温泉",
//	                "plPrice": 199,
//	                "sellNum": 0
//	            },
//	            {
//	                "id": 191,
//	                "image": "http://192.168.180.119:8090/group1/M00/01/AF/Cv4BH1Jl3EuAN1FTAADbrpGc0II153_280_440_130_130.jpg",
//	                "link": "",
//	                "listPrice": 290,
//	                "name": "宝龙测试月饼",
//	                "plPrice": 290,
//	                "sellNum": 1
//	            },
//	            {
//	                "id": 192,
//	                "image": "http://192.168.180.119:8090/group1/M00/01/AE/Cv4BIFJl3oGAIWrVAAEnVZEtuOo622_280_440_130_130.jpg",
//	                "link": "",
//	                "listPrice": 49,
//	                "name": "宝龙测试顶级肥牛",
//	                "plPrice": 49,
//	                "sellNum": 0
//	            },
//	            {
//	                "id": 193,
//	                "image": "http://192.168.180.119:8090//images/no-face-small.png",
//	                "link": "",
//	                "listPrice": 99,
//	                "name": "测试_1团购",
//	                "plPrice": 99,
//	                "sellNum": 0
//	            },
//	            {
//	                "id": 194,
//	                "image": "http://192.168.180.119:8090//images/no-face-small.png",
//	                "link": "",
//	                "listPrice": 29,
//	                "name": "大骨汤",
//	                "plPrice": 29,
//	                "sellNum": 101
//	            },
//	            {
//	                "id": 195,
//	                "image": "http://192.168.180.119:8090//images/no-face-small.png",
//	                "link": "",
//	                "listPrice": 29,
//	                "name": "钵钵鸡",
//	                "plPrice": 29,
//	                "sellNum": 1
//	            },
//	            {
//	                "id": 196,
//	                "image": "http://192.168.180.119:8090//images/no-face-small.png",
//	                "link": "",
//	                "listPrice": 29,
//	                "name": "钵钵鸡",
//	                "plPrice": 29,
//	                "sellNum": 1
//	            },
//	            {
//	                "id": 197,
//	                "image": "http://192.168.180.119:8090//images/no-face-small.png",
//	                "link": "",
//	                "listPrice": 29,
//	                "name": "大歌星",
//	                "plPrice": 29,
//	                "sellNum": 0
//	            }
//	        ]
//	    }
//	}
	private String id;
	private String image;
	private String link;
	private String listPrice;
	private String name;
	private String plPrice;
	private String sellNum;
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getListPrice() {
		return listPrice;
	}
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
