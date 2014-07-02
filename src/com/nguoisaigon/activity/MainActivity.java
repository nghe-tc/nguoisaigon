package com.nguoisaigon.activity;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.db.MusicDB;
import com.nguoisaigon.db.MusicDataDB;
import com.nguoisaigon.db.SettingDB;
import com.nguoisaigon.entity.MusicDataInfo;
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
		indicator.getIndeterminateDrawable().setColorFilter(
				R.color.welcome_loading_color,
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
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/wg_legacy_edition.ttf");
		loadingLabel.setTypeface(tf);
		loadingLabel.setVisibility(View.VISIBLE);
	}

	@SuppressWarnings("deprecation")
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
						JSONObject object = (JSONObject) appSettingObject
								.get(0);
						info.setAppLink(object.getString(this
								.getString(R.string.setting_applink)));
						info.setParseAppId(object.getString(this
								.getString(R.string.setting_parseappid)));
						info.setSettingId(object.getString(this
								.getString(R.string.setting_settingid)));
						settingDB.insert(info);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			this.isAppSettingDownloaded = true;
		} 
//		else if (!this.isMusicDownloaded) {
//			if (musicList == null)// Get list of songs
//			{
//				MusicDB db = new MusicDB(this);
//				musicList = new ArrayList<MusicInfo>();
//				numOfSongs = result.length();
//				Log.i("MainActivity", "numof results " + numOfSongs);
//				try {
//					JSONObject object = result.getJSONObject(0);
//					MusicInfo info = new MusicInfo();
//					info.setOwnerInfo(object.getString("ownerInfo"));
//					info.setPlayListId(object.getString("playListId"));
//					info.setTitle(object.getString("title"));
//					info.setSinger(object.getString("singer"));
//					String url = object.getString("playUrl");
//					info.setPlayUrl(url);
//
//					musicList.add(info);
//					db.insert(info);
//
//					WebService musicWS = new WebService(this);
//					musicWS.setDownloadingMusicRequest(url,
//							info.getPlayListId());
//					Log.i("MainActivity", "download file " + url);
//					musicWS.execute();
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			} else // Download song
//			{
//				Log.i("MainActivity", "numofsong " + numOfSongs);
//				synchronized (this) {
//					if(numOfSongs > 1)
//					{
//						numOfSongs--;
//						String info;
//						try {
//							info = result.getString(0);
//							Log.i("MainActivity", "MusicInfo " + info);
//							MusicDataInfo musicData = (MusicDataInfo) new Gson()
//									.fromJson(info, MusicDataInfo.class);
//							Log.i("MainActivity",
//									"musicData " + musicData.getMusicData());
//							MusicDataDB db = new MusicDataDB(this);
//							db.insert(musicData);
//							
//							MusicInfo mInfo = musicList.get(musicList.size() - numOfSongs);
//
//							WebService musicWS = new WebService(this);
//							musicWS.setDownloadingMusicRequest(mInfo.getPlayUrl(),
//									mInfo.getPlayListId());
//							Log.i("MainActivity", "download file " + mInfo.getPlayUrl());
//							musicWS.execute();
//							
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							Log.e("MainActivity", "error " + e.getMessage());
//						}
//					}
//				}
//				if (numOfSongs <= 0)
//					this.isMusicDownloaded = true;
//
//			}
//		}
		Log.i("MainActivity", "numofsong 5362536" + numOfSongs);
//		if (numOfSongs == 0 && this.isAppSettingDownloaded
//				&& this.isMusicDownloaded) {
			Log.i("MainActivity", "numofsong 666666666666" + numOfSongs);
			// Call the home page
			indicator.setVisibility(View.GONE);
			Intent intent = new Intent(this, HomeScreenActivity.class);
			startActivity(intent);
//		}
	}

	@Override
	public void onBackPressed() {
	}

	/**
	 * Link to Facebook. Open link in native browser
	 * 
	 */
	void linkToFacebook() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://www.facebook.com/Cafedansaigon"));
		startActivity(browserIntent);
	}
}
