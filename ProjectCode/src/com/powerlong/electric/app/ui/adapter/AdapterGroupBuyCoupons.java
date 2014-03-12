/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterGroupBuyCoupons.java
 * 
 * 2013-11-9-下午05:26:23
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.entity.GrouponCouponEntity;
import com.powerlong.electric.app.ui.MyGrouponCouponActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * AdapterGroupBuyCoupons
 * 
 * @author: hegao
 * 2013-11-9-下午05:26:23
 * 
 * @version 1.0.0
 * 
 */
public class AdapterGroupBuyCoupons<T> extends AdapterBaseCommon<T> {
	private boolean isUsed;
	
	/**
	 * 创建一个新的实例 AdapterGroupBuyCoupons.
	 *
	 * @param context
	 */
	public AdapterGroupBuyCoupons(Context context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder vh = null;
		isUsed = MyGrouponCouponActivity.isUsed;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.profile_groupon_coupon_list_item, null);
			vh.title = (TextView) convertView.findViewById(R.id.title);
			vh.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
			vh.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		if(isUsed) {
			GrouponCouponEntity entity = (GrouponCouponEntity) getDomain(position);
			vh.title.setText("团购券码：" + entity.getGrouponTicketCode());
			vh.subtitle.setText("使用时间:"+entity.getUpdateDate());
			vh.content.setText("团购券名： " + entity.getGrouponName());
		} else {
			GrouponCouponEntity entity = (GrouponCouponEntity) getDomain(position);
			vh.title.setText("团购券码：" + entity.getGrouponTicketCode());
			vh.subtitle.setText("有效时间:"+entity.getLimitTime());
			vh.content.setText("团购券名： " + entity.getGrouponName());
		}
		
		
		return convertView;
	}
	private class ViewHolder {
		TextView title;
		TextView subtitle;
		TextView content;
	}

}
