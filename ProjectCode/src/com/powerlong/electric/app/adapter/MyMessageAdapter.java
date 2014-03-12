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

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ChatMsgEntity;
import com.powerlong.electric.app.entity.ChatMsgListEntity;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.widget.CollapsibleTextView;

/**
 * 
 * MyMessageAdapter 我的账户  消息列表适配器
 * 
 * @author: Liang Wang
 * 2013-9-8-下午12:07:09
 * 
 * @version 1.0.0
 * 
 */
public class MyMessageAdapter extends BaseAdapter {
	private Context mContext = null;
	private LayoutInflater mLayoutInflater = null;
	private Activity mActivity;

	private String[] titles = new String[] { "侬好蛙干锅传奇:价值50元代金券一张",
			"大歌星量贩式KTV:3小时欢唱抵用券一张" };
	private String[] subTitles = new String[] { "2013.8.1", "2013.8.31" };

	public class ViewHolder {
		public ImageView photo;
		public TextView name;
		public TextView time;
		public TextView content;
	}

	/**
	 * 
	 * 创建一个新的实例 CashCouponAdapter.
	 * 
	 * @param context
	 *            上下文实例
	 */
	public MyMessageAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mActivity = (Activity)context;
	}

	/**
	 * 清除listview内容
	 */
	public void clear(){
		DataCache.MyMsgListCache.clear();
		notifyDataSetChanged();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return DataCache.MyMsgListCache.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return DataCache.MyMsgListCache.get(position);
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
		ChatMsgListEntity entity = DataCache.MyMsgListCache.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.profile_message_list_item, null);
			holder.photo = (ImageView)convertView.findViewById(R.id.sender_photo);
			holder.name = (TextView) convertView.findViewById(R.id.sender_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.send_time);
			holder.content = (TextView) convertView
					.findViewById(R.id.notification_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
		if(entity!=null){
			holder.name.setText(entity.getSender());
			holder.content.setText(entity.getContent(),BufferType.NORMAL);
			holder.time.setText(entity.getCreateDate());
			FinalBitmap fb = FinalBitmap.create(mActivity);
	        fb.configDiskCachePath(Constants.IMG_CACHE_PATH);
	        fb.configLoadfailImage(R.drawable.pic_56);
	        fb.display(holder.photo, entity.getImage());
		}
		return convertView;
	}
}
