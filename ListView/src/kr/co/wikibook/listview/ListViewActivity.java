package kr.co.wikibook.listview;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListView listview = (ListView) findViewById(R.id.listview);
		ArrayList<String> arraylist = new ArrayList<String>();
		arraylist.add("소녀시대");
		arraylist.add("원더걸스");
		arraylist.add("카라");
		ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.item,
						R.id.name, arraylist);
		listview.setAdapter(adapter);
	}
}