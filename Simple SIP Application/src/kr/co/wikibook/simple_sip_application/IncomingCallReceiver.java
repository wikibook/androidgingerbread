package kr.co.wikibook.simple_sip_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipProfile;
import android.util.Log;

public class IncomingCallReceiver extends BroadcastReceiver {
  private static final String TAG = "SimpleSIPApplication";
  private Intent incomingCallIntent = null;
  private Context incomingContext = null;
  private SipAudioCall incomingCall = null;

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "IncomingCallReceiver - onReceive");
    if (intent.getAction().equals(SimpleSIPApplication.INCOMING_CALL)) {
      incomingCallIntent = intent;
      incomingContext = context;
      context.startActivity(new Intent(context, IncomingCallActivity.class));
      SimpleSIPApplication mainActivity = (SimpleSIPApplication) context;
      SipAudioCall.Listener listener = new SipAudioCall.Listener() {
        @Override
        public void onRinging(SipAudioCall call, SipProfile caller) {
          try {
            Log.i(TAG, "SipAudioCall.Listener - onRinging");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        @Override
        public void onError(SipAudioCall call, int errorCode,
            String errorMessage) {
          Log.i(TAG, "startCall/onError.");
          Log.i(TAG, "errorCode is " + errorCode);
          Log.i(TAG, "errorMessage is " + errorMessage);
        }

        @Override
        public void onCallEnded(SipAudioCall call) {
          Log.i(TAG, "startCall/onCallEnded/Ready.");
          Intent intent = new Intent(
              IncomingCallActivity.CALL_CLOSE_BY_CLOSE_PEER);
          incomingContext.sendBroadcast(intent);
        }
      };
      try {
        incomingCall = mainActivity.mSipManager.takeAudioCall(
            incomingCallIntent, listener);
      } catch (SipException e) {
        e.printStackTrace();
      }
    } else if (intent.getAction().equals(IncomingCallActivity.RECEIVE_CALL)) {
      Log.i(TAG, "IncomingCallReceiver - onReceive /RECEIVE_CALL");
      try {
        SimpleSIPApplication mainActivity = (SimpleSIPApplication) incomingContext;
        incomingCall.answerCall(30);
        incomingCall.startAudio();
        incomingCall.setSpeakerMode(true);
        if (incomingCall.isMuted()) {
          incomingCall.toggleMute();
        }
        mainActivity.call = incomingCall;
        Log.i(TAG, "IncomingCall is stated....");
      } catch (Exception e) {
        Log.i(TAG, "IncomingCall is Failed.");
        if (incomingCall != null) {
          incomingCall.close();
        }
      }
    } else if (intent.getAction().equals(IncomingCallActivity.CLOSE_CALL)) {
      if (incomingCall != null) {
        try {
          incomingCall.endCall();
        } catch (SipException e) {
          e.printStackTrace();
        }
        incomingCall.close();
        incomingCall = null;
      }
    } else if (intent.getAction().equals(
        OutgoingCallActivity.CLOSE_OUTGOING_CALL)) {
      SimpleSIPApplication mainActivity = (SimpleSIPApplication) context;
      if (mainActivity.outGoingCall != null) {
        try {
          mainActivity.outGoingCall.endCall();
        } catch (SipException e) {
          e.printStackTrace();
        }
        mainActivity.outGoingCall.close();
        mainActivity.outGoingCall = null;
      }
    }
  }
}