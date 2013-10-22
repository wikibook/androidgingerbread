package kr.co.wikibook.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckBoxActivity extends Activity {
	TextView textview = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textview = (TextView) findViewById(R.id.message);

		/* 체크박스 사과 */
		CheckBox apple = (CheckBox) findViewById(R.id.apple);
		apple.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
				if (isChecked == true) {
					textview.setText("사과가 선택되었습니다.");
				} else {
					textview.setText("사과는 제외되었습니다.");
				}
			}
		});

		/* 체크박스 딸기 */
		CheckBox strawberry = (CheckBox) findViewById(R.id.strawberry);
		strawberry.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
				if (isChecked == true) {
					textview.setText("딸기가 선택되었습니다.");
				} else {
					textview.setText("딸기는 제외되었습니다.");
				}
			}
		});

		/* 체크박스 바나나 */
		CheckBox banana = (CheckBox) findViewById(R.id.banana);
		banana.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
				if (isChecked == true) {
					textview.setText("바나나가 선택되었습니다.");
				} else {
					textview.setText("바나나는 제외되었습니다.");
				}
			}
		});
	}
}