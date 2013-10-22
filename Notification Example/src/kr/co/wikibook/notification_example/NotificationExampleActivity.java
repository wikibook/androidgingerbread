package kr.co.wikibook.notification_example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;

public class NotificationExampleActivity extends Activity {
	static final int DIALOG_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btRunToast = (Button) findViewById(R.id.bt_run_toast);
		btRunToast.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Toast toast = Toast.makeText(NotificationExampleActivity.this,
								"�� ���� �佺Ʈ�� �ŷ¿� ����������.", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		Button btAddNotification = (Button) findViewById(R.id.bt_add_notification);
		btAddNotification.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int notification_icon = R.drawable.wikibooks_logo_trans;
				CharSequence tickerText = "�ȳ��ϼ���?";
				Context context = getApplicationContext();
				CharSequence contentTitle = "�� �޽��� �˸�";
				CharSequence contentText = "�ȵ���̵��� ���迡 ���� ���� ȯ���մϴ�.";
				Intent notificationIntent = new Intent(
								NotificationExampleActivity.this,
								NotificationActivity.class);
				PendingIntent contentIntent = PendingIntent.getActivity(
								NotificationExampleActivity.this, 0,
								notificationIntent, 0);
				Notification notification = new Notification(notification_icon,
								tickerText, System.currentTimeMillis());
				notification.setLatestEventInfo(context, contentTitle,
								contentText, contentIntent);
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
				mNotificationManager.notify(1, notification);
			}
		});
		Button btRunDialog = (Button) findViewById(R.id.bt_run_dialog);
		btRunDialog.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				showDialog(DIALOG_ID);
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_ID:
			TextView textview = new TextView(this);
			textview.setText("���̾�α׿� ���ϴ� �並 �ٿ� " + "ȭ�鿡 ��Ÿ���� �� �� �ֽ��ϴ�.");
			textview.setPadding(30, 30, 30, 30);
			dialog = new Dialog(this);
			dialog.setTitle("�˸�");
			dialog.setContentView(textview);
			break;
		}
		return dialog;
	}
}