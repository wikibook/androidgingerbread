package kr.co.wikibook.twitter;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddNewTwitActivity extends Activity {
  private Twitter mTwitter;
  private static final int _DID_CANT_UPDATE_STATUS = 0;
  private static final int _DID_UPDATING_STATUS = 1;
  long inReplyToStatusId = 0;
  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      removeDialog(_DID_UPDATING_STATUS);
    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_twit);
    Intent it = getIntent();
    Bundle extra = it.getExtras();
    if (extra != null) {
      inReplyToStatusId = extra.getLong("id");
    }
    mTwitter = TwitterActivity.mTwitter;
    if (inReplyToStatusId > 0) {
      String screen_name = extra.getString("screen_name");
      EditText edittext = (EditText) findViewById(R.id.twit_message_box);
      edittext.setText("@" + screen_name + " ");
    }
    Button bt_update_status = (Button) findViewById(R.id.bt_update_status);
    bt_update_status.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        showDialog(_DID_UPDATING_STATUS);
        new Thread(new Runnable() {
          public void run() {
            try {
              updateStatus();
              handler.sendMessage(handler.obtainMessage());
            } catch (TwitterException te) {
            }
          }
        }).start();
      }
    });
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    switch (id) {
    case _DID_CANT_UPDATE_STATUS: {
      ProgressDialog dialog = new ProgressDialog(this);
      dialog.setMessage("Ʈ���� �ø��µ� �����Ͽ����ϴ�..");
      dialog.setIndeterminate(true);
      dialog.setCancelable(true);
      return dialog;
    }
    case _DID_UPDATING_STATUS: {
      ProgressDialog dialog = new ProgressDialog(this);
      dialog.setMessage("�� Ʈ���� �ø��� ���Դϴ�...");
      dialog.setIndeterminate(true);
      dialog.setCancelable(true);
      return dialog;
    }
    }
    return null;
  }

  public void updateStatus() throws TwitterException {
    EditText twit_message_box = (EditText) findViewById(R.id.twit_message_box);
    String twit_message_box_text = twit_message_box.getText().toString();
    Status status = null;
    try {
      if (inReplyToStatusId > 0) {
        StatusUpdate su = new StatusUpdate(twit_message_box_text);
        su.setInReplyToStatusId(inReplyToStatusId);
        status = mTwitter.updateStatus(su);
      } else {
        status = mTwitter.updateStatus(twit_message_box_text);
      }
    } catch (TwitterException te) {
      throw te;
    } finally {
      finish();
    }
  }
}