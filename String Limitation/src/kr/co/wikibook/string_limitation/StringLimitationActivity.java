package kr.co.wikibook.string_limitation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StringLimitationActivity extends Activity {
	private TextView word_count = null;
	private EditText message = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		message = (EditText) findViewById(R.id.message);
		word_count = (TextView) findViewById(R.id.word_count);

		TextWatcher watcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
							int count) {
				int cnt = s.toString().length();
				word_count.setText(String.valueOf(cnt));
			}

			public void afterTextChanged(Editable s) {
			}
		};
		message.addTextChangedListener(watcher);
		Button show_keyboard = (Button) findViewById(R.id.show_keyboard);
		Button hide_keyboard = (Button) findViewById(R.id.hide_keyboard);
		show_keyboard.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(message, 0);
			}
		});
		hide_keyboard.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		});
	}
}
