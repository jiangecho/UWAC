package com.echo.uwac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WlanStateReceiver extends BroadcastReceiver{

	private static final String UWLAN_SSID = "ChinaUnicom";
	@Override
	public void onReceive(Context context, Intent intent) {
		if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			String ssidString = wifiInfo.getSSID();
			Log.d("jyj", "jyj " + ssidString + " " + wifiManager.getWifiState());
			
			int state = wifiManager.getWifiState();
			
			if (state == WifiManager.WIFI_STATE_ENABLED 
					&& ssidString.contains(UWLAN_SSID)) {
				Log.d("jyj", "jyj startService");
				Intent service = new Intent(context, UWlanConnetService.class);
				context.startService(service);
			}
			
		}
	}

}
