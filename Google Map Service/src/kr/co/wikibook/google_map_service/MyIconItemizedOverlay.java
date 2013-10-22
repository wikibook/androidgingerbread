package kr.co.wikibook.google_map_service;

import java.util.ArrayList;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyIconItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public MyIconItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlays.get(arg0);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlayItem(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
}