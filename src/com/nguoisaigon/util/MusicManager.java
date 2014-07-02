/**
 * 
 */
package com.nguoisaigon.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.nguoisaigon.entity.MusicInfo;
import com.nguoisaigon.util.WebService.WebServiceDelegate;

/**
 * @author NPHUUTHY
 * 
 */
public class MusicManager implements WebServiceDelegate {

	/**
	 * The Constant TAG.
	 */
	private static final String TAG = MusicManager.class.getSimpleName();

	/**
	 * The context.
	 */
	private Context context;

	/**
	 * The Constant MUSIC_DATA.
	 */
	public static final String MUSIC_DATA = "MUSIC_DATA";

	/**
	 * Instantiates a new music manager.
	 * 
	 * @param context
	 *            the context
	 */
	public MusicManager(Context context) {
		this.context = context;
		
	}

	/**
	 * Gets the music info list.
	 * 
	 * @return the music info list
	 */
	public void getMusicInfoList() {
		WebService musicWS = new WebService(this);
		musicWS.setGettingMusic();
		musicWS.execute();
	}

	/**
	 * The is downloading music.
	 */
	private boolean isDownloadingMusic = false;

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i(TAG, (result == null) ? "null" : result.toString());
		if (result != null) {
			if (!isDownloadingMusic) {
				saveDataToSharedPreference(context, MUSIC_DATA, result.toString());
				try {
					for (int i = 0; i < result.length(); i++) {
						JSONObject musicJSON = result.getJSONObject(i);
						MusicInfo musicInfo = new MusicInfo();
						musicInfo.setOwnerInfo(musicJSON.getString("ownerInfo"));
						musicInfo.setPlayListId(musicJSON.getString("playListId"));
						musicInfo.setPlayUrl(musicJSON.getString("playUrl"));
						musicInfo.setSinger(musicJSON.getString("singer"));
						musicInfo.setTitle(musicJSON.getString("title"));
						isDownloadingMusic = true;
						WebService musicWS = new WebService(this);
						musicWS.setDownloadingMusicRequest(musicInfo.getPlayUrl(), musicInfo.getPlayListId());
						musicWS.execute();
					}
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}
			} else {
				isDownloadingMusic = false;
				try {
					if (result.length() == 1) {
						JSONObject musicJSON = result.getJSONObject(0);
						Log.i(TAG, "Save id: " + musicJSON.getString("playListId"));
					}
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Save data to shared preference.
	 * 
	 * @param context
	 *            the context
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public static boolean saveDataToSharedPreference(Context context, String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(MUSIC_DATA, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * Gets the data from shared preference.
	 * 
	 * @param context
	 *            the context
	 * @param key
	 *            the key
	 * @return the data from shared preference
	 */
	public static String getDataFromSharedPreference(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(MUSIC_DATA, 0);
		return settings.getString(key, "");
	}
}
