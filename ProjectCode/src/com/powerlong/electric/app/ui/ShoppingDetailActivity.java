/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ShoppingDetailActivity.java
 * 
 * 2013年9月11日-下午3:22:22
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.ShopListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.NavigationFloorDetailsEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * ShoppingDetailActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月11日-下午3:22:22
 * 
 * @version 1.0.0
 * 
 */
public class ShoppingDetailActivity extends BaseActivity implements OnClickListener {
	private ListView listView;
	private String method;
	private String methodParams;
	private ImageView ivReturn;
	private ArrayAdapter<FloorDetailEntity> adapter;
	private List<String> itemId = new ArrayList<String>();

	private List<FloorDetailEntity> list = new ArrayList<FloorDetailEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floor_details);
		
		Intent intent = getIntent();
		method = intent.getStringExtra("method");
		methodParams = intent.getStringExtra("methodParams");
        
		findView();
		getData();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void findView() {
		listView = (ListView) findViewById(R.id.floor_list);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
	}
	
	private void getData() {
		showLoadingDialog(null);
		DataUtil.getNavShoppingData(getBaseContext(), mServerConnectionHandler);
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("BrandActivity", "msg.what = " + msg.what);
			LogUtil.d("BrandActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
		}

	};

	/**
	 * updateView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @param navId
	 * @exception
	 * @since 1.0.0
	 */
	protected void updateView(String navId) {
		ArrayList<NavigationFloorDetailsEntity> entities = DataCache.NavFloorDetailsCache;
		NavigationFloorDetailsEntity entity;
		List<FloorDetailEntity> list = new ArrayList<FloorDetailEntity>();

		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			
			list.add(new FloorDetailEntity(entity.getLogo(), entity.getShopName(), entity.getBrief(), 
					entity.getfScore(), entity.getClassification(), entity.getFavourNum()+""));
			itemId.add(entity.getSelfId()+"");
		}
		LogUtil.d("BrandActivity", "list szie = " + list.size());
		adapter = new ShopListAdapter(this, list, listView);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				entity.getItemList()
				Intent intent = new Intent(ShoppingDetailActivity.this, ItemDetailActivity.class);
				intent.putExtra("itemId", itemId.get(arg2));
				
				startActivity(intent);
			}
		});
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		}

	}
}
