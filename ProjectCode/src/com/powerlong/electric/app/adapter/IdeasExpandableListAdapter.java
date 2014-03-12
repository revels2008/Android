package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.entity.FilterEntity;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.widget.PlRangeSeekBar;
import com.powerlong.electric.app.widget.PlRangeSeekBar.OnRangeSeekBarChangeListener;

/**
 * @author IdeasAndroid 可展开（收缩）列表示例
 */
public class IdeasExpandableListAdapter extends BaseExpandableListAdapter {

	private Context mContext = null;
	// 测试数据，开发时可能来自数据库，网络....
	private String[] groups = { "价格区间:" };

	private List<String> groupList = null;
	private List<List<String>> itemList = null;

	private int CHILDVIEW_TYPE = 0;
	private final int VIEW_LIST_TYPE = 0;
	private final int VIEW_SEEKBAR_TYPE = 1;

	private Resources mResources = null;
	private LayoutInflater mLayoutInflater = null;
	private Handler mHandler = null;
	private boolean mOnlyItem = false;
	
	private OnSeekBarRangeListener onSeekBarRangeListener;
//	private TextView exTitle;

	public void setOnSeekBarRangeListener(
			OnSeekBarRangeListener onSeekBarRangeListener) {
		this.onSeekBarRangeListener = onSeekBarRangeListener;
	}

	private class ListChildViewHolder {
		TextView tvChildTitle;
		CheckBox cbChildChecked;
	}

	private class RangBarViewHolder {
		PlRangeSeekBar<Number> rangbar;
	}

	private class SeeckBarChildViewHolder {

	}

	public static class GroupViewHolder {
		public TextView tvGroupTitle;
		public TextView tvGroupValue;
		public ImageView tvGroupIndicator;
	}

	public IdeasExpandableListAdapter(Context context, Handler handler) {
		mLayoutInflater = LayoutInflater.from(context);
		this.mContext = context;
		mResources = mContext.getResources();
		groupList = new ArrayList<String>();
		itemList = new ArrayList<List<String>>();
		initData();
		mHandler = handler;
	}
	
	public IdeasExpandableListAdapter(Context context, Handler handler, boolean onlyItem) {
		mLayoutInflater = LayoutInflater.from(context);
		this.mContext = context;
		mResources = mContext.getResources();
		groupList = new ArrayList<String>();
		itemList = new ArrayList<List<String>>();
		LogUtil.d("IdeasExpandableListAdapter", "onlyItem = "+onlyItem);
		mOnlyItem = onlyItem;
		initData();
		mHandler = handler;
	}

	/**
	 * 初始化数据，将相关数据放到List中，方便处理
	 */
	private void initData() {
		LogUtil.d("IdeasExpandableListAdapter", "mOnlyItem = "+mOnlyItem);
		if(!mOnlyItem){
			for (int i = 0; i < groups.length; i++) {
				groupList.add(groups[i]);
			}
			String[] shopService = mResources
					.getStringArray(R.array.fileter_child_shop_service);
	
			List<String> item = new ArrayList<String>();
			List<String> item1 = new ArrayList<String>();
			for (int i = 0; i < shopService.length; i++) {
				item.add(shopService[i]);
				item1.add(shopService[i]);
			}
	
			itemList.add(item);
			itemList.add(item1);
		}else{
			groupList.add("选择优惠:");
			String[] barginList = mResources
			.getStringArray(R.array.fileter_child_bargin);

			List<String> item = new ArrayList<String>();
			List<String> item1 = new ArrayList<String>();
			for (int i = 0; i < barginList.length; i++) {
				item.add(barginList[i]);
				item1.add(barginList[i]);
			}
			itemList.add(item);
			itemList.add(item1);
		}
	}

	public void removeGroup(int groupId){
		if(groupList.get(groupId)!= null)
			groupList.remove(groupId);
		switch(groupId){
		case 0:
			groupList.add(groups[0]);
			break;
		case 1:
//			groupList.add(groups[1]);	
			break;
		}
		notifyDataSetChanged();
	}
	
	public boolean areAllItemsEnabled() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseExpandableListAdapter#getChildType(int, int)
	 */
	@Override
	public int getChildType(int groupPosition, int childPosition) {
		if(!mOnlyItem){
			if (groupPosition == 1) {
				CHILDVIEW_TYPE = VIEW_LIST_TYPE;
				return VIEW_LIST_TYPE;
			} else if (groupPosition == 0) {
				CHILDVIEW_TYPE = VIEW_SEEKBAR_TYPE;
				return VIEW_SEEKBAR_TYPE;
			} else {
				return VIEW_LIST_TYPE;
			}
		}else{
			return VIEW_LIST_TYPE;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseExpandableListAdapter#getChildTypeCount()
	 */
	@Override
	public int getChildTypeCount() {
		if (CHILDVIEW_TYPE == VIEW_LIST_TYPE) {
			if(!mOnlyItem)
				return mResources.getStringArray(R.array.fileter_child_shop_service).length;
			else{
				return mResources.getStringArray(R.array.fileter_child_bargin).length;
			}
		} else {
			return 1;
		}
	}

	/*
	 * 设置子节点对象，在事件处理时返回的对象，可存放一些数据
	 */
	public Object getChild(int groupPosition, int childPosition) {
		return itemList.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/*
	 * 字节点视图，这里我们显示一个文本对象
	 */
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ListChildViewHolder holder = null;
		RangBarViewHolder holder1 = null;
		int viewType = getChildType(groupPosition, childPosition);

		if (convertView == null) {
			switch (viewType) {
			case VIEW_LIST_TYPE:
				holder = new ListChildViewHolder();
				convertView = mLayoutInflater.inflate(
						R.layout.filter_shop_service_item_layout, null);
				holder.tvChildTitle = (TextView) convertView
						.findViewById(R.id.tvChildTitle);
				holder.cbChildChecked = (CheckBox) convertView
						.findViewById(R.id.chBoxChild);
				convertView.setTag(holder);
				break;
			case VIEW_SEEKBAR_TYPE:
				holder1 = new RangBarViewHolder();
				convertView = mLayoutInflater.inflate(
						R.layout.filter_price_rangebar_item_layout, null);

				convertView.setMinimumHeight(200);

				holder1.rangbar = (PlRangeSeekBar<Number>) convertView
						.findViewById(R.id.priceRangebar);
				convertView.setTag(holder1);
				break;
			}
		} else {
			switch (viewType) {
			case VIEW_LIST_TYPE:
				holder = (ListChildViewHolder) convertView.getTag();

				break;
			case VIEW_SEEKBAR_TYPE:
				holder1 = (RangBarViewHolder) convertView.getTag();
				break;
			}
		}

		if (viewType == VIEW_LIST_TYPE) {

			holder.tvChildTitle.setText(itemList.get(groupPosition).get(
					childPosition));

			FilterEntity entity = null;
			if(!mOnlyItem){
				if (groupPosition == 1) {
					entity = DataCache.ExShopServiceChecked.get(childPosition);
				}
			}else{
				entity = DataCache.ExBarginChecked.get(childPosition);
			}

			LogUtil.e("getChildView", "groupPosition=" + groupPosition);
			if (entity != null) {
				LogUtil.e("getChildView",
						"entity.getGroupId()=" + entity.getGroupId());
				if (entity.getGroupId() == groupPosition) {
					LogUtil.e("getChildView",
							"entity.isChecked()=" + entity.isChecked());
					holder.cbChildChecked.setChecked(entity.isChecked());
				} else {
					holder.cbChildChecked.setChecked(false);
				}
			} else {
				holder.cbChildChecked.setChecked(false);
			}

			holder.cbChildChecked.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					CheckBox cb = (CheckBox) view;

					if (cb.isChecked()) {
						FilterEntity entity = new FilterEntity(groupPosition,
								childPosition, itemList.get(groupPosition).get(
										childPosition), cb.isChecked());
						if(!mOnlyItem)
							DataCache.ExShopServiceChecked.put(childPosition,
									entity);
						else{
							DataCache.ExBarginChecked.put(childPosition,
									entity);
						}
					} else {
						FilterEntity entity = null;
						if(!mOnlyItem)
							entity =DataCache.ExShopServiceChecked
								.get(childPosition);
						else
							entity = DataCache.ExBarginChecked
							.get(childPosition);
						
						if (entity != null
								&& entity.getGroupId() == groupPosition) {
							if(!mOnlyItem)
								DataCache.ExShopServiceChecked
										.remove(childPosition);
							else
								DataCache.ExBarginChecked
								.remove(childPosition);
						}
					}
					mHandler.obtainMessage(1, groupPosition, childPosition)
							.sendToTarget();
				}
			});

			/*
			 * holder.cbChildChecked .setOnCheckedChangeListener(new
			 * OnCheckedChangeListener() {
			 * 
			 * @Override public void onCheckedChanged( CompoundButton
			 * compoundbutton, boolean flag) { LogUtil.e("cbChildChecked",
			 * "onCheckedChanged + flag =" + flag + ",childPosition=" +
			 * childPosition); LogUtil.e("cbChildChecked",
			 * "onCheckedChanged + flag =" + flag + ",groupPosition=" +
			 * groupPosition);
			 * 
			 * if (flag) { FilterEntity entity = new FilterEntity(
			 * groupPosition, childPosition, itemList .get(groupPosition).get(
			 * childPosition), flag); DataCache.ExShopServiceChecked.put(
			 * childPosition, entity);
			 * 
			 * } else { FilterEntity entity = DataCache.ExShopServiceChecked
			 * .get(childPosition); if (entity != null && entity.getGroupId() ==
			 * groupPosition) { DataCache.ExShopServiceChecked
			 * .remove(childPosition); } }
			 * 
			 * // mHandler.obtainMessage(1, groupPosition, // childPosition, //
			 * DataCache.ExShopServiceChecked).sendToTarget(); } });
			 */
		} else {
			int min = 0;
			int max = 6000;
			FilterEntity childEntity = DataCache.ExPriceChecked
			.get(groupPosition);
			if(childEntity!=null){
				min = childEntity.getMinium().intValue();
				max = childEntity.getMaxium().intValue();
			}
			holder1.rangbar.setSelectedMinValue(min);
			holder1.rangbar.setSelectedMaxValue(max);
			holder1.rangbar
					.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Number>() {

						@Override
						public void onRangeSeekBarValuesChanged(
								PlRangeSeekBar<?> bar, Number minValue,
								Number maxValue) {
							// TODO Auto-generated method stub
							LogUtil.e("getChildView", "groupPosition=" + groupPosition);
							LogUtil.e("rangbar", "minValue =" + minValue
									+ ",maxValue=" + maxValue);
							FilterEntity entity = new FilterEntity(minValue,
									maxValue);
							DataCache.ExPriceChecked.put(groupPosition, entity);
							mHandler.obtainMessage(2, groupPosition,
									childPosition).sendToTarget();
							if(onSeekBarRangeListener!=null){
								onSeekBarRangeListener.onSeekBarRanged(minValue, maxValue);
							}
						}
					});
		}
		return convertView;
	}
	
	public interface OnSeekBarRangeListener{
		public void onSeekBarRanged(Number min,Number max);
	}

	/*
	 * 返回当前分组的字节点个数
	 */
	public int getChildrenCount(int groupPosition) {
		if(!mOnlyItem){
			if (groupPosition == 0)
				return 1;
		}

		return itemList.get(groupPosition).size();
	}

	/*
	 * 返回分组对象，用于一些数据传递，在事件处理时可直接取得和分组相关的数据
	 */
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	/*
	 * 分组的个数
	 */
	public int getGroupCount() {
		return groupList.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/*
	 * 分组视图，这里也是一个文本视图
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder holder = null;
		if (convertView == null) {
			holder = new GroupViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.filter_expandable_list_header_layout, null);
			holder.tvGroupTitle = (TextView) convertView
					.findViewById(R.id.exTitle);
			holder.tvGroupValue = (TextView) convertView
					.findViewById(R.id.exValue);
			holder.tvGroupIndicator = (ImageView) convertView
					.findViewById(R.id.exIndicator);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		LogUtil.e("getGroupView", "groupPosition == " + groupPosition);
		if (groupPosition == 1) {
			LogUtil.e("getGroupView", "step0");
			holder.tvGroupTitle.setText(groupList.get(1));
			int childCount = getChildrenCount(groupPosition);
			String content = null;
			for (int i = 0; i < childCount; i++) {
				FilterEntity childEntity = DataCache.ExShopServiceChecked
				.get(Integer.valueOf(i));
				if (childEntity != null && childEntity.getChildId() == i) {
					if (StringUtil.isEmpty(content)) {
						content = childEntity.getValue();
					} else {
						content += "," + childEntity.getValue();
					}
				}
			}
			holder.tvGroupValue.setText(content==null?mContext.getResources().getString(R.string.str_shop_service_value_default):content);
		} else {
			LogUtil.e("getGroupView", "step1");
			holder.tvGroupTitle.setText(groupList.get(0));
			if(!mOnlyItem){
				FilterEntity childEntity = DataCache.ExPriceChecked
				.get(groupPosition);
				LogUtil.e("getGroupView", "childEntity = "+childEntity);
				String content = null;
				if(childEntity!=null){
					content = ""+childEntity.getMinium()+"-"+""+(childEntity.getMaxium().intValue()<6000?childEntity.getMaxium():"∞");
				}
				holder.tvGroupValue.setText(content==null?mContext.getResources().getString(R.string.str_price_value_default):content);
			}else{
				int childCount = getChildrenCount(groupPosition);
				String content = null;
				for (int i = 0; i < childCount; i++) {
					FilterEntity childEntity = DataCache.ExBarginChecked
					.get(Integer.valueOf(i));
					if (childEntity != null && childEntity.getChildId() == i) {
						if (StringUtil.isEmpty(content)) {
							content = childEntity.getValue();
						} else {
							content += "," + childEntity.getValue();
						}
					}
				}
				holder.tvGroupValue.setText(content==null?mContext.getResources().getString(R.string.str_shop_service_value_default):content);
			}
		}
		return convertView;
	}

	/*
	 * 判断分组是否为空，本示例中数据是固定的，所以不会为空，我们返回false 如果数据来自数据库，网络时，可以把判断逻辑写到这个方法中，如果为空
	 * 时返回true
	 */
	public boolean isEmpty() {
		return false;
	}

	/*
	 * 收缩列表时要处理的东西都放这儿
	 */
	public void onGroupCollapsed(int groupPosition) {

	}

	/*
	 * 展开列表时要处理的东西都放这儿
	 */
	public void onGroupExpanded(int groupPosition) {

	}

	/*
	 * Indicates whether the child and group IDs are stable across changes to
	 * the underlying data.
	 */
	public boolean hasStableIds() {
		return false;
	}

	/*
	 * Whether the child at the specified position is selectable.
	 */
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
