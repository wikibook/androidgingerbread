package kr.co.wikibook.simple_sip_application;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OutgoingCallActivity extends Activity {
  public static final String CLOSE_OUTGOING_CALL = "kr.co.wikibook.simple_sip_application.CLOSE_OUTGOING_CALL";
  public static final String CALL_CLOSE_BY_CLOSE_PEER = "kr.co.wikibook.simple_sip_application.CALL_CLOSE_BY_CLOSE_PEER_OC";
  /* 걸려온 전화가 받기 전에 끊겼을 때 액티비티를 종료시킬 브로드캐스트 리시버 */
  BroadcastReceiver br = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      finish();
    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.outgoing_call_activity);
    IntentFilter filter = new IntentFilter();
    filter.addAction(CALL_CLOSE_BY_CLOSE_PEER);
    this.registerReceiver(br, filter);
    Button closeCall = (Button) findViewById(R.id.close_call);
    closeCall.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(CLOSE_OUTGOING_CALL);
        sendBroadcast(intent);
        finish();
      }
    });
  }
}