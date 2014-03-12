/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * MyNotificationAdapter.java
 * 
 * 2013-9-8-下午12:07:09
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.entity.NotifyListEntity;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.widget.CollapsibleTextView;

/**
 * 
 * MyNotificationAdapter 我的账户 通知列表适配器
 * 
 * @author: Liang Wang
 * 2013-9-8-下午12:07:09
 * 
 * @version 1.0.0
 * 
 */
public class MyNotificationAdapter extends BaseAdapter {
	private Context mContext = null;
	private LayoutInflater mLayoutInflater = null;

	private String[] titles = new String[] { "侬好蛙干锅传奇:价值50元代金券一张",
			"大歌星量贩式KTV:3小时欢唱抵用券一张" };
	private String[] subTitles = new String[] { "2013.8.1", "2013.8.31" };

	public class ViewHolder {
		public ImageView photo;
		public TextView name;
		public TextView time;
		public ImageView type;
		public CollapsibleTextView content;
	}

	/**
	 * 
	 * 创建一个新的实例 CashCouponAdapter.
	 * 
	 * @param context
	 *            上下文实例
	 */
	public MyNotificationAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return DataCache.MyNotifyListCache.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return DataCache.MyNotifyListCache.get(position);
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
	
	public void clear() {
		DataCache.MyNotifyListCache.clear();
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NotifyListEntity entity = DataCache.MyNotifyListCache.get(position);
		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.profile_notification_list_item, null);
			holder.photo = (ImageView)convertView.findViewById(R.id.sender_photo);
			holder.name = (TextView) convertView.findViewById(R.id.sender_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.send_time);
			holder.content = (CollapsibleTextView) convertView
					.findViewById(R.id.notification_text);
			holder.type = (ImageView)convertView.findViewById(R.id.msg_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(entity!=null){
			String url = entity.getImage();
			if(!StringUtil.isEmpty(url)) {
				AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
				
				Drawable cachedImage = imageLoader.loadDrawable(url,holder.photo, new ILoadImageCallback() {
					@Override
					public void onObtainDrawable(Drawable drawable,ImageView imageView) {
						imageView.setImageDrawable(drawable);
					}
					
				});
				if (cachedImage == null) {
					holder.photo.setImageResource(R.drawable.pic_130);
				} else {
					holder.photo.setImageDrawable(cachedImage);
				}
			}
			if(entity.getType()==1)
				holder.type.setImageResource(R.drawable.notice_trade);
			else{
				holder.type.setImageResource(R.drawable.notice_syatem);
			}
			holder.name.setText(entity.getSender());
			holder.time.setText(entity.getCreateDate());
			holder.content.setDesc(entity.getContent(),BufferType.NORMAL);
		}
		return convertView;
	}
}
