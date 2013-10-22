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
		// ���ø��۸� Ŭ������ �� ���ø��۰� ȭ�鿡 ����ϴ�
		// ���� �並 �ٲߴϴ�.
		vf = (ViewFlipper) findViewById(R.id.flipper);
		vf.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int childId = vf.getDisplayedChild();
				int childCount = vf.getChildCount();

				// �ִϸ��̼� �߰�
				vf.setInAnimation(AnimationUtils.loadAnimation(
								ViewFlipperActivity.this,
								android.R.anim.slide_in_left));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
								ViewFlipperActivity.this,
								android.R.anim.slide_out_right));

				// ���� ��µ� �䰡 ������ ���� ���� ���ø��� Ŭ�� �ÿ�
				// ù ��° ���� �並 �����ݴϴ�.
				// �׷��� �ʴٸ� ���� ���� ���� ���� ���� �並 ���� �ݴϴ�.
				if (childCount == childId + 1) {
					vf.setDisplayedChild(0);
				} else {
					vf.setDisplayedChild(childId + 1);
				}
			}
		});
	}
}