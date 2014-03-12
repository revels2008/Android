/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * AddressAddActivity.java
 * 
 * 2013年8月28日-下午3:03:22
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.db.AssetsDatabaseManager;
import com.powerlong.electric.app.entity.AddressQueryEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * AddressAddActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月28日-下午3:03:22
 * 
 * @version 1.0.0
 * 
 */
public class AddressAddActivity extends BaseActivity implements OnClickListener {

	private ImageView ivReturn;
	private ImageView ivSave;
	private TextView title;
	private EditText etName;
	private EditText etPhoneNum;
	private EditText etQuery;
	private EditText etCity;
	private EditText etAddress;
	private Button btnQuery;
	private CheckBox cbAddress;
	private LinearLayout llProvince;
	private Spinner spProvince;
	private Spinner spCity;
	private Spinner spArea;
	private ArrayAdapter<String> proAdapter;
	private ArrayAdapter<String> cityAdapter;
	private ArrayAdapter<String> areaAdapter;
	private List<String> proList;
	private List<String> cityList;
	private List<String> areaList;
	private List<Integer> proIdList;
	private List<Integer> cityIdList;
	private List<Integer> areaIdList;
	private int proId;
	private int cityId;
	private int areaId;
	private int isOtherCommunity = 0;
	private int from = 0;
	
	private AlertDialog dlgAddress;
	private int selectPosition = 0;
	private static SQLiteDatabase db;
	private CharSequence[] listData = null;
	private List<String> nameList = new ArrayList<String>();
	private List<Integer> idList = new ArrayList<Integer>();
	private String strQuery = null;
	private int communityId = -1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		View viewToLoad = LayoutInflater.from(this.getParent()).inflate(R.layout.address_add, null);  
//		this.setContentView(viewToLoad);  
		setContentView(R.layout.address_add);
		
		from = getIntent().getExtras().getInt("from");
		
		findView();
		AssetsDatabaseManager.initManager(getApplication());  
		AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();  
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
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		ivSave = (ImageView) findViewById(R.id.ivFilter);
		ivSave.setOnClickListener(this);		
		title = (TextView) findViewById(R.id.txTitle);
		title.setText("新建收货地址");
		etName = (EditText) findViewById(R.id.et_name);
		etPhoneNum = (EditText) findViewById(R.id.et_phoneNum);
		etQuery = (EditText) findViewById(R.id.et_query);
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
				if(from == 2) {
					IntentUtil.start_activity(AddressAddActivity.this, ShoppingCartSettleAccountActivity.class);
				} 
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
	
	private String AddAddressParam() {
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("consignee", etName.getText().toString());
			obj.put("mobile", etPhoneNum.getText().toString());
			obj.put("areaCode", "");
			obj.put("tel", "");
			obj.put("ext", "");
			obj.put("province", proId);
			obj.put("city", cityId);
			obj.put("area", areaId);				
			obj.put("mall", Constants.mallId);
//			obj.put("zipCode", etZip.getText().toString());
			obj.put("userId", "");
			obj.put("addLabel", "");
			obj.put("type", "");
//			obj.put("isOtherCommunity", isOtherCommunity);
			obj.put("provincesInfo", "");
			if(communityId > -1) {
				obj.put("communityId", communityId);
				obj.put("isOtherCommunity", 0);
			} else {
				obj.put("communityId", "");
				obj.put("isOtherCommunity", 1);
//				obj.put("address", etAddress.getText().toString());
			}
			obj.put("address", etAddress.getText().toString());
			
			obj.put("isDefault", 1);
			obj.put("provincesInfo", etCity.getText().toString());
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
				if(entities.size() < 1) {
					ToastUtil.showExceptionTips(getBaseContext(), "没有匹配的地址");
					return;
				}
				for (int i=0; i<entities.size(); i++) {
					nameList.add(entities.get(i).getName());
					idList.add(entities.get(i).getId());
				}
				listData = nameList.toArray(new CharSequence[nameList.size()]);
				dlgAddress = new AlertDialog.Builder(AddressAddActivity.this)
				.setTitle("查询地址")
				.setSingleChoiceItems(listData, selectPosition, 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								selectPosition = which;
								dlgAddress.dismiss();
								etQuery.setText(nameList.get(which));
								strQuery = nameList.get(which);
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
			Intent data = new Intent();
//			data.putExtra("nameAdd", etName.getText().toString());
//			data.putExtra("phoneNumAdd", etPhoneNum.getText().toString());
//			data.putExtra("zipAdd", etZip.getText().toString());
//			data.putExtra("cityAdd", etCity.getText().toString());
//			data.putExtra("addressAdd", etAddress.getText().toString());
			setResult(Constants.ResultType.RESULT_FROM_ADD_ADDRESS, data);
			String param = AddAddressParam();
//			Log.v("AddressAddActivity  param=", param);
			
			if(etAddress.getText().toString() == null || "".equals(etAddress.getText().toString())) {
				ToastUtil.showExceptionTips(getBaseContext(), "请填写详细地址");
			} else{
				showLoadingDialog(null);
				HttpUtil.AddUserAddress(mServerConnectionHandler, param);
			}
			
			break;
		case R.id.btn_query:
			btnQuery.setClickable(false);
			String query = etQuery.getText().toString();
			if (query != null) {
				DataCache.AddressQueryCache.clear();
				HttpUtil.queryAddressInfoJson(getBaseContext(), mQueryHandler, query);
			} else {
				ToastUtil.showExceptionTips(getBaseContext(), "请输入查询内容");
			}
			break;
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
