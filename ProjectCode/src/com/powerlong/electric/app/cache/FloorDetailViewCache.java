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
public class FloorDetailViewCache {
	  private View baseView;
	    
	    private ImageView Logo;
	    private TextView name;
	    private TextView description;
	    private RatingBar grade;
	    private TextView evaluate;
	    private TextView category;
	    private TextView num;
	    
	    public FloorDetailViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public ImageView getImageView() {
	        if (Logo == null) {
	        	Logo = (ImageView) baseView.findViewById(R.id.shop_list_shopimage);
	        }
	        return Logo;
	    }
	    
	    public TextView getName() {
	        if (name == null) {
	        	name = (TextView) baseView.findViewById(R.id.shop_list_title_shop);
	        }
	        return name;
	    }

	    public TextView getDescription() {
	        if (description == null) {
	        	description = (TextView) baseView.findViewById(R.id.shop_list_shop_details);
	        }
	        return description;
	    }
	    
	    public RatingBar getGrade() {
	    	if (grade == null) {
	    		grade = (RatingBar) baseView.findViewById(R.id.shop_list_evaluation);
	    	}
	    	return grade;
	    }
	    
	    public TextView getEvaluate() {
	        if (evaluate == null) {
	        	evaluate = (TextView) baseView.findViewById(R.id.tv_shop_list_evaluate_orig);
	        }
	        return evaluate;
	    }
	    
	    public TextView getCategory() {
	        if (category == null) {
	        	category = (TextView) baseView.findViewById(R.id.shop_list_products_categories);
	        }
	        return category;
	    }
	    
	    public TextView getNum() {
	        if (num == null) {
	        	num = (TextView) baseView.findViewById(R.id.shop_list_favour_num);
	        }
	        return num;
	    }

}
