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
		 * DB�� �ִ� ����� �����ɴϴ�. ���⼭ ���� ������ ���� ��Ƽ��Ƽ�� ��Ȱ��ȭ ���·� �ٲ�� ����Ʈ�� ����� �Ҹ�ȴٴ�
		 * ���Դϴ�. ���� onResume()�� ȣ��Ǵ� �������� �ٽ� �ѹ� ����Ʈ�� �����;� �մϴ�.
		 */
		getDbData();
	}

	/** Called when the activity is first created. */
	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * DB�� �ִ� ����� �ٽ� �����ɴϴ�.
		 */
		getDbData();
	}

	private void getDbData() {
		// �����ͺ��̽� ���� �۾����� �ʱ�ȭ�մϴ�.
		// DB�� ��� SQLiteDatabase �ν��Ͻ��� �����մϴ�.
		SQLiteDatabase db = null;
		if (db == null) {
			db = openOrCreateDatabase("sqlite_test.db",
							SQLiteDatabase.CREATE_IF_NECESSARY,

							null);
		}
		// ���̺��� ���ڵ带 �������� ���� ���̺��� �����Ǿ����� Ȯ���մϴ�.
		checkTableIsCreated(db);
		Cursor c = db.rawQuery(Q_GET_LIST, null);
		startManagingCursor(c);
		ListAdapter adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_2, c, new String[] {
										"name", "area" },
						new int[] { android.R.id.text1, android.R.id.text2 });
		setListAdapter(adapter);
		// ���⼭ Ŀ���� �ݴ� �Ǽ��� ���ؼ��� �� �˴ϴ�.
		// c.close();
		// �����ͺ��̽��� ����� ��ġ�� SQLiteDatabase Ŭ������
		// close() �޼��带 ȣ���ؾ� �մϴ�.
		if (db != null) {
			db.close();
		}
	}

	private void checkTableIsCreated(SQLiteDatabase db) {
		// ���̺� ������ Ȯ���ϱ� ���� sqlite_master ���̺��� ��ȸ�մϴ�.
		Cursor c = db.query("sqlite_master", new String[] { "count(*)" },
						"name=?", new String[] { "user_info" }, null, null,
						null);
		Integer cnt = 0;
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			cnt = c.getInt(0);
			c.moveToNext();

		}
		// Ŀ���� ��� ���Ŀ� �ݴ� ���� �߿��մϴ�.
		c.close();
		// ���̺��� �������� �ʾҴٸ� ���̺��� �����մϴ�.
		if (cnt == 0) {
			db.execSQL(Q_CREATE_TABLE);
		}
	}

	/* �ɼ� �޴��� �����մϴ�. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "�� ����� ���");
		return (super.onCreateOptionsMenu(menu));
	}

	/* �ɼ� �޴��� Ư�� �������� Ŭ������ �� �ʿ��� �ϵ��� ó���մϴ�. */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return (itemCallback(item) || super.onOptionsItemSelected(item));
	}

	/* �������� ���̵� ���� �������� �ʿ��� �ϵ��� ó���մϴ�. */
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

	/* ��ϵ� ����ڸ� Ŭ������ �� EditUserActivity�� ȣ���մϴ�. */
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		super.onListItemClick(parent, v, position, id);

		Intent intent = new Intent(UserManagementActivity.this,
						EditUserActivity.class);
		intent.putExtra("id", Long.toString(id));
		startActivity(intent);
	}
}