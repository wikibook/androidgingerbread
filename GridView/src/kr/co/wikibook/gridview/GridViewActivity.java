package kr.co.wikibook.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.GridView;
import android.content.Context;

public class GridViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new GridViewAdapter(this));
	}

	public class GridViewAdapter extends BaseAdapter {
		private Context mContext;
		private Integer[] mImageResourceIds = { R.drawable.car1,
						R.drawable.car2, R.drawable.car3, R.drawable.car4,
						R.drawable.car5, R.drawable.car6, R.drawable.car1,
						R.drawable.car2, R.drawable.car3, R.drawable.car4,
						R.drawable.car5, R.drawable.car6, R.drawable.car1,
						R.drawable.car2, R.drawable.car3, R.drawable.car4,
						R.drawable.car5, R.drawable.car6
		};

		public GridViewAdapter(Context c) {
			mContext = c;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(75, 75));
				imageView.setAdjustViewBounds(false);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(mImageResourceIds[position]);
			return imageView;
		}

		public int getCount() {
			return mImageResourceIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}
	}
}