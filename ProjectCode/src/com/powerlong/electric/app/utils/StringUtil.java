/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * StringUtil.java
 * 
 * 2013-8-7-下午04:27:21
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.telephony.PhoneNumberUtils;

import com.powerlong.electric.app.config.Constants;

/**
 * 
 * StringUtil： 字符串工具类，用于实现一些字符串的常用操作
 * 
 * @author: Liang Wang 2013-8-7-下午04:27:21
 * 
 * @version 1.0.0
 * 
 */
public class StringUtil {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	
	/**
	 * 判断是否为空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || "".equals(text.trim()) || text.trim().length() == 0 || "null".equals(text.trim())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		if (isEmpty(sdate))
			return null;
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 两种方法结合判断手机号码合法性
	 * 
	 * @param number
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean isPhoneNumber(String number) {
		if (Pattern.compile("(\\d{11})|(\\+\\d{3,})").matcher(number).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是不是一个有效的手机号码
	 * 
	 * @param number
	 * @return
	 */
	private static boolean isPhoneNumberMethod(String number) {
		boolean isValid = false;

		/*
		 * 可接受的电话格式有:
		 * 
		 * ^//(? : 可以使用 "(" 作为开头
		 * 
		 * (//d{3}): 紧接着三个数字
		 * 
		 * //)? : 可以使用")"接续
		 * 
		 * [- ]? : 在上述格式后可以使用具选择性的 "-".
		 * 
		 * (//d{4}) : 再紧接着三个数字
		 * 
		 * [- ]? : 可以使用具选择性的 "-" 接续.
		 * 
		 * (//d{4})$: 以四个数字结束.
		 * 
		 * 可以比较下列数字格式:
		 * 
		 * (123)456-78900, 123-4560-7890, 12345678900, (123)-4560-7890
		 */

		String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";

		String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";

		CharSequence inputStr = number;

		/* 创建Pattern */

		Pattern pattern = Pattern.compile(expression);

		/* 将Pattern 以参数传入Matcher作Regular expression */

		Matcher matcher = pattern.matcher(inputStr);

		/* 创建Pattern2 */

		Pattern pattern2 = Pattern.compile(expression2);

		/* 将Pattern2 以参数传入Matcher2作Regular expression */

		Matcher matcher2 = pattern2.matcher(inputStr);

		if (matcher.matches() || matcher2.matches())

		{

			isValid = true;

		}

		return isValid;

	}

	/**
	 * 判别手机是否为正确手机号码； 号码段分配如下：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
	 */
	private static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 比较两个String
	 * 
	 * @param actual
	 * @param expected
	 * @return <ul>
	 *         <li>若两个字符串都为null，则返回true</li>
	 *         <li>若两个字符串都不为null，且相等，则返回true</li>
	 *         <li>否则返回false</li>
	 *         </ul>
	 */
	public static boolean isEquals(String actual, String expected) {
		return ObjectUtil.isEquals(actual, expected);
	}

	/**
	 * null字符串转换为长度为0的字符串
	 * 
	 * @param str
	 *            待转换字符串
	 * @return
	 * @see <pre>
	 * nullStrToEmpty(null) = &quot;&quot;;
	 * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
	 * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
	 * </pre>
	 */
	public static String nullStrToEmpty(String str) {
		return (str == null ? "" : str);
	}

	/**
	 * 将字符串首字母大写后返回
	 * 
	 * @param str
	 *            原字符串
	 * @return 首字母大写后的字符串
	 * 
	 *         <pre>
	 *      capitalizeFirstLetter(null)     =   null;
	 *      capitalizeFirstLetter("")       =   "";
	 *      capitalizeFirstLetter("2ab")    =   "2ab"
	 *      capitalizeFirstLetter("a")      =   "A"
	 *      capitalizeFirstLetter("ab")     =   "Ab"
	 *      capitalizeFirstLetter("Abc")    =   "Abc"
	 * </pre>
	 */
	public static String capitalizeFirstLetter(String str) {
		if (isEmpty(str)) {
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
				: new StringBuilder(str.length())
						.append(Character.toUpperCase(c))
						.append(str.substring(1)).toString();
	}

	/**
	 * 如果不是普通字符，则按照utf8进行编码
	 * 
	 * <pre>
	 * utf8Encode(null)        =   null
	 * utf8Encode("")          =   "";
	 * utf8Encode("aa")        =   "aa";
	 * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
	 * </pre>
	 * 
	 * @param str
	 *            原字符
	 * @return 编码后字符，若编码异常抛出异常
	 */
	public static String utf8Encode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * 如果不是普通字符，则按照utf8进行编码，编码异常则返回defultReturn
	 * 
	 * @param str
	 *            源字符串
	 * @param defultReturn
	 *            出现异常默认返回
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return defultReturn;
			}
		}
		return str;
	}

	/**
	 * 得到href链接的innerHtml
	 * 
	 * @param href
	 *            href内容
	 * @return href的innerHtml
	 *         <ul>
	 *         <li>空字符串返回""</li>
	 *         <li>若字符串不为空，且不符合链接正则的返回原内容</li>
	 *         <li>若字符串不为空，且符合链接正则的返回最后一个innerHtml</li>
	 *         </ul>
	 * @see <pre>
	 *      getHrefInnerHtml(null)                                  = ""
	 *      getHrefInnerHtml("")                                    = ""
	 *      getHrefInnerHtml("mp3")                                 = "mp3";
	 *      getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
	 *      getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 *      getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 *      getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
	 *      getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
	 *      getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
	 *      getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
	 *      getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
	 *      getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
	 * </pre>
	 */
	public static String getHrefInnerHtml(String href) {
		if (isEmpty(href)) {
			return "";
		}
		String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
		Pattern hrefPattern = Pattern
				.compile(hrefReg, Pattern.CASE_INSENSITIVE);
		Matcher hrefMatcher = hrefPattern.matcher(href);
		if (hrefMatcher.matches()) {
			return hrefMatcher.group(1);
		}
		return href;
	}

/**
     * html的转义字符转换成正常的字符串
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
	public static String htmlEscapeCharsToString(String source) {
		if (StringUtil.isEmpty(source)) {
			return source;
		} else {
			return source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
		}
	}

	/**
	 * 半角字符转换为全角字符
	 * 
	 * <pre>
	 * fullWidthToHalfWidth(null) = null;
	 * fullWidthToHalfWidth("") = "";
	 * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
	 * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String fullWidthToHalfWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == 12288) {
				source[i] = ' ';
				// } else if (source[i] == 12290) {
				// source[i] = '.';
			} else if (source[i] >= 65281 && source[i] <= 65374) {
				source[i] = (char) (source[i] - 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * 全角字符转换为半角字符
	 * 
	 * <pre>
	 * halfWidthToFullWidth(null) = null;
	 * halfWidthToFullWidth("") = "";
	 * halfWidthToFullWidth(" ") = new String(new char[] {12288});
	 * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String halfWidthToFullWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == ' ') {
				source[i] = (char) 12288;
				// } else if (source[i] == '.') {
				// source[i] = (char)12290;
			} else if (source[i] >= 33 && source[i] <= 126) {
				source[i] = (char) (source[i] + 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * replaceTag(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param loginUrl
	 * @param strings
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static String replaceTag(String oldChar, String[] newChar) {
		for (String s : newChar) {
			LogUtil.d("StringUtil", "s =" + s);
			oldChar = oldChar.replaceFirst(Constants.replaceTag, s);
			LogUtil.d("StringUtil", "oldChar =" + oldChar);
		}
		return oldChar;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static double toDouble(Object obj) {
		if (obj == null)
			return 0;
		return toDouble(obj.toString(), -1);
	}

	/**
	 * 字符串转双精度整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	private static double toDouble(String str, double defValue) {
		try {
			return Double.valueOf(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 将long型转换为字符串
	 * 
	 * @param value
	 *            要转换的long
	 * @exception
	 * @since 1.0.0
	 */
	public static String convertLongToString(long value) {
		return Long.toString(value);
	}

	/**
	 * 将double型转换为字符串
	 * 
	 * @param value
	 *            要转换的long
	 * @exception
	 * @since 1.0.0
	 */
	public static String convertDoubleToString(double value) {
		return Double.toString(value);
	}

	/**
	 * 将int型转换为字符串
	 * 
	 * @param value
	 *            要转换的long
	 * @exception
	 * @since 1.0.0
	 */
	public static String convertIntToString(int value) {
		return Integer.toString(value);
	}
	
	/**
	 * 获取括号中的国家区号
	 * 
	 * @param text
	 *            带有括号的国家区号
	 * @param defaultText
	 *            默认的国家区号(在获取错误时返回该值)
	 * @return
	 */
	public static String getCountryCodeBracketsInfo(String text,
			String defaultText) {
		if (text.contains("(") && text.contains(")")) {
			int leftBrackets = text.indexOf("(");
			int rightBrackets = text.lastIndexOf(")");
			if (leftBrackets < rightBrackets) {
				return "+" + text.substring(leftBrackets + 1, rightBrackets);
			}
		}
		if (defaultText != null) {
			return defaultText;
		} else {
			return text;
		}
	}
	
	/**
	 * 获取括号中的国家
	 * 
	 * @param text
	 *            带有括号的国家区号
	 * @param defaultText
	 *            默认的国家(在获取错误时返回该值)
	 * @return
	 */
	public static String getCountryNameBracketsInfo(String text,
			String defaultText) {
		if (text.contains("(") && text.contains(")")) {
			int leftBrackets = text.indexOf("(");
			int rightBrackets = text.lastIndexOf(")");
			if (leftBrackets < rightBrackets) {
				return text.substring(0, leftBrackets);
			}
		}
		if (defaultText != null) {
			return defaultText;
		} else {
			return text;
		}
	}
	
	/**
	 * 
	 * 获取首页广告位链接参数
	 * @param text
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getAdvertiseLinkDataInfo(String text){
		if (text.contains("{") && text.contains("}")) {
			int leftBrackets = text.indexOf("{");
			int rightBrackets = text.lastIndexOf("}");
			if (leftBrackets < rightBrackets) {
				if (leftBrackets < rightBrackets) {
					text = text.substring(leftBrackets, rightBrackets+1);
					LogUtil.d("getAdvertiseLinkDataInfo", "getAdvertiseLinkDataInfo text="+text);
					return text;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 判断字符串是否是有效的URL
	 * @param url
	 * @return 
	 *boolean  true:有效  false:无效
	 * @exception 
	 * @since  1.0.0
	 */
	public static boolean isValidUrl(String url){
		return !StringUtil.isEmpty(url) && Uri.parse(url).getHost() != null;
	}
	
	public static String formatDuring(long mss) {  
	    long days = mss / (1000 * 60 * 60 * 24);  
	    long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	    long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (mss % (1000 * 60)) / 1000;  
	    return days + " 天 " + hours + " 小时 " + minutes + " 分 "  
	            + seconds + " 秒 ";  
	}  

}
