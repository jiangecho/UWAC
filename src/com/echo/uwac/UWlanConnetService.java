package com.echo.uwac;

import com.echo.uwac.util.HttpUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UWlanConnetService extends Service{

	private Service mService;
	private ConnectTask mConnectTask;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("jyj", "jyj service oncreate");
		mService = this;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("jyj", "jyj service onStartCommand");
		if (mConnectTask == null) {
			String[] acountAndPassword = new String[2];
			SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
			acountAndPassword[0] = sharedPreferences.getString(Constant.ACOUNT, "18600933359");
			acountAndPassword[1] = sharedPreferences.getString(Constant.PASSWORD, "506086");
			mConnectTask = new ConnectTask();
			mConnectTask.execute(acountAndPassword);
		}
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("jyj", "jyj service destroyed");
	}
	
	private class ConnectTask extends AsyncTask<String, Void, Boolean>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... acountAndPassword) {
			boolean isConnected = HttpUtil.connect(acountAndPassword[0], acountAndPassword[1], 100, 3000);
			Log.d("jyj", "jyj isConnected: " + isConnected);

			return isConnected;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Toast.makeText(UWlanConnetService.this, "Connected: " + result, Toast.LENGTH_LONG).show();
			mConnectTask = null;
			mService.stopSelf();
		}
		
	}
}
