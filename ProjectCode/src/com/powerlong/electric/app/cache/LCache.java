/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * LCache.java
 * 
 * 2013-8-7-下午05:24:30
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.powerlong.electric.app.utils.LogUtil;

import android.graphics.Bitmap;

/**
 * 
 * LCache:
 * <p>
 * Bitmap缓存池
 * </p>
 * 
 * <p>
 * 基于2Q改进算法缓存
 * </p>
 * <p>
 * http://www.vldb.org/conf/1994/P439.PDF
 * </p>
 * 
 * 使用： LCache static lCache = new LCache();
 * 
 * ...
 * 
 * 
 * String imgUrl = "http://monstar.ch/uploads/img/201212/22085340_Zun9.jpg";
 * if(lCache.isCached(imgUrl)){ Bitmap tBitmap = lCache.get(imgUrl); }else{
 * //从服务端获取图像 Bitmap tBitmap = 服务端获取的Bitmap
 * 
 * lCache.put(imgUrl,tBitmap); }
 * 
 * @author: Liang Wang 2013-8-7-下午05:24:30
 * 
 * @version 1.0.0
 * 
 */
public class LCache implements ImageCache {
	private static final int DEFAULT_MAX_SIZE = (int) (1024 * 1024 * 10f); // 默认缓存池大小
																			// 10M
	private static final int DEFAULT_IN_QUEUE_MAX_SIZE = 40; // fInCacheQue队列默认大小
	private static final int DEFAULT_OUT_QUEUE_MAX_SIZE = 60; // fOutCacheQue队列默认大小
	private static final int DEFAULT_CACHE_QUEUE_MAX_SIZE = 0; // finalCacheQue队列默认大小

	private final HashMap<String, Ref> cache; // 缓存池
	private final Queue<String> fInCacheQue; // 一级缓存，Ain
	private final Queue<String> fOutCacheQue; // 清楚一级缓存记录，Aout
	private final Queue<String> finalCacheQue; // 最终缓存，Am

	private final int cacheMaxSize;
	private int size;
	private int curBitmapSize;

	/**
	 * 构建默认大小的Cache
	 */
	public LCache() {
		this(DEFAULT_MAX_SIZE);
	}

	/**
	 * 构建指定大小的Cache
	 * 
	 * @param cacheMaxSize
	 *            Cache最大容量
	 */
	public LCache(int cacheMaxSize) {
		if (0 >= cacheMaxSize) {
			throw new IllegalArgumentException(
					"cacheMaxSize must be greater than 0");
		}

		this.cacheMaxSize = cacheMaxSize;
		this.cache = new HashMap<String, Ref>();
		this.fInCacheQue = new Queue<String>(DEFAULT_IN_QUEUE_MAX_SIZE);
		this.fOutCacheQue = new Queue<String>(DEFAULT_OUT_QUEUE_MAX_SIZE);
		this.finalCacheQue = new Queue<String>(DEFAULT_CACHE_QUEUE_MAX_SIZE);
	}

	/**
	 * 
	 * @param key
	 *            key值 <a
	 *            href="\"http://www.eoeandroid.com/home.php?mod=space&uid=7300\""
	 *            target="\"_blank\"">
	 * @return</a> true-key对应的Bitmap在该缓存池中 false-key对应的Bitmap不在该缓存池中
	 */
	public synchronized boolean isCached(String key) {
//		LogUtil.d("LCache", "isCached + key = "+key);
		return this.cache.containsKey(key);
	}

	/**
	 * 获取缓存池中Bitmap
	 * 
	 * @param key
	 *            key值
	 * @return Bitmap
	 */
	public Bitmap getBitmap(String key) {
///		LogUtil.d("LCache", "getBitmap + key = "+key);
		if (null == key) {
			//throw new NullPointerException("The key can not be null");
			return null;
		}
//		LogUtil.d("LCache", "getBitmap + step0 = "+key);
		if ("".equals(key)) {
			//throw new IllegalArgumentException("The key dons't value");
			return null;
		}
//		LogUtil.d("LCache", "getBitmap + step1 = "+key);
		if(!this.isCached(key))
			return null;
//		LogUtil.d("LCache", "getBitmap + step2 = "+key);
		
		return this.getFromCache(key);
	}

	/**
	 * 将Bitmap缓存到该缓存池中
	 * 
	 * @param key
	 * @param mBitmap
	 */
	public synchronized void putBitmap(String key, Bitmap mBitmap) {
//		LogUtil.d("LCache", "putBitmap + key = "+key);
//		LogUtil.d("LCache", "putBitmap + mBitmap = "+mBitmap);
		if (this.cache.containsKey(key)) {
			return;
		}
//		LogUtil.d("LCache", "putBitmap + step1 = "+mBitmap);
		if (this.fOutCacheQue.contains(key)) {
//			LogUtil.d("LCache", "putBitmap + step2 = "+mBitmap);
			this.finalCacheQue.addToHead(key);
			this.fOutCacheQue.remove(key);
			this.reclaimCache(key, mBitmap);
		}

		else {
//			LogUtil.d("LCache", "putBitmap + step3 = "+mBitmap);
			this.fInCacheQue.addToHead(key);
			this.reclaimCache(key, mBitmap);
			if (this.fInCacheQue.isOverflow()) {
//				LogUtil.d("LCache", "putBitmap + step4 = "+mBitmap);
				String tKey = this.fInCacheQue.removeFromTail();
				this.clearCache(tKey);
				this.fOutCacheQue.addToHead(tKey);
				this.fOutCacheQue.trim();
			}
		}
	}

	private void reclaimCache(String key, Bitmap mBitmap) {
		if (this.hasFreeCache(mBitmap)) {
//			LogUtil.d("LCache", "reclaimCache + step0 = "+mBitmap);
			this.putIntoCache(key, mBitmap);
		}

		else if (this.fInCacheQue.isOverflow()) {
//			LogUtil.d("LCache", "reclaimCache + step1 = "+mBitmap);
			do {
				String tKey = this.fInCacheQue.removeFromTail();
				this.clearCache(tKey);
				this.fOutCacheQue.addToHead(key);
			} while (this.cacheMaxSize < (this.size + this.curBitmapSize));
			this.fOutCacheQue.trim();
			LogUtil.d("LCache", "reclaimCache + step2 = "+mBitmap);
			this.putIntoCache(key, mBitmap);
		}

		else {
			do {
				String tKey = this.finalCacheQue.removeFromTail();
				this.clearCache(tKey);
			} while (this.cacheMaxSize < (this.size + this.curBitmapSize));
			this.putIntoCache(key, mBitmap);
		}
	}

	private void putIntoCache(String key, Bitmap mBitmap) {
//		LogUtil.d("LCache", "putIntoCache + step0 key= "+key);
		this.cache.put(key, new Ref(mBitmap, this.curBitmapSize));
		this.size += this.curBitmapSize;
		this.curBitmapSize = 0;
	}

	private boolean hasFreeCache(Bitmap mBitmap) {
		boolean hasFreeCache = true;
		this.curBitmapSize = this.sizeOf(mBitmap);
		if (this.cacheMaxSize < (this.size + this.curBitmapSize)) {
			hasFreeCache = false;
		}

		return hasFreeCache;
	}

	private void clearCache(String key) {
		Ref tRef = this.cache.remove(key);
		if (null != tRef) {
			this.size -= tRef.getSize();
			if (null != tRef.getBitmap()) {
				tRef.getBitmap().recycle();
			}
		}
	}

	private Bitmap getFromCache(String key) {
		Bitmap tBitmap = null;
		synchronized (this) {
			if (this.finalCacheQue.contains(key)) {
				this.finalCacheQue.moveToHead(key);
			}
			tBitmap = this.cache.get(key).getBitmap();
		}

		return tBitmap;
	}

	/**
	 * Bitmap 存储大小
	 * 
	 * @param mBitmap
	 * @return
	 */
	private int sizeOf(Bitmap mBitmap) {
		int weight = 0;
		switch (mBitmap.getConfig()) {
		case ALPHA_8:
			weight = 1;
			break;
		case ARGB_4444:
			weight = 2;
			break;
		case ARGB_8888:
			weight = 4;
			break;
		case RGB_565:
			weight = 2;
			break;
		default:
			weight = 1;
			break;
		}

		return (mBitmap.getWidth() * mBitmap.getHeight() * weight);
	}

	public synchronized void destory() {
		this.cache.clear();
		this.finalCacheQue.clear();
		this.fInCacheQue.clear();
		this.fOutCacheQue.clear();
	}

	/**
	 * 队列
	 */
	class Queue<T> {
		private int capacity;

		private final List<T> list;

		/**
		 * @param capacity
		 *            队列最大容量，可为0；如果capacity为0，当前队列无大小限制
		 */
		public Queue(int capacity) {
			this.capacity = capacity;
			if (0 < this.capacity) {
				this.list = new ArrayList<T>(this.capacity);
			} else {
				this.list = new LinkedList<T>();
			}

		}

		/**
		 * 队列容量
		 * 
		 * @return 队列容量
		 */
		public int capacity() {
			return this.capacity;
		}

		/**
		 * 队列当前大小
		 * 
		 * @return 队列大小
		 */
		public int size() {
			return this.list.size();
		}

		/**
		 * 该队列中是否有key值
		 * 
		 * @param key
		 * @return true 存在key值，false 不存在key值
		 */
		public boolean contains(T key) {
			return this.list.contains(key);
		}

		/**
		 * 队列是否溢出
		 * 
		 * @return
		 */
		public boolean isOverflow() {
			if (0 == capacity) {
				return false;
			}

			else {
				return (this.list.size() > this.capacity);
			}
		}

		/**
		 * 将队列中key移动到队列头
		 * 
		 * @param key
		 *            值
		 */
		public void moveToHead(T key) {
			this.list.remove(key);
			this.list.add(key);
		}

		/**
		 * 将key添加到队列头
		 * 
		 * @param key
		 *            值
		 */
		public void addToHead(T key) {
			this.list.add(key);
		}

		/**
		 * 删除队列最后一个值
		 * 
		 * @return 当前删除队列值
		 */
		public T removeFromTail() {
			T tKey = null;
			if (0 < this.list.size()) {
				tKey = this.list.remove(0);
			}
			return tKey;
		}

		/**
		 * 删除队列中key值
		 * 
		 * @param key
		 *            key值
		 * @return 删除成功返回当前key，删除失败返回null
		 */
		public T remove(T key) {
			if (this.list.remove(key)) {
				return key;
			}
			return null;
		}

		/**
		 * 将Queue中超过capacity的长度截取掉 如果list.size <= capacity, 什么都不做
		 */
		public void trim() {
			if ((0 == capacity) || (this.list.size() <= this.capacity)) {
				return;
			}

			int tNum = this.list.size() - this.capacity;
			for (int idx = 0; idx < tNum; idx++) {
				this.removeFromTail();
			}
		}

		public void clear() {
			this.list.clear();
		}
	}

	class Ref {
		// private SoftReference<Bitmap> softReference;
		private Bitmap mBitmap;
		private int size;

		public Ref(Bitmap mBitmap, int size) {
			// this.softReference = new SoftReference<Bitmap>(mBitmap);
			this.mBitmap = mBitmap;
			this.size = size;
		}

		public int getSize() {
			return size;
		}

		public Bitmap getBitmap() {
			// return softReference.get();
			return this.mBitmap;
		}
	}
}
