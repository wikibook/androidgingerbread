package kr.co.wikibook.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;

public class ShaderActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new ShaderView(this));
  }

  private class ShaderView extends View {

    public ShaderView(Context context) {
      super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
      Paint paint = new Paint();

      int color1 = Color.BLUE;
      int color2 = Color.GREEN;

      SweepGradient sg = new SweepGradient(240, 200, color1, color2);

      paint.setShader(sg);

      canvas.drawPaint(paint);
    }
  }
}