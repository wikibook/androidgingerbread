package kr.co.wikibook.compose_shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;

public class ComposeShaderActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ShaderView(this));
	}

	private class ShaderView extends View {
		
		Context mContext;
		
		public ShaderView(Context context) {
			super(context);			
			mContext = context;
		}
		
		@Override
		public void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			int color1 = Color.GREEN;
			int color2 = Color.RED;
			
			LinearGradient lg = new LinearGradient(10, 10, 400, 10, color1, color2, TileMode.CLAMP);
			
			int color3 = Color.BLUE;
			int color4 = Color.GRAY;
			
			SweepGradient sg = new SweepGradient(240, 200, color3, color4);
			
			ComposeShader cs = new ComposeShader(lg, sg, new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
			paint.setShader(cs);
			canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
		}
	}
}
