package kr.co.wikibook.nfc_simple_reader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NFCSimpleReaderActivity extends ListActivity {

  ArrayList<String> ar = new ArrayList<String>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    ArrayAdapter<String> adapter = new ArrayAdapter(this,
        android.R.layout.simple_list_item_1, ar);
    setListAdapter(adapter);

    NdefMessage[] msgs = getNdefMessages(getIntent());
    putNfcDataToListView(msgs);
  }

  // ��Ƽ��Ƽ�� �̹� ���� ���϶� ����Ʈ�� �޾��� ���...
  @Override
  public void onNewIntent(Intent intent) {
    NdefMessage[] msgs = getNdefMessages(intent);
    putNfcDataToListView(msgs);
  }

  private void putNfcDataToListView(NdefMessage[] msgs) {
    ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();

    if (msgs != null && msgs.length > 0) {
      for (int i = 0; i < msgs.length; i++) {
        // NdefMessage���� NdefRecord ��ü �迭�� �����´�.
        NdefRecord[] records = msgs[i].getRecords();
        for (int j = 0; j < records.length; j++) {
          try {
            byte[] data = records[i].getPayload();

            /* ���̷ε� �������� 0��°�� ���ڵ� Ÿ���̴�. */
            String textEncoding = ((data[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = data[0] & 0077;
            String s = new String(data, languageCodeLength + 1, data.length
                - languageCodeLength - 1, textEncoding);

            adapter.add(s);
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private NdefMessage[] getNdefMessages(Intent intent) {
    // ����Ʈ �Ľ�
    NdefMessage[] msgs = null;
    String action = intent.getAction();
    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {

      Parcelable[] rawMsgs = intent
          .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

      if (rawMsgs != null) {
        msgs = new NdefMessage[rawMsgs.length];
        for (int i = 0; i < rawMsgs.length; i++) {
          msgs[i] = (NdefMessage) rawMsgs[i];
        }
      } else {
        // �� �� ���� ����Ʈ Ÿ���� ��쿡�� �� �����͸� ��ȯ�Ѵ�.
        byte[] empty = new byte[] {};
        NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty,
            empty, empty);
        NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
        msgs = new NdefMessage[] { msg };
      }
    } else {
      finish();
    }
    return msgs;
  }

}