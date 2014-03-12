package com.powerlong.electric.app.ui;


import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.MyOrderAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.OrderListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MyOrderActivity extends BaseActivity implements OnClickListener{
	private LayoutInflater mInflater;
	private ListView lv_order;
	private LinearLayout llmessage;
	private ImageView btn_back,iv_delete;
    private EditText et_search;
    private List<OrderListEntity> entities;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//关闭键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_my_order);
		InitView();
		getData();
	}
	/**
	 * getData(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getData() {
		showLoadingDialog(null);
		DataUtil.getMyOrderList(getApplicationContext(), shandle,"",1);
	}
	private ServerConnectionHandler shandle=new ServerConnectionHandler(){
		public void handleMessage(Message msg) {	
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
        //取出数据更新界面
		private void updateView(String string) {
			 entities = DataCache.UserOrderListCache;
			
		
			if(entities.size()>0){
				MyOrderAdapter adapter=new MyOrderAdapter(getApplicationContext(), entities);
				lv_order.setAdapter(adapter);
				lv_order.setVisibility(View.VISIBLE);
				llmessage.setVisibility(View.GONE);
			}else {
				llmessage.setVisibility(View.VISIBLE);
				lv_order.setVisibility(View.GONE);
			};
			
		};
	};
	//初始化控件
    private void InitView(){
    	mInflater=getLayoutInflater();
    	
    	btn_back=(ImageView)findViewById(R.id.iv_back);
    	btn_back.setOnClickListener(this);
    	llmessage=(LinearLayout)findViewById(R.id.ll_message);
    	
    	lv_order=(ListView)findViewById(R.id.lv_order_list);
    	
    	lv_order.setOnItemClickListener(itemClickListener);
    	
    	et_search=(EditText) findViewById(R.id.et_search);
    	iv_delete=(ImageView) findViewById(R.id.iv_delete);
    	et_search.addTextChangedListener(watcher);
    	iv_delete.setOnClickListener(this);
    }
	
    //订单的点击事件
    OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			LogUtil.d("===postion", String.valueOf(arg2));
			OrderListEntity entity=entities.get(arg2);
			Intent intent=new Intent(MyOrderActivity.this,MyOrderDetailActivity.class);
			intent.putExtra("orderItemId", (int)entity.getId());
			LogUtil.d("===entityid", String.valueOf(entity.getId()));
			startActivity(intent);
		}
	};
	
	//搜索框的监听事件
	TextWatcher watcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
			if(!StringUtil.isNullOrEmpty(et_search.getText().toString())){
				iv_delete.setVisibility(View.VISIBLE);
				DataUtil.getMyOrderList(getApplicationContext(), shandle,et_search.getText().toString(),1);
			}else{
				iv_delete.setVisibility(View.GONE);
				DataUtil.getMyOrderList(getApplicationContext(), shandle,"",1);
			}
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_delete:
			et_search.setText("");
			break;
		default:
			break;
		}
	}
}
