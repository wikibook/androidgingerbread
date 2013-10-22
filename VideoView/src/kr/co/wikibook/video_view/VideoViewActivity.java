package kr.co.wikibook.video_view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		VideoView vv = (VideoView) findViewById(R.id.video_view);
		String path = Environment.getExternalStorageDirectory().getPath()
						+ "/test.3gp";
		if (path != "") {
			vv.setVideoPath(path);
			vv.setMediaController(new MediaController(this));
			vv.start();
		}
	}
}