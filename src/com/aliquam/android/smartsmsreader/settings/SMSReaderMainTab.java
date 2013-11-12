package com.aliquam.android.smartsmsreader.settings;

import com.aliquam.android.smartsmsreader.R;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SMSReaderMainTab extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.tab_sms);
    	
    	Cursor curSms = managedQuery(Uri.parse("content://sms/inbox"), null, null, null, null);
        
        String[] body = new String[curSms.getCount()];
        String[] number = new String[curSms.getCount()];
        
        if(curSms.moveToFirst()) {
        	for(int i = 0; i < curSms.getCount(); i++) {
        		body[i]= curSms.getString(curSms.getColumnIndexOrThrow("body")).toString();
        		number[i]=curSms.getString(curSms.getColumnIndexOrThrow("address")).toString();
        		curSms.moveToNext();
        	}
        }
        
        ListView list = (ListView)findViewById(R.id.lsSMS);
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, number));
        list.setTextFilterEnabled(true);
    }
}
