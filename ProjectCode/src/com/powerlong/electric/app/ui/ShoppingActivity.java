/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ShoppingActivity.java
 * 
 * 2013年7月30日-下午4:04:51
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
 * ShoppingActivity
 * 
 * @author: YangCheng Miao
 * 2013年7月30日-下午4:04:51
 * 
 * @version 1.0.0
 * 
 */
public class ShoppingActivity extends BaseActivity implements OnClickListener{
	private ListView listView;
	private ProgressBar mpProgressBar = null;
	private String method;
	private String methodParams;
	private ImageView ivReturn;
	private long shopId;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
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
		
		private void findView() {
			mpProgressBar = (ProgressBar) findViewById(R.id.progressbar);
			listView = (ListView) findViewById(R.id.floor_list);
//			txTitle = (TextView) findViewById(R.id.txTitle);
			ivReturn = (ImageView) findViewById(R.id.ivReturn);
			ivReturn.setOnClickListener(this);
//			ivFilter = (ImageView) findViewById(R.id.ivFilter);
//			ivFilter.setOnClickListener(this);
			
		}
		
		private void getData() {
			showLoadingDialog(null);
			DataUtil.getNavShoppingItemData(getBaseContext(), method, methodParams, mServerConnectionHandler);
		}

		private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

			@Override
			public void handleMessage(Message msg) {

				LogUtil.d("FoodDetailActivity", "msg.what = " + msg.what);
				LogUtil.d("FoodDetailActivity", "msg.arg1 = " + msg.arg1);
				dismissLoadingDialog();
				switch (msg.what) {
				case Constants.HttpStatus.SUCCESS:
					updateView(Integer.toString(msg.arg1));
					break;
				case Constants.HttpStatus.NORMAL_EXCEPTION:
				case Constants.HttpStatus.CONNECTION_EXCEPTION:
					String fail = (String)msg.obj;
					Toast.makeText(ShoppingActivity.this, fail,
							Toast.LENGTH_SHORT).show();
					showCustomToast(fail);
					break;
				}
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
			final ArrayList<NavigationFloorDetailsEntity> entities = DataCache.NavFloorDetailsCache;
			NavigationFloorDetailsEntity entity;
			List<FloorDetailEntity> list = new ArrayList<FloorDetailEntity>();
			for (int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				list.add(new FloorDetailEntity(entity.getLogo(), entity.getShopName(), entity.getBrief(), 
						entity.getfScore(), entity.getClassification(), entity.getFavourNum()+""));
			}
			LogUtil.d("BrandActivity", "list szie = " + list.size());
			listView.setAdapter(new ShopListAdapter(this, list, listView));
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					shopId = entities.get(arg2).getSelfId();
					Intent intent = new Intent();
					intent.putExtra("shopId", shopId);
					intent.putExtra("from", "FoodDetailActivity");		
					intent.putExtra("orderType", "0");
					intent.setClass(ShoppingActivity.this, ShopDetailActivityNew.class);
					startActivity(intent);
				}
			});
			
		}
		
		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ivReturn:
				finish();
				break;

			default:
				break;
			}
		}

}
