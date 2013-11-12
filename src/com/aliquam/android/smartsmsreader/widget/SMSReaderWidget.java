package com.aliquam.android.smartsmsreader.widget;

import com.aliquam.android.smartsmsreader.R;
import com.aliquam.android.smartsmsreader.settings.SmartSMSReaderSettings;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class SMSReaderWidget extends AppWidgetProvider {
	private final String tag = "SmartSMSReader";
	public static final String PREFS_NAME = "UserSettings";
	public static String CLICK_ENABLE = "com.aliquam.android.smartsmsreader.CLICK_ENABLE";
		
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
		Log.i(tag, "onUpdate invoked");
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
				
		final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            
            // Click enable button
            Intent onClickEnableIntent = new Intent(context, SMSReaderWidget.class);
            onClickEnableIntent.setAction(SMSReaderWidget.CLICK_ENABLE);
            PendingIntent pendingClickEnableIntent = PendingIntent.getBroadcast(context, 0, onClickEnableIntent, 0);
            views.setOnClickPendingIntent(R.id.ibtnEnable, pendingClickEnableIntent);
            
            // Click setting button
            Intent onClickSettingIntent = new Intent(context, SmartSMSReaderSettings.class);
            PendingIntent pendingClickSettingIntent = PendingIntent.getActivity(context, 0, onClickSettingIntent, 0);
            views.setOnClickPendingIntent(R.id.ibtnSetting, pendingClickSettingIntent);
            
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(tag, "onReceive invoked");
		
		final String action = intent.getAction();
		Log.i(tag, action);
		
		if (action.equals(SMSReaderWidget.CLICK_ENABLE)) {
			ProcessClickEnableEvent(context, intent);
		} else {
			super.onReceive(context, intent);
		}
	}
		
	private void ProcessClickEnableEvent(Context context, Intent intent) {
		Log.i(tag, "ProcessClickEnableEvent invoked");
		
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
    	boolean isEnabled = settings.getBoolean("ENABLE", false);
    	
    	if(isEnabled) {
    		// Set to disabled
    		editor.putBoolean("ENABLE", false);
    	} else {
    		// Set to enabled
    		editor.putBoolean("ENABLE", true);
    	}
    	
    	editor.commit();
	}
}
