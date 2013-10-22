package kr.co.wikibook.context_menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.Toast;

public class ContextMenuActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn = (Button) findViewById(R.id.button);
		registerForContextMenu(btn);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("���� ����");
		menu.add(0, v.getId(), 0, "���");
		menu.add(0, v.getId(), 0, "���");
		menu.add(0, v.getId(), 0, "«��");
		menu.add(0, v.getId(), 0, "�쵿");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		String message = "";
		if (item.getTitle().equals("���")) {
			message = "����� �����ϼ̽��ϴ�.";
		} else if (item.getTitle().equals("���")) {
			message = "����� �����ϼ̽��ϴ�.";
		} else if (item.getTitle().equals("«��")) {
			message = "«���� �����ϼ̽��ϴ�.";
		} else if (item.getTitle().equals("�쵿")) {
			message = "�쵿�� �����ϼ̽��ϴ�.";
		} else {
			return false;
		}
		message += "\n������ ���̵�� " + item.getItemId() + " �Դϴ�.";
		// ��Ŭ������ DDMS�� �α� Ĺ�� �ѱ��� ����� �� ���� ������
		// �ε����ϰ� �佺Ʈ�� ����Ͽ����ϴ�.
		// �佺Ʈ�� ���ؼ��� �����忡�� ������ ���Դϴ�.
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
		return true;
	}
}