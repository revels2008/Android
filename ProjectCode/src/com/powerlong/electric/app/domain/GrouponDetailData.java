/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * GrouponDetailData.java
 * 
 * 2013-11-12-下午02:18:32
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * GrouponDetailData
 * 
 * @author: He Gao
 * 2013-11-12-下午02:18:32
 * 
 * @version 1.0.0
 * 
 * {
    "code": 0,
    "msg": "回复消息",
    "data": {
//        "buyNotify": "1",
//        "buyNum": 10,
//        "commentNum": 0,
//        "content": "<p>1&nbsp;</p>",
//        "countDownTime": 1385740800000,
//        "imageList": [
//            {
//                "picturePath": "http://192.168.180.119:8090/group1/M00/01/C8/wKi0d1J-3OiAXnfRAADH7VEYClc177_246_246.jpg"
//            }
//        ],
//        "isPaidfor": 1,
//        "isReturnItem": 1,
//        "isReturnMoney": 1,
//        "itemGroupList": [
//            {
//                "itemGrpoupId": 744,
//                "itemList": [
//                    {
//                        "id": 744,
//                        "itemGroupId": 744,
//                        "itemName": "女士连衣裙 84A黑色",
//                        "number": 1,
//                        "plPrice": 188
//                    }
//                ]
//            }
//        ],
//        "itemNum": 0,
//        "link": "",
//        "listPrice": 13,
//        "name": "素食",
//        "plPrice": 13,
//        "sellNum": 23,
//        "shopList": [
//            {
//                "evaluation": "",
//                "id": 122,
//                "name": "老鸭粉丝汤",
//                "prop": ""
//            }
//        ]
//    }
    
    
}
 * 
 */
public class GrouponDetailData{
	public static GrouponDetailData convertJsonToDetail(String json) throws JSONException{
		JSONObject jsObj = new JSONObject(json);
		GrouponDetailData detailData = new GrouponDetailData();
		detailData.setBuyNotify(jsObj.optString("buyNotify", ""));
		detailData.setBuyNum(jsObj.optString("buyNum", ""));
		detailData.setCommentNum(jsObj.optString("commentNum", ""));
		detailData.setContent(jsObj.optString("content", ""));
		detailData.setCountDownTime(jsObj.optString("countDownTime", ""));
		detailData.setImageList(jsObj.optString("imageList", ""));
		detailData.setIsPaidfor(jsObj.optString("isPaidfor", ""));
		detailData.setIsReturnItem(jsObj.optString("isReturnItem", ""));
		detailData.setIsReturnMoney(jsObj.optString("isReturnMoney", ""));
		detailData.setItemGroupList(jsObj.optString("itemGroupList", ""));
		detailData.setItemNum(jsObj.optString("itemNum", ""));
		detailData.setLink(jsObj.optString("link", ""));
		detailData.setListPrice(jsObj.optString("listPrice", ""));
		detailData.setName(jsObj.optString("name", ""));
		detailData.setPlPrice(jsObj.optString("plPrice", ""));
		detailData.setSellNum(jsObj.optString("sellNum", ""));
		detailData.setShopList(jsObj.optString("shopList", ""));
		return detailData;
	}
	private String buyNotify;
	private String buyNum;
	private String commentNum;
	private String content;
	private String countDownTime;
	private String imageList;
	private String isPaidfor;
	private String isReturnItem;
	private String isReturnMoney;
	private String itemGroupList;
	private String itemNum;
	private String link;
	private String listPrice;
	private String name;
	private String plPrice;
	private String sellNum;
	private String shopList;
	public Object getcountDownTime;
	public String getBuyNotify() {
		return buyNotify;
	}
	public void setBuyNotify(String buyNotify) {
		this.buyNotify = buyNotify;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCountDownTime() {
		return countDownTime;
	}
	public void setCountDownTime(String countDownTime) {
		this.countDownTime = countDownTime;
	}
	public String getImageList() {
//		"imageList": [
//{
//"picturePath": "http://192.168.180.119:8090/group1/M00/01/C8/wKi0d1J-3OiAXnfRAADH7VEYClc177_246_246.jpg"
//}
//],	
		String imgPath = null;
		try {
			JSONArray jsArr = new JSONArray(imageList);
			JSONObject jsObj = jsArr.getJSONObject(0);
			imgPath = jsObj.getString("picturePath");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imgPath;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public String getIsPaidfor() {
		return isPaidfor;
	}
	public void setIsPaidfor(String isPaidfor) {
		this.isPaidfor = isPaidfor;
	}
	public String getIsReturnItem() {
		return isReturnItem;
	}
	public void setIsReturnItem(String isReturnItem) {
		this.isReturnItem = isReturnItem;
	}
	public String getIsReturnMoney() {
		return isReturnMoney;
	}
	public void setIsReturnMoney(String isReturnMoney) {
		this.isReturnMoney = isReturnMoney;
	}
	public String getItemGroupList() {
		return itemGroupList;
	}
	public void setItemGroupList(String itemGroupList) {
		this.itemGroupList = itemGroupList;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
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
	public String getShopList() {
		return shopList;
	}
	public void setShopList(String shopList) {
		this.shopList = shopList;
	}
	
}