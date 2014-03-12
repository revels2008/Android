/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * MyOrderDetailActivity.java
 * 
 * 2013年9月18日-上午11:18:47
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleytest.cache.BitmapCache;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.MyOrderAdapter;
import com.powerlong.electric.app.adapter.OrderItemBargainAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.OrderDetailEntity;
import com.powerlong.electric.app.entity.OrderListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.ListViewUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * MyOrderDetailActivity
 * 
 * @author: Liang Wang
 * 2013年9月18日-上午11:18:47
 * 
 * @version 1.0.0
 * 
 */
public class MyOrderDetailActivity extends BaseActivity implements OnClickListener {
	private LayoutInflater mInflater;
	private ListView lv_order_detail;
	private LinearLayout llmessage, ll_order_detail,tempLayout;
    private TextView tv_title;
    private TextView tv_username,tv_userphone,tv_useraddr,tv_state;
    private LinearLayout.LayoutParams layoutParams;
    private ImageView ivback;
    private long orderId;
    private TextView tv_shop_name,tv_commodity_name,tv_commodity_price,tv_commodity_count,tv_commodity_introduction;
	private RequestQueue mRequestQueue;
	private ImageLoader imageLoader;
	private TextView tv_order_id,tv_order_zhifuid,tv_order_date,tv_freight,tv_money;
	private RelativeLayout rlShop;
	private LinearLayout llItem;
	private ListView lvOrder;
	private int stat;
	ArrayList<ItemBargainListEntity>  orderItemBargainList;
	private int allNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.order_detail);
		InitView();
		orderId= Long.parseLong(this.getIntent().getStringExtra("orderItemId"));
		stat = this.getIntent().getIntExtra("stat", 0);
		allNum = this.getIntent().getIntExtra("allNum", 0);
		LogUtil.d("===id", String.valueOf(orderId));
		
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
		DataUtil.getMyOrderLDetails(getApplicationContext(), shandle,orderId, stat);
	}
	
	private ServerConnectionHandler shandle=new ServerConnectionHandler(){
		public void handleMessage(Message msg) {		
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
		}

		private void updateView() {
			ArrayList<OrderDetailEntity> entityList=DataCache.UserOrderDetailCache;
			OrderDetailEntity orderDetailEntity=entityList.get(0);

			tv_title.setText("总计："+ allNum +"件商品,"+orderDetailEntity.getAllPrice()+"元");
			tv_username.setText("收货人:"+orderDetailEntity.getReceiver());
			tv_useraddr.setText(orderDetailEntity.getAddress());
			tv_userphone.setText(orderDetailEntity.getTel());
			SetTextView(tv_state,orderDetailEntity.getOrderStat());
			tv_order_id.setText(orderDetailEntity.getOrderNo());
			tv_order_zhifuid.setText(orderDetailEntity.getPayId());
			tv_order_date.setText(orderDetailEntity.getReceivedTime());
			
			tv_freight.setText(String.valueOf(orderDetailEntity.getFreight()));
			tv_money.setText(String.valueOf(orderDetailEntity.getAllPrice()));
			ArrayList<CartShopListEntity> shopEntities= DataCache.UserOrderDetailShopListCache;
			 
			 for(int j=0; j<shopEntities.size(); j++) {
				 CartShopListEntity shopEntity = shopEntities.get(j);
				 ArrayList<ItemListEntity>itemList= DataCache.UserOrderDetailItemListCache.get(shopEntity.getShopId());
				 
					for (int i = 0; i < itemList.size(); i++) {
						ItemListEntity entity=itemList.get(i);
						final long itemId = entity.getItemId();
						final String type = entity.getIsGroupon();
						
						tempLayout=(LinearLayout) mInflater.inflate(R.layout.item_myorder_detail, null);
						
						rlShop = (RelativeLayout) tempLayout.findViewById(R.id.rl_shop_title);
						llItem = (LinearLayout) tempLayout.findViewById(R.id.ll_order_item);
						llItem.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(type.equals("0")) {
									Intent intent = new Intent(MyOrderDetailActivity.this, ItemDetailActivity.class);
									intent.putExtra("itemId", itemId);
									startActivity(intent);
								} else {
									Intent intent = new Intent(MyOrderDetailActivity.this, GrouponDetailActivity.class);
									intent.putExtra("groupId", itemId);
									startActivity(intent);
								}
								
							}
						});
						
						if(i > 0) {
							rlShop.setVisibility(View.GONE);
						}
						tv_shop_name=(TextView) tempLayout.findViewById(R.id.tv_shop_name);
						tv_shop_name.setText(shopEntity.getShopName());
						
						tv_commodity_name=(TextView)tempLayout.findViewById(R.id.tv_commodity_name);
						
						tv_commodity_name.setText(entity.getItemName());
						tv_commodity_price=(TextView)tempLayout.findViewById(R.id.tv_commodity_price);
//						tv_commodity_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
						//单价
						if(!StringUtil.isNullOrEmpty(entity.getplPrice()+"")){
							tv_commodity_price.setText(Constants.moneyTag+String.valueOf(entity.getplPrice()));
						}else{
							tv_commodity_price.setText("");
						}
						tv_commodity_count=(TextView)tempLayout.findViewById(R.id.tv_commodity_count);
						tv_commodity_count.setText(Constants.numPrfix+String.valueOf(entity.getBuyNum()));
						//tv_commodity_introduction 商品简介？
						tv_commodity_introduction=(TextView)tempLayout.findViewById(R.id.tv_commodity_introduction);
						if(!StringUtil.isNullOrEmpty(entity.getProp())){
							tv_commodity_introduction.setText(entity.getProp());
						}else{
							tv_commodity_introduction.setText("");
						}
						int orderItemId = new Long(entity.getItemId()).intValue();
						orderItemBargainList = DataCache.orderItemBargainListCache.get(orderItemId);
						if(null != orderItemBargainList && orderItemBargainList.size() > 0) {
							lvOrder = (ListView) tempLayout.findViewById(R.id.lv_order_item_gift);
							
							OrderItemBargainAdapter adapter = new OrderItemBargainAdapter(getBaseContext(), orderItemBargainList, lvOrder);
							lvOrder.setAdapter(adapter);
							ListViewUtil.setListViewHeightBasedOnChildren(lvOrder);
						}
						
//						
						NetworkImageView imageView=(NetworkImageView)tempLayout.findViewById(R.id.im_commodity);
						imageView.setImageUrl(entity.getImage(), imageLoader);
						ll_order_detail.addView(tempLayout,layoutParams);
					}
			 }			
		};
		
	};
    //初始化控件
    private void InitView(){
    	mInflater=getLayoutInflater();
    	
    	ll_order_detail=(LinearLayout)findViewById(R.id.ll_order_detail);
    	
    	layoutParams=new LayoutParams(layoutParams.MATCH_PARENT,layoutParams.WRAP_CONTENT);
    	
    	tv_title=(TextView)findViewById(R.id.tv_title);
    	
    	ivback=(ImageView) this.findViewById(R.id.ivReturn);
        ivback.setOnClickListener(this);
        
        tv_username=(TextView)findViewById(R.id.tv_username);
        
        tv_userphone=(TextView)findViewById(R.id.tv_userphone);
        
        tv_useraddr=(TextView)findViewById(R.id.tv_useraddr);
        tv_state=(TextView)findViewById(R.id.tv_state);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
		
		tv_order_id=(TextView)findViewById(R.id.tv_order_id);
		tv_order_zhifuid=(TextView)findViewById(R.id.tv_order_zhifuid);
		tv_order_date=(TextView)findViewById(R.id.tv_order_date);
		tv_freight=(TextView)findViewById(R.id.tv_freight);
		tv_money=(TextView)findViewById(R.id.tv_money);
		
    }

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ivReturn:
			finish();
			break;

		default:
			break;
		}
		
	}
	//设置按钮的文字
	private void SetTextView(TextView textView, String state) {
		// orderStat 订单状态 0：已提交 待支付 1：已付款 待发货 2：已发货 待客户确认
		// 3：申请退款（已经提交申请，已发货）待商家审核
		// 4：申请退款（已经提交申请，未发货）待商家审核 5：商家审核未通过
		// 6：商家审核通过，待客户退货 7：待退款 8：退款成功
		// 9：交易成功 10：取消订单 11：评论 12：拒签

		if (state.equals("0")) {
			textView.setText("等待买家付款");
			
		} else if (state.equals("10")) {
			textView.setText("等待商家发货");
			
		} else if (state.equals("20")) {
			textView.setText("已拣货");
			
		} else if (state.equals("25")) {
			textView.setText("缺货");
			
		} else if (state.equals("30")) {
			textView.setText("等待买家确认收货");
			
		} else if (state.equals("40")) {
			textView.setText("买家已收到货，申请退货");
			
		} else if (state.equals("50")) {
			textView.setText("买家未收到货，申请退款");
			
		} else if (state.equals("60")) {
			textView.setText("商家拒绝退款/退货");
			
		} else if (state.equals("70")) {
			textView.setText("商家同意退货");
			
		} else if (state.equals("80")) {
			textView.setText("已退货，待退款");
			
		} else if (state.equals("90")) {
			textView.setText("退款成功");
			
		} else if (state.equals("100")) {
			textView.setText("交易完成");
			
		} else if (state.equals("110")) {
			textView.setText("交易关闭");
			
		} else if (state.equals("120")) {
			textView.setText("已评论");
			
		} else if (state.equals("130")) {
			textView.setText("交易关闭");
			
		} else if (state.equals("140")) {
			textView.setText("交易关闭");
			
		} else if (state.equals("150")) {
			textView.setText("交易关闭");
			
		}
	}
	
    
   
	
}
