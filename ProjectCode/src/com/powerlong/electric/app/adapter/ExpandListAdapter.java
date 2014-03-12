/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * ExpandListAdapter.java
 * 
 * 2013-8-8-下午08:24:47
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.entity.Categeories;

/**
 * 
 * ExpandListAdapter:可展开列表视图的适配器
 * 
 * @author: Liang Wang 2013-8-8-下午08:24:47
 * 
 * @version 1.0.0
 * 
 */
public class ExpandListAdapter<T> extends BaseAdapter {
	private final List<T> mList = new LinkedList<T>();
	private LayoutInflater mLayoutInflater = null;
	private Context mContext = null;

	static class ViewHolder {
		ImageView iconIv;
		TextView title;
		TextView summary;
		LinearLayout detailPanel;
		ImageView iconIExpand;
	}

	public ExpandListAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public List<T> getList() {
		return mList;
	}

	public void appendToList(List<T> list, boolean refresh) {
		if (list == null) {
			return;
		}
		if (!refresh) {
			mList.clear();
			mList.addAll(list);
		} else {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void appendToTopList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(0, list);
		notifyDataSetChanged();
	}

	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position > mList.size() - 1) {
			return null;
		}
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.expaned_list_items,
					null);
			holder.iconIv = (ImageView) convertView
					.findViewById(R.id.expanded_item_icon);
			holder.title = (TextView) convertView
					.findViewById(R.id.expanded_item_title);
			holder.summary = (TextView) convertView
					.findViewById(R.id.expanded_item_summary);
			holder.detailPanel = (LinearLayout) convertView
					.findViewById(R.id.details_panel);
			holder.iconIExpand = (ImageView) convertView
					.findViewById(R.id.expanded_item_expand_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (getList().get(position) instanceof Categeories) {
			Categeories category = (Categeories) getList().get(position);
			holder.iconIv.setImageResource(category.iconId);
			holder.title.setText(category.title);
			holder.summary.setText(category.summary);

			if (category.expand) {
				holder.detailPanel.removeAllViews();
				holder.detailPanel.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, dip2px(mContext, 0.5f));
				for(String item:category.details){
					TextView tv = (TextView)mLayoutInflater.inflate(R.layout.tip_detail, null);
					tv.setText(item);
					holder.detailPanel.addView(tv);
				}
			} else {
				holder.detailPanel.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	/**
	 * dip2px:convert dip to px
	 * 
	 * @param context
	 * @param dipValue
	 * @return int
	 * @exception
	 * @since 1.0.0
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
