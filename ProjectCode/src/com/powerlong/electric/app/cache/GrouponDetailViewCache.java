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
public class GrouponDetailViewCache {
	  private View baseView;
	    
	    private ImageView Logo;
	    private TextView name;
	    private TextView description;
	    private RatingBar grade;
	    private TextView category;
	    private TextView num;
	    
	    public GrouponDetailViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public ImageView getImageView() {
	        if (Logo == null) {
	        	Logo = (ImageView) baseView.findViewById(R.id.floor_shopimage);
	        }
	        return Logo;
	    }
	    
	    public TextView getName() {
	        if (name == null) {
	        	name = (TextView) baseView.findViewById(R.id.floor_title_shop);
	        }
	        return name;
	    }

	    public TextView getDescription() {
	        if (description == null) {
	        	description = (TextView) baseView.findViewById(R.id.floor_shop_details);
	        }
	        return description;
	    }
	    
	    public RatingBar getGrade() {
	    	if (grade == null) {
	    		grade = (RatingBar) baseView.findViewById(R.id.floor_evaluation);
	    	}
	    	return grade;
	    }
	    
	    public TextView getCategory() {
	        if (category == null) {
	        	category = (TextView) baseView.findViewById(R.id.floor_products_categories);
	        }
	        return category;
	    }
	    
	    public TextView getNum() {
	        if (num == null) {
	        	num = (TextView) baseView.findViewById(R.id.floor_favour_num);
	        }
	        return num;
	    }

}
