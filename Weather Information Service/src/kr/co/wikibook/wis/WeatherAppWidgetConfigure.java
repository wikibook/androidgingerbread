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
		// 환경설정 액티비티가 실행된 상태에서 백버튼을 누르면 위젯 추가가 취소될 수 있도록
		// RESULT_CANCELED를 설정합니다.
		setResult(RESULT_CANCELED);
		// 버튼을 눌렀을 때 액티비티를 종료하고 위젯을 추가할 수 있도록
		// 리스너를 등록합니다.
		Button configure_to_end_button = (Button) findViewById(R.id.configure_to_end_button);
		configure_to_end_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Make sure we pass back the original appWidgetId
				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
								mAppWidgetId);
				// RESULT_OK로 설정하지 않으면 액티비티가 종료돼도 위젯은 추가되지 않습니다.
				setResult(RESULT_OK, resultValue);
				finish();
			}
		});
		// 인텐트를 통해 위젯의 고유 아이디 값을 가져옵니다.
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
							AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		// 그래서 인텐트가 위젯의 아이디를 전달하지 않았거나
		// 잘못된 아이디를 전달했다면 위젯의 추가 없이 액티비티를 종료합니다.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
	}
}