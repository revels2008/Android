/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * ApacheUtility.java
 * 
 * 2013年8月26日-下午2:34:41
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

/**
 * 
 * ApacheUtility
 * 
 * @author: YangCheng Miao
 * 2013年8月26日-下午2:34:41
 * 
 * @version 1.0.0
 * 
 */
public class ApacheUtility {

	/**
	  * 获取图片流
	  * 
	  * @param uri 图片地址
	
	  * @return
	  * @throws MalformedURLException
	  */
	 public static InputStream GetImageByUrl(String uri) throws MalformedURLException {
		 URL url = new URL(uri);
		 URLConnection conn;
		 InputStream is;
		 try {
			 conn = url.openConnection();
			 conn.connect();
			 is = conn.getInputStream();
	
			 // 或者用如下方法
	
			 // is=(InputStream)url.getContent();
			 return is;
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	
		 return null;
	 }

 

 

	/**
	  * 获取Bitmap 
	
	  * 
	  * @param uri 图片地址
	  * @return
	  */
	 public static Bitmap GetBitmapByUrl(String uri) {
	
	  Bitmap bitmap;  
	  InputStream is;
	  try {
	
	   is =  GetImageByUrl(uri); 
	
	   bitmap = BitmapFactory.decodeStream(is);
	   is.close();
	
	   return bitmap;
	
	  } catch (MalformedURLException e) {
		  e.printStackTrace();
	
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	
	  return null;
	 }
	
	 

 

	/**
	  * 获取Drawable
	
	  * 
	  * @param uri  图片地址
	
	  * @return
	  */
	 public static Drawable GetDrawableByUrl(String uri) {
	
	  Drawable drawable;  
	  InputStream is;
	  try {
	
	   is =  GetImageByUrl(uri); 
	
	   drawable= Drawable.createFromStream(is, "src");
	
	   if (is != null) {
		   is.close();
	   }
	   
	   return drawable;
	
	  } catch (MalformedURLException e) {
		  e.printStackTrace();
	   
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  
	
	  return null;
	 }
	 
} 

