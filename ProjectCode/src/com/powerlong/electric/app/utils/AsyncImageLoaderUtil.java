/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * AsyncImageLoaderUtil.java
 * 
 * 2013年8月24日-下午5:47:21
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * 
 * AsyncImageLoaderUtil
 * 
 * @author: YangCheng Miao
 * 2013年8月24日-下午5:47:21
 * 
 * @version 1.0.0
 * 
 */
public class AsyncImageLoaderUtil {

		 // SoftReference是软引用，是为了更好的为了系统回收变量
		 private HashMap<String, SoftReference<Drawable>> imagesCache;

		 public AsyncImageLoaderUtil () {
		   imagesCache = new HashMap<String, SoftReference<Drawable>>();
		 }
		 
		 public Drawable loadDrawable(final String imageUrl,final ImageView imageView,final ILoadImageCallback callback){
		   if(imagesCache.containsKey(imageUrl)){
		     // 从缓存中获取
		     SoftReference<Drawable> softReference=imagesCache.get(imageUrl);
		     Drawable drawable=softReference.get();
		     if(null!=drawable){
		       return drawable;
		     }
		  }
		  
		  final Handler handler=new Handler(){
		    public void handleMessage(Message message){
		      callback.onObtainDrawable((Drawable)message.obj, imageView);
		    }
		  };
		  //建立一个新的线程下载图片
		  new Thread(){
		    public void run(){
		      Drawable drawable=ApacheUtility.GetDrawableByUrl(imageUrl); // 调用前面 ApacheUtility 类的方法

		      imagesCache.put(imageUrl, new SoftReference<Drawable>(drawable));
		      Message msg=handler.obtainMessage(0, drawable);
		      handler.sendMessage(msg);
		    }
		  }.start();
		   
		  return null;
		 }

		 

		 /**
		  * 异步加载图片的回调接口

		  */
		 public interface ILoadImageCallback {
		  public void onObtainDrawable (Drawable drawable, ImageView imageView);
		 }

}
