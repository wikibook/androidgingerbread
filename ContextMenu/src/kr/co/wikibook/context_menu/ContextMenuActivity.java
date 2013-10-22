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
		menu.setHeaderTitle("음식 고르기");
		menu.add(0, v.getId(), 0, "김밥");
		menu.add(0, v.getId(), 0, "라면");
		menu.add(0, v.getId(), 0, "짬뽕");
		menu.add(0, v.getId(), 0, "우동");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		String message = "";
		if (item.getTitle().equals("김밥")) {
			message = "김밥을 선택하셨습니다.";
		} else if (item.getTitle().equals("라면")) {
			message = "라면을 선택하셨습니다.";
		} else if (item.getTitle().equals("짬뽕")) {
			message = "짬뽕을 선택하셨습니다.";
		} else if (item.getTitle().equals("우동")) {
			message = "우동을 선택하셨습니다.";
		} else {
			return false;
		}
		message += "\n아이템 아이디는 " + item.getItemId() + " 입니다.";
		// 이클립스와 DDMS의 로그 캣은 한글을 출력할 수 없기 때문에
		// 부득이하게 토스트를 사용하였습니다.
		// 토스트에 대해서는 다음장에서 공부할 것입니다.
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
		return true;
	}
}