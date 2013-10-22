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
		/* DB에서 사용자 데이터를 가져와 에디트 텍스트에 채워 넣습니다. */
		loadUserData();
		/* 사용자 정보를 삭제할 리스너를 등록합니다. */
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
		/* 사용자 정보를 업데이트할 리스너를 등록합니다. */
		Button btSave = (Button) findViewById(R.id.bt_save);
		btSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SQLiteDatabase db = null;
				if (db == null) {
					db = openOrCreateDatabase("sqlite_test.db",
									SQLiteDatabase.CREATE_IF_NECESSARY,

									null);
				}
				// 에디트텍스트의 인스턴스를 가져옵니다.
				EditText etName = (EditText) findViewById(R.id.et_name);
				EditText etAge = (EditText) findViewById(R.id.et_age);
				EditText etArea = (EditText) findViewById(R.id.et_area);
				// 에디트텍스트로부터 입력된 데이터를 가져옵니다.
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
		button.setText("업데이트");
		etName.setText(name);
		etAge.setText(age);
		etArea.setText(area);
		if (db != null) {
			db.close();
		}
	}
}