package kr.co.wikibook.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;
import android.util.Log;

public class SpinnerActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
						this, R.array.singer,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
				Log.i("SpinnerActivity", "position = " + position + ", id = " + id);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				Log.i("SpinnerActivity", "선택된 가수가 없습니다.");
			}
		});
	}
}