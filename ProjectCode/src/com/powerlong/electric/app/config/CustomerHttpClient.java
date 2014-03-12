/**
 * 宝龙电商
 * com.powerlong.electric.app.config
 * CustomerHttpClient.java
 * 
 * 2013年11月25日-下午4:40:11
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.config;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * 
 * CustomerHttpClient
 * 
 * @author: YangCheng Miao
 * 2013年11月25日-下午4:40:11
 * 
 * @version 1.0.0
 * 
 */
public class CustomerHttpClient {
    private static final String CHARSET = HTTP.UTF_8;
    private static HttpClient customerHttpClient;

    private CustomerHttpClient() {
    }

    public static synchronized HttpClient getHttpClient(HttpParams params, SchemeRegistry schReg) {
        if (null == customerHttpClient) {
//            HttpParams params = new BasicHttpParams();
            // 设置一些基本参数
            
            HttpProtocolParams.setContentCharset(params,
                    CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
           
            // 使用线程安全的连接管理来创建HttpClient
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                    params, schReg);
            customerHttpClient = new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }
}
