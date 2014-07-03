package com.nguoisaigon.activity;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.MusicInfo;
import com.nguoisaigon.util.MusicManager;

public class MusicActivity extends Activity {

	private ArrayList<MusicInfo> songList = new ArrayList<>();
	private MediaPlayer player;
	private View stop, play;
	private ListView lvSongList;

	private int currentSong = 0;
	public static final String PREFIX_DOWNLOAD = "/.nguoisaigon/";
	private String path = Environment.getExternalStorageDirectory() + PREFIX_DOWNLOAD;

	private Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_layout);

		tf = Typeface.createFromAsset(getAssets(), "fonts/wg_legacy_edition.ttf");

		stop = (View) findViewById(R.id.btnMusicStop);
		play = (View) findViewById(R.id.btnMusicPlay);
		lvSongList = (ListView) findViewById(R.id.lvSongList);

		String musicData = MusicManager.getDataFromSharedPreference(getApplicationContext(), MusicManager.MUSIC_DATA);
		if (!TextUtils.isEmpty(musicData)) {
			try {
				JSONArray jsonArray = new JSONArray(musicData);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject musicJSON = jsonArray.getJSONObject(i);
					MusicInfo musicInfo = new MusicInfo();
					musicInfo.setOwnerInfo(musicJSON.getString("ownerInfo"));
					musicInfo.setPlayListId(musicJSON.getString("playListId"));
					musicInfo.setPlayUrl(musicJSON.getString("playUrl"));
					musicInfo.setSinger(musicJSON.getString("singer"));
					musicInfo.setTitle(musicJSON.getString("title"));
					songList.add(musicInfo);
				}
			} catch (Exception e) {
				Log.i("MusicActivity", e.getMessage());
			}
			Log.i("MusicActivity - total song", songList.size() + "");
		}
		updateData();
		player = new MediaPlayer();
		int size = songList.size();
		if (size > 0) {
			MusicInfo musicInfo = songList.get(currentSong);
			try {
				player.setDataSource(path + musicInfo.getPlayListId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		int size = songList.size();
		if (size > 0 && (size - 1) <= currentSong) {
			return;
		}
		currentSong++;
		playMusic(player, currentSong);
		play.setVisibility(View.GONE);
		stop.setVisibility(View.VISIBLE);
	}

	/**
	 * The first player start.
	 */
	private boolean isFirstStart = false;

	/**
	 * Play song
	 * 
	 * @param view
	 */
	public void btnPlayClick(View view) {
		int size = songList.size();
		if (size == 0) {
			return;
		}
		view.setVisibility(View.GONE);
		stop.setVisibility(View.VISIBLE);
		try {
			if (!isFirstStart) {
				playMusic(player, currentSong);
			} else {
				player.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop song
	 * 
	 * @param view
	 */
	public void btnStopClick(View view) {
		view.setVisibility(View.GONE);
		play.setVisibility(View.VISIBLE);
		if (player.isPlaying()) {
			player.pause();
		}
	}

	/**
	 * Back song
	 * 
	 * @param view
	 */
	public void btnBackClick(View view) {
		if (currentSong == 0) {
			return;
		}
		currentSong--;
		playMusic(player, currentSong);
		play.setVisibility(View.GONE);
		stop.setVisibility(View.VISIBLE);
	}

	/**
	 * Update data.
	 */
	public void updateData() {
		ProgressBar indicator = (ProgressBar) findViewById(R.id.musicIndicator);
		indicator.setVisibility(ProgressBar.GONE);
		TextView tvLoading = (TextView) findViewById(R.id.tvMusicLoading);
		tvLoading.setVisibility(TextView.GONE);

		// Assign adapter to ListView
		lvSongList.setAdapter(new MusicListAdapter(this));

		lvSongList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				stop.setVisibility(View.VISIBLE);
				play.setVisibility(View.GONE);
				currentSong = position;
				playMusic(player, currentSong);

				player.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						Log.i("Completion Listener", "Song Complete");
						int size = songList.size();
						if (size > 0 && (size - 1) == currentSong) {
							return;
						}
						currentSong++;
						playMusic(player, currentSong);
					}
				});
			}
		});
	}

	/**
	 * The Class MusicListAdapter.
	 */
	public class MusicListAdapter extends BaseAdapter {
		private final Context context;

		public MusicListAdapter(Context context) {
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.music_item, parent, false);

				holder = new ViewHolder();
				holder.musicTextView = (TextView) view.findViewById(R.id.songName);
				holder.musicTextView.setTypeface(tf);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			if (songList.size() <= 0) {
				holder.musicTextView.setText("No Song");
			} else {
				MusicInfo mInfo = songList.get(position);
				holder.musicTextView.setText(position + 1 + ". " + mInfo.getTitle() + " - " + mInfo.getSinger());
			}
			return view;
		}

		@Override
		public int getCount() {
			return songList.size();
		}

		@Override
		public Object getItem(int position) {
			return songList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}

	public static class ViewHolder {
		public TextView musicTextView;
	}

	/**
	 * Play music.
	 * 
	 * @param mp
	 *            the MediaPlayer
	 * @param id
	 *            the id
	 */
	private void playMusic(MediaPlayer mp, int id) {
		isFirstStart = true;
		MusicInfo musicInfo = songList.get(id);
		try {
			mp.stop();
			mp.reset();
			mp.setDataSource(path + musicInfo.getPlayListId());
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (player.isPlaying()) {
			player.stop();
			player.release();
		}
	}
}
