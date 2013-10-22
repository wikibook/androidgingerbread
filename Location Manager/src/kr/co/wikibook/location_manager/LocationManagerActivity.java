package kr.co.wikibook.location_manager;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationManagerActivity extends Activity {
	LocationListener mLocationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (location != null) {
					Toast.makeText(
									getBaseContext(),
									"위도는 " + location.getLatitude() + ", "
													+ "경도는 "
													+ location.getLongitude()
													+ " 입니다.",
									Toast.LENGTH_SHORT).show();
				}
			}

			public void onProviderDisabled(String arg0) {
				Toast.makeText(getBaseContext(), "provider disabled " + arg0,
								Toast.LENGTH_SHORT).show();
			}

			public void onProviderEnabled(String arg0) {
				Toast.makeText(getBaseContext(), "provider enabled " + arg0,
								Toast.LENGTH_SHORT).show();
			}

			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				Toast.makeText(getBaseContext(),
								"GPS 상태가 변경되었습니다.\n" + arg0 + ", " + arg1 + "",
								Toast.LENGTH_SHORT).show();
			}
		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5,
						mLocationListener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lm.removeUpdates(mLocationListener);
	}
}