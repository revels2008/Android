/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * MD5Utils.java
 * 
 * 2013-11-1-上午10:51:52
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * MD5Utils
 * 
 * @author: hegao
 * 2013-11-1-上午10:51:52
 * 
 * @version 1.0.0
 * 
 */
public class MD5Utils {
	public static String getMd5Str(String str){

		    byte[] hash;

		    try {

		        hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));

		    } catch (NoSuchAlgorithmException e) {

		        throw new RuntimeException("Huh, MD5 should be supported?", e);

		    } catch (UnsupportedEncodingException e) {

		        throw new RuntimeException("Huh, UTF-8 should be supported?", e);

		    }



		    StringBuilder hex = new StringBuilder(hash.length * 2);

		    for (byte b : hash) {

		        if ((b & 0xFF) < 0x10) hex.append("0");

		        hex.append(Integer.toHexString(b & 0xFF));

		    }

		    return hex.toString();

	}
}
