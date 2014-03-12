/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * SendSelectActivity.java
 * 
 * 2013年8月30日-下午3:58:23
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.LogisticsEntity;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * SendSelectActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月30日-下午3:58:23
 * 
 * @version 1.0.0
 * 
 */
public class SendSelectActivity extends BaseActivity implements OnClickListener {
	private PlTableView tableView;
	private TextView tvSend;
	private ImageView ivSelect;
	private ScrollView sv;
	private ImageView ivBack;
	private int positionNow;
	private double sendPrice;
	private long logisticsId;
	ArrayList<LogisticsEntity> logisticsEntities;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settle_account_body);
		
		Bundle bundle = getIntent().getExtras();
		positionNow = bundle.getInt("positionSend");
		findView();
		tableView = new PlTableView(getBaseContext(), null);
		createList();
		tableView.commit();
	}

	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		ivBack = (ImageView) findViewById(R.id.ivReturn);
		ivBack.setOnClickListener(this);
		
		
	}


	/**
	 * createList(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void createList() {
	
		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
		logisticsEntities = DataCache.LogisticListCache;
	
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(int i=0; i<logisticsEntities.size(); i++) {
			LogisticsEntity entity = logisticsEntities.get(i);
			RelativeLayout child = (RelativeLayout) mInflater.inflate(
					R.layout.settle_account_send_item, null);
			tvSend = (TextView) child.findViewById(R.id.tv_select);
			tvSend.setText(entity.getName());
			ivSelect = (ImageView) child.findViewById(R.id.iv_select);
			if (positionNow == i) {
				tvSend.setTextColor(Color.RED);
				ivSelect.setVisibility(View.VISIBLE);
			} else {
				ivSelect.setVisibility(View.GONE);
			}
			ViewItem viewItem = new ViewItem(child);
			tableView.addViewItem(viewItem);
		}
		/*RelativeLayout child0 = (RelativeLayout) mInflater.inflate(
				R.layout.settle_account_send_item, null);
		tvSend = (TextView) child0.findViewById(R.id.tv_select);
		tvSend.setText(getResources().getString(R.string.send_type1));
		ivSelect = (ImageView) child0.findViewById(R.id.iv_select);
		if (positionNow == 0) {
			tvSend.setTextColor(Color.RED);
			ivSelect.setVisibility(View.VISIBLE);
		} else {
			ivSelect.setVisibility(View.GONE);
		}
		
		ViewItem viewItem0 = new ViewItem(child0);
		tableView.addViewItem(viewItem0);
		
		RelativeLayout child1 = (RelativeLayout) mInflater.inflate(
				R.layout.settle_account_send_item, null);
		tvSend = (TextView) child1.findViewById(R.id.tv_select);
		tvSend.setText(getResources().getString(R.string.send_type2));
		ivSelect = (ImageView) child1.findViewById(R.id.iv_select);
		if (positionNow == 1) {
			tvSend.setTextColor(Color.RED);
			ivSelect.setVisibility(View.VISIBLE);
		} else {
			ivSelect.setVisibility(View.GONE);
		}
		ViewItem viewItem1 = new ViewItem(child1);
		tableView.addViewItem(viewItem1);*/
		
		sv = (ScrollView) findViewById(R.id.sv_settle_account);
		sv.addView(tableView);
	
	}
	
	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			switch (index) {
			case 0:
//				String send = getResources().getString(R.string.send_type1);
				Intent data = new Intent();
				data.putExtra("send", logisticsEntities.get(0).getName());
				data.putExtra("positionSend", 0);
				data.putExtra("logisticsId", logisticsEntities.get(0).getLogisticId()+"");
				setResult(Constants.ResultType.RESULT_FROM_SEND, data);
				finish();
				break;
			case 1:
				String send1 = getResources().getString(R.string.send_type2);
				Intent data1 = new Intent();
				data1.putExtra("send", logisticsEntities.get(1).getName());
				data1.putExtra("positionSend", 1);
				data1.putExtra("logisticsId", logisticsEntities.get(1).getLogisticId()+"");
				setResult(Constants.ResultType.RESULT_FROM_SEND, data1);
				
				finish();
				break;
			case 2:
//				String send = getResources().getString(R.string.send_type1);
				Intent data2 = new Intent();
				data2.putExtra("send", logisticsEntities.get(2).getName());
				data2.putExtra("positionSend", 2);
				data2.putExtra("logisticsId", logisticsEntities.get(2).getLogisticId()+"");
				setResult(Constants.ResultType.RESULT_FROM_SEND, data2);
				finish();
				break;
			case 3:
//				String send1 = getResources().getString(R.string.send_type2);
				Intent data3 = new Intent();
				data3.putExtra("send", logisticsEntities.get(3).getName());
				data3.putExtra("positionSend", 3);
				data3.putExtra("logisticsId", logisticsEntities.get(3).getLogisticId()+"");
				setResult(Constants.ResultType.RESULT_FROM_SEND, data3);
				
				finish();
				break;
			}			
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ivReturn:
			finish();
		
		}
		
	}	
}
