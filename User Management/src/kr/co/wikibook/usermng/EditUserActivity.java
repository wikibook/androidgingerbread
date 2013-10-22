package kr.co.wikibook.usermng;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class EditUserActivity extends Activity {
	private int _id = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_form);
		/* DB���� ����� �����͸� ������ ����Ʈ �ؽ�Ʈ�� ä�� �ֽ��ϴ�. */
		loadUserData();
		/* ����� ������ ������ �����ʸ� ����մϴ�. */
		Button btDelete = (Button) findViewById(R.id.bt_delete);
		btDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SQLiteDatabase db = null;
				if (db == null) {
					db = openOrCreateDatabase("sqlite_test.db",
									SQLiteDatabase.CREATE_IF_NECESSARY, null);
				}
				String query = "DELETE FROM user_info WHERE _id = " + _id;
				db.execSQL(query);
				if (db != null) {
					db.close();
				}
				finish();
			}
		});
		/* ����� ������ ������Ʈ�� �����ʸ� ����մϴ�. */
		Button btSave = (Button) findViewById(R.id.bt_save);
		btSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SQLiteDatabase db = null;
				if (db == null) {
					db = openOrCreateDatabase("sqlite_test.db",
									SQLiteDatabase.CREATE_IF_NECESSARY,

									null);
				}
				// ����Ʈ�ؽ�Ʈ�� �ν��Ͻ��� �����ɴϴ�.
				EditText etName = (EditText) findViewById(R.id.et_name);
				EditText etAge = (EditText) findViewById(R.id.et_age);
				EditText etArea = (EditText) findViewById(R.id.et_area);
				// ����Ʈ�ؽ�Ʈ�κ��� �Էµ� �����͸� �����ɴϴ�.
				String name = etName.getText().toString();
				String age = etAge.getText().toString();
				String area = etArea.getText().toString();
				String query = "UPDATE user_info SET" + " name = '" + name
								+ "', " + " age = " + age + ", " + " area = '"
								+ area + "' " + " WHERE _id = " + _id + " ";
				db.execSQL(query);
				if (db != null) {
					db.close();
				}
				finish();
			}
		} // OnClickListener
						); // btSave.setOnClickListener
	}

	private void loadUserData() {
		String id = getIntent().getExtras().getString("id");
		_id = Integer.parseInt(id);
		String query = "SELECT * FROM user_info WHERE _id = " + id;
		SQLiteDatabase db = null;
		if (db == null) {
			db = openOrCreateDatabase("sqlite_test.db",
							SQLiteDatabase.CREATE_IF_NECESSARY, null);

		}
		Cursor c = db.rawQuery(query, null);
		String name = "";
		String age = "";
		String area = "";
		if (c.moveToNext()) {
			int name_idx = c.getColumnIndex("name");
			name = c.getString(name_idx);
			int age_idx = c.getColumnIndex("age");
			age = c.getString(age_idx);
			int area_idx = c.getColumnIndex("area");
			area = c.getString(area_idx);
		}
		c.close();
		Button button = (Button) findViewById(R.id.bt_save);
		EditText etName = (EditText) findViewById(R.id.et_name);
		EditText etAge = (EditText) findViewById(R.id.et_age);
		EditText etArea = (EditText) findViewById(R.id.et_area);
		button.setText("������Ʈ");
		etName.setText(name);
		etAge.setText(age);
		etArea.setText(area);
		if (db != null) {
			db.close();
		}
	}
}