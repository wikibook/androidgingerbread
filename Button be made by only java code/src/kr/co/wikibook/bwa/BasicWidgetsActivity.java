package kr.co.wikibook.bwa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

public class BasicWidgetsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		LayoutParams lpFillWrap = new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT);
		Button button = new Button(this);
		button.setText("자바코드로 생성한 버튼");
		layout.addView(button, lpFillWrap);
		setContentView(layout);
	}
}