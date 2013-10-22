package kr.co.wikibook.bitmap_shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;

public class BitmapShaderActivity extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new CustomView(this));
  }

  private class CustomView extends View {
    private Context mContext;

    public CustomView(Context context) {
      super(context);
      mContext = context;
    }

    public void onDraw(Canvas canvas) {
      Paint paint = new Paint();

      Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
          R.drawable.pattern);

      BitmapShader bs = new BitmapShader(bitmap, TileMode.REPEAT,
          TileMode.REPEAT);
      paint.setShader(bs);

      canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
    }
  }
}