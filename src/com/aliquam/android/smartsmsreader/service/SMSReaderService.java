package com.aliquam.android.smartsmsreader.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReaderService extends BroadcastReceiver {
	private final String tag = "SmartSMSReader";
	
	@Override
    public void onReceive(Context context, Intent intent) {
    	Log.i(tag, "onReceive invoked");
    	
    	ProcessEvent(context, intent);
    }
	
	private void ProcessEvent(Context context, Intent intent) {
		Log.i(tag, "Processing event");
		
		Bundle extras = intent.getExtras();
    	
    	if(extras == null)
    	{
    		Log.i(tag, "Invalid message!");
    		return;
    	}
    	
    	Object[] pdus = (Object[]) extras.get("pdus");
    	Log.i(tag, "There are " + pdus.length + " message(s)");
    	
    	SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[0]);
    	String fromAddress = message.getOriginatingAddress();
    	Log.i(tag, "Message from: " + fromAddress + " " + message.getMessageBody().toString());
    	
    	Intent rtmIntent = new Intent();
    	rtmIntent.setClass(context, SmartSMSReaderActivity.class);
    	rtmIntent.putExtra("MESSAGE", message.getMessageBody().toString());
    	rtmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    	
    	context.startActivity(rtmIntent);
	}
}
