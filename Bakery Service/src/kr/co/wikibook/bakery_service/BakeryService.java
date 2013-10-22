package kr.co.wikibook.bakery_service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import kr.co.wikibook.bakery_service_interfaces.IBakeryService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

public class BakeryService extends Service implements Runnable {
	private Queue<String> mBreads = new LinkedList<String>();
	private boolean runThread = false;
	private ArrayList<ComponentName> acn = new ArrayList<ComponentName>();
	private IBakeryService.Stub mBinder = new IBakeryService.Stub() {
		public String getBread(android.content.ComponentName cn)
						throws android.os.RemoteException {
			// 가게에 들어온 고객인지 확인한다.
			if (acn.indexOf(cn) == -1) {
				return null;
			}
			String bread = null;
			bread = mBreads.poll();
			if (bread == null)
				bread = "";
			return bread;
		}

		public boolean enterBakery(android.content.ComponentName cn)
						throws android.os.RemoteException {

			// 가게에 들어온 고객인지 확인한다.
			if (acn.indexOf(cn) != -1) {
				return true;
			}
			if (acn.size() < 3) {
				acn.add(cn);
				return true;
			} else {
				return false;
			}
		}

		public boolean leaveBakery(android.content.ComponentName cn)
						throws android.os.RemoteException {
			// 가게에 들어온 고객인지 확인한다.
			if (acn.indexOf(cn) == -1) {
				return false;
			}
			acn.remove(cn);
			return true;
		}

		public int getCustomerCount() {
			return acn.size();
		}
	};

	@Override
	public void onCreate() {
		runThread = true;
		// 서비스가 시작할 때 빵 3개를 만들어 둔다.
		for (int i = 0; i < 3; i++)
			mBreads.offer(getBread());
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void onDestroy() {
		runThread = false;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public void run() {
		while (runThread) {
			synchronized (BakeryService.this) {
				if (mBreads.size() < 3) { // 빵은 3개만 만든다.
					mBreads.offer(getBread());
				}
			}
			try { // 10초에 한 번씩 빵을 만든다.
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String getBread() {
		String bread1 = "국화빵";
		String bread2 = "식빵";
		String bread3 = "곰보빵";
		String bread = "";
		Random random = new Random();
		int i = random.nextInt(2);
		switch (i) {
		case 0:
			bread = bread1;
			break;
		case 1:
			bread = bread2;
			break;
		case 2:
			bread = bread3;
			break;
		default:
			bread = bread1;
			break;
		}
		return bread;
	}
}