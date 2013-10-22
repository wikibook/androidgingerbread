package kr.co.wikibook.linear_layout;

import android.app.Activity;
import android.os.Bundle;

public class LinearLayoutActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //weight를 테스트하려면 위의 코드를 주석처리하고 아래의 코드의 주석을 해제하세요.
        //setContentView(R.layout.weight_test);
    }
}