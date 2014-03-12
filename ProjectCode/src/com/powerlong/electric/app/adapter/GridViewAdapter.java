package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;

//import ProtocalEngine.ProtocalEngine.ProtocalProcess.FeiliuProcess.Group.GroupAlbum.AlbumDetail.AlbumDetailData;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;

//import com.blade.qianghaoqi.util.AsyncImageLoader;

public class GridViewAdapter extends ArrayAdapter<String> {
	private Activity mActivity;
	private LayoutInflater mInflater;
	//private ArrayList<AlbumDetailData> mDatas;
	private HashMap<Integer, View> mViewMap = new HashMap<Integer, View>();
	
//	private HashMap<Integer, ViewHolder> mHolderMap = new HashMap<Integer, TeamInfoPhotosDetailAdapter.ViewHolder>();
	
	private Animation animation;
	
	private boolean Running;
	
	//AsyncImageLoader mAsyncImageLoader=new AsyncImageLoader();
	
	ArrayList<Drawable> mDatas = new ArrayList<Drawable>();

	public GridViewAdapter(Activity activity, int textViewResourceId,
	ArrayList<Drawable> objects)
			{
		super(activity, textViewResourceId);
		this.mActivity = activity;
		this.mDatas = objects;
	//	this.mDatas = (ArrayList<AlbumDetailData>) objects;
		mInflater = LayoutInflater.from(mActivity);
	//	animation = AnimationUtils.loadAnimation(mActivity, R.anim.shake);

		Running = false;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	//	if(mDatas == null){
	//		return 0;
	//	}
	//	return mDatas.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder aHolder = null;
		convertView = mViewMap.get(position);
		if (convertView == null) {
			aHolder = new ViewHolder(); 
			convertView = mInflater.inflate(R.layout.brand_grid_item, null);

			aHolder.mPhoto = (ImageView) convertView.findViewById(R.id.iv_brand_item);
			aHolder.mPhotoInfo = (TextView) convertView.findViewById(R.id.text_item);
			
			convertView.setTag(aHolder);
			mViewMap.put(position, convertView);
		} else {
			aHolder = (ViewHolder) convertView.getTag();
		}
		
	//	mHolderMap.put(position, aHolder);
		
		//mActivity.getResources().getDrawable(R.drawable.ic_launcher)
		//mActivity.getResources().getString(R.string.app_name);
		//aHolder.mPhoto.setBackground(mDatas.get(position));
		aHolder.mPhoto.setBackgroundDrawable(mDatas.get(position));
	//	mAsyncImageLoader.setViewImage(aHolder.mPhotoFont, mDatas.get(position).picPathSmall);
		
	//	aHolder.mPhotoFont = mDatas.get(position)
		
		return convertView;
	}

	static class ViewHolder {
		ImageView mPhoto;
		TextView mPhotoInfo;
	}
	
//	public void satrAnimation() {
//		final int aSize=mHolderMap.size();
//		for (int i = 0; i < aSize; i ++) {
//			mHolderMap.get(i).mPhotoFont.setAnimation(animation);
//		}
//		Running = true;
//	}
//	public void stopAnimation() {
//		final int aSize=mHolderMap.size();
//		for (int i = 0; i < aSize; i ++) {
//			mHolderMap.get(i).mPhotoFont.clearAnimation();
//			mHolderMap.get(i).mPhotoInfo.setVisibility(ImageView.GONE);
//		}
//		Running = false;
//	}
//	
//	public void showDelButton() {
//		final int aSize=mHolderMap.size();
//		for (int i = 0; i < aSize; i ++) {
//			mHolderMap.get(i).mPhotoInfo.setVisibility(ImageView.VISIBLE);
//		}
//	}

	public boolean isRunning() {
		return Running;
	}

	public void setRunning(boolean running) {
		Running = running;
	}
	
	public void clear(){
		this.mViewMap.clear();
		this.notifyDataSetChanged();
	}
	
}
