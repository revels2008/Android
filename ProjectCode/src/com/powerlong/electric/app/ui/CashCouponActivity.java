package com.powerlong.electric.app.ui;

import java.util.ArrayList;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.BarginListEntity;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.base.BaseSlidingFragmentActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.widget.ElasticScrollView;
import com.powerlong.electric.app.widget.ElasticScrollView.OnRefreshListener;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CashCouponActivity extends BaseActivity  implements OnClickListener,TextWatcher{
	private ElasticScrollView eScrollView;
	private LinearLayout linearItem;  
	private SlidingMenu sm = null;
	private LinearLayout linearRight;
	private LinearLayout linearLeft;
	private ImageView ivback;
	private Button btn_new;
    private Button btn_mycoupon;
    private EditText et_search;
    private LinearLayout.LayoutParams layoutParam;
    private ImageButton imgBack;
    private ImageView img_right;
    private LayoutInflater minflater;
    private LinearLayout llmessage;
    private RelativeLayout rl_food,rl_fun,rl_shopping;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		getWindow().setSoftInputMode(
		WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_coupon);
		intview();
		eScrollView = (ElasticScrollView) findViewById(R.id.my_scroll_view);
		//ScrollView暂时注释
//		eScrollView.setonRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
////				Thread thread = new Thread(new Runnable() {
////
////					@Override
////					public void run() {
////						 try {
////						 Thread.sleep(2000);
////						 } catch (InterruptedException e) {
////						 e.printStackTrace();
////						 }
////						Message message = handler.obtainMessage(0, "new Text");
////						handler.sendMessage(message);
////					}
////				});
////				thread.start();
////				DataUtil.getBargainListDta(getApplicationContext(), shandle);
//			}
//		});
		getData("", 0);
	}
	/**
	 * getData(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getData(String keyword, int type) {
		showLoadingDialog(null);
		DataUtil.getBargainListDta(getApplicationContext(), shandle,1, keyword, type);
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
		//取出数据填充界面
		private void updateView(String string) {
			ArrayList<BarginListEntity> entities = DataCache.MyBargainListCache;
			LogUtil.d("===", String.valueOf(entities.size()));
			if(entities.size()>0){
				llmessage.setVisibility(View.GONE);
				linearLeft.removeAllViews();
				linearRight.removeAllViews();
				for(int i=0;i<entities.size();i++){
				   if(i%4==0){
					    linearItem = (LinearLayout)minflater.inflate(
								R.layout.item_cash_coupon, null);
						linearItem.setBackgroundResource(R.drawable.coupon_bg1);
						linearItem.setLayoutParams(layoutParam);
						setLinearParm(linearItem,entities.get(i));
						linearLeft.addView(linearItem);
				   }else if(i%4==1){
					   	linearItem = (LinearLayout) minflater.inflate(
								R.layout.item_cash_coupon, null);
						linearItem.setBackgroundResource(R.drawable.coupon_bg2);
						linearItem.setLayoutParams(layoutParam);
						setLinearParm(linearItem,entities.get(i));
						linearRight.addView(linearItem);
				   }else if(i%4==2){
					   linearItem = (LinearLayout) minflater.inflate(
								R.layout.item_cash_coupon, null);
						linearItem.setBackgroundResource(R.drawable.coupon_bg3);
						linearItem.setLayoutParams(layoutParam);
						setLinearParm(linearItem,entities.get(i));
						linearLeft.addView(linearItem);
					   
				   }else if(i%4==3){
					   linearItem = (LinearLayout)minflater.inflate(
								R.layout.item_cash_coupon, null);
						linearItem.setBackgroundResource(R.drawable.coupon_bg4);
						linearItem.setLayoutParams(layoutParam);
						setLinearParm(linearItem,entities.get(i));
						linearRight.addView(linearItem);
				   }
				}
			}else {
			   
				llmessage.setVisibility(View.VISIBLE);
			}
//			eScrollView.onRefreshComplete();
			
		};
	};
	//设置每个优惠券的详细信息
	private void setLinearParm(LinearLayout linearItem,BarginListEntity entity){
	
		TextView tv_shopname= (TextView)linearItem.findViewById(R.id.tv_shopName);
		tv_shopname.setText(entity.getShopName());
		
		TextView tv_price= (TextView)linearItem.findViewById(R.id.tv_price);
		tv_price.setText(String.valueOf(entity.getPrice()));
		
		TextView tv_prop= (TextView)linearItem.findViewById(R.id.tv_prop);
		tv_prop.setText(entity.getProp());
		
		TextView tv_limitTime= (TextView)linearItem.findViewById(R.id.tv_limitTime);
		tv_limitTime.setText(entity.getLimitTime());
		
		Button btn_cash_coupon=(Button)linearItem.findViewById(R.id.btn_cash_coupon);
		switch(entity.getStat()){
		case 1:
			btn_cash_coupon.setText(" 已使用 ");
			btn_cash_coupon.setBackgroundResource(R.drawable.btn_bggrey_nor);
			break;
		case 2:
			btn_cash_coupon.setText(" 可用 ");
			btn_cash_coupon.setBackgroundResource(R.drawable.btn_bgred_nor);
			break;
		case 3:
			btn_cash_coupon.setText(" 已过期 ");
			btn_cash_coupon.setBackgroundResource(R.drawable.btn_bggrey_nor);
			
			break;
		}
		
	}
	//设置侧边栏
	private void initSlidingMenu() {

		/*setBehindView();
		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0f);
		sm.setMode(SlidingMenu.RIGHT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
		sm.setShadowWidth(10);
		sm.setBehindScrollScale(0.5f);*/
	}
	//设置侧边栏
	/*private void setBehindView() {
		setBehindContentView(R.layout.cash_coupon_sort);
		imgBack = (ImageButton) findViewById(R.id.action_back);
		imgBack.setOnClickListener(this);
		//处理分类
		rl_food=(RelativeLayout)findViewById(R.id.rl_food);
		rl_food.setOnClickListener(this);
		
		rl_fun=(RelativeLayout) findViewById(R.id.rl_fun);
		rl_fun.setOnClickListener(this);
		
		rl_shopping=(RelativeLayout) findViewById(R.id.rl_shopping);
		rl_shopping.setOnClickListener(this);
		
	}*/
	final Handler handler = new Handler() {
		public void handleMessage(Message message) {

			
			eScrollView.onRefreshComplete();
		}
	};
	//初始化view
	private void intview() {
		// TODO Auto-generated method stub
		ivback=(ImageView)this.findViewById(R.id.ivBack);
		ivback.setOnClickListener(this);
		
		img_right=(ImageView)findViewById(R.id.iv_top_right);
		img_right.setOnClickListener(this);
		btn_new = (Button)findViewById(R.id.btn_new);
		btn_new.setOnClickListener(this);
		btn_new.setSelected(true);
		
		//搜索框
		et_search=(EditText)findViewById(R.id.et_search);
		et_search.setFocusable(true);
		et_search.setFocusableInTouchMode(true);
		et_search.requestFocus();
		et_search.addTextChangedListener(this);
		
		
		
		btn_mycoupon = (Button)findViewById(R.id.btn_mycoupon);
		btn_mycoupon.setOnClickListener(this);
		
		linearItem = (LinearLayout) this.getLayoutInflater().inflate(
				R.layout.item_cash_coupon, null);
		linearLeft = (LinearLayout) this.findViewById(R.id.linear_left);
		linearRight = (LinearLayout) this.findViewById(R.id.linear_right);
		
		
		layoutParam = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
		layoutParam.setMargins(0, 10, 0, 0);
		layoutParam.height=(int) (426*Constants.displayWidth/480/1.25);
		
		 minflater = getLayoutInflater(); 
		 
		 llmessage=(LinearLayout)findViewById(R.id.ll_message);
	}
    
	/* 
	 * 按钮的点击事件
	 */
	@Override
	public void onClick(View view ) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.ivBack:
			finish();
			break;
		case R.id.btn_new:
			if(!btn_new.isSelected()) {
				getData("", 0);
			}
			btn_new.setSelected(true);
			btn_mycoupon.setSelected(false);
			
			
			break;
		case R.id.btn_mycoupon:
			if (!btn_mycoupon.isSelected()) {
				getData("", 1);
			}
			btn_mycoupon.setSelected(true);
			btn_new.setSelected(false);
			break;
		case R.id.action_back:
//			showContent();
			break;
		case R.id.rl_fun:
//			showContent();
			break;
		case R.id.rl_food:
//			showContent();
			break;
		case R.id.rl_shopping:
//			showContent();
			break;
		
		case R.id.iv_top_right:
//			showMenu();
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	String beforeChange = "";
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		beforeChange = s.toString();
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		String str = s.toString();
		if(str.length()>0 && !beforeChange.equals(str)){
			if(btn_new.isSelected()) {
				getData(str, 0);
			} else {
				getData(str, 1);
			}
			
		}
	}
	
}
