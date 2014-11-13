package com.echo.uwac.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpUtil {

	private static final String UWLAN_CONNECT_GET_URL = "http://202.106.46.37/login.do?callback=jQuery1710080550838141344_1415842861441&username=18600933359&password=506086&passwordType=6&wlanuserip=&userOpenAddress=bj&checkbox=1&basname=WLAN-AH-7750SR&setUserOnline=&sap=&macAddr=&bandMacAuth=0&isMacAuth=&basPushUrl=http%253A%252F%252F202.106.46.37%252F%2523%253FWLAN-AH-7750SR%253DWLAN-AH-7750SR&passwordkey=&_=14158428854";

	/**
	 * only return the status code
	 * @param url
	 * @return
	 */
	private static int httpGet(String url){
		int statusCode = 0;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			statusCode = response.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return statusCode;
	}
	
	public static boolean checkNetStatus(){
		boolean status = false;
		int statusCode = httpGet("http://www.baidu.com");
		if (statusCode == HttpStatus.SC_OK) {
			status = true;
			Log.d("jyj", "jyj checkNetStatus: " + status);
		}
		return status;
	}

	public static boolean connect(String acount, String password, int maxRetryCount, int retryInterval){
		int retryCount = 0;
		boolean isConnected = HttpUtil.checkNetStatus();
		StringBuffer sb = new StringBuffer();
		sb.append("http://202.106.46.37/login.do?callback=jQuery1710080550838141344_1415842861441&");
		sb.append("username=");
		sb.append(acount);
		sb.append("&password=");
		sb.append(password);
		sb.append("&passwordType=6&wlanuserip=&userOpenAddress=bj&checkbox=1&basname=WLAN-AH-7750SR&setUserOnline=&sap=&macAddr=&bandMacAuth=0&isMacAuth=&basPushUrl=http%253A%252F%252F202.106.46.37%252F%2523%253FWLAN-AH-7750SR%253DWLAN-AH-7750SR&passwordkey=&_=14158428854");
		while(!isConnected && retryCount < maxRetryCount) {
			int statusCode;
			statusCode = HttpUtil.httpGet(UWLAN_CONNECT_GET_URL);
			Log.d("jyj", "jyj statusCode " + statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				isConnected = true;
				retryCount = 0;
				break;
			}else {
				try {
					Thread.sleep(retryInterval);
					Log.d("jyj", "jyj retry: " + retryCount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				retryCount ++;
			}
		}
		
		return isConnected;
	}
}
