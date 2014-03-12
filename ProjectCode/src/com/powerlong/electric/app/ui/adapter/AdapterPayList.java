/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterPayList.java
 * 
 * 2013-11-2-下午05:43:12
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.DomainChannelItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * AdapterPayList
 * 
 * @author: hegao
 * 2013-11-2-下午05:43:12
 * 
 * @version 1.0.0
 * 
 */
public class AdapterPayList<T> extends AdapterBaseCommon<T> {

	
	/**
	 * 创建一个新的实例 AdapterPayList.
	 *
	 * @param context
	 */
	public AdapterPayList(Context context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.popwindow_paylist_item, null);
			vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DomainChannelItem channelItem = (DomainChannelItem) list_data.get(position);
		vh.tv_name.setText(channelItem.getName());
		return convertView;
	}
	private class ViewHolder{
		public TextView tv_name;
	}
}
