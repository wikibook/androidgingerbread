package kr.co.wikibook.view_flipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class ViewFlipperActivity extends Activity {
	ViewFlipper vf = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 뷰플리퍼를 클릭했을 때 뷰플리퍼가 화면에 출력하는
		// 하위 뷰를 바꿉니다.
		vf = (ViewFlipper) findViewById(R.id.flipper);
		vf.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int childId = vf.getDisplayedChild();
				int childCount = vf.getChildCount();

				// 애니메이션 추가
				vf.setInAnimation(AnimationUtils.loadAnimation(
								ViewFlipperActivity.this,
								android.R.anim.slide_in_left));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
								ViewFlipperActivity.this,
								android.R.anim.slide_out_right));

				// 현재 출력된 뷰가 마지막 하위 뷰라면 뷰플리퍼 클릭 시에
				// 첫 번째 하위 뷰를 보여줍니다.
				// 그렇지 않다면 현재 하위 뷰의 다음 하위 뷰를 보여 줍니다.
				if (childCount == childId + 1) {
					vf.setDisplayedChild(0);
				} else {
					vf.setDisplayedChild(childId + 1);
				}
			}
		});
	}
}