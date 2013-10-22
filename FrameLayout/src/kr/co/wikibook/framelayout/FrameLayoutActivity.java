package kr.co.wikibook.framelayout;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class FrameLayoutActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TabHost tabhost = getTabHost();
		tabhost.addTab(tabhost.newTabSpec("first_tab").setIndicator("Ã¹ ¹øÂ° ÅÇ")
						.setContent(R.id.first));
		tabhost.addTab(tabhost.newTabSpec("second_tab").setIndicator("µÎ ¹øÂ° ÅÇ")
						.setContent(R.id.second));
		tabhost.addTab(tabhost.newTabSpec("third_tab").setIndicator("¼¼ ¹øÂ° ÅÇ")
						.setContent(R.id.third));
		tabhost.setCurrentTab(0);
	}
}