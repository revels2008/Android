package com.powerlong.electric.app.adapter;

import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.entity.ChatMsgEntity;
import com.powerlong.electric.app.utils.LogUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 消息ListView的Adapter
 * 
 * @author way
 */
public class ChatMsgViewAdapter extends BaseAdapter {
	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<ChatMsgEntity> coll;// 消息对象数组
	private LayoutInflater mInflater;

	public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);
		LogUtil.d("chatMsg", "position = " + position + "type= " + entity.getType());
		if (entity.getType() == 1) {// 收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {// 自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getType()==0;

		ViewHolder viewHolder = null;
		
//		if (convertView == null) {
			if (!isComMsg) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.isComMsg = isComMsg;

			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
		viewHolder.tvSendTime.setText(entity.getCreateDate());
		viewHolder.tvUserName.setText(entity.getSender());
		viewHolder.tvContent.setText(entity.getContent());
		viewHolder.icon.setImageResource(R.drawable.pic_56_user);
		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView icon;
		public boolean isComMsg = true;
	}

}
