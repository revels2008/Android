/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterNavigationShopping.java
 * 
 * 2013-11-7-下午02:10:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.DMNavigationShopping;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * AdapterNavigationShopping
 * 
 * @author: HeGao
 * 2013-11-7-下午02:10:36
 * 
 * @version 1.0.0
 * 
 */
public class AdapterNavigationShopping<T> extends AdapterBaseCommon<T> {

	
	/**
	 * 创建一个新的实例 AdapterNavigationShopping.
	 *
	 * @param context
	 */
	public AdapterNavigationShopping(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.navigation_shop_item, null);
			vh.tv_navigation_shop = (TextView) convertView.findViewById(R.id.tv_navigation_shop);
			vh.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			vh.iv_navigation_shop = (ImageView) convertView.findViewById(R.id.iv_navigation_shop);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DMNavigationShopping dmNavigationShopping = (DMNavigationShopping) getDomain(position);
		vh.tv_navigation_shop.setText(dmNavigationShopping.getName());
		if(!"0".equals(dmNavigationShopping.getIsParent())){
			vh.tv_num.setText(dmNavigationShopping.getCount());
			vh.tv_num.setVisibility(View.VISIBLE);
			vh.iv_navigation_shop.setVisibility(View.GONE);
		}else{
			vh.tv_num.setVisibility(View.GONE);
			vh.iv_navigation_shop.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tv_navigation_shop;
		public ImageView iv_navigation_shop;
		public TextView tv_num;
	}
}
