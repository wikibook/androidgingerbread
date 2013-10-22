package kr.co.wikibook.google_map_service;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.content.Context;
import android.widget.Toast;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import java.util.List;

public class GoogleMapServiceActivity extends MapActivity {
  LocationListener mLocationListener;
  MapView mapView;
  MapController mc;
  List<Overlay> overlay;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mapView = (MapView) findViewById(R.id.mapview);
    mapView.setBuiltInZoomControls(true);
    mapView.setSatellite(true);
    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    mc = mapView.getController();
    mLocationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        if (location != null) {
          Toast.makeText(
              getBaseContext(),
              "위도는 " + location.getLatitude() + ", " + "경도는 "
                  + location.getLongitude() + " 입니다.", Toast.LENGTH_SHORT)
              .show();
          GeoPoint gp = new GeoPoint((int) (location.getLatitude() * 1000000),
              (int) (location.getLongitude() * 1000000));
          mc.animateTo(gp);
          mc.setZoom(14);
          Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
              R.drawable.comment_write);
          bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
          Drawable drawable = new BitmapDrawable(bitmap);
          MyIconItemizedOverlay mdio = new MyIconItemizedOverlay(drawable);
          OverlayItem overlayitem = new OverlayItem(gp, "", "");
          mdio.addOverlayItem(overlayitem);
          overlay = mapView.getOverlays();
          overlay.add(mdio);
        }
      }

      public void onProviderDisabled(String arg0) {
      }

      public void onProviderEnabled(String arg0) {
      }

      public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
      }
    };
    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2, 3,
        mLocationListener);
  }

  @Override
  protected boolean isRouteDisplayed() {
    return false;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    lm.removeUpdates(mLocationListener);
  }
}