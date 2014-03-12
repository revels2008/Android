/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * GrouponDetailNew.java
 * 
 * 2013-11-12-上午09:47:04
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.net.URL;
import java.util.Date;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.GrouponDetailData;
import com.powerlong.electric.app.domain.GrouponDetailInnerShop;
import com.powerlong.electric.app.ui.adapter.AdapterGrouponInnerShop;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.BitmapUtils;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * GrouponDetailNew
 * 
 * @author: Hegao
 * 2013-11-12-上午09:47:04
 * 
 * @version 1.0.0
 * 
 */
public class GrouponDetailNew extends BaseActivity{
	private Context context;
	@ViewInject(id=R.id.img_item)private ImageView img_item;
	@ViewInject(id=R.id.tv_rate) private TextView tv_rate;
	@ViewInject(id=R.id.tv_price) private TextView tv_price;
	@ViewInject(id=R.id.tv_price2) private TextView tv_price2;
	@ViewInject(id=R.id.tv_discount) private TextView tv_discount;
	@ViewInject(id=R.id.tv_name) private TextView tv_name;
	@ViewInject(id=R.id.tv_more_detail)private TextView tv_more_detail;
	@ViewInject(id=R.id.tv_all_comments) private TextView tv_all_comments;
	@ViewInject(id=R.id.tv_all_shops) private TextView tv_all_shops;
	@ViewInject(id=R.id.list_shops) ListView list_shops;
	@ViewInject(id=R.id.btn_buy) private Button btn_buy;
	@ViewInject(id=R.id.tv_content) private TextView tv_content;
	@ViewInject(id=R.id.img_shop) private ImageView img_shop;
	@ViewInject(id=R.id.tv_shop_name) private TextView tv_shop_name;
	@ViewInject(id=R.id.tv_shop_type) private TextView tv_shop_type;
	@ViewInject(id=R.id.tv_collect_num) private TextView tv_collect_num;
	@ViewInject(id=R.id.tv_contact) private TextView tv_contact;
	@ViewInject(id=R.id.tv_rejected) private TextView tv_rejected;
	@ViewInject(id=R.id.tv_refund) private TextView tv_refund;
	@ViewInject(id=R.id.tv_compensate) private TextView tv_compensate;
	@ViewInject(id=R.id.tv_sell_num) private TextView tv_sell_num;
	@ViewInject(id=R.id.tv_times) private TextView tv_times;
	private AdapterGrouponInnerShop<GrouponDetailInnerShop> adapterGrouponInnerShop;
	private long groupId;
	private String methodParams;
	private Handler h = new Handler();
	private long left_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groupon_detail);
		FinalActivity.initInjectedView(this);
		initService();
		try {
			initData();
		} catch (JSONException e) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	private void setViewData(GrouponDetailData detailData) {
		FinalBitmap fb = BitmapUtils.getFinalBitmap(context);
		fb.display(img_item, detailData.getImageList());
		
		tv_price.setText(Constants.moneyTag+detailData.getPlPrice());
		tv_price2.setText(Constants.moneyTag+detailData.getListPrice());
		
		tv_name.setText(detailData.getName());
		//无理由退货isReturnItem
		tv_rejected.setVisibility(("0".equals(detailData.getIsReturnItem()) ? View.GONE : View.VISIBLE));
		//过期退款isReturnMoney
		tv_refund.setVisibility("0".equals(detailData.getIsReturnMoney()) ? View.GONE : View.VISIBLE);
		//假一赔三isPaidfor
		tv_compensate.setVisibility("0".equals(detailData.getIsPaidfor()) ? View.GONE : View.VISIBLE);
		//显示html格式的团购内容
		tv_content.setText(Html.fromHtml(detailData.getContent(), imgGetter, null));
		//显示已团购数量
		tv_sell_num.setText(detailData.getSellNum()+"人已经团购");
		//显示倒计时
		left_time = Long.parseLong(detailData.getCountDownTime());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(left_time>0){
						h.post(new Runnable() {
							@Override
							public void run() {
								Date date = new Date(left_time);
								StringBuilder sb = new StringBuilder("倒计时：");
								sb.append(date.getHours()).append("小时").append(date.getMinutes());
								sb.append("分").append(date.getSeconds()).append("秒");
								tv_times.setText(sb.toString());
							}
						});
						left_time-=1000;
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
				}
			}
		}).start();
		//跳转更多详情
		tv_more_detail.setText("更多详情（包含"+detailData.getItemNum()+"件商品");
		if(Integer.parseInt(detailData.getItemNum())>0){
			tv_more_detail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(GrouponDetailNew.this,ActivityGroupMoreDetail.class);
					intent.putExtra("grouponId", groupId+"");
					startActivity(intent);
				}
			});
		}
		//显示所有评价
		tv_all_comments.setText("所有评价 "+detailData.getCommentNum());
		if(Integer.parseInt(detailData.getCommentNum())>0){
			tv_all_comments.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
				}
			});
		}
		//填充商铺数据
		adapterGrouponInnerShop.addData(GrouponDetailInnerShop.convertJsonToInnerShop(context, detailData.getShopList()));
		
//		tv_all_shops
	}
	
	private ImageGetter imgGetter = new Html.ImageGetter() {  
        public Drawable getDrawable(String source) {  
              Drawable drawable = null;  
              URL url;    
              try {     
                  url = new URL(source);    
                  drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片  
              } catch (Exception e) {    
                  return null;    
              }    
              drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable  
                            .getIntrinsicHeight());  
              return drawable;   
        }  
};  
	

	private void initData() throws JSONException {
		FinalHttp finalHttp = new FinalHttp();
		JSONObject jsObj = new JSONObject();
		jsObj.put("grouponId", groupId);
		jsObj.put("mall", Constants.mallId);
		AjaxParams params = new AjaxParams();
		params.put("data", jsObj.toString());
		finalHttp.get(Constants.ServerUrl.URL_GET_GROUPON_DETAIL, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if(t!=null && strMsg!=null){
					Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onSuccess(String t) {
				try {
					JSONObject jsObj = new JSONObject(t);
					if(0==jsObj.getInt("code")){
//						JSONObject data = jsObj.getJSONObject("data");
						GrouponDetailData detailData = GrouponDetailData.convertJsonToDetail(jsObj.getString("data"));
						setViewData(detailData);
					}else{
						Toast.makeText(context, jsObj.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void initService() {
		context = this;
		Intent intent = getIntent();
		groupId = intent.getLongExtra("groupId", groupId);
		methodParams = intent.getStringExtra("methodParams");
		if(!StringUtil.isEmpty(methodParams)){
			try {
				JSONObject obj = new JSONObject(methodParams);
				groupId = obj.getInt("grouponId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		adapterGrouponInnerShop = new AdapterGrouponInnerShop<GrouponDetailInnerShop>(context);
		list_shops.setAdapter(adapterGrouponInnerShop);
	}

	
	
}
