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
				 * ���� ��Ƽ��Ƽ���� �̵��� ���� ��Ƽ��Ƽ�� �����ϴ� �Ͱ� �����Ƿ� ��Ƽ��Ƽ�� �����ϴ� ��� ��ſ� ����
				 * ��Ƽ��Ƽ�� �����ؾ� �մϴ�.
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