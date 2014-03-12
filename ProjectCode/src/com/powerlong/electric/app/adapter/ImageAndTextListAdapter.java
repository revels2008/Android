package com.powerlong.electric.app.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.ViewCache;
import com.powerlong.electric.app.entity.ImageAndText;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;

public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {
	private GridView mGidView;
	private AsyncImageLoader asyncImageLoader;
	private HashMap<Integer, View> mViewMap = new HashMap<Integer, View>();
	private List<ImageAndText> mList;

	public ImageAndTextListAdapter(Activity activity,
			List<ImageAndText> imageAndTexts, GridView gridView) {
		super(activity, 0, imageAndTexts);
		this.mGidView = gridView;
		asyncImageLoader = new AsyncImageLoader();
		mList = imageAndTexts;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public ImageAndText getItem(int position) {
		return mList.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		ViewCache viewCache;
		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.brand_grid_item, null);
			viewCache = new ViewCache(convertView);
			convertView.setTag(viewCache);
		} else {
			viewCache = (ViewCache) convertView.getTag();
		}
		ImageAndText imageAndText = getItem(position);

		if (imageAndText != null) {
			// Load the image and set it on the ImageView
			String imageUrl = imageAndText.getImageUrl();
			ImageView imageView = viewCache.getImageView();
			imageView.setTag(Integer.valueOf(position) + imageUrl);
			Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
					new ImageCallback() {
						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) mGidView
									.findViewWithTag(Integer.valueOf(position)
											+ imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageDrawable(imageDrawable);
							}
						}
					});
			if (cachedImage == null) {
				imageView.setImageResource(R.drawable.pic_56);
				// Log.e("Adapter", "null");
			} else {
				imageView.setImageDrawable(cachedImage);
			}
			// Set the text on the TextView
			TextView textView = viewCache.getTextView();
			textView.setText(imageAndText.getText());
			textView.setVisibility(View.GONE);
		}

		return convertView;
	}

	public void clear() {
		// this.mViewMap.clear();
		this.notifyDataSetChanged();
	}

}