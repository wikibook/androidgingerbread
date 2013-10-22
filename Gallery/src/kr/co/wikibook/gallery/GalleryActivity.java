package kr.co.wikibook.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GalleryActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private Integer[] mImageIds = { R.drawable.car1, R.drawable.car2,
						R.drawable.car3, R.drawable.car4, R.drawable.car5,
						R.drawable.car6, R.drawable.car1, R.drawable.car2,
						R.drawable.car3, R.drawable.car4, R.drawable.car5,
						R.drawable.car6, R.drawable.car1, R.drawable.car2,
						R.drawable.car3, R.drawable.car4, R.drawable.car5,
						R.drawable.car6 };

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(mContext);
			iv.setImageResource(mImageIds[position]);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(200, 140));
			return iv;
		}
	}
}