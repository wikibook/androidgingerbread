package kr.co.wikibook.my_contents_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {
	private static final String URI = "content://kr.co.wikibook.provider.my_content_provider"
					+ "/friends";
	public static final Uri CONTENT_URI = Uri.parse(URI);
	private static final UriMatcher uriMatcher;
	private static final String DATABASE_NAME = "friends.db";
	private static final int DATABASE_VERSION = 1;
	private static final int FRIEND_DATA = 1;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("kr.co.wikibook.provider.my_content_provider",
						"friends", FRIEND_DATA);
	}
	public static final String TABLE = "friend_list";
	public static final String ID = "_id";
	public static final String FRIEND = "friend";
	private SQLiteDatabase db = null;

	@Override
	public boolean onCreate() {
		Context context = getContext();
		FriendListHelper dbHelper = new FriendListHelper(context,
						DATABASE_NAME, null, DATABASE_VERSION);
		db = dbHelper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
					String[] selectionArgs, String sort) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE);
		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = ID;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
						null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri curi;
		switch (uriMatcher.match(uri)) {
		case FRIEND_DATA:
			long id = db.insert(TABLE, "friend", values);
			if (id > 0) {
				curi = ContentUris.withAppendedId(CONTENT_URI, id);
				getContext().getContentResolver().notifyChange(curi, null);
			} else {
				throw new SQLException("Failed to add new item into " + uri);
			}
			break;
		default:
			throw new IllegalArgumentException("Unsupport URI:" + uri);
		}
		return curi;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case FRIEND_DATA:
			count = db.delete(TABLE, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupport URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
					String[] whereArgs) {
		int count;
		switch (uriMatcher.match(uri)) {
		case FRIEND_DATA:
			count = db.update(TABLE, values, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupport URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case FRIEND_DATA:
			return " vnd.android.cursor.dir//vnd.wikibook.friends";
		default:
			throw new IllegalArgumentException("Unsupport URI:" + uri);
		}
	}

	private static class FriendListHelper extends SQLiteOpenHelper {
		private static final String TAG = "MyContentProvider";
		private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
						+ " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ FRIEND + " TEXT);";

		public FriendListHelper(Context context, String name,
						CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE);
			onCreate(db);
		}
	}
}