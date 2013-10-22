package kr.co.wikibook.simple_sip_application;

import java.text.ParseException;
import android.app.Activity;
import android.app.PendingIntent;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SimpleSIPApplication extends Activity {
  public SipManager mSipManager = null;
  public SipProfile mSipProfile = null;
  public SipAudioCall call = null;
  public SipAudioCall outGoingCall = null;
  public IncomingCallReceiver callReceiver;
  public static final String INCOMING_CALL = "kr.co.wikibook.simple_sip_application.INCOMING_CALL";
  private String TAG = "SimpleSIPApplication";
  Button mStartCallButton = null;
  Button mConnectToSipServer = null;
  String mDomain = "";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    /* SipManager 객체의 인스턴스 생성 */
    if (mSipManager == null) {
      mSipManager = SipManager.newInstance(this);
    }
    /* 전화가 왔을 때 인텐트를 수신할 수 있게 리시버를 등록 */
    IntentFilter filter = new IntentFilter();
    filter.addAction(INCOMING_CALL);
    filter.addAction(IncomingCallActivity.RECEIVE_CALL);
    filter.addAction(IncomingCallActivity.CLOSE_CALL);
    filter.addAction(OutgoingCallActivity.CLOSE_OUTGOING_CALL);
    callReceiver = new IncomingCallReceiver();
    this.registerReceiver(callReceiver, filter);
    /* Call 버튼을 누르면 전화 걸기를 수행 */
    Button startCallButton = (Button) findViewById(R.id.start_call_button);
    startCallButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        startCall();
      }
    });
    mStartCallButton = startCallButton;
    /* SIP 서버 접속 버튼을 누르면 SIP 서버에 접속 */
    Button connectToSipServer = (Button) findViewById(R.id.connect_to_sip_server);
    mConnectToSipServer = connectToSipServer;
    connectToSipServer.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        EditText sipId = (EditText) findViewById(R.id.sip_id);
        EditText sipPass = (EditText) findViewById(R.id.sip_pass);
        EditText sipDomain = (EditText) findViewById(R.id.sip_domain);
        mDomain = sipDomain.getText().toString();
        /* 에디트텍스트에 입력된 정보를 바탕으로 SipProfile 객체를 생성 */
        try {
          SipProfile.Builder builder = new SipProfile.Builder(sipId.getText()
              .toString(), sipDomain.getText().toString());
          builder.setPassword(sipPass.getText().toString());
          mSipProfile = builder.build();
        } catch (ParseException e) {
          e.printStackTrace();
        }
        /* 전화가 걸렸을 때 수신할 펜딩인텐트 생성 */
        try {
          Intent intent = new Intent();
          intent.setAction(INCOMING_CALL);
          PendingIntent pendingIntent = PendingIntent.getBroadcast(
              SimpleSIPApplication.this, 0, intent, Intent.FILL_IN_DATA);
          mSipManager.open(mSipProfile, pendingIntent, null);
          /* 리스너를 통해 SIP 서버로의 요청에 대한 피드백을 받음 */
          mSipManager.setRegistrationListener(mSipProfile.getUriString(),
              new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                  // SIP 서버로의 접속 중....
                  Log.i(TAG, "Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri,
                    long expiryTime) {
                  // 아래의 로그가 출력되면 SIP 서버로 접속이 완료된 것임.
                  Log.i(TAG, "Ready..");
                }

                public void onRegistrationFailed(String localProfileUri,
                    int errorCode, String errorMessage) {
                  // SIP 서버에 등록하지 못하면 아래의 로그가 출력된다.
                  Log.i(TAG, "Registration failed. Please check settings.");
                }
              });
        } catch (SipException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (call != null) {
      call.close();
    }
    if (mSipManager == null) {
      return;
    }
    try {
      if (mSipProfile != null) {
        mSipManager.close(mSipProfile.getUriString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (callReceiver != null) {
      this.unregisterReceiver(callReceiver);
    }
  }

  /* 전화 걸기 */
  private void startCall() {
    /* SIP 서버에 접속한 상태가 아니면 전화를 걸지 않는다. */
    try {
      if (mSipProfile == null)
        return;
      if (!mSipManager.isOpened(mSipProfile.getUriString())) {
        return;
      }
    } catch (SipException e1) {
      e1.printStackTrace();
      return;
    }
    SipAudioCall.Listener listener = new SipAudioCall.Listener() {
      @Override
      public void onCallEstablished(SipAudioCall call) {
        call.startAudio();
        call.setSpeakerMode(true);
        if (call.isMuted()) {
          call.toggleMute();
        }
        outGoingCall = call;
        Log.i(TAG, "onCallEstablished.");
        Intent intent = new Intent(SimpleSIPApplication.this,
            OutgoingCallActivity.class);
        startActivity(intent);
      }

      @Override
      public void onCalling(SipAudioCall call) {
        Log.i(TAG, "startCall/onCalling.");
      }

      @Override
      public void onError(SipAudioCall call, int errorCode, String errorMessage) {
        Log.i(TAG, "startCall/onError.");
        Log.i(TAG, "errorCode is " + errorCode);
        Log.i(TAG, "errorMessage is " + errorMessage);
      }

      @Override
      public void onCallEnded(SipAudioCall call) {
        Log.i(TAG, "startCall/onCallEnded/Ready.");
        Intent intent = new Intent(
            OutgoingCallActivity.CALL_CLOSE_BY_CLOSE_PEER);
        SimpleSIPApplication.this.sendBroadcast(intent);
      }
    };
    try {
      EditText toSipId = (EditText) findViewById(R.id.to_sip_id);
      // SIP 서버에 등록된 상대방 URL은 sip:<수신번호>@<도메인>의 형식으로 구성된다.
      call = mSipManager.makeAudioCall(mSipProfile.getUriString(), "sip:"
          + toSipId.getText().toString() + "@" + mDomain, listener, 30);
    } catch (SipException e) {
      e.printStackTrace();
      Log.i(TAG, "Error when trying to close manager.", e);
      if (mSipProfile != null) {
        try {
          mSipManager.close(mSipProfile.getUriString());
        } catch (Exception ee) {
          Log.i(TAG, "Error when trying to close manager.", ee);
          ee.printStackTrace();
        }
      }
      if (call != null) {
        call.close();
      }
    }
  }
}