/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * ShoppingCartSettleCache.java
 * 
 * 2013年8月29日-下午8:00:42
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerlong.electric.app.R;

/**
 * 
 * ShoppingCartSettleCache
 * 
 * @author: YangCheng Miao
 *  2013年8月29日-下午8:00:42
 * 
 * @version 1.0.0
 * 
 */
public class ShoppingCartSettleCache {
	private View baseView;
	private ImageView Logo;
	private TextView name;
	private TextView description;
	private TextView price;
	private TextView count;
	private ListView listView;

	public ShoppingCartSettleCache(View baseView) {
		this.baseView = baseView;
	}

	public ImageView getImageView() {
		if (Logo == null) {
			Logo = (ImageView) baseView.findViewById(R.id.im_commodity);
		}
		return Logo;
	}

	public TextView getName() {
		if (name == null) {
			name = (TextView) baseView.findViewById(R.id.tv_commodity_name);
		}
		return name;
	}

	public TextView getDescription() {
		if (description == null) {
			description = (TextView) baseView
					.findViewById(R.id.tv_commodity_introduction);
		}
		return description;
	}

	public TextView getPrice() {
		if (price == null) {
			price = (TextView) baseView
					.findViewById(R.id.tv_commodity_price_now);
		}
		return price;
	}

	public TextView getCount() {
		if (count == null) {
			count = (TextView) baseView.findViewById(R.id.tv_commodity_amount);
		}
		return count;
	}
	
	public ListView getList() {
		if (listView == null) {
			listView = (ListView) baseView.findViewById(R.id.settle_item_list);
		}
		return listView;
	}
}
