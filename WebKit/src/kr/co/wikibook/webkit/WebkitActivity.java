package kr.co.wikibook.webkit;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebkitActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String tags = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>"
						+ "<body><font color=blue face=12>loadData()를 사용하면 "
						+ "HTML 태그 문자열을 웹뷰에 쉽게 렌더링 "
						+ "할 수 있습니다.</font></body></html>";
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.loadData(tags, "text/html", "UTF-8");
		//webview.loadUrl("http://m.daum.net");
	}
}