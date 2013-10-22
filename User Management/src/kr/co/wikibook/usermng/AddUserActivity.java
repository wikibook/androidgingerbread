package kr.co.wikibook.usermng;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class AddUserActivity extends Activity {
	private static final String TABLE = "user_info";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_form);
		// ����ڸ� ���� ����� ���� ���� ��ư�� ����ϴ�.
		// ��ư�� �ٽ� �����ְ� �ʹٸ� View.INVISIBLE ���
		// View.GONE�� �����մϴ�.
		Button btDelete = (Button) findViewById(R.id.bt_delete);
		btDelete.setVisibility(View.INVISIBLE);
		Button btSave = (Button) findViewById(R.id.bt_save);
		btSave.setOnClickListener(new OnClickListener() {
			// �����ϱ� ��ư�� ������ �� ȣ��˴ϴ�.
			public void onClick(View v) {
				// �����ͺ��̽� ���� �۾��� �ʱ�ȭ�մϴ�.
				// DB�� ��� SQLiteDatabase �ν��Ͻ��� �����մϴ�.
				SQLiteDatabase db = null;
				if (db == null) {
					db = openOrCreateDatabase("sqlite_test.db",
									SQLiteDatabase.CREATE_IF_NECESSARY, null);
				}
				// ����Ʈ�ؽ�Ʈ�� �ν��Ͻ��� �����ɴϴ�.
				EditText etName = (EditText) findViewById(R.id.et_name);
				EditText etAge = (EditText) findViewById(R.id.et_age);
				EditText etArea = (EditText) findViewById(R.id.et_area);
				// ����Ʈ�ؽ�Ʈ�κ��� �Էµ� �����͸� �����ɴϴ�.
				String name = etName.getText().toString();
				String age = etAge.getText().toString();
				String area = etArea.getText().toString();
				ContentValues values = new ContentValues();
				values.put("name", name);
				values.put("age", age);
				values.put("area", area);
				long aid = db.insert(TABLE, null, values);
				if (aid == -1) {
					Log.e(getLocalClassName(), "db insert - error occurred");
				}
				// �����ͺ��̽��� ��� ����ϰ� ���� SQLiteDatabase Ŭ������
				// close() �޼��带 ȣ���ؾ� �մϴ�.
				if (db != null) {
					db.close();
				}
				finish();
			}
		});
	}
}