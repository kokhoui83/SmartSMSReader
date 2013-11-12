package com.aliquam.android.smartsmsreader.settings;

import com.aliquam.android.smartsmsreader.R;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;

public class SmartSMSReaderSettings extends TabActivity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	Initialize();

    }

    private void Initialize() {
    	// Tab host
		TabHost tabs = getTabHost();
		tabs.setup();
		
		Intent intent;
		
		// SMS
		intent = new Intent().setClass(this, SMSReaderMainTab.class);
		AddTabs(tabs, "tag1", intent, "SMS", getResources().getDrawable(R.drawable.ic_tab_sms));
		// Dictionary
		intent = new Intent().setClass(this, SMSReaderDictionaryTab.class);
		AddTabs(tabs, "tag2", intent, "Dictionary", getResources().getDrawable(R.drawable.ic_tab_dictonary));
		// Settings
		intent = new Intent().setClass(this, SMSReaderSettingsTab.class);
		AddTabs(tabs, "tag3", intent, "Settings", getResources().getDrawable(R.drawable.ic_tab_options_selected));
		
	    tabs.setCurrentTab(0);
    }
    
    private void AddTabs(TabHost tabs, String name, Intent intent, String label, Drawable icon){
    	TabHost.TabSpec spec = tabs.newTabSpec(name);
    	spec.setContent(intent);
    	spec.setIndicator(label, icon);
    	tabs.addTab(spec);
    }
}
