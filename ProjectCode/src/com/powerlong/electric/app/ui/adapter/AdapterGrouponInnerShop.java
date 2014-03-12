/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterGrouponInnerShop.java
 * 
 * 2013-11-12-下午03:41:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.GrouponDetailInnerShop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * AdapterGrouponInnerShop
 * 
 * @author: He Gao
 * 2013-11-12-下午03:41:46
 * 
 * @version 1.0.0
 * 
 */
public class AdapterGrouponInnerShop<T> extends AdapterBaseCommon<T> {

	
	/**
	 * 创建一个新的实例 AdapterGrouponInnerShop.
	 *
	 * @param context
	 */
	public AdapterGrouponInnerShop(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder vh = null;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.adapter_search_category_sub, null);
			vh.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_summary);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		GrouponDetailInnerShop innerShop = (GrouponDetailInnerShop) getDomain(position);
		vh.tv_shop_name.setText(innerShop.getName());
		return convertView;
	}
	private class ViewHolder{
		public TextView tv_shop_name;
	}

}
