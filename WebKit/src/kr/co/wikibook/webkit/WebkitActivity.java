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
						+ "<body><font color=blue face=12>loadData()�� ����ϸ� "
						+ "HTML �±� ���ڿ��� ���信 ���� ������ "
						+ "�� �� �ֽ��ϴ�.</font></body></html>";
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.loadData(tags, "text/html", "UTF-8");
		//webview.loadUrl("http://m.daum.net");
	}
}