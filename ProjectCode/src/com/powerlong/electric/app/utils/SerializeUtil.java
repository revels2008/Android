/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * SerializeUtil.java
 * 
 * 2013-8-7-下午04:46:55
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * SerializeUtil： 序列化工具类
 * 
 * @author: Liang Wang 2013-8-7-下午04:46:55
 * 
 * @version 1.0.0
 * 
 */
public class SerializeUtil {
	/**
	 * 反序列化
	 * 
	 * @param filePath
	 *            序列化文件路径
	 * @return 得到的对象
	 */
	public static Object deserialization(String filePath) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(filePath));
			Object o = in.readObject();
			in.close();
			return o;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ClassNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 序列化
	 * 
	 * @param filePath
	 *            序列化文件路径
	 * @param obj
	 *            序列化的对象
	 * @return
	 */
	public static void serialization(String filePath, Object obj) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(obj);
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}
}
