package kr.co.wikibook.activity_life_cycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends Activity {
	private String TAG = "ActivityLifeCycle";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "SecondActivity/onCreate()");
		TextView textview = new TextView(this);
		textview.setText("SecondActivity !!");
		setContentView(textview);
	}
}