package kr.co.wikibook.drawsomething;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.View;

public class DrawSomethingActivity extends Activity {
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
    
    private static class MyView extends View {
      
      public MyView(Context context) {
        super(context);
      }
      
      @Override
      protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xFF504F95);
        paint. setStrokeWidth(30);
        canvas.drawColor(Color.GRAY);  
        
        Bitmap bitmapImage = BitmapFactory.decodeResource(
            getResources(),
            R.drawable.dp);
        
        Bitmap scaledBitmapImage = Bitmap.createScaledBitmap(bitmapImage, 
            130, 
            130, 
            false);
        
        int sc = canvas.saveLayer(20, 20, 
            150, 
            150,
            null,
            Canvas.MATRIX_SAVE_FLAG |
            Canvas.CLIP_SAVE_FLAG |
            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
            Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        RectF rectf = new RectF(20, 20, 150, 150);        
        canvas.drawRoundRect(rectf, 15, 15, paint);        
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));        
        canvas.drawBitmap(scaledBitmapImage, 20, 20, paint);
      }
    }
}