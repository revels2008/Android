/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ItemPicViewFlipperActivity.java
 * 
 * 2013年10月8日-上午9:24:16
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * ItemPicViewFlipperActivity
 * 
 * @author: Yangcheng Miao
 * 2013年10月8日-上午9:24:16
 * 
 * @version 1.0.0
 * 
 */
public class ItemPicViewFlipperActivity extends BaseActivity implements OnGestureListener {       
	private GestureDetector detector;
	private FinalBitmap fb;
	private List<String> dataArray = null;
	private LayoutInflater mInflater=null;
	private com.powerlong.electric.app.utils.AsyncImageLoader mAsyncImageLoader;
	private ViewFlipper flipper;
	private  View convertView=null;
	private int currentPage=0;
	private int screenHeight;
	private int screenWidth;
	private int index;
	private int imgHeight;
	private int imgWidth;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_pic_viewflipper);
		
		WindowManager wm = getWindowManager();
		screenHeight = wm.getDefaultDisplay().getHeight();
		screenWidth = wm.getDefaultDisplay().getWidth();
		detector = new GestureDetector(this);
		flipper = (ViewFlipper) this.findViewById(R.id.viewFlipper); 
		mInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		mAsyncImageLoader = new AsyncImageLoader();
		dataArray = getIntent().getStringArrayListExtra("urlList");	
		index = Integer.parseInt(getIntent().getExtras().getString("index"));
		fb = FinalBitmap.create(getBaseContext());
		fb.configDiskCachePath(Constants.IMG_CACHE_PATH);
		getData(index);
		for (int i = 0; i < dataArray.size(); i++) {  
			flipper.addView(addTextView(i,dataArray.get(i).toString()),i);
            convertView=null;
	    }  
		currentPage = index;
//		flipper.showNext();
		flipper.setDisplayedChild(index);
	}
	
	private void getData(int index) {
		HttpUtil.getItemLargeImgJson(getBaseContext(), mServerConnectionHandler, dataArray.get(index), screenHeight, screenWidth);
		Log.v("<<<<<<<<<screenHeight=", screenHeight+"");
		Log.v("<<<<<<<<<screenWidth=", screenWidth+"");
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemPicViewFlipperActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemPicViewFlipperActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				String urlNew = (String)msg.obj;
				//flipper.addView(addTextView(0, urlNew),0);
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
	 private View addTextView(final int i, String url) {		
		 if (convertView == null) {
				convertView = mInflater.inflate(R.layout.image_item, null);
			}
			ImageView imgView = (ImageView) convertView.findViewById(R.id.imgMatch);
			imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			
			fb.display(imgView, url);
			
			return convertView;
	}
	 
	private void loadAsyncImage(final ImageView imgView, String imgUrl,final View convertView)
	{	
		imgView.setTag(imgUrl);
        Drawable cachedAvartaImage = mAsyncImageLoader.loadDrawable(imgUrl, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) convertView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable); 	
                    imgHeight = imageDrawable.getIntrinsicHeight();
                    imgWidth = imageDrawable.getIntrinsicWidth();
                    LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams
        					(imgWidth*10, imgHeight*10);
        			imgView.setLayoutParams(lp);
                }else
                {
	                Log.d("Longqiang:","imageViewByTag is NULL");
                }
            }
        });
		if (cachedAvartaImage == null) {
//			imgView.setBackgroundResource(R.drawable.ic_launcher);
		}else{
			imgView.setImageDrawable(cachedAvartaImage);			
		}		
	}
	class listAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return dataArray.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.image_item, null);
			}
			ImageView imgView = (ImageView) convertView.findViewById(R.id.imgMatch);
			loadAsyncImage(imgView, dataArray.get(position),convertView);		

			return convertView;
		}
		
	}
	 @Override
	    public boolean onTouchEvent(MotionEvent event) {
	    	return this.detector.onTouchEvent(event);
	    }
	    
	    @Override
		public boolean onDown(MotionEvent e) {
			return false;
		}
	    
	    @Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
			if (e1.getX() - e2.getX() > 120 && currentPage < dataArray.size()-1) {
				currentPage++;
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
				this.flipper.showNext();	
//				if(currentPage==dataArray.size()){
//					currentPage=0;
//				}

				return true;
			} else if (e1.getX() - e2.getX() < -120 && currentPage > 0) {
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
				this.flipper.showPrevious();
//				if(currentPage==0){
//					currentPage=dataArray.size()-1;
//				}else{
					currentPage--;
//				}

				return true;
			}
			return false;
		}
	    
	    @Override
	    public void onLongPress(MotionEvent e) {
	    	
	    }
	    
	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
	    	return false;
	    }
	    
	    @Override
	    public void onShowPress(MotionEvent e) {
	    	
	    }
	    
	    @Override
	    public boolean onSingleTapUp(MotionEvent e) {
	    	finish();
	    	return false;
	    }

}
