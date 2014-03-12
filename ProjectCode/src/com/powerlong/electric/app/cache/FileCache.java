/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * FileCache.java
 * 
 * 2013-9-22-下午02:20:19
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.powerlong.electric.app.utils.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * FileCache 图片文件缓存
 * 
 * @author: Liang Wang 2013-9-22-下午02:20:19
 * 
 * @version 1.0.0
 * 
 */
public class FileCache {

	private File cacheDir;
	private Context mContext = null;
	private String mDirName = "";

	public FileCache(Context context,String dirName) {
		// 如果有SD卡则在SD卡中建一个LazyList的目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		mDirName = dirName;
		
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),"powerlong/"+
					dirName);
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();

		try {
			new File(cacheDir.getAbsolutePath(),".nomedia").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mContext = context;
	}

	public File getFile(String url) {
		// 将url的hashCode作为缓存的文件名
		//String filename = String.valueOf(url.hashCode());
		// Another possible solution
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, url);
		return f;
	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

	// decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	public Bitmap decodeFile(File f) {
		try {
			// decode image size
			/*BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = 1;*/
			 BitmapFactory.Options opts = new BitmapFactory.Options();  
			 opts.inPreferredConfig = Bitmap.Config.RGB_565;   
		       opts.inPurgeable = true;  
		       opts.inInputShareable = true;  
			return BitmapFactory.decodeStream(new FileInputStream(f), null, opts);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 将bitmap写入SD卡
	 * 
	 * @param cacheKey
	 *            filename
	 * @param response
	 *            bitmap void
	 * @exception
	 * @since 1.0.0
	 */
	public void WriteBitmapToFile(String cacheKey, Bitmap response) {
		try {

		/*	FileOutputStream fout = mContext.openFileOutput(cacheKey,
					Context.MODE_PRIVATE);*/
			File file = new File(cacheDir,cacheKey);
			if(!file.exists())
				file.createNewFile();
			
			FileOutputStream fout = new FileOutputStream(file);

			byte[] bytes = ImageUtil.bitmapToByte(response);

			fout.write(bytes);

			fout.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}
	}
}
