/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * MyOrderAdapter.java
 * 
 * 2013年9月14日-上午11:52:24
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.OrderListEntity;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;
import com.example.volleytest.cache.BitmapCache;

/**
 * 
 * MyOrderAdapter
 * 
 * @author: Liang Wang 2013年9月14日-上午11:52:24
 * 
 * @version 1.0.0
 * 
 */
public class MyOrderAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<OrderListEntity> mValues;
	private ImageLoader imageLoader;
	private RequestQueue mRequestQueue;
	private ItemListEntity UserEntity;
   
	public MyOrderAdapter(Context context, List<OrderListEntity> values) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mValues = values;
		mRequestQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mValues != null)
			return mValues.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (position == getCount() - 1 || mValues == null) {
			return null;
		}
		return mValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (position == getCount() - 1 || mValues == null) {
			return 0;
		}
		return mValues.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_myorder, null);
			holder = new ViewHolder();
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			holder.tv_order_msg = (TextView) convertView
					.findViewById(R.id.tv_order_msg);
			holder.iv_group_image = (ImageView) convertView
					.findViewById(R.id.iv_group_image);
			holder.iv_image = (NetworkImageView) convertView
					.findViewById(R.id.iv_image);
			holder.btn = (Button) convertView.findViewById(R.id.btn_ordersta);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		OrderListEntity entity = mValues.get(position);
		holder.tv_price.setText("￥" + entity.getAllPrice());
		holder.tv_count.setText(String.valueOf(entity.getAllNum()));
		
		LogUtil.d("jsonparser", "getView id="+entity.getId());
		
		ArrayList<ItemListEntity> UserEntities= DataCache.UserOrderItemListCache.get(entity.getId());
		
		
		if(UserEntities.size()>0){
			UserEntity = UserEntities.get(0);
			holder.tv_order_msg.setText(String.valueOf(UserEntity.getItemName()));
			
			SetButton(holder.btn, entity.getOrderStat());
			if (entity.getOrderType() == 1) {
				holder.iv_group_image.setVisibility(View.VISIBLE);
			} else {
				holder.iv_group_image.setVisibility(View.INVISIBLE);
			}
			//设置图片
			if(!StringUtil.isNullOrEmpty(UserEntity.getImage())){
				holder.iv_image.setImageUrl(UserEntity.getImage(), imageLoader);
			}else{
				holder.iv_image.setImageResource(R.drawable.pic_96);
			}
			
			holder.btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

				}
			});
		}
		else{
			holder.iv_image.setImageResource(R.drawable.pic_96);
			holder.iv_group_image.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView tv_order_msg, tv_price, tv_count;
		ImageView iv_group_image;
		NetworkImageView iv_image;
		Button btn;
	}

	private void SetButton(Button btn, String state) {
		// orderStat 订单状态 0：已提交 待支付 1：已付款 待发货 2：已发货 待客户确认
		// 3：申请退款（已经提交申请，已发货）待商家审核
		// 4：申请退款（已经提交申请，未发货）待商家审核 5：商家审核未通过
		// 6：商家审核通过，待客户退货 7：待退款 8：退款成功
		// 9：交易成功 10：取消订单 11：评论 12：拒签

		if (state.equals("0")) {
			btn.setText("支付");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("1")) {
			btn.setText("已经支付");
			btn.setBackgroundResource(R.drawable.btn_bgwhite_press);
			btn.setClickable(false);
		} else if (state.equals("2")) {
			btn.setText("确认收货");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("3")) {
			btn.setText("申请退款");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("4")) {
			btn.setText("申请退款");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("5")) {
			btn.setText("商家审核未通过");
			btn.setBackgroundResource(R.drawable.btn_bgwhite_press);
			btn.setClickable(false);
		} else if (state.equals("6")) {
			btn.setText("等待退货");
			btn.setBackgroundResource(R.drawable.btn_bgwhite_press);
			btn.setClickable(false);
		} else if (state.equals("7")) {
			btn.setText("待退款");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("8")) {
			btn.setText("退款成功");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("9")) {
			btn.setText("交易成功");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("10")) {
			btn.setText("取消订单");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("11")) {
			btn.setText("评论");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		} else if (state.equals("12")) {
			btn.setText("拒签");
			btn.setBackgroundResource(R.drawable.btn_bgorange_nor);
			btn.setClickable(true);
		}
	}
}
