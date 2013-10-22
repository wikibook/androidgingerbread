package kr.co.wikibook.customer;

import kr.co.wikibook.bakery_service_interfaces.IBakeryService;
import android.app.Activity;
import android.content.ComponentName;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CustomerActivity extends Activity {
	private IBakeryService mBinder;
	private TextView message;
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mBinder = IBakeryService.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName className) {
			mBinder = null;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ���񽺿� ����
		Intent intent = new Intent(IBakeryService.class.getName());
		startService(intent);
		bindService(intent, mConnection, BIND_AUTO_CREATE);
		message = (TextView) findViewById(R.id.message);
		Button enter = (Button) findViewById(R.id.enter);
		enter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {

					if (mBinder.enterBakery(getComponentName())) {
						message.setText("������ �����ϴ�.");
					} else {
						message.setText("������ �մ����� ������ �� �� �����ϴ�.");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		Button get_bread = (Button) findViewById(R.id.get_bread);
		get_bread.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String bread;
					bread = mBinder.getBread(getComponentName());
					if (bread != null) {
						if (bread.equals("")) {
							message.setText("���� ��� �ȷȽ��ϴ�.");
						} else {
							message.setText(bread + "��(��) �����Ͽ����ϴ�.");
						}
					} else {
						message.setText("���� ����� ���� ������ ���� �մϴ�.");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		Button leave = (Button) findViewById(R.id.leave);
		leave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (mBinder.leaveBakery(getComponentName())) {
						message.setText("�������� ���Խ��ϴ�.");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		Button customer_count = (Button) findViewById(R.id.customer_count);
		customer_count.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					message.setText("������ �� " + mBinder.getCustomerCount()
									+ "���� �մ��� �ֽ��ϴ�.");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mConnection != null) {
			unbindService(mConnection);
			mConnection = null;
		}
	}
}
