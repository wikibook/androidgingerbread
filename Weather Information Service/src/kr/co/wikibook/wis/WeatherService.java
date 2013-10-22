package kr.co.wikibook.wis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;

import android.os.IBinder;
import android.util.Log;

public class WeatherService extends Service implements Runnable {
	private final IBinder mBinder = new WeatherServiceLocalBinder();
	private String KMA_URL = "http://www.kma.go.kr/weather"
					+ "/forecast/mid-term-xml.jsp?stnId=109";
	public static final String AREA = "area";
	public static final String RESOURCE_IDS = "resource_ids";
	public static final String DATES = "dates";
	public static final String WEATHER_INFORMATION_RECEIVER = "kr.co.wikibook.wis.service.WeatherInformationUpdateEvent";
	public static final String WEATHER_INFORMATION_WIDGET_UPDATE_EVENT = "kr.co.wikibook.wis.service.WeatherInformationUpdateWidgetEvent";
	private Handler mHandler;
	private boolean mRunning;
	private long DELAY_TIME = 5000;
	private int idx = 0;

	public class WeatherServiceLocalBinder extends Binder {
		WeatherService getService() {
			Log.i("JunLog", "WeatherServiceLocalBinder.getService()");
			return WeatherService.this;
		}
	}

	@Override
	public void onCreate() {
		Log.i("JunLog", "WeatherService.onCreate()");
		mHandler = new Handler();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("JunLog", "WeatherService.onStartCommand(), Received start id "
						+ startId + ": " + intent);

		if (!mRunning) {
			mHandler.postDelayed(this, DELAY_TIME);
			mRunning = true;
		} else {
			Log.e("JunLog", "Thread is already running");
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i("JunLog", "WeatherService.onDestroy()");
		mRunning = false;
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("JunLog", "WeatherService.onBind()");
		return mBinder;
	}

	@Override
	public void run() {
		Log.e("JunLog", "WeatherService, In Thread");
		if (mRunning) {
			urlParser();
			mHandler.postDelayed(this, DELAY_TIME);
		}
	}

	private int getProperResourceId(String weather) {
		if (weather.equals("����")) {
			return R.drawable.nb01;
		} else if (weather.equals("��������")) {
			return R.drawable.nb02;
		} else if (weather.equals("��������")) {
			return R.drawable.nb03;
		} else if (weather.equals("�帮�� ��")) {
			return R.drawable.nb04;
		} else {
			return R.drawable.nb01;

		}
	}

	private void urlParser() {
		try {
			URL url = new URL(KMA_URL);
			SAXParserFactory spFactory = SAXParserFactory.newInstance();
			SAXParser sparser = spFactory.newSAXParser();
			XMLReader myReader = sparser.getXMLReader();
			WeatherInformationParser wparser = new WeatherInformationParser();
			myReader.setContentHandler(wparser);
			myReader.parse(new InputSource(url.openStream()));
		} catch (MalformedURLException me) {
			me.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}

	class WeatherInformationParser extends DefaultHandler {
		private int COUNT_OF_DAYS = 6;
		private String BODY_ELEMENT = "body";
		private String CITY_ELEMENT = "city";
		private String WF_ELEMENT = "wf";
		private String TMEF_ELEMENT = "tmEf";
		private String DATA_ELEMENT_NAME = "data";
		private String LOCATION_ELEMENT_NAME = "location";
		boolean inBody = false;
		private String element;

		private StringBuffer areaBuffer;
		private StringBuffer resourceIdBuffer;
		private StringBuffer dateBuffer;
		private int[] resourceIds;
		private int indexOfResourceIds = 0;
		private String[] dates;
		private int indexOfDates = 0;

		public WeatherInformationParser() {
		}

		public void startElement(String uri, String localName, String qName,
						Attributes atts) {
			element = localName;
			if (localName.equals(DATA_ELEMENT_NAME)) {
				resourceIdBuffer = new StringBuffer();
				dateBuffer = new StringBuffer();
			} else if (localName.equals(LOCATION_ELEMENT_NAME)) {
				areaBuffer = new StringBuffer();
				resourceIds = new int[COUNT_OF_DAYS];
				dates = new String[COUNT_OF_DAYS];
				indexOfResourceIds = 0;
				indexOfDates = 0;
			} else if (localName.equals(BODY_ELEMENT)) {
				inBody = true;
			}
		}

		public void endElement(String uri, String localName, String qName) {
			if (localName.equals(DATA_ELEMENT_NAME)) {
				resourceIds[indexOfResourceIds++] = getProperResourceId(resourceIdBuffer
								.toString().trim());
				dates[indexOfDates++] = dateBuffer.toString().trim();
				resourceIdBuffer = null;
				dateBuffer = null;
			} else if (localName.equals(LOCATION_ELEMENT_NAME)) {
				String area = areaBuffer.toString().trim();
				Intent intent = new Intent(WEATHER_INFORMATION_RECEIVER);

				intent.putExtra(AREA, area);
				intent.putExtra(RESOURCE_IDS, resourceIds);
				intent.putExtra(DATES, dates);
				sendBroadcast(intent);
				if (area.equals("����")) {
					Intent intentForWidget = new Intent(WeatherService.this,
									WeatherAppWidgetProvider.class);
					intentForWidget
									.setAction(WEATHER_INFORMATION_WIDGET_UPDATE_EVENT);
					intentForWidget.putExtra(AREA, area);
					intentForWidget.putExtra(RESOURCE_IDS, resourceIds[idx]);
					intentForWidget.putExtra(DATES, dates[idx]);
					sendBroadcast(intentForWidget);
					if (idx == (resourceIds.length - 1)) {
						idx = 0;
					} else {
						idx++;
					}
				}
				areaBuffer = null;
				resourceIds = null;
				dates = null;
			} else if (localName.equals(BODY_ELEMENT)) {
				inBody = false;
			}
		}

		public void characters(char[] chars, int start, int leng) {
			if (!inBody)
				return;
			if (element.equals(CITY_ELEMENT)) {
				areaBuffer.append(chars, start, leng);
			} else if (element.equals(WF_ELEMENT)) {
				resourceIdBuffer.append(chars, start, leng);
			} else if (element.equals(TMEF_ELEMENT)) {
				dateBuffer.append(chars, start, leng);
			}
		}
	}
}