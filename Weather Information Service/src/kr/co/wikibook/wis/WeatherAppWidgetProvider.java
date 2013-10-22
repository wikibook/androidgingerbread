package kr.co.wikibook.wis;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WeatherAppWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
					int[] appWidgetIds) {
    Log.i("Jun", "onUpdate !!!");
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
    Log.i("Jun", "onDeleted !!!");
	}

	@Override
	public void onEnabled(Context context) {
    Log.i("Jun", "onEnabled !!!");
	}

	@Override
	public void onDisabled(Context context) {
    Log.i("Jun", "onDisabled !!!");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
    Log.i("Jun", "onReceive !!!");
		if (intent.getAction().equals(
						WeatherService.WEATHER_INFORMATION_WIDGET_UPDATE_EVENT)) {
			String area = intent.getStringExtra(WeatherService.AREA);
			String date = intent.getStringExtra(WeatherService.DATES);

			int rId = intent.getIntExtra(WeatherService.RESOURCE_IDS, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(),
							R.layout.weather_appwidget);
			views.setTextViewText(R.id.area, area);
			views.setTextViewText(R.id.date, date);
			views.setImageViewResource(R.id.weather_icon, rId);
			ComponentName cn = new ComponentName(context,
							WeatherAppWidgetProvider.class);
			AppWidgetManager.getInstance(context).updateAppWidget(cn, views);
		} else {
			super.onReceive(context, intent);
		}
	}
}