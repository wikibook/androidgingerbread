package kr.co.wikibook.my_service;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

public class MyServiceActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this, MyService.class);
		startService(intent);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		stopService(intent);
	}
}