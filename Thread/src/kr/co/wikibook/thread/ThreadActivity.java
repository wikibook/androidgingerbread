package kr.co.wikibook.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadActivity extends Activity {
	private ProgressBar mProgress;
	private TextView textview;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			textview.setText("������� " + "�츮�� �ؽ�Ʈ���� ���ڿ��� " + "�����ϴ� �� ū ������ "
							+ "���� �ʽ��ϴ�.");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mProgress = (ProgressBar) findViewById(R.id.progressbar);
		textview = (TextView) findViewById(R.id.textview);
		new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (true) {
					if (i > 100) {
						Message message = handler.obtainMessage();
						handler.sendMessage(message);
			      /*textview.setText("������� " + "�츮�� �ؽ�Ʈ���� ���ڿ��� " + "�����ϴ� �� ū ������ "
	              + "���� �ʽ��ϴ�.");*/
						break;
					} else {
						mProgress.setProgress(i);
					}
					try {
						Thread.sleep(1000);
						i += 10;
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
		}).start();
	}
}