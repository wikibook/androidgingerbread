package kr.co.wikibook.android_application_life_cycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AALCActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i("AALCActivity", "onCreate");
	}
}