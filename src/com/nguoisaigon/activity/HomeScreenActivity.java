package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.nguoisaigon.R;
import com.nguoisaigon.db.DBHelper;
import com.nguoisaigon.db.SettingDB;
import com.nguoisaigon.entity.SettingInfo;

public class HomeScreenActivity extends Activity {
	private DBHelper datahelper;

	private ImageView storeHelp;
	private ImageView eventHelp;
	private ImageView newsHelp;
	private ImageView musicHelp;
	private ImageView btnhomestore;
	private ImageView btnhomehelp;
	private ImageView btnhomefacebook;
	private ImageView btnhomephone;
	private ImageView btnhomemusic;
	private ImageView btnhometv;
	private ImageView btnhomeevents;
	Handler handler = new Handler();

	Runnable hideHelp = new Runnable() {

		@Override
		public void run() {
			storeHelp.setVisibility(ImageView.GONE);
			eventHelp.setVisibility(ImageView.GONE);
			newsHelp.setVisibility(ImageView.GONE);
			musicHelp.setVisibility(ImageView.GONE);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		storeHelp = (ImageView) findViewById(R.id.homeStoreHelp);
		eventHelp = (ImageView) findViewById(R.id.homeCalendarHelp);
		newsHelp = (ImageView) findViewById(R.id.homeNewsHelp);
		musicHelp = (ImageView) findViewById(R.id.homeMusicHelp);

		btnhomestore = (ImageView) findViewById(R.id.btnhomestore);
		btnhomehelp = (ImageView) findViewById(R.id.btnhomehelp);
		btnhomefacebook = (ImageView) findViewById(R.id.btnhomefacebook);
		btnhomephone = (ImageView) findViewById(R.id.btnhomephone);
		btnhomemusic = (ImageView) findViewById(R.id.btnhomemusic);
		btnhometv = (ImageView) findViewById(R.id.btnhometv);
		btnhomeevents = (ImageView) findViewById(R.id.btnhomeevents);

		ImageView tvImage = (ImageView) findViewById(R.id.homeTVAnimation);
		tvImage.setBackgroundResource(R.drawable.tvanimation);
		AnimationDrawable tvAnimation = (AnimationDrawable) tvImage
				.getBackground();
		tvAnimation.start();

		ImageView tvLight = (ImageView) findViewById(R.id.homeTVLight);
		tvLight.setBackgroundResource(R.drawable.tvlightanimation);
		AnimationDrawable tvLightAnimation = (AnimationDrawable) tvLight
				.getBackground();
		tvLightAnimation.start();

		ImageView musicImage = (ImageView) findViewById(R.id.homeMusic);
		musicImage.setBackgroundResource(R.drawable.musicanimation);
		AnimationDrawable musicAnimation = (AnimationDrawable) musicImage
				.getBackground();
		musicAnimation.start();

		ImageView phoneImage = (ImageView) findViewById(R.id.homePhone);
		phoneImage.setBackgroundResource(R.drawable.phoneanimation);
		AnimationDrawable phoneAnimation = (AnimationDrawable) phoneImage
				.getBackground();
		phoneAnimation.start();
		// setDatahelper(new DBHelper(this));

		this.showHelp();

		this.setOntouchListener();
	}

	public void btnstore_click(View view) {
		btnhomestore.setImageResource(R.drawable.store_normal);
		Intent intent = new Intent(this, StoreMainActivity.class);
		startActivity(intent);
	}

	public void btnhelp_click(View view) {
		btnhomehelp.setImageResource(R.drawable.help_normal);
		this.showHelp();
	}

	public void btnfacebook_click(View view) {
		btnhomefacebook.setImageResource(R.drawable.fbboard_normal);
		SettingDB settingDB = new SettingDB(this);
		SettingInfo info = settingDB.getSetting();

		// Intent intent = new Intent(this, FacebookPlugin.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("link", info.getAppLink());
		// bundle.putString("description", "Dialog description");
		// bundle.putString("caption", "Dialog Caption");
		//
		// intent.putExtras(bundle);
		// startActivity(intent);
//		FacebookPlugin fbPlugin = new FacebookPlugin("Dialog description",
//				info.getAppLink(), "Dialog Caption");
//		fbPlugin.postToWall();
//		fbPlugin.showDialog("Dialog description", info.getAppLink(),
//				"Dialog Caption");
	}

	public void btnphone_click(View view) {
		btnhomephone.setImageResource(R.drawable.phone_normal);
		// Intent intent = new Intent(this, StoreMainActivity.class);
		// startActivity(intent);
	}

	public void btnmusic_click(View view) {
		btnhomemusic.setImageResource(R.drawable.musicplayer_normal);
		Intent intent = new Intent(this, MusicActivity.class);
		startActivity(intent);
	}

	public void btnevents_click(View view) {
		btnhomeevents.setImageResource(R.drawable.calendar_normal);
		Intent intent = new Intent(this, EventsActivity.class);
		startActivity(intent);
	}

	public void btntv_click(View view) {
		btnhometv.setImageResource(R.drawable.tv_normal);
		Intent intent = new Intent(this, NewsActivity.class);
		startActivity(intent);
	}

	public DBHelper getDatahelper() {
		return datahelper;
	}

	public void setDatahelper(DBHelper datahelper) {
		this.datahelper = datahelper;
	}

	@Override
	public void onBackPressed() {
		// Exit application
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	void showHelp() {
		storeHelp.setVisibility(ImageView.VISIBLE);
		eventHelp.setVisibility(ImageView.VISIBLE);
		newsHelp.setVisibility(ImageView.VISIBLE);
		musicHelp.setVisibility(ImageView.VISIBLE);

		handler.postDelayed(hideHelp, 5000);
	}

	private void setOntouchListener() {
		btnhomehelp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomehelp.setImageResource(R.drawable.help_over);
				return false;
			}
		});

		btnhomefacebook.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomefacebook.setImageResource(R.drawable.fbboard_over);
				return false;
			}
		});

		btnhomestore.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomestore.setImageResource(R.drawable.store_over);
				return false;
			}
		});

		btnhomemusic.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomemusic.setImageResource(R.drawable.musicplayer_over);
				return false;
			}
		});

		btnhomephone.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomephone.setImageResource(R.drawable.phone_over);
				return false;
			}
		});

		btnhometv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhometv.setImageResource(R.drawable.tv_over);
				return false;
			}
		});

		btnhomeevents.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomeevents.setImageResource(R.drawable.calendar_over);
				return false;
			}
		});
	}
}
