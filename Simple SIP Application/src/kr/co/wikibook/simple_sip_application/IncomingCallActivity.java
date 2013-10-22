package kr.co.wikibook.simple_sip_application;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IncomingCallActivity extends Activity {
  SipAudioCall incomingCall = null;
  private Button mReceiveCall = null;
  private static final String TAG = "SimpleSIPApplication";
  public static final String RECEIVE_CALL = "kr.co.wikibook.simple_sip_application.RECEIVE_CALL";
  public static final String CLOSE_CALL = "kr.co.wikibook.simple_sip_application.CLOSE_CALL";
  public static final String CALL_CLOSE_BY_CLOSE_PEER = "kr.co.wikibook.simple_sip_application.CALL_CLOSE_BY_CLOSE_PEER";
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
    setContentView(R.layout.incoming_call_activity);
    IntentFilter filter = new IntentFilter();
    filter.addAction(CALL_CLOSE_BY_CLOSE_PEER);
    this.registerReceiver(br, filter);
    Button receiveCall = (Button) findViewById(R.id.receive_call);
    mReceiveCall = receiveCall;
    receiveCall.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Button button = (Button) v;
        if (button.getText().toString().equals("전화받기")) {
          Log.i(TAG, "IncomingCallActivity - onClick");
          Intent intent = new Intent(RECEIVE_CALL);
          IncomingCallActivity.this.sendBroadcast(intent);
          /* 버튼의 레이블을 변경 */
          mReceiveCall.setText("전화끊기");
        } else if (button.getText().toString().equals("전화끊기")) {
          Intent intent = new Intent(CLOSE_CALL);
          IncomingCallActivity.this.sendBroadcast(intent);
          mReceiveCall.setText("전화받기");
          finish();
        }
      }
    });
  }
}