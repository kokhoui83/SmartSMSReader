package com.aliquam.android.smartsmsreader.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.aliquam.android.smartsmsreader.R;

public class SMSReaderSettingsTab extends Activity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
	protected final String tag = "SmartSMSReader";
	protected static final String PREFS_NAME = "UserSettings";
	private CheckBox m_cbEnabled = null;
	private SeekBar m_sbVolume = null;
	private SeekBar m_sbSpeech = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.tab_settings);
    	
    	Initialize();
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
	    
		if(isChecked) {
			editor.putBoolean("ENABLE", true);
			m_cbEnabled.setText(R.string.cb_enabled);
			
			m_sbVolume.setEnabled(true);
		} else {
			editor.putBoolean("ENABLE", false);
			m_cbEnabled.setText(R.string.cb_disabled);
			
			m_sbVolume.setEnabled(false);
		}
		
		editor.commit();
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		if(seekBar.getId() == R.id.sbVolume) {
			Log.i(tag, "User volume = ".concat(String.valueOf(progress)));
			
			editor.putInt("VOLUME", progress);
		
		} else if(seekBar.getId() == R.id.sbSpeed) {
			Log.i(tag, "Speech rate = ".concat(String.valueOf(progress)));
		}
		
		editor.commit();
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	private void Initialize() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	boolean isEnabled = settings.getBoolean("ENABLE", false);
    	int volume = settings.getInt("VOLUME", 0);
    	
    	// Volume seek bar
		m_sbVolume = (SeekBar)findViewById(R.id.sbVolume);
		m_sbVolume.setOnSeekBarChangeListener(this);
		
		// Speech rate seek bar
		m_sbSpeech = (SeekBar)findViewById(R.id.sbSpeed);
		m_sbSpeech.setOnSeekBarChangeListener(this);
		
		AudioManager manager = (AudioManager) this.getApplicationContext().getSystemService(AUDIO_SERVICE);
		m_sbVolume.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		m_sbVolume.setProgress(volume);
		
    	// Check box
    	m_cbEnabled = (CheckBox)findViewById(R.id.cbEnable);
    	m_cbEnabled.setOnCheckedChangeListener(this);
		
		if(isEnabled) {
			m_cbEnabled.setChecked(true);
			m_cbEnabled.setText(R.string.cb_enabled);
			
			m_sbVolume.setEnabled(true);
		} else {
			m_cbEnabled.setChecked(false);
			m_cbEnabled.setText(R.string.cb_disabled);
			
			m_sbVolume.setEnabled(false);
		}
	}

}
