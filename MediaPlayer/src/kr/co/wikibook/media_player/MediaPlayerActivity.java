package kr.co.wikibook.media_player;

import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MediaPlayerActivity extends Activity implements Runnable {
	private MediaPlayer mp;
	private String SDCARD_PATH = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
	private String mMp3FilePath;
	private ProgressBar mProgressBar;
	private Button play_button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mMp3FilePath = SDCARD_PATH + "/song.mp3";
		mp = new MediaPlayer();
		try {
			mp.setDataSource(mMp3FilePath);
			mp.prepare();
			play_button = (Button) findViewById(R.id.play_button);
			Button stop_button = (Button) findViewById(R.id.stop_button);
			Button prev_button = (Button) findViewById(R.id.prev_button);
			Button next_button = (Button) findViewById(R.id.next_button);
			mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
			new Thread(MediaPlayerActivity.this).start();
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
			mProgressBar.setProgress(0);
			mProgressBar.setMax(mp.getDuration());
			play_button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.pause();
						play_button.setText("재생");
					} else {
						mp.start();
						play_button.setText("중지");
					}
				}
			});
			stop_button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					try {
						mp.stop();
						mp.prepare();
						mp.seekTo(0);
						mProgressBar.setProgress(0);
						play_button.setText("재생");
					} catch (IOException ie) {
						ie.printStackTrace();
					}
				}
			});
			prev_button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int currentPosition = mp.getCurrentPosition();
					if (currentPosition - 20000 < 0) {
						mp.seekTo(0);
					} else {
						mp.seekTo(currentPosition - 20000);
					}
				}
			});
			next_button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int total = mp.getDuration();
					int currentPosition = mp.getCurrentPosition();
					if (currentPosition + 20000 > total) {
						mp.seekTo(total);
					} else {
						mp.seekTo(currentPosition + 20000);
					}
				}
			});
			mp.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					play_button.setText("재생");
					mProgressBar.setProgress(0);
				}
			});
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 액티비티가 소멸될 때 반드시 사용 중인 MediaPlayer 객체의
		// release() 메서드를 호출해주어야 합니다.
		// 그렇지 않으면 태스크가 소멸된 후에도 음악이 계속해서
		// 재생되는 상황이 벌어질지도 모릅니다.
		if (mp != null) {
			try {
				mp.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		int currentPosition = 0;
		while (mp != null) {
			try {
				Thread.sleep(1000);
				currentPosition = mp.getCurrentPosition();
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				return;
			}
			if (mp.isPlaying())
				mProgressBar.setProgress(currentPosition);
		}
	}
}
