/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * CollectionCache.java
 * 
 * 2013年8月28日-下午8:27:14
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.powerlong.electric.app.R;

/**
 * 
 * CollectionCache
 * 
 * @author: YangCheng Miao
 * 2013年8月28日-下午8:27:14
 * 
 * @version 1.0.0
 * 
 */
public class CollectionCache {
	 private View baseView;
	    private CheckBox cb;
	    private ImageView logo;
	    private TextView brand;
	    private TextView description;
	    private TextView price;
	    private TextView priceOrg;
	    private TextView sales;
	    
	    public CollectionCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public CheckBox getChecked() {
	    	if (cb == null) {
	    		cb = (CheckBox) baseView.findViewById(R.id.ck_commodity);
	    	}
	    	return cb;
	    }
	    
	    public ImageView getLogo() {
	        if (logo == null) {
	        	logo = (ImageView) baseView.findViewById(R.id.iv_collection_logo);
	        }
	        return logo;
	    }
	    
	    public TextView getBrand() {
	        if (brand == null) {
	        	brand = (TextView) baseView.findViewById(R.id.shopping_cart_list_brand);
	        }
	        return brand;
	    }

	    public TextView getDescription() {
	        if (description == null) {
	        	description = (TextView) baseView.findViewById(R.id.shopping_cart_list_describtion);
	        }
	        return description;
	    }
	    
	    public TextView getPrice() {
	    	if (price == null) {
	    		price = (TextView) baseView.findViewById(R.id.shopping_cart_list_price);
	    	}
	    	return price;
	    }
	    
	    public TextView getPriceOrg() {
	    	if (priceOrg == null) {
	    		priceOrg = (TextView) baseView.findViewById(R.id.shopping_cart_list_price_org);
	    	}
	    	return priceOrg;
	    }
	    
	    public TextView getSales() {
	        if (sales == null) {
	        	sales = (TextView) baseView.findViewById(R.id.shopping_cart_list_sales);
	        }
	        return sales;
	    }
}
