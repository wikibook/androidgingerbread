package kr.co.wikibook.linear_layout;

import android.app.Activity;
import android.os.Bundle;

public class LinearLayoutActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //weight�� �׽�Ʈ�Ϸ��� ���� �ڵ带 �ּ�ó���ϰ� �Ʒ��� �ڵ��� �ּ��� �����ϼ���.
        //setContentView(R.layout.weight_test);
    }
}