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

		/* üũ�ڽ� ��� */
		CheckBox apple = (CheckBox) findViewById(R.id.apple);
		apple.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
				if (isChecked == true) {
					textview.setText("����� ���õǾ����ϴ�.");
				} else {
					textview.setText("����� ���ܵǾ����ϴ�.");
				}
			}
		});

		/* üũ�ڽ� ���� */
		CheckBox strawberry = (CheckBox) findViewById(R.id.strawberry);
		strawberry.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
				if (isChecked == true) {
					textview.setText("���Ⱑ ���õǾ����ϴ�.");
				} else {
					textview.setText("����� ���ܵǾ����ϴ�.");
				}
			}
		});

		/* üũ�ڽ� �ٳ��� */
		CheckBox banana = (CheckBox) findViewById(R.id.banana);
		banana.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
				if (isChecked == true) {
					textview.setText("�ٳ����� ���õǾ����ϴ�.");
				} else {
					textview.setText("�ٳ����� ���ܵǾ����ϴ�.");
				}
			}
		});
	}
}