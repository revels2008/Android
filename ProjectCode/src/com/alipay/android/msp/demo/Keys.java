/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.alipay.android.msp.demo;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088211222587392";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "fz_plmall@powerlong.com";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANVeFsAeisE6zol7TBwWooWhzdHQswVI7De2+8Z5KICAV1Dc7QU2SwNMO7X1i4IXUvNbkx9G0CYEsEnJhNnFCkTXnTa/jIzC/qvdCRjhdkauaV0wHZK4HjGvcXh06qzIV5xAIMlBfNn3nDtafUf6k1JeiQo9YgdYdm5/0t8M1DWRAgMBAAECgYAutZFPb6A6mvAaAsbvozji/j/7fa+jKYqky8ckdOOb9fyndCXjLTaQu0CbHezzhr2fDt4PS8ZHfGFHVfliXxNXBmtt8nUWHvahJzIjRz2lFclE7BdBz4qDDFAq9vYEWbVBLZDf2w8aE8XWbNCCXgSuq/X52xtet+eulnq4MWtEPQJBAO/7/spcw0IScxXoQ9IbH9FIZCajZn40OnBiyXeIcQ0TCibKmIZpJZJutRgQtYx1l2h13BibBqVAu0WGhSQp3SsCQQDjm1wA6MKGoxjlC+lJpujHWnzJYV9DGPkIphkezgm0EO8MeHpaokV7iYWR3USa/dTuCKfn6MiT1XYqIGicAHIzAkEAoaeLv+Cwnqcy4sTsOnGPAzjSTbyv479mxiGlOGGuVXJH2k2KZLAbYQI19pn60Ty82t7ZfbGfzl1GSNUOhoe0tQJBAJTnH8KshA7HBtNZ/o4jtugs57RrkoH4BXxGBeskSi7WYT2cWBeRT7mpV4v84RQw+aucWBSdMxOcNAkNWMKufAcCQQDW2gLFfTwFdN9viAY5NUprPqZPPUnWpClJcu+g38tPEyJH+vO5ZQt51YXLd6qa4eMYt6GanXdY5RYGouTGC4sI";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
