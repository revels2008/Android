/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * HomeFragment.java
 * 
 * 2013-7-27-下午04:35:42
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.volleytest.cache.BitmapCache;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.BannerAdapter;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.widget.CircleFlowIndicator;
import com.powerlong.electric.app.widget.ElasticScrollView;
import com.powerlong.electric.app.widget.ElasticScrollView.OnRefreshListener;
import com.powerlong.electric.app.widget.PlBannerLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * HomeFragment
 * 
 * @author: Liang Wang 2013-7-27-下午04:35:42
 * 
 * @version 1.0.0
 * 
 */
public class HomeFragmentOld extends Fragment {
	private ElasticScrollView waterfall_scroll;
	//private LinearLayout waterfall_container;
	private ArrayList<LinearLayout> waterfall_items;
	private Display display;
	private int itemWidth;
	private int column_count = 2;// 显示列数
	private int page_count = 10;// 每次加载10张图片
	private int current_page = 0;
	private String mUrlTest = "http://192.168.180.115:8081/userWeb/ucUser/albumImgList.htm?page=3&itemsPerPage=20";
	private List<String> image_filenames;
	private boolean isConnected = false;
	
	private PlBannerLayout plBannerLayout = null;
	
	private static int PADDING = 6;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mQueue = Volley.newRequestQueue(getActivity());
		mBmpCache =  new BitmapCache();
		mImageLoader = new ImageLoader(mQueue, mBmpCache);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment_layout_new, container,
				false);
		display = getActivity().getWindowManager().getDefaultDisplay();
		itemWidth = display.getWidth() / column_count;// 根据屏幕大小计算每列大小
		mImageLoader = new ImageLoader(mQueue, mBmpCache,itemWidth);
		
		
		InitLayout(view);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		isConnected = false;
		current_page = 0;
		if(image_filenames!=null&& image_filenames.size()!=0)
			image_filenames.clear();
		//getDataTest();
	}
	
	final Handler handler = new Handler() {
      	public void handleMessage(Message message) {
      		String str = (String)message.obj;
      		OnReceiveData(/*str*/);
      	}
      };
      
    protected void OnReceiveData(/*String str*/) {
  		//TextView textView =  new TextView(this);
  		//textView.setText(str);
  		//elasticScrollView.addChild(textView, 1);
  		waterfall_scroll.onRefreshComplete();
  	}
      
	private void InitLayout(View view) {
		waterfall_scroll = (ElasticScrollView) view
				.findViewById(R.id.waterfall_scroll);
		waterfall_scroll.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Message message = handler.obtainMessage(0, "new Text");
						handler.sendMessage(message);
					}
				});
				thread.start();
			}
		});
		waterfall_items = new ArrayList<LinearLayout>();

		plBannerLayout = (PlBannerLayout)waterfall_scroll.findViewById(R.id.banner);
		plBannerLayout.setAdapter(new BannerAdapter(getActivity()));
		plBannerLayout.setmSideBuffer(3);
		
		CircleFlowIndicator indic = (CircleFlowIndicator) waterfall_scroll.findViewById(R.id.viewflowindic);
		plBannerLayout.setFlowIndicator(indic);
		plBannerLayout.setTimeSpan(2500);
		plBannerLayout.setSelection(3*1000);	//设置初始位置
		plBannerLayout.startAutoFlowTimer();  //启动自动播放
		
		waterfall_scroll.setView(plBannerLayout);
	}
	
	/**
	 * getDataTest(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getDataTest() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.setTimeout(3*1000);
		client.post(mUrlTest, params, new JsonHttpResponseHandler(){

			/* (non-Javadoc)
			 * @see com.loopj.android.http.AsyncHttpResponseHandler#onFailure(java.lang.Throwable)
			 */
			@Override
			public void onFailure(Throwable error) {
				LogUtil.d("HomeFragment", error.toString());
				isConnected = false;
				Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
			}

			/* (non-Javadoc)
			 * @see com.loopj.android.http.AsyncHttpResponseHandler#onSuccess(int, java.lang.String)
			 */
			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				LogUtil.d("HomeFragment", "onSuccess + content ="+object.toString());
				try {
					JSONArray array = object.getJSONArray("albumImgList");
					if(image_filenames == null){
						image_filenames = new LinkedList<String>();
					}
					for(int i =0;i<array.length();i++){
						JSONObject obj = array.getJSONObject(i);
						int id = obj.getInt("id");
						String fileName = obj.getString("fileName");
						String isDel = obj.getString("isDel");
						String imgDescription = obj.getString("imgDescription");
						String fileType = obj.getString("fileType");
						String createDate = obj.getString("createDate");
						String imgPath = obj.getString("imgPath");
						
						LogUtil.d("HomeFragment", "onSuccess + id ="+id);
						LogUtil.d("HomeFragment", "onSuccess + fileName ="+fileName);
						LogUtil.d("HomeFragment", "onSuccess + isDel ="+isDel);
						LogUtil.d("HomeFragment", "onSuccess + imgDescription ="+imgDescription);
						LogUtil.d("HomeFragment", "onSuccess + fileType ="+fileType);
						LogUtil.d("HomeFragment", "onSuccess + createDate ="+createDate);
						LogUtil.d("HomeFragment", "onSuccess + imgPath ="+imgPath);
						image_filenames.add(imgPath);
					}
					
					// 第一次加载
					AddItemToContainer(current_page, page_count);
				} catch (JSONException e) {
					e.printStackTrace();
					isConnected = false;
				}
				isConnected = true;
			}


			/* (non-Javadoc)
			 * @see com.loopj.android.http.JsonHttpResponseHandler#onSuccess(int, org.json.JSONArray)
			 */
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				LogUtil.d("HomeFragment","Onsuccess + content = "+response.toString());
				
			}
			
		});
	}
	
	private void AddItemToContainer(int pageindex, int pagecount) {
		int j = 0;
		int imagecount = image_filenames.size();
		LogUtil.w("HomeFragMent", "AddItemToContainer imagecount ="+imagecount);
		for (int i = pageindex * pagecount; i < pagecount * (pageindex + 1)
				&& i < imagecount; i++) {
			j = j >= column_count ? j = 0 : j;
			LogUtil.w("HomeFragMent", "AddItemToContainer  i ="+i);
			AddImage(image_filenames.get(i), j++);

		}
	}
	
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private BitmapCache mBmpCache = null;
	private void AddImage(String url, int columnIndex) {
		ImageView item = (ImageView) LayoutInflater.from(getActivity()).inflate(
				R.layout.waterfallitem, null);
		waterfall_items.get(columnIndex).addView(item);
		ImageListener listener = ImageLoader.getImageListener(item, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
	    mImageLoader.get(url, listener);
	}
}
