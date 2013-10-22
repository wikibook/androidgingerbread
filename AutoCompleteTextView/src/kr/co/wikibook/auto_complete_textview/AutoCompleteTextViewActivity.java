package kr.co.wikibook.auto_complete_textview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutoCompleteTextViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		AutoCompleteTextView actextview = (AutoCompleteTextView) findViewById(R.id.actextview);

		String[] words = { "apple", "appose", "application", "hero", "hello" };
		actextview.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_dropdown_item_1line, words));
	}
}