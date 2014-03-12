/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterSearchHistory.java
 * 
 * 2013-11-5-下午01:20:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.DomainSearchCategorySub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * AdapterSearchHistory
 * 
 * @author: hegao
 * 2013-11-5-下午01:20:29
 * 
 * @version 1.0.0
 * 
 */
public class AdapterSearchCategorySub<T> extends AdapterBaseCommon<T> {

	
	/**
	 * 创建一个新的实例 AdapterSearchHistory.
	 *
	 * @param context
	 */
	public AdapterSearchCategorySub(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.adapter_search_category_sub, null);
			vh.tv_summary = (TextView) convertView.findViewById(R.id.tv_summary);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DomainSearchCategorySub searchCategorySub = (DomainSearchCategorySub) getDomain(position);
		vh.tv_summary.setText(searchCategorySub.getName());
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tv_summary;
	}

}
