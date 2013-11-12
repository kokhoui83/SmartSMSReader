package com.aliquam.android.smartsmsreader.service;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import java.util.HashMap;

public class SmartSMSReaderActivity extends Activity implements OnInitListener,OnUtteranceCompletedListener {
	private TextToSpeech tts = null;
	private final String tag = "SmartSMSReader";
	private String msg = "";
	private int currentVol = 0;
	public static final String PREFS_NAME = "UserSettings";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Log.i(tag,"onCreate invoked");
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	boolean isEnabled = settings.getBoolean("ENABLE", false);
		
    	if(isEnabled) {
    		Log.i(tag,"Read SMS enabled");
    		
			Intent startingIntent = this.getIntent();
			msg = startingIntent.getStringExtra("MESSAGE");
			tts = new TextToSpeech(this,this);
    	} else {
    		Log.i(tag,"Read SMS enabled");
    		
    		finish();
    	}
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(tag,"onDestroy invoked");
		
		if(tts != null) {
			tts.shutdown();
		}
	}
	
	// Implement OnInitListener
	public void onInit(int status) {
		Log.i(tag,"onInit invoked");
		
		SetAppVolume();
		
		HashMap<String,String> hm = new HashMap<String,String>();
		hm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "smsmessage");
		
		tts.setOnUtteranceCompletedListener(this);
		tts.speak(msg, TextToSpeech.QUEUE_FLUSH, hm);
	}
	
	// Implement OnUtteranceCompletedListener
	public void onUtteranceCompleted(String utteranceId) {
		Log.i(tag,"onUtteranceCompleted :" + utteranceId);
		
		RestoreUserVolume();
		
		tts.shutdown();
		tts = null;
		finish();
	}
	
	private void SetAppVolume() {
		Log.i(tag, "SetAppVolume invoked");
		
		AudioManager manager = (AudioManager) this.getApplicationContext().getSystemService(AUDIO_SERVICE);
		currentVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	int volume = settings.getInt("VOLUME", 0);
    	
    	Log.i(tag, "User volume = ".concat(String.valueOf(volume)));
		
		manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
	}
	
	private void RestoreUserVolume() {
		Log.i(tag, "RestoreUserVolume invoked");
		
		AudioManager manager = (AudioManager) this.getApplicationContext().getSystemService(AUDIO_SERVICE);
		
		manager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, 0);
	}
}