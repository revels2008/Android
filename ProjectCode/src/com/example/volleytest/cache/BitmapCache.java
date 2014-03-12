package com.example.volleytest.cache;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.powerlong.electric.app.utils.LogUtil;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1) 
public class BitmapCache implements ImageCache {
	private android.support.v4.util.LruCache<String, Bitmap> mCache;
	public BitmapCache() {
		int maxSize = 3 * 1024 * 1024;
		mCache = new android.support.v4.util.LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
			
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		LogUtil.e("BitmapCache", "getBitmap url = "+url);
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		LogUtil.e("BitmapCache", "putBitmap url = "+url+",bitmap="+bitmap);
		mCache.put(url, bitmap);
	}

}
