/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterShopDetailCategory.java
 * 
 * 2013-11-7-下午08:56:24
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import javax.crypto.spec.IvParameterSpec;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.DomainShopDetailCategory;

/**
 * 
 * AdapterShopDetailCategory
 * 
 * @author: Hegao
 * 2013-11-7-下午08:56:24
 * 
 * @version 1.0.0
 * 
 */
public class AdapterShopDetailCategory<T> extends AdapterBaseCommon<T> {

	
	/**
	 * 创建一个新的实例 AdapterShopDetailCategory.
	 *
	 * @param context
	 */
	public AdapterShopDetailCategory(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.navigation_shop_item, null);
			vh.tv_navigation_shop = (TextView) convertView.findViewById(R.id.tv_navigation_shop);
			convertView.findViewById(R.id.tv_num).setVisibility(View.GONE);
			vh.iv_navigation_shop = (ImageView) convertView.findViewById(R.id.iv_navigation_shop);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DomainShopDetailCategory shopDetailCategory = (DomainShopDetailCategory) getDomain(position);
		vh.tv_navigation_shop.setText(shopDetailCategory.getName());
		if(shopDetailCategory.isSelect()){
			vh.iv_navigation_shop.setVisibility(View.VISIBLE);
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.B_black_30));
		}else{
			vh.iv_navigation_shop.setVisibility(View.GONE);
//			convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.nav_bg));
		}
		return convertView;
	}
	
	public void clearSelected(){
		for(T shopDetailCategory: list_data){
			((DomainShopDetailCategory)shopDetailCategory).setSelect(false);
		}
	}
	
	private class ViewHolder{
		public TextView tv_navigation_shop;
		public ImageView iv_navigation_shop;
		public TextView tv_num;
	}

}
