package kr.co.wikibook.multiple_activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		Button sa_prev_button = (Button) findViewById(R.id.sa_prev_button);
		Button sa_next_button = (Button) findViewById(R.id.sa_next_button);
		sa_prev_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*
				 * 이전 액티비티로의 이동은 현재 액티비티를 종료하는 것과 같으므로 액티비티를 실행하는 방법 대신에 현재
				 * 액티비티를 종료해야 합니다.
				 */
				/*
				 * Intent intent = new Intent(SecondActivity.this,
				 * FirstActivity.class); startActivity(intent);
				 */
				finish();
			}
		});
		sa_next_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this,
								ThirdActivity.class);
				startActivity(intent);
			}
		});
	}
}