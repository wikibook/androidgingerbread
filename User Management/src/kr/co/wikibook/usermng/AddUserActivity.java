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
		// 사용자를 새로 등록할 때는 삭제 버튼을 숨깁니다.
		// 버튼을 다시 보여주고 싶다면 View.INVISIBLE 대신
		// View.GONE를 설정합니다.
		Button btDelete = (Button) findViewById(R.id.bt_delete);
		btDelete.setVisibility(View.INVISIBLE);
		Button btSave = (Button) findViewById(R.id.bt_save);
		btSave.setOnClickListener(new OnClickListener() {
			// 저장하기 버튼을 눌렀을 때 호출됩니다.
			public void onClick(View v) {
				// 데이터베이스 관련 작업을 초기화합니다.
				// DB를 열어서 SQLiteDatabase 인스턴스를 생성합니다.
				SQLiteDatabase db = null;
				if (db == null) {
					db = openOrCreateDatabase("sqlite_test.db",
									SQLiteDatabase.CREATE_IF_NECESSARY, null);
				}
				// 에디트텍스트의 인스턴스를 가져옵니다.
				EditText etName = (EditText) findViewById(R.id.et_name);
				EditText etAge = (EditText) findViewById(R.id.et_age);
				EditText etArea = (EditText) findViewById(R.id.et_area);
				// 에디트텍스트로부터 입력된 데이터를 가져옵니다.
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
				// 데이터베이스를 모두 사용하고 나면 SQLiteDatabase 클래스의
				// close() 메서드를 호출해야 합니다.
				if (db != null) {
					db.close();
				}
				finish();
			}
		});
	}
}