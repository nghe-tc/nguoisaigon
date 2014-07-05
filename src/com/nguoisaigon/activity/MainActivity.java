package com.nguoisaigon.activity;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nguoisaigon.R;
import com.nguoisaigon.db.SettingDB;
import com.nguoisaigon.entity.MusicInfo;
import com.nguoisaigon.entity.SettingInfo;
import com.nguoisaigon.util.MusicManager;
import com.nguoisaigon.util.SystemUiHider;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity implements WebServiceDelegate {

	private AnimationDrawable animation;
	private ProgressBar indicator;
	TextView loadingLabel;

	Boolean isAppSettingDownloaded = false;
	Boolean isMusicDownloaded = false;

	ArrayList<MusicInfo> musicList = null;
	Integer numOfSongs = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Setup UI view
		ImageView welcomeIcon = (ImageView) findViewById(R.id.welcomeicon);
		welcomeIcon.setBackgroundResource(R.drawable.welcomeiconani);
		indicator = (ProgressBar) findViewById(R.id.welcome_indicator);
		loadingLabel = (TextView) findViewById(R.id.welcom_loadinglabel);

		// Set indicator color
		indicator.getIndeterminateDrawable().setColorFilter(R.color.welcome_loading_color,
				android.graphics.PorterDuff.Mode.MULTIPLY);

		// Start animations
		if (animation == null) {
			animation = (AnimationDrawable) welcomeIcon.getBackground();
			animation.start();
		}

		// Download data
		WebService appSettingWS = new WebService(this);
		appSettingWS.setGettingAppSetting();
		appSettingWS.execute();

		// Download music
		MusicManager musicManager = new MusicManager(getApplicationContext());
		musicManager.getMusicInfoList();

		indicator.setVisibility(View.VISIBLE);

		// Setup loading label
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/noteworthy.ttc");

		loadingLabel.setTypeface(tf);
		loadingLabel.setVisibility(View.VISIBLE);
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		if (!this.isAppSettingDownloaded) {
			JSONArray appSettingObject = result;
			if (appSettingObject != null) {
				// Insert database
				SettingDB settingDB = new SettingDB(this);
				SettingInfo info = new SettingInfo();
				if (info != null) {
					try {
						JSONObject object = (JSONObject) appSettingObject.get(0);
						info.setAppLink(object.getString(this.getString(R.string.setting_applink)));
						info.setParseAppId(object.getString(this.getString(R.string.setting_parseappid)));
						info.setSettingId(object.getString(this.getString(R.string.setting_settingid)));
						settingDB.insert(info);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			this.isAppSettingDownloaded = true;
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// Call the home page
				indicator.setVisibility(View.GONE);
				Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
				startActivity(intent);
			}
		}, 3000);
	}

	@Override
	public void onBackPressed() {
	}

	/**
	 * Link to Facebook. Open link in native browser
	 * 
	 */
	void linkToFacebook() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Cafedansaigon"));
		startActivity(browserIntent);
	}
}
