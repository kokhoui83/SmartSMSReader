package com.aliquam.android.smartsmsreader.settings;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.aliquam.android.smartsmsreader.R;

public class SMSReaderDictionaryTab extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//setContentView(R.layout.main);
    	
    	TextView textview = new TextView(this);
        textview.setText("This is the Dictionary tab");
        setContentView(textview);
    	
    }

}
