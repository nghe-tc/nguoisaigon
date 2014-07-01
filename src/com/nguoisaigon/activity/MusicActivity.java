package com.nguoisaigon.activity;

import com.nguoisaigon.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MusicActivity extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);
	}
	
	/**
	 * Close Music
	 * 
	 * @param view
	 */
	public void btnCloseClick(View view) {
		this.finish();
	}

	/**
	 * Share with Facebook
	 * 
	 * @param view
	 */
	public void btnFacebookClick(View view) {

	}

	/**
	 * Share with Email
	 * 
	 * @param view
	 */
	public void btnEmailClick(View view) {

	}
	
	/**
	 * Next song
	 * 
	 * @param view
	 */
	public void btnNextClick(View view) {

	}
	
	/**
	 * Play song
	 * 
	 * @param view
	 */
	public void btnPlayClick(View view) {

	}
	
	/**
	 * Stop song
	 * 
	 * @param view
	 */
	public void btnStopClick(View view) {

	}
	
	/**
	 * Back song
	 * 
	 * @param view
	 */
	public void btnBackClick(View view) {

	}
}
