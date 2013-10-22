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
    /* SipManager ��ü�� �ν��Ͻ� ���� */
    if (mSipManager == null) {
      mSipManager = SipManager.newInstance(this);
    }
    /* ��ȭ�� ���� �� ����Ʈ�� ������ �� �ְ� ���ù��� ��� */
    IntentFilter filter = new IntentFilter();
    filter.addAction(INCOMING_CALL);
    filter.addAction(IncomingCallActivity.RECEIVE_CALL);
    filter.addAction(IncomingCallActivity.CLOSE_CALL);
    filter.addAction(OutgoingCallActivity.CLOSE_OUTGOING_CALL);
    callReceiver = new IncomingCallReceiver();
    this.registerReceiver(callReceiver, filter);
    /* Call ��ư�� ������ ��ȭ �ɱ⸦ ���� */
    Button startCallButton = (Button) findViewById(R.id.start_call_button);
    startCallButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        startCall();
      }
    });
    mStartCallButton = startCallButton;
    /* SIP ���� ���� ��ư�� ������ SIP ������ ���� */
    Button connectToSipServer = (Button) findViewById(R.id.connect_to_sip_server);
    mConnectToSipServer = connectToSipServer;
    connectToSipServer.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        EditText sipId = (EditText) findViewById(R.id.sip_id);
        EditText sipPass = (EditText) findViewById(R.id.sip_pass);
        EditText sipDomain = (EditText) findViewById(R.id.sip_domain);
        mDomain = sipDomain.getText().toString();
        /* ����Ʈ�ؽ�Ʈ�� �Էµ� ������ �������� SipProfile ��ü�� ���� */
        try {
          SipProfile.Builder builder = new SipProfile.Builder(sipId.getText()
              .toString(), sipDomain.getText().toString());
          builder.setPassword(sipPass.getText().toString());
          mSipProfile = builder.build();
        } catch (ParseException e) {
          e.printStackTrace();
        }
        /* ��ȭ�� �ɷ��� �� ������ �������Ʈ ���� */
        try {
          Intent intent = new Intent();
          intent.setAction(INCOMING_CALL);
          PendingIntent pendingIntent = PendingIntent.getBroadcast(
              SimpleSIPApplication.this, 0, intent, Intent.FILL_IN_DATA);
          mSipManager.open(mSipProfile, pendingIntent, null);
          /* �����ʸ� ���� SIP �������� ��û�� ���� �ǵ���� ���� */
          mSipManager.setRegistrationListener(mSipProfile.getUriString(),
              new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                  // SIP �������� ���� ��....
                  Log.i(TAG, "Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri,
                    long expiryTime) {
                  // �Ʒ��� �αװ� ��µǸ� SIP ������ ������ �Ϸ�� ����.
                  Log.i(TAG, "Ready..");
                }

                public void onRegistrationFailed(String localProfileUri,
                    int errorCode, String errorMessage) {
                  // SIP ������ ������� ���ϸ� �Ʒ��� �αװ� ��µȴ�.
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

  /* ��ȭ �ɱ� */
  private void startCall() {
    /* SIP ������ ������ ���°� �ƴϸ� ��ȭ�� ���� �ʴ´�. */
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
      // SIP ������ ��ϵ� ���� URL�� sip:<���Ź�ȣ>@<������>�� �������� �����ȴ�.
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