/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * AddressManageActivity.java
 * 
 * 2013年8月27日-下午5:05:04
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.AddressManageListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.db.AssetsDatabaseManager;
import com.powerlong.electric.app.entity.AddressEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * AddressManageActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月27日-下午5:05:04
 * 
 * @version 1.0.0
 * 
 */
public class AddressManageActivity extends BaseActivity {
	ImageView ivReturn;
	ImageView ivAdd;
	PullToRefreshListView listView;
	List<AddressEntity> mList = new ArrayList<AddressEntity>();
	AddressManageListAdapter adapter;
	private int  positionAddress;
	private static SQLiteDatabase db;
	private boolean isThird = false;
	private int selectCommunity; //1只能第三方
	private int addressCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_manage);
		
		AssetsDatabaseManager.initManager(getApplication());  
		AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();  
		db = mg.getDatabase("acc.db3");  	
		listView = (PullToRefreshListView) findViewById(R.id.lv_address);
		listView.setMode(Mode.PULL_DOWN_TO_REFRESH);
		listView.setDividerDrawable(getResources().getDrawable(R.drawable.line_dotted));
		
		getData();
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				getData();
			}

		});
		final List<UserAddressEntity> usrAddressEntities= DataCache.UserAddressListCache;
		adapter = new AddressManageListAdapter(AddressManageActivity.this, getBaseContext(), usrAddressEntities);
		listView.setAdapter(adapter);
		
//		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent data = new Intent();			
				UserAddressEntity entity = (UserAddressEntity)arg0.getItemAtPosition(arg2);
//				Intent intent = new Intent(AddressManageActivity.this, AddressEditActivity.class);
				data.putExtra("name", entity.getConsignee());
				data.putExtra("phoneNum", entity.getMobile());
				data.putExtra("city", getNameById(Integer.parseInt(entity.getProvince()))
						+getNameById(Integer.parseInt(entity.getCity()))
						+getNameById(Integer.parseInt(entity.getArea())));
				data.putExtra("address", entity.getCommunityName() + "  " +entity.getAddress());
				if(entity.getCommunityId().equals("0")) {
					selectCommunity = 0;
				} else {
					if(entity.getCommunityName() != null && !entity.getCommunityName().equals("")) {
						selectCommunity = 0;
					} else {
						selectCommunity = 1;
					}
				}
				
				data.putExtra("selectCommunity", selectCommunity);
				data.putExtra("positionAddress", arg2);
				data.putExtra("addressId", entity.getAddressId());

//				startActivity(intent);
				setResult(Constants.ResultType.RESULT_FROM_ADDRESS, data);
				finish();
			}							
		});
		
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		ivAdd = (ImageView) findViewById(R.id.ivAdd);
		ivAdd.setImageDrawable(getResources().getDrawable(R.drawable.btn_add));
		ivAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addressCount = adapter.getCount();
				if(addressCount >= 10) {
					ToastUtil.showExceptionTips(getBaseContext(), "很抱歉，最多只能保存10个地址");
				} else {
					Intent intent = new Intent(
							AddressManageActivity.this,
							AddressAddActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("positionTime", 0);
					intent.putExtras(bundle);
					startActivityForResult(intent,
							Constants.ResultType.RESULT_FROM_ADD_ADDRESS);
				}
				
			}
		});
	}

	private void getData() {
		showLoadingDialog(null);
		DataUtil.getUserAddressListData(mServerConnectionHandler);
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("AddressManageActivity", "msg.what = "+msg.what);
			LogUtil.d("AddressManageActivity", "msg.arg1 = "+msg.arg1);
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			listView.onRefreshComplete();
			dismissLoadingDialog();
		}
	};
	
	private ServerConnectionHandler mDeleteHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("AddressManageActivity", "msg.what = "+msg.what);
			LogUtil.d("AddressManageActivity", "msg.arg1 = "+msg.arg1);
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
//				updateView();
				getData();
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
	 * updateView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	protected void updateView() {
		List<UserAddressEntity> entities = DataCache.UserAddressListCache;		
		UserAddressEntity entity;
		
		mList.clear();
		for (int i=0; i<entities.size(); i++) {
			entity = entities.get(i);
			mList.add(new AddressEntity(entity.getConsignee(), entity.getMobile(), 
					getNameById(Integer.parseInt(entity.getProvince()))
					+getNameById(Integer.parseInt(entity.getCity()))
					+getNameById(Integer.parseInt(entity.getArea())), 
					entity.getAddress(), entity.getAddressId(), entity.getCommunityName()));
		}
		
		adapter.notifyDataSetChanged();
	}
	
	public static String getNameById(int id) {
		String name = "";
		
		Cursor cursor = db.rawQuery("select * from UC_SYSTEM_REGION_DICT where id= " + id, null);
		for(cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()){
			name = cursor.getString(3);
		}
		cursor.close();
		return name;
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		switch(resultCode){
		case Constants.ResultType.RESULT_FROM_MANAGE_ADDRESS:
			String name = data.getExtras().getString("nameManage");
			String phoneNum = data.getExtras().getString("phoneNumManage");
			String address = data.getExtras().getString("addressManage");
			int position = data.getExtras().getInt("position");
			
			mList.get(position).setName(name);
			mList.get(position).setPhoneNum(phoneNum);
			mList.get(position).setAddress(address);
					
			adapter.notifyDataSetChanged();
			break;
			
		case Constants.ResultType.RESULT_FROM_ADD_ADDRESS:
//			String nameAdd = data.getExtras().getString("nameAdd");
//			String phoneNumAdd = data.getExtras().getString("phoneNumAdd");
//			String cityAdd = data.getExtras().getString("cityAdd");
//			String addressAdd = data.getExtras().getString("addressAdd");
//		
//			mList.add(new AddressEntity(nameAdd, phoneNumAdd, cityAdd, addressAdd));
			
//			adapter.notifyDataSetChanged();
			getData();
			break;
			
		case Constants.ResultType.RESULT_FROM_DELETE_ADDRESS:
			int positionDel = data.getExtras().getInt("positionDel");
			long addressId = data.getExtras().getLong("addressId");
			Log.v("addressId", "addressId=" + addressId);
			DataUtil.deleteUserAddressDataById(mDeleteHandler, addressId+"");
//			mList.remove(positionDel);
//			adapter.notifyDataSetChanged();
			
			break;
		}
	}
}
