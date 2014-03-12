/**
 * 宝龙电商
 * com.powerlong.electric.app.domain
 * OrderMsg.java
 * 
 * 2013-11-1-下午04:22:16
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
 * OrderMsg
 * 
 * @author: hegao
 * 2013-11-1-下午04:22:16
 * 
 * @version 1.0.0
 * [
    {
        "pakageNum": 1,
        "itemNum": 1,
        "price": 6,
        "orderNo": "13110292016IV5CP"
    },
    {
        "pakageNum": 1,
        "itemNum": 1,
        "price": 89,
        "orderNo": "13110292015B1XSG"
    }
]
 * 
 * {
    "data": {
        "carNum": 0,
        "orderList": [
            {
                "itemNum": 1,
                "orderNo": "1311129201WWE43Y",
                "pakageNum": 1,
                "price": 186
            }
        ],
        "orderParentNo": "13111292011A4C2C",
        "paymentNo": "13111292017PVDU2"
    },
    "code": 0,
    "msg": "下订单成功"
}
 * 
 * 
 * 
 * 
 */
public class DomainOrderMsg {
	public static List<DomainOrderMsg> convertJsToOrderMsg(Context context,String json){
		List<DomainOrderMsg> list = new ArrayList<DomainOrderMsg>();
		try {
			JSONArray jsArr = new JSONArray(json);
			for(int i = 0;i<jsArr.length();i++){
				JSONObject jsObj = jsArr.getJSONObject(i);
				DomainOrderMsg orderMsg = new DomainOrderMsg();
				orderMsg.setOrderNo(jsObj.optString("orderNo", ""));
				orderMsg.setPakageNum(jsObj.optString("pakageNum", ""));
				orderMsg.setItemNum(jsObj.optString("itemNum", ""));
				orderMsg.setPrice(jsObj.optString("price", ""));
				list.add(orderMsg);
			}
		} catch (Exception e) {
			NotificatinUtils.showCustomToast(context, e.getLocalizedMessage());
		}
		return list;
	}
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 包裹数
	 */
	private String pakageNum;
	/**
	 * 商品数
	 */
	private String itemNum;
	/**
	 * 余额
	 */
	private String price;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPakageNum() {
		return pakageNum;
	}
	public void setPakageNum(String pakageNum) {
		this.pakageNum = pakageNum;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
