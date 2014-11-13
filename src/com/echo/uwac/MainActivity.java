package com.echo.uwac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity{

	private EditText acountEditText;
	private EditText passwordEditText;

	SharedPreferences mSharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		acountEditText = (EditText) findViewById(R.id.accountEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		
		mSharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		String account = mSharedPreferences.getString(Constant.ACOUNT, "");
		String password = mSharedPreferences.getString(Constant.PASSWORD, "");
		
		acountEditText.setText(account);
		passwordEditText.setText(password);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public void onConnectButtonClick(View view) {
		String account = acountEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		
		if ((account != null && account.length() == 11)
				&& (password != null)) {
			Editor editor = mSharedPreferences.edit();
			editor.putString(Constant.ACOUNT, account);
			editor.putString(Constant.PASSWORD, password);
			editor.commit();
	
			Intent intent = new Intent(this, UWlanConnetService.class);
			startService(intent);
		}else {
			Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_LONG).show();
		}
	}
	
}
