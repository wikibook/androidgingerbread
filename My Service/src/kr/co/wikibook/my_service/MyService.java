package kr.co.wikibook.my_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	private String TAG = "MyService";

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand, Received start id " + startId + ": "
						+ intent);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return null;
	}
}