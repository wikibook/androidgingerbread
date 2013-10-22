package kr.co.wikibook.my_bluetooth_app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyBluetoothAppActivity extends Activity {
	public static final int REQUEST_ENABLE_BT = 0;
	public static final int SEARCH_BT_DEVICE = 1;
	private BluetoothAdapter mBluetoothAdapter = null;
	private ConnectedThread mConnectedThread;
	private ConnectThread mConnectThread;
	private AcceptThread mAcceptThread;
	private static UUID mUuid = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private ArrayAdapter<String> mAdapter;
	private ListView listView;
	private EditText message_box;
	private final int ADD_MESSAGE = 0;
	private final int SHOW_TOAST = 1;
	BluetoothDevice mDevice = null;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ADD_MESSAGE:
				mAdapter.add((String) msg.obj);
				break;
			case SHOW_TOAST: {
				Toast toast = Toast.makeText(MyBluetoothAppActivity.this,
						(String) msg.obj, Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayList<String> arrayList = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.message_timeline);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(mAdapter);
		message_box = (EditText) findViewById(R.id.message_box);
		Button send_message = (Button) findViewById(R.id.send_message);
		send_message.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String message = message_box.getText().toString();
				if (message.length() > 0) {
					sendMessage(message);
					message_box.setText("");
				}
			}
		});
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			// ba�� null�̸� ��ġ�� ��������� �������� �����Ƿ�
			// �׿� ���� ����ó�� �ڵ尡 �ʿ��մϴ�.
			finish();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!mBluetoothAdapter.isEnabled()) {
			Intent it = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(it, REQUEST_ENABLE_BT);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		if (mAcceptThread != null) {
			mAcceptThread.cancel();
			mAcceptThread = null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
					.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {
				}
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			finish();
		} else if (resultCode == SEARCH_BT_DEVICE) {
			mDevice = data.getParcelableExtra("device");
			// Ŭ���̾�Ʈ�� �ٸ���⿡ �����ϱ� ���� ���� �����带 ������Ѿ� �մϴ�.
			// listenUsingRfcommWithServiceRecord() �޼��尡 ����� ���¿���
			// createRfcommSocketToServiceRecord() �� �����ϸ� ���ܰ� �߻��� ���Դϴ�.
			if (mAcceptThread != null) {
				mAcceptThread.cancel();
				mAcceptThread = null;
			}
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}
			ConnectThread mConnectThread = new ConnectThread(mDevice);
			mConnectThread.start();
		} else {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "��ġ�� ����");
		menu.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "��ġ Ž�� Ȱ��ȭ");
		menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "���� ����");
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return (itemCallback(item) || super.onOptionsItemSelected(item));
	}

	private boolean itemCallback(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Intent it = new Intent(MyBluetoothAppActivity.this,
					SearchBluetoothDeviceActivity.class);
			startActivityForResult(it, SEARCH_BT_DEVICE);
			return true;
		case Menu.FIRST + 2:
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
			return true;
		case Menu.FIRST + 3:
			if (mAcceptThread != null) {
				mAcceptThread.cancel();
				mAcceptThread = null;
			}
			mAcceptThread = new AcceptThread(this);
			Thread thread = new Thread(mAcceptThread);
			thread.start();
			return true;
		default:
			return false;
		}
	}

	private void sendMessage(String message) {
		if (mConnectedThread != null) {
			mConnectedThread.write(message.getBytes());
			mAdapter.add("��: " + message);
		}
	}

	private void addMessageToListView(String data) {
		if (data != null && data.length() > 0) {
			Message message = handler.obtainMessage();
			message.what = ADD_MESSAGE;
			message.arg1 = 0;
			message.arg2 = 0;
			message.obj = "��: " + data;
			handler.sendMessage(message);
		}
	}

	private void startConnectedThread(BluetoothSocket socket) {
		// AcceptThread�κ��� ȣ��Ǿ��ٸ� mConnectThread�� null �� ���̹Ƿ�..
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		mConnectedThread = new ConnectedThread(socket);
		mConnectedThread.start();
	}

	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			BluetoothSocket tmp = null;
			mmDevice = device;
			try {
				tmp = device.createRfcommSocketToServiceRecord(mUuid);
			} catch (IOException e) {
			}
			mmSocket = tmp;
		}

		public void run() {
			// ��ġ �˻��� ����ǰ� �ִ��� Ȯ���Ͽ� �����մϴ�. ��ġ �˻���
			// ���� ���϶� ������ ������ ���� �ӵ��� ������ ���Դϴ�.
			mBluetoothAdapter.cancelDiscovery();
			try {
				mmSocket.connect();
			} catch (IOException connectException) {
				connectException.printStackTrace();
				try {
					mmSocket.close();
				} catch (IOException closeException) {
				}
				return;
			}
			startConnectedThread(mmSocket);
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}

	private class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket) {
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
			}
			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			showToast("������ ����Ǿ����ϴ�.");
			byte[] buffer = new byte[1024];
			int bytes;
			// InputStream ���κ��� �Է��� �о���Դϴ�.
			while (true) {
				try {
					// Read from the InputStream
					bytes = mmInStream.read(buffer);
					String msg = new String(buffer).trim();
					addMessageToListView(msg);
				} catch (IOException e) {
					break;
				}
			}
		}

		public void write(byte[] bytes) {
			try {
				mmOutStream.write(bytes);
			} catch (IOException e) {
			}
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}

	private class AcceptThread extends Thread {
		private final BluetoothServerSocket mmServerSocket;
		private Context mContext;

		public AcceptThread(Context context) {
			BluetoothServerSocket tmp = null;
			mContext = context;
			try {
				// UUID�� ����Ͽ� ���� ������ ����ϴ�.
				tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
						"My Bluetooth", mUuid);
			} catch (IOException e) {
				showToast("���� ������ ����µ� �����Ͽ����ϴ�. " + e.toString());
			}
			mmServerSocket = tmp;
		}

		public void run() {
			showToast("Ŭ���̾�Ʈ�� ��ٸ��� ���Դϴ�.");
			BluetoothSocket socket = null;
			// Ŭ���̾�Ʈ�� ������ �õ��Ҷ����� ��ٸ��ϴ�.
			while (true) {
				try {
					if (mmServerSocket != null) {
						socket = mmServerSocket.accept();
					}
				} catch (IOException e) {
					break;
				}
				// If a connection was accepted
				if (socket != null) {
					// Ŭ���̾�Ʈ�� ����ǰ� ������ �����Ǹ�
					// ������ ���� ������ �ۼ����� �����մϴ�.
					startConnectedThread(socket);
					showToast("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");
					try {
						if (mmServerSocket != null) {
							mmServerSocket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
						showToast("���� ������ �����ϴ� �� ������ �߻��Ͽ����ϴ�. " + e.toString());
					}
					break;
				}
			}
		}

		// ������ ������ �ݰ� �����带 �����մϴ�.
		public void cancel() {
			try {
				if (mmServerSocket != null) {
					mmServerSocket.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private void showToast(String msg) {
		Message message = handler.obtainMessage();
		message.what = SHOW_TOAST;
		message.arg1 = 0;
		message.arg2 = 0;
		message.obj = msg;
		handler.sendMessage(message);
	}
}
