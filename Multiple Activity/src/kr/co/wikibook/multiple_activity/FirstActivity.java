package kr.co.wikibook.multiple_activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;

public class FirstActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    /* 레이아웃으로 생성한 버튼의 인스턴스를 가져온다. */
    Button fa_next_button = (Button) findViewById(R.id.fa_next_button);
    fa_next_button.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Log.i("Multi Activity", "Button click is occured..");
        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        startActivity(intent);
      }
    });
    /* 전화걸기 버튼의 인스턴스를 생성하고 리스너를 할당한다. */
    Button call_button = (Button) findViewById(R.id.call_button);
    call_button.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        /* 전화를 걸기 위한 인텐트를 생성한다. */
        Intent intent = new Intent(Intent.ACTION_CALL);
        /* 전화번호를 Uri 인스턴스로 변환한다. */
        intent.setData(Uri.parse("tel:01012345678"));
        /* 생성된 인텐트를 사용해 액티비티를 실행한다. */
        startActivity(intent);
      }
    });
  }
}