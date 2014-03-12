/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterSearchCategory.java
 * 
 * 2013-11-5-上午11:41:10
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.DomainSearchCategory;

/**
 * 
 * AdapterSearchCategory
 * 
 * @author: hegao
 * 2013-11-5-上午11:41:10
 * 
 * @version 1.0.0
 * 
 */
public class AdapterSearchCategory<T> extends AdapterBaseCommon<T> {
	private Context context;
	
	/**
	 * 创建一个新的实例 AdapterSearchCategory.
	 *
	 * @param context
	 */
	public AdapterSearchCategory(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.adapter_search_category, null);
			vh.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
			vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			vh.tv_summary = (TextView) convertView.findViewById(R.id.tv_summary);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DomainSearchCategory searchCategory = (DomainSearchCategory) getDomain(position);
		vh.tv_title.setText(searchCategory.getName());
		vh.tv_summary.setText(searchCategory.getDescription());
		asynchronousLoadImage(vh.img_icon, searchCategory.getLogo());
		return convertView;
	}
	
	private  class ViewHolder{
		public ImageView img_icon;
		public TextView tv_title;
		public TextView tv_summary;
	}
	
}
