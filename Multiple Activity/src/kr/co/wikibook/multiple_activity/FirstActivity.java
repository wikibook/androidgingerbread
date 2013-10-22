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
    /* ���̾ƿ����� ������ ��ư�� �ν��Ͻ��� �����´�. */
    Button fa_next_button = (Button) findViewById(R.id.fa_next_button);
    fa_next_button.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Log.i("Multi Activity", "Button click is occured..");
        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        startActivity(intent);
      }
    });
    /* ��ȭ�ɱ� ��ư�� �ν��Ͻ��� �����ϰ� �����ʸ� �Ҵ��Ѵ�. */
    Button call_button = (Button) findViewById(R.id.call_button);
    call_button.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        /* ��ȭ�� �ɱ� ���� ����Ʈ�� �����Ѵ�. */
        Intent intent = new Intent(Intent.ACTION_CALL);
        /* ��ȭ��ȣ�� Uri �ν��Ͻ��� ��ȯ�Ѵ�. */
        intent.setData(Uri.parse("tel:01012345678"));
        /* ������ ����Ʈ�� ����� ��Ƽ��Ƽ�� �����Ѵ�. */
        startActivity(intent);
      }
    });
  }
}