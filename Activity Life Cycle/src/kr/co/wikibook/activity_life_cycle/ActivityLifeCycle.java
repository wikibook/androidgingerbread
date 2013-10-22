package kr.co.wikibook.activity_life_cycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityLifeCycle extends Activity {
	private String TAG = "ActivityLifeCycle";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i(TAG, "onCreate()");

		TextView text_view = (TextView) findViewById(R.id.text_view);
		text_view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActivityLifeCycle.this,
								SecondActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart()");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop()");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy()");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
	}
}