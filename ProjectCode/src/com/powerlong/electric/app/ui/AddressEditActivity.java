/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * AddressEditActivity.java
 * 
 * 2013年8月27日-下午7:24:04
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.db.AssetsDatabaseManager;
import com.powerlong.electric.app.entity.AddressQueryEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * AddressEditActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月27日-下午7:24:04
 * 
 * @version 1.0.0
 * 
 */
public class AddressEditActivity extends BaseActivity implements OnClickListener {
	private EditText etQuery;
	private EditText etCity;
	private Button btnQuery;
	
	private AlertDialog dlgAddress;
	private int selectPosition = 0;
	private CharSequence[] listData = null;
	private List<String> nameList = new ArrayList<String>();
	private ImageView ivSave;
	private TextView title;
	private EditText etName;
	private EditText etPhoneNum;
	private TextView tvCity;
	private EditText etAddress;
	private PlTableView tableView;
	private EditText tvAddress;
	private ImageView iv;
	private ImageView ivReturn;
	private LinearLayout llDelete;
	private String name;
	private String phoneNum;
	private String city;
	private String communityName;
	private String address;
	private int position;
	private long addressId;
	private CheckBox cbAddress;
	private LinearLayout llProvince;
	private Spinner spProvince;
	private Spinner spCity;
	private Spinner spArea;
	private ArrayAdapter<String> proAdapter;
	private ArrayAdapter<String> cityAdapter;
	private ArrayAdapter<String> areaAdapter;
	private List<String> idList = new ArrayList<String>();
	private List<String> proList;
	private List<String> cityList;
	private List<String> areaList;
	private List<Integer> proIdList;
	private List<Integer> cityIdList;
	private List<Integer> areaIdList;
	private int proId = -1;
	private int cityId = -1;
	private int areaId = -1;
	private int isOtherCommunity = 0;
	private SQLiteDatabase db;
	private String communityId;
	Bundle bundle = new Bundle();
	AssetsDatabaseManager mg;
	private Button btnDefault;
	private boolean isProvince = false; //是否是省市区
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_edit);
		
		bundle = getIntent().getExtras();
		name = bundle.getString("nameManage");
		phoneNum = bundle.getString("phoneNumManage");
		city = bundle.getString("cityManage");
		address = bundle.getString("addressManage");
		communityName = bundle.getString("communityName");
		position = bundle.getInt("positonManage");
		addressId = bundle.getLong("addressId");
		communityId = bundle.getString("communityId");
		if(!StringUtil.isNullOrEmpty(bundle.getString("provinceId"))) {
			proId = Integer.parseInt(bundle.getString("provinceId"));
			isProvince = true;
			
		}
		
//		if(ifP)
		
		findView();		
		AssetsDatabaseManager.initManager(getApplication());  
		mg = AssetsDatabaseManager.getManager();  
		db = mg.getDatabase("acc.db3");  	
		loadSpinnerData();
	}
	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		tableView = new PlTableView(getBaseContext(), null);
		
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		llDelete = (LinearLayout) findViewById(R.id.ll_delete_address);
		llDelete.setOnClickListener(this);
		btnDefault = (Button) findViewById(R.id.btn_defalt);
		btnDefault.setOnClickListener(this);
		ivSave = (ImageView) findViewById(R.id.ivFilter);
		ivSave.setOnClickListener(this);		
		title = (TextView) findViewById(R.id.txTitle);
		etName = (EditText) findViewById(R.id.et_name);
		etName.setText(name);
		etPhoneNum = (EditText) findViewById(R.id.et_phoneNum);
		etPhoneNum.setText(phoneNum);
//		tvCity = (TextView) findViewById(R.id.et_city);
//		tvCity.setText(city);
//		tvCity.setOnClickListener(this);
		etAddress = (EditText) findViewById(R.id.et_address);
		etAddress.setText(address);
		etQuery = (EditText) findViewById(R.id.et_query);
		etQuery.setText(communityName);
		etCity = (EditText) findViewById(R.id.et_city);
		etAddress = (EditText) findViewById(R.id.et_address);
		btnQuery = (Button) findViewById(R.id.btn_query);
		btnQuery.setOnClickListener(this);
		cbAddress = (CheckBox) findViewById(R.id.cb_add_address);
		llProvince = (LinearLayout) findViewById(R.id.ll_province);
		cbAddress.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					llProvince.setVisibility(View.VISIBLE);
					isOtherCommunity = 1;
				} else {
					llProvince.setVisibility(View.GONE);
					isOtherCommunity = 0;
				}
			}
		});
		spProvince = (Spinner) findViewById(R.id.sp_province);
		
	}

	private void loadSpinnerData()
	{
		spProvince = (Spinner) findViewById(R.id.sp_province);
		spProvince.setPrompt("请选择省份");
		proAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getProvinceData());
		proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spProvince.setAdapter(proAdapter);
    	
    	if(isProvince) {
			spProvince.setPrompt(proId + "");
			spCity = (Spinner) findViewById(R.id.sp_city);
			spArea = (Spinner) findViewById(R.id.sp_area);
			spCity.setPrompt(cityId+ "");
			spArea.setPrompt(areaId + "");
		}
    	spProvince.setOnItemSelectedListener(new OnItemSelectedListener() 
    	{	
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{					
				spCity = (Spinner) findViewById(R.id.sp_city);
				proId = proIdList.get(spProvince.getSelectedItemPosition());
				if(true)
				{	
					spArea = (Spinner) findViewById(R.id.sp_area);
					spCity = (Spinner) findViewById(R.id.sp_city);
					spCity.setPrompt("请选择城市");
					cityAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getCityData(proId));
					cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spCity.setAdapter(cityAdapter);
					spCity.setOnItemSelectedListener(new OnItemSelectedListener() 
					{
						
						
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							cityId = cityIdList.get(spCity.getSelectedItemPosition());
							if(true)
							{
								spArea = (Spinner) findViewById(R.id.sp_area);
								spArea.setPrompt("请选择县区");
								
								areaAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getAreaData(cityId));
								areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								spArea.setAdapter(areaAdapter);
								spArea.setOnItemSelectedListener(new OnItemSelectedListener() 
								{

									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										areaId = areaIdList.get(spArea.getSelectedItemPosition());
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {
										
										
									}
									
								});
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							
						}

					});							
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
	}
	
	private List<String> getProvinceData() {
		proList = new ArrayList<String>();
		proIdList = new ArrayList<Integer>();
		Cursor cursor = db.rawQuery("select * from UC_SYSTEM_REGION_DICT where LEVEL = 1", null);
		for(cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()){
			proList.add(cursor.getString(3));
			proIdList.add(cursor.getInt(0));
		}
		cursor.close();
		return proList;
	}
	
	private List<String> getCityData(int parentId) {
		cityList = new ArrayList<String>();
		cityIdList = new ArrayList<Integer>();
		Cursor cursor = db.rawQuery("select * from UC_SYSTEM_REGION_DICT where LEVEL = 2 and PARENT_ID = " + parentId, null);
		for(cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()){
			cityList.add(cursor.getString(3));
			cityIdList.add(cursor.getInt(0));
		}
		cursor.close();
		return cityList;
	}
	
	private List<String> getAreaData(int parentId) {
		areaList = new ArrayList<String>();
		areaIdList = new ArrayList<Integer>();
		Cursor cursor = db.rawQuery("select * from UC_SYSTEM_REGION_DICT where LEVEL = 3 and PARENT_ID = " + parentId, null);
		for(cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()){
			areaList.add(cursor.getString(3));
			areaIdList.add(cursor.getInt(0));
		}
		cursor.close();
		return areaList;
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("AddressAddActivity", "msg.what = " + msg.what);
			LogUtil.d("AddressAddActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
//			dismissLoadingDialog();
		}

	};
	
	
	
	private String EditAddressParam() {
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("consignee", etName.getText().toString());
			obj.put("moblie", etPhoneNum.getText().toString());
			obj.put("areaCode", "");
			obj.put("tel", "");
			obj.put("ext", "");
			obj.put("province", proId);
			obj.put("city", cityId);
			obj.put("area", areaId);
			obj.put("address", etAddress.getText().toString());
//			obj.put("zipCode", etZip.getText().toString());
			obj.put("userId", "");
			obj.put("id", addressId);
			obj.put("addLabel", "");
			obj.put("type", "");
			obj.put("isOtherCommunity", isOtherCommunity);
			obj.put("provincesInfo", "");
			obj.put("communityId", communityId);
			obj.put("isDefault", 1);
			obj.put("provincesInfo", "");
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}					
	
	}
	private ServerConnectionHandler mQueryHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("AddressAddActivity", "msg.what = " + msg.what);
			LogUtil.d("AddressAddActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				ArrayList<AddressQueryEntity> entities = DataCache.AddressQueryCache;
				for (int i=0; i<entities.size(); i++) {
					nameList.add(entities.get(i).getName());
					idList.add(entities.get(i).getId()+"");
					
					
				}
				listData = nameList.toArray(new CharSequence[nameList.size()]);
				dlgAddress = new AlertDialog.Builder(AddressEditActivity.this)
				.setTitle("查询地址")
				.setSingleChoiceItems(listData, selectPosition, 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								selectPosition = which;
								dlgAddress.dismiss();
								etQuery.setText(nameList.get(which));
								communityId = idList.get(which);
								
							}
						}).create();	
				
				dlgAddress.show();
				
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
			btnQuery.setClickable(true);
		}

	};
	
	private ServerConnectionHandler mDefaultHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("AddressAddActivity", "msg.what = " + msg.what);
			LogUtil.d("AddressAddActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				finish();
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
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ivFilter:
//			IntentUtil.start_activity(AddressEditActivity.this, AddressAddActivity.class);
			String name = etName.getText().toString();
			String phoneNum = etPhoneNum.getText().toString();
			String address = etAddress.getText().toString();

			Intent data = new Intent();
			data.putExtra("nameManage", name);
			data.putExtra("phoneNumManage", phoneNum);
			data.putExtra("addressManage", address);
			data.putExtra("position", position);
			setResult(Constants.ResultType.RESULT_FROM_MANAGE_ADDRESS, data);
			String param = EditAddressParam();
//			Log.v("AddressAddActivity  param=", param);
// 			showLoadingDialog(null);
			HttpUtil.AddUserAddress(mServerConnectionHandler, param);
			finish();
			break;
		case R.id.ll_delete_address:
			
			Intent dataDelete = new Intent();
			dataDelete.putExtra("positionDel", position);
			dataDelete.putExtra("addressId", addressId);
			setResult(Constants.ResultType.RESULT_FROM_DELETE_ADDRESS, dataDelete);
			finish();
			break;
		case R.id.et_query:
			btnQuery.setClickable(false);
			String query = etQuery.getText().toString();
			if (query != null) {
				DataCache.AddressQueryCache.clear();
				HttpUtil.queryAddressInfoJson(getBaseContext(), mQueryHandler, query);
			} else {
				ToastUtil.showExceptionTips(getBaseContext(), "请输入查询内容");
			}
			
		case R.id.btn_defalt:
			DataUtil.setUserDefaultAddress(mDefaultHandler, addressId+"");
		}
		
			
	}
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
//		db.close();
	}
	
	
}
