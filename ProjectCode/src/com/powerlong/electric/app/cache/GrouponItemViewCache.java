/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * FloorDetailViewCache.java
 * 
 * 2013年8月26日-下午4:22:25
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * FloorDetailViewCache
 * 
 * @author: YangCheng Miao
 * 2013年8月26日-下午4:22:25
 * 
 * @version 1.0.0
 * 
 */
public class GrouponItemViewCache {
	  private View baseView;
	    
	    private ImageView Logo;
	    private ImageView GrouponFlag;
	    private TextView name;
	    private TextView description;
	    private RatingBar grade;
	    private TextView category;
	    private TextView priceNow;
	    private TextView priceBefore;
	    private TextView num;
	    private TextView area;
	    
	    public GrouponItemViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public ImageView getImageView() {
	        if (Logo == null) {
	        	Logo = (ImageView) baseView.findViewById(R.id.floor_shopimage);
	        }
	        return Logo;
	    }
	    
	    public ImageView getGrouponView() {
	        if (GrouponFlag == null && Constants.isNeedGroupon) {
	        	GrouponFlag = (ImageView) baseView.findViewById(R.id.grouponFlag);
	        }
	        return GrouponFlag;
	    }
	    
	    public TextView getName() {
	        if (name == null) {
	        	name = (TextView) baseView.findViewById(R.id.floor_title_shop);
	        }
	        return name;
	    }

	    public TextView getCategory() {
	        if (category == null) {
	        	category = (TextView) baseView.findViewById(R.id.floor_shop_details);
	        }
	        return category;
	    }
	    
	    public RatingBar getGrade() {
	    	if (grade == null) {
	    		grade = (RatingBar) baseView.findViewById(R.id.floor_evaluation);
	    	}
	    	return grade;
	    }
	    
	    public TextView getDescription() {
	        if (description == null) {
	        	description = (TextView) baseView.findViewById(R.id.floor_shop_details);
	        }
	        return description;
	    }
	    
	    public TextView getPriceNow() {
	        if (priceNow == null) {
	        	priceNow = (TextView) baseView.findViewById(R.id.floor_products_plprice);
	        }
	        return priceNow;
	    }
	    
	    public TextView getPriceBefore() {
	        if (priceBefore == null) {
	        	priceBefore = (TextView) baseView.findViewById(R.id.floor_products_listprice);
	        }
	        return priceBefore;
	    }
	    
	    public TextView getNum() {
	        if (num == null) {
	        	num = (TextView) baseView.findViewById(R.id.floor_sell_num);
	        }
	        return num;
	    }
	    
	    public TextView getArea() {
	        if (area == null) {
	        	area = (TextView) baseView.findViewById(R.id.floor_area);
	        }
	        return area;
	    }

}
