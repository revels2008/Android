/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * OrderMsgNew.java
 * 
 * 2013-11-11-下午02:16:49
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.utils.StringUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * OrderMsgNew
 * 
 * @author: He Gao
 * 2013-11-11-下午02:16:49
 * 
 * @version 1.0.0
 * {
    "code": 0,
    "msg": "回复消息",
    "data": {
        "parentOrderList": [
            {
                "allNum": 1,
                "createdDate": "2013-11-4 18:13:09",
                "id": 1758,
                "itemList": [
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
                "orderNo": "1311049201GYHCCL",
                "realMon": 290
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 18:01:46",
                "id": 1756,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 594,
                        "image": "http://192.168.180.119:8090/group1/M00/01/9E/Cv4BIFJjnV2AYiguAABy7ZYsjjA439_130_130.jpg",
                        "itemName": "小脚裤（套西9007）G13205107 180（52码）杏色",
                        "listPrice": 399,
                        "plPrice": 399,
                        "prop": " 尺码 : 180（52码） 颜色 : 杏色",
                        "salesPrice": 399,
                        "type": "0"
                    }
                ],
                "orderNo": "1311049201TFG10B",
                "realMon": 407
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 17:58:00",
                "id": 1754,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 594,
                        "image": "http://192.168.180.119:8090/group1/M00/01/9E/Cv4BIFJjnV2AYiguAABy7ZYsjjA439_130_130.jpg",
                        "itemName": "小脚裤（套西9007）G13205107 180（52码）杏色",
                        "listPrice": 399,
                        "plPrice": 399,
                        "prop": " 尺码 : 180（52码） 颜色 : 杏色",
                        "salesPrice": 399,
                        "type": "0"
                    }
                ],
                "orderNo": "13110492018XIH2J",
                "realMon": 407
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 17:53:55",
                "id": 1752,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 737,
                        "image": "http://192.168.180.119:8090/group1/M00/01/B0/Cv4BIFJl94SAXTZJAABmq9IrZJQ579_130_130.jpg",
                        "itemName": "抽绳风衣 M/L浅卡其",
                        "listPrice": 290,
                        "plPrice": 290,
                        "prop": " 尺码 : M/L 颜色 : 浅卡其",
                        "salesPrice": 290,
                        "type": "0"
                    }
                ],
                "orderNo": "131104920144UCPY",
                "realMon": 290
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 17:46:37",
                "id": 1750,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 737,
                        "image": "http://192.168.180.119:8090/group1/M00/01/B0/Cv4BIFJl94SAXTZJAABmq9IrZJQ579_130_130.jpg",
                        "itemName": "抽绳风衣 M/L浅卡其",
                        "listPrice": 290,
                        "plPrice": 290,
                        "prop": " 尺码 : M/L 颜色 : 浅卡其",
                        "salesPrice": 290,
                        "type": "0"
                    }
                ],
                "orderNo": "1311049201MMT7V9",
                "realMon": 290
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 17:42:51",
                "id": 1748,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 632,
                        "image": "http://192.168.180.119:8090/group1/M00/01/A1/Cv4BIFJj1uGAJsf_AADlXDerlas371_130_130.jpg",
                        "itemName": "个人写真 / 情侣写真 最新套系① 399套餐",
                        "listPrice": 999,
                        "plPrice": 399,
                        "prop": " 套餐 : 399套餐",
                        "salesPrice": 399,
                        "type": "0"
                    }
                ],
                "orderNo": "1311049201U7NLKL",
                "realMon": 399
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 17:11:37",
                "id": 1746,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 744,
                        "image": "http://192.168.180.119:8090/group1/M00/01/BD/wKi0d1JupyCAUwB3AABXrqO4GYo553_130_130.jpg",
                        "itemName": "女士连衣裙 84A黑色",
                        "listPrice": 177,
                        "plPrice": 188,
                        "prop": " 尺码 : 84A 颜色 : 黑色",
                        "salesPrice": 188,
                        "type": "0"
                    }
                ],
                "orderNo": "1311049201V5TIRA",
                "realMon": 196
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 17:08:46",
                "id": 1744,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 744,
                        "image": "http://192.168.180.119:8090/group1/M00/01/BD/wKi0d1JupyCAUwB3AABXrqO4GYo553_130_130.jpg",
                        "itemName": "女士连衣裙 84A黑色",
                        "listPrice": 177,
                        "plPrice": 188,
                        "prop": " 尺码 : 84A 颜色 : 黑色",
                        "salesPrice": 188,
                        "type": "0"
                    }
                ],
                "orderNo": "1311049201SY1QG3",
                "realMon": 196
            },
            {
                "allNum": 2,
                "createdDate": "2013-11-4 17:00:47",
                "id": 1742,
                "itemList": [
                    {
                        "buyNum": 2,
                        "id": 744,
                        "image": "http://192.168.180.119:8090/group1/M00/01/BD/wKi0d1JupyCAUwB3AABXrqO4GYo553_130_130.jpg",
                        "itemName": "女士连衣裙 84A黑色",
                        "listPrice": 177,
                        "plPrice": 188,
                        "prop": " 尺码 : 84A 颜色 : 黑色",
                        "salesPrice": 188,
                        "type": "0"
                    }
                ],
                "orderNo": "1311049201G0LPRX",
                "realMon": 384
            },
            {
                "allNum": 1,
                "createdDate": "2013-11-4 16:58:26",
                "id": 1740,
                "itemList": [
                    {
                        "buyNum": 1,
                        "id": 632,
                        "image": "http://192.168.180.119:8090/group1/M00/01/A1/Cv4BIFJj1uGAJsf_AADlXDerlas371_130_130.jpg",
                        "itemName": "个人写真 / 情侣写真 最新套系① 399套餐",
                        "listPrice": 999,
                        "plPrice": 399,
                        "prop": " 套餐 : 399套餐",
                        "salesPrice": 399,
                        "type": "0"
                    }
                ],
                "orderNo": "13110492011GYDW3",
                "realMon": 399
            }
        ]
    }
}
 * 
 */
public class OrderMsgParent {
//	{
//        "allNum": 1,
//        "createdDate": "2013-11-4 18:13:09",
//        "id": 1758,
//        "itemList": [
//            {
//                "buyNum": 1,
//                "id": 738,
//                "image": "http://192.168.180.119:8090/group1/M00/01/B0/Cv4BIFJl94SAXTZJAABmq9IrZJQ579_130_130.jpg",
//                "itemName": "抽绳风衣 M/L深卡其",
//                "listPrice": 290,
//                "plPrice": 290,
//                "prop": " 尺码 : M/L 颜色 : 深卡其",
//                "salesPrice": 290,
//                "type": "0"
//            }
//        ],
//        "orderNo": "1311049201GYHCCL",
//        "realMon": 290
//    },
	public static List<OrderMsgParent> convertJsonToOrderMsgParent(Context context,String json, int stat){
		List<OrderMsgParent> list = new ArrayList<OrderMsgParent>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i=0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				OrderMsgParent orderMsgParent = new OrderMsgParent();
				orderMsgParent.setAllNum(jsObj.optString("allNum", ""));
				orderMsgParent.setCreatedDate(jsObj.optString("createdDate", ""));
				orderMsgParent.setId(jsObj.optString("id", ""));
				orderMsgParent.setItemList(jsObj.optString("itemList", ""));
				orderMsgParent.setOrderNo(jsObj.optString("orderNo", ""));
				orderMsgParent.setOrderStatus(jsObj.optString("orderStat", ""));
				String realMon = jsObj.optString("realMon", "");
				if(StringUtil.isNullOrEmpty(realMon)) {
					realMon = jsObj.optString("allPrice", "");
				}
				orderMsgParent.setRealMon(realMon);
				orderMsgParent.setStat(stat);
				list.add(orderMsgParent);
			}
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		return list;
	}
	
	private String allNum;
	private String createdDate;
	private String id;
	private String orderNo;
	private String realMon;
	private String itemList;
	private int stat;
	private String orderStatus;
	public String getAllNum() {
		return allNum;
	}
	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRealMon() {
		return realMon;
	}
	public void setRealMon(String realMon) {
		this.realMon = realMon;
	}
	public String getItemList() {
		return itemList;
	}
	public void setItemList(String itemList) {
		this.itemList = itemList;
	}
	/**
	 * stat
	 *
	 * @return  the stat
	 * @since   1.0.0
	 */
	
	public int getStat() {
		return stat;
	}
	/**
	 * @param stat the stat to set
	 */
	public void setStat(int stat) {
		this.stat = stat;
	}
	/**
	 * orderStatus
	 *
	 * @return  the orderStatus
	 * @since   1.0.0
	 */
	
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
