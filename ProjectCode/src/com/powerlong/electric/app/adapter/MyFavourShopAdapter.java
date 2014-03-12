/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * MyFavourShopAdapter.java
 * 
 * 2013年9月16日-下午7:53:16
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleytest.cache.BitmapCache;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.MyFavourItemAdapter.ViewHolder;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.OrderListEntity;
import com.powerlong.electric.app.entity.ShopFavourListEntity;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * MyFavourShopAdapter
 * 
 * @author: Liang Wang
 * 2013年9月16日-下午7:53:16
 * 
 * @version 1.0.0
 * 
 */
public class MyFavourShopAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<ShopFavourListEntity> mValues;
	private ImageLoader imageLoader;
	private RequestQueue mRequestQueue;
	private ShopFavourListEntity entity;
	private Handler mHandler;
	public HashMap<Integer, Boolean> isSelected;
	private int mpostion;
	private int selectNum = 0;
	private LinearLayout llOut;
   
	public MyFavourShopAdapter(Context context, List<ShopFavourListEntity> values,Handler handler) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mValues = values;
		mRequestQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
		mHandler=handler;
		isSelected = new HashMap<Integer,Boolean>();
		for (int i=0; i<values.size(); i++) {
			isSelected.put(i, false);
		}
		
	}

	@Override
	public int getCount() {
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
		if (position == getCount() - 1 || mValues == null) {
			return 0;
		}
		return mValues.get(position).getShopId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_myfavour_shop, null);
			holder = new ViewHolder();
			holder.ck_commodity=(CheckBox) convertView.findViewById(R.id.ck__shop_commodity);
			holder.ck_commodity.setFocusable(false);
			holder.ck_commodity.setFocusableInTouchMode(false);
			holder.iv_image = (NetworkImageView) convertView
					.findViewById(R.id.iv_collection_shop_logo);
			holder.floor_title_shop=(TextView)convertView.findViewById(R.id.floor_title_shop);
//			holder.floor_evaluation=(RatingBar)convertView.findViewById(R.id.floor_evaluation);
			holder.tv_shop_list_evaluate = (TextView) convertView.findViewById(R.id.tv_shop_list_evaluate);
			holder.floor_products_categories=(TextView)convertView.findViewById(R.id.floor_products_categories);
			holder.floor_favour_num=(TextView)convertView.findViewById(R.id.floor_favour_num);

	       
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 entity = mValues.get(position);
		 mpostion=position;
		if(entity.getImage() != null&&entity.getImage().length()>0){
			holder.iv_image.setImageUrl(entity.getImage(), imageLoader);
		}else{
			holder.iv_image.setImageResource(R.drawable.pic_56);
		} 
		LayoutParams itemImageParams = (LayoutParams) holder.iv_image
				.getLayoutParams();
		itemImageParams.width = (int) (120 * Constants.displayWidth / 480);
		
		itemImageParams.height = (int) (120 * Constants.displayWidth / 480);
		holder.iv_image.setLayoutParams(itemImageParams);
		if(!StringUtil.isNullOrEmpty(entity.getShopName())){
			holder.floor_title_shop.setText(entity.getShopName());
		}else{
			holder.floor_title_shop.setText("");
		}
		if(!StringUtil.isNullOrEmpty(entity.getClassification())){
			holder.floor_products_categories.setText(entity.getClassification());
		}else{
			holder.floor_products_categories.setText("");
		}
		if (!StringUtil.isNullOrEmpty(entity.getEvaluation()+"")) {
			holder.tv_shop_list_evaluate.setText(entity.getEvaluation()+"");
		}
		if(!StringUtil.isNullOrEmpty(String.valueOf(entity.getFavourNum()))){
			holder.floor_favour_num.setText("("+String.valueOf(entity.getFavourNum())+")");
		}else{
			holder.floor_favour_num.setText("()");
		}
		
		holder.ck_commodity.setChecked(isSelected.get(position)); 
		holder.ck_commodity.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				Message message;
				 if(!isSelected.get(position)){
//					 checkedEntite.add(entity);
					 isSelected.put(position, true);
					 selectNum++;
				 }else{
					 isSelected.put(position,false);
					 selectNum--;
				 }
				 
				 if(selectNum>0){
					 message=mHandler.obtainMessage(0);
				 }else{
					 message= mHandler.obtainMessage(1);
				 }
				 mHandler.sendMessage(message);
				 notifyDataSetChanged();
			}
		});
		
		return convertView;
	}

	class ViewHolder {
		CheckBox ck_commodity;
		TextView floor_title_shop, floor_products_categories, floor_favour_num,tv_shop_list_evaluate;
//		RatingBar floor_evaluation;
		LinearLayout ll_iv_out;
		NetworkImageView iv_image;
		Button btn;
	}
}
