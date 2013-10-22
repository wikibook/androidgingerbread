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
				 * ���� ��Ƽ��Ƽ���� �̵��� ���� ��Ƽ��Ƽ�� �����ϴ� �Ͱ� �����Ƿ� ��Ƽ��Ƽ�� �����ϴ� ��� ��ſ� ����
				 * ��Ƽ��Ƽ�� �����ؾ� �մϴ�.
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