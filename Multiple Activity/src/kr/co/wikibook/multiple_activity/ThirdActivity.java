package kr.co.wikibook.multiple_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThirdActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);
		Button ta_prev_button = (Button) findViewById(R.id.ta_prev_button);
		ta_prev_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*
				 * 이전 액티비티로의 이동은 현재 액티비티를 종료하는 것과 같으므로 액티비티를 실행하는 방법 대신에 현재
				 * 액티비티를 종료해야 합니다.
				 */
				/*
				 * Intent intent = new Intent(ThirdActivity.this,
				 * SecondActivity.class); startActivity(intent);
				 */
				finish();
			}
		});
	}
}