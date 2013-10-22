package kr.co.wikibook.usermng;

import android.app.ListActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListAdapter;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.content.Intent;

public class UserManagementActivity extends ListActivity {
	private static final String Q_CREATE_TABLE = "CREATE TABLE user_info (" +

	"_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT," + "age INTEGER,"
					+ "area TEXT" + ");";
	private final String Q_GET_LIST = "SELECT * FROM user_info"
					+ " ORDER BY _id DESC";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/*
		 * DB에 있는 목록을 가져옵니다. 여기서 가장 주의할 점은 액티비티가 비활성화 상태로 바뀌면 리스트의 목록은 소멸된다는
		 * 점입니다. 따라서 onResume()이 호출되는 시점에서 다시 한번 리스트를 가져와야 합니다.
		 */
		getDbData();
	}

	/** Called when the activity is first created. */
	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * DB에 있는 목록을 다시 가져옵니다.
		 */
		getDbData();
	}

	private void getDbData() {
		// 데이터베이스 관련 작업들을 초기화합니다.
		// DB를 열어서 SQLiteDatabase 인스턴스를 생성합니다.
		SQLiteDatabase db = null;
		if (db == null) {
			db = openOrCreateDatabase("sqlite_test.db",
							SQLiteDatabase.CREATE_IF_NECESSARY,

							null);
		}
		// 테이블에서 레코드를 가져오기 전에 테이블이 생성되었는지 확인합니다.
		checkTableIsCreated(db);
		Cursor c = db.rawQuery(Q_GET_LIST, null);
		startManagingCursor(c);
		ListAdapter adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_2, c, new String[] {
										"name", "area" },
						new int[] { android.R.id.text1, android.R.id.text2 });
		setListAdapter(adapter);
		// 여기서 커서를 닫는 실수를 범해서는 안 됩니다.
		// c.close();
		// 데이터베이스를 사용을 마치면 SQLiteDatabase 클래스의
		// close() 메서드를 호출해야 합니다.
		if (db != null) {
			db.close();
		}
	}

	private void checkTableIsCreated(SQLiteDatabase db) {
		// 테이블 생성을 확인하기 위해 sqlite_master 테이블을 조회합니다.
		Cursor c = db.query("sqlite_master", new String[] { "count(*)" },
						"name=?", new String[] { "user_info" }, null, null,
						null);
		Integer cnt = 0;
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			cnt = c.getInt(0);
			c.moveToNext();

		}
		// 커서는 사용 직후에 닫는 것이 중요합니다.
		c.close();
		// 테이블이 생성되지 않았다면 테이블을 생성합니다.
		if (cnt == 0) {
			db.execSQL(Q_CREATE_TABLE);
		}
	}

	/* 옵션 메뉴를 생성합니다. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "새 사용자 등록");
		return (super.onCreateOptionsMenu(menu));
	}

	/* 옵션 메뉴의 특정 아이템을 클릭했을 때 필요한 일들을 처리합니다. */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return (itemCallback(item) || super.onOptionsItemSelected(item));
	}

	/* 아이템의 아이디 값을 기준으로 필요한 일들을 처리합니다. */
	public boolean itemCallback(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Intent intent = new Intent(UserManagementActivity.this,
							AddUserActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

	/* 등록된 사용자를 클릭했을 때 EditUserActivity를 호출합니다. */
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		super.onListItemClick(parent, v, position, id);

		Intent intent = new Intent(UserManagementActivity.this,
						EditUserActivity.class);
		intent.putExtra("id", Long.toString(id));
		startActivity(intent);
	}
}