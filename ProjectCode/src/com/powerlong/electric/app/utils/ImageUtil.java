/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * ImageUtil.java
 * 
 * 2013-8-7-下午04:35:07
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * ImageUtil: 图片工具类
 * <ul>
 * Bitmap、byte数组、drawable之间转换
 * </ul>
 * 
 * @author: Liang Wang 2013-8-7-下午04:35:07
 * 
 * @version 1.0.0
 * 
 */
public class ImageUtil {
	/**
	 * Bitmap转换为byte数组
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * byte数组转换为Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : BitmapFactory
				.decodeByteArray(b, 0, b.length);
	}

	/**
	 * Drawable转换为Bitmap
	 * 
	 * @param d
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		return d == null ? null : ((BitmapDrawable) d).getBitmap();
	}

	/**
	 * Bitmap转换为Drawable
	 * 
	 * @param b
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * Drawable转换为byte数组
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * byte数组转换为Drawable
	 * 
	 * @param b
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	/**
	 * 根据imageUrl获得InputStream，需要自己手动关闭InputStream
	 * 
	 * @param imageUrl
	 *            图片url
	 * @return
	 */
	public static InputStream getInputStreamFromUrl(String imageUrl,
			int readTimeOut) {
		InputStream stream = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			if (readTimeOut > 0) {
				con.setReadTimeout(readTimeOut);
			}
			stream = con.getInputStream();
		} catch (MalformedURLException e) {
			closeInputStream(stream);
			throw new RuntimeException("MalformedURLException occurred. ", e);
		} catch (IOException e) {
			closeInputStream(stream);
			throw new RuntimeException("IOException occurred. ", e);
		}
		return stream;
	}

	/**
	 * 根据imageUrl获得Drawable
	 * 
	 * @param imageUrl
	 *            图片url
	 * @return
	 */
	public static Drawable getDrawableFromUrl(String imageUrl, int readTimeOut) {
		InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut);
		Drawable d = Drawable.createFromStream(stream, "src");
		closeInputStream(stream);
		return d;
	}

	/**
	 * 根据imageUrl获得Bitmap
	 * 
	 * @param imageUrl
	 *            图片url
	 * @return
	 */
	public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
		InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut);
		Bitmap b = BitmapFactory.decodeStream(stream);
		closeInputStream(stream);
		return b;
	}

	/**
	 * 缩放图片
	 * 
	 * @param org
	 *            原图片
	 * @param newWidth
	 *            新图片的宽度
	 * @param newHeight
	 *            新图片的高度
	 * @return
	 */
	public static Bitmap scaleImageTo(Bitmap org, float newWidth, float newHeight) {
		return scaleImage(org, (float) newWidth / org.getWidth(),
				(float) newHeight / org.getHeight());
	}

	/**
	 * 缩放图片
	 * 
	 * @param org
	 *            原图片
	 * @param scaleWidth
	 *            宽度缩放比例
	 * @param scaleHeight
	 *            高度缩放比例
	 * @return
	 */
	public static Bitmap scaleImage(Bitmap org, float scaleWidth,
			float scaleHeight) {
		if (org == null) {
			return null;
		}

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
				matrix, true);
	}

	/**
	 * 关闭InputStream
	 * 
	 * @param s
	 */
	private static void closeInputStream(InputStream s) {
		if (s != null) {
			try {
				s.close();
			} catch (IOException e) {
				throw new RuntimeException("IOException occurred. ", e);
			}
		}
	}
	
	/** 
     * 模糊效果 
     * @param bmp 
     * @return 
     */  
    public static Bitmap blurImage(Bitmap bmp)  
    {  
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int newColor = 0;  
          
        int[][] colors = new int[9][3];  
        for (int i = 1, length = width - 1; i < length; i++)  
        {  
            for (int k = 1, len = height - 1; k < len; k++)  
            {  
                for (int m = 0; m < 9; m++)  
                {  
                    int s = 0;  
                    int p = 0;  
                    switch(m)  
                    {  
                    case 0:  
                        s = i - 1;  
                        p = k - 1;  
                        break;  
                    case 1:  
                        s = i;  
                        p = k - 1;  
                        break;  
                    case 2:  
                        s = i + 1;  
                        p = k - 1;  
                        break;  
                    case 3:  
                        s = i + 1;  
                        p = k;  
                        break;  
                    case 4:  
                        s = i + 1;  
                        p = k + 1;  
                        break;  
                    case 5:  
                        s = i;  
                        p = k + 1;  
                        break;  
                    case 6:  
                        s = i - 1;  
                        p = k + 1;  
                        break;  
                    case 7:  
                        s = i - 1;  
                        p = k;  
                        break;  
                    case 8:  
                        s = i;  
                        p = k;  
                    }  
                    pixColor = bmp.getPixel(s, p);  
                    colors[m][0] = Color.red(pixColor);  
                    colors[m][1] = Color.green(pixColor);  
                    colors[m][2] = Color.blue(pixColor);  
                }  
                  
                for (int m = 0; m < 9; m++)  
                {  
                    newR += colors[m][0];  
                    newG += colors[m][1];  
                    newB += colors[m][2];  
                }  
                  
                newR = (int) (newR / 9F);  
                newG = (int) (newG / 9F);  
                newB = (int) (newB / 9F);  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                  
                newColor = Color.argb(255, newR, newG, newB);  
                bitmap.setPixel(i, k, newColor);  
                  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        return bitmap;  
    }  
      
    /** 
     * 柔化效果(高斯模糊)(优化后比上面快三倍) 
     * @param bmp 
     * @return 
     */  
    public static Bitmap blurImageAmeliorate(Bitmap bmp)  
    {  
        long start = System.currentTimeMillis();  
        // 高斯矩阵  
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);  
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int delta = 16; // 值越小图片会越亮，越大则越暗  
          
        int idx = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                for (int m = -1; m <= 1; m++)  
                {  
                    for (int n = -1; n <= 1; n++)  
                    {  
                        pixColor = pixels[(i + m) * width + k + n];  
                        pixR = Color.red(pixColor);  
                        pixG = Color.green(pixColor);  
                        pixB = Color.blue(pixColor);  
                          
                        newR = newR + (int) (pixR * gauss[idx]);  
                        newG = newG + (int) (pixG * gauss[idx]);  
                        newB = newB + (int) (pixB * gauss[idx]);  
                        idx++;  
                    }  
                }  
                  
                newR /= delta;  
                newG /= delta;  
                newB /= delta;  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                  
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);  
                  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        LogUtil.d("ImageUtils", "blurImageAmeliorate used time="+(end - start));  
        return bitmap;  
    }  

}
