
package kr.co.wikibook.get_contacts;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class GetContactsActivity extends ListActivity {
	private static final String URI = "content://kr.co.wikibook.provider"
					+ ".my_content_provider/friends";
	private static final String FRIEND = "friend";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ContentResolver cr = getContentResolver();
		ContentValues cv = new ContentValues();
		cv.put(FRIEND, "James");
		cr.insert(Uri.parse(URI), cv);
		ContentValues cv2 = new ContentValues();
		cv2.put(FRIEND, "Tom");
		cr.insert(Uri.parse(URI), cv2);
		Cursor c = cr.query(Uri.parse(URI), null, null, null, null);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_1, c,
						new String[] { FRIEND },
						new int[] { android.R.id.text1 });
		setListAdapter(adapter);
	}
}
