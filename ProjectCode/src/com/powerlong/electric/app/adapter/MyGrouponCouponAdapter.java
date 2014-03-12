/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * CashCouponAdapter.java
 * 
 * 2013-9-6-下午01:59:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.entity.GrouponCouponEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * CashCouponAdapter
 * 
 * @author: Liang Wang 2013-9-6-下午01:59:05
 * 
 * @version 1.0.0
 * 
 */
public class MyGrouponCouponAdapter extends BaseAdapter {

	private Context mContext = null;
	private LayoutInflater mLayoutInflater = null;

	private String[] titles = new String[] { "侬好蛙干锅传奇:价值50元代金券一张",
			"大歌星量贩式KTV:3小时欢唱抵用券一张" };
	private String[] subTitles = new String[] { "2013.8.1", "2013.8.31" };

	private class ViewHolder {
		TextView title;
		TextView subtitle;
	}

	/**
	 * 
	 * 创建一个新的实例 CashCouponAdapter.
	 * 
	 * @param context
	 *            上下文实例
	 */
	public MyGrouponCouponAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}
	/**
	 * 
	 * 清除listview内容
	 * @exception 
	 * @since  1.0.0
	 */
	public void clear(){
		DataCache.MyGrouponCouponListCache.clear();
		notifyDataSetChanged();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return DataCache.MyGrouponCouponListCache.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return DataCache.MyGrouponCouponListCache.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.profile_groupon_coupon_list_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.subtitle = (TextView) convertView
					.findViewById(R.id.subtitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GrouponCouponEntity entity = DataCache.MyGrouponCouponListCache.get(position);
		
		if(entity!=null){
			holder.title.setText(entity.getName());
			/*String subTitleText = String.format(
					mContext.getResources().getString(
							R.string.str_cash_coupon_expire_time), subTitles[0],
					subTitles[1]);*/
			holder.subtitle.setText("过期时间:"+entity.getLimitTime());
		}
		return convertView;
	}

}
