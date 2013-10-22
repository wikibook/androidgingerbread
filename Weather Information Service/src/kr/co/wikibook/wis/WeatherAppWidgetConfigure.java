package kr.co.wikibook.wis;

import android.app.Activity;
import android.os.Bundle;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class WeatherAppWidgetConfigure extends Activity {
	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_appwidget_configure);
		// ȯ�漳�� ��Ƽ��Ƽ�� ����� ���¿��� ���ư�� ������ ���� �߰��� ��ҵ� �� �ֵ���
		// RESULT_CANCELED�� �����մϴ�.
		setResult(RESULT_CANCELED);
		// ��ư�� ������ �� ��Ƽ��Ƽ�� �����ϰ� ������ �߰��� �� �ֵ���
		// �����ʸ� ����մϴ�.
		Button configure_to_end_button = (Button) findViewById(R.id.configure_to_end_button);
		configure_to_end_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Make sure we pass back the original appWidgetId
				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
								mAppWidgetId);
				// RESULT_OK�� �������� ������ ��Ƽ��Ƽ�� ����ŵ� ������ �߰����� �ʽ��ϴ�.
				setResult(RESULT_OK, resultValue);
				finish();
			}
		});
		// ����Ʈ�� ���� ������ ���� ���̵� ���� �����ɴϴ�.
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
							AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		// �׷��� ����Ʈ�� ������ ���̵� �������� �ʾҰų�
		// �߸��� ���̵� �����ߴٸ� ������ �߰� ���� ��Ƽ��Ƽ�� �����մϴ�.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
	}
}