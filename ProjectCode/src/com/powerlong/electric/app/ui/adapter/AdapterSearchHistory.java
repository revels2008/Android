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
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainSearchCategorySub;
import com.powerlong.electric.app.domain.DomainSearchHistory;

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
public class AdapterSearchHistory<T> extends AdapterBaseCommon<T> {

	
	/**
	 * 创建一个新的实例 AdapterSearchHistory.
	 *
	 * @param context
	 */
	public AdapterSearchHistory(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.adapter_search_history, null);
			vh.tv_summary = (TextView) convertView.findViewById(R.id.tv_summary);
			vh.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DomainSearchHistory searchHistory = (DomainSearchHistory) getDomain(position);
		vh.tv_summary.setText(searchHistory.getSearchWord());
		int type = searchHistory.getType();
		if(type == Constants.FilterType.GOODS){
			vh.tv_type.setVisibility(View.VISIBLE);
			vh.tv_type.setText("商品");
		}else if(type == Constants.FilterType.SHOPS){
			vh.tv_type.setVisibility(View.VISIBLE);
			vh.tv_type.setText("店铺");
		}else{
			vh.tv_type.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tv_summary;
		public TextView tv_type;
	}

}
